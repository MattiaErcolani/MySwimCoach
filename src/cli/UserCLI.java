package cli;

import bean.UtenteLoggatoBean;
import Other.Stampa;
import pattern.AbstractState;
import pattern.InitialState;
import pattern.StateMachineImpl;

import java.util.InputMismatchException;
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
        int choice;

        while(true) {
            try{
                choice = scan.nextInt();

                switch(choice){
                    case 0:
                        goNext(context, new InitialState()); // Logout
                        return;
                    case 3:
                        goNext(context, new SchedeUtenteMenuCLI(user)); // nuovo menu schede
                        return;
                    default:
                        Stampa.errorPrint("Input invalido. Scegliere un'opzione tra quelle disponibili: ");
                        mostraSchermata();
                        break;
                }

            } catch (InputMismatchException e){
                Stampa.errorPrint("Input non valido. Per favore, inserisci un numero intero: ");
                scan.nextLine(); // Pulisce il buffer
                mostraSchermata();
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