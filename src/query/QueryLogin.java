package query;

import model.UtenteLoggatoModel;
import other.Stampa;
import exceptions.UtenteNonPresenteException;
import exceptions.EmailGiaInUsoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryLogin {

    private QueryLogin() {}

    public static void checkEmail(Connection conn, String email) throws UtenteNonPresenteException {
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

    public static void registerUser(Connection conn, UtenteLoggatoModel registrazioneModel) {
        try (PreparedStatement pstmt = conn.prepareStatement(Query.REGISTRAZIONE)) {
            pstmt.setString(1, registrazioneModel.getCredenziali().getEmail());
            pstmt.setString(2, registrazioneModel.getCredenziali().getPassword());
            pstmt.setString(3, registrazioneModel.getNome());
            pstmt.setString(4, registrazioneModel.getCognome());
            pstmt.setInt(5, registrazioneModel.getIsIstructor() ? 1 : 0);
            pstmt.setInt(6, registrazioneModel.getAge());
            pstmt.setString(7, registrazioneModel.getLevel() != null ? registrazioneModel.getLevel() : "");
            pstmt.setString(8, registrazioneModel.getCertificate() != null ? registrazioneModel.getCertificate() : "");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Stampa.errorPrint(String.format("QueryLogin: %s", e.getMessage()));
        }
    }

    public static void registraIstruttore(Connection conn, String email, String nome, String cognome) {
        // Non serve inserire in una tabella separata, il ruolo è già salvato in utenti
    }
}