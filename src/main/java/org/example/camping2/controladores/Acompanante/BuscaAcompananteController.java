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

public class BuscaAcompananteController implements IdiomaListener {

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
    private TextField  idReservaText, nombreText, idText,
            apellidoText, dniText, emailText, telefonoText;

    // Tabla y columnas
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
    private TableColumn<Acompanante, String> telefonoColumn;  // Teléfono, que no tiene fx:id en FXML, se añade aquí


    private ObservableList<Acompanante> AcompanantesObservable = FXCollections.observableArrayList();

    public void setMemoriaAcompanante(Memoria<Acompanante, Integer> memoriaAcompanante) {
        this.memoriaAcompanante = memoriaAcompanante;
        cargarTablaCompleta();
    }

    @FXML
    private void initialize() {
        // Asociar columnas a propiedades del objeto Acompanante (ajustar nombres según la clase)
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
    }

    @FXML
    private void cargarTablaCompleta() {
        AcompanantesObservable.clear();
        if (memoriaAcompanante != null) {
            List<Acompanante> lista = memoriaAcompanante.findAll(); // Asegúrate que Memoria tiene método listar()
            AcompanantesObservable.addAll(lista);
        }
    }

    @FXML
    private void buscar() {
        if (memoriaAcompanante == null) return;

        List<Acompanante> lista = memoriaAcompanante.findAll();

        List<Acompanante> filtrados = lista.stream().filter(a -> {
            boolean coincide = true;

            if (!idText.getText().isEmpty()){
                coincide &= a.getId() != null &&
                        a.getId().toString().contains(idText.getText());
            }
            if (!idReservaText.getText().isEmpty()) {
                coincide &= a.getIdreserva().getId() != null &&
                        a.getIdreserva().getId().toString().contains(idReservaText.getText());
            }
            if (!nombreText.getText().isEmpty()) {
                coincide &= a.getNombre() != null &&
                        a.getNombre().toLowerCase().contains(nombreText.getText().toLowerCase());
            }
            if (!idText.getText().isEmpty()) {
                coincide &= a.getId() != null &&
                        a.getId().toString().contains(idText.getText());
            }
            if (!apellidoText.getText().isEmpty()) {
                coincide &= a.getApellido() != null &&
                        a.getApellido().toLowerCase().contains(apellidoText.getText().toLowerCase());
            }
            if (!dniText.getText().isEmpty()) {
                coincide &= a.getDni() != null &&
                        a.getDni().toLowerCase().contains(dniText.getText().toLowerCase());
            }
            if (!emailText.getText().isEmpty()) {
                coincide &= a.getEmail() != null &&
                        a.getEmail().toLowerCase().contains(emailText.getText().toLowerCase());
            }
            if (!telefonoText.getText().isEmpty()) {
                coincide &= a.getTelefono() != null &&
                        a.getTelefono().toLowerCase().contains(telefonoText.getText().toLowerCase());
            }
            return coincide;
        }).collect(Collectors.toList());

        AcompanantesObservable.setAll(filtrados);
    }

    @Override
    public void idiomaCambiado() {
        actualizarTextos();
    }

    private void actualizarTextos(){
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

        idText.setPromptText(GestorIdiomas.getTexto("idText"));
        idReservaText.setPromptText(GestorIdiomas.getTexto("idReservaText"));
        nombreText.setPromptText(GestorIdiomas.getTexto("nombreText"));
        apellidoText.setPromptText(GestorIdiomas.getTexto("apellidoField"));
        dniText.setPromptText(GestorIdiomas.getTexto("dniField"));
        emailText.setPromptText(GestorIdiomas.getTexto("emailField"));
        telefonoText.setPromptText(GestorIdiomas.getTexto("telefonoField"));
        idColumn.setText(GestorIdiomas.getTexto("id"));
        tipoColumn.setText(GestorIdiomas.getTexto("IDReserva"));
        capacidadColumn.setText(GestorIdiomas.getTexto("nombre"));
        precioColumn.setText(GestorIdiomas.getTexto("apellido"));
        minimoColumn.setText(GestorIdiomas.getTexto("dni"));
        estadoColumn.setText(GestorIdiomas.getTexto("email"));
        telefonoColumn.setText(GestorIdiomas.getTexto("telefono"));




    }
}
