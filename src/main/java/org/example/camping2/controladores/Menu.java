package org.example.camping2.controladores;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.example.camping2.controladores.Acompanante.AcompananteController;
import org.example.camping2.controladores.Clientes.*;
import org.example.camping2.Mapa.MapaCamping;
import org.example.camping2.controladores.Recursos.RecursoController;
import org.example.camping2.controladores.Reservas.ReservaController;
import org.example.camping2.modelo.dto.Acompanante;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controller class responsible for managing client-related functionalities within the camping management application.
 * This class handles the creation of clients, client updates, deletions, searches, and generates reports based on client data.
 * It interacts with memory (`Memoria`) and manages the loading and switching of different panels.
 *
 * @author Andrews Dos Ramos
 * @version 1.0.0
 * @since 31/01/2025
 */
public class Menu implements Liberable{
    @FXML
    private ImageView buscarImage;
    @FXML
    private ImageView agregarImage;
    @FXML
    private ImageView modificarImage;
    @FXML
    private ImageView eliminarImage;
    @FXML
    private VBox panelIzquierdo; // VBox on the left side of the screen
    @FXML
    private VBox panelDerecho; // VBox on the right side of the screen
    @FXML
    private TableView<?> tablaClientes;
    @FXML
    private StackPane areaContenido; // Container for dynamically loaded content
    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidoField;
    @FXML
    private DatePicker fechaNacimientoPicker;
    @FXML
    private ComboBox<String> estadoComboBox;
    @FXML
    private MenuItem menuEspanol, menuIngles, acompananteMenu, recursoMenu, reservaMenu, clienteMenu, mapaIteractivoMenu;
    @FXML
    private javafx.scene.control.Menu gestionMenu, visualizarMenu, idiomaMenu;

    private Memoria<Cliente, Integer> memoriaCliente;
    private Memoria<Reserva, Integer> memoriaReserva;
    private Memoria<Recurso, Integer> memoriaRecurso;
    private Memoria<Acompanante, Integer> memoriaAcompanante;
    private ClienteController clienteController;

