package org.example.camping2.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.camping2.modelo.dto.Cliente;
import org.example.camping2.modelo.dto.Usuario;
import org.example.camping2.modelo.memoria.Memoria;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

public class ControladorInicioNew implements IdiomaListener {

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

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }
    private void actualizarTexto() {
    }

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
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/MenuNew.fxml"));
                                Parent pantallaCarga = loader.load();

                                // Obtener el controlador y pasarle la raíz
                                MenuNew controlador = loader.getController();
                                controlador.setRaiz(raiz);

                                // Mostrar pantalla de carga
                                raiz.getChildren().setAll(pantallaCarga);
                                AnchorPane.setTopAnchor(pantallaCarga, 0.0);
                                AnchorPane.setBottomAnchor(pantallaCarga, 0.0);
                                AnchorPane.setLeftAnchor(pantallaCarga, 0.0);
                                AnchorPane.setRightAnchor(pantallaCarga, 0.0);

                            } catch (IOException e) {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Error al abrir la nueva ventana: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }

                }

                memoria.update(usuario);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                Stage stage = (Stage) textFieldContraseña.getScene().getWindow();
                alert.initOwner(stage);
                alert.initModality(Modality.WINDOW_MODAL);
                alert.setContentText("contraseña incorrectos.");
                alert.showAndWait();


            }  else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                Stage stage = (Stage) textFieldContraseña.getScene().getWindow();
                alert.initOwner(stage);
                alert.initModality(Modality.WINDOW_MODAL);
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
            Stage stage = (Stage) textFieldContraseña.getScene().getWindow();
            alert.initOwner(stage);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.setContentText("Se han agotado los intentos");
            alert.showAndWait();
            return false;
        }
        return true;
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
