package org.example.camping2.controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.example.camping2.Mapa.MapaCamping;
import org.example.camping2.controladores.Acompanante.AcompananteController;
import org.example.camping2.controladores.Acompanante.CrearAcompananteController;
import org.example.camping2.controladores.Clientes.ClienteController;
import org.example.camping2.controladores.Recursos.RecursoController;
import org.example.camping2.controladores.Reservas.ReservaController;
import org.example.camping2.modelo.dto.Acompanante;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.dto.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import org.example.camping2.modelo.memoria.Memoria;

import java.io.IOException;
import java.util.Locale;
import java.util.logging.Logger;

import static org.example.camping2.controladores.GestorIdiomas.cambiarIdioma;
import static org.example.camping2.controladores.GestorIdiomas.getTexto;

public class MenuNew implements IdiomaListener {

    private AnchorPane raiz;

    private static final Logger logger = LogConfig.configurarLogger(MenuNew.class);

    @FXML
    private StackPane contenedorCentral;
    @FXML
    private TreeView<String> treeMenu;
    private Memoria<Cliente, Integer> memoriaCliente;
    private Memoria<Reserva, Integer> memoriaReserva;
    private Memoria<Recurso, Integer> memoriaRecurso;
    private Memoria<Acompanante, Integer> memoriaAcompanante;
    private ClienteController clienteController;
    private AcompananteController acompananteController;
    private RecursoController recursoController;
    private ReservaController reservaController;

    @FXML
    private ComboBox<IdiomaVisual> comboBoxIdiomas;
    private TreeItem<String> cliente;
    private TreeItem<String> acompanante;
    private TreeItem<String> recurso;
    private TreeItem<String> reserva;
    private TreeItem<String> mapa;
    private TreeItem<String> generarInformes;

