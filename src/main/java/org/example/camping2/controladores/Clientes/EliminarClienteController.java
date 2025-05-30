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
public class EliminarClienteController implements IdiomaListener {

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

    /**
     * Initializes the controller and binds the columns of the table to the fields of the `Cliente` class.
     * Also binds the `clientes` observable list to the `clientesTableView`.
     */
    @FXML
    public void initialize() {
        // Bind columns to Cliente fields
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


        // Bind the observable list to the TableView
        clientesTableView.setItems(clientes);
        GestorIdiomas.agregarListener(this);
        actualizarTexto();

    }

    public void buscarClientes() {
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
            } else {
                clientes.addAll(resultados);
            }
        } catch (Exception e) {
            statusLabel.setText("Ocurrió un error al buscar los clientes.");
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
            System.out.println("No hay datos para buscar.");
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
            System.out.println("Error al buscar clientes: " + e.getMessage());
        }

        return resultados;
    }


    public void buscarTodosClientes() {
        // Clear previous results and reset status
        clientes.clear();
        statusLabel.setText("");

        try {
            // Simulate database search logic
            ObservableList<Cliente> resultados = FXCollections.observableArrayList();
            ;
            resultados.addAll(memoria.findAll());
            clientes.addAll(resultados);


        } catch (Exception e) {
            statusLabel.setText("Ocurrió un error al buscar los clientes.");
        }
    }

    /**
     * Attempts to delete the client based on the provided client ID.
     * If the ID is empty, an error message is displayed.
     * If the ID is valid but the client does not exist, a failure message is shown.
     * If an error occurs during deletion, a generic error message is shown.
     */
    @FXML
    public void eliminarClienteSeleccionado() {
        Cliente clienteSeleccionado = clientesTableView.getSelectionModel().getSelectedItem();

        if (clienteSeleccionado == null) {
            statusLabel.setText("Por favor, seleccione un cliente para eliminar.");
            return;
        }

        try {
            boolean eliminado = eliminarClienteDeBaseDatos(clienteSeleccionado.getId());
            if (eliminado) {
                clientes.remove(clienteSeleccionado); // También quita de la lista observable para refrescar tabla
                statusLabel.setStyle("-fx-text-fill: green;");
                statusLabel.setText("Cliente eliminado exitosamente.");
            } else {
                statusLabel.setStyle("-fx-text-fill: red;");
                statusLabel.setText("No se pudo eliminar el cliente seleccionado.");
            }
        } catch (Exception e) {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("Error al eliminar el cliente seleccionado.");
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
        if (clientes != null && memoria != null) {
            clientes.clear();
            clientes.addAll(memoria.findAll());
        }
    }

    @Override
    public void idiomaCambiado() {
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
}
