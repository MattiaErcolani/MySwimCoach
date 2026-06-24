package CLI;

import Bean.UtenteLoggatoBean;
import Controller.SchedaNuotoAssegnataController;
import Model.SchedaNuotoAssegnataModel;
import Other.Stampa;
import Pattern.AbstractState;
import Pattern.StateMachineImpl;

import java.util.List;
import java.util.Scanner;

public class RimuoviSchedaAssegnataCLI extends AbstractState {

    private final UtenteLoggatoBean istruttore;
    private final SchedaNuotoAssegnataController controller;

    public RimuoviSchedaAssegnataCLI(UtenteLoggatoBean istruttore) {
        this.istruttore = istruttore;
        this.controller = new SchedaNuotoAssegnataController();
    }

    @Override
    public void entry(StateMachineImpl context) {
        stampaBenvenuto();
        action(context);
    }

    @Override
    public void action(StateMachineImpl context) {
        Scanner scan = new Scanner(System.in);

        Stampa.printlnBlu("----- RIMUOVI ASSEGNAZIONE SCHEDA NUOTO -----");

        List<SchedaNuotoAssegnataModel> assegnate =
                controller.getAllSchedeAssegnate();

        if (assegnate.isEmpty()) {
            Stampa.println("❌ Nessuna scheda assegnata trovata.");
            goNext(context, new IstructorCLI(istruttore));
            return;
        }

        for (int i = 0; i < assegnate.size(); i++) {
            SchedaNuotoAssegnataModel s = assegnate.get(i);
            Stampa.println(
                    (i + 1) + ". ID Scheda: " + s.getIdScheda() +
                            " → Utente: " + s.getEmailUser()
            );
        }

        Stampa.print("Seleziona il numero dell'assegnazione da rimuovere: ");
        int scelta = scan.nextInt();
        scan.nextLine(); // pulizia buffer

        if (scelta < 1 || scelta > assegnate.size()) {
            Stampa.errorPrint("Scelta non valida!");
            goNext(context, new IstructorCLI(istruttore));
            return;
        }

        SchedaNuotoAssegnataModel selezionata =
                assegnate.get(scelta - 1);

        controller.rimuoviAssegnazione(
                selezionata.getIdScheda(),
                selezionata.getEmailUser()
        );

        Stampa.println("✅ Assegnazione rimossa con successo!");

        Stampa.println("\nPremi INVIO per tornare al menu...");
        scan.nextLine();

        goNext(context, new IstructorCLI(istruttore));
    }

    @Override
    public void stampaBenvenuto() {
        Stampa.printlnBlu("👨‍🏫 Istruttore: " + istruttore.getNome());
    }
}
