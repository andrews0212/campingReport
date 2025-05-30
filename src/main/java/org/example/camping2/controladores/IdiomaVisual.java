package org.example.camping2.controladores;

import javafx.scene.image.Image;

public class IdiomaVisual {
    private final String nombre;
    private final Image bandera;

    public IdiomaVisual(String nombre, Image bandera) {
        this.nombre = nombre;
        this.bandera = bandera;
    }

    public String getNombre() {
        return nombre;
    }

    public Image getBandera() {
        return bandera;
    }

    @Override
    public String toString() {
        return nombre;
    }
}