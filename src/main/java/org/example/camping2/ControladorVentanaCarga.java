package org.example.camping2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import org.example.camping2.dto.Usuario;
import org.example.camping2.memoria.Memoria;

import javax.swing.*;

public class ControladorVentanaCarga implements Initializable {

    @FXML
    private ProgressBar barraProgreso;
    private Memoria<Usuario, Integer> memoria;

    public ControladorVentanaCarga() {
        //
    }

    public void cargarBarra() {
        // Crear un hilo separado para simular una tarea en segundo plano
        Thread tarea = new Thread(() -> {
            try {
                // Simula el progreso inicial
                for (int i = 0; i <= 50; i++) {
                    Thread.sleep(50);
                    final double progreso = i / 100.0;
                    Platform.runLater(() -> barraProgreso.setProgress(progreso));
                }

                // Inicializa la memoria (simula tiempo de carga pesado)
                memoria = new Memoria<>(Usuario.class);

                // Simula progreso adicional después de inicializar la memoria
                for (int i = 51; i <= 100; i++) {
                    Thread.sleep(50);
                    final double progreso = i / 100.0;
                    Platform.runLater(() -> barraProgreso.setProgress(progreso));
                }

                // Cuando el progreso llega al 100%, cambia de escena
                Platform.runLater(this::abrirNuevaVentana);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                Platform.runLater(() -> {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                });
            }
        });

        // Inicia el hilo
        tarea.setDaemon(true); // Asegura que el hilo se detenga cuando la aplicación se cierra
        tarea.start();
    }


    private void abrirNuevaVentana()  {
        try{
            // Cargar el contenido de la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            // Acceder al controlador del FXML cargado
            HelloController controlador = loader.getController();

            // Pasar la instancia de Memoria al controlador
            controlador.setMemoria(memoria); // Pasa la memoria al controlador

            // Crear una nueva escena y asignarla al stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Nueva Ventana");
            stage.show();

            // Cerrar la ventana actual (opcional)
            Stage ventanaActual = (Stage) barraProgreso.getScene().getWindow();
            ventanaActual.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al abrir la nueva ventana: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Llama directamente al método cargarBarra() al inicializar

            cargarBarra();

    }


}
