package InMemory;
import dao.UserDao;
import Exceptions.CredenzialiSbagliateException;
import Exceptions.EmailGiaInUsoException;
import Exceptions.UtenteNonPresenteException;
import Model.CredenzialiModel;
import Model.UtenteLoggatoModel;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.List;

public class UserDaoInMemory implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoInMemory.class.getName());

    // "Database" in memoria
    private static final Map<String, UtenteLoggatoModel> databaseUtenti = new HashMap<>();

    static {
        // Utente normale
        CredenzialiModel cred1 = new CredenzialiModel("user1@example.com", "password1");
        UtenteLoggatoModel utente1 = new UtenteLoggatoModel(cred1, "Mario", "Rossi", false);
        databaseUtenti.put(cred1.getEmail(), utente1);

        // Istruttore
        CredenzialiModel cred2 = new CredenzialiModel("istruttore@example.com", "password2");
        UtenteLoggatoModel utente2 = new UtenteLoggatoModel(cred2, "Luigi", "Verdi", true);
        databaseUtenti.put(cred2.getEmail(), utente2);

        CredenzialiModel cred3 = new CredenzialiModel("coach1@test.com", "password3");
        UtenteLoggatoModel utente3 = new UtenteLoggatoModel(cred3, "Luigi", "Verdi", true);
        databaseUtenti.put(cred3.getEmail(), utente3);

        logger.info("Database utenti prepopolato con 2 utenti di test");
    }

    @Override
    public UtenteLoggatoModel loginMethod(CredenzialiModel credenzialiModel)
            throws UtenteNonPresenteException, CredenzialiSbagliateException {

        String email = credenzialiModel.getEmail();
        String password = credenzialiModel.getPassword();
        System.out.println("EMAIL CERCATA: [" + credenzialiModel.getEmail() + "]");
        System.out.println("UTENTI DISPONIBILI: " + databaseUtenti.keySet());
        // 1. Controlla se l'utente esiste
        if (!databaseUtenti.containsKey(email)) {
            throw new UtenteNonPresenteException();
        }

        UtenteLoggatoModel utente = databaseUtenti.get(email);

        // 2. Controlla password
        if (!utente.getCredenziali().getPassword().equals(password)) {
            throw new CredenzialiSbagliateException();
        }

        return utente;
    }

    @Override
    public void registrazioneMethod(UtenteLoggatoModel registrazioneModel) {
        String email = registrazioneModel.getCredenziali().getEmail();

        databaseUtenti.put(email, registrazioneModel);

        logger.info("Utente registrato correttamente in memoria: " + email);
    }

    public void controllaEmailMethod(UtenteLoggatoModel registrazioneModel) throws EmailGiaInUsoException {
        String email = registrazioneModel.getCredenziali().getEmail();

        if (databaseUtenti.containsKey(email)) {
            throw new EmailGiaInUsoException();
        }
    }

    public void registraIstruttoreMethod(String email, String nome, String cognome) {
        if (!databaseUtenti.containsKey(email)) {
            logger.severe("Utente non trovato per diventare istruttore: " + email);
            return;
        }

        UtenteLoggatoModel utente = databaseUtenti.get(email);
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setIstructor(true);

        logger.info("Utente promosso a istruttore: " + email);
    }

    // Metodo utile per test/debug
    public Map<String, UtenteLoggatoModel> getAllUsers() {
        return databaseUtenti;
    }

    @Override
    public List<UtenteLoggatoModel> getIstruttori() {
        return databaseUtenti.values().stream()
                .filter(UtenteLoggatoModel::getisIstructor)
                .collect(Collectors.toList());
    }

    @Override
    public UtenteLoggatoModel getUserByEmail(String email) {
        return databaseUtenti.get(email);
    }
}
