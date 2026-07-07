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

        while(true) {
            // ✅ Sfrutta il metodo sicuro di Stampa passando il riferimento a mostraSchermata
            int choice = Stampa.leggiInteroSicuro(scan, this::mostraSchermata);

            if (choice == -1) {
                continue; // Input errato, ripeti il ciclo (l'errore è già gestito da Stampa)
            }

            // ✅ Switch expression di Java 21 coerente con i tuoi case (0 e 3)
            switch(choice){
                case 0 -> {
                    goNext(context, new InitialState());
                    return;
                }
                case 3 -> {
                    goNext(context, new SchedeUtenteMenuCLI(user));
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