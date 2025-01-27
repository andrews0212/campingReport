package org.example.camping2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.camping2.dto.Cliente;
import org.example.camping2.memoria.Memoria;


public class AñadirClienteController {

        private Memoria<Cliente, Integer> memoria;

    @FXML
    private TextField nombreField;

    @FXML
    private TextField apellidoField;

    @FXML
    private TextField dniField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField telefonoField;

    @FXML
    private DatePicker fechaNacimientoField;

    @FXML
    private TextField estadoField;

    @FXML
    private Button añadirClienteButton;

    @FXML
    private void initialize() {
        // Agrega un placeholder para que se note que el controlador está funcionando
        System.out.println("Controlador inicializado correctamente");
    }

    @FXML
    private void handleAñadirCliente(MouseEvent event) {
            try {
                // Crear el objeto cliente con los datos del formulario
                Cliente cliente = new Cliente(
                        nombreField.getText(),
                        apellidoField.getText(),
                        dniField.getText(),
                        emailField.getText(),
                        telefonoField.getText(),
                        fechaNacimientoField.getValue(),
                        estadoField.getText(),
                        ""
                );

                // Intentar guardar el cliente

                memoria.add(cliente);

                // Mostrar mensaje de éxito
                mostrarAlerta("Éxito", "El cliente ha sido añadido correctamente", Alert.AlertType.INFORMATION);
                limpiarCampos();
            } catch (Exception e) {
                // Mostrar mensaje de error en una alerta gráfica
                mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }



    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        nombreField.clear();
        apellidoField.clear();
        dniField.clear();
        emailField.clear();
        telefonoField.clear();
        fechaNacimientoField.setValue(null);
        estadoField.clear();
    }

        // Método setMemoria
        public void setMemoria(Memoria<Cliente, Integer> memoria) {
            this.memoria = memoria;
        }

        // Resto del código del controlador

}
