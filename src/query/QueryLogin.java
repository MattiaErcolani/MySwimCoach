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
        String sql = "SELECT email FROM utenti WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new UtenteNonPresenteException();
            }
        } catch (SQLException e) {
            throw new UtenteNonPresenteException();
        }
    }

    public static boolean emailReg(Connection conn, String email) throws EmailGiaInUsoException {
        String sql = "SELECT email FROM utenti WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                throw new EmailGiaInUsoException();
            }
            return false;
        } catch (SQLException e) {
            throw new EmailGiaInUsoException();
        }
    }

    public static ResultSet loginUser(Connection conn, String email, String password) throws CredenzialiSbagliateException {
        String sql = "SELECT * FROM utenti WHERE email = ? AND password = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            return ps.executeQuery();
        } catch (SQLException e) {
            throw new CredenzialiSbagliateException();
        }
    }

    public static void registerUser(Connection conn, UtenteLoggatoModel registrazioneModel) {
        String sql = "INSERT INTO utenti (email, password, nome, cognome, ruolo, age, level, certificate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, registrazioneModel.getCredenziali().getEmail());
            ps.setString(2, registrazioneModel.getCredenziali().getPassword());
            ps.setString(3, registrazioneModel.getNome());
            ps.setString(4, registrazioneModel.getCognome());
            ps.setInt(5, registrazioneModel.getIsIstructor() ? 1 : 0);
            ps.setInt(6, registrazioneModel.getAge());
            ps.setString(7, registrazioneModel.getLevel() != null ? registrazioneModel.getLevel() : "");
            ps.setString(8, registrazioneModel.getCertificate() != null ? registrazioneModel.getCertificate() : "");
            ps.executeUpdate();
        } catch (SQLException e) {
            Stampa.errorPrint(String.format("QueryLogin: %s", e.getMessage()));
        }
    }

    public static void registraIstruttore(Connection conn, String email, String nome, String cognome) {
        // Non serve inserire in una tabella separata, il ruolo è già salvato in utenti
    }
}