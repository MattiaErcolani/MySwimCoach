package Query;

import Model.UtenteLoggatoModel;
import Other.Stampa;
import exceptions.CredenzialiSbagliateException;
import exceptions.UtenteNonPresenteException;
import exceptions.EmailGiaInUsoException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryLogin {

    private QueryLogin() {}

    public static void checkEmail(Statement smt, String email) throws UtenteNonPresenteException {
        try {
            String sql = String.format(Query.RICERCA_EMAIL, email);
            ResultSet rs = smt.executeQuery(sql);

            if (!rs.next()) {
                throw new UtenteNonPresenteException();
            }

        } catch (SQLException e) {
            throw new UtenteNonPresenteException();
        }
    }

    public static boolean emailReg(Statement smt, String email) throws EmailGiaInUsoException {
        try {
            String sql = String.format(Query.RICERCA_EMAIL, email);
            ResultSet rs = smt.executeQuery(sql);

            if (rs.next()) {
                throw new EmailGiaInUsoException();
            }
            return false;
        } catch (SQLException e) {
            throw new EmailGiaInUsoException();
        }
    }

    public static ResultSet loginUser(Statement stmt, String email, String password) throws CredenzialiSbagliateException {
        try {
            String sql = String.format(Query.VERIFICA_USER, email, password);
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new CredenzialiSbagliateException();
        }
    }

    public static void registerUser(Statement stmt, UtenteLoggatoModel registrazioneModel) {
        try {
            String email = registrazioneModel.getCredenziali().getEmail();
            String password = registrazioneModel.getCredenziali().getPassword();
            String nome = registrazioneModel.getNome();
            String cognome = registrazioneModel.getCognome();
            int ruolo = registrazioneModel.getisIstructor() ? 1 : 0;
            int age = registrazioneModel.getAge();
            String level = registrazioneModel.getLevel() != null ? registrazioneModel.getLevel() : "";
            String certificate = registrazioneModel.getCertificate() != null ? registrazioneModel.getCertificate() : "";

            String sql = String.format(Query.REGISTRAZIONE, email, password, nome, cognome, ruolo, age, level, certificate);
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            Stampa.errorPrint(String.format("QueryLogin: %s", e.getMessage()));
        }
    }

    public static void registraIstruttore(Statement stmt, String email, String nome, String cognome) {
        // Non serve inserire in una tabella separata, il ruolo è già salvato in utenti
    }

    private static void handleException(Exception e) {
        Stampa.errorPrint(String.format("QueryLogin: %s", e.getMessage()));
    }
}