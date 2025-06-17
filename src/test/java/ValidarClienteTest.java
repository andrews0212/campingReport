package org.example.camping2.modelo.validaciones;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidarClienteTest {

    @Test
    void testValidarNombre() {
        assertFalse(ValidarCliente.ValidarNombre("Andrews"));
        assertTrue(ValidarCliente.ValidarNombre("123")); // inválido
        assertTrue(ValidarCliente.ValidarNombre(""));    // inválido
    }

    @Test
    void testValidarApellido() {
        assertFalse(ValidarCliente.ValidarApellido("Dos Ramos"));
        assertTrue(ValidarCliente.ValidarApellido("456")); // inválido
        assertTrue(ValidarCliente.ValidarApellido(""));    // inválido
    }

    @Test
    void testValidarCorreo() {
        assertFalse(ValidarCliente.ValidarCorreo("correo@ejemplo.com"));
        assertTrue(ValidarCliente.ValidarCorreo("correo@.com")); // inválido
        assertTrue(ValidarCliente.ValidarCorreo("correo"));      // inválido
    }

    @Test
    void testValidarTelefono() {
        assertFalse(ValidarCliente.ValidarTelefono("612345678"));
        assertTrue(ValidarCliente.ValidarTelefono("61234"));     // inválido
        assertTrue(ValidarCliente.ValidarTelefono("abcdefgh"));  // inválido
    }

    @Test
    void testValidarEstado() {
        assertFalse(ValidarCliente.ValidarEstado("ACTIVO"));
        assertFalse(ValidarCliente.ValidarEstado("SUSPENDIDO"));
        assertTrue(ValidarCliente.ValidarEstado("INACTIVO"));    // inválido
        assertTrue(ValidarCliente.ValidarEstado(""));            // inválido
    }

    @Test
    void testValidarDNI() {
        assertFalse(ValidarCliente.ValidarDNIoNIE("12345678Z")); // válido (Z es letra correcta para 12345678)
        assertTrue(ValidarCliente.ValidarDNIoNIE("12345678A"));  // inválido (letra incorrecta para ese número)
        assertTrue(ValidarCliente.ValidarDNIoNIE("1234A678Z"));  // formato inválido
    }

    @Test
    void testValidarNIE() {
        assertFalse(ValidarCliente.ValidarDNIoNIE("X1234567L")); // válido
        assertTrue(ValidarCliente.ValidarDNIoNIE("Y1234567A"));  // inválido
        assertTrue(ValidarCliente.ValidarDNIoNIE("ZABCDEFGH"));  // formato inválido
    }
}
