package org.example.camping2.controladores.Recursos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.memoria.Memoria;

import java.util.HashMap;
import java.util.Map;

public class EliminarRecursoController implements IdiomaListener {

    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnBuscarTodos;
    @FXML
    private Button btnEliminar;

    @FXML
    private Label labelEliminar;
    @FXML
    private Label labelId;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelTipo;
    @FXML
    private Label labelCapacidad;
    @FXML
    private Label labelPrecio;
    @FXML
    private Label labelMinimoPersona;
    @FXML
    private Label labelEstado;

    private Memoria<Recurso, Integer> memoria;
    private Map<String, String> mapaTipoTraducido;

    @FXML
    private TextField idText;

    @FXML
    private TextField nombreText;

    @FXML
    private ComboBox<String> tipoCombo;

    @FXML
    private TextField capacidadText;

    @FXML
    private TextField precioText;

    @FXML
    private TextField minimoPersonaText;

    @FXML
    private TextField estadoText;

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

    private ObservableList<Recurso> recursoList = FXCollections.observableArrayList();

    public void setMemoriaRecurso(Memoria<Recurso, Integer> memoria) {
        this.memoria = memoria;
        cargarTabla(); // Carga inicial con todos los recursos
    }

    @FXML
    private void initialize() {
        mapaTipoTraducido = new HashMap<>();
        mapaTipoTraducido.clear();
        mapaTipoTraducido.put("PARCELA", GestorIdiomas.getTexto("PARCELA"));
        mapaTipoTraducido.put("BUNGALOW", GestorIdiomas.getTexto("BUNGALOW"));
        mapaTipoTraducido.put("BARBACOA", GestorIdiomas.getTexto("BARBACOA"));
        ObservableList<String> traducciones = FXCollections.observableArrayList(mapaTipoTraducido.values());
        tipoCombo.setItems(traducciones);
        tipoCombo.getSelectionModel().select(0);
        idColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        nombreColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        tipoColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTipo()));
        capacidadColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCapacidad()));
        precioColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPrecio()));
        minimoColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getMinimoPersonas()));
        estadoColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEstado()));

        recursoTable.setItems(recursoList);
        GestorIdiomas.agregarListener(this);
        actualizarTexto();
    }


    // Método para cargar todos los recursos en la tabla
    private void cargarTabla() {
        recursoList.setAll(memoria.findAll());
    }

    @FXML
    private void buscarRecursos() {
        recursoList.setAll(
                memoria.findAll().stream()
                        .filter(r -> {
                            boolean coincide = true;
                            if (!idText.getText().trim().isEmpty()) {
                                try {
                                    int idBuscado = Integer.parseInt(idText.getText().trim());
                                    coincide = coincide && r.getId() == idBuscado;
                                } catch (NumberFormatException e) {
                                    return false;
                                }
                            }
                            if (!nombreText.getText().trim().isEmpty()) {
                                coincide = coincide && r.getNombre().toLowerCase().contains(nombreText.getText().toLowerCase().trim());
                            }
                            if (tipoCombo.getValue() != null && !tipoCombo.getValue().isEmpty()) {
                                String tipo = mapaTipoTraducido.entrySet().stream()
                                        .filter(e -> e.getValue().equals(tipoCombo.getValue()))
                                        .map(Map.Entry::getKey)
                                        .findFirst()
                                        .orElse("PARCELA");
                                coincide = coincide && r.getTipo().toLowerCase().contains(tipo.toLowerCase());
                            }
                            if (!capacidadText.getText().trim().isEmpty()) {
                                try {
                                    int capacidadBuscada = Integer.parseInt(capacidadText.getText().trim());
                                    coincide = coincide && r.getCapacidad() == capacidadBuscada;
                                } catch (NumberFormatException e) {
                                    return false;
                                }
                            }
                            if (!precioText.getText().trim().isEmpty()) {
                                try {
                                    int precioBuscado = Integer.parseInt(precioText.getText().trim());
                                    coincide = coincide && r.getPrecio() == precioBuscado;
                                } catch (NumberFormatException e) {
                                    return false;
                                }
                            }
                            if (!minimoPersonaText.getText().trim().isEmpty()) {
                                try {
                                    int minimoBuscado = Integer.parseInt(minimoPersonaText.getText().trim());
                                    coincide = coincide && r.getMinimoPersonas() == minimoBuscado;
                                } catch (NumberFormatException e) {
                                    return false;
                                }
                            }
                            if (!estadoText.getText().trim().isEmpty()) {
                                coincide = coincide && r.getEstado().toLowerCase().contains(estadoText.getText().toLowerCase().trim());
                            }
                            return coincide;
                        })
                        .toList()
        );
    }

    @FXML
    private void buscarTodos() {
        cargarTabla();
    }


    @FXML
    private void eliminarRecurso() {
        Recurso seleccionado = recursoTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Debe seleccionar un recurso para eliminar.");
            return;
        }

        boolean eliminado = memoria.delete(seleccionado.getId());
        if (eliminado) {
            recursoList.remove(seleccionado);
            mostrarAlerta("Recurso eliminado correctamente.");
        } else {
            mostrarAlerta("Error al eliminar el recurso.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }

    private void actualizarTexto(){

        labelEliminar.setText(GestorIdiomas.getTexto("eliminarRecurso"));
        labelNombre.setText(GestorIdiomas.getTexto("nombre"));
        labelTipo.setText(GestorIdiomas.getTexto("tipo"));
        labelCapacidad.setText(GestorIdiomas.getTexto("capacidad"));
        labelId.setText(GestorIdiomas.getTexto("id"));
        labelPrecio.setText(GestorIdiomas.getTexto("precio"));
        labelMinimoPersona.setText(GestorIdiomas.getTexto("minimoPersonas"));
        labelEstado.setText(GestorIdiomas.getTexto("estado"));
        btnBuscar.setText(GestorIdiomas.getTexto("buscar"));
        btnBuscarTodos.setText(GestorIdiomas.getTexto("buscarTodos"));
        idColumn.setText(GestorIdiomas.getTexto("id"));
        nombreColumn.setText(GestorIdiomas.getTexto("nombre"));
        tipoColumn.setText(GestorIdiomas.getTexto("tipo"));
        capacidadColumn.setText(GestorIdiomas.getTexto("capacidad"));
        precioColumn.setText(GestorIdiomas.getTexto("precio"));
        minimoColumn.setText(GestorIdiomas.getTexto("minimoPersonas"));
        estadoColumn.setText(GestorIdiomas.getTexto("estado"));
        nombreText.setPromptText(GestorIdiomas.getTexto("nombreText"));
        capacidadText.setPromptText(GestorIdiomas.getTexto("capacidadText"));;
        idText.setPromptText(GestorIdiomas.getTexto("idText"));
        precioText.setPromptText(GestorIdiomas.getTexto("precioText"));
        minimoPersonaText.setPromptText(GestorIdiomas.getTexto("minimoPersonaText"));
        estadoText.setPromptText(GestorIdiomas.getTexto("estadoText"));
        btnEliminar.setText(GestorIdiomas.getTexto("eliminar"));

    }
}
