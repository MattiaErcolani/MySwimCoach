package Controller;
import Dao.UserDao;
import Model.CredenzialiModel;
import Model.UtenteLoggatoModel;
import Bean.UtenteLoggatoBean;
import Exceptions.EmailGiaInUsoException;
import Other.FactoryDao;
import Other.Stampa;

public class RegistrazioneController {

    public void registrazione(UtenteLoggatoBean utente, int age, String level, String certificate) throws EmailGiaInUsoException {
        UtenteLoggatoModel user = new UtenteLoggatoModel();
        CredenzialiModel credenziali = new CredenzialiModel();
        credenziali.setEmail(utente.getCredenziali().getEmail());
        credenziali.setPassword(utente.getCredenziali().getPassword());

        user.setCredenziali(credenziali);
        user.setNome(utente.getNome());
        user.setCognome(utente.getCognome());
        user.setIstructor(utente.getRuolo());
        user.setAge(age);
        user.setLevel(level);
        user.setCertificate(certificate);

        try {
            UserDao registrazioneDao = FactoryDao.getUserDAO();
            registrazioneDao.controllaEmailMethod(user);
            registrazioneDao.registrazioneMethod(user);

            if (utente.getRuolo()) {
                registrazioneDao.registraIstruttoreMethod(user.getCredenziali().getEmail(), user.getNome(), user.getCognome());
            }

        } catch (EmailGiaInUsoException e) {
            throw new EmailGiaInUsoException();
        }
    }
}
