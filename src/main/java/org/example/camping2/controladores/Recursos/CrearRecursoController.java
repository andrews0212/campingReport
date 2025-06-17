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
import org.example.camping2.modelo.validaciones.ValidarRecurso;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class CrearRecursoController implements IdiomaListener {

    @FXML
    private Label labelAgregarRecurso;
    @FXML
    private Label labelTipo;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelCapacidad;
    @FXML
    private Label labelPrecio;
    @FXML
    private Label labelMinimoPersonas;
    @FXML
    private Button btnCrear;

    @FXML
    private TextField nombreField;
    @FXML
    private ComboBox<String> tipoComboBox;
    @FXML
    private TextField capacidadField;
    @FXML
    private TextField precioField;
    @FXML
    private TextField minimoPersonasField;

    private Memoria<Recurso, Integer> memoria;
    private Logger log;
    private Map<String, String> mapaTipoTraducido;

    public void setLogger(Logger logger) {
        this.log = logger;
    }

    public void setMemoriaRecurso(Memoria<Recurso, Integer> memoria) {
        this.memoria = memoria;
    }

    @FXML
    public void initialize() {
        mapaTipoTraducido = new HashMap<>();
        mapaTipoTraducido.put("PARCELA", GestorIdiomas.getTexto("PARCELA"));
        mapaTipoTraducido.put("BUNGALOW", GestorIdiomas.getTexto("BUNGALOW"));
        mapaTipoTraducido.put("BARBACOA", GestorIdiomas.getTexto("BARBACOA"));
        ObservableList<String> traducciones = FXCollections.observableArrayList(mapaTipoTraducido.values());
        tipoComboBox.setItems(traducciones);
        tipoComboBox.getSelectionModel().select(0);
        GestorIdiomas.agregarListener(this);
        actualizarTexto();

        if (log != null) log.info("CrearRecursoController inicializado correctamente");
    }

    @FXML
    private void crearRecurso() {
        try {
            String nombre = nombreField.getText();
            String tipoTraducido = tipoComboBox.getValue();
            String tipo = mapaTipoTraducido.entrySet().stream()
                    .filter(e -> e.getValue().equals(tipoTraducido))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse("PARCELA");

            int capacidad = Integer.parseInt(capacidadField.getText());
            int precio = Integer.parseInt(precioField.getText());
            int minimoPersonas = Integer.parseInt(minimoPersonasField.getText());

            if (log != null) {
                log.info("Intentando crear recurso: " + nombre + ", Tipo: " + tipo + ", Capacidad: " + capacidad +
                        ", Precio: " + precio + ", Mínimo: " + minimoPersonas);
            }

            if (ValidarRecurso.ValidarNombre(nombre)) {
                if (ValidarRecurso.ValidarCapacidad(capacidad)) {
                    if (ValidarRecurso.ValidarPrecio(precio)) {
                        if (ValidarRecurso.ValidarMinimoPersonas(minimoPersonas)) {
                            Recurso recurso = new Recurso(nombre, tipo, capacidad, precio, minimoPersonas);
                            boolean agregado = memoria.add(recurso);
                            if (agregado) {
                                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Recurso creado correctamente");
                                if (log != null) log.info("Recurso creado exitosamente: " + recurso);
                                limpiarCampos();
                            } else {
                                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo crear el recurso");
                                if (log != null) log.warning("No se pudo agregar el recurso: " + recurso);
                            }
                        } else {
                            mostrarAlerta(Alert.AlertType.ERROR, "Error", "El mínimo de personas debe ser mayor a 0");
                            if (log != null) log.warning("Validación fallida: mínimo de personas inválido");
                        }
                    } else {
                        mostrarAlerta(Alert.AlertType.ERROR, "Error", "El precio debe ser mayor a 0");
                        if (log != null) log.warning("Validación fallida: precio inválido");
                    }
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "La capacidad debe ser mayor a 0");
                    if (log != null) log.warning("Validación fallida: capacidad inválida");
                }
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "El nombre no puede estar vacío");
                if (log != null) log.warning("Validación fallida: nombre vacío");
            }

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "Capacidad, precio o mínimo deben ser numéricos");
            if (log != null) log.severe("Error de formato numérico: " + e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error: " + e.getMessage());
            if (log != null) log.severe("Excepción inesperada al crear recurso: " + e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        Stage stage = (Stage) labelAgregarRecurso.getScene().getWindow();
        alerta.initOwner(stage);
        alerta.initModality(Modality.WINDOW_MODAL);
        alerta.show();
    }

    private void limpiarCampos() {
        nombreField.clear();
        tipoComboBox.setValue(null);
        capacidadField.clear();
        precioField.clear();
        minimoPersonasField.clear();

        if (log != null) log.info("Campos del formulario limpiados");
    }

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
        if (log != null) log.info("Idioma cambiado y texto actualizado");
    }

    public void actualizarTexto() {
        labelAgregarRecurso.setText(GestorIdiomas.getTexto("labelAgregarRecurso"));
        labelNombre.setText(GestorIdiomas.getTexto("nombre"));
        labelTipo.setText(GestorIdiomas.getTexto("tipo"));
        labelCapacidad.setText(GestorIdiomas.getTexto("capacidad"));
        labelPrecio.setText(GestorIdiomas.getTexto("precio"));
        labelMinimoPersonas.setText(GestorIdiomas.getTexto("minimoPersonas"));
        btnCrear.setText(GestorIdiomas.getTexto("btnCrear"));
        mapaTipoTraducido.clear();
        mapaTipoTraducido.put("PARCELA", GestorIdiomas.getTexto("PARCELA"));
        mapaTipoTraducido.put("BUNGALOW", GestorIdiomas.getTexto("BUNGALOW"));
        mapaTipoTraducido.put("BARBACOA", GestorIdiomas.getTexto("BARBACOA"));
        ObservableList<String> traducciones = FXCollections.observableArrayList(mapaTipoTraducido.values());
        tipoComboBox.setItems(traducciones);
        tipoComboBox.getSelectionModel().select(0);
    }
}
