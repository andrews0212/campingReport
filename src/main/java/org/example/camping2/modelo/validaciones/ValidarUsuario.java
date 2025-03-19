package org.example.camping2.modelo.validaciones;

import org.example.camping2.modelo.dto.Usuario;

public class ValidarUsuario {

    public static boolean validarNombreUsuario(String nombreUsuario) {
        return !RegexValidaciones.NOMBRE_USUARIO.matcher(nombreUsuario).matches();
    }
    public static boolean validarNombre(String nombre) {
        return !RegexValidaciones.NOMBRE.matcher(nombre).matches();
    }
    public static boolean validarApellido(String apellido) {
        return !RegexValidaciones.Apellido.matcher(apellido).matches();
    }
    public static boolean validarCorreo(String correo) {
        return !RegexValidaciones.CORREO.matcher(correo).matches();
    }
    public static boolean validarContrasenia(String contrasenia) {
        return !RegexValidaciones.CONTRASEÑA.matcher(contrasenia).matches();
    }
}
