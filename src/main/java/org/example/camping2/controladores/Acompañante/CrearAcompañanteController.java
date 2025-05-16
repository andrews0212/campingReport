package org.example.camping2.controladores.Acompañante;

import org.example.camping2.modelo.dto.Acompañante;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;

public class CrearAcompañanteController {

    private Memoria<Acompañante, Integer> memoriaAcompañante;
    private Memoria<Reserva, Integer> memoriaReserva;

    public void setMemoriaAcompañante(Memoria<Acompañante, Integer> memoriaAcompañante) {
        this.memoriaAcompañante = memoriaAcompañante;
    }

    public void setMemoriaReserva(Memoria<Reserva, Integer> memoriaReserva) {
        this.memoriaReserva = memoriaReserva;
    }
}
