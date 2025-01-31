package org.example.camping2.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.camping2.dto.Cliente;
import org.example.camping2.memoria.Memoria;

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
public class ModificarClienteController {

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

    private Memoria<Cliente, Integer> memoria;

    /**
     * Busca un cliente por su ID en la base de datos (memoria) y carga sus datos en los campos del formulario.
     *
     * @throws NumberFormatException Si el ID ingresado no es un número válido.
     */
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
        try {
            String idCliente = idClienteTextField.getText();
            if (idCliente.isEmpty()) {
                statusLabel.setText("Por favor, busque un cliente primero.");
                return;
            }

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
        } catch (Exception e) {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("Ocurrió un error al guardar los cambios.");
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
}
