package org.example.camping2.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.example.camping2.modelo.dto.Acompanante;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class GenerarInformeController implements IdiomaListener {

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
    @FXML private Button btnGenerarReserva;
    @FXML private Button btnGenerarRecurso;
    @FXML private Button btnGenerarAcom;


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
        tipoRecuCombo.setItems(traduccionesRecurso);
        tipoRecuCombo.getSelectionModel().select(0);
        mapaRecursoTraducido.clear();
        mapaRecursoTraducido.put("DISPONIBLE", GestorIdiomas.getTexto("DISPONIBLE"));
        mapaRecursoTraducido.put("OCUPADO", GestorIdiomas.getTexto("OCUPADO"));
        mapaRecursoTraducido.put("MANTENIMIENTO", GestorIdiomas.getTexto("MANTENIMIENTO"));
        traduccionesRecurso = FXCollections.observableArrayList(mapaRecursoTraducido.values());
        estadoRecuCombo.setItems(traduccionesRecurso);
        estadoRecuCombo.getSelectionModel().select(0);

        mapaReservaTraducido = new HashMap<>();
        mapaReservaTraducido.put("PENDIENTE", GestorIdiomas.getTexto("PENDIENTE"));
        mapaReservaTraducido.put("CANCELADA", GestorIdiomas.getTexto("CANCELADA"));
        mapaReservaTraducido.put("CONFIRMADA", GestorIdiomas.getTexto("CONFIRMADA"));
        mapaRecursoTraducido.put("NINGUN ESTADO", GestorIdiomas.getTexto("NINGUNESTADO"));
        ObservableList<String> traduccionesReserva = FXCollections.observableArrayList(mapaReservaTraducido.values());
        estadoReservaCombo.setItems(traduccionesReserva);
        estadoReservaCombo.getSelectionModel().select(0);
        GestorIdiomas.agregarListener(this);
        actualizarTextos();
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
            boolean filtroNombre = !nombreClienteField.getText().isEmpty();
            boolean filtroApellido = !apellidoClienteField.getText().isEmpty();
            boolean filtroFecha = fNacClienteField.getValue() != null;
            boolean filtroEstado = estadoClienteCombo.getValue() != null
                    && !estadoClienteCombo.getValue().equals("NINGUN ESTADO");

            if (!filtroNombre && !filtroApellido && !filtroFecha && !filtroEstado) {
                Alert alerta = new Alert(Alert.AlertType.WARNING, "Debe ingresar al menos un filtro para generar el reporte.", ButtonType.OK);
                alerta.showAndWait();
                return;
            }

            String estado = estadoClienteCombo.getValue();
            if (estadoClienteCombo.getValue().equals("Asset")) {
                estado = "ACTIVO";
            } else if (estadoClienteCombo.getValue().equals("Blocked")) {
                estado = "BLOQUEADO";
            } else if (estadoClienteCombo.getValue().equals("Suspended")) {
                estado = "SUSPENDIDO";
            }

            Map<String, Object> parametros = new HashMap<>();
            if (filtroNombre) parametros.put("nombre", nombreClienteField.getText() + "%");
            else parametros.put("nombre", null);

            if (filtroApellido) parametros.put("apellido", apellidoClienteField.getText() + "%");
            else parametros.put("apellido", null);

            parametros.put("fechaNacimiento", filtroFecha ? java.sql.Date.valueOf(fNacClienteField.getValue()) : null);
            parametros.put("estado", filtroEstado ? estado : null);

            Connection conexion = obtenerConexion();

            String rutaJasper = Paths.get(getClass().getResource("/Camping/Cliente.jasper").toURI()).toString();

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    rutaJasper,
                    parametros,
                    conexion
            );

            // 🚨 Verificamos si no hay resultados
            if (jasperPrint.getPages().isEmpty()) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "No se encontraron resultados con los filtros aplicados.", ButtonType.OK);
                Stage stage = (Stage) capacidadRecuText.getScene().getWindow();
                alerta.initOwner(stage);
                alerta.initModality(Modality.WINDOW_MODAL);
                alerta.showAndWait();
                conexion.close();
                return;
            }

            JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/resources/Pdf/report.pdf");
            JasperViewer.viewReport(jasperPrint, false);
            conexion.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void generarReporteReserva() {
        try {
            boolean filtroDNI = dniReservasField.getText() != null && !dniReservasField.getText().trim().isEmpty();
            boolean filtroFechaInicio = fechaInicioDate.getValue() != null;
            boolean filtroFechaFin = fechaFinDate.getValue() != null;
            boolean filtroEstado = estadoReservaCombo.getValue() != null
                    && !estadoReservaCombo.getValue().equalsIgnoreCase("NINGUN ESTADO");

            if (!filtroDNI && !filtroFechaInicio && !filtroFechaFin && !filtroEstado) {
                Alert alerta = new Alert(Alert.AlertType.WARNING, "Debe ingresar al menos un filtro para generar el reporte.", ButtonType.OK);
                Stage stage = (Stage) capacidadRecuText.getScene().getWindow();
                alerta.initOwner(stage);
                alerta.initModality(Modality.WINDOW_MODAL);
                alerta.showAndWait();
                return;
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("dni", filtroDNI ? dniReservasField.getText() : null);
            parametros.put("fechaInicio", filtroFechaInicio ? java.sql.Date.valueOf(fechaInicioDate.getValue()) : null);
            parametros.put("fechaFin", filtroFechaFin ? java.sql.Date.valueOf(fechaFinDate.getValue()) : null);
            String estado = filtroEstado ? estadoReservaCombo.getValue() : null;
            if(estado.equals("Pending")){
                estado = "PENDIENTE";
            } else if (estado.equals("Cancelled")) {
                estado = "CANCELADA";
            } else if (estado.equals("Confirmed")) {
                estado = "CONFIRMADA";
            }
            parametros.put("estado", filtroEstado ? estado : null);

            Connection conexion = obtenerConexion();
            if (conexion == null) {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "No se pudo conectar a la base de datos.", ButtonType.OK);
                Stage stage = (Stage) capacidadRecuText.getScene().getWindow();
                alerta.initOwner(stage);
                alerta.initModality(Modality.WINDOW_MODAL);
                alerta.showAndWait();
                return;
            }

            String rutaJasper = Paths.get(getClass().getResource("/Camping/Reserva.jasper").toURI()).toString();

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    rutaJasper,
                    parametros,
                    conexion
            );

            conexion.close();

            if (jasperPrint.getPages().isEmpty()) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "No se encontraron reservas que coincidan con los filtros.", ButtonType.OK);
                Stage stage = (Stage) capacidadRecuText.getScene().getWindow();
                alerta.initOwner(stage);
                alerta.initModality(Modality.WINDOW_MODAL);
                alerta.showAndWait();
                return;
            }

            JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/resources/Pdf/reportReserva.pdf");
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR, "Error al generar el reporte: " + e.getMessage(), ButtonType.OK);
            Stage stage = (Stage) capacidadRecuText.getScene().getWindow();
            alerta.initOwner(stage);
            alerta.initModality(Modality.WINDOW_MODAL);
            alerta.showAndWait();
        }
    }

    @FXML
    private void generarReporteRecurso() {
        try {
            boolean filtroTipo = tipoRecuCombo.getValue() != null;
            boolean filtroCapacidad = capacidadRecuText.getText() != null && !capacidadRecuText.getText().trim().isEmpty();
            boolean filtroEstado = estadoRecuCombo.getValue() != null;

            if (!filtroTipo && !filtroCapacidad && !filtroEstado) {
                Alert alerta = new Alert(Alert.AlertType.WARNING, "Debe ingresar al menos un filtro para generar el reporte.", ButtonType.OK);
                alerta.showAndWait();
                return;
            }
            String tipo = tipoRecuCombo.getValue();
            if(tipoRecuCombo.getValue().equals("BARBECUE")){
                tipo = "BARBACOA";
            } else if (tipoRecuCombo.getValue().equals("BUNGALOW")) {
                tipo = "BUNGALOW";
            } else if (tipoRecuCombo.getValue().equals("PARCEL")) {
                tipo = "PARCELA";
            }

            String estado = estadoRecuCombo.getValue();
            if (estadoRecuCombo.getValue().equals("Available")){
                estado = "DISPONIBLE";
            }
            else if (estadoRecuCombo.getValue().equals("Occupied")) {
                estado = "OCUPADO";
            }
            else if (estadoRecuCombo.getValue().equals("Maintenance")) {
                estado = "MANTENIMIENTO";
            }


            Map<String, Object> parametros = new HashMap<>();
            parametros.put("tipo", tipo);
            parametros.put("capacidad", filtroCapacidad ? Integer.parseInt(capacidadRecuText.getText()) : null);
            parametros.put("estado", estado);

            Connection conexion = obtenerConexion();
            if (conexion == null) {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "No se pudo conectar a la base de datos.", ButtonType.OK);
                alerta.showAndWait();
                return;
            }

            String rutaJasper = Paths.get(getClass().getResource("/Camping/Recurso.jasper").toURI()).toString();

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    rutaJasper,
                    parametros,
                    conexion
            );

            conexion.close();

            if (jasperPrint.getPages().isEmpty()) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "No se encontraron recursos que coincidan con los filtros.", ButtonType.OK);
                alerta.showAndWait();
                return;
            }

            JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/resources/Pdf/reportRecurso.pdf");
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR, "Error al generar el reporte: " + e.getMessage(), ButtonType.OK);
            Stage stage = (Stage) capacidadRecuText.getScene().getWindow();
            alerta.initOwner(stage);
            alerta.initModality(Modality.WINDOW_MODAL);
            alerta.showAndWait();
        }
    }
    @FXML
    private void generarReporteAcompanante() {
        try {
            boolean filtroNombre = nombreAcomField.getText() != null && !nombreAcomField.getText().trim().isEmpty();
            boolean filtroApellido = apellidoAcomField.getText() != null && !apellidoAcomField.getText().trim().isEmpty();
            boolean filtroReserva = idReservaAcomField.getText() != null && !idReservaAcomField.getText().trim().isEmpty();

            // Eliminar validación que bloquea el reporte si no hay filtros
            // Para que devuelva todos cuando todos los parámetros sean nulos

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("nombre", filtroNombre ? nombreAcomField.getText().trim() + "%" : null);
            parametros.put("apellido", filtroApellido ? apellidoAcomField.getText().trim() + "%" : null);
            parametros.put("idReserva", filtroReserva ? Integer.parseInt(idReservaAcomField.getText().trim()) : null);

            Connection conexion = obtenerConexion();
            if (conexion == null) {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "No se pudo conectar a la base de datos.", ButtonType.OK);
                Stage stage = (Stage) capacidadRecuText.getScene().getWindow();
                alerta.initOwner(stage);
                alerta.initModality(Modality.WINDOW_MODAL);
                alerta.showAndWait();
                return;
            }

            String rutaJasper = Paths.get(getClass().getResource("/Camping/Flower.jasper").toURI()).toString();

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    rutaJasper,
                    parametros,
                    conexion
            );

            conexion.close();

            if (jasperPrint.getPages().isEmpty()) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "No se encontraron acompañantes que coincidan con los filtros.", ButtonType.OK);
                Stage stage = (Stage) capacidadRecuText.getScene().getWindow();
                alerta.initOwner(stage);
                alerta.initModality(Modality.WINDOW_MODAL);
                alerta.showAndWait();
                return;
            }

            JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/resources/Pdf/reportAcompanante.pdf");
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR, "Error al generar el reporte: " + e.getMessage(), ButtonType.OK);
            Stage stage = (Stage) capacidadRecuText.getScene().getWindow();
            alerta.initOwner(stage);
            alerta.initModality(Modality.WINDOW_MODAL);
            alerta.showAndWait();
        }
    }

    @Override
    public void idiomaCambiado() {
        actualizarTextos();
    }

    private void actualizarTextos() {
        labelCliente.setText(GestorIdiomas.getTexto("cliente"));
        labelNombre.setText(GestorIdiomas.getTexto("nombre"));
        labelApellido.setText(GestorIdiomas.getTexto("apellido"));
        labelFNacimiento.setText(GestorIdiomas.getTexto("fechaNacimiento"));
        labelEstado.setText(GestorIdiomas.getTexto("estado"));
        labelReservas.setText(GestorIdiomas.getTexto("reserva"));
        labelAcompanante.setText(GestorIdiomas.getTexto("acompanante"));
        labelRecurso.setText(GestorIdiomas.getTexto("recurso"));
        labelDNI.setText(GestorIdiomas.getTexto("dni"));
        labelFInicio.setText(GestorIdiomas.getTexto("fechaInicio"));
        labelFechaFin.setText(GestorIdiomas.getTexto("fechaFin"));
        labelEstadoReserva.setText(GestorIdiomas.getTexto("estado"));
        labelIDReserva.setText(GestorIdiomas.getTexto("IDReserva"));
        labelNombreAcom.setText(GestorIdiomas.getTexto("nombre"));
        labelApellidoAcom.setText(GestorIdiomas.getTexto("apellido"));
        labelTipo.setText(GestorIdiomas.getTexto("tipo"));
        labelCapacidad.setText(GestorIdiomas.getTexto("capacidad"));
        labelEstadoRecu.setText(GestorIdiomas.getTexto("estado"));
        labelGenerar.setText(GestorIdiomas.getTexto("GenerarInformes"));
        btnGenerarCliente.setText(GestorIdiomas.getTexto("GenerarInformes"));
        btnGenerarReserva.setText(GestorIdiomas.getTexto("GenerarInformes"));
        btnGenerarRecurso.setText(GestorIdiomas.getTexto("GenerarInformes"));
        btnGenerarAcom.setText(GestorIdiomas.getTexto("GenerarInformes"));

        mapaClienteTraducido.clear();
        mapaClienteTraducido.put("ACTIVO", GestorIdiomas.getTexto("ACTIVO"));
        mapaClienteTraducido.put("BLOQUEADO", GestorIdiomas.getTexto("BLOQUEADO"));
        mapaClienteTraducido.put("SUSPENDIDO", GestorIdiomas.getTexto("SUSPENDIDO"));
        ObservableList<String> traducciones = FXCollections.observableArrayList(mapaClienteTraducido.values());
        estadoClienteCombo.setItems(traducciones);
        estadoClienteCombo.getSelectionModel().select(0);
        mapaRecursoTraducido.clear();
        mapaRecursoTraducido.put("PARCELA", GestorIdiomas.getTexto("PARCELA"));
        mapaRecursoTraducido.put("BUNGALOW", GestorIdiomas.getTexto("BUNGALOW"));
        mapaRecursoTraducido.put("BARBACOA", GestorIdiomas.getTexto("BARBACOA"));
        ObservableList<String> traduccionesRecurso = FXCollections.observableArrayList(mapaRecursoTraducido.values());
        tipoRecuCombo.setItems(traduccionesRecurso);
        tipoRecuCombo.getSelectionModel().select(0);
        mapaRecursoTraducido.clear();
        mapaRecursoTraducido.put("DISPONIBLE", GestorIdiomas.getTexto("DISPONIBLE"));
        mapaRecursoTraducido.put("OCUPADO", GestorIdiomas.getTexto("OCUPADO"));
        mapaRecursoTraducido.put("MANTENIMIENTO", GestorIdiomas.getTexto("MANTENIMIENTO"));
        traduccionesRecurso = FXCollections.observableArrayList(mapaRecursoTraducido.values());
        estadoRecuCombo.setItems(traduccionesRecurso);
        estadoRecuCombo.getSelectionModel().select(0);

        mapaReservaTraducido = new HashMap<>();
        mapaReservaTraducido.put("PENDIENTE", GestorIdiomas.getTexto("PENDIENTE"));
        mapaReservaTraducido.put("CANCELADA", GestorIdiomas.getTexto("CANCELADA"));
        mapaReservaTraducido.put("CONFIRMADA", GestorIdiomas.getTexto("CONFIRMADA"));
        mapaRecursoTraducido.put("NINGUN ESTADO", GestorIdiomas.getTexto("NINGUNESTADO"));
        ObservableList<String> traduccionesReserva = FXCollections.observableArrayList(mapaReservaTraducido.values());
        estadoReservaCombo.setItems(traduccionesReserva);
        estadoReservaCombo.getSelectionModel().select(0);


    }
}

