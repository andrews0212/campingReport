package org.example.camping2.Mapa;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.example.camping2.modelo.dto.Recurso;

import java.util.List;

public class MerenderoControlador {

    @FXML private Button mesa1, mesa2, mesa3, mesa4, mesa5, mesa6, mesa7, mesa8, mesa9, mesa10;
    @FXML private Label text1, text2, text3, text4, text5, text6, text7, text8, text9, text10;

    private List<Button> botonesMesas;
    private List<Label> textosMesas;
    private List<Recurso> recursosActuales;

    @FXML
    public void initialize() {
        botonesMesas = List.of(mesa1, mesa2, mesa3, mesa4, mesa5, mesa6, mesa7, mesa8, mesa9, mesa10);
        textosMesas = List.of(text1, text2, text3, text4, text5, text6, text7, text8, text9, text10);

        // Añadir manejadores para los botones
        for (int i = 0; i < botonesMesas.size(); i++) {
            final int index = i;  // variable final para usar en lambda
            botonesMesas.get(i).setOnAction(event -> {
                if (recursosActuales != null && index < recursosActuales.size()) {
                    Recurso recurso = recursosActuales.get(index);
                    System.out.println("Info recurso seleccionado:");
                    System.out.println("Nombre: " + recurso.getNombre());
                    System.out.println("Estado: " + recurso.getEstado());
                    // Añade aquí más info si quieres
                }
            });
        }

        // Inicia el Timeline para actualizar cada 5 minutos
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.minutes(5), e -> actualizarColores())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    public void setBarbacoas(List<Recurso> barbacoas) {
        this.recursosActuales = barbacoas;
        actualizarVistaCompleta();
    }

    private void actualizarVistaCompleta() {
        if (recursosActuales == null) return;

        for (int i = 0; i < recursosActuales.size() && i < botonesMesas.size(); i++) {
            Recurso recurso = recursosActuales.get(i);
            Button boton = botonesMesas.get(i);
            Label texto = textosMesas.get(i);

            texto.setText(recurso.getNombre() + "\n" + recurso.getEstado());
            cambiarColorBotonPorEstado(boton, recurso.getEstado());
        }
    }

    private void actualizarColores() {
        if (recursosActuales == null) return;

        for (int i = 0; i < recursosActuales.size() && i < botonesMesas.size(); i++) {
            Button boton = botonesMesas.get(i);
            Recurso recurso = recursosActuales.get(i);
            cambiarColorBotonPorEstado(boton, recurso.getEstado());
        }
    }

    private void cambiarColorBotonPorEstado(Button boton, String estado) {
        if (estado == null) estado = "";
        switch (estado.toLowerCase()) {
            case "disponible":
                boton.setStyle("-fx-background-color: #4CAF50;"); // verde
                break;
            case "ocupado":
                boton.setStyle("-fx-background-color: #F44336;"); // rojo
                break;
            case "mantenimiento":
                boton.setStyle("-fx-background-color: #FFC107;"); // amarillo
                break;
            default:
                boton.setStyle("-fx-background-color: #9E9E9E;"); // gris
        }
    }

}
