package dao;

import exceptions.UtenteNonPresenteException;
import model.RichiestaSchedaNuotoModel;
import other.Connect;
import other.Stampa;
import other.StatoRichiestaScheda;
import query.QueryRichiesteSchedaNuoto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RichiestaSchedaNuotoDaoMYSQL implements RichiestaSchedaNuotoDao {

    private static final Logger logger = Logger.getLogger(RichiestaSchedaNuotoDaoMYSQL.class.getName());

    @Override
    public void insertRichiesta(RichiestaSchedaNuotoModel richiesta) throws SQLException {
        try {
            Connection connection = Connect.getInstance().getDBConnection();
            QueryRichiesteSchedaNuoto.inserisciRichiesta(connection, richiesta);
        } catch (SQLException e) {
            logger.severe("RichiestaSchedaNuotoDAO: " + e.getMessage());
        }
    }

    @Override
    public List<RichiestaSchedaNuotoModel> getRichiesteByEmailUser(String emailUser)
            throws SQLException, UtenteNonPresenteException {

        List<RichiestaSchedaNuotoModel> lista = new ArrayList<>();
        Connection connection = Connect.getInstance().getDBConnection();

        try (ResultSet rs = QueryRichiesteSchedaNuoto.cercaRichiesteByEmailUser(connection, emailUser)) {
            while (rs.next()) {
                lista.add(mapResultSetToModel(rs));
            }
        }

        return lista;
    }

    @Override
    public List<RichiestaSchedaNuotoModel> getRichiesteByEmailIstruttore(String emailIstruttore)
            throws SQLException, UtenteNonPresenteException {

        List<RichiestaSchedaNuotoModel> lista = new ArrayList<>();
        Connection connection = Connect.getInstance().getDBConnection();

        try (ResultSet rs = QueryRichiesteSchedaNuoto.cercaRichiesteByEmailIstruttore(connection, emailIstruttore)) {
            while (rs.next()) {
                lista.add(mapResultSetToModel(rs));
            }
        }

        return lista;
    }

    @Override
    public boolean deleteRichiesta(int idRichiesta, String emailUser)
            throws SQLException, UtenteNonPresenteException {

        Connection connection = Connect.getInstance().getDBConnection();
        boolean cancellata = false;

        try {
            QueryRichiesteSchedaNuoto.cancellaRichiestaSchedaNuoto(connection, idRichiesta, emailUser);
            cancellata = true;
        } catch (UtenteNonPresenteException e) {
            Stampa.println("❌ Utente non presente: " + emailUser);
        } catch (SQLException e) {
            logger.severe("RichiestaSchedaNuotoDAO: " + e.getMessage());
        }

        return cancellata;
    }

    @Override
    public void updateStato(int idRichiesta, StatoRichiestaScheda nuovoStato) throws SQLException {
        Connection connection = Connect.getInstance().getDBConnection();
        QueryRichiesteSchedaNuoto.aggiornaStatoRichiesta(connection, idRichiesta, nuovoStato);
    }

    private RichiestaSchedaNuotoModel mapResultSetToModel(ResultSet rs) throws SQLException {
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