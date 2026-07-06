package org.example;

import other.Config;
import other.Connect;
import other.Stampa;
import pattern.StateMachineImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Config.loadFromFile();

        Scanner scanner = new Scanner(System.in);

        boolean validChoice = false;
        while (!validChoice) {
            Stampa.println("Scegli la versione dell'applicazione:");
            Stampa.println("1. MySQL (DAO reale)");
            Stampa.println("2. In Memory (tutti i DAO in memoria)");
            Stampa.println("3. JSON(LOGIN DAO)");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> { Config.setPersistenceType("mysql"); validChoice = true; }
                    case 2 -> { Config.setPersistenceType("memory"); validChoice = true; }
                    case 3 -> { Config.setPersistenceType("json"); validChoice = true; }
                    default -> Stampa.println("Scelta non valida, riprova.");
                }
            } catch (Exception e) {
                Stampa.println("Errore input: " + e.getMessage());
                scanner.nextLine();
            }
        }

        if ("mysql".equalsIgnoreCase(Config.getPersistenceType())) {
            testDatabaseConnection();
        }

        boolean validInput = false;
        while (!validInput) {
            try {
                mostraMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        Stampa.println("ciao");
                        interfacciaGrafica(stage);
                        validInput = true;
                        break;
                    case 2:
                        interfaceCLI();
                        validInput = true;
                        break;
                    default:
                        Stampa.println("Scelta non valida");
                }
            } catch (Exception e) {
                Stampa.errorPrint("Errore: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    public void interfacciaGrafica(Stage stage) throws IOException {
        URL fxmlUrl = Main.class.getResource("/Fxml/login.fxml");
        if (fxmlUrl == null) {
            throw new IOException("❌ File FXML non trovato: /Fxml/login.fxml");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MySwimCoach");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void interfaceCLI() {
        StateMachineImpl context = new StateMachineImpl();
        while (context.getState() != null) {
            context.goNext();
        }
        Stampa.println("Arrivederci");
    }

    public void mostraMenu() {
        Stampa.println(" ");
        Stampa.println("-------------- MySwimCoach --------------");
        Stampa.println("Scegli l'interfaccia da utilizzare:");
        Stampa.println("1. Interfaccia grafica");
        Stampa.println("2. Interfaccia a riga di comando");
    }

    private void testDatabaseConnection() {
        try {
            Connection conn = Connect.getInstance().getDBConnection();
            if (conn != null && !conn.isClosed()) {
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT 1")) {
                    if (rs.next()) {
                        Stampa.println("✅ Connessione al database verificata con successo.");
                    }
                }
            } else {
                Stampa.errorPrint("❌ Connessione al database non disponibile.");
            }
        } catch (Exception e) {
            Stampa.errorPrint("❌ Errore durante il test di connessione: " + e.getMessage());
        }
    }
}