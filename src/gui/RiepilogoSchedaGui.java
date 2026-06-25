package gui;

import bean.UtenteLoggatoBean;
import controller.SchedaNuotoController;
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


public class RiepilogoSchedaGui {

    @FXML private Button btnAggiungiEsercizio;
    @FXML private Label statusEsercizio;
    @FXML private Text titoloScheda;
    @FXML private Text livelloText;
    @FXML private Text durataText;
    @FXML private VBox eserciziBox;
    @FXML private Button btnTornaSchede;
    @FXML private Button btnElimina;
    @FXML private Button btnModifica;

    @FXML private VBox editBox;
    @FXML private ChoiceBox<String> livelloEditChoice;
    @FXML private TextField durataEditField;
    @FXML private TextField distanzaEditField;
    @FXML private Button btnSalvaModifiche;

    private UtenteLoggatoBean istruttore;
    private SchedaNuotoModel scheda;
    private final SchedaNuotoController schedaController = new SchedaNuotoController();

    @FXML
    public void initialize() {
        livelloEditChoice.getItems().addAll("BASE", "INTERMEDIO", "AVANZATO");
    }

    public void setDati(UtenteLoggatoBean istruttore, SchedaNuotoModel scheda) {
        this.istruttore = istruttore;
        this.scheda = scheda;
        caricaRiepilogo();
    }

    private void caricaRiepilogo() {
        titoloScheda.setText("Scheda: " + scheda.getIdScheda());
        livelloText.setText("Livello: " + scheda.getLivello());
        durataText.setText("Durata: " + scheda.getDurata() + " min | Distanza: " + scheda.getDistanzaTotale() + " m");

        editBox.setVisible(false);
        editBox.setManaged(false);
        livelloText.setVisible(true);
        durataText.setVisible(true);

        eserciziBox.getChildren().clear();

        if (scheda.getEsercizi().isEmpty()) {
            Text empty = new Text("Nessun esercizio presente.");
            empty.setStyle("-fx-fill: #90A4AE;");
            eserciziBox.getChildren().add(empty);
        } else {
            for (EsercizioModel e : scheda.getEsercizi()) {
                VBox card = new VBox(4);
                card.setStyle("-fx-background-color: #0A1628; -fx-padding: 12; -fx-background-radius: 8; -fx-border-color: #1E3A5F; -fx-border-radius: 8;");

                Text nome = new Text("📌 " + e.getNome());
                nome.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 13px;");

                Text dettagli = new Text("Stile: " + e.getStile() + " | Distanza: " + e.getDistanza() + "m");
                dettagli.setStyle("-fx-fill: #90A4AE; -fx-font-size: 12px;");

                Text info = new Text("Info: " + e.getInfo());
                info.setStyle("-fx-fill: #64B5F6; -fx-font-size: 11px;");

                Button btnRimuovi = new Button("🗑 Rimuovi");
                btnRimuovi.setStyle("-fx-background-color: #EF5350; -fx-text-fill: white; -fx-font-size: 11px; -fx-background-radius: 6; -fx-cursor: hand; -fx-padding: 3 8;");
                btnRimuovi.setOnAction(ev -> {
                    schedaController.deleteEsercizio(scheda.getIdScheda(), e);
                    scheda.getEsercizi().remove(e);
                    caricaRiepilogo();
                });

                card.getChildren().addAll(nome, dettagli, info, btnRimuovi);
                eserciziBox.getChildren().add(card);
            }
        }
    }

    @FXML
    private void attivaModifica() {
        livelloEditChoice.setValue(scheda.getLivello());
        durataEditField.setText(String.valueOf(scheda.getDurata()));
        distanzaEditField.setText(String.valueOf(scheda.getDistanzaTotale()));

        livelloText.setVisible(false);
        durataText.setVisible(false);
        editBox.setVisible(true);
        editBox.setManaged(true);
    }

    @FXML
    private void salvaModifiche() {
        try {
            String nuovoLivello = livelloEditChoice.getValue();
            int nuovaDurata = Integer.parseInt(durataEditField.getText().trim());
            int nuovaDistanza = Integer.parseInt(distanzaEditField.getText().trim());

            scheda.setLivello(nuovoLivello);
            scheda.setDurata(nuovaDurata);
            scheda.setDistanzaTotale(nuovaDistanza);

            schedaController.updateScheda(scheda);

            caricaRiepilogo();
        } catch (NumberFormatException e) {
            durataText.setText("⚠ Durata e distanza devono essere numeri.");
        }
    }

    @FXML
    private void aggiungiEsercizio() {
        TextField nomeEs = creaCampoTesto("Nome esercizio");
        TextField stileEs = creaCampoTesto("Stile");
        TextField distEs = creaCampoTesto("Distanza (m)");
        TextField infoEs = creaCampoTesto("Info");

        VBox esercizioForm = new VBox(5, nomeEs, stileEs, distEs, infoEs);
        esercizioForm.setStyle("-fx-background-color: #0A1628; -fx-padding: 10; -fx-background-radius: 8; -fx-border-color: #1E3A5F; -fx-border-radius: 8;");

        Button btnConferma = new Button("✔ Conferma");
        btnConferma.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 6; -fx-cursor: hand;");
        btnConferma.setOnAction(ev -> {
            try {
                String nome = nomeEs.getText().trim();
                String stile = stileEs.getText().trim();
                int dist = Integer.parseInt(distEs.getText().trim());
                String info = infoEs.getText().trim();

                EsercizioModel es = new EsercizioModel(nome, stile, dist, info);
                schedaController.insertEsercizio(scheda.getIdScheda(), es);
                scheda.getEsercizi().add(es);

                statusEsercizio.setStyle("-fx-text-fill: #4CAF50;");
                statusEsercizio.setText("✅ Esercizio aggiunto!");

                caricaRiepilogo();
            } catch (NumberFormatException ex) {
                statusEsercizio.setStyle("-fx-text-fill: #EF5350;");
                statusEsercizio.setText("⚠ Distanza non valida");
            }
        });

        esercizioForm.getChildren().add(btnConferma);
        eserciziBox.getChildren().add(esercizioForm);
    }

    private TextField creaCampoTesto(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setStyle("-fx-background-color: #0A1628; -fx-text-fill: white; -fx-prompt-text-fill: #455A75; -fx-border-color: #1E3A5F; -fx-border-radius: 8; -fx-background-radius: 8;");
        return field;
    }

    @FXML
    private void eliminaScheda() {
        schedaController.deleteScheda(scheda.getIdScheda());
        tornaSchede();
    }

    @FXML
    private void tornaSchede() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/visualizzaSchede.fxml"));
            Parent root = loader.load();

            VisualizzaSchedeGui controller = loader.getController();
            controller.setIstruttore(istruttore);

            Stage stage = (Stage) btnTornaSchede.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace()
;
        }
    }
}