package other;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final String CONFIG_FILE = "resources/resources/config.properties";
    private static final Properties properties = new Properties();

    private static String persistenceType;

    public static void loadFromFile() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            props.load(fis);
            persistenceType = props.getProperty("persistence.type", "mysql");
        } catch (IOException e) {
            System.err.println("Errore lettura config.properties, uso default mysql");
        }
    }

    public static String getPersistenceType() {
        return persistenceType;
    }

    public static void setPersistenceType(String type) {
        persistenceType = type;
    }
}