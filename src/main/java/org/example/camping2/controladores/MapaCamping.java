package org.example.camping2.controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private Memoria<Recurso, Integer> memoriaRecurso;
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
    }
}
