package org.example.camping2.controladores.Recursos;

import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.memoria.Memoria;

public class CrearRecursoController {
    private Memoria<Recurso,Integer> memoria;


    public void setMemoriaRecurso(Memoria<Recurso, Integer> memoria) {
        this.memoria = memoria;
    }
}
