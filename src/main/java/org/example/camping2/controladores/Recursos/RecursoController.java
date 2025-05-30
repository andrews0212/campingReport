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

public class RecursoController implements Liberable, IdiomaListener {

    @FXML
    private ImageView buscarImage;
    @FXML
    private ImageView agregarImage;
    @FXML
    private ImageView modificarImage;
    @FXML
    private ImageView eliminarImage;

    @FXML
    private Button ButtonBuscar;
    @FXML
    private Button ButtonAgregar;
    @FXML
    private Button ButtonModificar;
    @FXML
    private Button ButtonEliminar;


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
    public void liberarRecursos() {
        Platform.runLater(() -> {


            if (agregarImage.getImage() != null) {
                agregarImage.getImage().cancel();
            }
            agregarImage.setImage(null);

            if (eliminarImage.getImage() != null) {
                eliminarImage.getImage().cancel();
            }
            eliminarImage.setImage(null);

            if (modificarImage.getImage() != null) {
                modificarImage.getImage().cancel();
            }
            modificarImage.setImage(null);

            if (buscarImage.getImage() != null) {
                buscarImage.getImage().cancel();
            }
            buscarImage.setImage(null);
        });
    }

    @Override
    public void idiomaCambiado() {
        actualizarTexto();
    }

    private void actualizarTexto() {
    ButtonBuscar.setText(GestorIdiomas.getTexto("buscar"));
    ButtonAgregar.setText(GestorIdiomas.getTexto("agregar"));
    ButtonModificar.setText(GestorIdiomas.getTexto("actualizar"));
    ButtonEliminar.setText(GestorIdiomas.getTexto("eliminar"));

    }
}

