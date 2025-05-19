package org.example.camping2.controladores.Recursos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.memoria.Memoria;

import java.net.URL;
import java.util.ResourceBundle;

public class ModificarRecursoController {

    @FXML
    private TextField idText;

    @FXML
    private TextField nombreText;

    @FXML
    private ComboBox<String> tipoCombo; // No tiene fx:id, debes añadírselo en el FXML (sugerencia: fx:id="tipoComboBox")

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

    private final ObservableList<Recurso> recursoList = FXCollections.observableArrayList();


    @FXML
    private TextField nombreText1;

    @FXML
    private ComboBox<String> tipoCombo1; // También deberías asignarle un fx:id, como fx:id="tipoComboBox1"

    @FXML
    private TextField capacidadText1;

    @FXML
    private TextField precioText1;

    @FXML
    private TextField minimoPersonaText1;

    @FXML
    private TextField estadoText1;


    private Memoria<Recurso, Integer> memoria;

    public  void setMemoriaRecurso(Memoria<Recurso, Integer> memoria) {

            this.memoria = memoria;

            // Cargar recursos desde la memoria y mostrarlos directamente
        cargarRecursosDesdeMemoria();

    }

    @FXML
    public void initialize() {
        tipoCombo.getItems().addAll("PARCELA", "BUNGALOW", "BARBACOA");
        tipoCombo.getSelectionModel().select(0);
        tipoCombo1.getItems().addAll("PARCELA", "BUNGALOW", "BARBACOA");
        tipoCombo1.getSelectionModel().select(0);
        // Asociar columnas con propiedades del modelo Recurso
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        capacidadColumn.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        minimoColumn.setCellValueFactory(new PropertyValueFactory<>("minimoPersonas"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        recursoTable.setItems(recursoList);
    }

    private void cargarRecursosDesdeMemoria() {
        recursoList.setAll(memoria.findAll());
        recursoTable.setItems(recursoList);
    }
    @FXML
    private void filtrarRecursos() {
        String nombreFiltro = nombreText.getText().toLowerCase().trim();
        String tipoFiltro = tipoCombo.getValue() != null ? tipoCombo.getValue().toLowerCase().trim() : "";
        String estadoFiltro = estadoText.getText().toLowerCase().trim();

        // Para los campos numéricos, parseamos con cuidado:
        Integer capacidadFiltro = parseInteger(capacidadText.getText());
        Integer precioFiltro = parseInteger(precioText.getText());
        Integer minimoPersonaFiltro = parseInteger(minimoPersonaText.getText());

        recursoList.setAll(
                memoria.findAll().stream()
                        .filter(recurso ->
                                (nombreFiltro.isEmpty() || recurso.getNombre().toLowerCase().contains(nombreFiltro)) &&
                                        (tipoFiltro.isEmpty() || recurso.getTipo().toLowerCase().contains(tipoFiltro)) &&
                                        (estadoFiltro.isEmpty() || recurso.getEstado().toLowerCase().contains(estadoFiltro)) &&

                                        (capacidadFiltro == null || recurso.getCapacidad() == capacidadFiltro) &&
                                        (precioFiltro == null || recurso.getPrecio() == precioFiltro) &&
                                        (minimoPersonaFiltro == null || recurso.getMinimoPersonas() == minimoPersonaFiltro)
                        )
                        .toList()
        );
    }

    @FXML
    private void modificarRecurso() {
        Recurso seleccionado = recursoTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            String nombreNuevo = nombreText1.getText().trim();
            String tipoNuevo = tipoCombo1.getValue();
            String capacidadNuevo = capacidadText1.getText().trim();
            String precioNuevo = precioText1.getText().trim();
            String minimoNuevo = minimoPersonaText1.getText().trim();
            String estadoNuevo = estadoText1.getText().trim();

            if (!nombreNuevo.isEmpty()) {
                seleccionado.setNombre(nombreNuevo);
            }
            if (tipoNuevo != null && !tipoNuevo.isEmpty()) {
                seleccionado.setTipo(tipoNuevo);
            }
            if (!capacidadNuevo.isEmpty()) {
                try {
                    seleccionado.setCapacidad(Integer.parseInt(capacidadNuevo));
                } catch (NumberFormatException e) {
                    System.err.println("Capacidad inválida.");
                    return;
                }
            }
            if (!precioNuevo.isEmpty()) {
                try {
                    seleccionado.setPrecio(Integer.parseInt(precioNuevo));
                } catch (NumberFormatException e) {
                    System.err.println("Precio inválido.");
                    return;
                }
            }
            if (!minimoNuevo.isEmpty()) {
                try {
                    seleccionado.setMinimoPersonas(Integer.parseInt(minimoNuevo));
                } catch (NumberFormatException e) {
                    System.err.println("Mínimo personas inválido.");
                    return;
                }
            }
            if (!estadoNuevo.isEmpty()) {
                seleccionado.setEstado(estadoNuevo);
            }

            if (memoria.update(seleccionado)) {
                recursoTable.refresh();
            } else {
                System.err.println("Error al actualizar el recurso en memoria.");
            }
        }
    }
    @FXML
    public void buscarTodos(){
        cargarRecursosDesdeMemoria();
    }


    private Integer parseInteger(String texto) {
        try {
            if (texto == null || texto.trim().isEmpty()) {
                return null; // Si el texto está vacío o es nulo, devuelvo null
            }
            return Integer.parseInt(texto.trim()); // Si no, intento parsear
        } catch (NumberFormatException e) {
            return null; // Si no es un número válido, devuelvo null para ignorar el filtro
        }
    }



}
