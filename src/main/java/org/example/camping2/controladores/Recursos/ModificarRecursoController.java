package org.example.camping2.controladores.Recursos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.memoria.Memoria;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class ModificarRecursoController implements IdiomaListener {


    @FXML
    private Label labelModificar;
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
    @FXML
    private Label labelNombre2;
    @FXML
    private Label labelTipo2;
    @FXML
    private Label labelCapacidad2;
    @FXML
    private Label labelPrecio2;
    @FXML
    private Label labelMinimo2;
    @FXML
    private Label labelEstado2;

    @FXML
    private Button btnModificar;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnBuscarTodos;


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

    private Map<String, String> mapaTipoTraducido;

    private Memoria<Recurso, Integer> memoria;

    public  void setMemoriaRecurso(Memoria<Recurso, Integer> memoria) {

            this.memoria = memoria;

            // Cargar recursos desde la memoria y mostrarlos directamente
        cargarRecursosDesdeMemoria();

    }

    @FXML
    public void initialize() {
        mapaTipoTraducido = new HashMap<>();
        mapaTipoTraducido.clear();
        mapaTipoTraducido.put("PARCELA", GestorIdiomas.getTexto("PARCELA"));
        mapaTipoTraducido.put("BUNGALOW", GestorIdiomas.getTexto("BUNGALOW"));
        mapaTipoTraducido.put("BARBACOA", GestorIdiomas.getTexto("BARBACOA"));
        ObservableList<String> traducciones = FXCollections.observableArrayList(mapaTipoTraducido.values());
        tipoCombo.setItems(traducciones);
        tipoCombo.getSelectionModel().select(0);
        tipoCombo1.setItems(traducciones);
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
        GestorIdiomas.agregarListener(this);
        actualizarTexto();

    }

    private void cargarRecursosDesdeMemoria() {
        recursoList.setAll(memoria.findAll());
        recursoTable.setItems(recursoList);
    }
    @FXML
    private void filtrarRecursos() {
        String nombreFiltro = nombreText.getText().toLowerCase().trim();
        String tipoFiltro = tipoCombo.getValue() != null ? tipoCombo.getValue().toLowerCase().trim() : "";

        String tipo = mapaTipoTraducido.entrySet().stream()
                .filter(e -> e.getValue().equals(tipoFiltro))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("PARCELA");


        String estadoFiltro = estadoText.getText().toLowerCase().trim();

        // Para los campos numéricos, parseamos con cuidado:
        Integer capacidadFiltro = parseInteger(capacidadText.getText());
        Integer precioFiltro = parseInteger(precioText.getText());
        Integer minimoPersonaFiltro = parseInteger(minimoPersonaText.getText());


        recursoList.setAll(
                memoria.findAll().stream()
                        .filter(recurso ->
                                (nombreFiltro.isEmpty() || recurso.getNombre().toLowerCase().contains(nombreFiltro)) &&
                                        (tipo.isEmpty() || recurso.getTipo().toLowerCase().contains(tipo)) &&
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
            String tipo = mapaTipoTraducido.entrySet().stream()
                    .filter(e -> e.getValue().equals(tipoNuevo))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse("PARCELA");
            String capacidadNuevo = capacidadText1.getText().trim();
            String precioNuevo = precioText1.getText().trim();
            String minimoNuevo = minimoPersonaText1.getText().trim();
            String estadoNuevo = estadoText1.getText().trim();

            if (!nombreNuevo.isEmpty()) {
                seleccionado.setNombre(nombreNuevo);
            }
            if (tipoNuevo != null && !tipoNuevo.isEmpty()) {
                seleccionado.setTipo(tipo);
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


    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }
    private void actualizarTexto(){

            labelModificar.setText(GestorIdiomas.getTexto("modificarRecurso"));
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
            labelNombre2.setText(GestorIdiomas.getTexto("nombre"));
            labelTipo2.setText(GestorIdiomas.getTexto("tipo"));
            labelCapacidad2.setText(GestorIdiomas.getTexto("capacidad"));
            labelPrecio2.setText(GestorIdiomas.getTexto("precio"));
            labelMinimo2.setText(GestorIdiomas.getTexto("minimoPersonas"));
            labelEstado2.setText(GestorIdiomas.getTexto("estado"));
            btnModificar.setText(GestorIdiomas.getTexto("modificar"));
            nombreText1.setPromptText(GestorIdiomas.getTexto("nombreText"));
            capacidadText1.setPromptText(GestorIdiomas.getTexto("capacidadText"));
            precioText1.setPromptText(GestorIdiomas.getTexto("precioText"));
            minimoPersonaText1.setPromptText(GestorIdiomas.getTexto("minimoPersonaText"));
            estadoText1.setPromptText(GestorIdiomas.getTexto("estadoText"));
    }
}
