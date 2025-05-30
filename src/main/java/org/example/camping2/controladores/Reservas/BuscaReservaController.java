package org.example.camping2.controladores.Reservas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;

import java.util.Date;
import java.util.List;

public class BuscaReservaController implements IdiomaListener {

    Memoria<Reserva, Integer> memoriaReserva;

    @FXML
    private Label labelBuscarReserva;
    @FXML
    private Label labelIDReserva;
    @FXML
    private Label labelIDCliente;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelDNI;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnBuscarTodos;
    @FXML
    private TextField IDReservaText;
    @FXML
    private TextField IDClienteText;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField dniTextField;
    @FXML
    private TableView<Reserva> clientesTableView;
    @FXML
    private TableColumn<Reserva, Integer> IDReserva;
    @FXML
    private TableColumn<Reserva, Integer> IDClientecolumn;
    @FXML
    private TableColumn<Reserva, String> dniColumn;
    @FXML
    private TableColumn<Reserva, String> FechaInicioColumn;
    @FXML
    private TableColumn<Reserva, String> FechaFinColumn;
    @FXML
    private TableColumn<Reserva, String> EstadoColumn;
    @FXML
    private TableColumn<Reserva, Integer> PersonasColumn;
    @FXML
    private TableColumn<Reserva, Integer> PrecioTotalColumn;

    private ObservableList<Reserva> Reservas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        IDReserva.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        IDClientecolumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIdcliente().getId()));
        dniColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIdcliente().getDni()));
        FechaInicioColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getFechaInicio().toString()));
        FechaFinColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getFechaFin().toString()));
        EstadoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getEstado()));
        PersonasColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getNumeroPersonas()));
        PrecioTotalColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPrecioTotal()));
        clientesTableView.setItems(Reservas);
        GestorIdiomas.agregarListener(this);
        actualizarTexto();


    }

    public void setMemoriaReserva(Memoria<Reserva, Integer> memoriaReserva) {
        this.memoriaReserva = memoriaReserva;
        buscaTodos(); // Cargar automáticamente las reservas en la tabla
    }


    @FXML
    public void buscaTodos() {
        Reservas.clear();
        try {
            // Simulate database search logic
            ObservableList<Reserva> resultados = FXCollections.observableArrayList();;
            resultados.addAll(memoriaReserva.findAll());
            Reservas.addAll(resultados);


        }catch (Exception e){

        }
    }
    public void buscarReserva() {
        // Clear previous results and reset status
        Reservas.clear();


        try {
            String idReserva = IDReservaText.getText();
            String idCliente = IDClienteText.getText();
            String nombre = nombreTextField.getText();
            String dni = dniTextField.getText();

            // Simulate database search logic
            ObservableList<Reserva> resultados = buscarReservaBaseDatos(idReserva, idCliente, nombre, dni);

            if (!resultados.isEmpty()) {
                Reservas.addAll(resultados);
            }
        } catch (Exception e) {

        }
    }

    private ObservableList<Reserva> buscarReservaBaseDatos(String idReserva, String idCliente, String nombre, String dni) {
        ObservableList<Reserva> resultados = FXCollections.observableArrayList();

        // Validate that at least one search parameter is provided
        if (idCliente.isEmpty() && idReserva.isEmpty() && nombre.isEmpty() && dni.isEmpty()) {
            System.out.println("No hay datos para buscar.");
            return resultados;
        }

        // Search clients in memory
        try {
            for (Reserva reserva : memoriaReserva.findAll()) {
                boolean coincide = true;

                // Compare ID if provided
                if (!idReserva.isEmpty()) {
                    try {
                        int idNum = Integer.parseInt(idReserva);
                        coincide &= reserva.getId() == idNum;
                    } catch (NumberFormatException e) {
                        System.out.println("ID no válido: " + idReserva);
                        coincide = false;
                    }
                }
                if (!idCliente.isEmpty()) {
                    try {
                        int idNum = Integer.parseInt(idCliente);
                        coincide &= reserva.getIdcliente().getId() == idNum;
                    } catch (NumberFormatException e) {
                        System.out.println("IDCliente no válido: " + idReserva);
                        coincide = false;
                    }
                }

                // Compare name if provided
                if (!nombre.isEmpty()) {
                    coincide &= reserva.getIdcliente().getNombre().equalsIgnoreCase(nombre);
                }

                // Compare DNI if provided
                if (!dni.isEmpty()) {
                    coincide &= reserva.getIdcliente().getDni().equals(dni);
                }

                // Add to results if all conditions match
                if (coincide) {
                    resultados.add(reserva);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar clientes: " + e.getMessage());
        }

        return resultados;
    }

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }

    private void actualizarTexto() {
        labelBuscarReserva.setText(GestorIdiomas.getTexto("labelBuscarReserva"));
        labelIDReserva.setText(GestorIdiomas.getTexto("labelIDReserva"));
        labelIDCliente.setText(GestorIdiomas.getTexto("labelIDCliente"));
        labelNombre.setText(GestorIdiomas.getTexto("nombre"));
        labelDNI.setText(GestorIdiomas.getTexto("dni"));
        btnBuscar.setText(GestorIdiomas.getTexto("buscar"));
        btnBuscarTodos.setText(GestorIdiomas.getTexto("buscarTodos"));
        IDReserva.setText(GestorIdiomas.getTexto("labelIDReserva"));
        IDClientecolumn.setText(GestorIdiomas.getTexto("labelIDCliente"));
        dniColumn.setText(GestorIdiomas.getTexto("dni"));
        FechaInicioColumn.setText(GestorIdiomas.getTexto("fechaInicio"));
        FechaFinColumn.setText(GestorIdiomas.getTexto("fechaFin"));
        EstadoColumn.setText(GestorIdiomas.getTexto("estado"));
        PersonasColumn.setText(GestorIdiomas.getTexto("numeroPersonas"));
        PrecioTotalColumn.setText(GestorIdiomas.getTexto("precioTotal"));
        clientesTableView.setPlaceholder(new Label(GestorIdiomas.getTexto("noResultados")));



    }
}
