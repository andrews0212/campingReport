package org.example.camping2.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
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

    private AnchorPane raiz; // referencia al AnchorPane principal
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
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Se ha producido un error");
                    alert.setContentText(e.getMessage());

                    // Obtener el Stage desde cualquier nodo visible
                    Stage stage = (Stage) barraProgreso.getScene().getWindow();
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.initOwner(stage);  // Esto hace que el Alert se muestre delante

                    alert.showAndWait();  // Espera hasta que el usuario cierre el alert

                    System.exit(0);  // Solo salir después de mostrar el mensaje
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/presentacion.fxml"));

            Parent pantallaPrincipal = loader.load();
            raiz.getChildren().setAll(pantallaPrincipal);

            ControladorPresentacion controlador = loader.getController();
            controlador.setMemoria(memoria);
            controlador.setRaiz(raiz);

            AnchorPane.setTopAnchor(pantallaPrincipal, 0.0);
            AnchorPane.setBottomAnchor(pantallaPrincipal, 0.0);
            AnchorPane.setLeftAnchor(pantallaPrincipal, 0.0);
            AnchorPane.setRightAnchor(pantallaPrincipal, 0.0);

        } catch (Exception e) {
            e.printStackTrace();
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

    public void setRaiz(AnchorPane raiz) {
        this.raiz = raiz;
    }
}
