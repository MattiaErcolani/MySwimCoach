package gui;

import bean.UtenteLoggatoBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class HomeUtenteGui {

    private static final Logger logger = Logger.getLogger(HomeUtenteGui.class.getName());

    @FXML private Text welcomeText;
    @FXML private Button btnLogout;

    private UtenteLoggatoBean utente;

    public void setUtente(UtenteLoggatoBean utente) {
        this.utente = utente;
        welcomeText.setText("Ciao, " + utente.getNome() + "!");
    }

    @FXML
    private void showRichiestaScheda() {
        loadScene("/Fxml/richiestaScheda.fxml");
    }

    @FXML
    private void showVisualizzaRichieste() {
        loadScene("/Fxml/visualizzaRichiesteUtente.fxml");
    }

    @FXML
    private void showVisualizzaSchede() {
        loadScene("/Fxml/visualizzaSchedeUtente.fxml");
    }

    @FXML
    private void handleLogout() {
        loadScene("/Fxml/login.fxml");
    }

    private void loadScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof RichiestaSchedaGui richiestaSchedaGui) {
                richiestaSchedaGui.setUtente(utente);
            } else if (controller instanceof VisualizzaRichiesteUtenteGui visualizzaRichiesteUtenteGui) {
                visualizzaRichiesteUtenteGui.setUtente(utente);
            } else if (controller instanceof VisualizzaSchedeUtenteGui visualizzaSchedeUtenteGui) {
                visualizzaSchedeUtenteGui.setUtente(utente);
            }

            Stage stage = (Stage) btnLogout.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }
}