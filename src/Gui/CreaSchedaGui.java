package Gui;

import Bean.UtenteLoggatoBean;
import Controller.SchedaNuotoController;
import Model.EsercizioModel;
import Model.SchedaNuotoModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Other.Stampa;

import java.util.ArrayList;
import java.util.List;

public class CreaSchedaGui {

    @FXML private TextField idSchedaField;
    @FXML private ChoiceBox<String> livelloSchedaChoice;
    @FXML private TextField durataField;
    @FXML private TextField distanzaField;
    @FXML private VBox eserciziBox;
    @FXML private Label statusCreaScheda;
    @FXML private Button btnAggiungiEsercizio;
    @FXML private Button btnSalvaScheda;
    @FXML private Button btnTornaMenu;

    private UtenteLoggatoBean istruttore;
    private final SchedaNuotoController schedaController = new SchedaNuotoController();
    private final List<EsercizioModel> esercizi = new ArrayList<>();

    @FXML
    public void initialize() {
        livelloSchedaChoice.getItems().addAll("BASE", "INTERMEDIO", "AVANZATO");
        livelloSchedaChoice.setValue("BASE");
    }

    public void setIstruttore(UtenteLoggatoBean istruttore) {
        this.istruttore = istruttore;
    }

    @FXML
    private void aggiungiEsercizio() {
        TextField nomeEs = creaCampoTesto("Nome esercizio");
        TextField stileEs = creaCampoTesto("Stile");
        TextField distEs = creaCampoTesto("Distanza (m)");
        TextField infoEs = creaCampoTesto("Info");

        VBox esercizioForm = new VBox(5, nomeEs, stileEs, distEs, infoEs);
        esercizioForm.setStyle("-fx-background-color: #0A1628; -fx-padding: 10; -fx-background-radius: 8; -fx-border-color: #1E3A5F; -fx-border-radius: 8;");

        Button btnConferma = new Button("✔ Conferma esercizio");
        btnConferma.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 6; -fx-cursor: hand;");
        btnConferma.setOnAction(ev -> {
            try {
                String nome = nomeEs.getText().trim();
                String stile = stileEs.getText().trim();
                int dist = Integer.parseInt(distEs.getText().trim());
                String info = infoEs.getText().trim();

                EsercizioModel es = new EsercizioModel(nome, stile, dist, info);
                esercizi.add(es);

                Text riepilogo = new Text("✓ " + nome + " | " + stile + " | " + dist + "m | " + info);
                riepilogo.setStyle("-fx-fill: #64B5F6; -fx-font-size: 12px;");

                eserciziBox.getChildren().remove(esercizioForm);
                eserciziBox.getChildren().add(riepilogo);
            } catch (NumberFormatException ex) {
                statusCreaScheda.setStyle("-fx-text-fill: #EF5350;");
                statusCreaScheda.setText("⚠ Distanza esercizio non valida");
            }
        });

        esercizioForm.getChildren().add(btnConferma);
        eserciziBox.getChildren().add(esercizioForm);
    }

    @FXML
    private void salvaScheda() {
        try {
            String idScheda = idSchedaField.getText().trim();
            String livello = livelloSchedaChoice.getValue();
            int durata = Integer.parseInt(durataField.getText().trim());
            int distanza = Integer.parseInt(distanzaField.getText().trim());

            if (idScheda.isEmpty()) {
                statusCreaScheda.setStyle("-fx-text-fill: #EF5350;");
                statusCreaScheda.setText("Compila l'ID Scheda.");
                return;
            }

            SchedaNuotoModel scheda = new SchedaNuotoModel(idScheda, distanza, durata, livello);
            scheda.setEmailIstruttore(istruttore.getCredenziali().getEmail());

            schedaController.insertScheda(scheda);

            for (EsercizioModel es : esercizi) {
                schedaController.insertEsercizio(idScheda, es);
            }

            statusCreaScheda.setStyle("-fx-text-fill: #4CAF50;");
            statusCreaScheda.setText("✅ Scheda creata con successo!");

        } catch (NumberFormatException ex) {
            statusCreaScheda.setStyle("-fx-text-fill: #EF5350;");
            statusCreaScheda.setText("Durata e distanza devono essere numeri.");
        }
    }

    private TextField creaCampoTesto(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setStyle("-fx-background-color: #0A1628; -fx-text-fill: white; -fx-prompt-text-fill: #455A75; -fx-border-color: #1E3A5F; -fx-border-radius: 8; -fx-background-radius: 8;");
        return field;
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