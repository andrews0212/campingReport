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

    private Memoria<Recurso, Integer> memoria;

    public void setMemoriaRecurso(Memoria<Recurso, Integer> memoria) {
        this.memoria = memoria;
    }

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

    private Map<String, String> mapaTipoTraducido;


    @FXML
    public void initialize() {

        mapaTipoTraducido = new HashMap<>();
        mapaTipoTraducido.clear();
        mapaTipoTraducido.put("PARCELA", GestorIdiomas.getTexto("PARCELA"));
        mapaTipoTraducido.put("BUNGALOW", GestorIdiomas.getTexto("BUNGALOW"));
        mapaTipoTraducido.put("BARBACOA", GestorIdiomas.getTexto("BARBACOA"));
        ObservableList<String> traducciones = FXCollections.observableArrayList(mapaTipoTraducido.values());
        tipoComboBox.setItems(traducciones);
        tipoComboBox.getSelectionModel().select(0);
        GestorIdiomas.agregarListener(this);
        actualizarTexto();

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
                    .orElse("PARCELA");  // valor por defecto si no se encuentra

            int capacidad = Integer.parseInt(capacidadField.getText());
            int precio = Integer.parseInt(precioField.getText());
            int minimoPersonas = Integer.parseInt(minimoPersonasField.getText());

            Recurso recurso = new Recurso(nombre, tipo, capacidad, precio, minimoPersonas);

            if(ValidarRecurso.ValidarNombre(nombre)){
                if (ValidarRecurso.ValidarCapacidad(Integer.parseInt(capacidadField.getText()))) {
                    if (ValidarRecurso.ValidarPrecio(Integer.parseInt(precioField.getText()))) {
                        if (ValidarRecurso.ValidarMinimoPersonas(Integer.parseInt(minimoPersonasField.getText()))) {
                            boolean agregado = memoria.add(recurso);
                            if (agregado) {
                                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Recurso creado correctamente");
                                limpiarCampos();
                            } else {
                                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo crear el recurso");
                            }
                        }else {
                            mostrarAlerta(Alert.AlertType.ERROR, "Error", "El mínimo de personas debe ser mayor a 0");
                        }
                    }else {
                        mostrarAlerta(Alert.AlertType.ERROR, "Error", "El precio debe ser mayor a 0");

                    }
                }else{
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "La capacidad debe ser mayor a 0");
                }
            }else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "El nombre no puede estar vacío");
            }


        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "Capacidad, precio o mínimo deben ser numéricos");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    private void mostrarAlerta( Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);

        // Obtener el Stage actual y asignarlo como propietario
        Stage stage = (Stage) labelAgregarRecurso.getScene().getWindow(); // cualquier nodo sirve
        alerta.initOwner(stage);
        alerta.initModality(Modality.WINDOW_MODAL);  // Importante: no usar APPLICATION_MODAL

        alerta.show(); // No bloqueante
    }

    private void limpiarCampos() {
        nombreField.clear();
        tipoComboBox.setValue(null);
        capacidadField.clear();
        precioField.clear();
        minimoPersonasField.clear();
    }

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }
    public void actualizarTexto(){
        labelAgregarRecurso.setText(GestorIdiomas.getTexto("labelAgregarRecurso"));
        labelNombre.setText(GestorIdiomas.getTexto("nombre"));
        nombreField.setPromptText(GestorIdiomas.getTexto("nombreField"));
        labelTipo.setText(GestorIdiomas.getTexto("tipo"));
        labelCapacidad.setText(GestorIdiomas.getTexto("capacidad"));
        capacidadField.setPromptText(GestorIdiomas.getTexto("capacidadField"));
        labelPrecio.setText(GestorIdiomas.getTexto("precio"));
        precioField.setPromptText(GestorIdiomas.getTexto("precioField"));
        labelMinimoPersonas.setText(GestorIdiomas.getTexto("minimoPersonas"));
        minimoPersonasField.setPromptText(GestorIdiomas.getTexto("minimoPersonasField"));
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
