package org.example.camping2;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.camping2.dto.Cliente;
import org.example.camping2.memoria.Memoria;

import java.io.IOException;
public class TestControlleCliente {
    public static void main(String[] args) {
        try {
            FXMLLoader loader = new FXMLLoader(TestControlleCliente.class.getResource("/org/example/camping2/Menu.fxml"));


            // Acceder al controlador del FXML cargado
            ClienteController controladorCliente = loader.getController();
            controladorCliente.setMemoria(new Memoria<>(Cliente.class));

            // Crear una nueva escena y asignarla al stage
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar el archivo FXML.");
        }
    }
}
