package org.example.camping2.controladores.Reservas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;
import org.example.camping2.modelo.validaciones.ValidarReservas;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrearReservaController implements IdiomaListener {

    private Logger logger;

    Memoria<Reserva, Integer> memoriaReserva;
    Memoria<Recurso, Integer> memoriaRecurso;
    Memoria<Cliente, Integer> memoriaCliente;

    @FXML
    private Label labelCrearReserva;
    @FXML
    private Button btnCargar;
    @FXML
    private Button btnReserva;
    @FXML
    private Label labelDNI;
    @FXML
    private Label labelFechaInicio;
    @FXML
    private Label labelFechaFin;
    @FXML
    private Label labelPrecio;

    @FXML
    private ComboBox tipoComboBox;
    @FXML
    private TextField dniText;
    @FXML
    private DatePicker fechaInicio;
    @FXML
    private DatePicker fechaFin;
    @FXML
    private TextField precioText;
    ObservableList<String> tipos = FXCollections.observableArrayList("BUNGALOW", "BARBACOA", "PARCELA");
    @FXML
    private TableView<Recurso> tablaRecurso;
    @FXML
    private TableColumn<Recurso, Integer> IDColumn;
    @FXML
    private TableColumn<Recurso, String> nombreColumn;
    @FXML
    private TableColumn<Recurso, String> tipoColumn;
    @FXML
    private TableColumn<Recurso, Integer> capacidadColumn;
    @FXML
    private TableColumn<Recurso, Integer> precioColumn;
    @FXML
    private TableColumn<Recurso, Integer> minimoPersonasColumn;

    private ObservableList<Recurso> recursos = FXCollections.observableArrayList();
    private Map<String, String> mapaTipoTraducido;

    @FXML
    public void initialize() {
        tipoComboBox.setItems(tipos);
        mapaTipoTraducido = new HashMap<>();
        mapaTipoTraducido.clear();
        mapaTipoTraducido.put("PARCELA", GestorIdiomas.getTexto("PARCELA"));
        mapaTipoTraducido.put("BUNGALOW", GestorIdiomas.getTexto("BUNGALOW"));
        mapaTipoTraducido.put("BARBACOA", GestorIdiomas.getTexto("BARBACOA"));
        tipoComboBox.setItems(FXCollections.observableArrayList(mapaTipoTraducido.values()));
        tipoComboBox.getSelectionModel().select(0);
        nombreColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getNombre()));
        tipoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getTipo()));
        capacidadColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCapacidad()));
        precioColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPrecio()));
        minimoPersonasColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getMinimoPersonas()));
        IDColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        recursos.clear();
        GestorIdiomas.agregarListener(this);
        actualizarTexto();
    }

    // Setter para Logger
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setMemoriaReserva(Memoria<Reserva, Integer> memoriaReserva) {
        this.memoriaReserva = memoriaReserva;
        if (logger != null) logger.info("MemoriaReserva seteada.");
    }

    public void setMemoriaRecurso(Memoria<Recurso, Integer> memoriaRecurso) {
        this.memoriaRecurso = memoriaRecurso;
        if (logger != null) logger.info("MemoriaRecurso seteada.");

        // Si el ComboBox ya tiene una selección, cargar los recursos automáticamente
        if (tipoComboBox.getSelectionModel().getSelectedItem() != null) {
            cargarRecursosPorTipoSeleccionado();
        }
    }

    private void cargarRecursosPorTipoSeleccionado() {
        try {
            recursos.clear();
            String tipoTraducidoUI = tipoComboBox.getSelectionModel().getSelectedItem().toString();

            // Volver al valor real del tipo (clave)
            String tipoReal = mapaTipoTraducido.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(tipoTraducidoUI))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);

            if (tipoReal != null && memoriaRecurso != null) {
                recursos.addAll(memoriaRecurso.findAll().stream()
                        .filter(p -> p.getTipo().equals(tipoReal))
                        .toList());
                tablaRecurso.setItems(recursos);
                if (logger != null) logger.info("Recursos cargados para tipo: " + tipoReal);
            }

        } catch (Exception e) {
            if (logger != null) {
                logger.log(Level.SEVERE, "Error al cargar recursos", e);
            } else {
                System.out.println("Error al cargar recursos: " + e.getMessage());
            }
        }
    }

    public void setMemoriaCliente(Memoria<Cliente, Integer> memoriaCliente) {
        this.memoriaCliente = memoriaCliente;
        if (logger != null) logger.info("MemoriaCliente seteada.");
    }

    public void cargar(javafx.event.ActionEvent actionEvent) {
        try {
            recursos.clear();
            ObservableList<Recurso> recursos1 = FXCollections.observableArrayList();
            String tipo = tipoComboBox.getSelectionModel().getSelectedItem().toString();
            String tipoTraducido = mapaTipoTraducido.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(tipo))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);
            recursos1.addAll(memoriaRecurso.findAll().stream().filter(p -> p.getTipo().equals(tipoTraducido)).toList());
            recursos.addAll(recursos1);
            tablaRecurso.setItems(recursos);
            if (logger != null) logger.info("Carga de recursos completada para tipo: " + tipoTraducido);
        } catch (Exception e) {
            if (logger != null) {
                logger.log(Level.SEVERE, "Error en cargar recursos", e);
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void hacerReserva() {
        Recurso recurso = tablaRecurso.getSelectionModel().getSelectedItem();
        try {
            Cliente cliente = memoriaCliente.findAll().stream()
                    .filter(p -> p.getDni().toUpperCase().equals(dniText.getText().toUpperCase()))
                    .findFirst()
                    .get();
            if (cliente != null && recurso != null) {
                if (ValidarReservas.validarFechas(fechaInicio.getValue(), fechaFin.getValue())) {
                    memoriaReserva.add(new Reserva(cliente, recurso, fechaInicio.getValue(), fechaFin.getValue(), "ACTIVA", Integer.parseInt(precioText.getText()), 0));
                    mostrarAlerta("Reserva Realizada", "La reserva ha sido realizada con éxito", Alert.AlertType.INFORMATION);
                    if (logger != null) logger.info("Reserva realizada para cliente DNI: " + cliente.getDni());
                } else {
                    mostrarAlerta("Error en las fechas", "Las fechas no son válidas", Alert.AlertType.ERROR);
                    if (logger != null) logger.warning("Intento de reserva con fechas inválidas.");
                }
            }
        } catch (NoSuchElementException e) {
            mostrarAlerta("Cliente No Encontrado", "El cliente no existe", Alert.AlertType.ERROR);
            if (logger != null) logger.warning("Cliente no encontrado para DNI: " + dniText.getText());
        } catch (Exception e) {
            if (logger != null) logger.log(Level.SEVERE, "Error al hacer reserva", e);
        }
    }

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }

    private void actualizarTexto() {
        labelCrearReserva.setText(GestorIdiomas.getTexto("labelCrearReserva"));
        btnCargar.setText(GestorIdiomas.getTexto("btnCargar"));
        btnReserva.setText(GestorIdiomas.getTexto("btnReserva"));
        labelDNI.setText(GestorIdiomas.getTexto("dni"));
        labelFechaInicio.setText(GestorIdiomas.getTexto("fechaInicio"));
        labelFechaFin.setText(GestorIdiomas.getTexto("fechaFin"));
        labelPrecio.setText(GestorIdiomas.getTexto("precioTotal"));
        IDColumn.setText(GestorIdiomas.getTexto("id"));
        nombreColumn.setText(GestorIdiomas.getTexto("nombre"));
        tipoColumn.setText(GestorIdiomas.getTexto("tipo"));
        capacidadColumn.setText(GestorIdiomas.getTexto("capacidad"));
        precioColumn.setText(GestorIdiomas.getTexto("precio"));
        minimoPersonasColumn.setText(GestorIdiomas.getTexto("minimoPersonas"));
        mapaTipoTraducido.clear();
        mapaTipoTraducido.put("PARCELA", GestorIdiomas.getTexto("PARCELA"));
        mapaTipoTraducido.put("BUNGALOW", GestorIdiomas.getTexto("BUNGALOW"));
        mapaTipoTraducido.put("BARBACOA", GestorIdiomas.getTexto("BARBACOA"));

        tipoComboBox.setItems(FXCollections.observableArrayList(mapaTipoTraducido.values()));
        tipoComboBox.getSelectionModel().select(0);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);

        // Obtener el Stage actual y asignarlo como propietario
        Stage stage = (Stage) labelCrearReserva.getScene().getWindow();
        alerta.initOwner(stage);
        alerta.initModality(Modality.WINDOW_MODAL);

        alerta.show();
    }
}
