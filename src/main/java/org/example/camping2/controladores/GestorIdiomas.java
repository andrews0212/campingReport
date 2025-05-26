package org.example.camping2.controladores;

import org.example.camping2.controladores.IdiomaListener;

import java.util.*;

public class GestorIdiomas {

    private static Locale locale = new Locale("es", "ES");
    private static ResourceBundle bundle = ResourceBundle.getBundle("org.example.camping2.idiomas.messages", new Locale("es", "ES"));

    private static final List<IdiomaListener> listeners = new ArrayList<>();

    public static void cambiarIdioma(Locale nuevoLocale) {
        locale = nuevoLocale;
        bundle = ResourceBundle.getBundle("org.example.camping2.idiomas.messages", locale);

        // Notificar a todos los que están escuchando
        for (IdiomaListener listener : listeners) {
            listener.idiomaCambiado();
        }
    }

    public static String getTexto(String clave) {
        return bundle.getString(clave);
    }

    public static Locale getLocale() {
        return locale;
    }

    public static void agregarListener(IdiomaListener listener) {
        listeners.add(listener);
    }

    public static void quitarListener(IdiomaListener listener) {
        listeners.remove(listener);
    }
}
