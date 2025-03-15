package org.example.camping2.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.example.camping2.dto.Cliente;
import org.example.camping2.memoria.Memoria;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller class responsible for managing client-related functionalities within the camping management application.
 * This class handles the creation of clients, client updates, deletions, searches, and generates reports based on client data.
 * It interacts with memory (`Memoria`) and manages the loading and switching of different panels.
 *
 * @author Andrews Dos Ramos
 * @version 1.0.0
 * @since 31/01/2025
 */
public class ClienteController {

    @FXML
    private VBox panelDerecho; // VBox on the right side of the screen
    @FXML
    private TableView<?> tablaClientes;

    @FXML
    private Button btnCliente, btnRecurso, btnReservas, btnAñadir, btnEliminar, btnActualizar, btnBuscar;

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

    private Memoria<Cliente, Integer> memoria;


    /**
     * Initializes the controller and the various panels in the client section of the application.
     * This method is called automatically after the FXML file is loaded.
     */
    public void initialize() {
        memoria = new Memoria<>(Cliente.class);
    }

    /**
     * Sets the memory service that is responsible for storing and managing clients.
     *
     * @param memoria The memory service used for client data.
     */
    public void setMemoria(Memoria<Cliente, Integer> memoria) {
        this.memoria = memoria;
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
            parametros.put("nombre", nombreField.getText().isEmpty() ? null : nombreField.getText());
            parametros.put("apellido", apellidoField.getText().isEmpty() ? null : apellidoField.getText());
            parametros.put("fechaNacimiento", fechaNacimientoPicker.getValue() == null ? null : java.sql.Date.valueOf(fechaNacimientoPicker.getValue()));
            parametros.put("estado", estadoComboBox.getValue() == null || estadoComboBox.getValue().isEmpty() ? null : estadoComboBox.getValue());

            // Get the database connection
            Connection conexion = obtenerConexion();

            // Fill the report with the connection, parameters, and design
            JasperPrint jasperPrint = JasperFillManager.fillReport(ClienteController.class.getResource("/Camping/Flower.jasper").getPath(),
                    parametros,
                    conexion
            );

            // Export the report to PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/resources/Pdf/report.pdf");

            // Display the report
            JasperViewer.viewReport(jasperPrint, false);

            // Close the connection
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
            String usuario = "root";
            String contraseña = "";
            return DriverManager.getConnection(url, usuario, contraseña);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Toggles the visibility of the right panel (panelDerecho).
     * Used for showing or hiding additional content in the right panel.
     */
    public void alternarPanelDerecho() {
        panelDerecho.setVisible(!panelDerecho.isVisible());
    }

    /**
     * Loads the "Add Client" panel into the content area.
     * This method is called when the "Add" button is pressed.
     */
    @FXML
    private void manejarBotonAñadir() {
        cargarPanel("/org/example/camping2/AñadirClientePanel.fxml");
    }

    /**
     * Loads the "Delete Client" panel into the content area.
     * This method is called when the "Delete" button is pressed.
     */
    @FXML
    private void manejarBotonEliminar() {
        cargarPanel("/org/example/camping2/EliminarClientePane.fxml");
    }

    /**
     * Loads the "Update Client" panel into the content area.
     * This method is called when the "Update" button is pressed.
     */
    @FXML
    private void manejarBotonActualizar() {
        cargarPanel("/org/example/camping2/ActualizarClientePanel.fxml");
    }

    /**
     * Loads the "Search Client" panel into the content area.
     * This method is called when the "Search" button is pressed.
     */
    @FXML
    private void manejarBotonBuscar() {

        cargarPanel("/org/example/camping2/BuscarClientePanel.fxml");

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

    /**
     * Loads the given FXML file into the content area (StackPane).
     * This method is responsible for dynamically loading and displaying different client-related panels.
     *
     * @param archivoFXML The path to the FXML file to load.
     */
    private void cargarPanel(String archivoFXML) {
        try {
            // Clear the content area before adding a new panel
            areaContenido.getChildren().clear();

            // Load the FXML file for the specified panel
            FXMLLoader loader = new FXMLLoader(getClass().getResource(archivoFXML));
            Parent nuevoPanel = loader.load();

            // Get the controller of the loaded panel and pass the memory reference
            if (archivoFXML.equals("/org/example/camping2/AñadirClientePanel.fxml")) {
                AñadirClienteController controlador = loader.getController();
                controlador.setMemoria(memoria);
            } else if (archivoFXML.equals("/org/example/camping2/EliminarClientePane.fxml")) {
                EliminarClienteController controlador = loader.getController();
                controlador.setMemoria(memoria);
            } else if (archivoFXML.equals("/org/example/camping2/ActualizarClientePanel.fxml")) {
                ModificarClienteController controlador = loader.getController();
                controlador.setMemoria(memoria);
            } else if (archivoFXML.equals("/org/example/camping2/BuscarClientePanel.fxml")) {
                BuscarClientePanelController controlador = loader.getController();
                controlador.setMemoria(memoria);
            }

            // Add the loaded panel to the content area
            areaContenido.getChildren().add(nuevoPanel);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensaje("Error al cargar el panel: " + e.getMessage());
        }
    }
}
