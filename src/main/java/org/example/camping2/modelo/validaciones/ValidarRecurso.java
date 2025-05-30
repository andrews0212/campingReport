package org.example.camping2.modelo.validaciones;

public class ValidarRecurso {
    public static boolean ValidarNombre(String nombre) {
        return !(nombre.isEmpty() || nombre.length() > 50);
    }


    public static boolean ValidarPrecio(Integer precio) {
        return !(precio == null || precio < 0);
    }

    public static boolean ValidarCapacidad(Integer capacidad) {
        return !(capacidad == null || capacidad < 0);
    }

    public static boolean ValidarMinimoPersonas(Integer minimoPersonas) {
        return !(minimoPersonas == null || minimoPersonas < 0);
    }


}
