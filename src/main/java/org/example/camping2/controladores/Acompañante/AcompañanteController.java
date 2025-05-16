package org.example.camping2.controladores.Acompañante;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import org.example.camping2.controladores.Recursos.BuscarRecursoController;
import org.example.camping2.controladores.Recursos.CrearRecursoController;
import org.example.camping2.modelo.dto.Acompañante;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;

import java.io.IOException;

public class AcompañanteController {

    private Memoria<Acompañante, Integer> memoriaAcompañante;
    private Memoria<Reserva, Integer> memoriaReserva;

    private StackPane areaContenido;

    public void setAreaContenido(StackPane areaContenido) {
        this.areaContenido = areaContenido;
    }

    @FXML
    public void BuscarAcompañante(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/acompañante/BuscarAcompaniante.fxml"));
            Parent nuevoPanel = loader.load();

            BuscaAcompañanteController buscarRecursoController = loader.getController();
            buscarRecursoController.setMemoriaAcompañante(memoriaAcompañante);
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);

        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }

    @FXML
    public void CrearAcompañante(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/acompañante/CrearAcompaniante.fxml"));
            Parent nuevoPanel = loader.load();

            CrearAcompañanteController crearAcompañanteController = loader.getController();
            crearAcompañanteController.setMemoriaAcompañante(memoriaAcompañante);
            crearAcompañanteController.setMemoriaReserva(memoriaReserva);
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);

        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }

    @FXML
    public void ModificarAcompañante(ActionEvent actionEvent) {
    }

    @FXML
    public void EliminarAcompañante(ActionEvent actionEvent) {
    }

    public void setMemoriaAcompañante(Memoria<Acompañante, Integer> memoriaAcompañante) {
        this.memoriaAcompañante = memoriaAcompañante;
    }

    public void setMemoriaReserva(Memoria<Reserva, Integer> memoriaReserva) {
        this.memoriaReserva = memoriaReserva;
    }
}
