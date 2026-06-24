package Dao;

import Model.SchedaNuotoModel;
import Other.Connect;
import Other.Stampa;
import Query.QuerySchedaNuoto;
import Model.EsercizioModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchedaNuotoDaoMYSQL implements SchedaNuotoDao {

    @Override
    public void insertScheda(SchedaNuotoModel scheda) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();

            // La query effettiva è in QuerySchedaNuoto
            QuerySchedaNuoto.inserisciScheda(stmt, scheda);

        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            closeResources(stmt, null);
        }
    }

    @Override
    public void updateScheda(SchedaNuotoModel scheda) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();
            QuerySchedaNuoto.aggiornaScheda(stmt, scheda);
        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            closeResources(stmt, null);
        }
    }

    @Override
    public void deleteScheda(String idScheda) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();
            QuerySchedaNuoto.cancellaScheda(stmt, idScheda);
        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            closeResources(stmt, null);
        }
    }

    @Override
    public SchedaNuotoModel getSchedaById(String idScheda) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        SchedaNuotoModel scheda = null;

        try {
            connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();
            rs = QuerySchedaNuoto.cercaSchedaById(stmt, idScheda);

            if (rs.next()) {
                scheda = new SchedaNuotoModel(
                        rs.getString("id_scheda"),
                        rs.getInt("distanza_totale"),
                        rs.getInt("durata"),
                        rs.getString("livello")
                );
                scheda.setEmailIstruttore(rs.getString("email_istruttore"));
            }

        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            closeResources(stmt, rs);
        }

        return scheda;
    }

    @Override
    public List<SchedaNuotoModel> getAllSchede() {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<SchedaNuotoModel> schede = new ArrayList<>();

        try {
            connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();
            rs = QuerySchedaNuoto.getAllSchede(stmt);

            while (rs.next()) {
                SchedaNuotoModel s = new SchedaNuotoModel(
                        rs.getString("id_scheda"),
                        rs.getInt("distanza_totale"),
                        rs.getInt("durata"),
                        rs.getString("livello")
                );
                s.setEmailIstruttore(rs.getString("email_istruttore"));

                // Carica esercizi della scheda
                Statement stmt2 = connection.createStatement();
                ResultSet rsEs = QuerySchedaNuoto.getEserciziByScheda(stmt2, s.getIdScheda());
                while (rsEs.next()) {
                    EsercizioModel es = new EsercizioModel(
                            rsEs.getString("nome"),
                            rsEs.getString("stile"),
                            rsEs.getInt("distanza"),
                            rsEs.getString("info")
                    );
                    s.getEsercizi().add(es);
                }
                rsEs.close();
                stmt2.close();

                schede.add(s);
            }

        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            closeResources(stmt, rs);
        }

        return schede;
    }



    // --- Metodi di supporto ---
    private void closeResources(Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            handleDAOException(e);
        }
    }

    private void handleDAOException(Exception e) {
        Stampa.errorPrint("SchedaNuotoDAO: " + e.getMessage());
    }

    @Override
    public void insertEsercizio(String idScheda, EsercizioModel esercizio) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();
            QuerySchedaNuoto.inserisciEsercizio(stmt, idScheda, esercizio);
        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            closeResources(stmt, null);
        }
    }

    @Override
    public void deleteEsercizio(String idScheda, EsercizioModel esercizio) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();
            QuerySchedaNuoto.cancellaEsercizio(stmt, idScheda, esercizio);
        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            closeResources(stmt, null);
        }
    }
}

