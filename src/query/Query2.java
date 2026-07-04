package query;

public class Query2 {
    private Query2(){
        // Costruttore privato per evitare istanziazione
    }

    public static final String INSERISCI_RICHIESTA_SCHEDA =
            "INSERT INTO richieste_scheda (id_richiesta, email_user, email_istruttore, livello_utente, info_aggiuntive, stato_richiesta, data_richiesta) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String CERCA_RICHIESTE_USER =
            "SELECT * FROM richieste_scheda WHERE email_user = ?";

    public static final String CERCA_RICHIESTE_ISTRUTTORE =
            "SELECT * FROM richieste_scheda WHERE email_istruttore = ?";

    public static final String CANCELLA_RICHIESTA_SCHEDA =
            "DELETE FROM richieste_scheda WHERE id_richiesta = ? AND email_user = ?";

    public static final String AGGIORNA_STATO_RICHIESTA =
            "UPDATE richieste_scheda SET stato_richiesta = ? WHERE id_richiesta = ?";

    // Inserisce una nuova scheda
    public static final String INSERISCI_SCHEDA =
            "INSERT INTO schede_nuoto (id_scheda, distanza_totale, durata, livello, id_richiesta, email_istruttore) VALUES (?, ?, ?, ?, ?, ?)";


    // Recupera tutte le schede
    public static final String CERCA_TUTTE_LE_SCHEDE =
            "SELECT * FROM schede_nuoto";

    // Cerca scheda tramite ID
    public static final String CERCA_SCHEDA_BY_ID =
            "SELECT * FROM schede_nuoto WHERE id_scheda = ?";

    // Aggiorna una scheda
    public static final String AGGIORNA_SCHEDA =
            "UPDATE schede_nuoto SET distanza_totale = ?, durata = ?, livello = ? WHERE id_scheda = ?";

    // Cancella una scheda
    public static final String CANCELLA_SCHEDA =
            "DELETE FROM schede_nuoto WHERE id_scheda = ?";

    // Recupera tutti gli esercizi di una scheda
    public static final String CERCA_ESERCIZI_BY_SCHEDA =
            "SELECT * FROM esercizi_scheda WHERE id_scheda = ?";

    // Elimina un esercizio specifico
    public static final String CANCELLA_ESERCIZIO =
            "DELETE FROM esercizi_scheda WHERE id_scheda = ? AND nome = ? AND stile = ? AND distanza = ? AND info = ?";

    // Inserisce un esercizio nella scheda
    public static final String INSERISCI_ESERCIZIO =
            "INSERT INTO esercizi_scheda (id_scheda, nome, stile, distanza, info) VALUES (?, ?, ?, ?, ?)";


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

}
