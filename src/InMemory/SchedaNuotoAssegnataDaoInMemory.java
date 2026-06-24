package InMemory;

import Dao.SchedaNuotoAssegnataDao;
import Model.SchedaNuotoAssegnataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SchedaNuotoAssegnataDaoInMemory implements SchedaNuotoAssegnataDao {
    private static final List<SchedaNuotoAssegnataModel> assegnate = new ArrayList<>();

    @Override
    public void assegnaScheda(SchedaNuotoAssegnataModel scheda) {
        assegnate.add(scheda);
    }

    @Override
    public List<SchedaNuotoAssegnataModel> getAllSchedeAssegnate() {
        return new ArrayList<>(assegnate);
    }

    @Override
    public List<SchedaNuotoAssegnataModel> getSchedeByEmailUser(String emailUser) {
        return assegnate.stream()
                .filter(s -> s.getEmailUser().equals(emailUser))
                .collect(Collectors.toList());
    }

    @Override
    public void rimuoviAssegnazione(String idScheda, String emailUser) {
        assegnate.removeIf(s -> s.getIdScheda().equals(idScheda) && s.getEmailUser().equals(emailUser));
    }
}
