package org.example.camping2.controladores.Acompanante;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.camping2.modelo.dto.Acompanante;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.memoria.Memoria;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CrearAcompananteController {

    @FXML private TextField dniText;
    @FXML private DatePicker fechaInicio;
    @FXML private DatePicker fechaFin;
    @FXML private TextField precioText;

    @FXML private TableView<Reserva> clientesTableView;
    @FXML private TableColumn<Reserva, Integer> IDReserva;
    @FXML private TableColumn<Reserva, Integer> IDClientecolumn;
    @FXML private TableColumn<Reserva, String> dniColumn;
    @FXML private TableColumn<Reserva, LocalDate> FechaInicioColumn;
    @FXML private TableColumn<Reserva, LocalDate> FechaFinColumn;
    @FXML private TableColumn<Reserva, String> EstadoColumn;
    @FXML private TableColumn<Reserva, Integer> PersonasColumn;
    @FXML private TableColumn<Reserva, Integer> PrecioTotalColumn;

    @FXML private TextField nombreText;
    @FXML private TextField apellidoText;
    @FXML private TextField dniAcompText;
    @FXML private TextField emailText;
    @FXML private TextField telefonoText;

    private Memoria<Acompanante, Integer> memoriaAcompanante;
    private Memoria<Reserva, Integer> memoriaReserva;

    public void setMemoriaAcompanante(Memoria<Acompanante, Integer> memoriaAcompanante) {
        this.memoriaAcompanante = memoriaAcompanante;
    }

    public void setMemoriaReserva(Memoria<Reserva, Integer> memoriaReserva) {
        this.memoriaReserva = memoriaReserva;
        cargarTabla(); // Mostrar todas las reservas inicialmente
    }

    @FXML
    public void initialize() {
        IDReserva.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getId()));
        IDClientecolumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getIdcliente().getId()));

        dniColumn.setCellValueFactory(data -> {
            Cliente cliente = data.getValue().getIdcliente();
            return new SimpleStringProperty(cliente != null ? cliente.getDni() : "");
        });

        FechaInicioColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getFechaInicio()));
        FechaFinColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getFechaFin()));
        EstadoColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado()));
        PersonasColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNumeroPersonas()));
        PrecioTotalColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPrecioTotal()));
    }

    @FXML
    private void buscar() {
        String dniFiltro = dniText.getText().trim().toLowerCase();
        LocalDate fechaInicioFiltro = fechaInicio.getValue();
        LocalDate fechaFinFiltro = fechaFin.getValue();
        Integer precioFiltro = null;
        try {
            if (!precioText.getText().isEmpty()) {
                precioFiltro = Integer.parseInt(precioText.getText().trim());
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "El precio debe ser un número entero.");
            return;
        }

// Hacer una copia final para usar dentro del lambda
        final Integer precioFiltroFinal = precioFiltro;

        List<Reserva> filtradas = memoriaReserva.lista.stream()
                .filter(reserva -> {
                    // Filtrar por DNI (cliente)
                    if (!dniFiltro.isEmpty()) {
                        Cliente cliente = reserva.getIdcliente();
                        if (cliente == null || cliente.getDni() == null ||
                                !cliente.getDni().toLowerCase().contains(dniFiltro)) {
                            return false;
                        }
                    }
                    // Filtrar por fecha inicio
                    if (fechaInicioFiltro != null && reserva.getFechaInicio() != null) {
                        if (reserva.getFechaInicio().isBefore(fechaInicioFiltro)) {
                            return false;
                        }
                    }
                    // Filtrar por fecha fin
                    if (fechaFinFiltro != null && reserva.getFechaFin() != null) {
                        if (reserva.getFechaFin().isAfter(fechaFinFiltro)) {
                            return false;
                        }
                    }
                    // Filtrar por precio
                    if (precioFiltroFinal != null) {
                        if (!precioFiltroFinal.equals(reserva.getPrecioTotal())) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());


        ObservableList<Reserva> observableList = FXCollections.observableArrayList(filtradas);
        clientesTableView.setItems(observableList);
    }

    @FXML
    private void buscarTodos() {
        cargarTabla();
    }

    @FXML
    private void crearAcompanante() {
        Reserva reservaSeleccionada = clientesTableView.getSelectionModel().getSelectedItem();

        if (reservaSeleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Reserva no seleccionada", "Seleccione una reserva para asignar el acompañante.");
            return;
        }

        String nombre = nombreText.getText().trim();
        String apellido = apellidoText.getText().trim();
        String dni = dniAcompText.getText().trim();
        String email = emailText.getText().trim();
        String telefono = telefonoText.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos obligatorios", "Nombre, Apellido y DNI son obligatorios.");
            return;
        }

        Acompanante acomp = new Acompanante(reservaSeleccionada, nombre, apellido, dni, email, telefono);

        boolean exito = memoriaAcompanante.add(acomp);
        if (exito) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Acompañante creado correctamente.");
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo crear el acompañante.");
        }
    }

    private void cargarTabla() {
        ObservableList<Reserva> observableList = FXCollections.observableArrayList(memoriaReserva.lista);
        clientesTableView.setItems(observableList);
    }

    private void limpiarCampos() {
        nombreText.clear();
        apellidoText.clear();
        dniAcompText.clear();
        emailText.clear();
        telefonoText.clear();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
