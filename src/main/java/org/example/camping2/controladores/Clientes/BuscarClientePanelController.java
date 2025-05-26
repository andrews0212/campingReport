package org.example.camping2.controladores.Clientes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.memoria.Memoria;

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
    private Label labelTitulo, labelId, labelNombre, labelDNI;
    @FXML
    private Button buttonBuscar;
    @FXML
    private Button buttonBuscarTodos;

    private Memoria<Cliente, Integer> memoria;

    @FXML
    private TextField idClienteTextField;

    @FXML
    private TextField nombreTextField;

    @FXML
    private TextField dniTextField;

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
    private Label statusLabel;

    private ObservableList<Cliente> clientes = FXCollections.observableArrayList();

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
        // Clear previous results and reset status
        clientes.clear();
        statusLabel.setText("");

        try {
            String id = idClienteTextField.getText();
            String nombre = nombreTextField.getText();
            String dni = dniTextField.getText();

            // Simulate database search logic
            ObservableList<Cliente> resultados = buscarClientesEnBaseDatos(id, nombre, dni);

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
    private ObservableList<Cliente> buscarClientesEnBaseDatos(String id, String nombre, String dni) {
        ObservableList<Cliente> resultados = FXCollections.observableArrayList();

        // Validate that at least one search parameter is provided
        if (id.isEmpty() && nombre.isEmpty() && dni.isEmpty()) {
            System.out.println("No hay datos para buscar.");
            return resultados;
        }

        // Search clients in memory
        try {
            for (Cliente cliente : memoria.findAll()) {
                boolean coincide = true;

                // Compare ID if provided
                if (!id.isEmpty()) {
                    try {
                        int idNum = Integer.parseInt(id);
                        coincide &= cliente.getId() == idNum;
                    } catch (NumberFormatException e) {
                        System.out.println("ID no válido: " + id);
                        coincide = false;
                    }
                }

                // Compare name if provided
                if (!nombre.isEmpty()) {
                    coincide &= cliente.getNombre().equalsIgnoreCase(nombre);
                }

                // Compare DNI if provided
                if (!dni.isEmpty()) {
                    coincide &= cliente.getDni().equals(dni);
                }

                // Add to results if all conditions match
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

    idColumn.setText(GestorIdiomas.getTexto("idcliente"));
    nombreColumn.setText(GestorIdiomas.getTexto("nombre"));
    dniColumn.setText(GestorIdiomas.getTexto("dni"));
    apellidoColumn.setText(GestorIdiomas.getTexto("apellido"));

    }
}
