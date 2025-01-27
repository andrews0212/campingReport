package org.example.camping2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.camping2.dto.Cliente;
import org.example.camping2.memoria.Memoria;

public class BuscarClientePanelController {

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



    @FXML
    public void initialize() {
        // Asocia las columnas con los campos de la clase Cliente
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        nombreColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        apellidoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getApellido()));
        dniColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDni()));


        // Vincula la lista observable con la tabla
        clientesTableView.setItems(clientes);
    }

    public void buscarClientes() {
        // Limpia el estado anterior
        clientes.clear();
        statusLabel.setText("");

        try {
            String id = idClienteTextField.getText();
            String nombre = nombreTextField.getText();
            String dni = dniTextField.getText();

            // Simula la lógica de búsqueda en la base de datos

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

    private ObservableList<Cliente> buscarClientesEnBaseDatos(String id, String nombre, String dni) {
        ObservableList<Cliente> resultados = FXCollections.observableArrayList();

        // Validar si no se introdujo ningún dato
        if (id.isEmpty() && nombre.isEmpty() && dni.isEmpty()) {
            System.out.println("No hay datos para buscar.");
            return resultados;
        }

        // Buscar clientes en memoria
        try {
            for (Cliente cliente : memoria.findAll()) {
                boolean coincide = true;

                // Comparar ID si no está vacío
                if (!id.isEmpty()) {
                    try {
                        int idNum = Integer.parseInt(id);
                        coincide &= cliente.getId() == idNum;
                    } catch (NumberFormatException e) {
                        System.out.println("ID no válido: " + id);
                        coincide = false;
                    }
                }

                // Comparar nombre si no está vacío
                if (!nombre.isEmpty()) {
                    coincide &= cliente.getNombre().equalsIgnoreCase(nombre);
                }

                // Comparar DNI si no está vacío
                if (!dni.isEmpty()) {
                    coincide &= cliente.getDni().equals(dni);
                }

                // Agregar a los resultados si todas las condiciones coinciden
                if (coincide) {
                    resultados.add(cliente);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar clientes: " + e.getMessage());
        }

        return resultados;
    }
    public void setMemoria(Memoria<Cliente, Integer> memoria) {
        this.memoria = memoria;
    }

}
