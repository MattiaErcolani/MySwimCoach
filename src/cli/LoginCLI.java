package cli;

import bean.CredenzialiBean;
import bean.UtenteLoggatoBean;
import controller.LoginController;
import exceptions.CredenzialiSbagliateException;
import exceptions.UtenteNonPresenteException;
import Pattern.AbstractState;
import Pattern.StateMachineImpl;
import Other.Stampa;
import java.util.Scanner;

public class LoginCLI extends AbstractState {




    public void action(StateMachineImpl context) {
        Scanner scanner = new Scanner(System.in);
        LoginController logincontroller = new LoginController();

        Stampa.println("=== LOGIN UTENTE ===");

        Stampa.println("Inserisci email: ");
        String email = scanner.nextLine();

        Stampa.println("Inserisci password: ");
        String password = scanner.nextLine();


        try {
            CredenzialiBean credenzialiBean = new CredenzialiBean(email,password);


            UtenteLoggatoBean utente = logincontroller.login(credenzialiBean);


            if (utente != null) {
                Stampa.println("\nLogin effettuato con successo!");
                Stampa.println("Benvenuto/a, " + utente.getNome() + " " + utente.getCognome());
                Stampa.println("Email: " + utente.getCredenziali().getEmail());
                Stampa.println("Ruolo: " + (utente.isIstructor() ? "Istruttore" : "Utente"));

                AbstractState homeCLI;
                // Cambio stato in base al ruolo
                if (utente.isIstructor()) {
                    homeCLI = new IstructorCLI(utente);
                } else {
                    homeCLI = new UserCLI(utente);
                }

                goNext(context, homeCLI);
            }

        } catch (UtenteNonPresenteException | CredenzialiSbagliateException e) {
            Stampa.println("Errore durante il login: " + e.getMessage());
        }
    }
}