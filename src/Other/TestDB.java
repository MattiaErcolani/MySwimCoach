package Other;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDB {
    public static void main(String[] args) {
        System.out.println("--- INIZIO TEST CONNESSIONE ---");

        // 1. Cerchiamo di ottenere l'istanza della classe Connect
        Connect connessionePonte = Connect.getInstance();

        // 2. Proviamo ad aprire il tunnel verso il database
        Connection conn = connessionePonte.getDBConnection();

        // 3. Verifichiamo il risultato
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    System.out.println("✅ ESITO: Successo! Java è connesso a MySQL.");
                    System.out.println("Database collegato: " + conn.getCatalog());
                }
            } catch (SQLException e) {
                System.out.println("❌ ESITO: Connessione stabilita ma chiusa o non valida.");
            }
        } else {
            System.out.println("❌ ESITO: Fallimento. La connessione è NULL.");
            System.out.println("Controlla: 1. Password in connection.properties | 2. MySQL acceso | 3. Driver JDBC aggiunto");
        }

        System.out.println("--- FINE TEST ---");
    }
}