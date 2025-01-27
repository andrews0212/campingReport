package org.example.camping2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.camping2.dto.Cliente;
import org.example.camping2.memoria.Memoria;
import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLException;
import java.sql.SQLOutput;

public class EliminarClienteController {

    @FXML
    private TextField idClienteTextField;

    @FXML
    private Label statusLabel;

    private Memoria<Cliente, Integer> memoria;

    public void eliminarCliente() {
        String idCliente = idClienteTextField.getText();

        if (idCliente.isEmpty()) {
            statusLabel.setText("Por favor, ingrese un ID válido.");
            return;
        }

        try {
            // Simula la lógica para eliminar al cliente (reemplaza con tu lógica real)
            boolean eliminado = eliminarClienteDeBaseDatos(Integer.parseInt(idCliente));
            if (eliminado) {
                statusLabel.setStyle("-fx-text-fill: green;");
                statusLabel.setText("Cliente eliminado exitosamente.");
            } else {
                statusLabel.setStyle("-fx-text-fill: red;");
                statusLabel.setText("No se encontró un cliente con ese ID.");
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("El ID debe ser un número válido.");
        } catch (Exception e) {
            statusLabel.setText("Ocurrió un error al eliminar el cliente.");
        }
    }


    private boolean eliminarClienteDeBaseDatos(int id) {
        try {
            boolean valor = memoria.delete(id);// Intentamos eliminar el cliente de la memoria (base de datos)
            if (valor) {
                System.out.println("Cliente con ID " + id + " eliminado (simulado).");
                // Si se eliminó correctamente, mostramos un mensaje de éxito
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText(null);
                alert.setContentText("Cliente con ID " + id + " eliminado correctamente.");
                alert.showAndWait();
                return true;  // Cliente eliminado correctamente
            } else {
                return false;
            }

    } catch (Exception e) {
        // Captura otros posibles errores y muestra una alerta genera
            System.out.println(e.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Ocurrió un error inesperado al intentar eliminar el cliente.");
        alert.showAndWait();
        return false;  // Error general
    }
    }



    public void setMemoria(Memoria<Cliente, Integer> memoria) {
        this.memoria = memoria;
    }
}