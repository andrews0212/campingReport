package org.example.camping2.controladores;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.camping2.controladores.Clientes.ClienteController;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.memoria.Memoria;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

public class MainSinLogin extends Application {
//87654321B

    public static void main(String[] args) {
        System.setProperty("prism.order", "sw"); // Forzar renderizado por software
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        try {

            // Cargar la nueva ventana (Menu.fxml) si el usuario es autenticado
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/Menu.fxml"));
            Parent root = loader.load();
            // Obtener el controlador de la nueva ventana y configurar la memoria de clientes
            ClienteController controladorCliente = loader.getController();
            controladorCliente.setMemoriaCliente(new Memoria<>(Cliente.class));
            // Crear y mostrar la nueva escena
            stage = new Stage();
            stage.setTitle("EcoVenture");
            URL url = getClass().getResource("/org/example/camping2/logo.png");
            Image icon = new Image(url.toString());
            ImageView imageView = new ImageView(icon);
            imageView.setFitWidth(32); // Establecer el ancho del ícono
            imageView.setFitHeight(32); // Establecer la altura del ícono
            stage.getIcons().add(imageView.getImage());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al abrir la nueva ventana: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
