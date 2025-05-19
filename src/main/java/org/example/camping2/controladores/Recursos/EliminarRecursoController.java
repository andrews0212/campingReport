package org.example.camping2.controladores.Recursos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.memoria.Memoria;

public class EliminarRecursoController {

    private Memoria<Recurso, Integer> memoria;

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
        tipoCombo.getItems().addAll("PARCELA", "BUNGALOW", "BARBACOA");
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
                                coincide = coincide && r.getTipo().toLowerCase().contains(tipoCombo.getValue().toLowerCase());
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
}
