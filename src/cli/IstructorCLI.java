package cli;

import bean.UtenteLoggatoBean;
import other.Stampa;
import pattern.AbstractState;
import pattern.InitialState;
import pattern.StateMachineImpl;
import java.util.Scanner;

public class IstructorCLI extends AbstractState {

    protected UtenteLoggatoBean user;

    public IstructorCLI(UtenteLoggatoBean user) {
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

        while(true){
            // ✅ Usa il metodo sicuro di Stampa passando il riferimento a mostraSchermata
            int choice = Stampa.leggiInteroSicuro(scan, this::mostraSchermata);

            if (choice == -1) {
                continue; // Input errato, ripeti il ciclo (l'errore è già gestito da Stampa)
            }

            // ✅ Switch expression di Java 21 senza 'break' ridondanti
            switch(choice){
                case 0 -> {
                    goNext(context, new InitialState());
                    return;
                }
                case 1 -> {
                    goNext(context, new SchedeIstruttoreMenuCLI(user));
                    return;
                }
                default -> {
                    Stampa.errorPrint("Input invalido. Scegliere un'opzione valida.");
                    mostraSchermata();
                }
            }
        }
    }

    @Override
    public void mostraSchermata(){
        Stampa.println("   1. Gestione Schede");
        Stampa.println("   0. Logout");
        Stampa.print("Opzione scelta: ");
    }

    @Override
    public void stampaBenvenuto(){
        Stampa.println(" ");
        Stampa.printlnBlu("-------------- HOME ISTRUTTORE - MySwimCoach --------------");
        Stampa.println("Bentornato Coach " + this.user.getNome() + "!");
    }
}