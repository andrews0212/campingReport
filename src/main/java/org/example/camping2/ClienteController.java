package org.example.camping2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class ClienteController {

    @FXML
    private VBox panelDerecho; // Este es el VBox en el lado derecho
    @FXML
    private TableView<?> tablaClientes;

    @FXML
    private Button btnCliente, btnRecurso, btnReservas, btnAñadir, btnEliminar, btnActualizar, btnBuscar;

    @FXML
    private StackPane areaContenido; // Contenedor para el contenido que cambia

    @FXML
    private TextField nombreField;

    @FXML
    private TextField apellidoField;

    @FXML
    private DatePicker fechaNacimientoPicker;

    @FXML
    private ComboBox<String> estadoComboBox;

    @FXML
    private void generarReporte() {
        try {
            // Crear el mapa de parámetros
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("nombre", nombreField.getText().isEmpty() ? null : nombreField.getText());
            parametros.put("apellido", apellidoField.getText().isEmpty() ? null : apellidoField.getText());
            parametros.put("fechaNacimiento", fechaNacimientoPicker.getValue() == null ? null : java.sql.Date.valueOf(fechaNacimientoPicker.getValue()));
            parametros.put("estado", estadoComboBox.getValue() == null || estadoComboBox.getValue().isEmpty() ? null : estadoComboBox.getValue());


            // Obtener conexión a la base de datos
            Connection conexion = obtenerConexion();

            // Llenar el reporte con la conexión, parámetros y diseño
            JasperPrint jasperPrint = JasperFillManager.fillReport(ClienteController.class.getResource("/Camping/Flower.jasper").getPath(),
                    parametros,
                    conexion
            );

            // Exportar el reporte a PDF
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


    private Connection obtenerConexion() {
        try {
            // Cambia estos datos según tu configuración
            String url = "jdbc:mysql://localhost:3306/gestion_camping";
            String usuario = "root";
            String contraseña = "";
            return DriverManager.getConnection(url, usuario, contraseña);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Memoria<Cliente, Integer> memoria;

    public void alternarPanelDerecho() {
        // Alternar la visibilidad del panelDerecho
        panelDerecho.setVisible(!panelDerecho.isVisible());
    }

    public ClienteController() {
    }

    public void initialize() {
        // Inicializa la tabla o cualquier otra lógica aquí
    }

    public void setMemoria(Memoria<Cliente, Integer> memoria) {
        this.memoria = memoria;
    }

    @FXML
    private void manejarBotonAñadir() {
        // Acción para el botón Añadir: Agregar un nuevo cliente
        cargarPanel("AñadirClientePanel.fxml"); // Cargar el panel para añadir clientes
    }

    @FXML
    private void manejarBotonEliminar() {
        // Acción para el botón Eliminar: Eliminar un cliente
        cargarPanel("EliminarClientePane.fxml"); // Cargar el panel para eliminar clientes
    }

    @FXML
    private void manejarBotonActualizar() {
        // Acción para el botón Actualizar: Actualizar los datos del cliente
        cargarPanel("ActualizarClientePanel.fxml"); // Cargar el panel para actualizar datos
    }

    @FXML
    private void manejarBotonBuscar() {
        // Acción para el botón Buscar: Buscar un cliente
        cargarPanel("BuscarClientePanel.fxml"); // Cargar el panel para buscar clientes
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Acción");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para cargar diferentes paneles en el StackPane
    private void cargarPanel(String archivoFXML) {
        try {
            // Limpiar el área de contenido antes de añadir un nuevo panel
            areaContenido.getChildren().clear();

            // Cargar el archivo FXML correspondiente
            FXMLLoader loader = new FXMLLoader(getClass().getResource(archivoFXML));
            Parent nuevoPanel = loader.load();

            // Obtener el controlador del panel cargado y pasarle la referencia de memoria
            if (archivoFXML.equals("AñadirClientePanel.fxml")) {
                AñadirClienteController controlador = loader.getController();
                controlador.setMemoria(memoria);  // Pasar memoria
            } else if (archivoFXML.equals("EliminarClientePane.fxml")) {
                EliminarClienteController controlador = loader.getController();
                controlador.setMemoria(memoria);  // Pasar memoria
            } else if (archivoFXML.equals("ActualizarClientePanel.fxml")) {
                ModificarClienteController controlador = loader.getController();
                controlador.setMemoria(memoria);  // Pasar memoria
            } else if (archivoFXML.equals("BuscarClientePanel.fxml")) {
                BuscarClientePanelController controlador = loader.getController();
                controlador.setMemoria(memoria);  // Pasar memoria
            }

            // Agregar el nuevo panel al StackPane
            areaContenido.getChildren().add(nuevoPanel);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensaje("Error al cargar el panel: " + e.getMessage());
        }
    }

}
