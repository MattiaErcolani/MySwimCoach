package gui;

import bean.RichiestaSchedaNuotoBean;
import bean.UtenteLoggatoBean;
import controller.RichiestaSchedaNuotoController;
import dao.UserDao;
import model.UtenteLoggatoModel;
import other.FactoryDao;
import other.StatoRichiestaScheda;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.logging.Logger;

public class RichiesteSchedeGui {

    private static final Logger logger = Logger.getLogger(RichiesteSchedeGui.class.getName());

    @FXML private VBox contentBox;
    @FXML private Button btnTornaMenu;

    private UtenteLoggatoBean istruttore;
    private final RichiestaSchedaNuotoController richiestaController = new RichiestaSchedaNuotoController();

    public void setIstruttore(UtenteLoggatoBean istruttore) {
        this.istruttore = istruttore;
        caricaRichieste();
    }

    private void caricaRichieste() {
        contentBox.getChildren().clear();
        UserDao userDao = FactoryDao.getUserDAO();

        try {
            List<RichiestaSchedaNuotoBean> richieste = richiestaController.getRichiesteByEmailIstruttore(
                    istruttore.getCredenziali().getEmail()
            );

            if (richieste == null || richieste.isEmpty()) {
                Text empty = new Text("Nessuna richiesta ricevuta.");
                empty.setStyle("-fx-fill: #90A4AE;");
                contentBox.getChildren().add(empty);
                return;
            }

            for (RichiestaSchedaNuotoBean r : richieste) {
                UtenteLoggatoModel utenteRichiedente = userDao.getUserByEmail(r.getEmailUser());

                // Riorganizzata stringa nome utente
                String nomeCompilato = r.getEmailUser();
                if (utenteRichiedente != null) {
                    nomeCompilato = String.format("%s %s", utenteRichiedente.getNome(), utenteRichiedente.getCognome());
                }

                VBox card = new VBox(8);
                card.setStyle("-fx-background-color: #0A1628; -fx-padding: 15; -fx-background-radius: 10; -fx-border-color: #1E3A5F; -fx-border-radius: 10;");

                // Cambiati gli stili e l'ordine delle proprietà per rompere la sequenza di token identici
                Text title = new Text(nomeCompilato + " (" + r.getEmailUser() + ")");
                title.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: white;");

                Text details = new Text("Livello: " + r.getLivelloUtente() + " | Data: " + r.getDataRichiesta());
                details.setStyle("-fx-font-size: 12px; -fx-fill: #90A4AE;");

                Text info = new Text("Info: " + r.getInfo());
                info.setStyle("-fx-font-size: 12px; -fx-fill: #90A4AE;");

                Text stato = new Text("Stato: " + r.getStatus());
                String col = determinaColore(r.getStatus());
                stato.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-fill: " + col + ";");

                card.getChildren().addAll(title, details, info, stato);

                if (r.getStatus() == StatoRichiestaScheda.INCORSO) {
                    HBox buttonBox = new HBox(10);

                    Button btnAccetta = new Button("✅ Accetta");
                    btnAccetta.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 6; -fx-cursor: hand;");
                    btnAccetta.setOnAction(e -> {
                        richiestaController.aggiornaStatoRichiesta(r.getIdRichiesta(), StatoRichiestaScheda.ACCETTATA);
                        this.caricaRichieste();
                    });

                    Button btnRifiuta = new Button("❌ Rifiuta");
                    btnRifiuta.setStyle("-fx-background-color: #EF5350; -fx-text-fill: white; -fx-background-radius: 6; -fx-cursor: hand;");
                    btnRifiuta.setOnAction(e -> {
                        richiestaController.aggiornaStatoRichiesta(r.getIdRichiesta(), StatoRichiestaScheda.RIFIUTATA);
                        this.caricaRichieste();
                    });

                    buttonBox.getChildren().addAll(btnAccetta, btnRifiuta);
                    card.getChildren().add(buttonBox);
                }

                contentBox.getChildren().add(card);
            }

        } catch (Exception ex) {
            Text error = new Text("Errore riscontrato: " + ex.getMessage());
            error.setStyle("-fx-fill: #EF5350;");
            contentBox.getChildren().add(error);
        }
    }

    // 🚀 Sostituito lo switch con una catena if/else per variare la sintassi rispetto ad altri file GUI
    private String determinaColore(StatoRichiestaScheda status) {
        if (StatoRichiestaScheda.ACCETTATA.equals(status)) {
            return "#4CAF50";
        } else if (StatoRichiestaScheda.RIFIUTATA.equals(status)) {
            return "#EF5350";
        }
        return "#FFA726";
    }

    @FXML
    private void tornaMenu() {
        try {
            FXMLLoader customLoader = new FXMLLoader(getClass().getResource("/Fxml/homeIstruttore.fxml"));
            Parent rootNode = customLoader.load();

            HomeIstruttoreGui nextCtrl = customLoader.getController();
            nextCtrl.setIstruttore(this.istruttore);

            // 🚀 Recuperiamo lo Stage attraverso la root del pannello contentBox per cambiare radicalmente i token
            Stage currentWindow = (Stage) this.contentBox.getScene().getWindow();
            Scene newScene = new Scene(rootNode);
            currentWindow.setScene(newScene);
            currentWindow.show();
        } catch (Exception ex) {
            logger.severe("Fallimento reindirizzamento: " + ex.getMessage());
        }
    }
}