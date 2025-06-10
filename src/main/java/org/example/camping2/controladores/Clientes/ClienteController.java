package org.example.camping2.controladores.Clientes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.memoria.Memoria;

import java.io.IOException;
import java.util.logging.Logger;

public class ClienteController implements IdiomaListener{

    private Logger logger;
    private StackPane areaContenido;
    private Memoria<Cliente, Integer> memoria;


    @FXML
    public void initialize() {
        GestorIdiomas.agregarListener(this);
        actualizarTextos();
    }


    public void setMemoria(Memoria<Cliente, Integer> memoria) {
        this.memoria = memoria;

    }




    /**
     * Loads the "Add Client" panel into the content area.
     * This method is called when the "Add" button is pressed.
     */

    @FXML
    public void AgregarPanelCliente() {
        cargarPanel("/org/example/camping2/vista/clientes/AñadirClientePanel.fxml");
    }

    /**
     * Loads the "Delete Client" panel into the content area.
     * This method is called when the "Delete" button is pressed.
     */
    @FXML
    public void EliminarPanelCliente() {
        cargarPanel("/org/example/camping2/vista/clientes/EliminarClientePane.fxml");
    }

    /**
     * Loads the "Update Client" panel into the content area.
     * This method is called when the "Update" button is pressed.
     */
    @FXML
    public void ModificarPanelCliente() {
        cargarPanel("/org/example/camping2/vista/clientes/ActualizarClientePanel.fxml");
    }

    /**
     * Loads the "Search Client" panel into the content area.
     * This method is called when the "Search" button is pressed.
     */
    @FXML
    public void BuscarPanelCliente() {

        cargarPanel("/org/example/camping2/vista/clientes/BuscarClientePanel.fxml");

    }
    private void cargarPanel(String archivoFXML) {
        try {
            areaContenido.getChildren().clear();
            // Load the FXML file for the specified panel
            FXMLLoader loader = new FXMLLoader(getClass().getResource(archivoFXML));
            Parent nuevoPanel = loader.load();

            // Get the controller of the loaded panel and pass the memory reference
            if (archivoFXML.equals("/org/example/camping2/vista/clientes/AñadirClientePanel.fxml")) {
                AñadirClienteController controlador = loader.getController();
                controlador.setLogger(logger);
                controlador.setMemoria(memoria);
            } else if (archivoFXML.equals("/org/example/camping2/vista/clientes/EliminarClientePane.fxml")) {
                EliminarClienteController controlador = loader.getController();
                controlador.setLogger(logger);
                controlador.setMemoria(memoria);
            } else if (archivoFXML.equals("/org/example/camping2/vista/clientes/ActualizarClientePanel.fxml")) {
                ModificarClienteController controlador = loader.getController();
                controlador.setLogger(logger);
                controlador.setMemoria(memoria);
            } else if (archivoFXML.equals("/org/example/camping2/vista/clientes/BuscarClientePanel.fxml")) {
                BuscarClientePanelController controlador = loader.getController();
                controlador.setLogger(logger);
                controlador.setMemoria(memoria);
            }

            // Add the loaded panel to the content area
            areaContenido.getChildren().add(nuevoPanel);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensaje("Error al cargar el panel: " + e.getMessage());
        }
    }

    public void setAreaContenido(StackPane areaContenido) {
        this.areaContenido = areaContenido;
    }
    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acción");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }



    private void actualizarTextos() {


    }

    @Override
    public void idiomaCambiado() {
        actualizarTextos();
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
