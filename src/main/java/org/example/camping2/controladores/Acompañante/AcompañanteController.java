package org.example.camping2.controladores.Acompañante;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.example.camping2.modelo.dto.Acompañante;
import org.example.camping2.modelo.memoria.Memoria;

public class AcompañanteController {

    private Memoria<Acompañante, Integer> memoriaAcompañante;
    private StackPane areaContenido;

    public void setAreaContenido(StackPane areaContenido) {
        this.areaContenido = areaContenido;
    }

    public void setMemoria(Memoria<Acompañante, Integer> memoriaAcompañante) {
        this.memoriaAcompañante = memoriaAcompañante;
    }

    @FXML
    public void BuscarAcompañante(ActionEvent actionEvent) {
    }

    @FXML
    public void CrearAcompañante(ActionEvent actionEvent) {
    }

    @FXML
    public void ModificarAcompañante(ActionEvent actionEvent) {
    }

    @FXML
    public void EliminarAcompañante(ActionEvent actionEvent) {
    }
}
