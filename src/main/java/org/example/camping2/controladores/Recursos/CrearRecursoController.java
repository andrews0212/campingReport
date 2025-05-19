package org.example.camping2.controladores.Recursos;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.memoria.Memoria;

public class CrearRecursoController {

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

    @FXML
    public void initialize() {
        tipoComboBox.getItems().addAll("PARCELA", "BUNGALOW", "BARBACOA");
        tipoComboBox.getSelectionModel().select(0);
    }

    @FXML
    private void crearRecurso() {
        try {
            String nombre = nombreField.getText();
            String tipo = tipoComboBox.getValue();
            int capacidad = Integer.parseInt(capacidadField.getText());
            int precio = Integer.parseInt(precioField.getText());
            int minimoPersonas = Integer.parseInt(minimoPersonasField.getText());

            Recurso recurso = new Recurso(nombre, tipo, capacidad, precio, minimoPersonas);

            boolean agregado = memoria.add(recurso);

            if (agregado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Recurso creado correctamente");
                limpiarCampos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo crear el recurso");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "Capacidad, precio o mínimo deben ser numéricos");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        nombreField.clear();
        tipoComboBox.setValue(null);
        capacidadField.clear();
        precioField.clear();
        minimoPersonasField.clear();
    }
}