    @FXML
    public void initialize() {
        TreeItem<String> root = new TreeItem<>("Menú");
        root.setExpanded(true);
        logger.warning("INICIADO");

        // Inicializar memorias
        memoriaCliente = new Memoria<>(Cliente.class);
        memoriaReserva = new Memoria<>(Reserva.class);
        memoriaRecurso = new Memoria<>(Recurso.class);
        memoriaAcompanante = new Memoria<>(Acompanante.class);

        // Inicializar controladores y pasar memorias y contenedorCentral
        clienteController = new ClienteController();
        clienteController.setLogger(logger);
        clienteController.setMemoria(memoriaCliente);
        clienteController.setAreaContenido(contenedorCentral);

        acompananteController = new AcompananteController();
        acompananteController.setLogger(logger);
        acompananteController.setMemoriaAcompanante(memoriaAcompanante);
        acompananteController.setMemoriaReserva(memoriaReserva);
        acompananteController.setAreaContenido(contenedorCentral);


        recursoController = new RecursoController();
        recursoController.setLogger(logger);
        recursoController.setMemoria(memoriaRecurso);
        recursoController.setAreaContenido(contenedorCentral);

        reservaController = new ReservaController();
        reservaController.setLogger(logger);
        reservaController.setMemoriaReserva(memoriaReserva);
        reservaController.setMemoriaRecurso(memoriaRecurso);
        reservaController.setMemoriaCliente(memoriaCliente);
        reservaController.setAreaContenido(contenedorCentral);

        // Crear nodos principales del árbol
        cliente = new TreeItem<>("Cliente");
        acompanante = new TreeItem<>("Acompañante");
        recurso = new TreeItem<>("Recurso");
        reserva = new TreeItem<>("Reserva");
        mapa = new TreeItem<>("Mapa");
        generarInformes = new TreeItem<>("Generar Informes");



        // Añadir acciones a cada nodo
        cliente.getChildren().addAll(
                new TreeItem<>("Buscar"),
                new TreeItem<>("Añadir"),
                new TreeItem<>("Modificar"),
                new TreeItem<>("Eliminar")
        );

        acompanante.getChildren().addAll(
                new TreeItem<>("Buscar"),
                new TreeItem<>("Añadir"),
                new TreeItem<>("Modificar"),
                new TreeItem<>("Eliminar")
        );

        recurso.getChildren().addAll(
                new TreeItem<>("Buscar"),
                new TreeItem<>("Añadir"),
                new TreeItem<>("Modificar"),
                new TreeItem<>("Eliminar")
        );

        reserva.getChildren().addAll(
                new TreeItem<>("Buscar"),
                new TreeItem<>("Añadir"),
                new TreeItem<>("Modificar"),
                new TreeItem<>("Eliminar")
        );

        // Añadir nodos principales al root
        root.getChildren().addAll(cliente, acompanante, recurso, reserva, mapa, generarInformes);
        treeMenu.setRoot(root);
        treeMenu.setShowRoot(false);

        // Expande el nodo cliente por defecto y selecciónalo
        cliente.setExpanded(true);
        treeMenu.getSelectionModel().select(cliente);

        // Comportamiento acordeón para que solo un nodo esté expandido a la vez
        TreeItem<String>[] mainNodes = new TreeItem[]{cliente, acompanante, recurso, reserva};
        for (TreeItem<String> node : mainNodes) {
            node.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
                if (isNowExpanded) {
                    for (TreeItem<String> otherNode : mainNodes) {
                        if (otherNode != node) {
                            otherNode.setExpanded(false);
                        }
                    }
                }
            });
        }

        treeMenu.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (newSelection.getParent() != null && !newSelection.getParent().getValue().equals("Menú")) {
                    String categoria = newSelection.getParent().getValue();
                    String accion = newSelection.getValue();
                    ejecutarAccion(categoria, accion);
                } else {
                    String valorNodo = newSelection.getValue();
                    if (valorNodo.equals(GestorIdiomas.getTexto("Mapa"))) {
                        abrirMapa();
                    } else if (valorNodo.equals("Generar Informes")) { // <-- Aquí lo manejas
                        generarInformes();
                    }
                }
            }
        });


        // Inicializar ComboBox de idiomas con imágenes
        ObservableList<IdiomaVisual> idiomas = FXCollections.observableArrayList(
                new IdiomaVisual("Español", new Image(getClass().getResourceAsStream("/org/example/camping2/imagenes/es.png"))),
                new IdiomaVisual("Inglés", new Image(getClass().getResourceAsStream("/org/example/camping2/imagenes/en.png")))
        );

        comboBoxIdiomas.setItems(idiomas);
        comboBoxIdiomas.getSelectionModel().selectFirst();

        // Renderizado de celdas con bandera + texto
        comboBoxIdiomas.setCellFactory(lv -> new ListCell<IdiomaVisual>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(IdiomaVisual item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(item.getBandera());
                    imageView.setFitWidth(24);
                    imageView.setFitHeight(24);
                    setText(item.getNombre());
                    setGraphic(imageView);
                }
            }
        });

        comboBoxIdiomas.setButtonCell(new ListCell<IdiomaVisual>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(IdiomaVisual item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(item.getBandera());
                    imageView.setFitWidth(24);
                    imageView.setFitHeight(24);
                    setText(item.getNombre());
                    setGraphic(imageView);
                }
            }
        });

        // Listener para cambio de idioma
        comboBoxIdiomas.getSelectionModel().selectedItemProperty().addListener((obs, oldIdioma, newIdioma) -> {
            if (newIdioma != null) {
                String nombreIdioma = newIdioma.getNombre();
                if ("Español".equals(nombreIdioma)) {
                    cambiarIdioma(new Locale("es", "ES"));
                } else if ("Inglés".equals(nombreIdioma)) {
                    cambiarIdioma(new Locale("en", "US"));
                }
            }
        });

        // Registrar listener de idioma
        GestorIdiomas.agregarListener(this);

        // Actualizar textos iniciales en árbol
        actualizarTextos();
    }

    private void generarInformes() {
        try {
            // Supongamos que tienes un FXML llamado MapaCamping.fxml para el mapa
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/GenerarInformes.fxml"));
            AnchorPane mapaPane = loader.load();
            GenerarInformeController mapaController = loader.getController();
            mapaController.setMemoriaRecurso(memoriaRecurso);
            mapaController.setMemoriaReserva(memoriaReserva);
            mapaController.setMemoriaAcompanante(memoriaAcompanante);
            mapaController.setMemoriaCliente(memoriaCliente);

            // Limpia y pone el mapa en el contenedor central
            contenedorCentral.getChildren().clear();
            contenedorCentral.getChildren().add(mapaPane);

            // Opcional: ajustar anclas para que el AnchorPane ocupe todo el contenedor
            AnchorPane.setTopAnchor(mapaPane, 0.0);
            AnchorPane.setBottomAnchor(mapaPane, 0.0);
            AnchorPane.setLeftAnchor(mapaPane, 0.0);
            AnchorPane.setRightAnchor(mapaPane, 0.0);

            logger.info("Mapa cargado correctamente");

        } catch (IOException e) {
            logger.severe("Error al cargar la vista MapaCamping.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void abrirMapa() {
        try {
            // Supongamos que tienes un FXML llamado MapaCamping.fxml para el mapa
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/mapa/MapaCamping.fxml"));
            AnchorPane mapaPane = loader.load();
            MapaCamping mapaController = loader.getController();
            mapaController.setMemoriaRecurso(memoriaRecurso);
            mapaController.setMemoriaReserva(memoriaReserva);



            // Limpia y pone el mapa en el contenedor central
            contenedorCentral.getChildren().clear();
            contenedorCentral.getChildren().add(mapaPane);

            // Opcional: ajustar anclas para que el AnchorPane ocupe todo el contenedor
            AnchorPane.setTopAnchor(mapaPane, 0.0);
            AnchorPane.setBottomAnchor(mapaPane, 0.0);
            AnchorPane.setLeftAnchor(mapaPane, 0.0);
            AnchorPane.setRightAnchor(mapaPane, 0.0);

            logger.info("Mapa cargado correctamente");

        } catch (IOException e) {
            logger.severe("Error al cargar la vista MapaCamping.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cambiarIdiomaEspañol() {
        cambiarIdioma(new Locale("es", "ES"));
    }


    private void cambiarIdiomaIngles() {
        cambiarIdioma(new Locale("en", "US"));
    }


    private void ejecutarAccion(String categoria, String accion) {
        String clienteTxt = GestorIdiomas.getTexto("cliente");
        String acompananteTxt = GestorIdiomas.getTexto("acompanante");
        String recursoTxt = GestorIdiomas.getTexto("recurso");
        String reservaTxt = GestorIdiomas.getTexto("reserva");

        String buscarTxt = GestorIdiomas.getTexto("buscar");
        String agregarTxt = GestorIdiomas.getTexto("agregar");
        String actualizarTxt = GestorIdiomas.getTexto("actualizar");
        String eliminarTxt = GestorIdiomas.getTexto("eliminar");

        if (categoria.equals(clienteTxt)) {
            if (accion.equals(buscarTxt)) buscarCliente();
            else if (accion.equals(agregarTxt)) anadirCliente();
            else if (accion.equals(actualizarTxt)) modificarCliente();
            else if (accion.equals(eliminarTxt)) eliminarCliente();
        } else if (categoria.equals(acompananteTxt)) {
            if (accion.equals(buscarTxt)) buscarAcompanante();
            else if (accion.equals(agregarTxt)) anadirAcompanante();
            else if (accion.equals(actualizarTxt)) modificarAcompanante();
            else if (accion.equals(eliminarTxt)) eliminarAcompanante();
        } else if (categoria.equals(recursoTxt)) {
            if (accion.equals(buscarTxt)) buscarRecurso();
            else if (accion.equals(agregarTxt)) anadirRecurso();
            else if (accion.equals(actualizarTxt)) modificarRecurso();
            else if (accion.equals(eliminarTxt)) eliminarRecurso();
        } else if (categoria.equals(reservaTxt)) {
            if (accion.equals(buscarTxt)) buscarReserva();
            else if (accion.equals(agregarTxt)) anadirReserva();
            else if (accion.equals(actualizarTxt)) modificarReserva();
            else if (accion.equals(eliminarTxt)) eliminarReserva();
        }
    }


    // Aquí pones tus métodos para cada acción. Por ejemplo:
    private void buscarCliente() {
        clienteController.BuscarPanelCliente();
        System.out.println("Buscar Cliente");
    }
    private void anadirCliente() {
        clienteController.AgregarPanelCliente();
        System.out.println("Añadir Cliente");
        // Código para abrir ventana añadir cliente...
    }
    private void modificarCliente() {
        clienteController.ModificarPanelCliente();
        System.out.println("Modificar Cliente");
        // Código para abrir ventana modificar cliente...
    }
    private void eliminarCliente() {
        clienteController.EliminarPanelCliente();
        System.out.println("Eliminar Cliente");
        // Código para abrir ventana eliminar cliente...
    }

    // Lo mismo para acompañante
    private void buscarAcompanante() {
        acompananteController.BuscarAcompanante();
    }
    private void anadirAcompanante() {
        acompananteController.CrearAcompanante();
    }
    private void modificarAcompanante() {
        acompananteController.ModificarAcompanante();
    }
    private void eliminarAcompanante() {
        acompananteController.EliminarAcompanante();
    }

    // Y para recurso
    private void buscarRecurso() {
        recursoController.BuscarRecursos();
    }
    private void anadirRecurso() {
        recursoController.CrearRecurso();
    }
    private void modificarRecurso() {
        recursoController.ModificarRecurso();
    }
    private void eliminarRecurso() {
        recursoController.EliminarRecurso();
    }

    // Y para reserva
    private void buscarReserva() {
        reservaController.BuscarReservaBoton();
    }
    private void anadirReserva() {
        reservaController.agregarReservaBoton();
    }
    private void modificarReserva() {
        reservaController.modificarReservaBoton();
    }
    private void eliminarReserva() {
        reservaController.eliminarReservaBoton();
    }


    public void setRaiz(AnchorPane raiz) {
        this.raiz = raiz;
    }

    @Override
    public void idiomaCambiado() {
        actualizarTextos();
    }

    private void actualizarTextos() {
        // Actualiza textos de árbol según el idioma actual

        // Para cada TreeItem usamos getTexto(clave), que supongo te da el texto traducido

        cliente.setValue(GestorIdiomas.getTexto("cliente"));
        cliente.getChildren().get(0).setValue(GestorIdiomas.getTexto("buscar"));
        cliente.getChildren().get(1).setValue(GestorIdiomas.getTexto("agregar"));
        cliente.getChildren().get(2).setValue(GestorIdiomas.getTexto("actualizar"));
        cliente.getChildren().get(3).setValue(GestorIdiomas.getTexto("eliminar"));

        acompanante.setValue(GestorIdiomas.getTexto("acompanante"));
        acompanante.getChildren().get(0).setValue(GestorIdiomas.getTexto("buscar"));
        acompanante.getChildren().get(1).setValue(GestorIdiomas.getTexto("agregar"));
        acompanante.getChildren().get(2).setValue(GestorIdiomas.getTexto("actualizar"));
        acompanante.getChildren().get(3).setValue(GestorIdiomas.getTexto("eliminar"));

        recurso.setValue(getTexto("recurso"));
        recurso.getChildren().get(0).setValue(GestorIdiomas.getTexto("buscar"));
        recurso.getChildren().get(1).setValue(GestorIdiomas.getTexto("agregar"));
        recurso.getChildren().get(2).setValue(GestorIdiomas.getTexto("actualizar"));
        recurso.getChildren().get(3).setValue(GestorIdiomas.getTexto("eliminar"));

        reserva.setValue(GestorIdiomas.getTexto("reserva"));
        reserva.getChildren().get(0).setValue(GestorIdiomas.getTexto("buscar"));
        reserva.getChildren().get(1).setValue(GestorIdiomas.getTexto("agregar"));
        reserva.getChildren().get(2).setValue(GestorIdiomas.getTexto("actualizar"));
        reserva.getChildren().get(3).setValue(GestorIdiomas.getTexto("eliminar"));
    }

}
