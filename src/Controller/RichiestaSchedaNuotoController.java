package Controller;

import Bean.RichiestaSchedaNuotoBean;
import Dao.RichiestaSchedaNuotoDao;
import Exceptions.UtenteNonPresenteException;
import Model.RichiestaSchedaNuotoModel;
import Other.FactoryDao;
import Other.StatoRichiestaScheda;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RichiestaSchedaNuotoController {

    private final RichiestaSchedaNuotoDao richiestaDao;

    public RichiestaSchedaNuotoController() {
        this.richiestaDao = FactoryDao.getRichiestaSchedaNuotoDao();
    }

    // ✅ Inserisce una nuova richiesta (email istruttore scelta dal menu a tendina)
    public void inserisciRichiesta(RichiestaSchedaNuotoBean bean) throws SQLException, UtenteNonPresenteException {

        // Trasformazione Bean -> Model
        RichiestaSchedaNuotoModel model = new RichiestaSchedaNuotoModel();
        model.setIdRichiesta(bean.getIdRichiesta());
        model.setNome(bean.getNome());
        model.setCognome(bean.getCognome());
        model.setLivelloUtente(bean.getLivelloUtente());
        model.setEmailIstruttore(bean.getEmailIstruttore()); // scelta dal menu a tendina
        model.setEmailUser(bean.getEmailUser());
        model.setInfo(bean.getInfo());
        model.setDataRichiesta(bean.getDataRichiesta());
        model.setStatus(StatoRichiestaScheda.INCORSO); // Default iniziale

        richiestaDao.insertRichiesta(model);
    }

    // ✅ Recupera le richieste di un utente
    public List<RichiestaSchedaNuotoBean> getRichiesteByEmailUser(String emailUser) throws SQLException, UtenteNonPresenteException {
        List<RichiestaSchedaNuotoModel> modelli = richiestaDao.getRichiesteByEmailUser(emailUser);
        List<RichiestaSchedaNuotoBean> beans = new ArrayList<>();

        for(RichiestaSchedaNuotoModel m : modelli) {
            RichiestaSchedaNuotoBean b = new RichiestaSchedaNuotoBean();
            b.setIdRichiesta(m.getIdRichiesta());
            b.setNome(m.getNome());
            b.setCognome(m.getCognome());
            b.setLivelloUtente(m.getLivelloUtente());
            b.setEmailIstruttore(m.getEmailIstruttore());
            b.setEmailUser(m.getEmailUser());
            b.setInfo(m.getInfo());
            b.setDataRichiesta(m.getDataRichiesta());
            b.setStatus(m.getStatus());
            beans.add(b);
        }
        return beans;
    }

    // ✅ Recupera le richieste di un istruttore
    public List<RichiestaSchedaNuotoBean> getRichiesteByEmailIstruttore(String emailIstruttore) throws SQLException, UtenteNonPresenteException {
        List<RichiestaSchedaNuotoModel> modelli = richiestaDao.getRichiesteByEmailIstruttore(emailIstruttore);
        List<RichiestaSchedaNuotoBean> beans = new ArrayList<>();

        for(RichiestaSchedaNuotoModel m : modelli) {
            RichiestaSchedaNuotoBean b = new RichiestaSchedaNuotoBean();
            b.setIdRichiesta(m.getIdRichiesta());
            b.setNome(m.getNome());
            b.setCognome(m.getCognome());
            b.setLivelloUtente(m.getLivelloUtente());
            b.setEmailIstruttore(m.getEmailIstruttore());
            b.setEmailUser(m.getEmailUser());
            b.setInfo(m.getInfo());
            b.setDataRichiesta(m.getDataRichiesta());
            b.setStatus(m.getStatus());
            beans.add(b);
        }
        return beans;
    }

    // ✅ Cancella una richiesta
    public boolean cancellaRichiesta(int idRichiesta, String emailUser) throws SQLException, UtenteNonPresenteException {
        return richiestaDao.deleteRichiesta(idRichiesta, emailUser);
    }

    // ✅ Aggiorna lo stato di una richiesta
    public void aggiornaStatoRichiesta(int idRichiesta, StatoRichiestaScheda nuovoStato) {
        try {
            richiestaDao.updateStato(idRichiesta, nuovoStato);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
