package org.example.camping2.controladores.Acompanante;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Acompanante;
import org.example.camping2.modelo.memoria.Memoria;

import java.util.List;
import java.util.stream.Collectors;

public class EliminarAcompananteController implements IdiomaListener {

    @FXML private Label labelEliminarAcompanante, labelIDAcompanante, labelIDCliente, labelIDReserva, labelNombre, labelApellido, labelDNI, labelEmail;
    @FXML private Button btnBuscar, btnBuscarTodos, btnEliminar;
    @FXML private TextField idText, idClienteText, idReservaText, nombreText, apellidoField, dniText, emailText, telefonoText;
    @FXML private TableView<Acompanante> recursoTable;
    @FXML private TableColumn<Acompanante, Integer> idColumn;
    @FXML private TableColumn<Acompanante, String> idClienteColumn, idReservaColumn, nombreColumn, apellidoColumn, dniColumn, emailColumn, telefonoColumn;

    private Memoria<Acompanante, Integer> memoriaAcompanante;

    public void setMemoriaAcompanante(Memoria<Acompanante, Integer> memoriaAcompanante) {
        this.memoriaAcompanante = memoriaAcompanante;
        cargarTabla(memoriaAcompanante.findAll());
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idClienteColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIdreserva().getIdcliente().getId().toString()));
        idReservaColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIdreserva().getId().toString()));
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
        String idClienteFiltro = idClienteText.getText().trim().toLowerCase();
        String idReservaFiltro = idReservaText.getText().trim().toLowerCase();
        String nombreFiltro = nombreText.getText().trim().toLowerCase();
        String apellidoFiltro = apellidoField.getText().trim().toLowerCase();
        String dniFiltro = dniText.getText().trim().toLowerCase();
        String emailFiltro = emailText.getText().trim().toLowerCase();
        String telefonoFiltro = telefonoText.getText().trim().toLowerCase();

        List<Acompanante> filtrados = memoriaAcompanante.findAll().stream()
                .filter(a -> {
                    if (!idFiltro.isEmpty() && !(a.getId() + "").equals(idFiltro)) return false;
                    if (!idClienteFiltro.isEmpty() && (a.getIdreserva().getIdcliente().getId()== null || !a.getIdreserva().getIdcliente().getId().equals(Integer.parseInt(idClienteFiltro)))) return false;
                    if (!idReservaFiltro.isEmpty() && (a.getIdreserva().getId() == null || !a.getIdreserva().getId().equals(Integer.parseInt(idReservaFiltro)))) return false;
                    if (!nombreFiltro.isEmpty() && (a.getNombre() == null || !a.getNombre().toLowerCase().contains(nombreFiltro))) return false;
                    if (!apellidoFiltro.isEmpty() && (a.getApellido() == null || !a.getApellido().toLowerCase().contains(apellidoFiltro))) return false;
                    if (!dniFiltro.isEmpty() && (a.getDni() == null || !a.getDni().toLowerCase().contains(dniFiltro))) return false;
                    if (!emailFiltro.isEmpty() && (a.getEmail() == null || !a.getEmail().toLowerCase().contains(emailFiltro))) return false;
                    if (!telefonoFiltro.isEmpty() && (a.getEmail() == null || !a.getTelefono().toLowerCase().contains(telefonoFiltro))) return false;
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

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje ) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);

        // Obtener el Stage actual y asignarlo como propietario
        Stage stage = (Stage) dniText.getScene().getWindow(); // cualquier nodo sirve
        alerta.initOwner(stage);
        alerta.initModality(Modality.WINDOW_MODAL);  // Importante: no usar APPLICATION_MODAL

        alerta.show(); // No bloqueante
    }

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }

    private void actualizarTexto() {
        labelEliminarAcompanante.setText(GestorIdiomas.getTexto("EliminarAcompanante"));
        labelIDAcompanante.setText(GestorIdiomas.getTexto("IDAcompanante"));
        labelIDCliente.setText(GestorIdiomas.getTexto("IDCliente"));
        labelIDReserva.setText(GestorIdiomas.getTexto("IDReserva"));
        labelNombre.setText(GestorIdiomas.getTexto("nombre"));
        labelApellido.setText(GestorIdiomas.getTexto("apellido"));
        labelDNI.setText(GestorIdiomas.getTexto("dni"));
        labelEmail.setText(GestorIdiomas.getTexto("email"));
        btnBuscar.setText(GestorIdiomas.getTexto("buscar"));
        btnBuscarTodos.setText(GestorIdiomas.getTexto("buscarTodos"));
        btnEliminar.setText(GestorIdiomas.getTexto("eliminar"));



        idColumn.setText(GestorIdiomas.getTexto("id"));
        idClienteColumn.setText(GestorIdiomas.getTexto("IDCliente"));
        idReservaColumn.setText(GestorIdiomas.getTexto("IDReserva"));
        nombreColumn.setText(GestorIdiomas.getTexto("nombre"));
        apellidoColumn.setText(GestorIdiomas.getTexto("apellido"));
        dniColumn.setText(GestorIdiomas.getTexto("dni"));
        emailColumn.setText(GestorIdiomas.getTexto("email"));
        telefonoColumn.setText(GestorIdiomas.getTexto("telefono"));



    }
}
