package org.example.camping2.modelo.validaciones;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidarRecursoTest {

    @Test
    void testValidarNombre() {
        assertTrue(ValidarRecurso.ValidarNombre("Casa rural"));
        assertTrue(ValidarRecurso.ValidarNombre("A".repeat(50)));
        assertFalse(ValidarRecurso.ValidarNombre("")); // vacío
        assertFalse(ValidarRecurso.ValidarNombre("A".repeat(51))); // más de 50
    }

    @Test
    void testValidarPrecio() {
        assertTrue(ValidarRecurso.ValidarPrecio(10));
        assertFalse(ValidarRecurso.ValidarPrecio(-5)); // negativo
        assertFalse(ValidarRecurso.ValidarPrecio(null)); // nulo
    }

    @Test
    void testValidarCapacidad() {
        assertTrue(ValidarRecurso.ValidarCapacidad(4));
        assertFalse(ValidarRecurso.ValidarCapacidad(-1));
        assertFalse(ValidarRecurso.ValidarCapacidad(null));
    }

    @Test
    void testValidarMinimoPersonas() {
        assertTrue(ValidarRecurso.ValidarMinimoPersonas(2));
        assertFalse(ValidarRecurso.ValidarMinimoPersonas(-3));
        assertFalse(ValidarRecurso.ValidarMinimoPersonas(null));
    }
}
