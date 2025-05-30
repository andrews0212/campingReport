package org.example.camping2.controladores.Clientes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.memoria.Memoria;
import org.example.camping2.modelo.validaciones.ValidarCliente;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller class responsible for handling the "Add Client" UI in the camping application.
 * This class manages the client form for adding new clients to the system. It interacts with
 * the `Memoria` service to store client data.
 *
 * @author Andrews Dos Ramos
 * @version 1.0.0
 * @since 31/01/2025
 */
public class AñadirClienteController implements IdiomaListener {

    private Memoria<Cliente, Integer> memoria;

    @FXML
    private Label labelAgregar;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelApellido;
    @FXML
    private Label labelDNI;
    @FXML
    private Label labelEmail;
    @FXML
    private Label labelTelefono;
    @FXML
    private Label labelFechaNacimiento;
    @FXML
    private Label labelEstado;

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
    private Button añadirClienteButton;

    @FXML
    private ComboBox estadoCombo;
    private Map<String, String> mapaEstadoTraducido;

    /**
     * Initializes the controller, printing a placeholder message to indicate proper initialization.
     */
    @FXML
    private void initialize() {

        mapaEstadoTraducido = new HashMap<>();
        mapaEstadoTraducido.clear();
        mapaEstadoTraducido.put("ACTIVO", GestorIdiomas.getTexto("ACTIVO"));
        mapaEstadoTraducido.put("BLOQUEADO", GestorIdiomas.getTexto("BLOQUEADO"));
        mapaEstadoTraducido.put("SUSPENDIDO", GestorIdiomas.getTexto("SUSPENDIDO"));
        ObservableList<String> traducciones = FXCollections.observableArrayList(mapaEstadoTraducido.values());
        estadoCombo.setItems(traducciones);
        estadoCombo.getSelectionModel().selectFirst();
        GestorIdiomas.agregarListener(this);
        actualizarTexto();
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
        if (ValidarCliente.ValidarNombre(nombreField.getText())){
            mostrarAlerta("Error", "El nombre es invalido", Alert.AlertType.ERROR);
        } else if (ValidarCliente.ValidarApellido(apellidoField.getText())) {
            mostrarAlerta("Error", "El apellido es invalido", Alert.AlertType.ERROR);
        } else if (ValidarCliente.ValidarDNIoNIE(dniField.getText())) {
            mostrarAlerta("Error", "El dni es invalido", Alert.AlertType.ERROR);
        } else if (ValidarCliente.ValidarCorreo(emailField.getText())) {
            mostrarAlerta("Error", "El correo es invalido", Alert.AlertType.ERROR);
        } else if (ValidarCliente.ValidarTelefono(telefonoField.getText())) {
            mostrarAlerta("Error", "El telefono es invalido", Alert.AlertType.ERROR);
        } else if (fechaNacimientoField.getValue() == null) {
            mostrarAlerta("Error", "La fecha de nacimiento es invalida", Alert.AlertType.ERROR);
        } else {
            try {

                String estadoTraducido = estadoCombo.getSelectionModel().getSelectedItem().toString();
                String estadoClave = mapaEstadoTraducido.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(estadoTraducido))
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .orElse("ACTIVO"); // Valor por defecto en caso de error

            // Create a new client object with the form data
            Cliente cliente = new Cliente(
                    nombreField.getText(),
                    apellidoField.getText(),
                    dniField.getText(),
                    emailField.getText(),
                    telefonoField.getText(),
                    fechaNacimientoField.getValue(),
                    estadoClave,""
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

        // Obtener el Stage actual y asignarlo como propietario
        Stage stage = (Stage) nombreField.getScene().getWindow(); // cualquier nodo sirve
        alerta.initOwner(stage);
        alerta.initModality(Modality.WINDOW_MODAL);  // Importante: no usar APPLICATION_MODAL

        alerta.show(); // No bloqueante
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
    }

    /**
     * Sets the `Memoria` service for storing the clients.
     *
     * @param memoria The memory service that will store the clients.
     */
    public void setMemoria(Memoria<Cliente, Integer> memoria) {
        this.memoria = memoria;
    }

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }
    private void actualizarTexto(){
        labelAgregar.setText(GestorIdiomas.getTexto("labelAgregar"));
        labelNombre.setText(GestorIdiomas.getTexto("nombre"));
        nombreField.setPromptText(GestorIdiomas.getTexto("nombreField"));
        labelApellido.setText(GestorIdiomas.getTexto("apellido"));
        apellidoField.setPromptText(GestorIdiomas.getTexto("apellidoField"));
        labelDNI.setText(GestorIdiomas.getTexto("dni"));
        dniField.setPromptText(GestorIdiomas.getTexto("dniField"));
        labelEmail.setText(GestorIdiomas.getTexto("email"));
        emailField.setPromptText(GestorIdiomas.getTexto("emailField"));
        labelTelefono.setText(GestorIdiomas.getTexto("telefono"));
        telefonoField.setPromptText(GestorIdiomas.getTexto("telefonoField"));
        labelFechaNacimiento.setText(GestorIdiomas.getTexto("fechaNacimiento"));
        labelEstado.setText(GestorIdiomas.getTexto("estado"));
        añadirClienteButton.setText(GestorIdiomas.getTexto("addCliente"));

        // Traducción de estados
        mapaEstadoTraducido.clear();
        mapaEstadoTraducido.put("ACTIVO", GestorIdiomas.getTexto("ACTIVO"));
        mapaEstadoTraducido.put("BLOQUEADO", GestorIdiomas.getTexto("BLOQUEADO"));
        mapaEstadoTraducido.put("SUSPENDIDO", GestorIdiomas.getTexto("SUSPENDIDO"));

        // Mostrar solo los valores traducidos en el ComboBox
        ObservableList<String> traducidos = FXCollections.observableArrayList(mapaEstadoTraducido.values());
        estadoCombo.setItems(traducidos);
        estadoCombo.getSelectionModel().selectFirst();
    }


}
