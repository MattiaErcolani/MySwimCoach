package dao;

import exceptions.CredenzialiSbagliateException;
import exceptions.EmailGiaInUsoException;
import exceptions.UtenteNonPresenteException;
import model.CredenzialiModel;
import model.UtenteLoggatoModel;
import other.Connect;
import query.Query;
import query.QueryLogin;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.sql.*;

public class UserDaoMYSQL implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoMYSQL.class.getName());
    private static final String ERRORE_DAO = "Errore in userDAO: ";
    private static final String SELECT_UTENTE = "SELECT email, password, nome, cognome, ruolo, age, level, certificate FROM utenti";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";
    private static final String COL_NOME = "nome";
    private static final String COL_COGNOME = "cognome";
    private static final String COL_RUOLO = "ruolo";

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
                    utenteloggatoModel.setNome(rs.getString(COL_NOME));
                    utenteloggatoModel.setCognome(rs.getString(COL_COGNOME));
                    utenteloggatoModel.getCredenziali().setEmail(rs.getString(COL_EMAIL));
                    utenteloggatoModel.getCredenziali().setPassword(password);
                    utenteloggatoModel.setIstructor(rs.getBoolean(COL_RUOLO));
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
        String sql = SELECT_UTENTE + " WHERE ruolo = 1";

        try (Connection connection = Connect.getInstance().getDBConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CredenzialiModel credenziali = new CredenzialiModel(
                        rs.getString(COL_EMAIL),
                        rs.getString(COL_PASSWORD)
                );
                UtenteLoggatoModel istruttore = new UtenteLoggatoModel(
                        credenziali,
                        rs.getString(COL_NOME),
                        rs.getString(COL_COGNOME),
                        true
                );
                lista.add(istruttore);
            }

        } catch (SQLException e) {
            logger.severe(ERRORE_DAO + e.getMessage());
        }

        return lista;
    }

    @Override
    public UtenteLoggatoModel getUserByEmail(String email) {
        String sql = SELECT_UTENTE + " WHERE email = ?";

        try (Connection connection = Connect.getInstance().getDBConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CredenzialiModel credenziali = new CredenzialiModel(
                            rs.getString(COL_EMAIL),
                            rs.getString(COL_PASSWORD)
                    );
                    return new UtenteLoggatoModel(
                            credenziali,
                            rs.getString(COL_NOME),
                            rs.getString(COL_COGNOME),
                            rs.getInt(COL_RUOLO) == 1
                    );
                }
            }

        } catch (SQLException e) {
            logger.severe(ERRORE_DAO + e.getMessage());
        }

        return null;
    }
}