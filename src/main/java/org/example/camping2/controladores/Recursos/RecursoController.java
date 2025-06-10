package org.example.camping2.controladores.Recursos;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.example.camping2.controladores.GestorIdiomas;
import org.example.camping2.controladores.IdiomaListener;
import org.example.camping2.controladores.Liberable;
import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.memoria.Memoria;

import java.io.IOException;
import java.util.logging.Logger;

public class RecursoController implements  IdiomaListener {

    private Logger logger;



    private StackPane areaContenido;
    private Memoria<Recurso, Integer> memoria;


    @FXML
    private void initialize() {
        GestorIdiomas.agregarListener(this);
        actualizarTexto();
    }
    @FXML
    public void BuscarRecursos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/recurso/BuscarRecurso.fxml"));
            Parent nuevoPanel = loader.load();

            BuscarRecursoController buscarRecursoController = loader.getController();
            buscarRecursoController.setLogger(logger);
            buscarRecursoController.setMemoriaRecurso(memoria);
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);
        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }
    @FXML
    public void CrearRecurso(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/recurso/CrearRecurso.fxml"));
            Parent nuevoPanel = loader.load();

            CrearRecursoController crearRecursoController = loader.getController();
            crearRecursoController.setLogger(logger);
            crearRecursoController.setMemoriaRecurso(memoria);
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);
        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }
    @FXML
    public void ModificarRecurso(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/recurso/ModificarRecurso.fxml"));
            Parent nuevoPanel = loader.load();

            ModificarRecursoController modificarRecursoController = loader.getController();
            modificarRecursoController.setLogger(logger);
            modificarRecursoController.setMemoriaRecurso(memoria);
            areaContenido.getChildren().clear();
            areaContenido.getChildren().add(nuevoPanel);
        } catch (IOException e) {
            e.printStackTrace();
            // aquí puedes poner un Alert si quieres
        }
    }
    @FXML
    public void EliminarRecurso(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/camping2/vista/recurso/EliminarRecurso.fxml"));
            Parent nuevoPanel = loader.load();

            EliminarRecursoController eliminarRecursoController = loader.getController();
            eliminarRecursoController.setLogger(logger);
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


    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }

    private void actualizarTexto() {


    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}

