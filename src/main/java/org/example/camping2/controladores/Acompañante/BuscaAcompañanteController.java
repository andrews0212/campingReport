package org.example.camping2.controladores.Acompañante;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.camping2.modelo.dto.Acompañante;
import org.example.camping2.modelo.memoria.Memoria;

import java.util.List;
import java.util.stream.Collectors;

public class BuscaAcompañanteController {

    private Memoria<Acompañante, Integer> memoriaAcompañante;

    // TextFields (con fx:id del FXML)
    @FXML
    private TextField idClienteText, idReservaText, nombreText, idText,
            apellidoText, dniText, emailText, telefonoText;

    // Tabla y columnas
    @FXML
    private TableView<Acompañante> recursoTable;

    @FXML
    private TableColumn<Acompañante, Integer> idColumn;
    @FXML
    private TableColumn<Acompañante, Integer> nombreColumn;   // Aquí se asume que ID Cliente es Integer
    @FXML
    private TableColumn<Acompañante, Integer> tipoColumn;     // ID Reserva Integer
    @FXML
    private TableColumn<Acompañante, String> capacidadColumn; // Nombre
    @FXML
    private TableColumn<Acompañante, String> precioColumn;    // Apellido
    @FXML
    private TableColumn<Acompañante, String> minimoColumn;    // DNI
    @FXML
    private TableColumn<Acompañante, String> estadoColumn;    // Email
    @FXML
    private TableColumn<Acompañante, String> telefonoColumn;  // Teléfono, que no tiene fx:id en FXML, se añade aquí


    private ObservableList<Acompañante> acompañantesObservable = FXCollections.observableArrayList();

    public void setMemoriaAcompañante(Memoria<Acompañante, Integer> memoriaAcompañante) {
        this.memoriaAcompañante = memoriaAcompañante;
        cargarTablaCompleta();
    }

    @FXML
    private void initialize() {
        // Asociar columnas a propiedades del objeto Acompañante (ajustar nombres según la clase)
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("idCliente"));  // Cambia por el nombre real
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("idReserva"));   // Cambia por el nombre real
        capacidadColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        minimoColumn.setCellValueFactory(new PropertyValueFactory<>("dni"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Como el teléfono no tiene fx:id en FXML, para mostrarlo hay que asignarlo aquí con código
        // Para esto, lo mejor es darle fx:id al TableColumn del teléfono en FXML y enlazarlo aquí,
        // o crear una columna programáticamente.
        // Aquí asumo que has añadido fx:id="telefonoColumn" en el FXML para la columna Teléfono:
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        recursoTable.setItems(acompañantesObservable);
    }

    @FXML
    private void cargarTablaCompleta() {
        acompañantesObservable.clear();
        if (memoriaAcompañante != null) {
            List<Acompañante> lista = memoriaAcompañante.findAll(); // Asegúrate que Memoria tiene método listar()
            acompañantesObservable.addAll(lista);
        }
    }

    @FXML
    private void buscar() {
        if (memoriaAcompañante == null) return;

        List<Acompañante> lista = memoriaAcompañante.findAll();

        List<Acompañante> filtrados = lista.stream().filter(a -> {
            boolean coincide = true;

            if (!idClienteText.getText().isEmpty()) {
                coincide &= a.getIdreserva().getIdcliente().getId() != null &&
                        a.getIdreserva().getIdcliente().getId().toString().contains(idClienteText.getText());
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

        acompañantesObservable.setAll(filtrados);
    }
}
