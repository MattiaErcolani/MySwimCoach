package cli;

import bean.UtenteLoggatoBean;
import controller.SchedaNuotoController;
import model.EsercizioModel;
import model.SchedaNuotoModel;
import other.Stampa;
import pattern.AbstractState;
import pattern.StateMachineImpl;

import java.util.Scanner;

public class CreaSchedaNuotoCLI extends AbstractState {

    private final UtenteLoggatoBean istruttore;
    private final SchedaNuotoController controller;

    public CreaSchedaNuotoCLI(UtenteLoggatoBean istruttore) {
        this.istruttore = istruttore;
        this.controller = new SchedaNuotoController();
    }

    @Override
    public void entry(StateMachineImpl context) {
        stampaBenvenuto();
        action(context);
    }

    @Override
    public void action(StateMachineImpl context) {
        Scanner scan = new Scanner(System.in);

        Stampa.printlnBlu("----- CREAZIONE SCHEDA NUOTO -----");

        // --- Dati base scheda ---
        Stampa.print("ID Scheda: ");
        String idScheda = scan.nextLine();

        Stampa.print("Livello (BASE / INTERMEDIO / AVANZATO): ");
        String livello = scan.nextLine();

        Stampa.print("Durata (minuti): ");
        int durata = scan.nextInt();

        Stampa.print("Distanza totale (metri): ");
        int distanza = scan.nextInt();
        scan.nextLine(); // pulizia buffer

        // --- Creazione MODEL scheda ---
        SchedaNuotoModel scheda = new SchedaNuotoModel(
                idScheda,
                distanza,
                durata,
                livello
        );
        scheda.setEmailIstruttore(istruttore.getCredenziali().getEmail());

        // --- Salvataggio scheda nel database PRIMA degli esercizi ---
        controller.insertScheda(scheda);

        // --- Aggiunta esercizi ---
        Stampa.println("✅ Ora inserisci gli esercizi della scheda.");
        boolean continua = true;
        while (continua) {
            Stampa.print("Nome esercizio: ");
            String nome = scan.nextLine();

            Stampa.print("Stile: ");
            String stile = scan.nextLine();

            Stampa.print("Distanza (metri): ");
            int distEsercizio = scan.nextInt();
            scan.nextLine(); // pulizia buffer

            Stampa.print("Info aggiuntive: ");
            String info = scan.nextLine();

            // Creazione e salvataggio esercizio
            EsercizioModel esercizio = new EsercizioModel(nome, stile, distEsercizio, info);
            scheda.getEsercizi().add(esercizio);
            controller.insertEsercizio(idScheda, esercizio);

            Stampa.print("Vuoi inserire un altro esercizio? (S/N): ");
            String risposta = scan.nextLine();
            if (!risposta.equalsIgnoreCase("S")) {
                continua = false;
            }
        }

        Stampa.println("✅ Scheda nuoto creata con successo!");

        // --- Fine e ritorno al menu istruttore ---
        Stampa.println("\nPremi INVIO per tornare al menu...");
        scan.nextLine();

        goNext(context, new IstructorCLI(istruttore));
    }

    @Override
    public void stampaBenvenuto() {
        Stampa.println("Istruttore: " + istruttore.getNome());
    }
}