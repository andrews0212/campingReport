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

            BuscarRecursoController buscarRecursoController = loader.getController();
            buscarRecursoController.setMemoriaRecurso(memoria);
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);
        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }
    @FXML
    public void CrearRecurso(ActionEvent actionEvent){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/recurso/CrearRecurso.fxml"));
            Parent nuevoPanel = loader.load();

            CrearRecursoController crearRecursoController = loader.getController();
            crearRecursoController.setMemoriaRecurso(memoria);
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);
        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }
    @FXML
    public void ModificarRecurso(ActionEvent actionEvent){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/recurso/ModificarRecurso.fxml"));
            Parent nuevoPanel = loader.load();

            ModificarRecursoController modificarRecursoController = loader.getController();
            modificarRecursoController.setMemoriaRecurso(memoria);
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);
        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }
    @FXML
    public void EliminarRecurso(ActionEvent actionEvent){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/recurso/EliminarRecurso.fxml"));
            Parent nuevoPanel = loader.load();

            EliminarRecursoController eliminarRecursoController = loader.getController();
            eliminarRecursoController.setMemoriaRecurso(memoria);
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
