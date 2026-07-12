package test;

import bean.RichiestaSchedaNuotoBean;
import controller.RichiestaSchedaNuotoController;
import other.Stampa;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import other.StatoRichiestaScheda;

public class TestRichiestaScheda {

    private static final RichiestaSchedaNuotoController controller = new RichiestaSchedaNuotoController();
    private static final String EMAIL_UTENTE = "test@mail.it";
    private static final String EMAIL_ISTRUTTORE = "mattiaercolani@mail.it";

    public static void main(String[] args) {
        Stampa.println("--- INIZIO TEST RICHIESTA SCHEDA ---");
        testInserisciRichiesta();
        testGetRichiesteByEmailUser();
        testAggiornaStatoRichiesta();
        Stampa.println("--- FINE TEST ---");
    }

    private static void testInserisciRichiesta() {
        Stampa.println("\n[TEST 1] Inserimento richiesta scheda:");
        try {
            RichiestaSchedaNuotoBean bean = creaBean();
            controller.inserisciRichiesta(bean);
            Stampa.println("✅ ESITO: Successo! Richiesta inserita correttamente.");
        } catch (Exception e) {
            Stampa.println("❌ ESITO: Fallimento. Eccezione: " + e.getMessage());
        }
    }

    private static void testGetRichiesteByEmailUser() {
        Stampa.println("\n[TEST 2] Recupero richieste per email utente:");
        try {
            List<RichiestaSchedaNuotoBean> richieste = controller.getRichiesteByEmailUser(EMAIL_UTENTE);
            if (richieste != null && !richieste.isEmpty()) {
                Stampa.println("✅ ESITO: Successo! Trovate " + richieste.size() + " richieste.");
            } else {
                Stampa.println("⚠️ ESITO: Nessuna richiesta trovata per l'utente.");
            }
        } catch (Exception e) {
            Stampa.println("❌ ESITO: Fallimento. Eccezione: " + e.getMessage());
        }
    }

    private static void testAggiornaStatoRichiesta() {
        Stampa.println("\n[TEST 3] Aggiornamento stato richiesta:");
        try {
            List<RichiestaSchedaNuotoBean> richieste = controller.getRichiesteByEmailUser(EMAIL_UTENTE);
            if (richieste != null && !richieste.isEmpty()) {
                int idRichiesta = richieste.getFirst().getIdRichiesta();
                controller.aggiornaStatoRichiesta(idRichiesta, StatoRichiestaScheda.ACCETTATA);
                Stampa.println("✅ ESITO: Successo! Stato aggiornato ad ACCETTATA per richiesta ID: " + idRichiesta);
            } else {
                Stampa.println("⚠️ ESITO: Nessuna richiesta disponibile per aggiornare lo stato.");
            }
        } catch (Exception e) {
            Stampa.println("❌ ESITO: Fallimento. Eccezione: " + e.getMessage());
        }
    }

    private static RichiestaSchedaNuotoBean creaBean() {
        RichiestaSchedaNuotoBean bean = new RichiestaSchedaNuotoBean();
        bean.setEmailUser(EMAIL_UTENTE);
        bean.setEmailIstruttore(EMAIL_ISTRUTTORE);
        bean.setLivelloUtente("BASE");
        bean.setInfo("Test richiesta scheda");
        bean.setDataRichiesta(LocalDate.now(ZoneId.systemDefault()));
        bean.setStatus(StatoRichiestaScheda.INCORSO);
        return bean;
    }
}
