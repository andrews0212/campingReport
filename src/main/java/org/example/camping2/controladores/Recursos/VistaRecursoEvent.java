package org.example.camping2.controladores.Recursos;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.camping2.modelo.dto.Recurso;

public class VistaRecursoEvent {

    @FXML
    private Label nombre;
    @FXML private Label tipo;
    @FXML private Label capacidad;
    @FXML private Label precio;
    @FXML private Label minimo;
    @FXML private Label estado;

    private Recurso recurso;

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
        cargarDatos();
    }

    private void cargarDatos() {
        if (recurso != null) {
            nombre.setText(recurso.getNombre());
            tipo.setText(recurso.getTipo());
            capacidad.setText(String.valueOf(recurso.getCapacidad()));
            precio.setText(String.valueOf(recurso.getPrecio()));
            minimo.setText(String.valueOf(recurso.getMinimoPersonas()));
            estado.setText(recurso.getEstado());
        }
    }
}