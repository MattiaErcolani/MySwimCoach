package query;

public class Query2 {
    private Query2(){
        // Costruttore privato per evitare istanziazione
    }

    public static final String INSERISCI_RICHIESTA_SCHEDA =
            "INSERT INTO richieste_scheda (id_richiesta, email_user, email_istruttore, livello_utente, info_aggiuntive, stato_richiesta, data_richiesta) " +
                    "VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s')";

    public static final String CERCA_RICHIESTE_USER =
            "SELECT * FROM richieste_scheda WHERE email_user = '%s'";

    public static final String CERCA_RICHIESTE_ISTRUTTORE =
            "SELECT * FROM richieste_scheda WHERE email_istruttore = '%s'";

    public static final String CANCELLA_RICHIESTA_SCHEDA =
            "DELETE FROM richieste_scheda WHERE id_richiesta = %d AND email_user = '%s'";

    public static final String AGGIORNA_STATO_RICHIESTA =
            "UPDATE richieste_scheda SET stato_richiesta = '%s' WHERE id_richiesta = %d";

    // Inserisce una nuova scheda
    public static final String INSERISCI_SCHEDA =
            "INSERT INTO schede_nuoto (id_scheda, distanza_totale, durata, livello, id_richiesta, email_istruttore) VALUES ('%s', %d, %d, '%s', %d, '%s')";

    // Cerca scheda tramite ID
    public static final String CERCA_SCHEDA_BY_ID =
            "SELECT * FROM schede_nuoto WHERE id_scheda = '%s'";

    // Recupera tutte le schede
    public static final String CERCA_TUTTE_LE_SCHEDE =
            "SELECT * FROM schede_nuoto";

    // Aggiorna una scheda
    public static final String AGGIORNA_SCHEDA =
            "UPDATE schede_nuoto SET distanza_totale = %d, durata = %d, livello = '%s' WHERE id_scheda = '%s'";

    // Cancella una scheda
    public static final String CANCELLA_SCHEDA =
            "DELETE FROM schede_nuoto WHERE id_scheda = '%s'";

    // Recupera tutti gli esercizi di una scheda
    public static final String CERCA_ESERCIZI_BY_SCHEDA =
            "SELECT * FROM esercizi_scheda WHERE id_scheda = '%s'";

    // Elimina un esercizio specifico
    public static final String CANCELLA_ESERCIZIO =
            "DELETE FROM esercizi_scheda WHERE id_scheda = '%s' AND nome = '%s' AND stile = '%s' AND distanza = %d AND info = '%s'";


    public static final String INSERISCI_ASSEGNAZIONE =
            "INSERT INTO schede_nuoto_assegnate (emailUser, emailIstruttore, idScheda, dataAssegnazione) " +
                    "VALUES ('%s', '%s', '%s', '%s')";

    public static final String CERCA_SCHEDE_USER =
            "SELECT * FROM schede_nuoto_assegnate WHERE emailUser = '%s'";

    public static final String CERCA_SCHEDE_ISTRUTTORE =
            "SELECT * FROM schede_nuoto_assegnate WHERE emailIstruttore = '%s'";

    // -------- SCHEDA NUOTO ASSEGNATA --------

    // assegna scheda a utente
    public static final String ASSEGNA_SCHEDA =
            "INSERT INTO schede_nuoto_assegnate (idScheda, emailUser, emailIstruttore) " +
                    "VALUES ('%s', '%s', '%s')";

    // cerca scheda assegnata a un utente
    public static final String CERCA_SCHEDA_ASSEGNATA_USER =
            "SELECT * FROM schede_nuoto_assegnate WHERE emailUser = '%s'";

    // tutte le schede assegnate
    public static final String CERCA_TUTTE_SCHEDE_ASSEGNATE =
            "SELECT * FROM schede_nuoto_assegnate";

    // Inserisce un esercizio nella scheda
    public static final String INSERISCI_ESERCIZIO =
            "INSERT INTO esercizi_scheda (id_scheda, nome, stile, distanza, info) VALUES ('%s', '%s', '%s', %d, '%s')";



}
