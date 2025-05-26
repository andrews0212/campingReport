package org.example.camping2.controladores.Clientes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import org.example.camping2.controladores.Idioma;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.memoria.Memoria;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClienteController {

    @FXML
    private Button ButtonBuscar, ButtonAgregar, ButtonModificar, ButtonEliminar;
    private ResourceBundle bundle;
    private StackPane areaContenido;
    private MenuItem menuIngles;
    private MenuItem menuEspanol;
    private MenuItem acompananteMenu;
    private MenuItem recursoMenu;
    private MenuItem reservaMenu;
    private MenuItem clienteMenu;
    private MenuItem mapaIteractivoMenu;
    private javafx.scene.control.Menu gestionMenu, visualizarMenu, idiomaMenu;
    private Memoria<Cliente, Integer> memoria;


    public void setMemoria(Memoria<Cliente, Integer> memoria) {
        this.memoria = memoria;
    }


    private void cambiarIdioma(Locale locale) {
        bundle = ResourceBundle.getBundle("org.example.camping2.idiomas.messages", locale);
        ButtonBuscar.setText(bundle.getString("buscar"));
        ButtonAgregar.setText(bundle.getString("agregar"));
        ButtonModificar.setText(bundle.getString("actualizar"));
        ButtonEliminar.setText(bundle.getString("eliminar"));
        acompananteMenu.setText(bundle.getString("acompanante"));
        recursoMenu.setText(bundle.getString("recurso"));
        reservaMenu.setText(bundle.getString("reserva"));
        clienteMenu.setText(bundle.getString("cliente"));
        gestionMenu .setText(bundle.getString("gestion"));
        visualizarMenu.setText(bundle.getString("visualizar"));
        idiomaMenu.setText(bundle.getString("idioma"));
        mapaIteractivoMenu.setText(bundle.getString("mapa"));
        Idioma.idioma = locale;

    }
    private static void guardarIdioma(Locale locale){

    }


    /**
     * Loads the "Add Client" panel into the content area.
     * This method is called when the "Add" button is pressed.
     */

    @FXML
    private void manejarBotonAñadir() {
        cargarPanel("/org/example/camping2/vista/clientes/AñadirClientePanel.fxml");
    }

    /**
     * Loads the "Delete Client" panel into the content area.
     * This method is called when the "Delete" button is pressed.
     */
    @FXML
    private void manejarBotonEliminar() {
        cargarPanel("/org/example/camping2/vista/clientes/EliminarClientePane.fxml");
    }

    /**
     * Loads the "Update Client" panel into the content area.
     * This method is called when the "Update" button is pressed.
     */
    @FXML
    private void manejarBotonActualizar() {
        cargarPanel("/org/example/camping2/vista/clientes/ActualizarClientePanel.fxml");
    }

    /**
     * Loads the "Search Client" panel into the content area.
     * This method is called when the "Search" button is pressed.
     */
    @FXML
    private void manejarBotonBuscar() {

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
                controlador.setMemoria(memoria);
            } else if (archivoFXML.equals("/org/example/camping2/vista/clientes/EliminarClientePane.fxml")) {
                EliminarClienteController controlador = loader.getController();
                controlador.setMemoria(memoria);
            } else if (archivoFXML.equals("/org/example/camping2/vista/clientes/ActualizarClientePanel.fxml")) {
                ModificarClienteController controlador = loader.getController();
                controlador.setMemoria(memoria);
            } else if (archivoFXML.equals("/org/example/camping2/vista/clientes/BuscarClientePanel.fxml")) {
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

    public Button getButtonBuscar() {
        return ButtonBuscar;
    }

    public Button getButtonAgregar() {
        return ButtonAgregar;
    }

    public Button getButtonModificar() {
        return ButtonModificar;
    }

    public Button getButtonEliminar() {
        return ButtonEliminar;
    }

    public void setButtonBuscar(Button buttonBuscar) {
        ButtonBuscar = buttonBuscar;
    }

    public void setButtonAgregar(Button buttonAgregar) {
        ButtonAgregar = buttonAgregar;
    }

    public void setButtonModificar(Button buttonModificar) {
        ButtonModificar = buttonModificar;
    }

    public void setButtonEliminar(Button buttonEliminar) {
        ButtonEliminar = buttonEliminar;
    }

    public void setMenu(MenuItem menuEspanol, MenuItem menuIngles, MenuItem acompananteMenu, MenuItem recursoMenu, MenuItem reservaMenu, MenuItem clienteMenu, MenuItem mapaIteractivoMenu, Menu gestionMenu, Menu visualizarMenu, Menu idiomaMenu, Menu gestionMenu1, Menu visualizarMenu1, Menu idiomaMenu1, Locale locale) {
        this.menuIngles = menuIngles;
        this.menuEspanol = menuEspanol;
        this.acompananteMenu = acompananteMenu;
        this.recursoMenu = recursoMenu;
        this.reservaMenu = reservaMenu;
        this.clienteMenu = clienteMenu;
        this.mapaIteractivoMenu = mapaIteractivoMenu;
        this.gestionMenu = gestionMenu;
        this.visualizarMenu = visualizarMenu;
        this.idiomaMenu = idiomaMenu;
        menuIngles.setOnAction(event -> cambiarIdioma(new Locale("en", "US")));
        menuEspanol.setOnAction(event -> cambiarIdioma(new Locale("es", "ES")));
        cambiarIdioma(locale);

    }
}
