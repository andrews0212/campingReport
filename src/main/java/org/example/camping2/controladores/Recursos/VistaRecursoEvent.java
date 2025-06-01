package org.example.camping2.controladores.Recursos;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.dto.Recurso;

public class VistaRecursoEvent {


    @FXML private Label labelNombreRecurso, labelTipo, labelCapacidad, labelPrecio, labelMinimoPersonas;
    @FXML private Label labelNombre,labelApellido,LabelDNI,labelEmail,labelTelefono, labelResultNombre, labelResultApellido, labelResultDNI, labelResultEmail, labelResultTelefono;

    @FXML
    private Label nombre;
    @FXML private Label tipo;
    @FXML private Label capacidad;
    @FXML private Label precio;
    @FXML private Label minimo;
    @FXML private Label estado;

    private Recurso recurso;
    private Cliente cliente;

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
        if (cliente != null) {
            labelResultNombre.setText(cliente.getNombre());
            labelResultApellido.setText(cliente.getApellido());
            labelResultDNI.setText(String.valueOf(cliente.getDni()));
            labelResultEmail.setText(cliente.getEmail());
            labelResultTelefono.setText(String.valueOf(cliente.getTelefono()));
        }
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;

    }
}