package cli;

import bean.CredenzialiBean;
import bean.UtenteLoggatoBean;
import controller.RegistrazioneController;
import Exceptions.EmailGiaInUsoException;
import Exceptions.EmailNonValidaException;
import Pattern.AbstractState;
import Pattern.StateMachineImpl;
import Other.Stampa;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RegistrazioneCLI extends AbstractState {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    public void action(StateMachineImpl context) {
        Scanner scanner = new Scanner(System.in);
        Stampa.println("--- REGISTRAZIONE ---");

        Stampa.println("Inserisci il tuo nome: ");
        String nome = scanner.nextLine();

        Stampa.println("Inserisci il tuo cognome: ");
        String cognome = scanner.nextLine();

        Stampa.println("Inserisci la tua email: ");
        String email = scanner.nextLine();
        try {
            isValidEmail(email);
        } catch (EmailNonValidaException e) {
            Stampa.errorPrint("Email non valida");
            return;
        }

        Stampa.println("Inserisci la tua password: ");
        String password = scanner.nextLine();

        Stampa.println("Sei un istruttore? (s/n): ");
        boolean isIstructor = scanner.nextLine().equalsIgnoreCase("s");

        Stampa.println("Inserisci la tua età: ");
        int age = Integer.parseInt(scanner.nextLine());

        String level = "";
        String certificate = "";

        if (isIstructor) {
            Stampa.println("Inserisci il tuo certificato: ");
            certificate = scanner.nextLine();
        } else {
            Stampa.println("Inserisci il tuo livello (BASE / INTERMEDIO / AVANZATO): ");
            level = scanner.nextLine();
        }

        CredenzialiBean credenziali = new CredenzialiBean(email, password);
        UtenteLoggatoBean utente = new UtenteLoggatoBean(credenziali, nome, cognome, isIstructor);
        utente.setRuolo(isIstructor);

        RegistrazioneController registrazionecontroller = new RegistrazioneController();
        try {
            registrazionecontroller.registrazione(utente, age, level, certificate);
        } catch (EmailGiaInUsoException e) {
            Stampa.errorPrint("❌ Email già in uso.");
            return;
        }

        context.setUtenteloggatobean(utente);
        Stampa.println("✅ Registrazione completata!");

        if (isIstructor) {
            goNext(context, new IstructorCLI(utente));
        } else {
            goNext(context, new UserCLI(utente));
        }
    }

    private boolean isValidEmail(String email) throws EmailNonValidaException {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    @Override
    public void entry(StateMachineImpl context) {
        stampaBenvenuto();
        action(context);
    }

    @Override
    public void exit(StateMachineImpl context) {
        Stampa.println("riportato alla home");
    }

    @Override
    public void stampaBenvenuto() {
        Stampa.println("Benvenuto inserisci le credenziali per registrarti");
    }

    @Override
    public void mostraSchermata() {
        Stampa.println("1. Conferma registrazione");
        Stampa.println("2. Torna indietro");
        Stampa.println("0. Esci");
        Stampa.print("Opzione scelta: ");
    }
}
