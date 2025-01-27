package org.example.camping2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.example.camping2.dto.Cliente;
import org.example.camping2.dto.Usuario;
import org.example.camping2.memoria.Memoria;

import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.util.Optional;

public class HelloController {

    private Memoria<Usuario, Integer> memoria;

    @FXML
    private TextField textFieldUsuario;
    @FXML
    private PasswordField textFieldContraseña;
    // Constructor sin parámetros
    public HelloController() {
        // No hacer nada aquí, JavaFX lo necesita vacío
    }


    // Método para configurar la memoria
    public void setMemoria(Memoria<Usuario, Integer> memoria) {
        this.memoria = memoria;
    }

    public void iniciar(javafx.event.ActionEvent actionEvent) throws IOException {

        if (memoria != null) {
            Optional<Usuario> usuarioEncontrado = memoria.lista.stream()
                    .filter(p -> p.getNickname().equals(textFieldUsuario.getText()) && p.getContraseña().equals(textFieldContraseña.getText()))
                    .findFirst();

            if (usuarioEncontrado.isPresent()) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
                    Parent root = loader.load();

                    // Acceder al controlador del FXML cargado
                    ClienteController controladorCliente = loader.getController(); // Asegúrate de usar ClienteController

                    controladorCliente.setMemoria(new Memoria<>(Cliente.class));
                    // Crear una nueva escena y asignarla al stage
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

                    // Cerrar la ventana actual (opcional)
                    Stage ventanaActual = (Stage) textFieldContraseña.getScene().getWindow();
                    ventanaActual.close();

                } catch (IOException e) {
                    e.printStackTrace(); // Esto te dará más detalles del error
                    JOptionPane.showMessageDialog(null, "Error al abrir la nueva ventana: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                // Si no se encuentra el usuario, puedes mostrar un mensaje de error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Usuario o contraseña incorrectos.");
                alert.showAndWait();
            }
        }
    }


}
