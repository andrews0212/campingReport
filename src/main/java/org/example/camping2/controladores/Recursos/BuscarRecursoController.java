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
import java.util.stream.Collectors;

public class BuscarRecursoController implements IdiomaListener {

    //label

    @FXML
    private Label labelBuscarRecurso;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelTipo;
    @FXML
    private Label labelCapacidad;
    @FXML
    private Label labelId;
    @FXML
    private Label labelPrecio;
    @FXML
    private Label labelMinimoPersonas;
    @FXML
    private Label labelEstado;

    //text

    @FXML
    private TextField nombreText;
    @FXML
    private TextField tipoText;
    @FXML
    private TextField capacidadText;
    @FXML
    private TextField idText;
    @FXML
    private TextField precioText;
    @FXML
    private TextField minimoPersonaText;
    @FXML
    private TextField estadoText;

    //Button
    @FXML
    private Button buscarButton;
    @FXML
    private Button buscarTodosButton;



    @FXML
    private TableView<Recurso> recursoTable;

    @FXML
    private TableColumn<Recurso, Integer> idColumn;
    @FXML
    private TableColumn<Recurso, String> nombreColumn;
    @FXML
    private TableColumn<Recurso, String> tipoColumn;
    @FXML
    private TableColumn<Recurso, Integer> capacidadColumn;
    @FXML
    private TableColumn<Recurso, Integer> precioColumn;
    @FXML
    private TableColumn<Recurso, Integer> minimoColumn;
    @FXML
    private TableColumn<Recurso, String> estadoColumn;

    private Memoria<Recurso, Integer> memoriaRecurso;

    private ObservableList<Recurso> recursoList = FXCollections.observableArrayList();

    public void setMemoriaRecurso(Memoria<Recurso, Integer> memoriaRecurso) {
        this.memoriaRecurso = memoriaRecurso;
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
        if (memoriaRecurso == null) return;

        List<Recurso> recursos = memoriaRecurso.findAll();

        // Filtrar según los campos que no estén vacíos
        List<Recurso> filtrados = recursos.stream().filter(r -> {
            boolean matches = true;
            // Para cada campo, si el TextField no está vacío, filtrar por coincidencia
            if (!nombreText.getText().trim().isEmpty()) {
                matches = matches && r.getNombre().toLowerCase().contains(nombreText.getText().toLowerCase().trim());
            }
            if (!tipoText.getText().trim().isEmpty()) {
                matches = matches && r.getTipo().toLowerCase().contains(tipoText.getText().toLowerCase().trim());
            }
            if (!capacidadText.getText().trim().isEmpty()) {
                try {
                    int cap = Integer.parseInt(capacidadText.getText().trim());
                    matches = matches && r.getCapacidad() == cap;
                } catch (NumberFormatException e) {
                    // No filtrar si no es un número válido
                }
            }
            if (!idText.getText().trim().isEmpty()) {
                try {
                    int idBuscado = Integer.parseInt(idText.getText().trim());
                    matches = matches && r.getId() == idBuscado;
                } catch (NumberFormatException e) {
                }
            }
            if (!precioText.getText().trim().isEmpty()) {
                try {
                    double precioBuscado = Double.parseDouble(precioText.getText().trim());
                    matches = matches && r.getPrecio() == precioBuscado;
                } catch (NumberFormatException e) {
                }
            }
            if (!minimoPersonaText.getText().trim().isEmpty()) {
                try {
                    int minPers = Integer.parseInt(minimoPersonaText.getText().trim());
                    matches = matches && r.getMinimoPersonas() == minPers;
                } catch (NumberFormatException e) {
                }
            }
            if (!estadoText.getText().trim().isEmpty()) {
                matches = matches && r.getEstado().toLowerCase().contains(estadoText.getText().toLowerCase().trim());
            }

            return matches;
        }).collect(Collectors.toList());

        recursoList.setAll(filtrados);
    }

    @FXML
    private void BuscarTodos() {
        cargarTodosRecursos();
    }

    private void cargarTodosRecursos() {
        if (memoriaRecurso == null) return;
        recursoList.setAll(memoriaRecurso.findAll());
    }

    @Override

    public void idiomaCambiado() {
    actualizarTexto();
    }
    public void actualizarTexto(){
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
