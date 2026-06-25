package cli;

import bean.RichiestaSchedaNuotoBean;
import bean.UtenteLoggatoBean;
import controller.RichiestaSchedaNuotoController;
import other.Stampa;
import pattern.AbstractState;
import pattern.StateMachineImpl;
import other.StatoRichiestaScheda;

import java.util.List;
import java.util.Scanner;

public class VisualizzaRichiesteSchedaNuotoIstruttoreCLI extends AbstractState {

    private final UtenteLoggatoBean istruttore;
    private final RichiestaSchedaNuotoController controller;

    public VisualizzaRichiesteSchedaNuotoIstruttoreCLI(UtenteLoggatoBean istruttore) {
        this.istruttore = istruttore;
        this.controller = new RichiestaSchedaNuotoController();
    }

    @Override
    public void entry(StateMachineImpl context) {
        stampaBenvenuto();
        action(context);
    }

    @Override
    public void action(StateMachineImpl context) {
        Scanner scan = new Scanner(System.in);
        try {
            List<RichiestaSchedaNuotoBean> richieste =
                    controller.getRichiesteByEmailIstruttore(
                            istruttore.getCredenziali().getEmail()
                    );

            if (richieste == null || richieste.isEmpty()) {
                Stampa.println("📭 Nessuna richiesta di scheda nuoto trovata.");
            } else {
                Stampa.println("📋 Richieste di scheda nuoto ricevute:\n");

                for (int i = 0; i < richieste.size(); i++) {
                    RichiestaSchedaNuotoBean r = richieste.get(i);
                    Stampa.println((i + 1) + ". ----------------------------------");
                    Stampa.println("📌 ID Richiesta: " + r.getIdRichiesta());
                    Stampa.println("📧 Email Utente: " + r.getEmailUser());
                    Stampa.println("🏊 Livello: " + r.getLivelloUtente());
                    Stampa.println("📅 Data: " + r.getDataRichiesta());
                    Stampa.println("📄 Info: " + r.getInfo());
                    Stampa.println("⏳ Stato: " + r.getStatus());
                }

                Stampa.print("\nSeleziona il numero della richiesta da gestire (0 per tornare): ");
                int scelta = Integer.parseInt(scan.nextLine()) - 1;

                if (scelta >= 0 && scelta < richieste.size()) {
                    RichiestaSchedaNuotoBean selezionata = richieste.get(scelta);
                    Stampa.println("1. Accetta");
                    Stampa.println("2. Rifiuta");
                    Stampa.print("Scelta: ");
                    int azione = Integer.parseInt(scan.nextLine());

                    if (azione == 1) {
                        controller.aggiornaStatoRichiesta(selezionata.getIdRichiesta(), StatoRichiestaScheda.ACCETTATA);
                        Stampa.println("✅ Richiesta accettata!");
                    } else if (azione == 2) {
                        controller.aggiornaStatoRichiesta(selezionata.getIdRichiesta(), StatoRichiestaScheda.RIFIUTATA);
                        Stampa.println("❌ Richiesta rifiutata.");
                    } else {
                        Stampa.println("Operazione annullata.");
                    }
                }
            }

        } catch (Exception e) {
            Stampa.errorPrint("❌ Errore durante la visualizzazione delle richieste.");
        }

        Stampa.println("\nPremi INVIO per tornare indietro...");
        scan.nextLine();
        goBack(context);
    }
}
