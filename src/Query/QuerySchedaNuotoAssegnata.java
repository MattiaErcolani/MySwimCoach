package Query;

import Model.SchedaNuotoAssegnataModel;
import Other.Stampa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

public class QuerySchedaNuotoAssegnata {

    private QuerySchedaNuotoAssegnata() {}

    public static void inserisciAssegnazione(Statement stmt, SchedaNuotoAssegnataModel scheda) throws SQLException {
        String sql = String.format(Locale.US,
                "INSERT INTO scheda_nuoto_assegnata (idScheda, emailUser) VALUES ('%s', '%s')",
                scheda.getIdScheda(), scheda.getEmailUser());
        stmt.executeUpdate(sql);
    }

    public static ResultSet getAllAssegnazioni(Statement stmt) throws SQLException {
        String sql = "SELECT * FROM scheda_nuoto_assegnata";
        return stmt.executeQuery(sql);
    }

    public static ResultSet cercaSchedeByEmailUser(Statement stmt, String emailUser) throws SQLException {
        String sql = String.format("SELECT * FROM scheda_nuoto_assegnata WHERE emailUser = '%s'", emailUser);
        return stmt.executeQuery(sql);
    }

    public static void rimuoviAssegnazione(Statement stmt, String idScheda, String emailUser) throws SQLException {
        String sql = String.format("DELETE FROM scheda_nuoto_assegnata WHERE idScheda = '%s' AND emailUser = '%s'",
                idScheda, emailUser);
        stmt.executeUpdate(sql);
    }
}
