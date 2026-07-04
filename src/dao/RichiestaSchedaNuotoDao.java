package dao;

import exceptions.UtenteNonPresenteException;
import model.RichiestaSchedaNuotoModel;
import other.StatoRichiestaScheda;

import java.sql.SQLException;
import java.util.List;

public interface RichiestaSchedaNuotoDao {

    // Inserisce una nuova richiesta nel DB
    void insertRichiesta(RichiestaSchedaNuotoModel richiesta) throws SQLException;

    // Restituisce tutte le richieste fatte da un utente
    List<RichiestaSchedaNuotoModel> getRichiesteByEmailUser(String emailUser)
            throws SQLException, UtenteNonPresenteException;

    // Restituisce tutte le richieste assegnate a un istruttore
    List<RichiestaSchedaNuotoModel> getRichiesteByEmailIstruttore(String emailIstruttore)
            throws SQLException, UtenteNonPresenteException;

    // Aggiorna lo stato di una richiesta
    void updateStato(int idRichiesta, StatoRichiestaScheda nuovoStato) throws SQLException;

    // Elimina una richiesta
    boolean deleteRichiesta(int idRichiesta, String emailUser)
            throws SQLException, UtenteNonPresenteException;
}
