package org.example.camping2.modelo.validaciones;

import java.time.LocalDate;

public class ValidarReservas {
    public static boolean ValidarEstado(String estado) {
        return !RegexValidaciones.ESTADO_RESERVA.matcher(estado).matches();
    }
    public static boolean ValidarFecha(LocalDate fechaInicio, LocalDate FechaFin){
        return fechaInicio.isAfter(FechaFin);
    }

}
