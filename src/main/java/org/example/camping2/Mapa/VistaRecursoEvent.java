package org.example.camping2.Mapa;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.dto.Recurso;

public class VistaRecursoEvent implements IdiomaListener {


    @FXML private Label labelNombreRecurso, labelTipo, labelCapacidad, labelPrecio, labelMinimoPersonas, labelRecurso, labelCliente,labelEstado;
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

    @FXML
    private void initialize(){
        GestorIdiomas.agregarListener(this);
        actualizarTexto();
    }
    private void cargarDatos() {
        if (recurso != null) {
            nombre.setText(recurso.getNombre());
            tipo.setText(recurso.getTipo());
            capacidad.setText(String.valueOf(recurso.getCapacidad()));
            precio.setText(String.valueOf(recurso.getPrecio()));
            minimo.setText(String.valueOf(recurso.getMinimoPersonas()));
            estado.setText(GestorIdiomas.getTexto(recurso.getEstado()));
        }
        if (cliente != null) {
            labelResultNombre.setText(cliente.getNombre());
            labelResultApellido.setText(cliente.getApellido());
            labelResultDNI.setText(String.valueOf(cliente.getDni()));
            labelResultEmail.setText(cliente.getEmail());
            labelResultTelefono.setText(String.valueOf(cliente.getTelefono()));
        } else {
            labelResultNombre.setText("");
            labelResultApellido.setText("");
            labelResultDNI.setText("");
            labelResultEmail.setText("");
            labelResultTelefono.setText("");
        }
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;

    }

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }

    private void actualizarTexto() {
        labelRecurso.setText(GestorIdiomas.getTexto("recurso") + ": ");
        labelNombreRecurso.setText(GestorIdiomas.getTexto("nombre")  + ": ");
        labelTipo.setText(GestorIdiomas.getTexto("tipo")  + ": ");
        labelCapacidad.setText(GestorIdiomas.getTexto("capacidad")  + ": ");
        labelPrecio.setText(GestorIdiomas.getTexto("precio")  + ": ");
        labelMinimoPersonas.setText(GestorIdiomas.getTexto("minimoPersonas")  + ": ");
        labelNombre.setText(GestorIdiomas.getTexto("nombre")  + ": ");
        labelApellido.setText(GestorIdiomas.getTexto("apellido")  + ": ");
        LabelDNI.setText(GestorIdiomas.getTexto("dni")  + ": ");
        labelEmail.setText(GestorIdiomas.getTexto("email")  + ": ");
        labelTelefono.setText(GestorIdiomas.getTexto("telefono") + ": ");
        labelCliente.setText(GestorIdiomas.getTexto("cliente") + ": ");
        labelEstado.setText(GestorIdiomas.getTexto("estado") + ": ");
    }
}