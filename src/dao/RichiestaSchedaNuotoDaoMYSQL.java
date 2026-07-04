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

        try {
            Connection connection = Connect.getInstance().getDBConnection();
            return QueryRichiesteSchedaNuoto.cercaRichiesteByEmailUser(connection, emailUser);
        } catch (UtenteNonPresenteException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<RichiestaSchedaNuotoModel> getRichiesteByEmailIstruttore(String emailIstruttore)
            throws SQLException, UtenteNonPresenteException {

        try {
            Connection connection = Connect.getInstance().getDBConnection();
            return QueryRichiesteSchedaNuoto.cercaRichiesteByEmailIstruttore(connection, emailIstruttore);
        } catch (UtenteNonPresenteException e) {
            return new ArrayList<>();
        }
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
}