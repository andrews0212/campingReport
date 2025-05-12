package org.example.camping2.controladores.Reservas;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;

import java.util.stream.Stream;

public class ModificarReservaController {
    Memoria<Reserva, Integer> memoriaReserva;

    @FXML
    private DatePicker fechaInicio;
    @FXML
    private DatePicker fechaFin;
    @FXML
    private TextField precioText;
    @FXML
    private ComboBox estadoCombo;

    ObservableList<String> tipos = FXCollections.observableArrayList("ACTIVA", "FINALIZADA", "CANCELADA");
    @FXML
    private TableView<Reserva> reservaTable;
    @FXML
    private TableColumn<Reserva, Integer> idColumn;
    @FXML
    private TableColumn<Reserva, String> fechaInicioColumn;
    @FXML
    private TableColumn<Reserva, String> fechaFinColumn;
    @FXML
    private TableColumn<Reserva, String> precioColumn;
    @FXML
    private TableColumn<Reserva, String> nombreColumn;
    @FXML
    private TableColumn<Reserva, String> apellidoColumn;
    @FXML
    private TableColumn<Reserva, String> dniColumn;

    private ObservableList<Reserva> Reserva = FXCollections.observableArrayList();

    @FXML
    private  TextField idText1;
    @FXML
    private DatePicker fechaInicio1;
    @FXML
    private DatePicker fechaFin1;
    @FXML
    private TextField precioText1;
    @FXML
    private ComboBox estadoCombo1;

    @FXML
    public void initialize() {
        estadoCombo.setItems(tipos);
        estadoCombo1.setItems(tipos);
        estadoCombo1.getSelectionModel().selectFirst();
        estadoCombo.getSelectionModel().selectFirst();
        reservaTable.setItems(Reserva);
        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        fechaInicioColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFechaInicio().toString()));
        fechaFinColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFechaFin().toString()));
        precioColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrecioTotal().toString()));
        nombreColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdcliente().getNombre()));
        apellidoColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdcliente().getApellido()));
        dniColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdcliente().getDni()));
    }
    @FXML
    public void buscarReservas() {
        Stream<Reserva> stream = memoriaReserva.findAll().stream();

        // Filtrar por ID si no está vacío
        if (!idText1.getText().isEmpty()) {
            try {
                int id = Integer.parseInt(idText1.getText());
                stream = stream.filter(reserva -> reserva.getId() == id);
            } catch (NumberFormatException e) {
                // Puedes mostrar una alerta si lo deseas
            }
        }

        // Filtrar por fecha de inicio
        if (fechaInicio1.getValue() != null) {
            stream = stream.filter(reserva -> !reserva.getFechaInicio().isBefore(fechaInicio1.getValue()));
        }

        // Filtrar por fecha de fin
        if (fechaFin1.getValue() != null) {
            stream = stream.filter(reserva -> !reserva.getFechaFin().isAfter(fechaFin1.getValue()));
        }

        // Filtrar por precio
        if (!precioText1.getText().isEmpty()) {
            try {
                double precio = Double.parseDouble(precioText1.getText());
                stream = stream.filter(reserva -> reserva.getPrecioTotal() == precio);
            } catch (NumberFormatException e) {
                // Puedes mostrar una alerta si lo deseas
            }
        }

        // Filtrar por estado
        String estadoSeleccionado = (String) estadoCombo1.getSelectionModel().getSelectedItem();
        if (estadoSeleccionado != null && !estadoSeleccionado.isEmpty()) {
            stream = stream.filter(reserva -> reserva.getEstado().equalsIgnoreCase(estadoSeleccionado));
        }

        // Actualizar tabla
        Reserva.setAll(stream.toList());
    }

    @FXML
    public void cargarTodos(){
        Reserva.clear();
        Reserva.addAll(memoriaReserva.findAll());
    }
    @FXML
    public void modificarReserva() {
        Reserva seleccionada = reservaTable.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarAlerta("Debe seleccionar una reserva para modificar.");
            return;
        }

        // Validar y actualizar fechaInicio
        if (fechaInicio.getValue() != null) {
            seleccionada.setFechaInicio(fechaInicio.getValue());
        }

        // Validar y actualizar fechaFin
        if (fechaFin.getValue() != null) {
            seleccionada.setFechaFin(fechaFin.getValue());
        }

        // Validar y actualizar precio
        if (!precioText.getText().isEmpty()) {
            try {
                Integer precio = Integer.parseInt(precioText.getText());
                seleccionada.setPrecioTotal(precio);
            } catch (NumberFormatException e) {
                mostrarAlerta("Precio inválido. Debe ser un número.");
                return;
            }
        }

        // Actualizar estado
        String estado = (String) estadoCombo.getSelectionModel().getSelectedItem();
        if (estado != null) {
            seleccionada.setEstado(estado);
        }

        // Guardar los cambios en la memoria
        memoriaReserva.update(seleccionada);

        // Refrescar la tabla
        reservaTable.refresh();
    }


    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    public Memoria<Reserva, Integer> getMemoriaReserva() {
        return memoriaReserva;
    }

    public void setMemoriaReserva(Memoria<Reserva, Integer> memoriaReserva) {
        this.memoriaReserva = memoriaReserva;
    }
}
