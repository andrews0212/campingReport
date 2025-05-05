package org.example.camping2.controladores.Reservas;

import javafx.fxml.FXML;
import org.example.camping2.modelo.dto.Reserva;
import org.example.camping2.modelo.memoria.Memoria;

public class CrearReservaController {
    Memoria<Reserva, Integer> memoriaReserva;

    public Memoria<Reserva, Integer> getMemoriaReserva() {
        return memoriaReserva;
    }

    public void setMemoriaReserva(Memoria<Reserva, Integer> memoriaReserva) {
        this.memoriaReserva = memoriaReserva;
    }

}
