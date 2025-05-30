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
 * Controller class responsible for handling the "Search Client" functionality in the camping application.
 * This class allows users to search for clients based on various criteria (ID, name, or DNI) and displays
 * the results in a table. It interacts with the `Memoria` service to retrieve client data stored in memory.
 *
 * @author Andrews Dos Ramos
 * @version 1.0.0
 * @since 31/01/2025
 */
public class BuscarClientePanelController implements IdiomaListener {

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

    /**
     * Searches for clients based on the provided search criteria (ID, name, and/or DNI).
     * The results are displayed in the table. If no clients are found or if an error occurs, an appropriate
     * message is shown in the `statusLabel`.
     */
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


    public void buscarTodosClientes(){
        // Clear previous results and reset status
        clientes.clear();
        statusLabel.setText("");

        try {
            // Simulate database search logic
            ObservableList<Cliente> resultados = FXCollections.observableArrayList();;
            resultados.addAll(memoria.findAll());
            clientes.addAll(resultados);


        }catch (Exception e){
            statusLabel.setText("Ocurrió un error al buscar los clientes.");
        }
    }

    /**
     * Simulates the search of clients based on the provided ID, name, and DNI.
     * It compares the input values with the data stored in memory and returns a list of matching clients.
     *
     * @param id The ID of the client to search for (optional).
     * @param nombre The name of the client to search for (optional).
     * @param dni The DNI of the client to search for (optional).
     * @return A list of clients that match the search criteria.
     */
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

    /**
     * Sets the `Memoria` service used to store and retrieve the clients.
     *
     * @param memoria The memory service that will be used for client data.
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
    public void actualizarTexto(){
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
