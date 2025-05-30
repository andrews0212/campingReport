package org.example.camping2.controladores.Reservas;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class EliminarReservaController implements IdiomaListener {
    Memoria<Reserva, Integer> memoriaReserva;

    @FXML
    private Label labelEliminarReserva;
    @FXML
    private Label labelId;
    @FXML
    private Label labelFechaInicio;
    @FXML
    private Label labelFechaFin;
    @FXML
    private Label labelPrecio;
    @FXML
    private Label labelEstado;
    @FXML
    private Label labelDNI;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnBuscarTodos;
    @FXML
    private Button btnEliminar;

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
    private TextField dniText;

    @FXML
    private TableView<Reserva> reservaTable;
    @FXML
    private TableColumn<Reserva, Integer> idColumn;
    @FXML
    private TableColumn<Reserva, String> fechaInicioColumn;
    @FXML
    private TableColumn<Reserva, String> fechaFinColumn;

    @FXML
    private TableColumn<Reserva, String> nombreColumn;
    @FXML
    private TableColumn<Reserva, String> apellidoColumn;
    @FXML
    private TableColumn<Reserva, String> dniColumn;

    private ObservableList<Reserva> Reserva = FXCollections.observableArrayList();

    public Memoria<Reserva, Integer> getMemoriaReserva() {
        return memoriaReserva;
    }

    public void setMemoriaReserva(Memoria<Reserva, Integer> memoriaReserva) {
        this.memoriaReserva = memoriaReserva;
        cargarTodos();
    }

    private Map<String, String> mapaEstadoTraducido;
    @FXML
    public void initialize() {
        mapaEstadoTraducido = new HashMap<>();
        mapaEstadoTraducido.clear();
        mapaEstadoTraducido.put("ACTIVA", GestorIdiomas.getTexto("ACTIVA"));
        mapaEstadoTraducido.put("FINALIZADA", GestorIdiomas.getTexto("FINALIZADA"));
        mapaEstadoTraducido.put("CANCELADA", GestorIdiomas.getTexto("CANCELADA"));
        estadoCombo1.setItems(FXCollections.observableArrayList(mapaEstadoTraducido.values()));
        estadoCombo1.getSelectionModel().selectFirst();
        reservaTable.setItems(Reserva);
        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        fechaInicioColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFechaInicio().toString()));
        fechaFinColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFechaFin().toString()));
        nombreColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdcliente().getNombre()));
        apellidoColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdcliente().getApellido()));
        dniColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdcliente().getDni()));
        GestorIdiomas.agregarListener(this);
        actualizarTexto();
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
        String estadoTraducido = mapaEstadoTraducido.entrySet().stream()
                .filter(entry -> entry.getValue().equals(estadoSeleccionado))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
        if (estadoSeleccionado != null && !estadoSeleccionado.isEmpty()) {
            stream = stream.filter(reserva -> reserva.getEstado().equalsIgnoreCase(estadoTraducido));
        }

        // 🔍 Filtrar por DNI
        if (!dniText.getText().isEmpty()) {
            String dni = dniText.getText().trim();
            stream = stream.filter(reserva -> reserva.getIdcliente().getDni().equalsIgnoreCase(dni));
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
    public void eliminarReserva() {
        Reserva seleccionada = reservaTable.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarAlerta("Debe seleccionar una reserva para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de que desea eliminar esta reserva?");

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                memoriaReserva.delete(seleccionada.getId());
                Reserva.remove(seleccionada); // Actualiza la tabla
            }
        });
    }
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }

    private void actualizarTexto() {
        mapaEstadoTraducido.clear();
        mapaEstadoTraducido.put("ACTIVA", GestorIdiomas.getTexto("ACTIVA"));
        mapaEstadoTraducido.put("FINALIZADA", GestorIdiomas.getTexto("FINALIZADA"));
        mapaEstadoTraducido.put("CANCELADA", GestorIdiomas.getTexto("CANCELADA"));
        estadoCombo1.setItems(FXCollections.observableArrayList(mapaEstadoTraducido.values()));
        estadoCombo1.getSelectionModel().selectFirst();
        labelEliminarReserva.setText(GestorIdiomas.getTexto("eliminarReserva"));
        labelId.setText(GestorIdiomas.getTexto("id"));
        labelFechaInicio.setText(GestorIdiomas.getTexto("fechaInicio"));
        labelFechaFin.setText(GestorIdiomas.getTexto("fechaFin"));
        labelPrecio.setText(GestorIdiomas.getTexto("precioTotal"));
        labelEstado.setText(GestorIdiomas.getTexto("estado"));
        labelDNI.setText(GestorIdiomas.getTexto("dni"));
        btnBuscar.setText(GestorIdiomas.getTexto("buscar"));
        btnBuscarTodos.setText(GestorIdiomas.getTexto("buscarTodos"));
        btnEliminar.setText(GestorIdiomas.getTexto("eliminar"));
        idColumn.setText(GestorIdiomas.getTexto("id"));
        fechaInicioColumn.setText(GestorIdiomas.getTexto("fechaInicio"));
        fechaFinColumn.setText(GestorIdiomas.getTexto("fechaFin"));
        nombreColumn.setText(GestorIdiomas.getTexto("nombre"));
        apellidoColumn.setText(GestorIdiomas.getTexto("apellido"));



    }
}
