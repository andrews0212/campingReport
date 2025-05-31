package org.example.camping2.controladores.Acompanante;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Acompanante;
import org.example.camping2.modelo.memoria.Memoria;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BuscaAcompananteController implements IdiomaListener {

    private Logger logger;

    private Memoria<Acompanante, Integer> memoriaAcompanante;

    @FXML
    private Label labelBuscarAcompanante;
    @FXML
    private Label labelIDAcompanante;
    @FXML
    private Label labelIDReserva;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelApellido;
    @FXML
    private Label labelDNI;
    @FXML
    private Label labelEmail;
    @FXML
    private Label labelTelefono;

    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnBuscarTodos;

    @FXML
    private TextField idReservaText, nombreText, idText,
            apellidoText, dniText, emailText, telefonoText;

    @FXML
    private TableView<Acompanante> recursoTable;

    @FXML
    private TableColumn<Acompanante, Integer> idColumn;
    @FXML
    private TableColumn<Acompanante, Integer> tipoColumn;     // ID Reserva Integer
    @FXML
    private TableColumn<Acompanante, String> capacidadColumn; // Nombre
    @FXML
    private TableColumn<Acompanante, String> precioColumn;    // Apellido
    @FXML
    private TableColumn<Acompanante, String> minimoColumn;    // DNI
    @FXML
    private TableColumn<Acompanante, String> estadoColumn;    // Email
    @FXML
    private TableColumn<Acompanante, String> telefonoColumn;  // Teléfono

    private ObservableList<Acompanante> AcompanantesObservable = FXCollections.observableArrayList();

    public void setMemoriaAcompanante(Memoria<Acompanante, Integer> memoriaAcompanante) {
        this.memoriaAcompanante = memoriaAcompanante;
        if (logger != null) {
            logger.info("Memoria de Acompanante asignada.");
        }
        cargarTablaCompleta();
    }

    @FXML
    private void initialize() {
        if (logger != null) {
            logger.info("Inicializando BuscaAcompananteController...");
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tipoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIdreserva().getId()));
        capacidadColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        minimoColumn.setCellValueFactory(new PropertyValueFactory<>("dni"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        recursoTable.setItems(AcompanantesObservable);
        GestorIdiomas.agregarListener(this);
        actualizarTextos();

        if (logger != null) {
            logger.info("BuscaAcompananteController inicializado.");
        }
    }

    @FXML
    private void cargarTablaCompleta() {
        if (logger != null) {
            logger.info("Cargando tabla completa de Acompanantes...");
        }
        AcompanantesObservable.clear();
        if (memoriaAcompanante != null) {
            List<Acompanante> lista = memoriaAcompanante.findAll();
            AcompanantesObservable.addAll(lista);
            if (logger != null) {
                logger.info("Se cargaron " + lista.size() + " acompañantes en la tabla.");
            }
        } else {
            if (logger != null) {
                logger.warning("Memoria de Acompanante no asignada al cargar tabla.");
            }
        }
    }

    @FXML
    private void buscar() {
        if (logger != null) {
            logger.info("Iniciando búsqueda de acompañantes...");
        }

        if (memoriaAcompanante == null) {
            if (logger != null) {
                logger.warning("Memoria de Acompanante no asignada, búsqueda abortada.");
            }
            return;
        }

        List<Acompanante> lista = memoriaAcompanante.findAll();

        List<Acompanante> filtrados = lista.stream().filter(a -> {
            boolean coincide = true;

            if (!idText.getText().isEmpty()) {
                coincide &= a.getId() != null && a.getId().toString().contains(idText.getText());
            }
            if (!idReservaText.getText().isEmpty()) {
                coincide &= a.getIdreserva().getId() != null && a.getIdreserva().getId().toString().contains(idReservaText.getText());
            }
            if (!nombreText.getText().isEmpty()) {
                coincide &= a.getNombre() != null && a.getNombre().toLowerCase().contains(nombreText.getText().toLowerCase());
            }
            if (!apellidoText.getText().isEmpty()) {
                coincide &= a.getApellido() != null && a.getApellido().toLowerCase().contains(apellidoText.getText().toLowerCase());
            }
            if (!dniText.getText().isEmpty()) {
                coincide &= a.getDni() != null && a.getDni().toLowerCase().contains(dniText.getText().toLowerCase());
            }
            if (!emailText.getText().isEmpty()) {
                coincide &= a.getEmail() != null && a.getEmail().toLowerCase().contains(emailText.getText().toLowerCase());
            }
            if (!telefonoText.getText().isEmpty()) {
                coincide &= a.getTelefono() != null && a.getTelefono().toLowerCase().contains(telefonoText.getText().toLowerCase());
            }
            return coincide;
        }).collect(Collectors.toList());

        AcompanantesObservable.setAll(filtrados);

        if (logger != null) {
            logger.info("Búsqueda completada. Resultados encontrados: " + filtrados.size());
        }
    }

    @Override
    public void idiomaCambiado() {
        if (logger != null) {
            logger.info("Idioma cambiado, actualizando textos...");
        }
        actualizarTextos();
    }

    private void actualizarTextos() {
        labelBuscarAcompanante.setText(GestorIdiomas.getTexto("buscarAcompanante"));
        labelIDAcompanante.setText(GestorIdiomas.getTexto("IDAcompanante"));
        labelIDReserva.setText(GestorIdiomas.getTexto("IDReserva"));
        labelNombre.setText(GestorIdiomas.getTexto("nombre"));
        labelApellido.setText(GestorIdiomas.getTexto("apellido"));
        labelDNI.setText(GestorIdiomas.getTexto("dni"));
        labelEmail.setText(GestorIdiomas.getTexto("email"));
        labelTelefono.setText(GestorIdiomas.getTexto("telefono"));

        btnBuscar.setText(GestorIdiomas.getTexto("buscar"));
        btnBuscarTodos.setText(GestorIdiomas.getTexto("buscarTodos"));

        idColumn.setText(GestorIdiomas.getTexto("id"));
        tipoColumn.setText(GestorIdiomas.getTexto("IDReserva"));
        capacidadColumn.setText(GestorIdiomas.getTexto("nombre"));
        precioColumn.setText(GestorIdiomas.getTexto("apellido"));
        minimoColumn.setText(GestorIdiomas.getTexto("dni"));
        estadoColumn.setText(GestorIdiomas.getTexto("email"));
        telefonoColumn.setText(GestorIdiomas.getTexto("telefono"));
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
