package Dao;

import Model.SchedaNuotoAssegnataModel;
import Other.Connect;
import Other.Stampa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchedaNuotoAssegnataDaoMYSQL implements SchedaNuotoAssegnataDao {

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
            Stampa.errorPrint("Errore in assegnazione scheda: " + e.getMessage());
        }
    }

    @Override
    public List<SchedaNuotoAssegnataModel> getAllSchedeAssegnate() {
        List<SchedaNuotoAssegnataModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM scheda_nuoto_assegnata";

        try (Connection conn = Connect.getInstance().getDBConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new SchedaNuotoAssegnataModel(
                        rs.getString("idScheda"),
                        rs.getString("emailUser"),
                        rs.getInt("distanzaTotale"),
                        rs.getInt("durata")
                ));
            }

        } catch (SQLException e) {
            Stampa.errorPrint("Errore getAllSchedeAssegnate: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public List<SchedaNuotoAssegnataModel> getSchedeByEmailUser(String emailUser) {
        List<SchedaNuotoAssegnataModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM scheda_nuoto_assegnata WHERE emailUser = ?";

        try (Connection conn = Connect.getInstance().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, emailUser);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new SchedaNuotoAssegnataModel(
                        rs.getString("idScheda"),
                        rs.getString("emailUser"),
                        rs.getInt("distanzaTotale"),
                        rs.getInt("durata")
                ));
            }

        } catch (SQLException e) {
            Stampa.errorPrint("Errore getSchedeByEmailUser: " + e.getMessage());
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
            Stampa.errorPrint("Errore rimuoviAssegnazione: " + e.getMessage());
        }
    }
}
