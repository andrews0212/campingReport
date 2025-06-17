package org.example.camping2.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.camping2.modelo.dto.Usuario;
import org.example.camping2.modelo.memoria.Memoria;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorInicioNew implements IdiomaListener {

    private Logger logger;
    private Memoria<Usuario, Integer> memoria;

    @FXML
    private TextField textFieldUsuario;

    @FXML
    private PasswordField textFieldContraseña;

    private AnchorPane raiz;

    @FXML
    public void initialize() {
        GestorIdiomas.agregarListener(this);
        actualizarTexto();
    }

    public void setMemoria(Memoria<Usuario, Integer> memoria) {
        this.memoria = memoria;
    }

    public void setRaiz(AnchorPane raiz) {
        this.raiz = raiz;
    }

    public void setLog(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }

    private void actualizarTexto() {
        // Aquí iría la lógica multilingüe
    }

    public void iniciar(ActionEvent actionEvent) {
        if (memoria != null) {
            Usuario usuario = null;
            for (Usuario usu : memoria.findAll()) {
                if (usu.getNickname().equals(textFieldUsuario.getText())) {
                    usuario = usu;
                    break;
                }
            }

            if (usuario != null) {
                if (validarIntentos(usuario)) {
                    if (ContraseñaValida(usuario)) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/MenuNew.fxml"));
                            Parent pantallaCarga = loader.load();

                            MenuNew controlador = loader.getController();
                            controlador.setRaiz(raiz);


                            raiz.getChildren().setAll(pantallaCarga);
                            AnchorPane.setTopAnchor(pantallaCarga, 0.0);
                            AnchorPane.setBottomAnchor(pantallaCarga, 0.0);
                            AnchorPane.setLeftAnchor(pantallaCarga, 0.0);
                            AnchorPane.setRightAnchor(pantallaCarga, 0.0);

                        } catch (IOException e) {
                            if (logger != null)
                                logger.log(Level.WARNING, "Error cargando MenuNew.fxml", e);
                            JOptionPane.showMessageDialog(null, "Error al abrir la nueva ventana: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        return; // Evita que muestre alertas de error tras iniciar sesión correctamente
                    } else {
                        if (logger != null)
                            logger.warning("Contraseña incorrecta para el usuario: " + usuario.getNickname());
                    }
                } else {
                    if (logger != null)
                        logger.warning("Usuario bloqueado por demasiados intentos: " + usuario.getNickname());
                }

                memoria.update(usuario);
                mostrarAlerta("Contraseña incorrecta.");
            } else {
                if (logger != null)
                    logger.warning("Intento de inicio con usuario no existente: " + textFieldUsuario.getText());
                mostrarAlerta("Usuario o contraseña incorrectos.");
            }
        }
    }

    private boolean ContraseñaValida(Usuario usuario) {
        String contrasenaIngresada = textFieldContraseña.getText();
        String hashAlmacenado = usuario.getContraseña();

        if (hashAlmacenado == null || hashAlmacenado.isBlank()) {
            if (logger != null)
                logger.warning("Hash de contraseña nulo o vacío para el usuario: " + usuario.getNickname());
            return false;
        }

        boolean coincide = false;
        try {
            coincide = BCrypt.checkpw(contrasenaIngresada, hashAlmacenado);
        } catch (Exception e) {
            if (logger != null)
                logger.log(Level.SEVERE, "Error al comparar contraseñas con BCrypt", e);
            return false;
        }

        if (coincide) {
            usuario.setIntentos(0);
            memoria.update(usuario);
            return true;
        } else {
            usuario.setIntentos(usuario.getIntentos() == null ? 1 : usuario.getIntentos() + 1);
            memoria.update(usuario);
            return false;
        }
    }



    private boolean validarIntentos(Usuario usuario) {
        if (usuario.getIntentos() == null) {
            usuario.setIntentos(0);
        }
        if (usuario.getIntentos() >= 3) {
            mostrarAlerta("Se han agotado los intentos");
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Stage stage = (Stage) textFieldContraseña.getScene().getWindow();
        alert.initOwner(stage);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void volver(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/presentacion.fxml"));
            Parent pantallaPrincipal = loader.load();

            raiz.getChildren().setAll(pantallaPrincipal);

            ControladorPresentacion controlador = loader.getController();
            controlador.setMemoria(memoria);
            controlador.setRaiz(raiz);
            controlador.setLog(logger); // Pasa también el logger de vuelta

            AnchorPane.setTopAnchor(pantallaPrincipal, 0.0);
            AnchorPane.setBottomAnchor(pantallaPrincipal, 0.0);
            AnchorPane.setLeftAnchor(pantallaPrincipal, 0.0);
            AnchorPane.setRightAnchor(pantallaPrincipal, 0.0);

        } catch (IOException e) {
            if (logger != null)
                logger.log(Level.WARNING, "Error al volver a la presentación", e);
            throw new RuntimeException(e);
        }
    }
}
