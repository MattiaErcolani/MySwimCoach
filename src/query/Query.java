package query;

public class Query {

    private Query() {
        // Costruttore privato per evitare istanziazione
    }

    // Query per cercare se un'email esiste (usata sia nel login che nella registrazione)
    public static final String RICERCA_EMAIL =
            "SELECT email FROM utenti WHERE email = '%s'";

    // Query per verificare email e password per il login
    public static final String VERIFICA_USER =
            "SELECT * FROM utenti WHERE email = '%s' AND password = '%s'";

    public static final String REGISTRAZIONE =
            "INSERT INTO utenti (email, password, nome, cognome, ruolo, age, level, certificate) VALUES ('%s', '%s', '%s', '%s', %d, %d, '%s', '%s')";
}
