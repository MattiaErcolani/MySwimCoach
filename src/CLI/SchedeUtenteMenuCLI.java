package CLI;

import Bean.UtenteLoggatoBean;
import Other.Stampa;
import Pattern.AbstractState;
import Pattern.StateMachineImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SchedeUtenteMenuCLI extends AbstractState {

    protected UtenteLoggatoBean user;

    public SchedeUtenteMenuCLI(UtenteLoggatoBean user){
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
                        goNext(context, new UserCLI(user)); // torna al menù principale
                        return;
                    case 1:
                        goNext(context, new RichiestaSchedaNuotoCLI(user));
                        return;
                    case 2:
                        goNext(context, new VisualizzaRichiesteSchedaNuotoCLI(user));
                        return;
                    case 3:
                        goNext(context, new VisualizzaSchedeNuotoUtenteCLI(user));
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
        Stampa.println("   1. Richiesta Scheda Nuoto");
        Stampa.println("   2. Visualizza Richieste Scheda Nuoto");
        Stampa.println("   3. Visualizza Schede Nuoto");
        Stampa.println("   0. Torna al Menù Principale");
        Stampa.print("Opzione scelta: ");
    }
}
