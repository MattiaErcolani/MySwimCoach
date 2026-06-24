package Controller;

import Dao.SchedaNuotoAssegnataDao;
import Model.SchedaNuotoAssegnataModel;
import Other.FactoryDao;

import java.util.List;

public class SchedaNuotoAssegnataController {

    private final SchedaNuotoAssegnataDao dao;

    public SchedaNuotoAssegnataController() {
        this.dao = FactoryDao.getSchedaNuotoAssegnataDao();
    }

    public void assegnaScheda(SchedaNuotoAssegnataModel scheda) {
        dao.assegnaScheda(scheda);
    }

    public List<SchedaNuotoAssegnataModel> getAllSchedeAssegnate() {
        return dao.getAllSchedeAssegnate();
    }

    public List<SchedaNuotoAssegnataModel> getSchedeByEmailUser(String emailUser) {
        return dao.getSchedeByEmailUser(emailUser);
    }

    public void rimuoviAssegnazione(String idScheda, String emailUser) {
        dao.rimuoviAssegnazione(idScheda, emailUser);
    }
}
