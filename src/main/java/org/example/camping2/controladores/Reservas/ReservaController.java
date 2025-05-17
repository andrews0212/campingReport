package org.example.camping2.controladores.Reservas;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.example.camping2.controladores.Liberable;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;

import java.io.IOException;

public class ReservaController implements Liberable {

    @FXML
    private ImageView buscarImage;
    @FXML
    private ImageView agregarImage;
    @FXML
    private ImageView modificarImage;
    @FXML
    private ImageView eliminarImage;

    private StackPane areaContenido;
    private Memoria<Reserva, Integer> memoriaReserva;
    private Memoria<Recurso, Integer> memoriaRecurso;
    private Memoria<Cliente, Integer> memoriaCliente;

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
            crearReservaController.setMemoriaRecurso(memoriaRecurso);
            crearReservaController.setMemoriaCliente(memoriaCliente);
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

    public Memoria<Recurso, Integer> getMemoriaRecurso() {
        return memoriaRecurso;
    }

    public void setMemoriaRecurso(Memoria<Recurso, Integer> memoriaRecurso) {
        this.memoriaRecurso = memoriaRecurso;
    }

    public void setMemoriaCliente(Memoria<Cliente, Integer> memoriaCliente) {
        this.memoriaCliente = memoriaCliente;
    }

    @Override
    public void liberarRecursos() {
        Platform.runLater(() -> {


        if (agregarImage.getImage() != null) {
            agregarImage.getImage().cancel();
        }
        agregarImage.setImage(null);

        if (eliminarImage.getImage() != null) {
            eliminarImage.getImage().cancel();
        }
        eliminarImage.setImage(null);

        if (modificarImage.getImage() != null) {
            modificarImage.getImage().cancel();
        }
        modificarImage.setImage(null);

        if (buscarImage.getImage() != null) {
            buscarImage.getImage().cancel();
        }
        buscarImage.setImage(null);
        });
    }

}
