package Gui;

import Bean.UtenteLoggatoBean;
import Controller.SchedaNuotoAssegnataController;
import Model.SchedaNuotoAssegnataModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Other.Stampa;

import java.util.List;

public class RimuoviSchedaGui {

    @FXML private VBox contentBox;
    @FXML private Button btnTornaMenu;

    private UtenteLoggatoBean istruttore;
    private final SchedaNuotoAssegnataController assegnataController = new SchedaNuotoAssegnataController();

    public void setIstruttore(UtenteLoggatoBean istruttore) {
        this.istruttore = istruttore;
        caricaAssegnazioni();
    }

    private void caricaAssegnazioni() {
        contentBox.getChildren().clear();

        List<SchedaNuotoAssegnataModel> assegnate = assegnataController.getAllSchedeAssegnate();

        if (assegnate.isEmpty()) {
            Text empty = new Text("Nessuna scheda assegnata trovata.");
            empty.setStyle("-fx-fill: #90A4AE;");
            contentBox.getChildren().add(empty);
            return;
        }

        for (SchedaNuotoAssegnataModel s : assegnate) {
            VBox card = new VBox(8);
            card.setStyle("-fx-background-color: #0A1628; -fx-padding: 15; -fx-background-radius: 10; -fx-border-color: #1E3A5F; -fx-border-radius: 10;");

            Text title = new Text("ID Scheda: " + s.getIdScheda());
            title.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

            Text details = new Text("Utente: " + s.getEmailUser() + " | Durata: " + s.getDurata() + " min | Distanza: " + s.getDistanzaTotale() + " m");
            details.setStyle("-fx-fill: #90A4AE; -fx-font-size: 12px;");

            Button btnRimuovi = new Button("🗑 Rimuovi assegnazione");
            btnRimuovi.setStyle("-fx-background-color: #EF5350; -fx-text-fill: white; -fx-background-radius: 6; -fx-cursor: hand;");
            btnRimuovi.setOnAction(e -> {
                assegnataController.rimuoviAssegnazione(s.getIdScheda(), s.getEmailUser());
                caricaAssegnazioni();
            });

            card.getChildren().addAll(title, details, btnRimuovi);
            contentBox.getChildren().add(card);
        }
    }

    @FXML
    private void tornaMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/homeIstruttore.fxml"));
            Parent root = loader.load();

            HomeIstruttoreGui controller = loader.getController();
            controller.setIstruttore(istruttore);

            Stage stage = (Stage) btnTornaMenu.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            Stampa.errorPrint(e.getMessage());
        }
    }
}