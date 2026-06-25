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


public class RegistrazioneIstruttoreGui {

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
        String nome = nomeField.getText().trim();
        String cognome = cognomeField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String ageText = ageField.getText().trim();
        String certificate = certificateField.getText().trim();

        if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || password.isEmpty() || ageText.isEmpty() || certificate.isEmpty()) {
            errorLabel.setText("Compila tutti i campi.");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            errorLabel.setText("Età non valida.");
            return;
        }

        CredenzialiBean credenziali = new CredenzialiBean(email, password);
        UtenteLoggatoBean utente = new UtenteLoggatoBean(credenziali, nome, cognome, true);
        utente.setRuolo(true);

        RegistrazioneController controller = new RegistrazioneController();
        try {
            controller.registrazione(utente, age, "", certificate);
        } catch (EmailGiaInUsoException e) {
            errorLabel.setText("Email già in uso.");
            return;
        }

        errorLabel.setStyle("-fx-text-fill: #4CAF50;");
        errorLabel.setText("✅ Registrazione completata!");
    }

    @FXML
    private void goToLogin() {
        loadScene("/Fxml/login.fxml");
    }

    private void loadScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) registratiButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace()
;
        }
    }
}