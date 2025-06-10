package org.example.camping2.controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ControladorRaiz {

    @FXML
    private AnchorPane raiz;


    @FXML
    public void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/PantallaCargaNew.fxml"));
            Parent pantallaCarga = loader.load();


           ControladorVentanaCarga controlador = loader.getController();
           controlador.setRaiz(raiz);


//            MenuNew controlador = loader.getController();
//            controlador.setRaiz(raiz);

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
//    @FXML
//    public void initialize() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/PantallaCargaNew.fxml"));
//            Parent pantallaCarga = loader.load();
//
//            // Obtener el controlador y pasarle la raíz
//            ControladorVentanaCarga controlador = loader.getController();
//            controlador.setRaiz(raiz);
//
//            // Mostrar pantalla de carga
//            raiz.getChildren().setAll(pantallaCarga);
//            AnchorPane.setTopAnchor(pantallaCarga, 0.0);
//            AnchorPane.setBottomAnchor(pantallaCarga, 0.0);
//            AnchorPane.setLeftAnchor(pantallaCarga, 0.0);
//            AnchorPane.setRightAnchor(pantallaCarga, 0.0);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



    public AnchorPane getAnchorPane() {
        return raiz;
    }
    public void setAnchorPane(AnchorPane anchorPane) {
        this.raiz = anchorPane;
    }
}
