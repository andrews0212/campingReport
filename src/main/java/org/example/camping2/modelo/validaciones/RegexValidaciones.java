package org.example.camping2.modelo.validaciones;

import java.util.regex.Pattern;

public interface RegexValidaciones {
    Pattern NOMBRE_USUARIO = Pattern.compile("[a-zA-Z0-9_-]+");
    Pattern NOMBRE = Pattern.compile("[a-zA-Z\\s]+");
    Pattern Apellido = Pattern.compile("[a-zA-Z\\s]+");
    Pattern CORREO = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    Pattern CONTRASEÑA = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])([A-Za-z\\d$@$!%*?&]|[^ ]){8,15}$");
    Pattern DNI = Pattern.compile("^[0-9]{8}[A-Za-z]$");
    Pattern NIE = Pattern.compile("^[XZY]{1}[0-9]{7}[A-Za-z]$");
    Pattern TELEFONO = Pattern.compile("^[0-9]{9}$");
    Pattern ESTADO = Pattern.compile("\\b(ACTIVO|BLOQUEADO|SUSPENDIDO)\\b");
    Pattern ESTADO_RESERVA = Pattern.compile("\\b(CONFIRMADA|PENDIENTE|CANCELADA)\\b");



}
