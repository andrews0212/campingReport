package org.example.camping2.controladores.Acompanante;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.camping2.modelo.dto.Acompanante;
import org.example.camping2.modelo.memoria.Memoria;

import java.util.List;
import java.util.stream.Collectors;

public class EliminarAcompananteController {

    @FXML private TextField idText, nombreText, tipoText, capacidadText, precioText, minimoPersonaText, estadoText;
    @FXML private TableView<Acompanante> recursoTable;
    @FXML private TableColumn<Acompanante, Integer> idColumn;
    @FXML private TableColumn<Acompanante, String> nombreColumn, tipoColumn, capacidadColumn, precioColumn, minimoColumn, estadoColumn;

    private Memoria<Acompanante, Integer> memoriaAcompanante;

    public void setMemoriaAcompanante(Memoria<Acompanante, Integer> memoriaAcompanante) {
        this.memoriaAcompanante = memoriaAcompanante;
        cargarTabla(memoriaAcompanante.findAll());
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIdreserva().getIdcliente().getId().toString()));
        tipoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIdreserva().getId().toString()));
        capacidadColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        minimoColumn.setCellValueFactory(new PropertyValueFactory<>("dni"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    @FXML
    public void buscar() {
        String idFiltro = idText.getText().trim();
        String idClienteFiltro = nombreText.getText().trim().toLowerCase();
        String idReservaFiltro = tipoText.getText().trim().toLowerCase();
        String nombreFiltro = capacidadText.getText().trim().toLowerCase();
        String apellidoFiltro = precioText.getText().trim().toLowerCase();
        String dniFiltro = minimoPersonaText.getText().trim().toLowerCase();
        String emailFiltro = estadoText.getText().trim().toLowerCase();

        List<Acompanante> filtrados = memoriaAcompanante.findAll().stream()
                .filter(a -> {
                    if (!idFiltro.isEmpty() && !(a.getId() + "").equals(idFiltro)) return false;
                    if (!idClienteFiltro.isEmpty() && (a.getIdreserva().getIdcliente().getId()== null || !a.getIdreserva().getIdcliente().getId().equals(Integer.parseInt(idClienteFiltro)))) return false;
                    if (!idReservaFiltro.isEmpty() && (a.getIdreserva().getId() == null || !a.getIdreserva().getId().equals(Integer.parseInt(idReservaFiltro)))) return false;
                    if (!nombreFiltro.isEmpty() && (a.getNombre() == null || !a.getNombre().toLowerCase().contains(nombreFiltro))) return false;
                    if (!apellidoFiltro.isEmpty() && (a.getApellido() == null || !a.getApellido().toLowerCase().contains(apellidoFiltro))) return false;
                    if (!dniFiltro.isEmpty() && (a.getDni() == null || !a.getDni().toLowerCase().contains(dniFiltro))) return false;
                    if (!emailFiltro.isEmpty() && (a.getEmail() == null || !a.getEmail().toLowerCase().contains(emailFiltro))) return false;
                    return true;
                })
                .collect(Collectors.toList());

        cargarTabla(filtrados);
    }

    @FXML
    public void buscarTodos() {
        cargarTabla(memoriaAcompanante.findAll());
    }

    @FXML
    public void eliminarSeleccionado() {
        Acompanante seleccionado = recursoTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección requerida", "Seleccione un acompañante de la tabla para eliminar.");
            return;
        }

        boolean eliminado = memoriaAcompanante.delete(seleccionado.getId());
        if (eliminado) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Eliminado", "Acompañante eliminado correctamente.");
            cargarTabla(memoriaAcompanante.findAll());
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el acompañante.");
        }
    }

    private void cargarTabla(List<Acompanante> lista) {
        ObservableList<Acompanante> datos = FXCollections.observableArrayList(lista);
        recursoTable.setItems(datos);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
