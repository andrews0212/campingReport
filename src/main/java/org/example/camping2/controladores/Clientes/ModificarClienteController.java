package org.example.camping2.controladores.Clientes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.memoria.Memoria;
import org.example.camping2.modelo.validaciones.ValidarCliente;

/**
 * Controlador para la ventana de modificación de clientes.
 * Este controlador permite buscar, modificar y guardar los detalles de un cliente existente.
 *
 * <p>Los datos del cliente se pueden buscar por su ID, y una vez cargados en los campos correspondientes,
 * el usuario puede modificar la información y guardar los cambios.</p>
 *
 * @author Andrews Dos Ramos
 * @version 1.0.0
 * @since 31/01/2025
 */
public class ModificarClienteController implements IdiomaListener {

    @FXML
    private TextField idClienteTextField;

    @FXML
    private TextField nombreTextField;

    @FXML
    private TextField apellidoTextField;

    @FXML
    private TextField dniTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField telefonoTextField;

    @FXML
    private TextField estadoTextField;

    @FXML
    private TextArea comentariosTextArea;

    @FXML
    private Label statusLabel;

    @FXML
    private Label labelID;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelApellido;
    @FXML
    private Label labelDNI;
    @FXML
    private Label labelEmail;
    @FXML
    private Label labelTelefono;
    @FXML
    private Label labelEstado;
    @FXML
    private Label labelComentarios;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Label textTitulo;



    private Memoria<Cliente, Integer> memoria;

    /**
     * Busca un cliente por su ID en la base de datos (memoria) y carga sus datos en los campos del formulario.
     *
     * @throws NumberFormatException Si el ID ingresado no es un número válido.
     */

    public void initialize() {
        GestorIdiomas.agregarListener(this);
        actualizarTexto();
    }


