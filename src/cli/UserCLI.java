package cli;

import bean.UtenteLoggatoBean;
import other.Stampa;
import pattern.AbstractState;
import pattern.InitialState;
import pattern.StateMachineImpl;
import java.util.Scanner;

public class UserCLI extends AbstractState {

    protected UtenteLoggatoBean user;

    public UserCLI(UtenteLoggatoBean user){
        this.user = user;
    }

    @Override
    public void entry(StateMachineImpl cli){
        stampaBenvenuto();
        mostraSchermata();
    }

    @Override
    public void action(StateMachineImpl context){
        Scanner scan = new Scanner(System.in);

        while (true) {
            // Sfrutta il metodo in Stampa passando il riferimento a mostraSchermata (Java Lambda)
            int choice = Stampa.leggiInteroSicuro(scan, this::mostraSchermata);

            if (choice == -1) {
                continue; // L'utente ha sbagliato input, il metodo ha già stampato l'errore, ripeti il ciclo
            }

            // Java 21 Switch Expression: compatto, pulito e senza "break;" ripetuti
            switch (choice) {
                case 0 -> {
                    goNext(context, new InitialState()); // Logout
                    return;
                }
                case 3 -> {
                    goNext(context, new SchedeUtenteMenuCLI(user)); // nuovo menu schede
                    return;
                }
                default -> {
                    Stampa.errorPrint("Input invalido. Scegliere un'opzione tra quelle disponibili: ");
                    mostraSchermata();
                }
            }
        }
    }

    @Override
    public void mostraSchermata(){
        Stampa.println("   3. Gestione Schede Nuoto");
        Stampa.println("   0. Logout");
        Stampa.print("Opzione scelta: ");
    }

    @Override
    public void stampaBenvenuto(){
        Stampa.println(" ");
        Stampa.printlnBlu("-------------- HOME STUDENTE - SWIMAPP --------------");
        Stampa.println("Ciao " + this.user.getNome() + ", scegli un'opzione:");
    }
}