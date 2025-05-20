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

public class ModificarAcompananteController {

    @FXML private TextField idText, idReserva, nombreText, ApellidoText, dniText, emailText, telefonoText;
    @FXML private TableView<Acompanante> recursoTable;
    @FXML private TableColumn<Acompanante, Integer> idColumn, tipoColumn;
    @FXML private TableColumn<Acompanante, String> capacidadColumn, precioColumn, minimoColumn, estadoColumn;
    @FXML private TableColumn<Acompanante, String> telefonoColumn;
    @FXML private TextField nombreText1, ApellidoText1, dniText1, emailText1, telefonoText1;
    @FXML private Button modificarBtn;

    private Memoria<Acompanante, Integer> memoriaAcompanante;

    public void setMemoriaAcompanante(Memoria<Acompanante, Integer> memoriaAcompanante) {
        this.memoriaAcompanante = memoriaAcompanante;
        cargarTabla(memoriaAcompanante.findAll());
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tipoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIdreserva().getId()));
        capacidadColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        minimoColumn.setCellValueFactory(new PropertyValueFactory<>("dni"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        recursoTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> mostrarSeleccionado(newSel));
    }

    @FXML
    public void buscar() {
        String idFiltro = idText.getText().trim();
        String idReservaFiltro = idReserva.getText().trim();
        String nombreFiltro = nombreText.getText().trim().toLowerCase();
        String apellidoFiltro = ApellidoText.getText().trim().toLowerCase();
        String dniFiltro = dniText.getText().trim().toLowerCase();
        String emailFiltro = emailText.getText().trim().toLowerCase();
        String telefonoFiltro = telefonoText.getText().trim();

        List<Acompanante> filtrados = memoriaAcompanante.findAll().stream()
                .filter(a -> {
                    if (!idFiltro.isEmpty() && !(a.getId() + "").equals(idFiltro)) return false;
                    if (!idReservaFiltro.isEmpty() && !(a.getIdreserva() + "").equals(idReservaFiltro)) return false;
                    if (!nombreFiltro.isEmpty() && (a.getNombre() == null || !a.getNombre().toLowerCase().contains(nombreFiltro))) return false;
                    if (!apellidoFiltro.isEmpty() && (a.getApellido() == null || !a.getApellido().toLowerCase().contains(apellidoFiltro))) return false;
                    if (!dniFiltro.isEmpty() && (a.getDni() == null || !a.getDni().toLowerCase().contains(dniFiltro))) return false;
                    if (!emailFiltro.isEmpty() && (a.getEmail() == null || !a.getEmail().toLowerCase().contains(emailFiltro))) return false;
                    if (!telefonoFiltro.isEmpty() && (a.getTelefono() == null || !a.getTelefono().contains(telefonoFiltro))) return false;
                    return true;
                }).collect(Collectors.toList());

        cargarTabla(filtrados);
    }

    @FXML
    public void buscarTodos() {
        cargarTabla(memoriaAcompanante.findAll());
    }

    private void cargarTabla(List<Acompanante> lista) {
        ObservableList<Acompanante> datos = FXCollections.observableArrayList(lista);
        recursoTable.setItems(datos);
    }

    private void mostrarSeleccionado(Acompanante acompanante) {
        if (acompanante != null) {
            nombreText1.setText(acompanante.getNombre());
            ApellidoText1.setText(acompanante.getApellido());
            dniText1.setText(acompanante.getDni());
            emailText1.setText(acompanante.getEmail());
            telefonoText1.setText(acompanante.getTelefono());
        }
    }

    @FXML
    public void modificar() {
        Acompanante seleccionado = recursoTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección requerida", "Seleccione un acompañante de la tabla.");
            return;
        }

        // Actualizar datos
        seleccionado.setNombre(nombreText1.getText().trim());
        seleccionado.setApellido(ApellidoText1.getText().trim());
        seleccionado.setDni(dniText1.getText().trim());
        seleccionado.setEmail(emailText1.getText().trim());
        seleccionado.setTelefono(telefonoText1.getText().trim());

        if (memoriaAcompanante.update(seleccionado)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Modificado", "Acompañante actualizado correctamente.");
            cargarTabla(memoriaAcompanante.findAll());
            recursoTable.refresh();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el acompañante.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
