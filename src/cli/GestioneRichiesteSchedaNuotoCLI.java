package cli;

import bean.RichiestaSchedaNuotoBean;
import bean.UtenteLoggatoBean;
import controller.RichiestaSchedaNuotoController;
import other.Stampa;
import other.StatoRichiestaScheda;
import pattern.AbstractState;
import pattern.StateMachineImpl;

import java.util.List;
import java.util.Scanner;

public class GestioneRichiesteSchedaNuotoCLI extends AbstractState {

    private final UtenteLoggatoBean istruttore;
    private final RichiestaSchedaNuotoController controller;

    public GestioneRichiesteSchedaNuotoCLI(UtenteLoggatoBean istruttore) {
        this.istruttore = istruttore;
        this.controller = new RichiestaSchedaNuotoController();
    }

    @Override
    public void action(StateMachineImpl context) {
        Scanner scan = new Scanner(System.in);

        // Recupero richieste IN_CORSO per questo istruttore
        List<RichiestaSchedaNuotoBean> lista;
        try {
            lista = controller.getRichiesteByEmailIstruttore(istruttore.getCredenziali().getEmail());
        } catch (Exception e) {
            Stampa.errorPrint("Errore nel recupero richieste: " + e.getMessage());
            return;
        }

        if (lista == null || lista.isEmpty()) {
            Stampa.println("Non ci sono richieste in attesa di approvazione.");
            return;
        }

        // Stampa elenco richieste
        Stampa.println("Seleziona una richiesta da gestire:");
        for (int i = 0; i < lista.size(); i++) {
            RichiestaSchedaNuotoBean r = lista.get(i);
            Stampa.println((i + 1) + ") Utente: " + r.getEmailUser() + " | Info: " + r.getInfo() + " | Data: " + r.getDataRichiesta());
        }

        Stampa.print("\nScelta (0 per tornare indietro): ");
        int scelta;
        try {
            scelta = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            Stampa.errorPrint("Inserisci un numero valido.");
            return;
        }

        if (scelta == 0) return;

        if (scelta > 0 && scelta <= lista.size()) {
            RichiestaSchedaNuotoBean selezionata = lista.get(scelta - 1);
            gestisciSingolaRichiesta(selezionata);
        } else {
            Stampa.errorPrint("Scelta fuori intervallo.");
        }
    }

    private void gestisciSingolaRichiesta(RichiestaSchedaNuotoBean r) {
        Scanner scan = new Scanner(System.in);

        Stampa.println("\n--- Gestione Richiesta ID: " + r.getIdRichiesta() + " ---");
        Stampa.println("Dettagli: " + r.getInfo());
        Stampa.println("1. Accetta");
        Stampa.println("2. Rifiuta");
        Stampa.print("Inserisci azione: ");

        int azione;
        try {
            azione = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            Stampa.errorPrint("Azione non valida.");
            return;
        }

        try {
            if (azione == 1) {
                controller.aggiornaStatoRichiesta(r.getIdRichiesta(), StatoRichiestaScheda.ACCETTATA);
                r.setStatus(StatoRichiestaScheda.ACCETTATA);
                Stampa.println("✅ Richiesta accettata!");
            } else if (azione == 2) {
                controller.aggiornaStatoRichiesta(r.getIdRichiesta(), StatoRichiestaScheda.RIFIUTATA);
                r.setStatus(StatoRichiestaScheda.RIFIUTATA);
                Stampa.println("❌ Richiesta rifiutata.");
            } else {
                Stampa.println("Operazione annullata.");
            }
        } catch (Exception e) {
            Stampa.errorPrint("Errore nell'aggiornamento dello stato: " + e.getMessage());
        }
    }

    @Override
    public void entry(StateMachineImpl context) {
        stampaBenvenuto();
        action(context);
    }

    @Override
    public void mostraSchermata() {
        Stampa.printlnBlu("------ GESTIONE RICHIESTE SCHEDA NUOTO ------");
    }

    @Override
    public void stampaBenvenuto() {
        Stampa.println("Istruttore: " + istruttore.getNome());
    }
}
