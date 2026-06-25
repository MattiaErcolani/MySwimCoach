package gui;

import bean.CredenzialiBean;
import bean.UtenteLoggatoBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class LoginGui {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button loginButton;
    @FXML private Hyperlink registerLink;

    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Compila tutti i campi.");
            return;
        }

        try {
            CredenzialiBean credenziali = new CredenzialiBean(email, password);
            controller.LoginController loginController = new controller.LoginController();
            UtenteLoggatoBean utente = loginController.login(credenziali);

            if (utente != null) {
                if (utente.isIstructor()) {
                    loadSceneIstruttore("/Fxml/homeIstruttore.fxml", utente);
                } else {
                    loadSceneUtente("/Fxml/homeUtente.fxml", utente);
                }
            } else {
                errorLabel.setText("Email o password errati.");
            }

        } catch (Exception e) {
            errorLabel.setText("Errore durante il login: " + e.getMessage());
        }
    }

    @FXML
    private void goToRegistrazione() {
        try {
            loadScene("/Fxml/registrazione.fxml");
        } catch (Exception e) {
            errorLabel.setText("Errore nel caricamento della schermata.");
        }
    }

    private void loadScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace()
;
            errorLabel.setText("Errore nel caricamento: " + e.getMessage());
        }
    }

    private void loadSceneIstruttore(String fxmlPath, UtenteLoggatoBean utente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            HomeIstruttoreGui controller = loader.getController();
            controller.setIstruttore(utente);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace()
;
            errorLabel.setText("Errore nel caricamento: " + e.getMessage());
        }
    }

    private void loadSceneUtente(String fxmlPath, UtenteLoggatoBean utente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            HomeUtenteGui controller = loader.getController();
            controller.setUtente(utente);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace()
;
            errorLabel.setText("Errore nel caricamento: " + e.getMessage());
        }
    }

}