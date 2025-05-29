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
import java.util.stream.Collectors;

public class ModificarAcompananteController implements IdiomaListener {

    @FXML private Label labelModificarAcompanante;
    @FXML private Button btnBuscar, btnBuscarTodos;
    @FXML private Label labelIDAcompanante, labelIDReserva, labelNombre, labelApellido, labelDNI, labelEmail, labelTelefono;
    @FXML private Label labelNombre2, labelApellido2, labelDNI2, labelEmail2, labelTelefono2;

    @FXML private TextField idText, idReserva, nombreText, ApellidoText, dniText, emailText, telefonoText;
    @FXML private TableView<Acompanante> recursoTable;
    @FXML private TableColumn<Acompanante, Integer> idColumn, idReservaColum;
    @FXML private TableColumn<Acompanante, String> nombreColumn, apellidoColumn, dniColumn, emailColumn;
    @FXML private TableColumn<Acompanante, String> telefonoColumn;
    @FXML private TextField nombreText1, ApellidoText1, dniText1, emailText1, telefonoText1;
    @FXML private Button btnModificar;

    private Memoria<Acompanante, Integer> memoriaAcompanante;

    public void setMemoriaAcompanante(Memoria<Acompanante, Integer> memoriaAcompanante) {
        this.memoriaAcompanante = memoriaAcompanante;
        cargarTabla(memoriaAcompanante.findAll());
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idReservaColum.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIdreserva().getId()));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidoColumn.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        dniColumn.setCellValueFactory(new PropertyValueFactory<>("dni"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        GestorIdiomas.agregarListener(this);
        actualizarTexto();
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

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }

    private void actualizarTexto() {
        labelModificarAcompanante.setText(GestorIdiomas.getTexto("modificarAcompanante"));
        btnBuscar.setText(GestorIdiomas.getTexto("buscar"));
        btnBuscarTodos.setText(GestorIdiomas.getTexto("buscarTodos"));
        labelIDAcompanante.setText(GestorIdiomas.getTexto("labelIDAcompanante"));
        labelIDReserva.setText(GestorIdiomas.getTexto("labelIDReserva"));
        labelNombre.setText(GestorIdiomas.getTexto("nombre"));
        labelApellido.setText(GestorIdiomas.getTexto("apellido"));
        labelDNI.setText(GestorIdiomas.getTexto("dni"));
        labelEmail.setText(GestorIdiomas.getTexto("email"));
        labelTelefono.setText(GestorIdiomas.getTexto("telefono"));
        labelNombre2.setText(GestorIdiomas.getTexto("nombre"));
        labelApellido2.setText(GestorIdiomas.getTexto("apellido"));
        labelDNI2.setText(GestorIdiomas.getTexto("dni"));
        labelEmail2.setText(GestorIdiomas.getTexto("email"));
        labelTelefono2.setText(GestorIdiomas.getTexto("telefono"));
        btnModificar.setText(GestorIdiomas.getTexto("modificar"));

        idColumn.setText(GestorIdiomas.getTexto("labelIDAcompanante"));
        idReservaColum.setText(GestorIdiomas.getTexto("labelIDReserva"));
        nombreColumn.setText(GestorIdiomas.getTexto("nombre"));
        apellidoColumn.setText(GestorIdiomas.getTexto("apellido"));
        dniColumn.setText(GestorIdiomas.getTexto("dni"));
        emailColumn.setText(GestorIdiomas.getTexto("email"));
        telefonoColumn.setText(GestorIdiomas.getTexto("telefono"));

        idText.setPromptText(GestorIdiomas.getTexto("labelIDAcompanante"));
        idReserva.setPromptText(GestorIdiomas.getTexto("labelIDReserva"));
        nombreText.setPromptText(GestorIdiomas.getTexto("nombreText"));
        ApellidoText.setPromptText(GestorIdiomas.getTexto("apellidoField"));
        dniText.setPromptText(GestorIdiomas.getTexto("dniField"));
        emailText.setPromptText(GestorIdiomas.getTexto("emailField"));
        telefonoText.setPromptText(GestorIdiomas.getTexto("telefonoField"));
        nombreText1.setPromptText(GestorIdiomas.getTexto("nombreText"));
        ApellidoText1.setPromptText(GestorIdiomas.getTexto("apellidoField"));
        dniText1.setPromptText(GestorIdiomas.getTexto("dniField"));
        emailText1.setPromptText(GestorIdiomas.getTexto("emailField"));
        telefonoText1.setPromptText(GestorIdiomas.getTexto("telefonoField"));



    }

}
