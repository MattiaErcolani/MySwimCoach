package Controller;

import Dao.SchedaNuotoDao;
import Model.SchedaNuotoModel;
import Model.EsercizioModel;
import Other.FactoryDao;

import java.util.List;

/**
 * Controller per gestire le schede di nuoto.
 * Fa da tramite tra il livello CLI/utente e la DAO.
 */
public class SchedaNuotoController {

    // DAO utilizzata dal controller (presa dal Factory)
    private final SchedaNuotoDao schedaDao;

    /**
     * Costruttore: inizializza il controller tramite FactoryDao.
     */
    public SchedaNuotoController() {
        this.schedaDao = FactoryDao.getSchedaNuotoDao(); // coerenza con le altre DAO
    }

    // ===========================
    // Gestione schede
    // ===========================

    /**
     * Recupera tutte le schede di nuoto disponibili.
     * @return lista di tutte le schede
     */
    public List<SchedaNuotoModel> getAllSchede() {
        return schedaDao.getAllSchede();
    }

    /**
     * Recupera una scheda specifica tramite ID.
     * @param idScheda ID della scheda da cercare
     * @return la scheda trovata oppure null se non esiste
     */
    public SchedaNuotoModel getSchedaById(String idScheda) {
        return schedaDao.getSchedaById(idScheda);
    }

    /**
     * Inserisce una nuova scheda di nuoto.
     * @param scheda la scheda da aggiungere
     */
    public void insertScheda(SchedaNuotoModel scheda) {
        schedaDao.insertScheda(scheda);
    }

    /**
     * Aggiorna una scheda esistente.
     * @param scheda la scheda aggiornata (deve avere lo stesso idScheda)
     */
    public void updateScheda(SchedaNuotoModel scheda) {
        schedaDao.updateScheda(scheda);
    }

    /**
     * Elimina una scheda tramite ID.
     * @param idScheda ID della scheda da eliminare
     */
    public void deleteScheda(String idScheda) {
        schedaDao.deleteScheda(idScheda);
    }

    // ===========================
    // Gestione esercizi
    // ===========================

    /**
     * Aggiunge un esercizio a una scheda di nuoto.
     * @param idScheda ID della scheda a cui aggiungere l'esercizio
     * @param esercizio esercizio da aggiungere
     * @return true se l'esercizio è stato aggiunto, false se la scheda non esiste
     */
    public boolean aggiungiEsercizio(String idScheda, EsercizioModel esercizio) {
        SchedaNuotoModel scheda = schedaDao.getSchedaById(idScheda);
        if (scheda == null) return false; // scheda non trovata
        scheda.getEsercizi().add(esercizio); // aggiunge l'esercizio
        schedaDao.updateScheda(scheda);      // salva la scheda aggiornata
        return true;
    }

    /**
     * Recupera tutti gli esercizi di una scheda.
     * @param idScheda ID della scheda
     * @return lista di esercizi, oppure null se la scheda non esiste
     */
    public List<EsercizioModel> getEsercizi(String idScheda) {
        SchedaNuotoModel scheda = schedaDao.getSchedaById(idScheda);
        if (scheda == null) return null;
        return scheda.getEsercizi();
    }

    public void insertEsercizio(String idScheda, EsercizioModel esercizio) {
        schedaDao.insertEsercizio(idScheda, esercizio);
    }

    public void deleteEsercizio(String idScheda, EsercizioModel esercizio) {
        schedaDao.deleteEsercizio(idScheda, esercizio);
    }


}
