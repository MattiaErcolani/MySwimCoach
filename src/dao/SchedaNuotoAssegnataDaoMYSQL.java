package dao;

import model.SchedaNuotoAssegnataModel;
import Other.Connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SchedaNuotoAssegnataDaoMYSQL implements SchedaNuotoAssegnataDao {

    private static final Logger logger = Logger.getLogger(SchedaNuotoAssegnataDaoMYSQL.class.getName());

    @Override
    public void assegnaScheda(SchedaNuotoAssegnataModel scheda) {
        String sql = "INSERT INTO scheda_nuoto_assegnata (idScheda, emailUser, distanzaTotale, durata) VALUES (?, ?, ?, ?)";

        try (Connection conn = Connect.getInstance().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, scheda.getIdScheda());
            ps.setString(2, scheda.getEmailUser());
            ps.setInt(3, scheda.getDistanzaTotale());
            ps.setInt(4, scheda.getDurata());
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Errore in assegnazione scheda: " + e.getMessage());
        }
    }

    @Override
    public List<SchedaNuotoAssegnataModel> getAllSchedeAssegnate() {
        List<SchedaNuotoAssegnataModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM scheda_nuoto_assegnata";

        try (Connection conn = Connect.getInstance().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSetToModel(rs));
            }

        } catch (SQLException e) {
            logger.severe("Errore getAllSchedeAssegnate: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public List<SchedaNuotoAssegnataModel> getSchedeByEmailUser(String emailUser) {
        List<SchedaNuotoAssegnataModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM scheda_nuoto_assegnata WHERE emailUser = ?";

        try (Connection conn = Connect.getInstance().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ps.setString(1, emailUser);

            while (rs.next()) {
                lista.add(mapResultSetToModel(rs));
            }

        } catch (SQLException e) {
            logger.severe("Errore getSchedeByEmailUser: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public void rimuoviAssegnazione(String idScheda, String emailUser) {
        String sql = "DELETE FROM scheda_nuoto_assegnata WHERE idScheda = ? AND emailUser = ?";

        try (Connection conn = Connect.getInstance().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idScheda);
            ps.setString(2, emailUser);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Errore rimuoviAssegnazione: " + e.getMessage());
        }
    }

    private SchedaNuotoAssegnataModel mapResultSetToModel(ResultSet rs) throws SQLException {
        return new SchedaNuotoAssegnataModel(
                rs.getString("idScheda"),
                rs.getString("emailUser"),
                rs.getInt("distanzaTotale"),
                rs.getInt("durata")
        );
    }
}