package org.example.camping2.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.camping2.modelo.dto.Usuario;
import org.example.camping2.modelo.memoria.Memoria;
import org.example.camping2.modelo.validaciones.ValidarUsuario;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorVentanaRegistro {

    private Logger logger;

    private Memoria<Usuario, Integer> memoria;

    @FXML
    private TextField CampoUsuario;
    @FXML
    private TextField CampoNombre;
    @FXML
    private TextField CampoApellido;
    @FXML
    private TextField CampoCorreo;
    @FXML
    private TextField CampoContraseña;
    @FXML
    private TextField CampoConfirmarContraseña;
    @FXML
    private AnchorPane raiz;

    public void setMemoria(Memoria<Usuario, Integer> memoria) {
        this.memoria = memoria;
    }

    public void crearUsuario() {
        logger.info("Intentando crear un nuevo usuario...");

        try {
            if (ValidarUsuario.validarNombreUsuario(CampoUsuario.getText())) {
                logger.warning("Nombre de usuario inválido: " + CampoUsuario.getText());
                alerta("Nombre de usuario incorrecto");
            } else if (ValidarUsuario.validarNombre(CampoNombre.getText())) {
                logger.warning("Nombre inválido: " + CampoNombre.getText());
                alerta("Nombre incorrecto");
            } else if (ValidarUsuario.validarApellido(CampoApellido.getText())) {
                logger.warning("Apellido inválido: " + CampoApellido.getText());
                alerta("Apellido incorrecto");
            } else if (ValidarUsuario.validarCorreo(CampoCorreo.getText())) {
                logger.warning("Correo inválido: " + CampoCorreo.getText());
                alerta("Correo incorrecto");
            } else if (ValidarUsuario.validarContrasenia(CampoContraseña.getText())) {
                logger.warning("Contraseña inválida");
                alerta("La contraseña no es válida");
            } else if (!CampoContraseña.getText().equals(CampoConfirmarContraseña.getText())) {
                logger.warning("Las contraseñas no coinciden");
                alerta("Las contraseñas no coinciden");
            } else {
                String hash = BCrypt.hashpw(CampoContraseña.getText(), BCrypt.gensalt(12));

                Usuario usuario = new Usuario(
                        CampoNombre.getText(),
                        CampoApellido.getText(),
                        CampoCorreo.getText(),
                        CampoUsuario.getText(),
                        "ACTIVO",
                        hash
                );

                if (memoria.add(usuario)) {
                    logger.info("Usuario creado correctamente: " + usuario.getNickname());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Usuario creado correctamente", ButtonType.OK);
                    alert.setTitle("Usuario creado");
                    alert.show();
                } else {
                    logger.severe("No se pudo guardar el usuario en memoria: " + usuario.getNickname());
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error al crear el usuario", ButtonType.OK);
                    Stage stage = (Stage) CampoUsuario.getScene().getWindow();
                    alert.initOwner(stage);
                    alert.initModality(Modality.WINDOW_MODAL);
                    alert.setTitle("Error al crear el usuario");
                    alert.show();
                }
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error inesperado al crear usuario", e);
            alerta("Se produjo un error inesperado.");
        }
    }

    private void alerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR, texto, ButtonType.OK);
        alert.setTitle("Error al crear el usuario");

        if (raiz != null && raiz.getScene() != null) {
            alert.initOwner(raiz.getScene().getWindow());
        }

        alert.showAndWait();
    }

    public void setRaiz(AnchorPane raiz) {
        this.raiz = raiz;
    }

    public void volver(ActionEvent actionEvent) {
        logger.info("Volviendo a la pantalla de presentación...");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/presentacion.fxml"));
            Parent pantallaPrincipal = loader.load();
            raiz.getChildren().setAll(pantallaPrincipal);

            ControladorPresentacion controlador = loader.getController();
            controlador.setMemoria(memoria);
            controlador.setRaiz(raiz);

            AnchorPane.setTopAnchor(pantallaPrincipal, 0.0);
            AnchorPane.setBottomAnchor(pantallaPrincipal, 0.0);
            AnchorPane.setLeftAnchor(pantallaPrincipal, 0.0);
            AnchorPane.setRightAnchor(pantallaPrincipal, 0.0);

            logger.info("Pantalla de presentación cargada correctamente");

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al volver a la pantalla de presentación", e);
            alerta("No se pudo volver a la pantalla principal.");
        }
    }

    public void setLog(Logger logger) {
        this.logger = logger;
    }
}
