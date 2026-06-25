package controller;

import dao.UserDao;
import bean.UtenteLoggatoBean;
import bean.CredenzialiBean;
import Model.UtenteLoggatoModel;
import Model.CredenzialiModel;
import exceptions.CredenzialiSbagliateException;
import exceptions.UtenteNonPresenteException;
import Other.FactoryDao;
import Other.Stampa;

public class LoginController {
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private boolean ruolo;

    public UtenteLoggatoBean login(CredenzialiBean credenzialiBean)throws CredenzialiSbagliateException,UtenteNonPresenteException{
        CredenzialiModel credenzialiModel = new CredenzialiModel(
                credenzialiBean.getEmail(),
                credenzialiBean.getPassword()
        );

        UtenteLoggatoBean utenteloggatobean = new UtenteLoggatoBean(credenzialiBean,nome,cognome,ruolo);


        try{

            // collegarsi al Dao per ottenere gli utenti

            UserDao userDAO = FactoryDao.getUserDAO();

            UtenteLoggatoModel utenteloggatoModel = userDAO.loginMethod(credenzialiModel);
            CredenzialiModel credenzialimodel = utenteloggatoModel.getCredenziali();
            CredenzialiBean credenzialibean = new CredenzialiBean(
                    credenzialimodel.getEmail(),
                    credenzialimodel.getPassword()
            );

            if (utenteloggatoModel != null && utenteloggatoModel.getCredenziali() != null) {

                utenteloggatobean.setNome(utenteloggatoModel.getNome());
                utenteloggatobean.setCognome(utenteloggatoModel.getCognome());

                utenteloggatobean.setCredenziali(credenzialibean);
                utenteloggatobean.setRuolo(utenteloggatoModel.getisIstructor());
                return utenteloggatobean;
            }else {
                Stampa.errorPrint("❌ Credenziali mancanti o errate");
                return null;
            }
            // prende dalla Dao le credenziali dell'utente


        } catch (UtenteNonPresenteException un) {
            return null;
        } catch (CredenzialiSbagliateException cl){
            return null;
        }

    }
}
