package org.example.camping2.controladores.Clientes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.memoria.Memoria;
import org.example.camping2.modelo.validaciones.ValidarCliente;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModificarClienteController implements IdiomaListener {

    private Logger logger;

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @FXML
    private TextField idClienteTextField, nombreTextField, dniTextField, apellidoTextField, telefonoTextField, emailTextField, estadoTextField;
    @FXML
    private TextField idClienteTextField1, nombreTextField1, dniTextField1, apellidoTextField1, telefonoTextField1, emailTextField1, estadoTextField1;
    @FXML
    private DatePicker fechaNacimientoDate, fechaNacimientoDate1;
    @FXML
    private TextArea comentariosTextArea;
    @FXML
    private Label statusLabel, labelId, labelNombre, labelApellido, labelDNI, labelEmail, labelTelefono, labelEstado, textTitulo;
    @FXML
    private Button buttonBuscar, buttonBuscarTodos, btnGuardar;
    @FXML
    private TableView<Cliente> clientesTableView;
    @FXML
    private TableColumn<Cliente, Integer> idColumn;
    @FXML
    private TableColumn<Cliente, String> nombreColumn, apellidoColumn, dniColumn, telefonoColumn, emailColumn, fechaNacimientoColumn, estadoColumn, comentarioColumn;

    private ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    private Memoria<Cliente, Integer> memoria;

    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        nombreColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        apellidoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getApellido()));
        dniColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDni()));
        telefonoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTelefono()));
        emailColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        fechaNacimientoColumn.setCellValueFactory(cellData -> {
            LocalDate fecha = cellData.getValue().getFechaNacimiento();
            return new javafx.beans.property.SimpleStringProperty(fecha != null ? fecha.toString() : "");
        });
        estadoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEstado()));
        comentarioColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getComentarios()));
        clientesTableView.setItems(clientes);
        GestorIdiomas.agregarListener(this);
        actualizarTexto();
        clientesTableView.getSelectionModel().selectedItemProperty().addListener((obs, antiguo, nuevo) -> {
            if (nuevo != null) {
                rellenarCampos(nuevo);
            }
        });
        if (logger != null) logger.info("ModificarClienteController inicializado.");
    }

    public void buscarClientes() {
        clientes.clear();
        statusLabel.setText("");

        try {
            ObservableList<Cliente> resultados = buscarClientesEnBaseDatos(
                    idClienteTextField.getText(),
                    nombreTextField.getText(),
                    apellidoTextField.getText(),
                    dniTextField.getText(),
                    telefonoTextField.getText(),
                    emailTextField.getText(),
                    fechaNacimientoDate.getValue() != null ? fechaNacimientoDate.getValue().toString() : "",
                    estadoTextField.getText()
            );

            if (resultados.isEmpty()) {
                statusLabel.setText("No se encontraron clientes.");
                if (logger != null) logger.info("No se encontraron clientes con los criterios dados.");
            } else {
                clientes.addAll(resultados);
                if (logger != null) logger.info("Clientes encontrados: " + resultados.size());
            }
        } catch (Exception e) {
            statusLabel.setText("Ocurrió un error al buscar los clientes.");
            if (logger != null) logger.log(Level.SEVERE, "Error al buscar clientes", e);
        }
    }

    public void guardarCambios() {
        String idCliente = idClienteTextField1.getText();
        if (idCliente.isEmpty()) {
            statusLabel.setText("Por favor, busque un cliente primero.");
            return;
        }

        try {
            if (ValidarCliente.ValidarNombre(nombreTextField1.getText())) {
                logger.warning("Validación fallida: Nombre inválido -> " + nombreTextField1.getText());
                mostrarAlerta("Error", "El nombre es invalido", Alert.AlertType.ERROR);
                return;
            } else if (ValidarCliente.ValidarApellido(apellidoTextField1.getText())) {
                logger.warning("Validación fallida: Apellido inválido -> " + apellidoTextField1.getText());
                mostrarAlerta("Error", "El apellido es invalido", Alert.AlertType.ERROR);
                return;
            } else if (ValidarCliente.ValidarDNIoNIE(dniTextField1.getText())) {
                logger.warning("Validación fallida: DNI inválido -> " + dniTextField1.getText());
                mostrarAlerta("Error", "El dni es invalido", Alert.AlertType.ERROR);
                return;
            } else if (ValidarCliente.ValidarCorreo(emailTextField1.getText())) {
                logger.warning("Validación fallida: Email inválido -> " + emailTextField1.getText());
                mostrarAlerta("Error", "El correo es invalido", Alert.AlertType.ERROR);
                return;
            } else if (ValidarCliente.ValidarTelefono(telefonoTextField1.getText())) {
                logger.warning("Validación fallida: Teléfono inválido -> " + telefonoTextField1.getText());
                mostrarAlerta("Error", "El telefono es invalido", Alert.AlertType.ERROR);
                return;
            } else if (ValidarCliente.ValidarEstado(estadoTextField1.getText())) {
                logger.warning("Validación fallida: Estado inválido -> " + estadoTextField1.getText());
                mostrarAlerta("Error", "El estado es invalido", Alert.AlertType.ERROR);
                return;
            }

            Cliente cliente = new Cliente(
                    nombreTextField1.getText(),
                    apellidoTextField1.getText(),
                    dniTextField1.getText(),
                    emailTextField1.getText(),
                    telefonoTextField1.getText(),
                    fechaNacimientoDate1.getValue(),
                    estadoTextField1.getText(),
                    comentariosTextArea.getText()
            );

            boolean actualizado = actualizarClienteEnBaseDatos(Integer.parseInt(idCliente), cliente);
            if (actualizado) {
                for (int i = 0; i < clientes.size(); i++) {
                    if (clientes.get(i).getId() == Integer.parseInt(idCliente)) {
                        clientes.set(i, cliente);
                        break;
                    }
                }
                statusLabel.setStyle("-fx-text-fill: green;");
                statusLabel.setText("Cliente actualizado exitosamente.");
                if (logger != null) logger.info("Cliente actualizado: ID " + idCliente);
            } else {
                statusLabel.setStyle("-fx-text-fill: red;");
                statusLabel.setText("Error al actualizar el cliente.");
                if (logger != null) logger.warning("No se pudo actualizar el cliente: ID " + idCliente);
            }

        } catch (Exception e) {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("Ocurrió un error al guardar los cambios.");
            if (logger != null) logger.log(Level.SEVERE, "Error al guardar cambios del cliente", e);
        }
    }

    public void buscarTodosClientes() {
        clientes.clear();
        statusLabel.setText("");
        try {
            clientes.addAll(memoria.findAll());
            if (logger != null) logger.info("Todos los clientes cargados.");
        } catch (Exception e) {
            statusLabel.setText("Ocurrió un error al buscar los clientes.");
            if (logger != null) logger.log(Level.SEVERE, "Error al cargar todos los clientes", e);
        }
    }

    private void rellenarCampos(Cliente cliente) {
        idClienteTextField1.setText(String.valueOf(cliente.getId()));
        nombreTextField1.setText(cliente.getNombre());
        apellidoTextField1.setText(cliente.getApellido());
        dniTextField1.setText(cliente.getDni());
        telefonoTextField1.setText(cliente.getTelefono());
        emailTextField1.setText(cliente.getEmail());
        fechaNacimientoDate1.setValue(cliente.getFechaNacimiento());
        estadoTextField1.setText(cliente.getEstado());
        comentariosTextArea.setText(cliente.getComentarios());
    }

    private Cliente buscarClienteEnBaseDatos(int id) {
        return memoria.findById(id);
    }

    private boolean actualizarClienteEnBaseDatos(int id, Cliente cliente) {
        cliente.setId(id);
        return memoria.update(cliente);
    }

    public void setMemoria(Memoria<Cliente, Integer> memoria) {
        this.memoria = memoria;
        if (clientes != null && memoria != null) {
            clientes.clear();
            clientes.addAll(memoria.findAll());
            if (logger != null) logger.info("Memoria establecida y clientes cargados.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        Stage stage = (Stage) nombreTextField.getScene().getWindow();
        alerta.initOwner(stage);
        alerta.initModality(Modality.WINDOW_MODAL);
        alerta.show();
    }

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }

    public void actualizarTexto() {
        labelNombre.setText(GestorIdiomas.getTexto("nombre"));
        labelApellido.setText(GestorIdiomas.getTexto("apellido"));
        labelDNI.setText(GestorIdiomas.getTexto("dni"));
        labelEmail.setText(GestorIdiomas.getTexto("email"));
        labelEstado.setText(GestorIdiomas.getTexto("estado"));
        labelTelefono.setText(GestorIdiomas.getTexto("telefono"));
        labelId.setText(GestorIdiomas.getTexto("id"));
        buttonBuscar.setText(GestorIdiomas.getTexto("buscar"));
        btnGuardar.setText(GestorIdiomas.getTexto("guardar"));
        textTitulo.setText(GestorIdiomas.getTexto("modificarCliente"));
    }

    private ObservableList<Cliente> buscarClientesEnBaseDatos(
            String id, String nombre, String apellido, String dni,
            String telefono, String email, String fechaNacimiento, String estado
    ) {
        ObservableList<Cliente> resultados = FXCollections.observableArrayList();

        for (Cliente cliente : memoria.findAll()) {
            boolean coincide = true;

            if (!id.isEmpty()) {
                try {
                    int idNum = Integer.parseInt(id);
                    coincide &= cliente.getId() == idNum;
                } catch (NumberFormatException e) {
                    coincide = false;
                }
            }
            if (!nombre.isEmpty()) coincide &= cliente.getNombre().equalsIgnoreCase(nombre);
            if (!apellido.isEmpty()) coincide &= cliente.getApellido().equalsIgnoreCase(apellido);
            if (!dni.isEmpty()) coincide &= cliente.getDni().equalsIgnoreCase(dni);
            if (!telefono.isEmpty()) coincide &= cliente.getTelefono().equalsIgnoreCase(telefono);
            if (!email.isEmpty()) coincide &= cliente.getEmail().equalsIgnoreCase(email);
            if (!fechaNacimiento.isEmpty()) coincide &= cliente.getFechaNacimiento() != null && cliente.getFechaNacimiento().toString().equals(fechaNacimiento);
            if (!estado.isEmpty()) coincide &= cliente.getEstado().equalsIgnoreCase(estado);

            if (coincide) resultados.add(cliente);
        }

        return resultados;
    }
}
