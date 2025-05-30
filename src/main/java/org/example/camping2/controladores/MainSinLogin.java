package org.example.camping2.controladores;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
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
            Font.loadFont(getClass().getResourceAsStream("/fonts/MYRIADPRO-REGULAR.OTF"), 12);
            Font.loadFont(getClass().getResourceAsStream("/fonts/Helvetica Rounded Bold.otf"), 12);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("EcoVenture");
            URL url = getClass().getResource("/org/example/camping2/logo.png");
            Image icon = new Image(url.toString());
            ImageView imageView = new ImageView(icon);
            imageView.setFitWidth(32); // Establecer el ancho del ícono
            imageView.setFitHeight(32); // Establecer la altura del ícono
            stage.getIcons().add(imageView.getImage());
            stage.setScene(scene);
            stage.setFullScreen(true); // Pantalla completa
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al abrir la nueva ventana: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