    /**
     * Initializes the controller and the various panels in the client section of the application.
     * This method is called automatically after the FXML file is loaded.
     */
    public void initialize() {
        memoriaCliente = new Memoria<>(Cliente.class);
        memoriaReserva = new Memoria<>(Reserva.class);
        memoriaRecurso = new Memoria<>(Recurso.class);
        memoriaAcompanante = new Memoria<>(Acompanante.class);


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/clientes/BotonesCrudCliente.fxml"));
            Parent nuevoPanel = loader.load();                   // ① carga el FXML y crea el controlador
            clienteController = loader.getController();  // ② ahora ya no es null
            clienteController.setMenu(menuEspanol, menuIngles,acompananteMenu, recursoMenu, reservaMenu, clienteMenu, mapaIteractivoMenu, gestionMenu, visualizarMenu, idiomaMenu, gestionMenu, visualizarMenu, idiomaMenu, Idioma.idioma);
            clienteController.setAreaContenido(areaContenido);
            clienteController.setMemoria(memoriaCliente);
            panelIzquierdo.getChildren().clear();
            panelIzquierdo.getChildren().add(nuevoPanel);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensaje("Error al cargar el panel izquierdo: " + e.getMessage());
        }
    }


    public void alternarPanelDerecho() {
        panelDerecho.setVisible(!panelDerecho.isVisible());
    }
    /**
     * Sets the memory service that is responsible for storing and managing clients.
     *
     * @param memoriaCliente The memory service used for client data.
     */
    public void setMemoriaCliente(Memoria<Cliente, Integer> memoriaCliente) {
        this.memoriaCliente = memoriaCliente;
    }


    /**
     * Handles the button click for generating a report with client data based on the input fields.
     * The report is generated using the JasperReports library and exported to a PDF file.
     */
    @FXML
    private void generarReporte() {
        try {
            // Prepare parameters for the report
            Map<String, Object> parametros = new HashMap<>();

            // Si el campo de nombre no está vacío, filtrar por aquellos nombres que comienzan con la letra proporcionada
            String nombre = nombreField.getText().isEmpty() ? null : nombreField.getText();
            if (nombre != null && !nombre.isEmpty()) {
                // Si el nombre empieza con "A", podemos poner un filtro en el reporte para que solo se muestren los que comienzan con "A"
                parametros.put("nombre", nombre + "%"); // El "%" es para la consulta SQL para el "LIKE"
            } else {
                parametros.put("nombre", null);
            }

            // Si el campo de apellido no está vacío, filtrar por aquellos apellidos que comienzan con la letra proporcionada
            String apellido = apellidoField.getText().isEmpty() ? null : apellidoField.getText();
            if (apellido != null && !apellido.isEmpty()) {
                parametros.put("apellido", apellido + "%"); // Similar al filtro de nombre
            } else {
                parametros.put("apellido", null);
            }

            // Si la fecha de nacimiento no es nula, convertirla a java.sql.Date
            parametros.put("fechaNacimiento", fechaNacimientoPicker.getValue() == null ? null : java.sql.Date.valueOf(fechaNacimientoPicker.getValue()));

            // Solo agregar el estado si no es "NINGUN ESTADO"
            String estado = estadoComboBox.getValue();
            if (estado == null || estado.isEmpty() || "NINGUN ESTADO".equals(estado)) {
                parametros.put("estado", null); // No se pasa el estado
            } else {
                parametros.put("estado", estado);
            }

            // Obtener la conexión a la base de datos
            Connection conexion = obtenerConexion();

            // Llenar el reporte con la conexión, los parámetros y el diseño
            JasperPrint jasperPrint = JasperFillManager.fillReport(Menu.class.getResource("/Camping/Flower.jasper").getPath(),
                    parametros,
                    conexion
            );

            // Exportar el reporte a un archivo PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/resources/Pdf/report.pdf");

            // Mostrar el reporte
            JasperViewer.viewReport(jasperPrint, false);

            // Cerrar la conexión
            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error al generar el reporte: " + e.getMessage());
        }
    }

    /**
     * Establishes a connection to the database using JDBC.
     *
     * @return The database connection.
     */
    private Connection obtenerConexion() {
        try {
            // Database connection details
            String url = "jdbc:mysql://localhost:3306/gestion_camping";
            String usuario = "andrews";
            String contraseña = "Dosramos02";
            return DriverManager.getConnection(url, usuario, contraseña);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Displays a message to the user using an informational alert.
     *
     * @param mensaje The message to display in the alert.
     */
    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acción");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    @FXML
    private void JavaDoc(){
        try {
            // Abrir el enlace en el navegador por defecto
            Desktop.getDesktop().browse(new URI("https://andrews0212.github.io/javaDocCamping/org.example.camping2/module-summary.html"));
        } catch (Exception ex) {
            // Mostrar un mensaje de error si ocurre algún problema
        }

    }


    public void cargarPanelIzquierdoReservas(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = null;
            MenuItem item = (MenuItem) actionEvent.getSource();
            String id = item.getId();  //  Aquí obtienes el ID
            // 🔁 Liberar recursos del panel actual si es necesario
            if (!panelIzquierdo.getChildren().isEmpty()) {
                Node nodoActual = panelIzquierdo.getChildren().get(0);
                Object controllerActual = nodoActual.getProperties().get("controller");
                if (controllerActual instanceof Liberable) {
                    ((Liberable) controllerActual).liberarRecursos();
                }
            }
            if(id.equals("reservaMenu")){
                 loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/reservas/BotonesCrudReserva.fxml"));

            }
            if(id.equals("clienteMenu")){
                 loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/clientes/BotonesCrudCliente.fxml"));
            }
            if(id.equals("recursoMenu")){
                loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/recurso/BotonesCrudRecurso.fxml"));
            }
            if(id.equals("acompananteMenu")){
                loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/Acompanante/BotonesCrudAcompaniante.fxml"));
            }
            Parent nuevoPanel = loader.load();
            // Inyectamos areaContenido en el nuevo controlador de botones
            if(id.equals("reservaMenu")){
                ReservaController controller = loader.getController();
                controller.setAreaContenido(areaContenido);
                controller.setMemoriaReserva(memoriaReserva);
                controller.setMemoriaRecurso(memoriaRecurso);
                controller.setMemoriaCliente(memoriaCliente);
                panelIzquierdo.getChildren().clear();
                panelIzquierdo.getChildren().add(nuevoPanel);

            }
            if(id.equals("clienteMenu")){
                clienteController = loader.getController();  // ② ahora ya no es null
                clienteController.setMenu(menuEspanol, menuIngles,acompananteMenu, recursoMenu, reservaMenu, clienteMenu, mapaIteractivoMenu, gestionMenu, visualizarMenu, idiomaMenu, gestionMenu, visualizarMenu, idiomaMenu, Idioma.idioma);
                clienteController.setMemoria(memoriaCliente);
                clienteController.setAreaContenido(areaContenido);
                panelIzquierdo.getChildren().clear();
                panelIzquierdo.getChildren().add(nuevoPanel);
            }
            if(id.equals("recursoMenu")){
                RecursoController controller = loader.getController();
                controller.setAreaContenido(areaContenido);
                controller.setMemoria(memoriaRecurso);
                panelIzquierdo.getChildren().clear();
                panelIzquierdo.getChildren().add(nuevoPanel);
            }
            if(id.equals("acompananteMenu")){
                AcompananteController controller = loader.getController();
                controller.setAreaContenido(areaContenido);
                controller.setMemoriaAcompanante(memoriaAcompanante);
                controller.setMemoriaReserva(memoriaReserva);
                panelIzquierdo.getChildren().clear();
                panelIzquierdo.getChildren().add(nuevoPanel);
            }
        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensaje("Error al cargar el panel izquierdo: " + e.getMessage());
        }
    }

    public void setAreaContenido(StackPane areaContenido) {
        this.areaContenido = areaContenido;
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
    @FXML
    public void cargarVentanaMapa(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/mapa/MapaCamping.fxml"));
            Parent root = loader.load();
            MapaCamping controller = loader.getController();
            controller.setMemoriaRecurso(memoriaRecurso);
            Stage stage = new Stage();
            stage.setTitle("Mapa del Camping");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


