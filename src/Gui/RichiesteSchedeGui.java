package Gui;

import bean.RichiestaSchedaNuotoBean;
import bean.UtenteLoggatoBean;
import Controller.RichiestaSchedaNuotoController;
import Dao.UserDao;
import Model.UtenteLoggatoModel;
import Other.FactoryDao;
import Other.StatoRichiestaScheda;
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

public class RichiesteSchedeGui {

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
                String nomeUtente = utenteRichiedente != null
                        ? utenteRichiedente.getNome() + " " + utenteRichiedente.getCognome()
                        : r.getEmailUser();

                VBox card = new VBox(8);
                card.setStyle("-fx-background-color: #0A1628; -fx-padding: 15; -fx-background-radius: 10; -fx-border-color: #1E3A5F; -fx-border-radius: 10;");

                Text title = new Text(nomeUtente + " (" + r.getEmailUser() + ")");
                title.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

                Text details = new Text("Livello: " + r.getLivelloUtente() + " | Data: " + r.getDataRichiesta());
                details.setStyle("-fx-fill: #90A4AE; -fx-font-size: 12px;");

                Text info = new Text("Info: " + r.getInfo());
                info.setStyle("-fx-fill: #90A4AE; -fx-font-size: 12px;");

                Text stato = new Text("Stato: " + r.getStatus());
                stato.setStyle("-fx-fill: " + getColoreStato(r.getStatus()) + "; -fx-font-size: 12px; -fx-font-weight: bold;");

                card.getChildren().addAll(title, details, info, stato);

                if (r.getStatus() == StatoRichiestaScheda.INCORSO) {
                    HBox buttonBox = new HBox(10);

                    Button btnAccetta = new Button("✅ Accetta");
                    btnAccetta.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 6; -fx-cursor: hand;");
                    btnAccetta.setOnAction(e -> {
                        richiestaController.aggiornaStatoRichiesta(r.getIdRichiesta(), StatoRichiestaScheda.ACCETTATA);
                        caricaRichieste();
                    });

                    Button btnRifiuta = new Button("❌ Rifiuta");
                    btnRifiuta.setStyle("-fx-background-color: #EF5350; -fx-text-fill: white; -fx-background-radius: 6; -fx-cursor: hand;");
                    btnRifiuta.setOnAction(e -> {
                        richiestaController.aggiornaStatoRichiesta(r.getIdRichiesta(), StatoRichiestaScheda.RIFIUTATA);
                        caricaRichieste();
                    });

                    buttonBox.getChildren().addAll(btnAccetta, btnRifiuta);
                    card.getChildren().add(buttonBox);
                }

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