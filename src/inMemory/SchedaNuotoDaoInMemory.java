package inMemory;

import dao.SchedaNuotoDao;
import model.SchedaNuotoModel;
import model.EsercizioModel;
import java.util.ArrayList;
import java.util.List;

public class SchedaNuotoDaoInMemory implements SchedaNuotoDao {
    private static final List<SchedaNuotoModel> schede = new ArrayList<>();

    @Override
    public void insertScheda(SchedaNuotoModel scheda) {
        schede.add(scheda);
    }

    @Override
    public List<SchedaNuotoModel> getAllSchede() {
        return new ArrayList<>(schede);
    }

    @Override
    public SchedaNuotoModel getSchedaById(String idScheda) {
        return schede.stream()
                .filter(s -> s.getIdScheda().equals(idScheda))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void updateScheda(SchedaNuotoModel scheda) {
        for (int i = 0; i < schede.size(); i++) {
            if (schede.get(i).getIdScheda().equals(scheda.getIdScheda())) {
                schede.set(i, scheda);
                return;
            }
        }
    }

    @Override
    public void deleteScheda(String idScheda) {
        schede.removeIf(s -> s.getIdScheda().equals(idScheda));
    }

    @Override
    public void insertEsercizio(String idScheda, EsercizioModel esercizio) {
        SchedaNuotoModel scheda = getSchedaById(idScheda);
        if (scheda != null) {
            scheda.getEsercizi().add(esercizio);
        }
    }

    @Override
    public void deleteEsercizio(String idScheda, EsercizioModel esercizio) {
        SchedaNuotoModel scheda = getSchedaById(idScheda);
        if (scheda != null) {
            scheda.getEsercizi().remove(esercizio);
        }
    }
}
