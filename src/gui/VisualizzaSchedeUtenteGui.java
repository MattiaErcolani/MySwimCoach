package gui;

import bean.UtenteLoggatoBean;
import controller.SchedaNuotoAssegnataController;
import Model.SchedaNuotoAssegnataModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class VisualizzaSchedeUtenteGui {

    @FXML private VBox contentBox;
    @FXML private Button btnTornaMenu;

    private UtenteLoggatoBean utente;
    private final SchedaNuotoAssegnataController assegnataController = new SchedaNuotoAssegnataController();

    public void setUtente(UtenteLoggatoBean utente) {
        this.utente = utente;
        caricaSchede();
    }

    private void caricaSchede() {
        contentBox.getChildren().clear();

        List<SchedaNuotoAssegnataModel> schede = assegnataController.getSchedeByEmailUser(
                utente.getCredenziali().getEmail()
        );

        if (schede == null || schede.isEmpty()) {
            Text empty = new Text("Nessuna scheda assegnata al momento.");
            empty.setStyle("-fx-fill: #90A4AE;");
            contentBox.getChildren().add(empty);
            return;
        }

        for (SchedaNuotoAssegnataModel s : schede) {
            VBox card = new VBox(8);
            card.setStyle("-fx-background-color: #0A1628; -fx-padding: 15; -fx-background-radius: 10; -fx-border-color: #1E3A5F; -fx-border-radius: 10;");

            Text title = new Text("ID Scheda: " + s.getIdScheda());
            title.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

            Text details = new Text("Durata: " + s.getDurata() + " min | Distanza: " + s.getDistanzaTotale() + " m");
            details.setStyle("-fx-fill: #90A4AE; -fx-font-size: 12px;");

            card.getChildren().addAll(title, details);
            contentBox.getChildren().add(card);
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