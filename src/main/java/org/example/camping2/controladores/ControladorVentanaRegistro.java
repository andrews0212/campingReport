package org.example.camping2.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.camping2.modelo.dto.Usuario;
import org.example.camping2.modelo.memoria.Memoria;
import org.example.camping2.modelo.validaciones.RegexValidaciones;
import org.example.camping2.modelo.validaciones.ValidarUsuario;

import java.io.IOException;

public class ControladorVentanaRegistro {

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

    public void crearUsuario(){

        if(ValidarUsuario.validarNombreUsuario(CampoUsuario.getText())){
            alerta("Nombre de usuario incorrecto");
        } else if (ValidarUsuario.validarNombre(CampoNombre.getText())) {
            alerta("Nombre incorrecto");
        } else if (ValidarUsuario.validarApellido(CampoApellido.getText())) {
            alerta("Apellido incorrecto");
        } else if (ValidarUsuario.validarCorreo(CampoCorreo.getText())) {
            alerta("Correo incorrecto");
        } else if (ValidarUsuario.validarContrasenia(CampoContraseña.getText())){
            alerta("La contraseña no es valida");
        } else if (CampoContraseña.getText().equals(CampoConfirmarContraseña.getText())){
            Usuario usuario = new Usuario(CampoNombre.getText(), CampoApellido.getText(), CampoCorreo.getText(), CampoUsuario.getText(), "ACTIVO", CampoContraseña.getText());
            if (memoria.add(usuario)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Usuario creado correctamente", ButtonType.OK);
                alert.setTitle("Usuario creado");
                alert.show();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error al crear el usuario", ButtonType.OK);
                alert.setTitle("Error al crear el usuario");
                alert.show();
            }
        }
    }

    private void alerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR, texto, ButtonType.OK);
        alert.setTitle("Error al crear el usuario");

        // Obtener el Stage desde el AnchorPane raíz
        if (raiz != null && raiz.getScene() != null) {
            alert.initOwner(raiz.getScene().getWindow());
        }

        alert.showAndWait(); // Mejor usar showAndWait para bloquear y mantener el foco
    }


    public void setRaiz(AnchorPane raiz) {
        this.raiz = raiz;

    }

    public void volver(ActionEvent actionEvent) {
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

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
