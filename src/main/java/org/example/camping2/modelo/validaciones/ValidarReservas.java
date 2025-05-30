package org.example.camping2.modelo.validaciones;

import java.time.LocalDate;

public class ValidarReservas {


    public static boolean validarFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        // Devuelve true si la fecha de inicio es igual o anterior a la fecha fin
        return !fechaInicio.isAfter(fechaFin);
    }
}
