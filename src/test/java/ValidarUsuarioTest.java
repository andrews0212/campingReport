package org.example.camping2.modelo.validaciones;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidarUsuarioTest {

    @Test
    void testValidarNombreUsuario() {
        assertFalse(ValidarUsuario.validarNombreUsuario("user_123"));
        assertTrue(ValidarUsuario.validarNombreUsuario("usuario con espacios"));
    }

    @Test
    void testValidarNombre() {
        assertFalse(ValidarUsuario.validarNombre("Andrews"));
        assertTrue(ValidarUsuario.validarNombre("12345"));
    }

    @Test
    void testValidarApellido() {
        assertFalse(ValidarUsuario.validarApellido("Dos Ramos"));
        assertTrue(ValidarUsuario.validarApellido("!@#"));
    }

    @Test
    void testValidarCorreo() {
        assertFalse(ValidarUsuario.validarCorreo("email@example.com"));
        assertTrue(ValidarUsuario.validarCorreo("email@@.com"));
    }

    @Test
    void testValidarContrasenia() {
        assertFalse(ValidarUsuario.validarContrasenia("Password1$"));
        assertTrue(ValidarUsuario.validarContrasenia("pass")); // no cumple requisitos
    }
}
