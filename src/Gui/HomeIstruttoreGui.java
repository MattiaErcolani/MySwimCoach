package Gui;

import Bean.UtenteLoggatoBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeIstruttoreGui {

    @FXML private Text welcomeText;
    @FXML private Button btnLogout;

    private UtenteLoggatoBean istruttore;

    public void setIstruttore(UtenteLoggatoBean istruttore) {
        this.istruttore = istruttore;
        welcomeText.setText("Ciao, " + istruttore.getNome() + "!");
    }

    @FXML
    private void showVisualizzaSchede() {
        loadScene("/Fxml/visualizzaSchede.fxml");
    }

    @FXML
    private void showRichieste() {
        loadScene("/Fxml/richiesteSchede.fxml");
    }

    @FXML
    private void showCreaScheda() {
        loadScene("/Fxml/creaScheda.fxml");
    }

    @FXML
    private void showAssegnaScheda() {
        loadScene("/Fxml/assegnaScheda.fxml");
    }

    @FXML
    private void showRimuoviScheda() {
        loadScene("/Fxml/rimuoviScheda.fxml");
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
            if (controller instanceof VisualizzaSchedeGui) {
                ((VisualizzaSchedeGui) controller).setIstruttore(istruttore);
            } else if (controller instanceof RichiesteSchedeGui) {
                ((RichiesteSchedeGui) controller).setIstruttore(istruttore);
            } else if (controller instanceof CreaSchedaGui) {
                ((CreaSchedaGui) controller).setIstruttore(istruttore);
            } else if (controller instanceof AssegnaSchedaGui) {
                ((AssegnaSchedaGui) controller).setIstruttore(istruttore);
            } else if (controller instanceof RimuoviSchedaGui) {
                ((RimuoviSchedaGui) controller).setIstruttore(istruttore);
            }

            Stage stage = (Stage) btnLogout.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}