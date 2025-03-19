package org.example.camping2.modelo.validaciones;

public class ValidarCliente {

    public static boolean ValidarNombre(String nombre){
    return !RegexValidaciones.NOMBRE.matcher(nombre).matches();
    }
    public static boolean ValidarApellido(String apellido){
        return !RegexValidaciones.Apellido.matcher(apellido).matches();
    }
    public static boolean ValidarCorreo(String correo) {
        return !RegexValidaciones.CORREO.matcher(correo).matches();
    }
    public static boolean ValidarTelefono(String telefono) {
        return !RegexValidaciones.TELEFONO.matcher(telefono).matches();
    }
    public static boolean ValidarDNIoNIE(String documento) {
        if (RegexValidaciones.DNI.matcher(documento).matches()) {
            return !ValidarDNI(documento);
        } else if (RegexValidaciones.NIE.matcher(documento).matches()) {
            return !ValidarNIE(documento);
        }
        return true;
    }

    public static boolean ValidarEstado(String estado) {
        return !RegexValidaciones.ESTADO.matcher(estado).matches();
    }

    private static boolean ValidarDNI(String dni) {
        String numeroDNI = dni.substring(0, 8); // Los 8 primeros dígitos
        char letraDNI = dni.charAt(8); // La letra al final

        // Calcular la letra correspondiente
        int num = Integer.parseInt(numeroDNI);
        char letraCalculada = calcularLetraDNI(num);

        return letraDNI == letraCalculada; // Comparar la letra calculada con la letra introducida
    }
    private static char calcularLetraDNI(int dni) {
        String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";
        int resto = dni % 23;
        return LETRAS_DNI.charAt(resto);
    }
    private static boolean ValidarNIE(String nie) {
        char primeraLetra = nie.charAt(0); // La primera letra
        int numeroNIE = Integer.parseInt(nie.substring(1, 8)); // Los 7 números
        char letraNIE = nie.charAt(8); // La última letra

        // Convertir la letra del NIE (X -> 0, Y -> 1, Z -> 2)
        int prefijoNIE = (primeraLetra == 'X') ? 0 : (primeraLetra == 'Y') ? 1 : 2;

        // Calcular la letra correspondiente del NIE
        char letraCalculada = calcularLetraNIE(prefijoNIE, numeroNIE);

        return letraNIE == letraCalculada; // Comparar la letra calculada con la letra introducida
    }
    private static char calcularLetraNIE(int prefijo, int numero) {
        String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";
        int dniCompleto = prefijo * 10000000 + numero;
        int resto = dniCompleto % 23;
        return LETRAS_DNI.charAt(resto);
    }

}
