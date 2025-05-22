package org.example.camping2.controladores;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.camping2.controladores.Recursos.VistaRecursoEvent;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.memoria.Memoria;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MapaCamping {

    private final Map<String, Button> botones = new HashMap<>();
    private Map<String, ImageView> casas = new HashMap<>();

    private Memoria<Recurso, Integer> memoriaRecurso;
    //=== Imagens casas ==//
    @FXML private ImageView casaEL4;
    @FXML private ImageView casaEL3;
    @FXML private ImageView casaEL2;
    @FXML private ImageView casaEL1;
    @FXML private ImageView casaDII;
    @FXML private ImageView casaDI;
    @FXML private ImageView casaB29;
    @FXML private ImageView casaB28;
    @FXML private ImageView casaB27;
    @FXML private ImageView casaB26;
    @FXML private ImageView casaB25;
    @FXML private ImageView casaB24;
    @FXML private ImageView casaB23;
    @FXML private ImageView casaB22;
    @FXML private ImageView casaB21;
    @FXML private ImageView casaB20;
    @FXML private ImageView casaB19;
    @FXML private ImageView casaB18;
    @FXML private ImageView casaB17;
    @FXML private ImageView casaB16;
    @FXML private ImageView casaB15;
    @FXML private ImageView casaB14;
    @FXML private ImageView casaB13;
    @FXML private ImageView casaB12;
    @FXML private ImageView casaB11;
    @FXML private ImageView casaB10;
    @FXML private ImageView casaB7;
    @FXML private ImageView casaB6;
    @FXML private ImageView casaB5;
    // === Sección EL ===
    @FXML private Button EL1;
    @FXML private Button EL2;
    @FXML private Button EL3;
    @FXML private Button EL4;

    // === Sección D ===
    @FXML private Button DI;
    @FXML private Button DII;

    // === Sección B (10 al 29) ===
    @FXML private Button B10;
    @FXML private Button B11;
    @FXML private Button B12;
    @FXML private Button B13;
    @FXML private Button B14;
    @FXML private Button B15;
    @FXML private Button B16;
    @FXML private Button B17;
    @FXML private Button B18;
    @FXML private Button B19;
    @FXML private Button B20;
    @FXML private Button B21;
    @FXML private Button B22;
    @FXML private Button B23;
    @FXML private Button B24;
    @FXML private Button B25;
    @FXML private Button B26;
    @FXML private Button B27;
    @FXML private Button B28;
    @FXML private Button B29;

    // === Sección A (20 al 29) ===
    @FXML private Button A2;
    @FXML private Button A3;
    @FXML private Button A4;
    @FXML private Button A5;
    @FXML private Button A6;
    @FXML private Button A7;
    @FXML private Button A8;
    @FXML private Button A9;
    @FXML private Button A12;
    @FXML private Button A13;
    @FXML private Button A14;
    @FXML private Button A15;
    @FXML private Button A16;
    @FXML private Button A17;
    @FXML private Button A18;
    @FXML private Button A19;
    @FXML private Button A20;
    @FXML private Button A21;
    @FXML private Button A22;
    @FXML private Button A23;
    @FXML private Button A24;
    @FXML private Button A25;
    @FXML private Button A26;
    @FXML private Button A27;
    @FXML private Button A28;
    @FXML private Button A29;
    @FXML private Button A30;
    // === Sección S (1 al 16) ===
    // === Sección S (16 al 39) ===
    @FXML private Button S13;
    @FXML private Button S14;
    @FXML private Button S15;
    @FXML private Button S16;
    @FXML private Button S17;
    @FXML private Button S18;
    @FXML private Button S21;
    @FXML private Button S22;
    @FXML private Button S23;
    @FXML private Button S24;
    @FXML private Button S25;
    @FXML private Button S26;
    @FXML private Button S27;
    @FXML private Button S28;
    @FXML private Button S29;
    @FXML private Button S30;
    @FXML private Button S31;
    @FXML private Button S32;
    @FXML private Button S33;
    @FXML private Button S34;
    @FXML private Button S35;
    @FXML private Button S36;
    @FXML private Button S37;
    @FXML private Button S38;
    @FXML private Button S39;

    @FXML
    public void initialize() {
        agregarBotones(
                EL1, EL2, EL3, EL4,
                DI, DII,
                B10, B11, B12, B13, B14, B15, B16, B17, B18, B19,
                B20, B21, B22, B23, B24, B25, B26, B27, B28, B29,
                A2, A3, A4, A5, A6, A7, A8, A9,
                A12, A13, A14, A15, A16, A17, A18, A19,
                A20, A21, A22, A23, A24, A25, A26, A27, A28, A29, A30,
                S13,S14,S15,
                S16, S17, S18, S21, S22, S23,
                S24, S25, S26, S27, S28, S29, S30, S31,
                S32, S33, S34, S35, S36, S37, S38, S39
        );

        // Asignar evento de clic a cada botón
        for (Map.Entry<String, Button> entry : botones.entrySet()) {
            Button boton = entry.getValue();
            boton.setOnMouseClicked(event -> manejarClicBoton(entry.getKey(), event));
        }
        prepararReferenciasCasas();      // <-- Llama este nuevo método

    }

    private void prepararReferenciasCasas() {
        casas.put("EL1", casaEL1);
        casas.put("EL2", casaEL2);
        casas.put("EL3", casaEL3);
        casas.put("EL4", casaEL4);
        casas.put("DI", casaDI);
        casas.put("DII", casaDII);
        casas.put("B29", casaB29);
        casas.put("B28", casaB28);
        casas.put("B27", casaB27);
        casas.put("B26", casaB26);
        casas.put("B25", casaB25);
        casas.put("B24", casaB24);
        casas.put("B23", casaB23);
        casas.put("B22", casaB22);
        casas.put("B21", casaB21);
        casas.put("B20", casaB20);
        casas.put("B19", casaB19);
        casas.put("B18", casaB18);
        casas.put("B17", casaB17);
        casas.put("B16", casaB16);
        casas.put("B15", casaB15);
        casas.put("B14", casaB14);
        casas.put("B13", casaB13);
        casas.put("B12", casaB12);
        casas.put("B11", casaB11);
        casas.put("B10", casaB10);
        casas.put("B7", casaB7);
        casas.put("B6", casaB6);
        casas.put("B5", casaB5);
    }
    private void actualizarColoresCasas() {
        for (Map.Entry<String, ImageView> entry : casas.entrySet()) {
            String id = entry.getKey();
            ImageView casa = entry.getValue();

            // Buscar recurso por nombre (id)
            Optional<Recurso> recursoOpt = memoriaRecurso.findAll().stream()
                    .filter(r -> r.getNombre().equals(id))
                    .findFirst();

            String imagen;

            if (recursoOpt.isPresent()) {
                String estado = recursoOpt.get().getEstado().toLowerCase();

                switch (estado) {
                    case "disponible":
                        imagen = "home_verde.png";
                        break;
                    case "ocupado":
                        imagen = "home_rojo.png";
                        break;
                    case "mantenimiento":
                        imagen = "home_amarillo.png";
                        break;
                    default:
                        // En caso de que no reconozca el estado, puedes poner un icono neutro o el verde
                        imagen = "home_verde.png";
                        break;
                }
            } else {
                // Si no encuentra el recurso, puedes poner imagen por defecto o nada
                imagen = "home_verde.png";
            }

            casa.setImage(new javafx.scene.image.Image(getClass().getResource("/org/example/camping2/Iconos/" + imagen).toExternalForm()));
        }
    }



    private void agregarBotones(Button... botonesArray) {
        for (Button b : botonesArray) {
            if (b != null) {
                botones.put(b.getId(), b);
            }
        }
    }

    private void manejarClicBoton(String idBoton, MouseEvent event) {
        Optional<Recurso> recursoEncontrado = memoriaRecurso.findAll().stream()
                .filter(p -> p.getNombre().equals(idBoton))
                .findFirst();

        if (recursoEncontrado.isPresent()) {
            Recurso recurso = recursoEncontrado.get();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/mapa/vistaRecursoEvent.fxml"));
                Parent root = loader.load();

                // Obtener el controlador de la ventana detalle
                VistaRecursoEvent controladorDetalle = loader.getController();

                // Pasar el recurso al controlador
                controladorDetalle.setRecurso(recurso);

                // Crear nueva escena y ventana para mostrar
                Stage stage = new Stage();
                stage.setTitle("Detalles del recurso");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana principal mientras está abierta
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al abrir la ventana de detalles");
            }

        } else {
            System.out.println("Recurso con nombre " + idBoton + " no encontrado.");
        }
    }


    public void setMemoriaRecurso(Memoria<Recurso, Integer> memoriaRecurso) {
        this.memoriaRecurso = memoriaRecurso;
        actualizarColoresCasas();        // <-- Primer actualización al inicio

        // Crear temporizador que actualice cada 5 minutos
        Timeline timeline = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(javafx.util.Duration.minutes(5), e -> actualizarColoresCasas())
        );
        timeline.setCycleCount(javafx.animation.Animation.INDEFINITE); // hace que se repita indefinidamente
        timeline.play();
    }
}
