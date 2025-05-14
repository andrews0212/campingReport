package org.example.camping2.controladores.Recursos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.memoria.Memoria;

import java.io.IOException;

public class RecursoController {

    private StackPane areaContenido;
    private Memoria<Recurso, Integer> memoria;


    @FXML
    public void BuscarRecursos(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/recurso/BuscarRecurso.fxml"));
            Parent nuevoPanel = loader.load();

            BuscarRecursoController buscaReservaController = loader.getController();
            buscaReservaController.setMemoriaRecurso(memoria);
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);
        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }



    public void setMemoria(Memoria<Recurso, Integer> memoria) {
        this.memoria = memoria;
    }

    public void setAreaContenido(StackPane areaContenido) {
        this.areaContenido = areaContenido;
    }
}
