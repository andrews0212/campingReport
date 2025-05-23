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

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/Menu.fxml"));
            Parent root = loader.load();
            ClienteController controladorCliente = loader.getController();
            controladorCliente.setMemoriaCliente(new Memoria<>(Cliente.class));
            stage = new Stage();
            stage.setTitle("EcoVenture");
            URL url = getClass().getResource("/org/example/camping2/logo.png");
            Image icon = new Image(url.toString());
            ImageView imageView = new ImageView(icon);
            imageView.setFitWidth(32);
            imageView.setFitHeight(32);
            stage.getIcons().add(imageView.getImage());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al abrir la nueva ventana: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
