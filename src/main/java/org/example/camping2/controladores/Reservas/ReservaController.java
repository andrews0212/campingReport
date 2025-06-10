package org.example.camping2.controladores.Reservas;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.controladores.Liberable;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;

import java.io.IOException;
import java.util.logging.Logger;

public class ReservaController implements IdiomaListener {

    private Logger logger;

    private StackPane areaContenido;
    private Memoria<Reserva, Integer> memoriaReserva;
    private Memoria<Recurso, Integer> memoriaRecurso;
    private Memoria<Cliente, Integer> memoriaCliente;


    public void initialize() {
        GestorIdiomas.agregarListener(this);
        actualizarTexto();
    }

        public void BuscarReservaBoton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/reservas/BuscarReserva.fxml"));
            Parent nuevoPanel = loader.load();

            BuscaReservaController buscaReservaController = loader.getController();
            buscaReservaController.setLogger(logger);
            buscaReservaController.setMemoriaReserva(memoriaReserva);
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);
        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }

    public void agregarReservaBoton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/reservas/CrearReserva.fxml"));
            Parent nuevoPanel = loader.load();

            CrearReservaController crearReservaController = loader.getController();
            crearReservaController.setLogger(logger);
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

    public void modificarReservaBoton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/reservas/ModificarReservas.fxml"));
            Parent nuevoPanel = loader.load();

            ModificarReservaController modificarReservaController = loader.getController();
            modificarReservaController.setLogger(logger);
            modificarReservaController.setMemoriaReserva(memoriaReserva);
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void eliminarReservaBoton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/reservas/EliminarReserva.fxml"));
            Parent nuevoPanel = loader.load();
            EliminarReservaController eliminarReservaController = loader.getController();
            eliminarReservaController.setLogger(logger);
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
    public void idiomaCambiado() {
        actualizarTexto();
    }

    private void actualizarTexto() {

    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
