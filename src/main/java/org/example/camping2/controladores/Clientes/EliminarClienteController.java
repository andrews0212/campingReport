package org.example.camping2.controladores.Clientes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.memoria.Memoria;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the "Eliminar Cliente" (Delete Client) view.
 * ... [doc unchanged] ...
 */
public class EliminarClienteController implements IdiomaListener {

    private Logger logger;

    @FXML
    private Label labelTitulo, labelId, labelNombre, labelDNI, labelApellido, labelTelefono, labelEmail, labelFechaNacimiento, labelEstado;
    @FXML
    private Button buttonBuscar;
    @FXML
    private Button buttonBuscarTodos;
    @FXML
    private TextField idClienteTextField, nombreTextField, dniTextField, apellidoTextField, telefonoTextField, emailTextField, fechaNacimientoTextField, estadoTextField;
    @FXML
    private TableView<Cliente> clientesTableView;
    @FXML
    private TableColumn<Cliente, Integer> idColumn;
    @FXML
    private TableColumn<Cliente, String> nombreColumn;
    @FXML
    private TableColumn<Cliente, String> apellidoColumn;
    @FXML
    private TableColumn<Cliente, String> dniColumn;
    @FXML
    private TableColumn<Cliente, String> telefonoColumn;
    @FXML
    private TableColumn<Cliente, String> emailColumn;
    @FXML
    private TableColumn<Cliente, String> fechaNacimientoColumn;
    @FXML
    private TableColumn<Cliente, String> estadoColumn;
    @FXML
    private TableColumn<Cliente, String> comentarioColumn;
    @FXML
    private Label statusLabel;

    private ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    private Memoria<Cliente, Integer> memoria;

