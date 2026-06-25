package CLI;

import bean.UtenteLoggatoBean;
import Other.Stampa;
import Pattern.AbstractState;
import Pattern.InitialState;
import Pattern.StateMachineImpl;

import java.util.InputMismatchException;
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
        int choice;

        while(true){
            try{
                choice = scan.nextInt();

                switch(choice){
                    case 0:
                        goNext(context, new InitialState()); // Logout
                        return;
                    case 1:
                        goNext(context, new SchedeIstruttoreMenuCLI(user)); // CLI intermedia per le schede
                        return;
                    default:
                        Stampa.errorPrint("Input invalido. Scegliere un'opzione valida.");
                        mostraSchermata();
                        break;
                }

            } catch (InputMismatchException e){
                Stampa.errorPrint("Input non valido. Inserisci un numero intero.");
                scan.nextLine(); // pulisce il buffer
                mostraSchermata();
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
