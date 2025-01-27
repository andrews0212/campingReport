package org.example.camping2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.camping2.dto.Cliente;
import org.example.camping2.memoria.Memoria;

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



    public void buscarCliente() {
        String idCliente = idClienteTextField.getText();

        if (idCliente.isEmpty()) {
            statusLabel.setText("Por favor, ingrese un ID válido.");
            return;
        }

        try {
            // Simula la lógica para buscar un cliente por ID (reemplaza con tu lógica real)
            Cliente cliente = buscarClienteEnBaseDatos(Integer.parseInt(idCliente));
            if (cliente != null) {
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

    public void guardarCambios() {
        try {
            String idCliente = idClienteTextField.getText();
            if (idCliente.isEmpty()) {
                statusLabel.setText("Por favor, busque un cliente primero.");
                return;
            }

            // Simula la lógica para guardar los cambios del cliente (reemplaza con tu lógica real)
            Cliente cliente = new Cliente(
                    nombreTextField.getText(),
                    apellidoTextField.getText(),
                    dniTextField.getText(),
                    emailTextField.getText(),
                    telefonoTextField.getText(),
                    null, // Suponiendo que no se modifica fechaNacimiento aquí
                    estadoTextField.getText(),
                    comentariosTextArea.getText()
            );

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

    private Cliente buscarClienteEnBaseDatos(int id) {

        return memoria.findById(id);
    }

    private boolean actualizarClienteEnBaseDatos(int id, Cliente cliente) {
        cliente.setId(id);


        return memoria.update(cliente); // Simulación: devuelve true si se actualizó correctamente.
    }

    public void setMemoria(Memoria<Cliente, Integer> memoria) {
        this.memoria = memoria;
    }
}
