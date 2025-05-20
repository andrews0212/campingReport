package org.example.camping2.controladores.Acompanante;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.example.camping2.controladores.Liberable;
import org.example.camping2.modelo.dto.Acompanante;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;


import java.io.IOException;

public class AcompananteController implements Liberable {

    @FXML
    private ImageView buscarImage;
    @FXML
    private ImageView agregarImage;
    @FXML
    private ImageView modificarImage;
    @FXML
    private ImageView eliminarImage;


    private Memoria<Acompanante, Integer> memoriaAcompanante;
    private Memoria<Reserva, Integer> memoriaReserva;

    private StackPane areaContenido;

    public void setAreaContenido(StackPane areaContenido) {
        this.areaContenido = areaContenido;
    }

    @FXML
    public void BuscarAcompanante(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/Acompanante/BuscarAcompaniante.fxml"));
            Parent nuevoPanel = loader.load();

            BuscaAcompananteController buscarRecursoController = loader.getController();
            buscarRecursoController.setMemoriaAcompanante(memoriaAcompanante);
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);

        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }

    @FXML
    public void CrearAcompanante(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/Acompanante/CrearAcompaniante.fxml"));
            Parent nuevoPanel = loader.load();

            CrearAcompananteController crearAcompananteController = loader.getController();
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
    public void ModificarAcompanante(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/Acompanante/ModificarAcompaniante.fxml"));
            Parent nuevoPanel = loader.load();

            ModificarAcompananteController modificarAcompananteController = loader.getController();
            modificarAcompananteController.setMemoriaAcompanante(memoriaAcompanante);
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);

        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }

    @FXML
    public void EliminarAcompanante(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/Acompanante/EliminarAcompaniante.fxml"));
            Parent nuevoPanel = loader.load();

            EliminarAcompananteController eliminarAcompananteController = loader.getController();
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

