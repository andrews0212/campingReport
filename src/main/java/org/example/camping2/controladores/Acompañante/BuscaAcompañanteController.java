package org.example.camping2.controladores.Acompañante;

import org.example.camping2.modelo.dto.Acompañante;
import org.example.camping2.modelo.memoria.Memoria;

public class BuscaAcompañanteController {

    private Memoria<Acompañante, Integer> memoriaAcompañante;


    public void setMemoriaAcompañante(Memoria<Acompañante, Integer> memoriaAcompañante) {
        this.memoriaAcompañante = memoriaAcompañante;
    }
}
