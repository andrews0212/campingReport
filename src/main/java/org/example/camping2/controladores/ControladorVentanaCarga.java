package org.example.camping2.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.camping2.modelo.dto.Usuario;
import org.example.camping2.modelo.memoria.Memoria;

import javax.swing.*;

public class ControladorVentanaCarga implements Initializable {

    private static final Logger logger = LogConfig.configurarLogger(ControladorVentanaCarga.class);

    private AnchorPane raiz;
    @FXML
    private ProgressBar barraProgreso;
    private Memoria<Usuario, Integer> memoria;

    public ControladorVentanaCarga() {
    }

    public void cargarBarra() {
        Thread tarea = new Thread(() -> {
            try {
                for (int i = 0; i <= 50; i++) {
                    Thread.sleep(50);
                    final double progreso = i / 100.0;
                    Platform.runLater(() -> barraProgreso.setProgress(progreso));
                }

                memoria = new Memoria<>(Usuario.class);

                for (int i = 51; i <= 100; i++) {
                    Thread.sleep(50);
                    final double progreso = i / 100.0;
                    Platform.runLater(() -> barraProgreso.setProgress(progreso));
                }

                Platform.runLater(this::abrirNuevaVentana);

            } catch (InterruptedException e) {
                logger.log(Level.WARNING, "El hilo de la barra de carga fue interrumpido.", e);
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error crítico durante la inicialización de la aplicación.", e);

                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Se ha producido un error");
                    alert.setContentText(e.getMessage());

                    Stage stage = (Stage) barraProgreso.getScene().getWindow();
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.initOwner(stage);

                    alert.showAndWait();
                    System.exit(0);
                });
            }
        });

        tarea.setDaemon(true);
        tarea.start();
    }

    private void abrirNuevaVentana() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/presentacion.fxml"));
            Parent pantallaPrincipal = loader.load();
            raiz.getChildren().setAll(pantallaPrincipal);

            ControladorPresentacion controlador = loader.getController();
            controlador.setMemoria(memoria);
            controlador.setRaiz(raiz);
            controlador.setLog(logger);

            AnchorPane.setTopAnchor(pantallaPrincipal, 0.0);
            AnchorPane.setBottomAnchor(pantallaPrincipal, 0.0);
            AnchorPane.setLeftAnchor(pantallaPrincipal, 0.0);
            AnchorPane.setRightAnchor(pantallaPrincipal, 0.0);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al cargar la vista 'presentacion.fxml'.", e);
            JOptionPane.showMessageDialog(null, "Error al abrir la nueva ventana: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarBarra();
    }



    public void setRaiz(AnchorPane raiz) {
        this.raiz = raiz;
    }
}