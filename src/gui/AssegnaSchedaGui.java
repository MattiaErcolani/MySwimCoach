package gui;

import bean.RichiestaSchedaNuotoBean;
import bean.UtenteLoggatoBean;
import controller.RichiestaSchedaNuotoController;
import controller.SchedaNuotoAssegnataController;
import controller.SchedaNuotoController;
import dao.UserDao;
import model.SchedaNuotoAssegnataModel;
import model.SchedaNuotoModel;
import model.UtenteLoggatoModel;
import Other.FactoryDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class AssegnaSchedaGui {

    @FXML private VBox contentBox;
    @FXML private Button btnTornaMenu;
    @FXML private Button btnTornaSchede;

    private UtenteLoggatoBean istruttore;
    private final SchedaNuotoController schedaController = new SchedaNuotoController();
    private final SchedaNuotoAssegnataController assegnataController = new SchedaNuotoAssegnataController();
    private final RichiestaSchedaNuotoController richiestaController = new RichiestaSchedaNuotoController();

    public void setIstruttore(UtenteLoggatoBean istruttore) {
        this.istruttore = istruttore;
        mostraListaSchede();
    }

    @FXML
    private void mostraListaSchede() {
        btnTornaSchede.setVisible(false);
        btnTornaSchede.setManaged(false);
        contentBox.getChildren().clear();

        List<SchedaNuotoModel> schede = schedaController.getAllSchede();
        String emailIstruttore = istruttore.getCredenziali().getEmail();

        boolean hasSchede = false;
        for (SchedaNuotoModel s : schede) {
            if (!emailIstruttore.equals(s.getEmailIstruttore())) continue;
            hasSchede = true;

            VBox card = new VBox(5);
            card.setStyle("-fx-background-color: #0A1628; -fx-padding: 15; -fx-background-radius: 10; -fx-border-color: #1E3A5F; -fx-border-radius: 10; -fx-cursor: hand;");

            Text title = new Text("ID: " + s.getIdScheda() + " | Livello: " + s.getLivello());
            title.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

            Text details = new Text("Durata: " + s.getDurata() + " min | Distanza: " + s.getDistanzaTotale() + " m");
            details.setStyle("-fx-fill: #90A4AE; -fx-font-size: 12px;");

            card.getChildren().addAll(title, details);
            card.setOnMouseClicked(e -> mostraListaUtenti(s));

            contentBox.getChildren().add(card);
        }

        if (!hasSchede) {
            Text empty = new Text("Non hai ancora creato schede.");
            empty.setStyle("-fx-fill: #90A4AE;");
            contentBox.getChildren().add(empty);
        }
    }

    private void mostraListaUtenti(SchedaNuotoModel scheda) {
        btnTornaSchede.setVisible(true);
        btnTornaSchede.setManaged(true);
        contentBox.getChildren().clear();

        Text title = new Text("Scheda: " + scheda.getIdScheda());
        title.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;");
        contentBox.getChildren().add(title);

        Text subtitle = new Text("Seleziona l'utente a cui assegnare:");
        subtitle.setStyle("-fx-fill: #90A4AE; -fx-font-size: 12px;");
        contentBox.getChildren().add(subtitle);

        UserDao userDao = FactoryDao.getUserDAO();

        try {
            List<RichiestaSchedaNuotoBean> richieste = richiestaController.getRichiesteByEmailIstruttore(
                    istruttore.getCredenziali().getEmail()
            );

            if (richieste == null || richieste.isEmpty()) {
                Text empty = new Text("Nessuna richiesta ricevuta da utenti.");
                empty.setStyle("-fx-fill: #90A4AE;");
                contentBox.getChildren().add(empty);
                return;
            }

            for (RichiestaSchedaNuotoBean r : richieste) {
                UtenteLoggatoModel utente = userDao.getUserByEmail(r.getEmailUser());
                String nomeUtente = utente != null
                        ? utente.getNome() + " " + utente.getCognome()
                        : "Sconosciuto";

                VBox card = new VBox(5);
                card.setStyle("-fx-background-color: #0A1628; -fx-padding: 15; -fx-background-radius: 10; -fx-border-color: #1E3A5F; -fx-border-radius: 10; -fx-cursor: hand;");

                Text userTitle = new Text(nomeUtente + " (" + r.getEmailUser() + ")");
                userTitle.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

                Text userDetails = new Text("Livello: " + r.getLivelloUtente() + " | Stato: " + r.getStatus());
                userDetails.setStyle("-fx-fill: #90A4AE; -fx-font-size: 12px;");

                card.getChildren().addAll(userTitle, userDetails);
                card.setOnMouseClicked(e -> assegna(scheda, r.getEmailUser()));

                contentBox.getChildren().add(card);
            }

        } catch (Exception ex) {
            Text error = new Text("Errore: " + ex.getMessage());
            error.setStyle("-fx-fill: #EF5350;");
            contentBox.getChildren().add(error);
        }
    }

    private void assegna(SchedaNuotoModel scheda, String emailUser) {
        SchedaNuotoAssegnataModel assegnata = new SchedaNuotoAssegnataModel(
                scheda.getIdScheda(),
                emailUser,
                scheda.getDistanzaTotale(),
                scheda.getDurata()
        );

        assegnataController.assegnaScheda(assegnata);

        btnTornaSchede.setVisible(false);
        btnTornaSchede.setManaged(false);
        contentBox.getChildren().clear();

        Text success = new Text("✅ Scheda " + scheda.getIdScheda() + " assegnata a " + emailUser + "!");
        success.setStyle("-fx-fill: #4CAF50; -fx-font-size: 14px; -fx-font-weight: bold;");
        contentBox.getChildren().add(success);

        Button btnTornaLista = new Button("← Torna alla lista schede");
        btnTornaLista.setStyle("-fx-background-color: #1565C0; -fx-text-fill: white; -fx-background-radius: 6; -fx-cursor: hand; -fx-padding: 8 14;");
        btnTornaLista.setOnAction(e -> mostraListaSchede());
        contentBox.getChildren().add(btnTornaLista);
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
            e.printStackTrace()
;
        }
    }
}