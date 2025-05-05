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

    public void BuscarReservaBoton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/reservas/BuscarReserva.fxml"));
            Parent nuevoPanel = loader.load();

            BuscaReservaController buscaReservaController = loader.getController();
            buscaReservaController.setMemoriaReserva(memoriaReserva);
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);
        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }

    public void agregarReservaBoton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/reservas/CrearReserva.fxml"));
            Parent nuevoPanel = loader.load();

            CrearReservaController crearReservaController = loader.getController();
            crearReservaController.setMemoriaReserva(memoriaReserva);
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);
        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }

    public void modificarReservaBoton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/reservas/ModificarReservas.fxml"));
            Parent nuevoPanel = loader.load();

            ModificarReservaController modificarReservaController = loader.getController();
            modificarReservaController.setMemoriaReserva(memoriaReserva);
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void eliminarReservaBoton(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/reservas/EliminarReserva.fxml"));
            Parent nuevoPanel = loader.load();
            EliminarReservaController eliminarReservaController = loader.getController();
            eliminarReservaController.setMemoriaReserva(memoriaReserva);

            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
