package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class RegistrazioneGui {

    private static final Logger logger = Logger.getLogger(RegistrazioneGui.class.getName());

    @FXML private Button btnUtente;
    @FXML private Button btnIstruttore;
    @FXML private Hyperlink loginLink;

    @FXML
    private void goToRegistrazioneUtente() {
        loadScene("/Fxml/registrazioneUtente.fxml");
    }

    @FXML
    private void goToRegistrazioneIstruttore() {
        loadScene("/Fxml/registrazioneIstruttore.fxml");
    }

    @FXML
    private void goToLogin() {
        loadScene("/Fxml/login.fxml");
    }

    private void loadScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) btnUtente.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }
}