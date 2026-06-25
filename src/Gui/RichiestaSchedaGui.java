package Gui;

import bean.RichiestaSchedaNuotoBean;
import bean.UtenteLoggatoBean;
import controller.RichiestaSchedaNuotoController;
import Dao.UserDao;
import Model.UtenteLoggatoModel;
import Other.FactoryDao;
import Other.StatoRichiestaScheda;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RichiestaSchedaGui {

    @FXML private ChoiceBox<String> istruttoreChoice;
    @FXML private ChoiceBox<String> livelloChoice;
    @FXML private TextField infoField;
    @FXML private Label statusLabel;
    @FXML private Button btnInvia;
    @FXML private Button btnTornaMenu;

    private UtenteLoggatoBean utente;
    private List<UtenteLoggatoModel> istruttori = new ArrayList<>();

    public void setUtente(UtenteLoggatoBean utente) {
        this.utente = utente;
        caricaIstruttori();

        livelloChoice.getItems().addAll("BASE", "INTERMEDIO", "AVANZATO");
        livelloChoice.setValue("BASE");
    }

    private void caricaIstruttori() {
        UserDao userDao = FactoryDao.getUserDAO();
        istruttori = userDao.getIstruttori();

        istruttoreChoice.getItems().clear();
        for (UtenteLoggatoModel ist : istruttori) {
            istruttoreChoice.getItems().add(ist.getNome() + " " + ist.getCognome() + " (" + ist.getCredenziali().getEmail() + ")");
        }

        if (!istruttori.isEmpty()) {
            istruttoreChoice.setValue(istruttoreChoice.getItems().get(0));
        }
    }

    @FXML
    private void inviaRichiesta() {
        if (istruttoreChoice.getValue() == null) {
            statusLabel.setStyle("-fx-text-fill: #EF5350;");
            statusLabel.setText("Nessun istruttore disponibile.");
            return;
        }

        int idx = istruttoreChoice.getItems().indexOf(istruttoreChoice.getValue());
        String emailIstruttore = istruttori.get(idx).getCredenziali().getEmail();

        RichiestaSchedaNuotoController controller = new RichiestaSchedaNuotoController();
        RichiestaSchedaNuotoBean bean = new RichiestaSchedaNuotoBean();

        try {
            int idRandom = ThreadLocalRandom.current().nextInt(0, 1000);
            bean.setIdRichiesta(idRandom);
            bean.setEmailIstruttore(emailIstruttore);
            bean.setInfo(infoField.getText().trim());
            bean.setLivelloUtente(livelloChoice.getValue());
            bean.setNome(utente.getNome());
            bean.setCognome(utente.getCognome());
            bean.setEmailUser(utente.getCredenziali().getEmail());
            bean.setDataRichiesta(LocalDate.now());
            bean.setStatus(StatoRichiestaScheda.INCORSO);

            controller.inserisciRichiesta(bean);

            statusLabel.setStyle("-fx-text-fill: #4CAF50;");
            statusLabel.setText("✅ Richiesta inviata con successo!");

            infoField.clear();

        } catch (Exception e) {
            statusLabel.setStyle("-fx-text-fill: #EF5350;");
            statusLabel.setText("❌ Errore: " + e.getMessage());
        }
    }

    @FXML
    private void tornaMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/homeUtente.fxml"));
            Parent root = loader.load();

            HomeUtenteGui controller = loader.getController();
            controller.setUtente(utente);

            Stage stage = (Stage) btnTornaMenu.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace()
;
        }
    }
}