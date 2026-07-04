package cli;

import bean.UtenteLoggatoBean;
import controller.SchedaNuotoAssegnataController;
import model.SchedaNuotoAssegnataModel;
import Other.Stampa;
import pattern.AbstractState;
import pattern.StateMachineImpl;

import java.util.List;
import java.util.Scanner;

public class VisualizzaSchedeNuotoUtenteCLI extends AbstractState {

    private final UtenteLoggatoBean user;
    private final SchedaNuotoAssegnataController assegnataController;

    public VisualizzaSchedeNuotoUtenteCLI(UtenteLoggatoBean user) {
        this.user = user;
        this.assegnataController = new SchedaNuotoAssegnataController();
    }

    @Override
    public void entry(StateMachineImpl context) {
        stampaBenvenuto();
        action(context);
    }

    @Override
    public void action(StateMachineImpl context) {
        Scanner scan = new Scanner(System.in);

        Stampa.printlnBlu("----- LE TUE SCHEDE NUOTO -----");

        List<SchedaNuotoAssegnataModel> schede = assegnataController.getSchedeByEmailUser(
                user.getCredenziali().getEmail()
        );

        if (schede == null || schede.isEmpty()) {
            Stampa.println("Nessuna scheda assegnata al momento.");
        } else {
            for (SchedaNuotoAssegnataModel s : schede) {
                Stampa.println("-------------------------------");
                Stampa.println("ID Scheda: " + s.getIdScheda());
                Stampa.println("Durata: " + s.getDurata() + " minuti");
                Stampa.println("Distanza totale: " + s.getDistanzaTotale() + " metri");
            }
        }

        Stampa.println("\nPremi INVIO per tornare al menu...");
        scan.nextLine();
        goBack(context);
    }

    @Override
    public void stampaBenvenuto() {
        Stampa.println("👀 Le tue schede nuoto assegnate");
    }
}