package query;

import model.SchedaNuotoAssegnataModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuerySchedaNuotoAssegnata {

    private QuerySchedaNuotoAssegnata() {}

    public static void inserisciAssegnazione(Connection conn, SchedaNuotoAssegnataModel scheda) throws SQLException {
        String sql = "INSERT INTO scheda_nuoto_assegnata (idScheda, emailUser) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, scheda.getIdScheda());
            ps.setString(2, scheda.getEmailUser());
            ps.executeUpdate();
        }
    }

    public static List<SchedaNuotoAssegnataModel> getAllAssegnazioni(Connection conn) throws SQLException {
        List<SchedaNuotoAssegnataModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM scheda_nuoto_assegnata";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapResultSetToModel(rs));
            }
        }
        return lista;
    }

    public static List<SchedaNuotoAssegnataModel> cercaSchedeByEmailUser(Connection conn, String emailUser) throws SQLException {
        List<SchedaNuotoAssegnataModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM scheda_nuoto_assegnata WHERE emailUser = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emailUser);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSetToModel(rs));
                }
            }
        }
        return lista;
    }

    public static void rimuoviAssegnazione(Connection conn, String idScheda, String emailUser) throws SQLException {
        String sql = "DELETE FROM scheda_nuoto_assegnata WHERE idScheda = ? AND emailUser = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idScheda);
            ps.setString(2, emailUser);
            ps.executeUpdate();
        }
    }

    private static SchedaNuotoAssegnataModel mapResultSetToModel(ResultSet rs) throws SQLException {
        return new SchedaNuotoAssegnataModel(
                rs.getString("idScheda"),
                rs.getString("emailUser"),
                rs.getInt("distanzaTotale"),
                rs.getInt("durata")
        );
    }
}