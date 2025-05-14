package org.example.camping2.controladores.Recursos;

import org.example.camping2.modelo.dto.Recurso;
import org.example.camping2.modelo.memoria.Memoria;

public class BuscarRecursoController {

    private Memoria<Recurso, Integer> memoriaRecurso;

    public void setMemoriaRecurso(Memoria<Recurso, Integer> memoriaRecurso) {
        this.memoriaRecurso = memoriaRecurso;
    }
}
