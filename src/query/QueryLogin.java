package query;

import model.UtenteLoggatoModel;
import other.Stampa;
import exceptions.CredenzialiSbagliateException;
import exceptions.UtenteNonPresenteException;
import exceptions.EmailGiaInUsoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryLogin {

    private QueryLogin() {}

    public static void checkEmail(Connection conn, String email) throws UtenteNonPresenteException {
        // NOTA: La tua costante Query.RICERCA_EMAIL nella classe Query dovrà ora usare il punto di domanda '?'
        // Esempio atteso in Query.java: public static final String RICERCA_EMAIL = "SELECT * FROM utenti WHERE email = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(Query.RICERCA_EMAIL)) {
            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) {
                    throw new UtenteNonPresenteException();
                }
            }
        } catch (SQLException e) {
            throw new UtenteNonPresenteException();
        }
    }

    public static boolean emailReg(Connection conn, String email) throws EmailGiaInUsoException {
        try (PreparedStatement pstmt = conn.prepareStatement(Query.RICERCA_EMAIL)) {
            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    throw new EmailGiaInUsoException();
                }
                return false;
            }
        } catch (SQLException e) {
            throw new EmailGiaInUsoException();
        }
    }

    // ATTENZIONE: Ritornare un ResultSet aperto fuori dal blocco try-with-resources può generare altri avvisi (Resource Leak).
    // È fortemente consigliato mappare i dati del ResultSet in un oggetto Model dentro questo metodo e chiudere il ResultSet.
    public static ResultSet loginUser(Connection conn, String email, String password) throws CredenzialiSbagliateException {
        // Esempio atteso in Query.java: public static final String VERIFICA_USER = "SELECT * FROM utenti WHERE email = ? AND password = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(Query.VERIFICA_USER);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            return pstmt.executeQuery();
        } catch (SQLException e) {
            throw new CredenzialiSbagliateException();
        }
    }

    public static void registerUser(Connection conn, UtenteLoggatoModel registrazioneModel) {
        // Esempio atteso in Query.java: public static final String REGISTRAZIONE = "INSERT INTO utenti (email, password, nome, cognome, ruolo, age, level, certificate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(Query.REGISTRAZIONE)) {

            String email = registrazioneModel.getCredenziali().getEmail();
            String password = registrazioneModel.getCredenziali().getPassword();
            String nome = registrazioneModel.getNome();
            String cognome = registrazioneModel.getCognome();
            int ruolo = registrazioneModel.getIsIstructor() ? 1 : 0;
            int age = registrazioneModel.getAge();
            String level = registrazioneModel.getLevel() != null ? registrazioneModel.getLevel() : "";
            String certificate = registrazioneModel.getCertificate() != null ? registrazioneModel.getCertificate() : "";

            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, nome);
            pstmt.setString(4, cognome);
            pstmt.setInt(5, ruolo);
            pstmt.setInt(6, age);
            pstmt.setString(7, level);
            pstmt.setString(8, certificate);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            Stampa.errorPrint(String.format("QueryLogin: %s", e.getMessage()));
        }
    }

    public static void registraIstruttore(Connection conn, String email, String nome, String cognome) {
        // Non serve inserire in una tabella separata, il ruolo è già salvato in utenti
    }

    private static void handleException(Exception e) {
        Stampa.errorPrint(String.format("QueryLogin: %s", e.getMessage()));
    }
}