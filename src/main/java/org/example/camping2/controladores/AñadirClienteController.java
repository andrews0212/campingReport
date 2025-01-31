package org.example.camping2.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.camping2.dto.Cliente;
import org.example.camping2.memoria.Memoria;

/**
 * Controller class responsible for handling the "Add Client" UI in the camping application.
 * This class manages the client form for adding new clients to the system. It interacts with
 * the `Memoria` service to store client data.
 *
 * @author Andrews Dos Ramos
 * @version 1.0.0
 * @since 31/01/2025
 */
public class AñadirClienteController {

    private Memoria<Cliente, Integer> memoria;

    @FXML
    private TextField nombreField;

    @FXML
    private TextField apellidoField;

    @FXML
    private TextField dniField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField telefonoField;

    @FXML
    private DatePicker fechaNacimientoField;

    @FXML
    private TextField estadoField;

    @FXML
    private Button añadirClienteButton;

    /**
     * Initializes the controller, printing a placeholder message to indicate proper initialization.
     */
    @FXML
    private void initialize() {
        // Placeholder to confirm controller initialization
        System.out.println("Controlador inicializado correctamente");
    }

    /**
     * Handles the "Add Client" button click event.
     * This method collects the data from the form, creates a `Cliente` object,
     * and attempts to add it to the memory.
     * If successful, it shows a success alert; otherwise, it shows an error alert.
     *
     * @param event The mouse event that triggered the action.
     */
    @FXML
    private void handleAñadirCliente(MouseEvent event) {
        try {
            // Create a new client object with the form data
            Cliente cliente = new Cliente(
                    nombreField.getText(),
                    apellidoField.getText(),
                    dniField.getText(),
                    emailField.getText(),
                    telefonoField.getText(),
                    fechaNacimientoField.getValue(),
                    estadoField.getText(),
                    ""
            );

            // Try to add the client to memory
            memoria.add(cliente);

            // Show success alert
            mostrarAlerta("Éxito", "El cliente ha sido añadido correctamente", Alert.AlertType.INFORMATION);
            limpiarCampos();
        } catch (Exception e) {
            // Show error alert
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Displays an alert with the given title, message, and alert type.
     *
     * @param titulo The title of the alert.
     * @param mensaje The message to display in the alert.
     * @param tipo The type of the alert (e.g., INFORMATION, ERROR).
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Clears the form fields after a successful client addition.
     */
    private void limpiarCampos() {
        nombreField.clear();
        apellidoField.clear();
        dniField.clear();
        emailField.clear();
        telefonoField.clear();
        fechaNacimientoField.setValue(null);
        estadoField.clear();
    }

    /**
     * Sets the `Memoria` service for storing the clients.
     *
     * @param memoria The memory service that will store the clients.
     */
    public void setMemoria(Memoria<Cliente, Integer> memoria) {
        this.memoria = memoria;
    }

    // Additional methods or code for the controller can go here
}
