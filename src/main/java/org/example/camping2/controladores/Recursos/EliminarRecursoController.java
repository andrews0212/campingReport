package org.example.camping2.controladores.Recursos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.memoria.Memoria;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class EliminarRecursoController implements IdiomaListener {
    private Logger logger;

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
        mapaTipoTraducido.put("PARCELA", GestorIdiomas.getTexto("PARCELA"));
        mapaTipoTraducido.put("BUNGALOW", GestorIdiomas.getTexto("BUNGALOW"));
        mapaTipoTraducido.put("BARBACOA", GestorIdiomas.getTexto("BARBACOA"));
        ObservableList<String> traducciones = FXCollections.observableArrayList(mapaTipoTraducido.values());
        tipoCombo.setItems(traducciones);
        tipoCombo.getSelectionModel().select(0);

        // Configuración de columnas...
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        nombreColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        tipoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTipo()));
        capacidadColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCapacidad()));
        precioColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPrecio()));
        minimoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getMinimoPersonas()));
        estadoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEstado()));

        recursoTable.setItems(recursoList);
        GestorIdiomas.agregarListener(this);
        actualizarTexto();
    }

    private void cargarTabla() {
        logger.info("Cargando todos los recursos en la tabla");
        recursoList.setAll(memoria.findAll());
    }

    @FXML
    private void buscarRecursos() {
        logger.info("Buscando recursos con los filtros ingresados");
        recursoList.setAll(
                memoria.findAll().stream()
                        .filter(r -> {
                            boolean coincide = true;
                            try {
                                if (!idText.getText().trim().isEmpty()) {
                                    int idBuscado = Integer.parseInt(idText.getText().trim());
                                    coincide = coincide && r.getId() == idBuscado;
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
                                    int capacidadBuscada = Integer.parseInt(capacidadText.getText().trim());
                                    coincide = coincide && r.getCapacidad() == capacidadBuscada;
                                }
                                if (!precioText.getText().trim().isEmpty()) {
                                    int precioBuscado = Integer.parseInt(precioText.getText().trim());
                                    coincide = coincide && r.getPrecio() == precioBuscado;
                                }
                                if (!minimoPersonaText.getText().trim().isEmpty()) {
                                    int minimoBuscado = Integer.parseInt(minimoPersonaText.getText().trim());
                                    coincide = coincide && r.getMinimoPersonas() == minimoBuscado;
                                }
                                if (!estadoText.getText().trim().isEmpty()) {
                                    coincide = coincide && r.getEstado().toLowerCase().contains(estadoText.getText().toLowerCase().trim());
                                }
                            } catch (NumberFormatException e) {
                                logger.warning("Error al convertir valor numérico durante búsqueda: " + e.getMessage());
                                return false;
                            }
                            return coincide;
                        })
                        .toList()
        );
    }

    @FXML
    private void buscarTodos() {
        logger.info("Acción: Buscar todos los recursos");
        cargarTabla();
    }

    @FXML
    private void eliminarRecurso() {
        try {
            Recurso seleccionado = recursoTable.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                logger.warning("Intento de eliminar sin seleccionar un recurso");
                mostrarAlerta("Error","Debe seleccionar un recurso para eliminar.", Alert.AlertType.WARNING);
                return;
            }

            logger.info("Eliminando recurso con ID: " + seleccionado.getId());
            boolean eliminado = memoria.delete(seleccionado.getId());
            if (eliminado) {
                logger.info("Recurso eliminado exitosamente: " + seleccionado.getId());
                recursoList.remove(seleccionado);
                mostrarAlerta("Error","Recurso eliminado correctamente.", Alert.AlertType.WARNING);
            } else {
                logger.severe("Error al eliminar el recurso con ID: " + seleccionado.getId());
                mostrarAlerta("Error","Error al eliminar el recurso.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            logger.severe("Error al eliminar el recurso: " + e.getMessage());

            // Aquí detectamos si la causa es la violación de FK
            Throwable cause = e;
            while (cause != null) {
                if (cause instanceof org.hibernate.exception.ConstraintViolationException
                        || (cause.getMessage() != null && cause.getMessage().contains("Cannot delete or update a parent row"))) {
                    mostrarAlerta("Error","No se puede eliminar el recurso porque está siendo usado en una reserva.", Alert.AlertType.WARNING);
                    logger.severe("No se puede eliminar el recurso porque está siendo usado en una reserva.");
                    break;
                }
                cause = cause.getCause();
            }

            // Si no era por FK, mostramos el mensaje genérico
            if (cause == null) {
                mostrarAlerta("Error", "Error al eliminar el recurso: " + e.getMessage(), Alert.AlertType.WARNING);
            }
        }
    }


    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);

        Stage stage = (Stage) tipoCombo.getScene().getWindow();
        alerta.initOwner(stage);
        alerta.initModality(Modality.WINDOW_MODAL);
        alerta.show();
        logger.info("Mostrando alerta - Tipo: " + tipo + ", Título: " + titulo + ", Mensaje: " + mensaje);
    }

    @Override
    public void idiomaCambiado() {
        logger.info("Idioma cambiado, actualizando textos");
        actualizarTexto();
    }

    private void actualizarTexto() {

        labelEliminar.setText(GestorIdiomas.getTexto("eliminarRecurso"));
        labelNombre.setText(GestorIdiomas.getTexto("nombre"));
        labelTipo.setText(GestorIdiomas.getTexto("tipo"));
        labelCapacidad.setText(GestorIdiomas.getTexto("capacidad"));
        labelId.setText(GestorIdiomas.getTexto("labelIDRecurso"));
        labelPrecio.setText(GestorIdiomas.getTexto("precio"));
        labelMinimoPersona.setText(GestorIdiomas.getTexto("minimoPersonas"));
        labelEstado.setText(GestorIdiomas.getTexto("estado"));
        btnBuscar.setText(GestorIdiomas.getTexto("buscar"));
        btnBuscarTodos.setText(GestorIdiomas.getTexto("buscarTodos"));
        idColumn.setText(GestorIdiomas.getTexto("labelIDRecurso"));
        nombreColumn.setText(GestorIdiomas.getTexto("nombre"));
        tipoColumn.setText(GestorIdiomas.getTexto("tipo"));
        capacidadColumn.setText(GestorIdiomas.getTexto("capacidad"));
        precioColumn.setText(GestorIdiomas.getTexto("precio"));
        minimoColumn.setText(GestorIdiomas.getTexto("minimoPersonas"));
        estadoColumn.setText(GestorIdiomas.getTexto("estado"));
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
        logger.info("Inicializando EliminarRecursoController");
    }
}
