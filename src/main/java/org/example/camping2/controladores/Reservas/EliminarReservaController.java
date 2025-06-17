package org.example.camping2.controladores.Reservas;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class EliminarReservaController implements IdiomaListener {

    private Logger logger;

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
    private TextField idText1;
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

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Memoria<Reserva, Integer> getMemoriaReserva() {
        return memoriaReserva;
    }

    public void setMemoriaReserva(Memoria<Reserva, Integer> memoriaReserva) {
        this.memoriaReserva = memoriaReserva;
        if (logger != null) {
            logger.info("Memoria de reservas establecida.");
        }
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

        if (logger != null) {
            logger.info("Inicialización del controlador EliminarReservaController completada.");
        }
    }

    @FXML
    public void buscarReservas() {
        if (logger != null) {
            logger.info("Buscando reservas con filtros aplicados.");
        }

        Stream<Reserva> stream = memoriaReserva.findAll().stream();

        // Filtrar por ID si no está vacío
        if (!idText1.getText().isEmpty()) {
            try {
                int id = Integer.parseInt(idText1.getText());
                stream = stream.filter(reserva -> reserva.getId() == id);
                if (logger != null) {
                    logger.info("Filtro aplicado: id = " + id);
                }
            } catch (NumberFormatException e) {
                if (logger != null) {
                    logger.warning("Error al parsear el ID: " + e.getMessage());
                }
            }
        }

        // Filtrar por fecha de inicio
        if (fechaInicio1.getValue() != null) {
            stream = stream.filter(reserva -> !reserva.getFechaInicio().isBefore(fechaInicio1.getValue()));
            if (logger != null) {
                logger.info("Filtro aplicado: fechaInicio >= " + fechaInicio1.getValue());
            }
        }

        // Filtrar por fecha de fin
        if (fechaFin1.getValue() != null) {
            stream = stream.filter(reserva -> !reserva.getFechaFin().isAfter(fechaFin1.getValue()));
            if (logger != null) {
                logger.info("Filtro aplicado: fechaFin <= " + fechaFin1.getValue());
            }
        }

        // Filtrar por precio
        if (!precioText1.getText().isEmpty()) {
            try {
                double precio = Double.parseDouble(precioText1.getText());
                stream = stream.filter(reserva -> reserva.getPrecioTotal() == precio);
                if (logger != null) {
                    logger.info("Filtro aplicado: precioTotal = " + precio);
                }
            } catch (NumberFormatException e) {
                if (logger != null) {
                    logger.warning("Error al parsear el precio: " + e.getMessage());
                }
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
            if (logger != null) {
                logger.info("Filtro aplicado: estado = " + estadoTraducido);
            }
        }

        // Filtrar por DNI
        if (!dniText.getText().isEmpty()) {
            String dni = dniText.getText().trim();
            stream = stream.filter(reserva -> reserva.getIdcliente().getDni().equalsIgnoreCase(dni));
            if (logger != null) {
                logger.info("Filtro aplicado: dni = " + dni);
            }
        }

        Reserva.setAll(stream.toList());

        if (logger != null) {
            logger.info("Número de reservas encontradas: " + Reserva.size());
        }
    }

    @FXML
    public void cargarTodos() {
        if (logger != null) {
            logger.info("Cargando todas las reservas.");
        }
        Reserva.clear();
        Reserva.addAll(memoriaReserva.findAll());
    }

    @FXML
    public void eliminarReserva() {
        Reserva seleccionada = reservaTable.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarAlerta("Debe seleccionar una reserva para eliminar.");
            if (logger != null) {
                logger.warning("Intento de eliminar reserva sin selección.");
            }
            return;
        }

        try {
            memoriaReserva.delete(seleccionada.getId());
            Reserva.remove(seleccionada); // Actualiza la tabla
            if (logger != null) {
                logger.info("Reserva eliminada: ID " + seleccionada.getId());
            }
        } catch (Exception e) {
            // Verificamos si es una excepción por restricción de clave foránea
            if (e.getCause() != null && e.getCause().getMessage().contains("a foreign key constraint fails")) {
                mostrarAlerta("No se puede eliminar la reserva porque tiene acompañantes asociados.");
                if (logger != null) {
                    logger.warning("Error al eliminar reserva: reserva con acompañantes asociados. ID: " + seleccionada.getId());
                }
            } else {
                mostrarAlerta("Error inesperado al eliminar la reserva.");
                e.printStackTrace(); // o usar logger
                if (logger != null) {
                    logger.severe("Error inesperado al eliminar reserva: " + e.getMessage());
                }
            }
        }
    }



    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        // Obtener el Stage actual y asignarlo como propietario
        Stage stage = (Stage) labelDNI.getScene().getWindow(); // cualquier nodo sirve
        alert.initOwner(stage);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
        if (logger != null) {
            logger.info("Idioma cambiado y textos actualizados.");
        }
    }

    private void actualizarTexto() {
        mapaEstadoTraducido.clear();
        mapaEstadoTraducido.put("ACTIVA", GestorIdiomas.getTexto("ACTIVA"));
        mapaEstadoTraducido.put("FINALIZADA", GestorIdiomas.getTexto("FINALIZADA"));
        mapaEstadoTraducido.put("CANCELADA", GestorIdiomas.getTexto("CANCELADA"));
        estadoCombo1.setItems(FXCollections.observableArrayList(mapaEstadoTraducido.values()));
        estadoCombo1.getSelectionModel().selectFirst();
        labelEliminarReserva.setText(GestorIdiomas.getTexto("eliminarReserva"));
        labelId.setText(GestorIdiomas.getTexto("labelIDReserva"));
        labelFechaInicio.setText(GestorIdiomas.getTexto("fechaInicio"));
        labelFechaFin.setText(GestorIdiomas.getTexto("fechaFin"));
        labelPrecio.setText(GestorIdiomas.getTexto("precioTotal"));
        labelEstado.setText(GestorIdiomas.getTexto("estado"));
        labelDNI.setText(GestorIdiomas.getTexto("dni"));
        btnBuscar.setText(GestorIdiomas.getTexto("buscar"));
        btnBuscarTodos.setText(GestorIdiomas.getTexto("buscarTodos"));
        btnEliminar.setText(GestorIdiomas.getTexto("eliminar"));
        idColumn.setText(GestorIdiomas.getTexto("labelIDReserva"));
        fechaInicioColumn.setText(GestorIdiomas.getTexto("fechaInicio"));
        fechaFinColumn.setText(GestorIdiomas.getTexto("fechaFin"));
        nombreColumn.setText(GestorIdiomas.getTexto("nombre"));
        apellidoColumn.setText(GestorIdiomas.getTexto("apellido"));
    }
}
