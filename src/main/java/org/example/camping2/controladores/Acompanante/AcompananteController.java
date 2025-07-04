package org.example.camping2.controladores.Acompanante;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Acompanante;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;


import java.io.IOException;
import java.util.logging.Logger;

public class AcompananteController implements  IdiomaListener {
    private Logger logger;


    private Memoria<Acompanante, Integer> memoriaAcompanante;
    private Memoria<Reserva, Integer> memoriaReserva;

    private StackPane areaContenido;

    public void setAreaContenido(StackPane areaContenido) {
        this.areaContenido = areaContenido;
    }

    @FXML
    public void initialize() {
        GestorIdiomas.agregarListener(this);
        actualizarTexto();
    }
    @FXML
    public void BuscarAcompanante() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/Acompanante/BuscarAcompaniante.fxml"));
            Parent nuevoPanel = loader.load();

            BuscaAcompananteController buscarRecursoController = loader.getController();
            buscarRecursoController.setLogger(logger);
            buscarRecursoController.setMemoriaAcompanante(memoriaAcompanante);
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);

        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }

    @FXML
    public void CrearAcompanante() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/Acompanante/CrearAcompaniante.fxml"));
            Parent nuevoPanel = loader.load();

            CrearAcompananteController crearAcompananteController = loader.getController();
            crearAcompananteController.setLogger(logger);
            crearAcompananteController.setMemoriaAcompanante(memoriaAcompanante);
            crearAcompananteController.setMemoriaReserva(memoriaReserva);


            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);

        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }

    @FXML
    public void ModificarAcompanante() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/Acompanante/ModificarAcompaniante.fxml"));
            Parent nuevoPanel = loader.load();

            ModificarAcompananteController modificarAcompananteController = loader.getController();
            modificarAcompananteController.setLogger(logger);
            modificarAcompananteController.setMemoriaAcompanante(memoriaAcompanante);


            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);

        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }

    @FXML
    public void EliminarAcompanante() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/Acompanante/EliminarAcompaniante.fxml"));
            Parent nuevoPanel = loader.load();

            EliminarAcompananteController eliminarAcompananteController = loader.getController();
            eliminarAcompananteController.setLogger(logger);
            eliminarAcompananteController.setMemoriaAcompanante(memoriaAcompanante);
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);

        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }

    public void setMemoriaAcompanante(Memoria<Acompanante, Integer> memoriaAcompanante) {
        this.memoriaAcompanante = memoriaAcompanante;
    }

    public void setMemoriaReserva(Memoria<Reserva, Integer> memoriaReserva) {
        this.memoriaReserva = memoriaReserva;
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

