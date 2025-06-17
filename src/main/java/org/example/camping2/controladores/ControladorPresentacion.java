package org.example.camping2.controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import org.example.camping2.modelo.dto.Usuario;
import org.example.camping2.modelo.memoria.Memoria;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorPresentacion {

    private Logger logger;
    private Memoria<Usuario, Integer> memoria;
    private AnchorPane raiz;

    public void iniciar(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/LoginNew.fxml"));
            Parent pantallaCarga = loader.load();

            ControladorInicioNew controladorLogin = loader.getController();
            controladorLogin.setMemoria(memoria);
            controladorLogin.setRaiz(raiz);
            controladorLogin.setLog(logger); // Se pasa el logger

            raiz.getChildren().setAll(pantallaCarga);
            AnchorPane.setTopAnchor(pantallaCarga, 0.0);
            AnchorPane.setBottomAnchor(pantallaCarga, 0.0);
            AnchorPane.setLeftAnchor(pantallaCarga, 0.0);
            AnchorPane.setRightAnchor(pantallaCarga, 0.0);

        } catch (IOException e) {
            logger.log(Level.WARNING, "Error cargando LoginNew.fxml", e);
        }
    }

    @FXML
    public void registrar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/Registro.fxml"));
            Parent pantallaCarga = loader.load();

            ControladorVentanaRegistro controladorVentanaRegistro = loader.getController();
            controladorVentanaRegistro.setMemoria(memoria);
            controladorVentanaRegistro.setRaiz(raiz);
            controladorVentanaRegistro.setLog(logger); // Se pasa el logger

            raiz.getChildren().setAll(pantallaCarga);
            AnchorPane.setTopAnchor(pantallaCarga, 0.0);
            AnchorPane.setBottomAnchor(pantallaCarga, 0.0);
            AnchorPane.setLeftAnchor(pantallaCarga, 0.0);
            AnchorPane.setRightAnchor(pantallaCarga, 0.0);

        } catch (IOException e) {
            logger.log(Level.WARNING, "Error cargando Registro.fxml", e);
        }
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
}
