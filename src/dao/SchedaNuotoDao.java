package dao;

import model.EsercizioModel;
import model.SchedaNuotoModel;
import java.util.List;

public interface SchedaNuotoDao {
    void insertScheda(SchedaNuotoModel scheda);
    List<SchedaNuotoModel> getAllSchede();
    SchedaNuotoModel getSchedaById(String idScheda);
    void updateScheda(SchedaNuotoModel scheda);
    void deleteScheda(String idScheda);
    void insertEsercizio(String idScheda, EsercizioModel esercizio);
    void deleteEsercizio(String idScheda, EsercizioModel esercizio);
}
