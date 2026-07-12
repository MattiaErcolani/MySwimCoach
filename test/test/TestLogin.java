package test;

import bean.CredenzialiBean;
import bean.UtenteLoggatoBean;
import controller.LoginController;
import other.Stampa;

public class TestLogin {

    private static final LoginController loginController = new LoginController();
    private static final String EMAIL_VALIDA = "mattiaercolani@mail.it";
    private static final String PASSWORD_VALIDA = "password123";
    private static final String PASSWORD_ERRATA = "passwordsbagliata";

    public static void main(String[] args) {
        Stampa.println("--- INIZIO TEST LOGIN ---");
        testLoginCredenzialiErrate();
        testLoginCorretto();
        Stampa.println("--- FINE TEST ---");
    }

    private static void testLoginCredenzialiErrate() {
        Stampa.println("\n[TEST 1] Login con credenziali errate:");
        try {
            CredenzialiBean credenziali = new CredenzialiBean(EMAIL_VALIDA, PASSWORD_ERRATA);
            UtenteLoggatoBean risultato = loginController.login(credenziali);

            if (risultato == null) {
                Stampa.println("✅ ESITO: Successo! Il sistema ha restituito null per credenziali errate.");
            } else {
                Stampa.println("❌ ESITO: Fallimento. Il sistema ha accettato credenziali errate.");
            }
        } catch (Exception e) {
            Stampa.println("✅ ESITO: Successo! Il sistema ha lanciato un'eccezione: " + e.getMessage());
        }
    }

    private static void testLoginCorretto() {
        Stampa.println("\n[TEST 2] Login con credenziali corrette:");
        try {
            CredenzialiBean credenziali = new CredenzialiBean(EMAIL_VALIDA, PASSWORD_VALIDA);
            UtenteLoggatoBean risultato = loginController.login(credenziali);

            if (risultato != null && risultato.getNome() != null) {
                Stampa.println("✅ ESITO: Successo! Utente loggato: " + risultato.getNome() + " " + risultato.getCognome());
            } else {
                Stampa.println("❌ ESITO: Fallimento. Il sistema non ha restituito un utente valido.");
            }
        } catch (Exception e) {
            Stampa.println("❌ ESITO: Fallimento. Eccezione inattesa: " + e.getMessage());
        }
    }
}