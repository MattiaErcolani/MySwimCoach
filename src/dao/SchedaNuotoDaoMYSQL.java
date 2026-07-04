package dao;

import model.EsercizioModel;
import model.SchedaNuotoModel;
import Other.Connect;
import Other.Stampa;
import query.QuerySchedaNuoto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SchedaNuotoDaoMYSQL implements SchedaNuotoDao {

    private static final Logger logger = Logger.getLogger(SchedaNuotoDaoMYSQL.class.getName());

    @Override
    public void insertScheda(SchedaNuotoModel scheda) {
        Connection connection = Connect.getInstance().getDBConnection();
        QuerySchedaNuoto.inserisciScheda(connection, scheda);
    }

    @Override
    public void updateScheda(SchedaNuotoModel scheda) {
        try {
            Connection connection = Connect.getInstance().getDBConnection();
            QuerySchedaNuoto.aggiornaScheda(connection, scheda);
        } catch (SQLException e) {
            logger.severe("SchedaNuotoDAO: " + e.getMessage());
        }
    }

    @Override
    public void deleteScheda(String idScheda) {
        try {
            Connection connection = Connect.getInstance().getDBConnection();
            QuerySchedaNuoto.cancellaScheda(connection, idScheda);
        } catch (SQLException e) {
            logger.severe("SchedaNuotoDAO: " + e.getMessage());
        }
    }

    @Override
    public SchedaNuotoModel getSchedaById(String idScheda) {
        try {
            Connection connection = Connect.getInstance().getDBConnection();
            return QuerySchedaNuoto.cercaSchedaById(connection, idScheda);
        } catch (SQLException e) {
            logger.severe("SchedaNuotoDAO: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<SchedaNuotoModel> getAllSchede() {
        try {
            Connection connection = Connect.getInstance().getDBConnection();
            List<SchedaNuotoModel> schede = QuerySchedaNuoto.getAllSchede(connection);

            for (SchedaNuotoModel s : schede) {
                List<EsercizioModel> esercizi = QuerySchedaNuoto.getEserciziByScheda(connection, s.getIdScheda());
                s.setEsercizi(esercizi);
            }

            return schede;
        } catch (SQLException e) {
            logger.severe("SchedaNuotoDAO: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public void insertEsercizio(String idScheda, EsercizioModel esercizio) {
        Connection connection = Connect.getInstance().getDBConnection();
        QuerySchedaNuoto.inserisciEsercizio(connection, idScheda, esercizio);
    }

    @Override
    public void deleteEsercizio(String idScheda, EsercizioModel esercizio) {
        try {
            Connection connection = Connect.getInstance().getDBConnection();
            QuerySchedaNuoto.cancellaEsercizio(connection, idScheda, esercizio);
        } catch (SQLException e) {
            logger.severe("SchedaNuotoDAO: " + e.getMessage());
        }
    }

    private void handleDAOException(Exception e) {
        Stampa.errorPrint(String.format("SchedaNuotoDAO: %s", e.getMessage()));
    }
}