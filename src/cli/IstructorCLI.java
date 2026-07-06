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

        while (true) {
            // ✅ RIUTILIZZA IL METODO SICURO CENTRALIZZATO IN STAMPA (Zero duplicazione!)
            int choice = Stampa.leggiInteroSicuro(scan, this::mostraSchermata);

            if (choice == -1) {
                continue; // L'utente ha sbagliato input, il metodo ha già stampato l'errore, ripeti il ciclo
            }

            // ✅ SWITCH EXPRESSION DI JAVA 21 (Compatto e senza bug di 'fall-through')
            switch (choice) {
                case 0 -> {
                    goNext(context, new InitialState()); // Logout
                    return;
                }
                case 1 -> {
                    goNext(context, new SchedeIstruttoreMenuCLI(user)); // CLI intermedia per le schede
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