package org.example.camping2.Mapa;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;

import java.io.IOException;
import java.util.List;

public class MerenderoControlador {

    @FXML private Button mesa1, mesa2, mesa3, mesa4, mesa5, mesa6, mesa7, mesa8, mesa9, mesa10;
    @FXML private Label text1, text2, text3, text4, text5, text6, text7, text8, text9, text10;

    private List<Button> botonesMesas;
    private List<Label> textosMesas;
    private List<Recurso> recursosActuales;
    private Memoria<Reserva, Integer> memoriaReserva;
    private Memoria<Recurso, Integer> memoriaRecurso;


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
                    Reserva reservaEncontrada = null;
                    // Se puede que no tenga reserva
                    for (Reserva reserva : memoriaReserva.findAll()) {
                        if (reserva.getIdrecurso().getId().equals(recurso.getId())) {
                            reservaEncontrada = reserva;
                            break;
                        }
                    }
                    Cliente cliente = null;
                    if (reservaEncontrada != null) {
                        cliente = reservaEncontrada.getIdcliente(); // puede ser null
                    }
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/mapa/vistaRecursoEvent.fxml"));
                        Parent root = loader.load();

                        VistaRecursoEvent controladorDetalle = loader.getController();
                        controladorDetalle.setCliente(cliente);
                        controladorDetalle.setRecurso(recurso);
                        // Puede ser null, pero el controlador debe saber gestionarlo


                        Stage stage = new Stage();
                        stage.setTitle("Detalles del recurso");
                        stage.setScene(new Scene(root));
                        Stage primaryStage = (Stage) mesa1.getScene().getWindow();
                        stage.initOwner(primaryStage); // <-- aquí el stage principal, no null
                        stage.initModality(Modality.WINDOW_MODAL);


                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error al abrir la ventana de detalles");
                    }
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
        if (recursosActuales == null || memoriaRecurso == null) return;

        memoriaRecurso.actualizarMemoriaBD();
        memoriaReserva.actualizarMemoriaBD();

        for (int i = 0; i < recursosActuales.size() && i < botonesMesas.size(); i++) {
            Recurso recursoViejo = recursosActuales.get(i);
            Recurso recursoActualizado = memoriaRecurso.findById(recursoViejo.getId());

            if (recursoActualizado != null) {
                recursosActuales.set(i, recursoActualizado);  // actualizar la lista
                cambiarColorBotonPorEstado(botonesMesas.get(i), recursoActualizado.getEstado());
                textosMesas.get(i).setText(recursoActualizado.getNombre() + "\n" + recursoActualizado.getEstado());
            }
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

    public void setMemoriaReserva(Memoria<Reserva, Integer> memoriaReserva) {
        this.memoriaReserva = memoriaReserva;
        memoriaReserva.actualizarMemoriaBD();
    }


    public void setMemoriaRecurso(Memoria<Recurso, Integer> memoriaRecurso) {
        this.memoriaRecurso = memoriaRecurso;
        memoriaRecurso.actualizarMemoriaBD();
    }

}
