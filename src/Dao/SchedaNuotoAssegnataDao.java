package Dao;

import Model.SchedaNuotoAssegnataModel;
import java.util.List;

public interface SchedaNuotoAssegnataDao {

    void assegnaScheda(SchedaNuotoAssegnataModel scheda);

    List<SchedaNuotoAssegnataModel> getAllSchedeAssegnate();

    List<SchedaNuotoAssegnataModel> getSchedeByEmailUser(String emailUser);

    void rimuoviAssegnazione(String idScheda, String emailUser);
}
