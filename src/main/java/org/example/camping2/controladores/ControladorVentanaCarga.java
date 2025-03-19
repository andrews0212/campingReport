package org.example.camping2.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.camping2.modelo.dto.Usuario;
import org.example.camping2.modelo.memoria.Memoria;

import javax.swing.*;

/**
 * Controller class responsible for managing the loading window of the application.
 * It simulates a background loading task with a progress bar and transitions to a new window once the loading is complete.
 * The class also handles memory initialization and error handling during the loading process.
 *
 * @author Andrews Dos Ramos
 * @version 1.0.0
 * @since 31/01/2025
 */
public class ControladorVentanaCarga implements Initializable {

    @FXML
    private ProgressBar barraProgreso;  // Progress bar to indicate loading progress
    private Memoria<Usuario, Integer> memoria;  // Memory service for user data

    /**
     * Default constructor for the controller.
     */
    public ControladorVentanaCarga() {
        // No initialization needed in the constructor
    }

    /**
     * Starts the loading task by simulating progress through a background thread.
     * The progress bar is updated while performing a memory initialization task.
     * Once the loading reaches 100%, the controller transitions to a new window.
     */
    public void cargarBarra() {
        // Create a new thread to simulate background loading task
        Thread tarea = new Thread(() -> {
            try {
                // Simulate initial progress
                for (int i = 0; i <= 50; i++) {
                    Thread.sleep(50);  // Simulate task delay
                    final double progreso = i / 100.0;
                    Platform.runLater(() -> barraProgreso.setProgress(progreso));  // Update progress bar
                }

                // Initialize memory (simulate time-consuming task)
                memoria = new Memoria<>(Usuario.class);

                // Simulate additional progress after initializing memory
                for (int i = 51; i <= 100; i++) {
                    Thread.sleep(50);
                    final double progreso = i / 100.0;
                    Platform.runLater(() -> barraProgreso.setProgress(progreso));
                }

                // When progress reaches 100%, open a new window
                Platform.runLater(this::abrirNuevaVentana);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                // Handle any errors and display an error message
                Platform.runLater(() -> {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);  // Exit the application on error
                });
            }
        });

        // Start the background task as a daemon thread
        tarea.setDaemon(true);
        tarea.start();
    }

    /**
     * Opens a new window after the loading process is completed.
     * The method loads a new FXML file, sets up the controller, and shows the new scene.
     *
     * @throws Exception If there is an error loading the new window or scene.
     */
    private void abrirNuevaVentana() {
        try {
            // Load the content for the new window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/VentanaLogin.fxml"));
            Parent root = loader.load();

            // Access the controller of the newly loaded FXML
            ControladorInicio controlador = loader.getController();

            // Pass the memory instance to the controller
            controlador.setMemoria(memoria);

            // Create a new scene and set it to the stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("EcoVenture");
            stage.setResizable(false);
            URL url = getClass().getResource("/org/example/camping2/logo.png");
            Image icon = new Image(url.toString());
            ImageView imageView = new ImageView(icon);
            imageView.setFitWidth(32); // Establecer el ancho del ícono
            imageView.setFitHeight(32); // Establecer la altura del ícono
            stage.getIcons().add(imageView.getImage());
            stage.show();

            // Close the current window (optional)
            Stage ventanaActual = (Stage) barraProgreso.getScene().getWindow();
            ventanaActual.close();

        } catch (Exception e) {
            e.printStackTrace();
            // Show error message if there is an issue opening the new window
            JOptionPane.showMessageDialog(null, "Error al abrir la nueva ventana: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Initializes the controller and starts the loading task when the window is loaded.
     * This method is automatically called by JavaFX after the FXML file is loaded.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarBarra();  // Call the method to start the loading process
    }
}
