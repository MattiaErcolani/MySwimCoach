package test;
import other.Stampa;
import other.Connect;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDB {

    public static void main(String[] args) {
        Stampa.println("--- INIZIO TEST CONNESSIONE ---");

        Connect connessionePonte = Connect.getInstance();
        Connection conn = connessionePonte.getDBConnection();

        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    Stampa.println("✅ ESITO: Successo! Java è connesso a MySQL.");
                    Stampa.println("Database collegato: " + conn.getCatalog());
                }
            } catch (SQLException e) {
                Stampa.println("❌ ESITO: Connessione stabilita ma chiusa o non valida.");
            }
        } else {
            Stampa.println("❌ ESITO: Fallimento. La connessione è NULL.");
            Stampa.println("Controlla: 1. Password in connection.properties | 2. MySQL acceso | 3. Driver JDBC aggiunto");
        }

        Stampa.println("--- FINE TEST ---");
    }
}