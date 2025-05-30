package org.example.camping2.controladores.Reservas;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;
import org.example.camping2.modelo.validaciones.ValidarReservas;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ModificarReservaController implements IdiomaListener {



    @FXML
    private Label labelModificar;
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
    private Label labelFechaInicio2;
    @FXML
    private Label labelFechaFin2;
    @FXML
    private Label labelPrecio2;
    @FXML
    private Label labelEstado2;
    @FXML
    private Button btnModificar;


    Memoria<Reserva, Integer> memoriaReserva;

    @FXML
    private DatePicker fechaInicio;
    @FXML
    private DatePicker fechaFin;
    @FXML
    private TextField precioText;
    @FXML
    private ComboBox estadoCombo;
    @FXML
    private TextField dniText;

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

    Map<String, String> mapaEstadoTraducido;

    @FXML
    public void initialize() {
        mapaEstadoTraducido = new HashMap<>();
        mapaEstadoTraducido.clear();
        mapaEstadoTraducido.put("ACTIVA", GestorIdiomas.getTexto("ACTIVA"));
        mapaEstadoTraducido.put("FINALIZADA", GestorIdiomas.getTexto("FINALIZADA"));
        mapaEstadoTraducido.put("CANCELADA", GestorIdiomas.getTexto("CANCELADA"));


        estadoCombo.setItems(FXCollections.observableArrayList(mapaEstadoTraducido.values()));
        estadoCombo1.setItems(FXCollections.observableArrayList(mapaEstadoTraducido.values()));
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
    public void modificarReserva() {
        Reserva seleccionada = reservaTable.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarAlerta("Atención","Debe seleccionar una reserva para modificar.",  Alert.AlertType.WARNING);
            return;
        }

        // Validar fechas
        if (fechaInicio.getValue() == null || fechaFin.getValue() == null) {
            mostrarAlerta("Atención","Debe ingresar ambas fechas.",  Alert.AlertType.WARNING);
            return;
        }

        if (!ValidarReservas.validarFechas(fechaInicio.getValue(), fechaFin.getValue())) {
            mostrarAlerta("Atención","La fecha de inicio no puede ser posterior a la fecha de fin.",  Alert.AlertType.WARNING);
            return;
        }

        seleccionada.setFechaInicio(fechaInicio.getValue());
        seleccionada.setFechaFin(fechaFin.getValue());

        // Validar precio
        if (!precioText.getText().isEmpty()) {
            try {
                int precio = Integer.parseInt(precioText.getText());
                seleccionada.setPrecioTotal(precio);
            } catch (NumberFormatException e) {
                mostrarAlerta("Atención","Precio inválido. Debe ser un número.",  Alert.AlertType.WARNING);
                return;
            }
        }

        // Traducir estado (si estás usando traducción como en EliminarReservaController)
        String estadoSeleccionado = (String) estadoCombo.getSelectionModel().getSelectedItem();
        if (estadoSeleccionado != null) {
            String estadoOriginal = mapaEstadoTraducido.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(estadoSeleccionado))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);
            if (estadoOriginal != null) {
                seleccionada.setEstado(estadoOriginal);
            }
        }

        // Guardar cambios y refrescar tabla
        memoriaReserva.update(seleccionada);
        reservaTable.refresh();
    }


    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);

        // Obtener el Stage actual y asignarlo como propietario
        Stage stage = (Stage) labelDNI.getScene().getWindow(); // cualquier nodo sirve
        alerta.initOwner(stage);
        alerta.initModality(Modality.WINDOW_MODAL);  // Importante: no usar APPLICATION_MODAL

        alerta.show(); // No bloqueante
    }


    public Memoria<Reserva, Integer> getMemoriaReserva() {
        return memoriaReserva;
    }

    public void setMemoriaReserva(Memoria<Reserva, Integer> memoriaReserva) {
        this.memoriaReserva = memoriaReserva;
        cargarTodos();
    }

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }

    private void actualizarTexto() {
        labelModificar.setText(GestorIdiomas.getTexto("labelModificarReserva"));
        labelId.setText(GestorIdiomas.getTexto("id"));
        labelFechaInicio.setText(GestorIdiomas.getTexto("fechaInicio"));
        labelFechaFin.setText(GestorIdiomas.getTexto("fechaFin"));
        labelPrecio.setText(GestorIdiomas.getTexto("precioTotal"));
        labelEstado.setText(GestorIdiomas.getTexto("estado"));
        labelDNI.setText(GestorIdiomas.getTexto("dni"));

        btnBuscar.setText(GestorIdiomas.getTexto("buscar"));
        btnBuscarTodos.setText(GestorIdiomas.getTexto("buscarTodos"));
        btnModificar.setText(GestorIdiomas.getTexto("modificar"));

        labelFechaInicio2.setText(GestorIdiomas.getTexto("fechaInicio"));
        labelFechaFin2.setText(GestorIdiomas.getTexto("fechaFin"));
        labelPrecio2.setText(GestorIdiomas.getTexto("precioTotal"));
        labelEstado2.setText(GestorIdiomas.getTexto("estado"));

        idColumn.setText(GestorIdiomas.getTexto("id"));
        fechaInicioColumn.setText(GestorIdiomas.getTexto("fechaInicio"));
        fechaFinColumn.setText(GestorIdiomas.getTexto("fechaFin"));
        precioColumn.setText(GestorIdiomas.getTexto("precioTotal"));
        nombreColumn.setText(GestorIdiomas.getTexto("nombre"));
        apellidoColumn.setText(GestorIdiomas.getTexto("apellido"));
        dniColumn.setText(GestorIdiomas.getTexto("dni"));



        fechaInicio.setPromptText(GestorIdiomas.getTexto("fechaInicioText"));
        fechaFin.setPromptText(GestorIdiomas.getTexto("fechaFinText"));
        precioText.setPromptText(GestorIdiomas.getTexto("precioField"));
        estadoCombo.setPromptText(GestorIdiomas.getTexto("estadoText"));

        mapaEstadoTraducido.clear();
        mapaEstadoTraducido.put("ACTIVA", GestorIdiomas.getTexto("ACTIVA"));
        mapaEstadoTraducido.put("FINALIZADA", GestorIdiomas.getTexto("FINALIZADA"));
        mapaEstadoTraducido.put("CANCELADA", GestorIdiomas.getTexto("CANCELADA"));

        estadoCombo.setItems(FXCollections.observableArrayList(mapaEstadoTraducido.values()));
        estadoCombo1.setItems(FXCollections.observableArrayList(mapaEstadoTraducido.values()));
        estadoCombo1.getSelectionModel().selectFirst();
        estadoCombo.getSelectionModel().selectFirst();

    }
}
