package gui;

import bean.RichiestaSchedaNuotoBean;
import bean.UtenteLoggatoBean;
import controller.RichiestaSchedaNuotoController;
import Other.StatoRichiestaScheda;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.logging.Logger;

public class VisualizzaRichiesteUtenteGui {

    private static final Logger logger = Logger.getLogger(VisualizzaRichiesteUtenteGui.class.getName());
    private static final String STILE_TESTO_SECONDARIO = "-fx-fill: #90A4AE; -fx-font-size: 12px;";
    private static final String STILE_TESTO_TITOLO = "-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;";
    private static final String STILE_CARD = "-fx-background-color: #0A1628; -fx-padding: 15; -fx-background-radius: 10; -fx-border-color: #1E3A5F; -fx-border-radius: 10;";

    @FXML private VBox contentBox;
    @FXML private Button btnTornaMenu;

    private UtenteLoggatoBean utente;
    private final RichiestaSchedaNuotoController controller = new RichiestaSchedaNuotoController();

    public void setUtente(UtenteLoggatoBean utente) {
        this.utente = utente;
        caricaRichieste();
    }

    private void caricaRichieste() {
        contentBox.getChildren().clear();

        try {
            List<RichiestaSchedaNuotoBean> richieste = controller.getRichiesteByEmailUser(
                    utente.getCredenziali().getEmail()
            );

            if (richieste == null || richieste.isEmpty()) {
                Text empty = new Text("Nessuna richiesta inviata.");
                empty.setStyle("-fx-fill: #90A4AE;");
                contentBox.getChildren().add(empty);
                return;
            }

            for (RichiestaSchedaNuotoBean r : richieste) {
                VBox card = new VBox(6);
                card.setStyle(STILE_CARD);

                Text title = new Text("ID Richiesta: " + r.getIdRichiesta());
                title.setStyle(STILE_TESTO_TITOLO);

                Text livello = new Text("Livello: " + r.getLivelloUtente());
                livello.setStyle(STILE_TESTO_SECONDARIO);

                Text istruttore = new Text("Istruttore: " + r.getEmailIstruttore());
                istruttore.setStyle(STILE_TESTO_SECONDARIO);

                Text info = new Text("Info: " + r.getInfo());
                info.setStyle(STILE_TESTO_SECONDARIO);

                Text data = new Text("Data: " + r.getDataRichiesta());
                data.setStyle(STILE_TESTO_SECONDARIO);

                Text stato = new Text("Stato: " + r.getStatus());
                stato.setStyle("-fx-fill: " + getColoreStato(r.getStatus()) + "; -fx-font-size: 12px; -fx-font-weight: bold;");

                card.getChildren().addAll(title, livello, istruttore, info, data, stato);
                contentBox.getChildren().add(card);
            }

        } catch (Exception ex) {
            Text error = new Text("Errore: " + ex.getMessage());
            error.setStyle("-fx-fill: #EF5350;");
            contentBox.getChildren().add(error);
        }
    }

    private String getColoreStato(StatoRichiestaScheda stato) {
        switch (stato) {
            case ACCETTATA: return "#4CAF50";
            case RIFIUTATA: return "#EF5350";
            default: return "#FFA726";
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
            logger.severe(e.getMessage());
        }
    }
}