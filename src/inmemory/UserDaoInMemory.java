package inmemory;
import dao.UserDao;
import exceptions.CredenzialiSbagliateException;
import exceptions.EmailGiaInUsoException;
import exceptions.UtenteNonPresenteException;
import model.CredenzialiModel;
import model.UtenteLoggatoModel;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.List;

public class UserDaoInMemory implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoInMemory.class.getName());

    private static final Map<String, UtenteLoggatoModel> databaseUtenti = new HashMap<>();

    static {
        CredenzialiModel cred1 = new CredenzialiModel("user1@example.com", "password1");
        UtenteLoggatoModel utente1 = new UtenteLoggatoModel(cred1, "Mario", "Rossi", false);
        databaseUtenti.put(cred1.getEmail(), utente1);

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
        logger.log(java.util.logging.Level.INFO, "EMAIL CERCATA: [{0}]", credenzialiModel.getEmail());
        logger.log(java.util.logging.Level.INFO, "UTENTI DISPONIBILI: {0}", databaseUtenti.keySet());

        if (!databaseUtenti.containsKey(email)) {
            throw new UtenteNonPresenteException();
        }

        UtenteLoggatoModel utente = databaseUtenti.get(email);

        if (!utente.getCredenziali().getPassword().equals(password)) {
            throw new CredenzialiSbagliateException();
        }

        return utente;
    }

    @Override
    public void registrazioneMethod(UtenteLoggatoModel registrazioneModel) {
        String email = registrazioneModel.getCredenziali().getEmail();
        databaseUtenti.put(email, registrazioneModel);
        logger.log(java.util.logging.Level.INFO, "Utente registrato correttamente in memoria: {0}", email);
    }

    public void controllaEmailMethod(UtenteLoggatoModel registrazioneModel) throws EmailGiaInUsoException {
        String email = registrazioneModel.getCredenziali().getEmail();
        if (databaseUtenti.containsKey(email)) {
            throw new EmailGiaInUsoException();
        }
    }

    public void registraIstruttoreMethod(String email, String nome, String cognome) {
        if (!databaseUtenti.containsKey(email)) {
            logger.log(java.util.logging.Level.SEVERE, "Utente non trovato per diventare istruttore: {0}", email);
            return;
        }

        UtenteLoggatoModel utente = databaseUtenti.get(email);
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setIstructor(true);

        logger.log(java.util.logging.Level.INFO, "Utente promosso a istruttore: {0}", email);
    }

    public Map<String, UtenteLoggatoModel> getAllUsers() {
        return databaseUtenti;
    }

    @Override
    public List<UtenteLoggatoModel> getIstruttori() {
        return databaseUtenti.values().stream()
                .filter(UtenteLoggatoModel::getIsIstructor)
                .collect(Collectors.toList());
    }

    @Override
    public UtenteLoggatoModel getUserByEmail(String email) {
        return databaseUtenti.get(email);
    }
}