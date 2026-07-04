package query;

import exceptions.UtenteNonPresenteException;
import model.RichiestaSchedaNuotoModel;
import other.Stampa;
import other.StatoRichiestaScheda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public static ResultSet cercaRichiesteByEmailUser(Connection conn, String emailUser)
            throws SQLException, UtenteNonPresenteException {

        PreparedStatement ps = conn.prepareStatement(Query2.CERCA_RICHIESTE_USER);
        ps.setString(1, emailUser);
        ResultSet rs = ps.executeQuery();

        if (!rs.isBeforeFirst()) {
            throw new UtenteNonPresenteException();
        }

        return rs;
    }

    // --- Cerca richieste per istruttore ---
    public static ResultSet cercaRichiesteByEmailIstruttore(Connection conn, String emailIstruttore)
            throws SQLException, UtenteNonPresenteException {

        PreparedStatement ps = conn.prepareStatement(Query2.CERCA_RICHIESTE_ISTRUTTORE);
        ps.setString(1, emailIstruttore);
        ResultSet rs = ps.executeQuery();

        if (!rs.isBeforeFirst()) {
            throw new UtenteNonPresenteException();
        }

        return rs;
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

    // --- Gestione eccezioni centralizzata ---
    private static void handleException(Exception e) {
        Stampa.errorPrint(String.format("QueryRichiesteSchedaNuoto: %s", e.getMessage()));
    }
}