package org.example.camping2.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.example.camping2.modelo.dto.Acompanante;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class GenerarInformeController {

    private Memoria<Recurso, Integer> memoriaRecurso;
    private Memoria<Reserva, Integer> memoriaReserva;
    private Memoria<Cliente, Integer> memoriaCliente;
    private Memoria<Acompanante, Integer> memoriaAcompanante;

    @FXML private Label labelCliente;
    @FXML private TextField nombreClienteField;
    @FXML private TextField apellidoClienteField;
    @FXML private DatePicker fNacClienteField;
    @FXML private ComboBox<String> estadoClienteCombo;
    @FXML private Label labelNombre;
    @FXML private Label labelNombreAcom;
    @FXML private Label labelApellido;
    @FXML private Label labelApellidoAcom;
    @FXML private Label labelFNacimiento;
    @FXML private Label labelEstado;

    @FXML private Label labelReservas;
    @FXML private Label labelAcompanante;
    @FXML private Label labelRecurso;

    @FXML private Label labelDNI;
    @FXML private Label labelFInicio;
    @FXML private Label labelFechaFin;
    @FXML private Label labelEstadoReserva;

    @FXML private Label labelIDReserva;
    @FXML private TextField nombreAcomField;
    @FXML private TextField apellidoAcomField;
    @FXML private DatePicker fNacAcomField;
    @FXML private TextField idReservaAcomField;

    @FXML private Label labelFechaNacimiento;

    @FXML private TextField NombreRecuField;
    @FXML private Label labelNombreRecu;
    @FXML private Label labelTipo;
    @FXML private Label labelCapacidad;
    @FXML private ComboBox<String> tipoRecuCombo;
    @FXML private TextField capacidadRecuText;
    @FXML private ComboBox<String> estadoRecuCombo;
    @FXML private Label labelEstadoRecu;

    @FXML private TextField dniReservasField;
    @FXML private DatePicker fechaInicioDate;
    @FXML private DatePicker fechaFinDate;
    @FXML private ComboBox<String> estadoReservaCombo;

    @FXML private Button btnGenerarCliente;
    @FXML private Label labelGenerar;

    private Map<String, String> mapaClienteTraducido;
    private Map<String, String> mapaRecursoTraducido;
    private Map<String, String> mapaReservaTraducido;


    @FXML
    private void initialize(){
        mapaClienteTraducido = new HashMap<>();
        mapaClienteTraducido.put("ACTIVO", GestorIdiomas.getTexto("ACTIVO"));
        mapaClienteTraducido.put("BLOQUEADO", GestorIdiomas.getTexto("BLOQUEADO"));
        mapaClienteTraducido.put("SUSPENDIDO", GestorIdiomas.getTexto("SUSPENDIDO"));
        ObservableList<String> traducciones = FXCollections.observableArrayList(mapaClienteTraducido.values());
        estadoClienteCombo.setItems(traducciones);
        estadoClienteCombo.getSelectionModel().select(0);
        mapaRecursoTraducido = new HashMap<>();
        mapaRecursoTraducido.put("PARCELA", GestorIdiomas.getTexto("PARCELA"));
        mapaRecursoTraducido.put("BUNGALOW", GestorIdiomas.getTexto("BUNGALOW"));
        mapaRecursoTraducido.put("BARBACOA", GestorIdiomas.getTexto("BARBACOA"));
        ObservableList<String> traduccionesRecurso = FXCollections.observableArrayList(mapaRecursoTraducido.values());
        estadoRecuCombo.setItems(traduccionesRecurso);
        estadoRecuCombo.getSelectionModel().select(0);
        mapaReservaTraducido = new HashMap<>();
        mapaReservaTraducido.put("PENDIENTE", GestorIdiomas.getTexto("PENDIENTE"));
        mapaReservaTraducido.put("CANCELADA", GestorIdiomas.getTexto("CANCELADA"));
        mapaReservaTraducido.put("CONFIRMADA", GestorIdiomas.getTexto("CONFIRMADA"));
        ObservableList<String> traduccionesReserva = FXCollections.observableArrayList(mapaReservaTraducido.values());
        estadoReservaCombo.setItems(traduccionesReserva);
        estadoReservaCombo.getSelectionModel().select(0);
    }


    public void setMemoriaRecurso(Memoria<Recurso, Integer> memoriaRecurso) {
        this.memoriaRecurso = memoriaRecurso;
    }

    public void setMemoriaReserva(Memoria<Reserva, Integer> memoriaReserva) {
        this.memoriaReserva = memoriaReserva;
    }

    public void setMemoriaCliente(Memoria<Cliente, Integer> memoriaCliente) {
        this.memoriaCliente = memoriaCliente;
    }

    public void setMemoriaAcompanante(Memoria<Acompanante, Integer> memoriaAcompanante) {
        this.memoriaAcompanante = memoriaAcompanante;
    }
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
    @FXML
    private void generarReporte() {
        try {
            // Prepare parameters for the report
            Map<String, Object> parametros = new HashMap<>();

            // Si el campo de nombre no está vacío, filtrar por aquellos nombres que comienzan con la letra proporcionada
            String nombre = nombreClienteField.getText().isEmpty() ? null : nombreAcomField.getText();
            if (nombre != null && !nombre.isEmpty()) {
                // Si el nombre empieza con "A", podemos poner un filtro en el reporte para que solo se muestren los que comienzan con "A"
                parametros.put("nombre", nombre + "%"); // El "%" es para la consulta SQL para el "LIKE"
            } else {
                parametros.put("nombre", null);
            }

            // Si el campo de apellido no está vacío, filtrar por aquellos apellidos que comienzan con la letra proporcionada
            String apellido = apellidoClienteField.getText().isEmpty() ? null : apellidoClienteField.getText();
            if (apellido != null && !apellido.isEmpty()) {
                parametros.put("apellido", apellido + "%"); // Similar al filtro de nombre
            } else {
                parametros.put("apellido", null);
            }

            // Si la fecha de nacimiento no es nula, convertirla a java.sql.Date
            parametros.put("fechaNacimiento", fNacClienteField.getValue() == null ? null : java.sql.Date.valueOf(fNacClienteField.getValue()));

            // Solo agregar el estado si no es "NINGUN ESTADO"
            String estado = estadoClienteCombo.getValue();
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

        }
    }
}
