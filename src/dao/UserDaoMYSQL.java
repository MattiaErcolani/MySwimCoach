package dao;

import exceptions.CredenzialiSbagliateException;
import exceptions.EmailGiaInUsoException;
import exceptions.UtenteNonPresenteException;
import model.CredenzialiModel;
import model.UtenteLoggatoModel;
import other.Connect;
import other.Stampa;
import query.Query;
import query.QueryLogin;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.sql.*;

public class UserDaoMYSQL implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoMYSQL.class.getName());
    private static final String ERRORE_DAO = "Errore in userDAO: ";

    @Override
    public UtenteLoggatoModel loginMethod(CredenzialiModel credenzialiModel) throws UtenteNonPresenteException, CredenzialiSbagliateException {
        UtenteLoggatoModel utenteloggatoModel = new UtenteLoggatoModel();

        try {
            Connection connection = Connect.getInstance().getDBConnection();
            String email = credenzialiModel.getEmail();
            String password = credenzialiModel.getPassword();

            QueryLogin.checkEmail(connection, email);

            try (PreparedStatement ps = connection.prepareStatement(Query.VERIFICA_USER)) {
                ps.setString(1, email);
                ps.setString(2, password);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new CredenzialiSbagliateException();
                    }
                    if (utenteloggatoModel.getCredenziali() == null) {
                        utenteloggatoModel.setCredenziali(new CredenzialiModel());
                    }
                    utenteloggatoModel.setNome(rs.getString("Nome"));
                    utenteloggatoModel.setCognome(rs.getString("Cognome"));
                    utenteloggatoModel.getCredenziali().setEmail(rs.getString("Email"));
                    utenteloggatoModel.getCredenziali().setPassword(password);
                    utenteloggatoModel.setIstructor(rs.getBoolean("ruolo"));
                }
            }
        } catch (SQLException e) {
            logger.severe(ERRORE_DAO + e.getMessage());
        } catch (UtenteNonPresenteException e) {
            throw new UtenteNonPresenteException();
        }

        return utenteloggatoModel;
    }


    @Override
    public void registrazioneMethod(UtenteLoggatoModel registrazioneModel) {
        Connection connection = Connect.getInstance().getDBConnection();
        QueryLogin.registerUser(connection, registrazioneModel);
    }

    public void controllaEmailMethod(UtenteLoggatoModel registrazioneModel) throws EmailGiaInUsoException {
        Connection connection = Connect.getInstance().getDBConnection();
        String email = registrazioneModel.getCredenziali().getEmail();
        try {
            QueryLogin.emailReg(connection, email);
        } catch (EmailGiaInUsoException e) {
            throw new EmailGiaInUsoException();
        }
    }

    public void registraIstruttoreMethod(String email, String nome, String cognome) {
        Connection connection = Connect.getInstance().getDBConnection();
        QueryLogin.registraIstruttore(connection, email, nome, cognome);
    }

    @Override
    public List<UtenteLoggatoModel> getIstruttori() {
        List<UtenteLoggatoModel> lista = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Connection connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM utenti WHERE ruolo = 1");

            while (rs.next()) {
                CredenzialiModel credenziali = new CredenzialiModel(
                        rs.getString("email"),
                        rs.getString("password")
                );
                UtenteLoggatoModel istruttore = new UtenteLoggatoModel(
                        credenziali,
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        true
                );
                lista.add(istruttore);
            }

        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            closeResources(stmt, rs);
        }

        return lista;
    }

    @Override
    public UtenteLoggatoModel getUserByEmail(String email) {
        ResultSet rs = null;
        String sql = "SELECT * FROM utenti WHERE email = ?";

        try (Connection connection = Connect.getInstance().getDBConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                CredenzialiModel credenziali = new CredenzialiModel(
                        rs.getString("email"),
                        rs.getString("password")
                );
                return new UtenteLoggatoModel(
                        credenziali,
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getInt("ruolo") == 1
                );
            }

        } catch (SQLException e) {
            handleDAOException(e);
        }

        return null;
    }

    private void handleDAOException(Exception e) {
        Stampa.errorPrint(String.format("UserDAOMySQL: %s", e.getMessage()));
    }

    private void closeResources(Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            handleDAOException(e);
        }
    }
}