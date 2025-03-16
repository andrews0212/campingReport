package org.example.camping2.controladores;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Main application class for the Camping Management System.
 * This class serves as the entry point for launching the JavaFX application.
 * It is responsible for loading the initial FXML view and setting up the main stage (window) for the application.
 *
 * <p>The application starts by loading the main FXML file (PantallaCarga.fxml), which contains the initial user interface,
 * and displays it in a window (Stage).</p>
 *
 * @author Andrews Dos Ramos
 * @version 1.0.0
 * @since 31/01/2025
 */
public class ControladorPantallaCarga extends Application {

    /**
     * The main entry point for starting the JavaFX application.
     * This method loads the initial FXML file, creates a scene, and shows the main stage.
     *
     * @param stage The primary stage for the application, to be shown on the screen.
     * @throws IOException If there is an issue loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML file for the initial UI (PantallaCarga.fxml)
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/camping2/PantallaCarga.fxml"));

        // Create the scene from the loaded FXML and set it in the stage
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("EcoVenture");
        URL url = getClass().getResource("/org/example/camping2/logo.png");
        Image icon = new Image(url.toString());
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(32); // Establecer el ancho del ícono
        imageView.setFitHeight(32); // Establecer la altura del ícono
        stage.getIcons().add(imageView.getImage());

        stage.setScene(scene);

        // Show the main stage
        stage.show();
    }

    /**
     * Main method to launch the JavaFX application.
     * This method triggers the launch of the application by calling the launch() method of the Application class.
     *
     * @param args Command-line arguments (unused in this case).
     */
    public static void main(String[] args) {
        launch();
    }
}
