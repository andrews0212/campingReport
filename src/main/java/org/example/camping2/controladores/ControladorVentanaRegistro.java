package org.example.camping2.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.example.camping2.dto.Usuario;
import org.example.camping2.memoria.Memoria;

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
        if (CampoContraseña.getText().equals(CampoConfirmarContraseña.getText())){
            Usuario usuario = new Usuario(CampoUsuario.getText(), CampoNombre.getText(), CampoApellido.getText(), CampoCorreo.getText(), "Activo", CampoContraseña.getText());
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
}
