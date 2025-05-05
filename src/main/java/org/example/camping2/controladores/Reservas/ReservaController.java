package org.example.camping2.controladores.Reservas;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;

import java.io.IOException;

public class ReservaController {
    private StackPane areaContenido;
    private Memoria<Reserva, Integer> memoriaReserva;


    private void cargarPanel(String archivoFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(archivoFXML));
            Parent nuevoPanel = loader.load();
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);
        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }

    public void BuscarReservaBoton(ActionEvent actionEvent) {

        cargarPanel("/org/example/camping2/BuscarReserva.fxml");
    }

    public void agregarReservaBoton(ActionEvent actionEvent) {
        cargarPanel("/org/example/camping2/CrearReserva.fxml");
    }

    public void modificarReservaBoton(ActionEvent actionEvent) {
        cargarPanel("/org/example/camping2/ModificarReservas.fxml");
    }

    public void eliminarReservaBoton(ActionEvent actionEvent) {
        cargarPanel("/org/example/camping2/EliminarReserva.fxml");
    }
    public void setAreaContenido(StackPane areaContenido) {
        this.areaContenido = areaContenido;
    }

    public StackPane getAreaContenido() {
        return areaContenido;
    }

    public Memoria<Reserva, Integer> getMemoriaReserva() {
        return memoriaReserva;
    }

    public void setMemoriaReserva(Memoria<Reserva, Integer> memoriaReserva) {
        this.memoriaReserva = memoriaReserva;
    }
}
