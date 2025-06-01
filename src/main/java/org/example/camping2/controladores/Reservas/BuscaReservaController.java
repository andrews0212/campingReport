package org.example.camping2.controladores.Reservas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BuscaReservaController implements IdiomaListener {

    private Logger log;

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

    // Setter para inyectar el Logger desde fuera
    public void setLog(Logger log) {
        this.log = log;
        if (log != null) {
            log.info("Logger inyectado correctamente en BuscaReservaController");
        }
    }

    @FXML
    public void initialize() {
        if (log != null) log.info("Inicializando BuscaReservaController");

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

        if (log != null) log.info("Tabla y textos inicializados");
    }

    public void setMemoriaReserva(Memoria<Reserva, Integer> memoriaReserva) {
        this.memoriaReserva = memoriaReserva;
        if (log != null) log.info("MemoriaReserva seteada, buscando todas las reservas");
        buscaTodos(); // Cargar automáticamente las reservas en la tabla
    }

    @FXML
    public void buscaTodos() {
        Reservas.clear();
        if (log != null) log.info("buscaTodos() llamado: Limpiando lista y cargando todas las reservas");
        try {
            ObservableList<Reserva> resultados = FXCollections.observableArrayList();
            resultados.addAll(memoriaReserva.findAll());
            Reservas.addAll(resultados);
            if (log != null) log.info("Reservas cargadas: " + resultados.size());
        } catch (Exception e) {
            if (log != null) log.log(Level.SEVERE, "Error en buscaTodos: " + e.getMessage(), e);
        }
    }

    @FXML
    public void buscarReserva() {
        Reservas.clear();
        if (log != null) log.info("buscarReserva() llamado");

        try {
            String idReserva = IDReservaText.getText();
            String idCliente = IDClienteText.getText();
            String nombre = nombreTextField.getText();
            String dni = dniTextField.getText();

            if (log != null) log.info(String.format("Parámetros búsqueda - idReserva: '%s', idCliente: '%s', nombre: '%s', dni: '%s'",
                    idReserva, idCliente, nombre, dni));

            ObservableList<Reserva> resultados = buscarReservaBaseDatos(idReserva, idCliente, nombre, dni);

            if (!resultados.isEmpty()) {
                Reservas.addAll(resultados);
                if (log != null) log.info("Reservas encontradas: " + resultados.size());
            } else {
                if (log != null) log.info("No se encontraron reservas con los parámetros indicados");
            }
        } catch (Exception e) {
            if (log != null) log.log(Level.SEVERE, "Error en buscarReserva: " + e.getMessage(), e);
        }
    }

    private ObservableList<Reserva> buscarReservaBaseDatos(String idReserva, String idCliente, String nombre, String dni) {
        ObservableList<Reserva> resultados = FXCollections.observableArrayList();

        if (idCliente.isEmpty() && idReserva.isEmpty() && nombre.isEmpty() && dni.isEmpty()) {
            if (log != null) log.warning("Intento de búsqueda sin parámetros");
            System.out.println("No hay datos para buscar.");
            return resultados;
        }

        try {
            for (Reserva reserva : memoriaReserva.findAll()) {
                boolean coincide = true;

                if (!idReserva.isEmpty()) {
                    try {
                        int idNum = Integer.parseInt(idReserva);
                        coincide &= reserva.getId() == idNum;
                    } catch (NumberFormatException e) {
                        if (log != null) log.warning("ID no válido para reserva: " + idReserva);
                        coincide = false;
                    }
                }

                if (!idCliente.isEmpty()) {
                    try {
                        int idNum = Integer.parseInt(idCliente);
                        coincide &= reserva.getIdcliente().getId() == idNum;
                    } catch (NumberFormatException e) {
                        if (log != null) log.warning("IDCliente no válido: " + idCliente);
                        coincide = false;
                    }
                }

                if (!nombre.isEmpty()) {
                    coincide &= reserva.getIdcliente().getNombre().equalsIgnoreCase(nombre);
                }

                if (!dni.isEmpty()) {
                    coincide &= reserva.getIdcliente().getDni().equals(dni);
                }

                if (coincide) {
                    resultados.add(reserva);
                }
            }
            if (log != null) log.info("Búsqueda completada con " + resultados.size() + " resultados");
        } catch (Exception e) {
            if (log != null) log.log(Level.SEVERE, "Error al buscar clientes: " + e.getMessage(), e);
        }

        return resultados;
    }

    @Override
    public void idiomaCambiado() {
        if (log != null) log.info("Idioma cambiado, actualizando textos");
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
        if (log != null) log.info("Textos de UI actualizados según idioma");
    }

    public void setLogger(Logger logger) {
        this.log = logger;
    }
}
