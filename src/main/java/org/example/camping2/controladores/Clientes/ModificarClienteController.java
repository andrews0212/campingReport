package org.example.camping2.controladores.Clientes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.memoria.Memoria;
import org.example.camping2.modelo.validaciones.ValidarCliente;

import java.time.LocalDate;

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
    private TextField idClienteTextField, nombreTextField, dniTextField, apellidoTextField, telefonoTextField, emailTextField, estadoTextField;
    @FXML
    private TextField idClienteTextField1, nombreTextField1, dniTextField1, apellidoTextField1, telefonoTextField1, emailTextField1, estadoTextField1;

    @FXML
    private DatePicker fechaNacimientoDate, fechaNacimientoDate1;
    @FXML
    private TextArea comentariosTextArea;

    @FXML
    private Label statusLabel;

    @FXML
    private Label labelId;
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
    private Button buttonBuscar;
    @FXML
    private Button buttonBuscarTodos;
    @FXML
    private Button btnGuardar;
    @FXML
    private Label textTitulo;

    private ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    @FXML
    private TableView<Cliente> clientesTableView;
    @FXML
    private TableColumn<Cliente, Integer> idColumn;
    @FXML
    private TableColumn<Cliente, String> nombreColumn;
    @FXML
    private TableColumn<Cliente, String> apellidoColumn;
    @FXML
    private TableColumn<Cliente, String> dniColumn;
    @FXML
    private TableColumn<Cliente, String> telefonoColumn;
    @FXML
    private TableColumn<Cliente, String> emailColumn;
    @FXML
    private TableColumn<Cliente, String> fechaNacimientoColumn;
    @FXML
    private TableColumn<Cliente, String> estadoColumn;
    @FXML
    private TableColumn<Cliente, String> comentarioColumn;

    private Memoria<Cliente, Integer> memoria;
    /**
     * Busca un cliente por su ID en la base de datos (memoria) y carga sus datos en los campos del formulario.
     *
     * @throws NumberFormatException Si el ID ingresado no es un número válido.
     */

    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        nombreColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        apellidoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getApellido()));
        dniColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDni()));
        telefonoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTelefono()));
        emailColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        fechaNacimientoColumn.setCellValueFactory(cellData -> {
            LocalDate fecha = cellData.getValue().getFechaNacimiento();
            return new javafx.beans.property.SimpleStringProperty(fecha != null ? fecha.toString() : "");
        });
        estadoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEstado()));
        comentarioColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getComentarios()));
        clientesTableView.setItems(clientes);
        GestorIdiomas.agregarListener(this);
        actualizarTexto();
        clientesTableView.getSelectionModel().selectedItemProperty().addListener((obs, antiguo, nuevo) -> {
            if (nuevo != null) {
                rellenarCampos(nuevo);
            }
        });
    }


    public void buscarClientes() {
        clientes.clear();
        statusLabel.setText("");

        try {
            String id = idClienteTextField.getText();
            String nombre = nombreTextField.getText();
            String apellido = apellidoTextField.getText();
            String dni = dniTextField.getText();
            String telefono = telefonoTextField.getText();
            String email = emailTextField.getText();
            LocalDate fechaNacimiento = fechaNacimientoDate.getValue();
            String estado = estadoTextField.getText();

            ObservableList<Cliente> resultados = buscarClientesEnBaseDatos(
                    id, nombre, apellido, dni, telefono, email, fechaNacimiento.toString(), estado
            );

            if (resultados.isEmpty()) {
                statusLabel.setText("No se encontraron clientes.");
            } else {
                clientes.addAll(resultados);
            }
        } catch (Exception e) {
            statusLabel.setText("Ocurrió un error al buscar los clientes.");
        }
    }
    private void rellenarCampos(Cliente cliente) {
        idClienteTextField1.setText(String.valueOf(cliente.getId()));
        nombreTextField1.setText(cliente.getNombre());
        apellidoTextField1.setText(cliente.getApellido());
        dniTextField1.setText(cliente.getDni());
        telefonoTextField1.setText(cliente.getTelefono());
        emailTextField1.setText(cliente.getEmail());
        fechaNacimientoDate1.setValue(cliente.getFechaNacimiento());
        estadoTextField1.setText(cliente.getEstado());

        comentariosTextArea.setText(cliente.getComentarios());
    }

    /**
     * Guarda los cambios realizados en la información del cliente.
     * Primero valida si un cliente ha sido encontrado y luego actualiza los datos en la memoria (base de datos).
     */
    public void guardarCambios() {

            String idCliente = idClienteTextField1.getText();
            if (idCliente.isEmpty()) {
                statusLabel.setText("Por favor, busque un cliente primero.");
                return;
            }

            if (ValidarCliente.ValidarNombre(nombreTextField1.getText())) {
                mostrarAlerta("Error", "El nombre es invalido", Alert.AlertType.ERROR);
            } else if (ValidarCliente.ValidarApellido(apellidoTextField1.getText())) {
                mostrarAlerta("Error", "El apellido es invalido", Alert.AlertType.ERROR);
            } else if (ValidarCliente.ValidarDNIoNIE(dniTextField1.getText())) {
                mostrarAlerta("Error", "El dni es invalido", Alert.AlertType.ERROR);
            } else if (ValidarCliente.ValidarCorreo(emailTextField1.getText())) {
                mostrarAlerta("Error", "El correo es invalido", Alert.AlertType.ERROR);
            } else if (ValidarCliente.ValidarTelefono(telefonoTextField1.getText())) {
                mostrarAlerta("Error", "El telefono es invalido", Alert.AlertType.ERROR);
            } else if (ValidarCliente.ValidarEstado(estadoTextField1.getText())) {
                mostrarAlerta("Error", "El estado es invalido", Alert.AlertType.ERROR);
            } else {
                try {
                // Crear un nuevo cliente con los datos modificados
                Cliente cliente = new Cliente(
                        nombreTextField1.getText(),
                        apellidoTextField1.getText(),
                        dniTextField1.getText(),
                        emailTextField1.getText(),
                        telefonoTextField1.getText(),
                        null, // Suponiendo que no se modifica la fecha de nacimiento aquí
                        estadoTextField1.getText(),
                        comentariosTextArea.getText()
                );

                // Intentar actualizar el cliente en la base de datos (memoria)
                boolean actualizado = actualizarClienteEnBaseDatos(Integer.parseInt(idCliente), cliente);
                    if (actualizado) {
                        // Actualizar el cliente en la lista observable para que la tabla refresque la fila modificada
                        for (int i = 0; i < clientes.size(); i++) {
                            if (clientes.get(i).getId() == Integer.parseInt(idCliente)) {
                                clientes.set(i, cliente); // Reemplazar el cliente viejo con el actualizado
                                break;
                            }
                        }
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
        if (clientes != null && memoria != null) {
            clientes.clear();
            clientes.addAll(memoria.findAll());
        }
    }


    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);

        // Obtener el Stage actual y asignarlo como propietario
        Stage stage = (Stage) nombreTextField.getScene().getWindow(); // cualquier nodo sirve
        alerta.initOwner(stage);
        alerta.initModality(Modality.WINDOW_MODAL);  // Importante: no usar APPLICATION_MODAL

        alerta.show(); // No bloqueante
    }

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }
    public void actualizarTexto(){
        labelNombre.setText(GestorIdiomas.getTexto("nombre"));
        labelApellido.setText(GestorIdiomas.getTexto("apellido"));
        labelDNI.setText(GestorIdiomas.getTexto("dni"));
        labelEmail.setText(GestorIdiomas.getTexto("email"));
        labelEstado.setText(GestorIdiomas.getTexto("estado"));
        labelTelefono.setText(GestorIdiomas.getTexto("telefono"));
        labelId.setText(GestorIdiomas.getTexto("id"));
        buttonBuscar.setText(GestorIdiomas.getTexto("buscar"));
        btnGuardar.setText(GestorIdiomas.getTexto("guardar"));
        textTitulo.setText(GestorIdiomas.getTexto("modificarCliente"));



    }


    public void buscarTodosClientes(){
        // Clear previous results and reset status
        clientes.clear();
        statusLabel.setText("");

        try {
            // Simulate database search logic
            ObservableList<Cliente> resultados = FXCollections.observableArrayList();;
            resultados.addAll(memoria.findAll());
            clientes.addAll(resultados);


        }catch (Exception e){
            statusLabel.setText("Ocurrió un error al buscar los clientes.");
        }
    }

    /**
     * Simulates the search of clients based on the provided ID, name, and DNI.
     * It compares the input values with the data stored in memory and returns a list of matching clients.
     *
     * @param id The ID of the client to search for (optional).
     * @param nombre The name of the client to search for (optional).
     * @param dni The DNI of the client to search for (optional).
     * @return A list of clients that match the search criteria.
     */
    private ObservableList<Cliente> buscarClientesEnBaseDatos(
            String id, String nombre, String apellido, String dni,
            String telefono, String email, String fechaNacimiento, String estado
    ) {
        ObservableList<Cliente> resultados = FXCollections.observableArrayList();

        if (id.isEmpty() && nombre.isEmpty() && apellido.isEmpty() && dni.isEmpty()
                && telefono.isEmpty() && email.isEmpty()
                && fechaNacimiento.isEmpty() && estado.isEmpty()) {
            System.out.println("No hay datos para buscar.");
            return resultados;
        }

        try {
            for (Cliente cliente : memoria.findAll()) {
                boolean coincide = true;

                if (!id.isEmpty()) {
                    try {
                        int idNum = Integer.parseInt(id);
                        coincide &= cliente.getId() == idNum;
                    } catch (NumberFormatException e) {
                        coincide = false;
                    }
                }

                if (!nombre.isEmpty()) {
                    coincide &= cliente.getNombre().equalsIgnoreCase(nombre);
                }

                if (!apellido.isEmpty()) {
                    coincide &= cliente.getApellido().equalsIgnoreCase(apellido);
                }

                if (!dni.isEmpty()) {
                    coincide &= cliente.getDni().equalsIgnoreCase(dni);
                }

                if (!telefono.isEmpty()) {
                    coincide &= cliente.getTelefono().equalsIgnoreCase(telefono);
                }

                if (!email.isEmpty()) {
                    coincide &= cliente.getEmail().equalsIgnoreCase(email);
                }

                if (!fechaNacimiento.isEmpty()) {
                    coincide &= cliente.getFechaNacimiento().toString().equalsIgnoreCase(fechaNacimiento);
                }

                if (!estado.isEmpty()) {
                    coincide &= cliente.getEstado().equalsIgnoreCase(estado);
                }

                if (coincide) {
                    resultados.add(cliente);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar clientes: " + e.getMessage());
        }

        return resultados;
    }
}
