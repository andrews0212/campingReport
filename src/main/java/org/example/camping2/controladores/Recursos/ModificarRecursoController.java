package org.example.camping2.controladores.Recursos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.memoria.Memoria;
import org.example.camping2.modelo.validaciones.ValidarRecurso;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ModificarRecursoController implements IdiomaListener {

    private Logger logger;

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @FXML
    private Label labelModificar, labelId, labelNombre, labelTipo, labelCapacidad, labelPrecio, labelMinimoPersona, labelEstado;
    @FXML
    private Label labelNombre2, labelTipo2, labelCapacidad2, labelPrecio2, labelMinimo2, labelEstado2;
    @FXML
    private Button btnModificar, btnBuscar, btnBuscarTodos;
    @FXML
    private TextField idText, nombreText, capacidadText, precioText, minimoPersonaText, estadoText;
    @FXML
    private ComboBox<String> tipoCombo;
    @FXML
    private TableView<Recurso> recursoTable;
    @FXML
    private TableColumn<Recurso, Integer> idColumn, capacidadColumn, precioColumn, minimoColumn;
    @FXML
    private TableColumn<Recurso, String> nombreColumn, tipoColumn, estadoColumn;
    @FXML
    private TextField nombreText1, capacidadText1, precioText1, minimoPersonaText1;
    @FXML
    private ComboBox<String> tipoCombo1;
    @FXML
    private ComboBox<String> estadoCombo;


    private final ObservableList<Recurso> recursoList = FXCollections.observableArrayList();
    private Map<String, String> mapaTipoTraducido;
    private Map<String, String> mapaEstadoTraducido;

    private Memoria<Recurso, Integer> memoria;

    public void setMemoriaRecurso(Memoria<Recurso, Integer> memoria) {
        this.memoria = memoria;
        if (logger != null) {
            logger.info("Memoria de recursos establecida.");
        }
        cargarRecursosDesdeMemoria();
    }

    @FXML
    public void initialize() {
        if (logger != null) {
            logger.info("Inicializando controlador ModificarRecursoController.");
        }
        mapaTipoTraducido = new HashMap<>();
        mapaTipoTraducido.put("PARCELA", GestorIdiomas.getTexto("PARCELA"));
        mapaTipoTraducido.put("BUNGALOW", GestorIdiomas.getTexto("BUNGALOW"));
        mapaTipoTraducido.put("BARBACOA", GestorIdiomas.getTexto("BARBACOA"));

        ObservableList<String> traducciones = FXCollections.observableArrayList(mapaTipoTraducido.values());
        tipoCombo.setItems(traducciones);
        tipoCombo.getSelectionModel().select(0);
        tipoCombo1.setItems(traducciones);
        tipoCombo1.getSelectionModel().select(0);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        capacidadColumn.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        minimoColumn.setCellValueFactory(new PropertyValueFactory<>("minimoPersonas"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        recursoTable.setItems(recursoList);

        mapaEstadoTraducido = new HashMap<>();
        mapaEstadoTraducido.put("DISPONIBLE", GestorIdiomas.getTexto("DISPONIBLE"));
        mapaEstadoTraducido.put("OCUPADO", GestorIdiomas.getTexto("OCUPADO"));
        mapaEstadoTraducido.put("MANTENIMIENTO", GestorIdiomas.getTexto("MANTENIMIENTO"));
        traducciones = FXCollections.observableArrayList(mapaEstadoTraducido.values());
        estadoCombo.setItems(traducciones);
        estadoCombo.getSelectionModel().select(0);

        GestorIdiomas.agregarListener(this);
        actualizarTexto();
    }

    private void cargarRecursosDesdeMemoria() {
        recursoList.setAll(memoria.findAll());
        if (logger != null) {
            logger.info("Recursos cargados desde memoria: " + recursoList.size());
        }
        recursoTable.setItems(recursoList);
    }

    @FXML
    private void filtrarRecursos() {
        if (logger != null) {
            logger.info("Filtrando recursos...");
        }
        try {
            String id = idText.getText().trim();
            String nombreFiltro = nombreText.getText().toLowerCase().trim();
            String tipoFiltro = tipoCombo.getValue() != null ? tipoCombo.getValue().toLowerCase().trim() : "";

            String tipo = mapaTipoTraducido.entrySet().stream()
                    .filter(e -> e.getValue().equalsIgnoreCase(tipoFiltro))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse("");

            String estadoFiltro = estadoText.getText().toLowerCase().trim();

            Integer capacidadFiltro = parseInteger(capacidadText.getText());
            Integer precioFiltro = parseInteger(precioText.getText());
            Integer minimoPersonaFiltro = parseInteger(minimoPersonaText.getText());

            recursoList.setAll(
                    memoria.findAll().stream()
                            .filter(recurso ->
                                    (nombreFiltro.isEmpty() || recurso.getNombre().toLowerCase().contains(nombreFiltro)) &&
                                            (tipoFiltro.isEmpty() || recurso.getTipo().equalsIgnoreCase(tipo)) &&
                                            (estadoFiltro.isEmpty() || recurso.getEstado().toLowerCase().contains(estadoFiltro)) &&
                                            (capacidadFiltro == null || recurso.getCapacidad() == capacidadFiltro) &&
                                            (precioFiltro == null || recurso.getPrecio() == precioFiltro) &&
                                            (minimoPersonaFiltro == null || recurso.getMinimoPersonas() == minimoPersonaFiltro) &&
                                            (id.isEmpty() || recurso.getId().equals(Integer.parseInt(id)))
                            ).toList()
            );

            if (logger != null) {
                logger.info("Filtrado completado. Recursos encontrados: " + recursoList.size());
            }
        } catch (Exception e) {
            if (logger != null) {
                logger.severe("Error al filtrar recursos: " + e.getMessage());
            }
        }
    }

    @FXML
    private void modificarRecurso() {
        Recurso seleccionado = recursoTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Debe seleccionar un recurso de la tabla.");
            if (logger != null) {
                logger.warning("Intento de modificar sin seleccionar recurso.");
            }
            return;
        }
        if (logger != null) {
            logger.info("Modificando recurso con ID: " + seleccionado.getId());
        }
        try {
            String nombreNuevo = nombreText1.getText().trim();
            String tipoNuevo = tipoCombo1.getValue();
            String tipo = mapaTipoTraducido.entrySet().stream()
                    .filter(e -> e.getValue().equals(tipoNuevo))
                    .map(Map.Entry::getKey)
                    .findFirst().orElse(null);

            String capacidadNuevo = capacidadText1.getText().trim();
            String precioNuevo = precioText1.getText().trim();
            String minimoNuevo = minimoPersonaText1.getText().trim();


            String estadoNuevo = estadoCombo.getValue();
            String estado = mapaEstadoTraducido.entrySet().stream()
                    .filter(e -> e.getValue().equals(estadoNuevo))
                    .map(Map.Entry::getKey)
                    .findFirst().orElse(null);


            if (!nombreNuevo.isEmpty()) {
                if (!ValidarRecurso.ValidarNombre(nombreNuevo)) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "Nombre no válido (vacío o demasiado largo).");
                    if (logger != null) {
                        logger.warning("Nombre no válido: " + nombreNuevo);
                    }
                    return;
                }
                seleccionado.setNombre(nombreNuevo);
            }

            if (tipo != null) seleccionado.setTipo(tipo);

            if (!capacidadNuevo.isEmpty()) {
                Integer capacidad = parseInteger(capacidadNuevo);
                if (!ValidarRecurso.ValidarCapacidad(capacidad)) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "Capacidad no válida (debe ser >= 0).");
                    if (logger != null) {
                        logger.warning("Capacidad no válida: " + capacidadNuevo);
                    }
                    return;
                }
                seleccionado.setCapacidad(capacidad);
            }

            if (!precioNuevo.isEmpty()) {
                Integer precio = parseInteger(precioNuevo);
                if (!ValidarRecurso.ValidarPrecio(precio)) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "Precio no válido (debe ser >= 0).");
                    if (logger != null) {
                        logger.warning("Precio no válido: " + precioNuevo);
                    }
                    return;
                }
                seleccionado.setPrecio(precio);
            }

            if (!minimoNuevo.isEmpty()) {
                Integer minimo = parseInteger(minimoNuevo);
                if (!ValidarRecurso.ValidarMinimoPersonas(minimo)) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "Mínimo de personas no válido (debe ser >= 0).");
                    if (logger != null) {
                        logger.warning("Mínimo de personas no válido: " + minimoNuevo);
                    }
                    return;
                }
                seleccionado.setMinimoPersonas(minimo);
            }

            if (!estadoNuevo.isEmpty()) {
                seleccionado.setEstado(estado);
            }

            if (memoria.update(seleccionado)) {
                recursoTable.refresh();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Recurso modificado correctamente.");
                if (logger != null) {
                    logger.info("Recurso modificado con éxito: " + seleccionado);
                }
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el recurso en memoria.");
                if (logger != null) {
                    logger.warning("Fallo al actualizar el recurso en memoria: " + seleccionado);
                }
            }

        } catch (Exception e) {
            if (logger != null) {
                logger.severe("Error al modificar recurso: " + e.getMessage());
            }
        }
    }

    @FXML
    public void buscarTodos() {
        if (logger != null) {
            logger.info("Recargando todos los recursos desde memoria.");
        }
        cargarRecursosDesdeMemoria();
    }

    private Integer parseInteger(String texto) {
        try {
            if (texto == null || texto.trim().isEmpty()) return null;
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            if (logger != null) {
                logger.warning("No se pudo parsear número: " + texto);
            }
            return null;
        }
    }

    @Override
    public void idiomaCambiado() {
        if (logger != null) {
            logger.info("Idioma cambiado. Actualizando textos...");
        }
        actualizarTexto();
    }

    private void actualizarTexto() {
        labelModificar.setText(GestorIdiomas.getTexto("modificarRecurso"));
        labelId.setText(GestorIdiomas.getTexto("labelIDRecurso"));
        labelNombre.setText(GestorIdiomas.getTexto("nombre"));
        labelTipo.setText(GestorIdiomas.getTexto("tipo"));
        labelCapacidad.setText(GestorIdiomas.getTexto("capacidad"));
        labelPrecio.setText(GestorIdiomas.getTexto("precio"));
        labelMinimoPersona.setText(GestorIdiomas.getTexto("minimoPersonas"));
        labelEstado.setText(GestorIdiomas.getTexto("estado"));

        labelNombre2.setText(GestorIdiomas.getTexto("nombre"));
        labelTipo2.setText(GestorIdiomas.getTexto("tipo"));
        labelCapacidad2.setText(GestorIdiomas.getTexto("capacidad"));
        labelPrecio2.setText(GestorIdiomas.getTexto("precio"));
        labelMinimo2.setText(GestorIdiomas.getTexto("minimoPersonas"));
        labelEstado2.setText(GestorIdiomas.getTexto("estado"));

        btnModificar.setText(GestorIdiomas.getTexto("modificar"));
        btnBuscar.setText(GestorIdiomas.getTexto("buscar"));
        btnBuscarTodos.setText(GestorIdiomas.getTexto("buscarTodos"));

        // Actualizar elementos de comboBox con traducciones
        mapaTipoTraducido.put("PARCELA", GestorIdiomas.getTexto("PARCELA"));
        mapaTipoTraducido.put("BUNGALOW", GestorIdiomas.getTexto("BUNGALOW"));
        mapaTipoTraducido.put("BARBACOA", GestorIdiomas.getTexto("BARBACOA"));

        ObservableList<String> traducciones = FXCollections.observableArrayList(mapaTipoTraducido.values());
        tipoCombo.setItems(traducciones);
        tipoCombo.getSelectionModel().select(0);
        tipoCombo1.setItems(traducciones);
        tipoCombo1.getSelectionModel().select(0);

        // Actualizar elementos de comboBox con traducciones
        mapaEstadoTraducido.put("DISPONIBLE", GestorIdiomas.getTexto("DISPONIBLE"));
        mapaEstadoTraducido.put("OCUPADO", GestorIdiomas.getTexto("OCUPADO"));
        mapaEstadoTraducido.put("MANTENIMIENTO", GestorIdiomas.getTexto("MANTENIMIENTO"));

         traducciones = FXCollections.observableArrayList(mapaEstadoTraducido.values());
        estadoCombo.setItems(traducciones);
        estadoCombo.getSelectionModel().select(0);

        idColumn.setText(GestorIdiomas.getTexto("labelIDRecurso"));
        nombreColumn.setText(GestorIdiomas.getTexto("nombre"));
        tipoColumn.setText(GestorIdiomas.getTexto("tipo"));
        capacidadColumn.setText(GestorIdiomas.getTexto("capacidad"));
        precioColumn.setText(GestorIdiomas.getTexto("precio"));
        minimoColumn.setText(GestorIdiomas.getTexto("minimoPersonas"));
        estadoColumn.setText(GestorIdiomas.getTexto("estado"));
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        Stage stage = (Stage) labelNombre2.getScene().getWindow();
        alerta.initOwner(stage);
        alerta.initModality(Modality.WINDOW_MODAL);
        alerta.show();
    }
}
