package cli;

import bean.UtenteLoggatoBean;
import other.Stampa;
import pattern.AbstractState;
import pattern.StateMachineImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SchedeIstruttoreMenuCLI extends AbstractState {

    protected UtenteLoggatoBean user;

    public SchedeIstruttoreMenuCLI(UtenteLoggatoBean user){
        this.user = user;
    }

    @Override
    public void entry(StateMachineImpl cli){
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
                        goNext(context, new IstructorCLI(user)); // torna al menù principale
                        return;
                    case 1:
                        goNext(context, new VisualizzaSchedeNuotoIstruttoreCLI(user));
                        return;
                    case 2:
                        goNext(context, new VisualizzaRichiesteSchedaNuotoIstruttoreCLI(user));
                        return;
                    case 3:
                        goNext(context, new CreaSchedaNuotoCLI(user));
                        return;
                    case 4:
                        goNext(context, new AssegnaSchedaNuotoCLI(user));
                        return;
                    case 5:
                        goNext(context, new RimuoviSchedaAssegnataCLI(user));
                        return;
                    default:
                        Stampa.errorPrint("Input invalido. Scegliere un'opzione valida.");
                        mostraSchermata();
                        break;
                }

            } catch (InputMismatchException e){
                Stampa.errorPrint("Input non valido. Inserisci un numero intero.");
                scan.nextLine();
                mostraSchermata();
            }
        }
    }

    @Override
    public void mostraSchermata(){
        Stampa.println("   1. Visualizza Schede Nuoto");
        Stampa.println("   2. Visualizza Richieste Schede Nuoto");
        Stampa.println("   3. Crea Scheda Nuoto");
        Stampa.println("   4. Assegna Scheda");
        Stampa.println("   5. Rimuovi Scheda Assegnata");
        Stampa.println("   0. Torna al Menù Principale");
        Stampa.print("Opzione scelta: ");
    }
}