    @FXML
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
    }

    public void buscarClientes() {
        logger.info("Inicio de busqueda de clientes con filtros");
        clientes.clear();
        statusLabel.setText("");

        try {
            String id = idClienteTextField.getText();
            String nombre = nombreTextField.getText();
            String apellido = apellidoTextField.getText();
            String dni = dniTextField.getText();
            String telefono = telefonoTextField.getText();
            String email = emailTextField.getText();
            String fechaNacimiento = fechaNacimientoTextField.getText();
            String estado = estadoTextField.getText();

            ObservableList<Cliente> resultados = buscarClientesEnBaseDatos(
                    id, nombre, apellido, dni, telefono, email, fechaNacimiento, estado
            );

            if (resultados.isEmpty()) {
                statusLabel.setText("No se encontraron clientes.");
                logger.info("No se encontraron clientes con los filtros especificados.");
            } else {
                clientes.addAll(resultados);
                logger.info(resultados.size() + " clientes encontrados y cargados en la tabla.");
            }
        } catch (Exception e) {
            statusLabel.setText("Ocurrió un error al buscar los clientes.");
            logger.log(Level.SEVERE, "Error al buscar clientes", e);
        }
    }

    private ObservableList<Cliente> buscarClientesEnBaseDatos(
            String id, String nombre, String apellido, String dni,
            String telefono, String email, String fechaNacimiento, String estado
    ) {
        ObservableList<Cliente> resultados = FXCollections.observableArrayList();

        if (id.isEmpty() && nombre.isEmpty() && apellido.isEmpty() && dni.isEmpty()
                && telefono.isEmpty() && email.isEmpty()
                && fechaNacimiento.isEmpty() && estado.isEmpty()) {
            logger.warning("Intento de búsqueda sin criterios especificados.");
            return resultados;
        }

        try {
            for (Cliente cliente : memoria.findAll()) {
                boolean coincide = true;

                if (!id.isEmpty()) {
                    try {
                        int idNum = Integer.parseInt(id);
                        coincide &= cliente.getId() == idNum;
                    } catch (NumberFormatException e) {
                        coincide = false;
                        logger.warning("ID ingresado no es un número válido: " + id);
                    }
                }

                if (!nombre.isEmpty()) {
                    coincide &= cliente.getNombre().equalsIgnoreCase(nombre);
                }

                if (!apellido.isEmpty()) {
                    coincide &= cliente.getApellido().equalsIgnoreCase(apellido);
                }

                if (!dni.isEmpty()) {
                    coincide &= cliente.getDni().equalsIgnoreCase(dni);
                }

                if (!telefono.isEmpty()) {
                    coincide &= cliente.getTelefono().equalsIgnoreCase(telefono);
                }

                if (!email.isEmpty()) {
                    coincide &= cliente.getEmail().equalsIgnoreCase(email);
                }

                if (!fechaNacimiento.isEmpty()) {
                    coincide &= cliente.getFechaNacimiento().toString().equalsIgnoreCase(fechaNacimiento);
                }

                if (!estado.isEmpty()) {
                    coincide &= cliente.getEstado().equalsIgnoreCase(estado);
                }

                if (coincide) {
                    resultados.add(cliente);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar clientes en la memoria", e);
        }

        return resultados;
    }


    public void buscarTodosClientes() {
        logger.info("Buscando todos los clientes");
        clientes.clear();
        statusLabel.setText("");

        try {
            ObservableList<Cliente> resultados = FXCollections.observableArrayList();
            resultados.addAll(memoria.findAll());
            clientes.addAll(resultados);
            logger.info(resultados.size() + " clientes cargados.");
        } catch (Exception e) {
            statusLabel.setText("Ocurrió un error al buscar los clientes.");
            logger.log(Level.SEVERE, "Error al buscar todos los clientes", e);
        }
    }

    @FXML
    public void eliminarClienteSeleccionado() {
        Cliente clienteSeleccionado = clientesTableView.getSelectionModel().getSelectedItem();

        if (clienteSeleccionado == null) {
            statusLabel.setText("Por favor, seleccione un cliente para eliminar.");
            logger.warning("Intento de eliminar cliente sin selección.");
            return;
        }

        try {
            logger.info("Intentando eliminar cliente con ID: " + clienteSeleccionado.getId());
            boolean eliminado = eliminarClienteDeBaseDatos(clienteSeleccionado.getId());
            if (eliminado) {
                clientes.remove(clienteSeleccionado);
                statusLabel.setStyle("-fx-text-fill: green;");
                statusLabel.setText("Cliente eliminado exitosamente.");
                logger.info("Cliente con ID " + clienteSeleccionado.getId() + " eliminado exitosamente.");
            } else {
                statusLabel.setStyle("-fx-text-fill: red;");
                statusLabel.setText("No se pudo eliminar el cliente seleccionado.");
                logger.warning("No se pudo eliminar cliente con ID: " + clienteSeleccionado.getId());
            }
        } catch (Exception e) {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("Error al eliminar el cliente seleccionado.");
            logger.log(Level.SEVERE, "Error al eliminar cliente con ID " + clienteSeleccionado.getId(), e);
        }
    }

    private boolean eliminarClienteDeBaseDatos(int id) {
        try {
            boolean valor = memoria.delete(id);
            if (valor) {
                logger.info("Cliente con ID " + id + " eliminado (simulado).");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText(null);
                alert.setContentText("Cliente con ID " + id + " eliminado correctamente.");
                alert.showAndWait();
                return true;
            } else {
                logger.warning("No se encontró cliente con ID " + id + " para eliminar.");
                return false;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error inesperado al eliminar cliente con ID " + id, e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Ocurrió un error inesperado al intentar eliminar el cliente.");
            alert.showAndWait();
            return false;
        }
    }

    public void setMemoria(Memoria<Cliente, Integer> memoria) {
        this.memoria = memoria;
        if (clientes != null && memoria != null) {
            clientes.clear();
            clientes.addAll(memoria.findAll());
            logger.info("Memoria establecida y clientes cargados: " + clientes.size());
        }
    }

    @Override
    public void idiomaCambiado() {
        logger.info("Idioma cambiado - actualizando textos");
        actualizarTexto();
    }

    private void actualizarTexto() {
        buttonBuscar.setText(GestorIdiomas.getTexto("buscar"));
        buttonBuscarTodos.setText(GestorIdiomas.getTexto("buscarTodos"));
        labelTitulo.setText(GestorIdiomas.getTexto("buscarCliente"));
        labelId.setText(GestorIdiomas.getTexto("idcliente"));
        labelNombre.setText(GestorIdiomas.getTexto("nombre"));
        labelDNI.setText(GestorIdiomas.getTexto("dni"));
        labelApellido.setText(GestorIdiomas.getTexto("apellido"));
        labelTelefono.setText(GestorIdiomas.getTexto("telefono"));
        labelEmail.setText(GestorIdiomas.getTexto("email"));
        labelFechaNacimiento.setText(GestorIdiomas.getTexto("fechaNacimiento"));
        labelEstado.setText(GestorIdiomas.getTexto("estado"));

        idColumn.setText(GestorIdiomas.getTexto("idcliente"));
        nombreColumn.setText(GestorIdiomas.getTexto("nombre"));
        dniColumn.setText(GestorIdiomas.getTexto("dni"));
        apellidoColumn.setText(GestorIdiomas.getTexto("apellido"));
        telefonoColumn.setText(GestorIdiomas.getTexto("telefono"));
        emailColumn.setText(GestorIdiomas.getTexto("email"));
        fechaNacimientoColumn.setText(GestorIdiomas.getTexto("fechaNacimiento"));
        estadoColumn.setText(GestorIdiomas.getTexto("estado"));
        comentarioColumn.setText(GestorIdiomas.getTexto("comentario"));
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
