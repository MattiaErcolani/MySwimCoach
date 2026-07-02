package controller;

import bean.RichiestaSchedaNuotoBean;
import dao.RichiestaSchedaNuotoDao;
import exceptions.UtenteNonPresenteException;
import model.RichiestaSchedaNuotoModel;
import other.FactoryDao;
import other.StatoRichiestaScheda;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RichiestaSchedaNuotoController {

    private static final Logger logger = Logger.getLogger(RichiestaSchedaNuotoController.class.getName());
    private final RichiestaSchedaNuotoDao richiestaDao;

    public RichiestaSchedaNuotoController() {
        this.richiestaDao = FactoryDao.getRichiestaSchedaNuotoDao();
    }

    public void inserisciRichiesta(RichiestaSchedaNuotoBean bean) throws SQLException {
        RichiestaSchedaNuotoModel model = new RichiestaSchedaNuotoModel();
        model.setIdRichiesta(bean.getIdRichiesta());
        model.setNome(bean.getNome());
        model.setCognome(bean.getCognome());
        model.setLivelloUtente(bean.getLivelloUtente());
        model.setEmailIstruttore(bean.getEmailIstruttore());
        model.setEmailUser(bean.getEmailUser());
        model.setInfo(bean.getInfo());
        model.setDataRichiesta(bean.getDataRichiesta());
        model.setStatus(StatoRichiestaScheda.INCORSO);

        richiestaDao.insertRichiesta(model);
    }

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

    public boolean cancellaRichiesta(int idRichiesta, String emailUser) throws SQLException, UtenteNonPresenteException {
        return richiestaDao.deleteRichiesta(idRichiesta, emailUser);
    }

    public void aggiornaStatoRichiesta(int idRichiesta, StatoRichiestaScheda nuovoStato) {
        try {
            richiestaDao.updateStato(idRichiesta, nuovoStato);
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
    }
}