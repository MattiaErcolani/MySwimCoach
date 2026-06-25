package cli;

import bean.RichiestaSchedaNuotoBean;
import bean.UtenteLoggatoBean;
import controller.RichiestaSchedaNuotoController;
import Other.Stampa;
import Other.StatoRichiestaScheda;
import Pattern.AbstractState;
import Pattern.StateMachineImpl;
import dao.UserDao;
import Model.UtenteLoggatoModel;
import Other.FactoryDao;
import java.util.List;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

public class RichiestaSchedaNuotoCLI extends AbstractState {

    private final UtenteLoggatoBean utente;

    public RichiestaSchedaNuotoCLI(UtenteLoggatoBean utente) {
        this.utente = utente;
    }

    @Override
    public void entry(StateMachineImpl context) {
        stampaBenvenuto();
        action(context);
    }

    @Override
    public void action(StateMachineImpl context) {
        Scanner scanner = new Scanner(System.in);
        int scelta = -1;

        while (scelta != 0) {
            mostraSchermata();
            Stampa.print("Scegli un'opzione: ");
            String input = scanner.nextLine();

            try {
                scelta = Integer.parseInt(input);

                switch (scelta) {
                    case 1:
                        inserisciRichiesta(scanner);
                        break;
                    case 0:
                        goBack(context);
                        return;
                    default:
                        Stampa.println("❌ Scelta non valida.");
                }

            } catch (NumberFormatException e) {
                Stampa.println("❌ Input non valido. Inserisci un numero intero.");
            }
        }
    }

    private void inserisciRichiesta(Scanner scanner) {
        RichiestaSchedaNuotoController controller = new RichiestaSchedaNuotoController();
        RichiestaSchedaNuotoBean bean = new RichiestaSchedaNuotoBean();

        try {
            int idRandom = ThreadLocalRandom.current().nextInt(0, 1000);
            bean.setIdRichiesta(idRandom);

            UserDao userDao = FactoryDao.getUserDAO();
            List<UtenteLoggatoModel> istruttori = userDao.getIstruttori();

            if (istruttori.isEmpty()) {
                Stampa.println("❌ Nessun istruttore disponibile.");
                return;
            }

            Stampa.println("👨‍🏫 Seleziona un istruttore:");
            for (int i = 0; i < istruttori.size(); i++) {
                UtenteLoggatoModel ist = istruttori.get(i);
                Stampa.println((i + 1) + ". " + ist.getNome() + " " + ist.getCognome());
            }

            Stampa.print("Scelta: ");
            int sceltaIst = Integer.parseInt(scanner.nextLine()) - 1;

            if (sceltaIst < 0 || sceltaIst >= istruttori.size()) {
                Stampa.println("❌ Scelta non valida.");
                return;
            }

            String emailIstruttore = istruttori.get(sceltaIst).getCredenziali().getEmail();
            bean.setEmailIstruttore(emailIstruttore);

            Stampa.print("📄 Inserisci informazioni aggiuntive: ");
            String info = scanner.nextLine();
            bean.setInfo(info);

            Stampa.print("💪 Inserisci il tuo livello: ");
            String livello = scanner.nextLine();
            bean.setLivelloUtente(livello);

            bean.setNome(utente.getNome());
            bean.setCognome(utente.getCognome());
            bean.setEmailUser(utente.getCredenziali().getEmail());
            bean.setDataRichiesta(LocalDate.now());
            bean.setStatus(StatoRichiestaScheda.INCORSO);

            controller.inserisciRichiesta(bean);

            Stampa.println("\n✅ Richiesta di scheda di nuoto inviata con successo!");
            Stampa.println("👤 Istruttore: " + emailIstruttore);
            Stampa.println("💪 Livello: " + bean.getLivelloUtente());
            Stampa.println("📝 Info: " + info);

        } catch (Exception e) {
            Stampa.println("❌ Errore durante l'invio della richiesta: " + e.getMessage());
        }
    }

    @Override
    public void exit(StateMachineImpl context) {
        Stampa.println("🔙 Tornato al menu principale");
    }

    @Override
    public void stampaBenvenuto() {
        Stampa.println("📚 --- Benvenuto nella richiesta della scheda di nuoto ---");
        Stampa.println("Ciao " + utente.getNome() + ", scegli un'opzione:");
    }

    @Override
    public void mostraSchermata() {
        Stampa.println("1. Invia richiesta scheda di nuoto");
        Stampa.println("0. Torna indietro");
    }
}
