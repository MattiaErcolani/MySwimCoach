package controller;

import dao.UserDao;
import bean.UtenteLoggatoBean;
import bean.CredenzialiBean;
import model.UtenteLoggatoModel;
import model.CredenzialiModel;
import exceptions.CredenzialiSbagliateException;
import exceptions.UtenteNonPresenteException;
import other.FactoryDao;
import other.Stampa;

public class LoginController {

    public UtenteLoggatoBean login(CredenzialiBean credenzialiBean) throws CredenzialiSbagliateException, UtenteNonPresenteException {
        CredenzialiModel credenzialiModel = new CredenzialiModel(
                credenzialiBean.getEmail(),
                credenzialiBean.getPassword()
        );

        UtenteLoggatoBean utenteloggatobean = new UtenteLoggatoBean(credenzialiBean, null, null, false);

        try {
            UserDao userDAO = FactoryDao.getUserDAO();
            UtenteLoggatoModel utenteloggatoModel = userDAO.loginMethod(credenzialiModel);
            CredenzialiModel credenzialimodel = utenteloggatoModel.getCredenziali();
            CredenzialiBean credenzialibean = new CredenzialiBean(
                    credenzialimodel.getEmail(),
                    credenzialimodel.getPassword()
            );

            if (utenteloggatoModel.getCredenziali() != null) {
                utenteloggatobean.setNome(utenteloggatoModel.getNome());
                utenteloggatobean.setCognome(utenteloggatoModel.getCognome());
                utenteloggatobean.setCredenziali(credenzialibean);
                utenteloggatobean.setRuolo(utenteloggatoModel.getIsIstructor());
                return utenteloggatobean;
            } else {
                Stampa.errorPrint("❌ Credenziali mancanti o errate");
                return null;
            }

        }  catch (UtenteNonPresenteException | CredenzialiSbagliateException e) {
        return null;
    }
    }
}