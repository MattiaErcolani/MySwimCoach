package dao;

import exceptions.CredenzialiSbagliateException;
import exceptions.EmailGiaInUsoException;
import exceptions.UtenteNonPresenteException;
import model.CredenzialiModel;
import model.UtenteLoggatoModel;
import other.Connect;
import other.Stampa;
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
        Statement stmt;

        try {
            Connection connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();

            String email = credenzialiModel.getEmail();
            String password = credenzialiModel.getPassword();

            QueryLogin.checkEmail(stmt, email);

            try (ResultSet rs = QueryLogin.loginUser(stmt, email, password)) {
                if (!rs.next()) {
                    throw new CredenzialiSbagliateException();
                } else {
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
        try (Connection connection = Connect.getInstance().getDBConnection();
             Statement stmt = connection.createStatement()) {
            QueryLogin.registerUser(stmt, registrazioneModel);
        } catch (SQLException e) {
            logger.severe(ERRORE_DAO + e.getMessage());
        }
    }

    public void controllaEmailMethod(UtenteLoggatoModel registrazioneModel) throws EmailGiaInUsoException {
        Statement stmt = null;

        try (Connection connection = Connect.getInstance().getDBConnection()) {
            stmt = connection.createStatement();
            String email = registrazioneModel.getCredenziali().getEmail();
            boolean emailInUse = QueryLogin.emailReg(stmt, email);
            if (emailInUse) {
                throw new EmailGiaInUsoException();
            }
        } catch (SQLException e) {
            logger.severe(ERRORE_DAO + e.getMessage());
        } finally {
            closeResources(stmt, null);
        }
    }

    public void registraIstruttoreMethod(String email, String nome, String cognome) {
        Statement stmt = null;

        try (Connection connection = Connect.getInstance().getDBConnection()) {
            stmt = connection.createStatement();
            QueryLogin.registraIstruttore(stmt, email, nome, cognome);
        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            closeResources(stmt, null);
        }
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
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Connection connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();
            String sql = String.format("SELECT * FROM utenti WHERE email = '%s'", email);
            rs = stmt.executeQuery(sql);

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
        } finally {
            closeResources(stmt, rs);
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