package Gui;

import Bean.UtenteLoggatoBean;
import Controller.SchedaNuotoController;
import Model.EsercizioModel;
import Model.SchedaNuotoModel;
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

public class VisualizzaSchedeGui {

    @FXML private VBox contentBox;
    @FXML private Button btnTornaMenu;

    private UtenteLoggatoBean istruttore;
    private final SchedaNuotoController schedaController = new SchedaNuotoController();

    public void setIstruttore(UtenteLoggatoBean istruttore) {
        this.istruttore = istruttore;
        caricaSchede();
    }

    private void caricaSchede() {
        contentBox.getChildren().clear();

        List<SchedaNuotoModel> schede = schedaController.getAllSchede();
        String emailIstruttore = istruttore.getCredenziali().getEmail();

        boolean hasSchede = false;
        for (SchedaNuotoModel s : schede) {
            if (!emailIstruttore.equals(s.getEmailIstruttore())) continue;
            hasSchede = true;

            VBox card = new VBox(8);
            card.setStyle("-fx-background-color: #0A1628; -fx-padding: 15; -fx-background-radius: 10; -fx-border-color: #1E3A5F; -fx-border-radius: 10; -fx-cursor: hand;");

            Text title = new Text("ID: " + s.getIdScheda() + " | Livello: " + s.getLivello());
            title.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

            Text details = new Text("Durata: " + s.getDurata() + " min | Distanza: " + s.getDistanzaTotale() + " m");
            details.setStyle("-fx-fill: #90A4AE; -fx-font-size: 12px;");

            Text hint = new Text("Clicca per vedere il riepilogo completo");
            hint.setStyle("-fx-fill: #1565C0; -fx-font-size: 11px;");

            card.getChildren().addAll(title, details, hint);

            card.setOnMouseClicked(e -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/riepilogoScheda.fxml"));
                    Parent root = loader.load();

                    RiepilogoSchedaGui controller = loader.getController();
                    controller.setDati(istruttore, s);

                    Stage stage = (Stage) btnTornaMenu.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            contentBox.getChildren().add(card);
        }

        if (!hasSchede) {
            Text empty = new Text("Non hai ancora creato schede.");
            empty.setStyle("-fx-fill: #90A4AE;");
            contentBox.getChildren().add(empty);
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