    public void buscarCliente() {
        String idCliente = idClienteTextField.getText();

        if (idCliente.isEmpty()) {
            statusLabel.setText("Por favor, ingrese un ID válido.");
            return;
        }

        try {
            // Buscar cliente en la memoria (base de datos simulada)
            Cliente cliente = buscarClienteEnBaseDatos(Integer.parseInt(idCliente));
            if (cliente != null) {
                // Cargar los datos del cliente en los campos correspondientes
                nombreTextField.setText(cliente.getNombre());
                apellidoTextField.setText(cliente.getApellido());
                dniTextField.setText(cliente.getDni());
                emailTextField.setText(cliente.getEmail());
                telefonoTextField.setText(cliente.getTelefono());
                estadoTextField.setText(cliente.getEstado());
                comentariosTextArea.setText(cliente.getComentarios());
                statusLabel.setText("");
            } else {
                statusLabel.setText("No se encontró un cliente con ese ID.");
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("El ID debe ser un número válido.");
        } catch (Exception e) {
            statusLabel.setText("Ocurrió un error al buscar el cliente.");
        }
    }

    /**
     * Guarda los cambios realizados en la información del cliente.
     * Primero valida si un cliente ha sido encontrado y luego actualiza los datos en la memoria (base de datos).
     */
    public void guardarCambios() {

            String idCliente = idClienteTextField.getText();
            if (idCliente.isEmpty()) {
                statusLabel.setText("Por favor, busque un cliente primero.");
                return;
            }

            if (ValidarCliente.ValidarNombre(nombreTextField.getText())) {
                mostrarAlerta("Error", "El nombre es invalido", Alert.AlertType.ERROR);
            } else if (ValidarCliente.ValidarApellido(apellidoTextField.getText())) {
                mostrarAlerta("Error", "El apellido es invalido", Alert.AlertType.ERROR);
            } else if (ValidarCliente.ValidarDNIoNIE(dniTextField.getText())) {
                mostrarAlerta("Error", "El dni es invalido", Alert.AlertType.ERROR);
            } else if (ValidarCliente.ValidarCorreo(emailTextField.getText())) {
                mostrarAlerta("Error", "El correo es invalido", Alert.AlertType.ERROR);
            } else if (ValidarCliente.ValidarTelefono(telefonoTextField.getText())) {
                mostrarAlerta("Error", "El telefono es invalido", Alert.AlertType.ERROR);
            } else if (ValidarCliente.ValidarEstado(estadoTextField.getText())) {
                mostrarAlerta("Error", "El estado es invalido", Alert.AlertType.ERROR);
            } else {
                try {
                // Crear un nuevo cliente con los datos modificados
                Cliente cliente = new Cliente(
                        nombreTextField.getText(),
                        apellidoTextField.getText(),
                        dniTextField.getText(),
                        emailTextField.getText(),
                        telefonoTextField.getText(),
                        null, // Suponiendo que no se modifica la fecha de nacimiento aquí
                        estadoTextField.getText(),
                        comentariosTextArea.getText()
                );

                // Intentar actualizar el cliente en la base de datos (memoria)
                boolean actualizado = actualizarClienteEnBaseDatos(Integer.parseInt(idCliente), cliente);
                if (actualizado) {
                    statusLabel.setStyle("-fx-text-fill: green;");
                    statusLabel.setText("Cliente actualizado exitosamente.");
                } else {
                    statusLabel.setStyle("-fx-text-fill: red;");
                    statusLabel.setText("Error al actualizar el cliente.");
                }
            } catch(Exception e){
                statusLabel.setStyle("-fx-text-fill: red;");
                statusLabel.setText("Ocurrió un error al guardar los cambios.");
            }
        }
    }

    /**
     * Busca un cliente por su ID en la base de datos (memoria).
     *
     * @param id El ID del cliente a buscar.
     * @return El cliente encontrado, o null si no se encuentra.
     */
    private Cliente buscarClienteEnBaseDatos(int id) {
        return memoria.findById(id);
    }

    /**
     * Actualiza los datos de un cliente en la base de datos (memoria).
     *
     * @param id El ID del cliente a actualizar.
     * @param cliente El cliente con los nuevos datos.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    private boolean actualizarClienteEnBaseDatos(int id, Cliente cliente) {
        cliente.setId(id);
        return memoria.update(cliente); // Devuelve true si se actualizó correctamente
    }

    /**
     * Establece la memoria utilizada para almacenar los clientes.
     *
     * @param memoria Memoria de clientes.
     */
    public void setMemoria(Memoria<Cliente, Integer> memoria) {
        this.memoria = memoria;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }
    public void actualizarTexto(){
        labelNombre.setText(GestorIdiomas.getTexto("nombre"));
        labelApellido.setText(GestorIdiomas.getTexto("apellido"));
        labelComentarios.setText(GestorIdiomas.getTexto("comentarios"));
        labelDNI.setText(GestorIdiomas.getTexto("dni"));
        labelEmail.setText(GestorIdiomas.getTexto("email"));
        labelEstado.setText(GestorIdiomas.getTexto("estado"));
        labelTelefono.setText(GestorIdiomas.getTexto("telefono"));
        labelID.setText(GestorIdiomas.getTexto("id"));
        btnBuscar.setText(GestorIdiomas.getTexto("buscar"));
        btnGuardar.setText(GestorIdiomas.getTexto("guardar"));
        idClienteTextField.setPromptText(GestorIdiomas.getTexto("idText"));
        nombreTextField.setPromptText(GestorIdiomas.getTexto("nombreField"));
        apellidoTextField.setPromptText(GestorIdiomas.getTexto("apellidoField"));
        dniTextField.setPromptText(GestorIdiomas.getTexto("dniField"));
        emailTextField.setPromptText(GestorIdiomas.getTexto("emailField"));
        telefonoTextField.setPromptText(GestorIdiomas.getTexto("telefonoField"));
        estadoTextField.setPromptText(GestorIdiomas.getTexto("estadoField"));
        comentariosTextArea.setPromptText(GestorIdiomas.getTexto("comentariosField"));
        textTitulo.setText(GestorIdiomas.getTexto("modificarCliente"));



    }
}
