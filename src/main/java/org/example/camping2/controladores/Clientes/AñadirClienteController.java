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
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private Logger logger;
    private Memoria<Cliente, Integer> memoria;

    @FXML private Label labelAgregar;
    @FXML private Label labelNombre;
    @FXML private Label labelApellido;
    @FXML private Label labelDNI;
    @FXML private Label labelEmail;
    @FXML private Label labelTelefono;
    @FXML private Label labelFechaNacimiento;
    @FXML private Label labelEstado;

    @FXML private TextField nombreField;
    @FXML private TextField apellidoField;
    @FXML private TextField dniField;
    @FXML private TextField emailField;
    @FXML private TextField telefonoField;
    @FXML private DatePicker fechaNacimientoField;
    @FXML private Button añadirClienteButton;
    @FXML private ComboBox estadoCombo;
    private Map<String, String> mapaEstadoTraducido;

    @FXML
    private void initialize() {

        mapaEstadoTraducido = new HashMap<>();
        actualizarTexto();

    }

    @FXML
    private void handleAñadirCliente(MouseEvent event) {
        logger.info("Intentando añadir un nuevo cliente...");

        if (ValidarCliente.ValidarNombre(nombreField.getText())) {
            logger.warning("Validación fallida: Nombre inválido -> " + nombreField.getText());
            mostrarAlerta("Error", "El nombre es invalido", Alert.AlertType.ERROR);
            return;
        }

        if (ValidarCliente.ValidarApellido(apellidoField.getText())) {
            logger.warning("Validación fallida: Apellido inválido -> " + apellidoField.getText());
            mostrarAlerta("Error", "El apellido es invalido", Alert.AlertType.ERROR);
            return;
        }

        if (ValidarCliente.ValidarDNIoNIE(dniField.getText())) {
            logger.warning("Validación fallida: DNI inválido -> " + dniField.getText());
            mostrarAlerta("Error", "El dni es invalido", Alert.AlertType.ERROR);
            return;
        }

        if (ValidarCliente.ValidarCorreo(emailField.getText())) {
            logger.warning("Validación fallida: Email inválido -> " + emailField.getText());
            mostrarAlerta("Error", "El correo es invalido", Alert.AlertType.ERROR);
            return;
        }

        if (ValidarCliente.ValidarTelefono(telefonoField.getText())) {
            logger.warning("Validación fallida: Teléfono inválido -> " + telefonoField.getText());
            mostrarAlerta("Error", "El telefono es invalido", Alert.AlertType.ERROR);
            return;
        }

        if (fechaNacimientoField.getValue() == null) {
            logger.warning("Validación fallida: Fecha de nacimiento no seleccionada.");
            mostrarAlerta("Error", "La fecha de nacimiento es invalida", Alert.AlertType.ERROR);
            return;
        }

        try {
            String estadoTraducido = estadoCombo.getSelectionModel().getSelectedItem().toString();
            String estadoClave = mapaEstadoTraducido.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(estadoTraducido))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse("ACTIVO");

            Cliente cliente = new Cliente(
                    nombreField.getText(),
                    apellidoField.getText(),
                    dniField.getText(),
                    emailField.getText(),
                    telefonoField.getText(),
                    fechaNacimientoField.getValue(),
                    estadoClave,
                    ""
            );

            memoria.add(cliente);
            logger.info("Cliente añadido exitosamente: " + cliente.toString());

            mostrarAlerta("Éxito", "El cliente ha sido añadido correctamente", Alert.AlertType.INFORMATION);
            limpiarCampos();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al añadir cliente: ", e);
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);

        Stage stage = (Stage) nombreField.getScene().getWindow();
        alerta.initOwner(stage);
        alerta.initModality(Modality.WINDOW_MODAL);
        alerta.show();
        logger.info("Mostrando alerta - Tipo: " + tipo + ", Título: " + titulo + ", Mensaje: " + mensaje);
    }

    private void limpiarCampos() {
        nombreField.clear();
        apellidoField.clear();
        dniField.clear();
        emailField.clear();
        telefonoField.clear();
        fechaNacimientoField.setValue(null);
        logger.info("Campos del formulario limpiados.");
    }

    public void setMemoria(Memoria<Cliente, Integer> memoria) {
        this.memoria = memoria;
        logger.info("Memoria establecida en AñadirClienteController.");
    }

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
        logger.info("Idioma cambiado y textos actualizados en AñadirClienteController.");
    }

    private void actualizarTexto() {
        labelAgregar.setText(GestorIdiomas.getTexto("labelAgregar"));
        labelNombre.setText(GestorIdiomas.getTexto("nombre"));
        labelApellido.setText(GestorIdiomas.getTexto("apellido"));
        labelDNI.setText(GestorIdiomas.getTexto("dni"));
        labelEmail.setText(GestorIdiomas.getTexto("email"));
        labelTelefono.setText(GestorIdiomas.getTexto("telefono"));
        labelFechaNacimiento.setText(GestorIdiomas.getTexto("fechaNacimiento"));
        labelEstado.setText(GestorIdiomas.getTexto("estado"));
        añadirClienteButton.setText(GestorIdiomas.getTexto("addCliente"));

        mapaEstadoTraducido.clear();
        mapaEstadoTraducido.put("ACTIVO", GestorIdiomas.getTexto("ACTIVO"));
        mapaEstadoTraducido.put("BLOQUEADO", GestorIdiomas.getTexto("BLOQUEADO"));
        mapaEstadoTraducido.put("SUSPENDIDO", GestorIdiomas.getTexto("SUSPENDIDO"));

        ObservableList<String> traducidos = FXCollections.observableArrayList(mapaEstadoTraducido.values());
        estadoCombo.setItems(traducidos);
        estadoCombo.getSelectionModel().selectFirst();
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
        logger.info("AñadirClienteController inicializado correctamente.");
    }
}
