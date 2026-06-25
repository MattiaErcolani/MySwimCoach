package dao;
import exceptions.EmailGiaInUsoException;
import exceptions.UtenteNonPresenteException;
import exceptions.CredenzialiSbagliateException;
import Model.UtenteLoggatoModel;
import Model.CredenzialiModel;
import java.util.List;

// uso interfaccia perchè devo solo chiamare i metodi
public interface UserDao {
    UtenteLoggatoModel loginMethod(CredenzialiModel credenzialiModel)throws UtenteNonPresenteException,CredenzialiSbagliateException;

    void registrazioneMethod(UtenteLoggatoModel registrazioneModel);

    void controllaEmailMethod(UtenteLoggatoModel registrazioneModel) throws EmailGiaInUsoException;

    void registraIstruttoreMethod(String email, String nome, String cognome) ;

    List<UtenteLoggatoModel> getIstruttori();

    UtenteLoggatoModel getUserByEmail(String email);

}