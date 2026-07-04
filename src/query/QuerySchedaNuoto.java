package query;

import model.EsercizioModel;
import model.SchedaNuotoModel;
import other.Stampa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuerySchedaNuoto {

    private QuerySchedaNuoto() { }

    // --- Inserisce una nuova scheda nel DB ---
    public static void inserisciScheda(Connection conn, SchedaNuotoModel scheda) {
        try {
            // Calcola il prossimo id_richiesta
            int nextId = 1;
            try (PreparedStatement psId = conn.prepareStatement("SELECT COALESCE(MAX(id_richiesta), 0) + 1 AS nextId FROM schede_nuoto");
                 ResultSet rs = psId.executeQuery()) {
                if (rs.next()) {
                    nextId = rs.getInt("nextId");
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(Query2.INSERISCI_SCHEDA)) {
                ps.setString(1, scheda.getIdScheda());
                ps.setInt(2, scheda.getDistanzaTotale());
                ps.setInt(3, scheda.getDurata());
                ps.setString(4, scheda.getLivello());
                ps.setInt(5, nextId);
                ps.setString(6, scheda.getEmailIstruttore());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            handleException(e);
        }
    }

    // --- Recupera una scheda tramite ID ---
    public static SchedaNuotoModel cercaSchedaById(Connection conn, String idScheda) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(Query2.CERCA_SCHEDA_BY_ID)) {
            ps.setString(1, idScheda);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SchedaNuotoModel scheda = new SchedaNuotoModel(
                            rs.getString("id_scheda"),
                            rs.getInt("distanza_totale"),
                            rs.getInt("durata"),
                            rs.getString("livello")
                    );
                    scheda.setEmailIstruttore(rs.getString("email_istruttore"));
                    return scheda;
                }
            }
        }
        return null;
    }

    // --- Recupera tutte le schede ---
    public static List<SchedaNuotoModel> getAllSchede(Connection conn) throws SQLException {
        List<SchedaNuotoModel> schede = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(Query2.CERCA_TUTTE_LE_SCHEDE);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SchedaNuotoModel s = new SchedaNuotoModel(
                        rs.getString("id_scheda"),
                        rs.getInt("distanza_totale"),
                        rs.getInt("durata"),
                        rs.getString("livello")
                );
                s.setEmailIstruttore(rs.getString("email_istruttore"));
                schede.add(s);
            }
        }
        return schede;
    }

    // --- Aggiorna una scheda ---
    public static void aggiornaScheda(Connection conn, SchedaNuotoModel scheda) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(Query2.AGGIORNA_SCHEDA)) {
            ps.setInt(1, scheda.getDistanzaTotale());
            ps.setInt(2, scheda.getDurata());
            ps.setString(3, scheda.getLivello());
            ps.setString(4, scheda.getIdScheda());
            ps.executeUpdate();
        }
    }

    // --- Cancella una scheda ---
    public static void cancellaScheda(Connection conn, String idScheda) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(Query2.CANCELLA_SCHEDA)) {
            ps.setString(1, idScheda);
            ps.executeUpdate();
        }
    }

    // --- Inserisce un esercizio ---
    public static void inserisciEsercizio(Connection conn, String idScheda, EsercizioModel esercizio) {
        try (PreparedStatement ps = conn.prepareStatement(Query2.INSERISCI_ESERCIZIO)) {
            ps.setString(1, idScheda);
            ps.setString(2, esercizio.getNome());
            ps.setString(3, esercizio.getStile());
            ps.setInt(4, esercizio.getDistanza());
            ps.setString(5, esercizio.getInfo());
            ps.executeUpdate();
        } catch (SQLException e) {
            handleException(e);
        }
    }

    // --- Recupera esercizi per scheda ---
    public static List<EsercizioModel> getEserciziByScheda(Connection conn, String idScheda) throws SQLException {
        List<EsercizioModel> esercizi = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(Query2.CERCA_ESERCIZI_BY_SCHEDA)) {
            ps.setString(1, idScheda);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    esercizi.add(new EsercizioModel(
                            rs.getString("nome"),
                            rs.getString("stile"),
                            rs.getInt("distanza"),
                            rs.getString("info")
                    ));
                }
            }
        }
        return esercizi;
    }

    // --- Cancella un esercizio ---
    public static void cancellaEsercizio(Connection conn, String idScheda, EsercizioModel esercizio) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(Query2.CANCELLA_ESERCIZIO)) {
            ps.setString(1, idScheda);
            ps.setString(2, esercizio.getNome());
            ps.setString(3, esercizio.getStile());
            ps.setInt(4, esercizio.getDistanza());
            ps.setString(5, esercizio.getInfo());
            ps.executeUpdate();
        }
    }

    // --- Gestione eccezioni centralizzata ---
    private static void handleException(Exception e) {
        Stampa.errorPrint(String.format("SchedaNuotoQuery: %s", e.getMessage()));
    }
}