package org.example.camping2.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.example.camping2.modelo.dto.Usuario;
import org.example.camping2.modelo.memoria.Memoria;
import org.example.camping2.modelo.validaciones.RegexValidaciones;
import org.example.camping2.modelo.validaciones.ValidarUsuario;

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

    private void alerta(String texto){
        Alert alert = new Alert(Alert.AlertType.ERROR, texto, ButtonType.OK);
        alert.setTitle("Error al crear el usuario");
        alert.show();
    }
}
