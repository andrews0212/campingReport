package org.example.camping2.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.memoria.Memoria;

/**
 * Controller for the "Eliminar Cliente" (Delete Client) view.
 * This class handles the logic for deleting a client from the system based on the client's ID.
 * It interacts with the memory service (Memoria) to perform the deletion and updates the user interface with appropriate status messages.
 *
 * <p>It handles the following:
 * <ul>
 *   <li>Validates the client ID input.</li>
 *   <li>Attempts to delete the client and shows a success or error message.</li>
 *   <li>Handles errors, such as invalid IDs or database connection issues.</li>
 * </ul>
 * </p>
 *
 * @author Andrews Dos Ramos
 * @version 1.0.0
 * @since 31/01/2025
 */
public class EliminarClienteController {

    @FXML
    private TextField idClienteTextField;  // Text field for entering the client ID to delete

    @FXML
    private Label statusLabel;  // Label for showing the status of the deletion operation

    private Memoria<Cliente, Integer> memoria;  // Memory service for handling client data

    /**
     * Attempts to delete the client based on the provided client ID.
     * If the ID is empty, an error message is displayed.
     * If the ID is valid but the client does not exist, a failure message is shown.
     * If an error occurs during deletion, a generic error message is shown.
     */
    public void eliminarCliente() {
        String idCliente = idClienteTextField.getText();

        if (idCliente.isEmpty()) {
            statusLabel.setText("Por favor, ingrese un ID válido.");
            return;
        }

        try {
            // Simulate the deletion logic (replace with actual logic to remove the client)
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

    /**
     * Attempts to delete a client from the database (or memory).
     * If the client is successfully deleted, an informational alert is shown to the user.
     * If there is an error, an error alert is displayed.
     *
     * @param id The ID of the client to delete.
     * @return true if the client was deleted successfully, false otherwise.
     */
    private boolean eliminarClienteDeBaseDatos(int id) {
        try {
            boolean valor = memoria.delete(id);  // Try to delete the client from memory (simulating database)
            if (valor) {
                System.out.println("Cliente con ID " + id + " eliminado (simulado).");
                // If deleted successfully, show a success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText(null);
                alert.setContentText("Cliente con ID " + id + " eliminado correctamente.");
                alert.showAndWait();
                return true;  // Client deleted successfully
            } else {
                return false;  // Client not found
            }
        } catch (Exception e) {
            // Catch other possible errors and show an error alert
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Ocurrió un error inesperado al intentar eliminar el cliente.");
            alert.showAndWait();
            return false;  // General error
        }
    }

    /**
     * Sets the memory service that manages the client data.
     * This is used to interact with the memory and perform deletion operations.
     *
     * @param memoria The memory service for client data.
     */
    public void setMemoria(Memoria<Cliente, Integer> memoria) {
        this.memoria = memoria;
    }
}
