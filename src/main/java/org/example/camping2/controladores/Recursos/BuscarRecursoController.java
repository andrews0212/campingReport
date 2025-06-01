package org.example.camping2.controladores.Recursos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.memoria.Memoria;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BuscarRecursoController implements IdiomaListener {

    private Logger logger;

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    // Labels
    @FXML private Label labelBuscarRecurso;
    @FXML private Label labelNombre;
    @FXML private Label labelTipo;
    @FXML private Label labelCapacidad;
    @FXML private Label labelId;
    @FXML private Label labelPrecio;
    @FXML private Label labelMinimoPersonas;
    @FXML private Label labelEstado;

    // TextFields
    @FXML private TextField nombreText;
    @FXML private TextField tipoText;
    @FXML private TextField capacidadText;
    @FXML private TextField idText;
    @FXML private TextField precioText;
    @FXML private TextField minimoPersonaText;
    @FXML private TextField estadoText;

    // Buttons
    @FXML private Button buscarButton;
    @FXML private Button buscarTodosButton;

    // Tabla
    @FXML private TableView<Recurso> recursoTable;
    @FXML private TableColumn<Recurso, Integer> idColumn;
    @FXML private TableColumn<Recurso, String> nombreColumn;
    @FXML private TableColumn<Recurso, String> tipoColumn;
    @FXML private TableColumn<Recurso, Integer> capacidadColumn;
    @FXML private TableColumn<Recurso, Integer> precioColumn;
    @FXML private TableColumn<Recurso, Integer> minimoColumn;
    @FXML private TableColumn<Recurso, String> estadoColumn;

    private Memoria<Recurso, Integer> memoriaRecurso;
    private ObservableList<Recurso> recursoList = FXCollections.observableArrayList();

    public void setMemoriaRecurso(Memoria<Recurso, Integer> memoriaRecurso) {
        this.memoriaRecurso = memoriaRecurso;
        if (logger != null) logger.info("Memoria de recursos configurada.");
        cargarTodosRecursos();
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        nombreColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        tipoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTipo()));
        capacidadColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCapacidad()));
        precioColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPrecio()));
        minimoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getMinimoPersonas()));
        estadoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEstado()));
        recursoTable.setItems(recursoList);
        GestorIdiomas.agregarListener(this);
        actualizarTexto();
    }

    @FXML
    private void Buscar() {
        if (logger != null) logger.info("Botón 'Buscar' pulsado.");
        if (memoriaRecurso == null) return;

        List<Recurso> recursos = memoriaRecurso.findAll();

        List<Recurso> filtrados = recursos.stream().filter(r -> {
            boolean matches = true;
            try {
                if (!nombreText.getText().trim().isEmpty())
                    matches &= r.getNombre().toLowerCase().contains(nombreText.getText().toLowerCase().trim());
                if (!tipoText.getText().trim().isEmpty())
                    matches &= r.getTipo().toLowerCase().contains(tipoText.getText().toLowerCase().trim());
                if (!capacidadText.getText().trim().isEmpty()) {
                    int cap = Integer.parseInt(capacidadText.getText().trim());
                    matches &= r.getCapacidad() == cap;
                }
                if (!idText.getText().trim().isEmpty()) {
                    int idBuscado = Integer.parseInt(idText.getText().trim());
                    matches &= r.getId() == idBuscado;
                }
                if (!precioText.getText().trim().isEmpty()) {
                    double precioBuscado = Double.parseDouble(precioText.getText().trim());
                    matches &= r.getPrecio() == precioBuscado;
                }
                if (!minimoPersonaText.getText().trim().isEmpty()) {
                    int minPers = Integer.parseInt(minimoPersonaText.getText().trim());
                    matches &= r.getMinimoPersonas() == minPers;
                }
                if (!estadoText.getText().trim().isEmpty())
                    matches &= r.getEstado().toLowerCase().contains(estadoText.getText().toLowerCase().trim());
            } catch (NumberFormatException e) {
                if (logger != null)
                    logger.log(Level.WARNING, "Error de formato numérico durante la búsqueda: " + e.getMessage(), e);
                matches = false;
            }
            return matches;
        }).collect(Collectors.toList());

        if (logger != null)
            logger.info("Recursos encontrados: " + filtrados.size());

        recursoList.setAll(filtrados);
    }

    @FXML
    private void BuscarTodos() {
        if (logger != null) logger.info("Botón 'Buscar Todos' pulsado.");
        cargarTodosRecursos();
    }

    private void cargarTodosRecursos() {
        if (memoriaRecurso == null) return;
        recursoList.setAll(memoriaRecurso.findAll());
        if (logger != null) logger.info("Cargados todos los recursos: " + recursoList.size());
    }

    @Override
    public void idiomaCambiado() {
        if (logger != null) logger.info("Idioma cambiado, actualizando textos.");
        actualizarTexto();
    }

    public void actualizarTexto() {
        labelBuscarRecurso.setText(GestorIdiomas.getTexto("buscarRecurso"));
        labelNombre.setText(GestorIdiomas.getTexto("nombre"));
        labelTipo.setText(GestorIdiomas.getTexto("tipo"));
        labelCapacidad.setText(GestorIdiomas.getTexto("capacidad"));
        labelId.setText(GestorIdiomas.getTexto("id"));
        labelPrecio.setText(GestorIdiomas.getTexto("precio"));
        labelMinimoPersonas.setText(GestorIdiomas.getTexto("minimoPersonas"));
        labelEstado.setText(GestorIdiomas.getTexto("estado"));
        buscarButton.setText(GestorIdiomas.getTexto("buscar"));
        buscarTodosButton.setText(GestorIdiomas.getTexto("buscarTodos"));
        idColumn.setText(GestorIdiomas.getTexto("id"));
        nombreColumn.setText(GestorIdiomas.getTexto("nombre"));
        tipoColumn.setText(GestorIdiomas.getTexto("tipo"));
        capacidadColumn.setText(GestorIdiomas.getTexto("capacidad"));
        precioColumn.setText(GestorIdiomas.getTexto("precio"));
        minimoColumn.setText(GestorIdiomas.getTexto("minimoPersonas"));
        estadoColumn.setText(GestorIdiomas.getTexto("estado"));
    }
}
