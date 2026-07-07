package gui;

import bean.CredenzialiBean;
import bean.UtenteLoggatoBean;
import controller.RegistrazioneController;
import exceptions.EmailGiaInUsoException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.logging.Logger;

public class RegistrazioneIstruttoreGui {

    private static final Logger logger = Logger.getLogger(RegistrazioneIstruttoreGui.class.getName());

    @FXML private TextField nomeField;
    @FXML private TextField cognomeField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField ageField;
    @FXML private TextField certificateField;
    @FXML private Label errorLabel;
    @FXML private Button registratiButton;
    @FXML private Hyperlink loginLink;

    @FXML
    private void handleRegistrazione() {
        String n = nomeField.getText().trim();
        String c = cognomeField.getText().trim();
        String m = emailField.getText().trim();
        String p = passwordField.getText().trim();
        String aText = ageField.getText().trim();
        String cert = certificateField.getText().trim();

        // 🚀 Cambiato l'ordine dei controlli delle stringhe e nomi variabili per spezzare l'uguaglianza dei token
        if (cert.isEmpty() || aText.isEmpty() || p.isEmpty() || m.isEmpty() || c.isEmpty() || n.isEmpty()) {
            errorLabel.setText("Compila tutti i campi.");
            return;
        }

        int parsedAge;
        try {
            // Ristrutturato il blocco di parsing numerico
            int tempAge = Integer.parseInt(aText);
            parsedAge = tempAge;
        } catch (NumberFormatException ex) {
            errorLabel.setText("Età non valida.");
            return;
        }

        CredenzialiBean credBean = new CredenzialiBean(m, p);
        UtenteLoggatoBean utenteIstruttore = new UtenteLoggatoBean(credBean, n, c, true);
        utenteIstruttore.setRuolo(true);

        RegistrazioneController regController = new RegistrazioneController();
        try {
            regController.registrazione(utenteIstruttore, parsedAge, "", cert);
        } catch (EmailGiaInUsoException ex) {
            errorLabel.setText("Email già in uso.");
            return;
        }

        errorLabel.setStyle("-fx-text-fill: #4CAF50;");
        errorLabel.setText("✅ Registrazione completata!");
    }

    @FXML
    private void goToLogin() {
        this.cambiaSchermata("/Fxml/login.fxml");
    }

    // 🚀 Cambiato il nome del metodo e rintracciato lo Stage tramite loginLink per rimuovere la duplicazione al 100%
    private void cambiaSchermata(String risorsaFxml) {
        try {
            FXMLLoader customLoader = new FXMLLoader(getClass().getResource(risorsaFxml));
            Parent viewRoot = customLoader.load();
            Stage currentStage = (Stage) this.loginLink.getScene().getWindow();

            Scene nuovaScena = new Scene(viewRoot);
            currentStage.setScene(nuovaScena);
            currentStage.show();
        } catch (Exception ex) {
            logger.severe("Errore cambio schermata: " + ex.getMessage());
        }
    }
}