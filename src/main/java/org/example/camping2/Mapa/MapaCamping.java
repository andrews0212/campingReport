package org.example.camping2.Mapa;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MapaCamping implements IdiomaListener {

    private static final Map<String, Button> botones = new HashMap<>();

    private static Map<String, ImageView> casas = new HashMap<>();


    private Memoria<Recurso, Integer> memoriaRecurso;
    private Memoria<Reserva, Integer> memoriaReserva;

    @FXML private TableView tablaBungalow;
    @FXML private TableColumn<Recurso, String> bungalowColumn;
    @FXML private Label labelMapaCamping, labelMantenimiento, labelDisponible, labelOcupado;
    @FXML private Button btnRefrezcar;
    //=== Imagenes parcelas == /

    @FXML private ImageView parcelaA30;
    @FXML private ImageView parcelaA29;
    @FXML private ImageView parcelaA28;
    @FXML private ImageView parcelaA27;
    @FXML private ImageView parcelaA26;
    @FXML private ImageView parcelaA25;
    @FXML private ImageView parcelaA24;
    @FXML private ImageView parcelaA23;
    @FXML private ImageView parcelaA22;
    @FXML private ImageView parcelaA21;
    @FXML private ImageView parcelaA20;
    @FXML private ImageView parcelaA19;
    @FXML private ImageView parcelaA18;
    @FXML private ImageView parcelaA17;
    @FXML private ImageView parcelaA16;
    @FXML private ImageView parcelaA15;
    @FXML private ImageView parcelaA14;
    @FXML private ImageView parcelaA13;
    @FXML private ImageView parcelaA12;
    @FXML private ImageView parcelaA9;
    @FXML private ImageView parcelaA8;
    @FXML private ImageView parcelaA7;
    @FXML private ImageView parcelaA6;
    @FXML private ImageView parcelaA5;
    @FXML private ImageView parcelaA4;
    @FXML private ImageView parcelaA3;
    @FXML private ImageView parcelaA2;
    @FXML private ImageView parcelaS39;
    @FXML private ImageView parcelaS38;
    @FXML private ImageView parcelaS37;
    @FXML private ImageView parcelaS36;
    @FXML private ImageView parcelaS35;
    @FXML private ImageView parcelaS34;
    @FXML private ImageView parcelaS33;
    @FXML private ImageView parcelaS32;
    @FXML private ImageView parcelaS31;
    @FXML private ImageView parcelaS30;
    @FXML private ImageView parcelaS29;
    @FXML private ImageView parcelaS28;
    @FXML private ImageView parcelaS27;
    @FXML private ImageView parcelaS26;
    @FXML private ImageView parcelaS25;
    @FXML private ImageView parcelaS24;
    @FXML private ImageView parcelaS23;
    @FXML private ImageView parcelaS22;
    @FXML private ImageView parcelaS21;
    @FXML private ImageView parcelaS18;
    @FXML private ImageView parcelaS17;
    @FXML private ImageView parcelaS16;
    @FXML private ImageView parcelaS15;
    @FXML private ImageView parcelaS14;
    @FXML private ImageView parcelaS13;

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
    @FXML private Button B5;
    @FXML private Button B6;
    @FXML private Button B7;
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

    @FXML private Button PR14;
    @FXML private Button PR13;
    @FXML private Button PR12;
    @FXML private Button PR11;
    @FXML private Button PR4;
    @FXML private Button PR15;

    public static Map<String, Button> getBotones() {
        return botones;
    }

    public static Map<String, ImageView> getCasas() {
        return casas;
    }
    // En MapaCamping.java
    public static ImageView getCasa(String id) {
        return casas.get(id);
    }

    @FXML
    public void initialize() {

        agregarBotones(
                EL1, EL2, EL3, EL4,
                DI, DII,B5,B6,B7,
                B10, B11, B12, B13, B14, B15, B16, B17, B18, B19,
                B20, B21, B22, B23, B24, B25, B26, B27, B28, B29,
                A2, A3, A4, A5, A6, A7, A8, A9,
                A12, A13, A14, A15, A16, A17, A18, A19,
                A20, A21, A22, A23, A24, A25, A26, A27, A28, A29, A30,
                S13,S14,S15,
                S16, S17, S18, S21, S22, S23,
                S24, S25, S26, S27, S28, S29, S30, S31,
                S32, S33, S34, S35, S36, S37, S38, S39, PR4, PR11, PR12, PR13, PR14, PR15
        );

        // Asignar evento de clic a cada botón
        for (Map.Entry<String, Button> entry : botones.entrySet()) {
            Button boton = entry.getValue();
            boton.setOnMouseClicked(event -> manejarClicBoton(entry.getKey(), event));
        }
        prepararReferenciasCasas();      // <-- Llama este nuevo método

        bungalowColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));

        GestorIdiomas.agregarListener(this);
        actualizarTexto();


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
        casas.put("A30", parcelaA30);
        casas.put("A29", parcelaA29);
        casas.put("A28", parcelaA28);
        casas.put("A27", parcelaA27);
        casas.put("A26", parcelaA26);
        casas.put("A25", parcelaA25);
        casas.put("A24", parcelaA24);
        casas.put("A23", parcelaA23);
        casas.put("A22", parcelaA22);
        casas.put("A21", parcelaA21);
        casas.put("A20", parcelaA20);
        casas.put("A19", parcelaA19);
        casas.put("A18", parcelaA18);
        casas.put("A17", parcelaA17);
        casas.put("A16", parcelaA16);
        casas.put("A15", parcelaA15);
        casas.put("A14", parcelaA14);
        casas.put("A13", parcelaA13);
        casas.put("A12", parcelaA12);
        casas.put("A9", parcelaA9);
        casas.put("A8", parcelaA8);
        casas.put("A7", parcelaA7);
        casas.put("A6", parcelaA6);
        casas.put("A5", parcelaA5);
        casas.put("A4", parcelaA4);
        casas.put("A3", parcelaA3);
        casas.put("A2", parcelaA2);
        casas.put("S39", parcelaS39);
        casas.put("S38", parcelaS38);
        casas.put("S37", parcelaS37);
        casas.put("S36", parcelaS36);
        casas.put("S35", parcelaS35);
        casas.put("S34", parcelaS34);
        casas.put("S33", parcelaS33);
        casas.put("S32", parcelaS32);
        casas.put("S31", parcelaS31);
        casas.put("S30", parcelaS30);
        casas.put("S29", parcelaS29);
        casas.put("S28", parcelaS28);
        casas.put("S27", parcelaS27);
        casas.put("S26", parcelaS26);
        casas.put("S25", parcelaS25);
        casas.put("S24", parcelaS24);
        casas.put("S23", parcelaS23);
        casas.put("S22", parcelaS22);
        casas.put("S21", parcelaS21);
        casas.put("S18", parcelaS18);
        casas.put("S17", parcelaS17);
        casas.put("S16", parcelaS16);
        casas.put("S15", parcelaS15);
        casas.put("S14", parcelaS14);
        casas.put("S13", parcelaS13);

    }
    public void actualizarColoresCasas() {
        if (memoriaRecurso != null && memoriaReserva != null){
            memoriaRecurso.actualizarMemoriaBD();
            memoriaReserva.actualizarMemoriaBD();
        }

        for (Map.Entry<String, ImageView> entry : casas.entrySet()) {
            String id = entry.getKey();
            ImageView casa = entry.getValue();

            // Buscar recurso por nombre (id)
            Optional<Recurso> recursoOpt = memoriaRecurso.findAll().stream()
                    .filter(r -> r.getNombre().equals(id))
                    .findFirst();

            if (recursoOpt.isPresent()) {
                Recurso recurso = recursoOpt.get();

                // Si el recurso es barbacoa, no hacemos nada, pasamos al siguiente
                if (recurso.getTipo().toLowerCase().startsWith("barbacoa")) {
                    continue; // salta a la siguiente iteración del for
                }

                String estado = recurso.getEstado().toLowerCase();
                String imagen;

                if (recurso.getTipo().toLowerCase().startsWith("parcela")) {
                    switch (estado) {
                        case "disponible":
                            imagen = "acampada_verde.png";
                            break;
                        case "ocupado":
                            imagen = "acampada_rojo.png";
                            break;
                        case "mantenimiento":
                            imagen = "acampada_amarillo.png";
                            break;
                        default:
                            imagen = "acampada_verde.png";
                            break;
                    }
                } else {
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
                            imagen = "home_verde.png";
                            break;
                    }
                }

                casa.setImage(new Image(
                        getClass().getResource("/org/example/camping2/Iconos/" + imagen).toExternalForm()));

            } else {
                // Si no encuentra el recurso, puedes poner imagen por defecto o nada
                casa.setImage(new Image(
                        getClass().getResource("/org/example/camping2/Iconos/home_verde.png").toExternalForm()));
            }
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

        if (idBoton.startsWith("PR")) {
            // Abrir ventana MerenderoControlador y pasarle las barbacoas adecuadas
            abrirVentanaMerendero(idBoton);
        } else {
            mostrarDetalleRecurso(idBoton); // Lo que ya hacías con los demás botones
        }

    }

    private void abrirVentanaMerendero(String prId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/mapa/Merenderos.fxml"));
            Parent root = loader.load();

            MerenderoControlador controlador = loader.getController();

            int[] rango = switch (prId) {
                case "PR4" -> new int[]{1, 10};
                case "PR11" -> new int[]{11, 20};
                case "PR12" -> new int[]{21, 30};
                case "PR13" -> new int[]{31, 40};
                case "PR14" -> new int[]{41, 50};
                case "PR15" -> new int[]{51, 60};
                default -> new int[]{1, 10};
            };

            final int inicio = rango[0];
            final int fin = rango[1];
            memoriaRecurso.actualizarMemoriaBD();

            List<Recurso> barbacoas = memoriaRecurso.findAll().stream()
                    .filter(r -> r.getTipo().toLowerCase().contains("barbacoa"))
                    .filter(r -> {
                        try {
                            String numeroStr = r.getNombre().replaceAll("[^0-9]", "");
                            int numero = Integer.parseInt(numeroStr);
                            return numero >= inicio && numero <= fin;
                        } catch (Exception e) {
                            return false;
                        }
                    })
                    .collect(Collectors.toList());


            // Pasar barbacoas al controlador
            controlador.setBarbacoas(barbacoas);
            controlador.setMemoriaReserva(memoriaReserva);
            controlador.setMemoriaRecurso(memoriaRecurso);


            Stage stage = new Stage();
            stage.setTitle("Merendero " + prId);
            stage.setScene(new Scene(root));
            Stage primaryStage = (Stage) casaB12.getScene().getWindow();
            stage.initOwner(primaryStage); // <-- aquí el stage principal, no null
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarDetalleRecurso(String idBoton) {
        Optional<Recurso> recursoEncontrado = memoriaRecurso.findAll().stream()
                .filter(p -> p.getNombre().equals(idBoton))
                .findFirst();

        if (recursoEncontrado.isEmpty()) {
            System.out.println("Recurso con nombre " + idBoton + " no encontrado.");
            return;
        }

        Recurso recurso = recursoEncontrado.get();
        LocalDate hoy = LocalDate.now();

        // LÓGICA CORREGIDA: Buscar la reserva activa para la fecha actual
        Optional<Reserva> reservaActualOpt = memoriaReserva.findAll().stream()
                .filter(reserva -> reserva.getIdrecurso().getId().equals(recurso.getId()) &&
                        !hoy.isBefore(reserva.getFechaInicio()) && // hoy >= fechaInicio
                        !hoy.isAfter(reserva.getFechaFin()))      // hoy <= fechaFin
                .findFirst();

        Reserva reservaEncontrada = reservaActualOpt.orElse(null);
        Cliente cliente = null;
        if (reservaEncontrada != null) {
            cliente = reservaEncontrada.getIdcliente();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/mapa/vistaRecursoEvent.fxml"));
            Parent root = loader.load();

            VistaRecursoEvent controladorDetalle = loader.getController();
            controladorDetalle.setCliente(cliente); // Puede ser null si no hay reserva hoy
            controladorDetalle.setRecurso(recurso);

            Stage stage = new Stage();
            stage.setTitle("Detalles del recurso");
            stage.setScene(new Scene(root));
            Stage primaryStage = (Stage) casaB12.getScene().getWindow();
            stage.initOwner(primaryStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al abrir la ventana de detalles");
        }
    }




    public void setMemoriaRecurso(Memoria<Recurso, Integer> memoriaRecurso) {
        this.memoriaRecurso = memoriaRecurso;
        memoriaRecurso.actualizarMemoriaBD();
        actualizarColoresCasas();        // <-- Primer actualización al inicio

        // Crear temporizador que actualice cada 5 minutos
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.minutes(5), e -> actualizarColoresCasas())
        );
        timeline.setCycleCount(Animation.INDEFINITE); // hace que se repita indefinidamente
        timeline.play();
    }

    public List<Recurso> getBungalowsPorSalir() {
        memoriaReserva.actualizarMemoriaBD();
        LocalDate hoy = LocalDate.now();
        LocalTime ahora = LocalTime.now();
        LocalTime horaSalida = LocalTime.of(14, 0); // 12:00 PM

        // Solo filtra reservas con salida hoy si la hora actual es antes de las 12
        if (ahora.isBefore(horaSalida)) {
            return memoriaReserva.findAll().stream()
                    .filter(reserva -> reserva.getFechaFin().equals(hoy) && !reserva.getIdrecurso().getTipo().equals("BARBACOA"))
                    .map(Reserva::getIdrecurso)
                    .collect(Collectors.toList());
        } else {
            // Ya pasó mediodía, no quedan reservas por salir hoy
            return List.of();
        }
    }

    public void setMemoriaReserva(Memoria<Reserva, Integer> memoriaReserva) {
        this.memoriaReserva = memoriaReserva;
        memoriaReserva.actualizarMemoriaBD();
        tablaBungalow.setItems(FXCollections.observableArrayList(getBungalowsPorSalir()));
    }

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }

    private void actualizarTexto() {
        labelMapaCamping.setText(GestorIdiomas.getTexto("mapa"));
        labelMantenimiento.setText(GestorIdiomas.getTexto("MANTENIMIENTO"));
        labelDisponible.setText(GestorIdiomas.getTexto("DISPONIBLE"));
        labelOcupado.setText(GestorIdiomas.getTexto("OCUPADO"));
        bungalowColumn.setText(GestorIdiomas.getTexto("bungalowMapa"));
        btnRefrezcar.setText(GestorIdiomas.getTexto("btnRefrezcar"));

    }


}
