package org.example.camping2.controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.camping2.modelo.dto.Usuario;
import org.example.camping2.modelo.memoria.Memoria;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;

public class ControladorPresentacion {

    private Memoria<Usuario, Integer> memoria;
    private AnchorPane raiz; // referencia al AnchorPane principal


    public void iniciar(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/LoginNew.fxml"));
            Parent pantallaCarga = loader.load();

            ControladorInicioNew controladorLogin = loader.getController();
            controladorLogin.setMemoria(memoria);
            controladorLogin.setRaiz(raiz);
            // Mostrar pantalla de carga
            raiz.getChildren().setAll(pantallaCarga);
            AnchorPane.setTopAnchor(pantallaCarga, 0.0);
            AnchorPane.setBottomAnchor(pantallaCarga, 0.0);
            AnchorPane.setLeftAnchor(pantallaCarga, 0.0);
            AnchorPane.setRightAnchor(pantallaCarga, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void registrar(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/Registro.fxml"));
            Parent pantallaCarga = loader.load();

            ControladorVentanaRegistro controladorVentanaRegistro = loader.getController();
            controladorVentanaRegistro.setMemoria(memoria);
            controladorVentanaRegistro.setRaiz(raiz);
            // Mostrar pantalla de carga
            raiz.getChildren().setAll(pantallaCarga);
            AnchorPane.setTopAnchor(pantallaCarga, 0.0);
            AnchorPane.setBottomAnchor(pantallaCarga, 0.0);
            AnchorPane.setLeftAnchor(pantallaCarga, 0.0);
            AnchorPane.setRightAnchor(pantallaCarga, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMemoria(Memoria<Usuario, Integer> memoria) {
        this.memoria = memoria;
    }


    public void setRaiz(AnchorPane raiz) {
        this.raiz=raiz;
    }
}
