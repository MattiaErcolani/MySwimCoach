package InMemory;

import Dao.RichiestaSchedaNuotoDao;
import Model.RichiestaSchedaNuotoModel;
import Other.StatoRichiestaScheda;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RichiestaSchedaNuotoDaoInMemory implements RichiestaSchedaNuotoDao {

    // ⚠️ SOLO MODEL nella lista
    private static final List<RichiestaSchedaNuotoModel> richieste = new ArrayList<>();
    private final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    // --- Inserisce una nuova richiesta ---
    @Override
    public void insertRichiesta(RichiestaSchedaNuotoModel model) {

        // Assegna ID se non presente
        model.setIdRichiesta(ID_GENERATOR.getAndIncrement());

        // Stato di default
        if (model.getStatus() == null) {
            model.setStatus(StatoRichiestaScheda.INCORSO);
        }

        richieste.add(model);
    }

    // --- Recupera richieste per utente ---
    @Override
    public List<RichiestaSchedaNuotoModel> getRichiesteByEmailUser(String emailUser) {
        List<RichiestaSchedaNuotoModel> risultati = new ArrayList<>();

        for (RichiestaSchedaNuotoModel r : richieste) {
            if (r.getEmailUser().equalsIgnoreCase(emailUser)) {
                risultati.add(r);
            }
        }

        return risultati;
    }

    // --- Recupera richieste per istruttore ---
    @Override
    public List<RichiestaSchedaNuotoModel> getRichiesteByEmailIstruttore(String emailIstruttore) {
        List<RichiestaSchedaNuotoModel> risultati = new ArrayList<>();

        for (RichiestaSchedaNuotoModel r : richieste) {
            if (r.getEmailIstruttore().equalsIgnoreCase(emailIstruttore)) {
                risultati.add(r);
            }
        }

        return risultati;
    }

    // --- Cancella richiesta ---
    @Override
    public boolean deleteRichiesta(int idRichiesta, String emailUser) {
        return richieste.removeIf(r ->
                r.getIdRichiesta() == idRichiesta &&
                        r.getEmailUser().equalsIgnoreCase(emailUser)
        );
    }

    // --- Aggiorna stato richiesta ---
    @Override
    public void updateStato(int idRichiesta, StatoRichiestaScheda nuovoStato) {
        for (RichiestaSchedaNuotoModel r : richieste) {
            if (r.getIdRichiesta() == idRichiesta) {
                r.setStatus(nuovoStato);
                return;
            }
        }
    }
}
