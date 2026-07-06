package query;

import exceptions.UtenteNonPresenteException;
import model.RichiestaSchedaNuotoModel;
import other.StatoRichiestaScheda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryRichiesteSchedaNuoto {

    private QueryRichiesteSchedaNuoto() { }

    // --- Inserisce una nuova richiesta nel DB ---
    public static void inserisciRichiesta(Connection conn, RichiestaSchedaNuotoModel richiesta) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(Query2.INSERISCI_RICHIESTA_SCHEDA)) {
            ps.setInt(1, richiesta.getIdRichiesta());
            ps.setString(2, richiesta.getEmailUser());
            ps.setString(3, richiesta.getEmailIstruttore());
            ps.setString(4, richiesta.getLivelloUtente());
            ps.setString(5, richiesta.getInfo());
            ps.setString(6, richiesta.getStatus().name());
            ps.setString(7, richiesta.getDataRichiesta().toString());
            ps.executeUpdate();
        }
    }

    // --- Cerca richieste per utente ---
    public static List<RichiestaSchedaNuotoModel> cercaRichiesteByEmailUser(Connection conn, String emailUser)
            throws SQLException, UtenteNonPresenteException {

        List<RichiestaSchedaNuotoModel> lista = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(Query2.CERCA_RICHIESTE_USER)) {
            ps.setString(1, emailUser);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSetToModel(rs));
                }
            }
        }

        if (lista.isEmpty()) {
            throw new UtenteNonPresenteException();
        }

        return lista;
    }

    // --- Cerca richieste per istruttore ---
    public static List<RichiestaSchedaNuotoModel> cercaRichiesteByEmailIstruttore(Connection conn, String emailIstruttore)
            throws SQLException, UtenteNonPresenteException {

        List<RichiestaSchedaNuotoModel> lista = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(Query2.CERCA_RICHIESTE_ISTRUTTORE)) {
            ps.setString(1, emailIstruttore);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSetToModel(rs));
                }
            }
        }

        if (lista.isEmpty()) {
            throw new UtenteNonPresenteException();
        }

        return lista;
    }

    // --- Cancella richiesta ---
    public static void cancellaRichiestaSchedaNuoto(Connection conn, int idRichiesta, String emailUser)
            throws SQLException, UtenteNonPresenteException {

        try (PreparedStatement ps = conn.prepareStatement(Query2.CANCELLA_RICHIESTA_SCHEDA)) {
            ps.setInt(1, idRichiesta);
            ps.setString(2, emailUser);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new UtenteNonPresenteException();
            }
        }
    }

    // --- Aggiorna stato della richiesta ---
    public static void aggiornaStatoRichiesta(Connection conn, int idRichiesta, StatoRichiestaScheda nuovoStato)
            throws SQLException {

        try (PreparedStatement ps = conn.prepareStatement(Query2.AGGIORNA_STATO_RICHIESTA)) {
            ps.setString(1, nuovoStato.name());
            ps.setInt(2, idRichiesta);
            ps.executeUpdate();
        }
    }

    // --- Mapping ResultSet -> Model ---
    private static RichiestaSchedaNuotoModel mapResultSetToModel(ResultSet rs) throws SQLException {
        RichiestaSchedaNuotoModel model = new RichiestaSchedaNuotoModel();
        model.setIdRichiesta(rs.getInt("id_richiesta"));
        model.setLivelloUtente(rs.getString("livello_utente"));
        model.setEmailIstruttore(rs.getString("email_istruttore"));
        model.setEmailUser(rs.getString("email_user"));
        model.setInfo(rs.getString("info_aggiuntive"));
        model.setDataRichiesta(rs.getDate("data_richiesta").toLocalDate());

        String statoDalDB = rs.getString("stato_richiesta");
        if (statoDalDB != null) {
            try {
                model.setStatus(StatoRichiestaScheda.valueOf(statoDalDB.toUpperCase()));
            } catch (IllegalArgumentException e) {
                model.setStatus(StatoRichiestaScheda.INCORSO);
            }
        } else {
            model.setStatus(StatoRichiestaScheda.INCORSO);
        }

        return model;
    }
}