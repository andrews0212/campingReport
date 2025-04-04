package org.example.camping2.controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.dto.Usuario;
import org.example.camping2.modelo.memoria.Memoria;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controlador para la ventana de inicio de sesión.
 * Este controlador maneja la lógica de autenticación de usuarios en la aplicación, verificando el nombre de usuario
 * y la contraseña ingresados y, si la autenticación es exitosa, redirigiendo al usuario a la siguiente ventana.
 *
 * <p>Si las credenciales del usuario coinciden con las almacenadas en la memoria, se cargará la siguiente vista (Menu.fxml)
 * y se abrirá la ventana correspondiente. En caso contrario, se mostrará un mensaje de error.</p>
 *
 * @author Andrews Dos Ramos
 * @version 1.0.0
 * @since 31/01/2025
 */
public class ControladorInicio {

    private Memoria<Usuario, Integer> memoria;

    @FXML
    private TextField textFieldUsuario;
    @FXML
    private PasswordField textFieldContraseña;
    @FXML
    private Label labelUsuario;
    @FXML
    private Label labelContraseña;

    @FXML
    private Button botonInciar;
    @FXML
    private Button botonRegistrar;

    @FXML
    private MenuItem menuIngles;

    @FXML
    private MenuItem menuEspañol;

    private ResourceBundle bundle;


    @FXML
    public void initialize() {
        // Configurar eventos de cambio de idioma
        menuIngles.setOnAction(event -> cambiarIdioma(new Locale("en", "US")));
        menuEspañol.setOnAction(event -> cambiarIdioma(new Locale("es", "ES")));

        // Cargar idioma predeterminado (Español)
        cambiarIdioma(new Locale("es", "ES"));
    }
    /**
     * Constructor del controlador. Este constructor está vacío porque JavaFX requiere un constructor sin parámetros
     * para trabajar con el FXML.
     */
    public ControladorInicio() {
        // No hacer nada aquí, JavaFX lo necesita vacío
    }

    /**
     * Método para configurar la memoria donde se almacenan los usuarios.
     *
     * @param memoria Memoria de usuarios que contiene los datos de los usuarios registrados.
     */
    public void setMemoria(Memoria<Usuario, Integer> memoria) {
        this.memoria = memoria;
    }

    /**
     * Método que se ejecuta al presionar el botón de iniciar sesión.
     * Verifica si el nombre de usuario y la contraseña coinciden con los datos almacenados en la memoria.
     * Si las credenciales son correctas, abre la ventana del menú de cliente.
     *
     * @param actionEvent El evento de acción que desencadena el inicio de sesión.
     * @throws IOException Si ocurre un error al cargar la nueva ventana.
     */
    public void iniciar(javafx.event.ActionEvent actionEvent) throws IOException {

        if (memoria != null) {
            Usuario usuario = null;
            for (Usuario usu : memoria.findAll()){
                if (usu.getNickname().equals(textFieldUsuario.getText())){
                    usuario=usu;
                }
            }

            if (usuario != null) {
                if(validarIntentos(usuario)){
                    if (ContraseñaValida(usuario)) {
                        if(validarIntentos(usuario)){
                            try {
                                // Cargar la nueva ventana (Menu.fxml) si el usuario es autenticado
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/Menu.fxml"));
                                Parent root = loader.load();

                                // Obtener el controlador de la nueva ventana y configurar la memoria de clientes
                                ClienteController controladorCliente = loader.getController();
                                controladorCliente.setMemoria(new Memoria<>(Cliente.class));

                                // Crear y mostrar la nueva escena
                                Stage stage = new Stage();
                                stage.setTitle("EcoVenture");
                                URL url = getClass().getResource("/org/example/camping2/logo.png");
                                Image icon = new Image(url.toString());
                                ImageView imageView = new ImageView(icon);
                                imageView.setFitWidth(32); // Establecer el ancho del ícono
                                imageView.setFitHeight(32); // Establecer la altura del ícono
                                stage.getIcons().add(imageView.getImage());
                                stage.setScene(new Scene(root));
                                stage.show();

                                // Cerrar la ventana actual (de inicio de sesión)
                                Stage ventanaActual = (Stage) textFieldContraseña.getScene().getWindow();
                                ventanaActual.close();

                            } catch (IOException e) {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Error al abrir la nueva ventana: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                }

                }

                    memoria.update(usuario);


            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Usuario o contraseña incorrectos.");
                alert.showAndWait();
            }


        }
    }

    private boolean ContraseñaValida(Usuario usuario) {
         if(usuario.getContraseña().equals(textFieldContraseña.getText())){
             usuario.setIntentos(0);
             memoria.update(usuario);
             return true;
         }
         if(usuario.getIntentos() == null){
             usuario.setIntentos(1);
             return false;
         }else{
             usuario.setIntentos(usuario.getIntentos() + 1);
             return false;
         }


    }

    private boolean validarIntentos(Usuario usuario) {
        if (usuario.getIntentos() == null){
            usuario.setIntentos(0);
        }
        if(usuario.getIntentos() >= 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Se han agotado los intentos");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void AbrirRegistro() throws IOException {
        // Cargar el archivo FXML
        URL fxmlLocation = getClass().getResource("/org/example/camping2/VentanaRegistro.fxml");
        if (fxmlLocation == null) {
            System.err.println("No se pudo cargar el archivo FXML. La URL es nula.");
            throw new IOException("No se encontró el archivo FXML.");
        }
        System.out.println("URL del archivo FXML cargada correctamente: " + fxmlLocation);

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();

        // Obtener el controlador de la nueva ventana y configurar la memoria de clientes
        ControladorVentanaRegistro controladorVentanaRegistro = loader.getController();
        controladorVentanaRegistro.setMemoria(memoria);

        // Crear y mostrar la nueva escena
        Stage stage = new Stage();
        stage.setTitle("EcoVenture");

        // Intentar cargar el ícono
        URL iconUrl = getClass().getResource("/org/example/camping2/logo.png");
        if (iconUrl != null) {
            Image icon = new Image(iconUrl.toString());
            ImageView imageView = new ImageView(icon);
            imageView.setFitWidth(32); // Establecer el ancho del ícono
            imageView.setFitHeight(32); // Establecer la altura del ícono
            stage.getIcons().add(imageView.getImage());
        } else {
            System.out.println("No se pudo cargar el ícono.");
        }

        stage.setScene(new Scene(root));
        stage.show();
    }
    private void cambiarIdioma(Locale locale) {
        bundle = ResourceBundle.getBundle("org.example.camping2.idiomas.messages", locale);
        labelUsuario.setText(bundle.getString("usuario"));
        labelContraseña.setText(bundle.getString("contraseña"));
        textFieldUsuario.setPromptText(bundle.getString("usuario"));
        textFieldContraseña.setPromptText(bundle.getString("contraseña"));
        botonInciar.setText(bundle.getString("iniciar"));
        botonRegistrar.setText(bundle.getString("registrar"));
    }

}
