package org.example.camping2.controladores.Acompanante;

import org.example.camping2.modelo.dto.Acompanante;
import org.example.camping2.modelo.memoria.Memoria;

public class EliminarAcompananteController {

    private Memoria<Acompanante, Integer> memoriaAcompanante;

    public void setMemoriaAcompanante(Memoria<Acompanante, Integer> memoriaAcompanante) {
        this.memoriaAcompanante = memoriaAcompanante;
    }
}
