package org.example.camping2.modelo.validaciones;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidarReservasTest {

    @Test
    void testFechasValidas() {
        LocalDate inicio = LocalDate.of(2025, 6, 10);
        LocalDate fin = LocalDate.of(2025, 6, 12);
        assertTrue(ValidarReservas.validarFechas(inicio, fin));
    }

    @Test
    void testFechasIguales() {
        LocalDate fecha = LocalDate.of(2025, 6, 12);
        assertTrue(ValidarReservas.validarFechas(fecha, fecha));
    }

    @Test
    void testFechaInicioMayor() {
        LocalDate inicio = LocalDate.of(2025, 6, 15);
        LocalDate fin = LocalDate.of(2025, 6, 12);
        assertFalse(ValidarReservas.validarFechas(inicio, fin));
    }
}
