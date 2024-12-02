package com.pinguela.topecars.web.util;


public class Validator {
	
	public static boolean esTelefonoValido(String telefono) {
	    if (telefono == null) {
	        return false;
	    }
	    String regex = "^[6789]\\d{8}$";
	    return telefono.matches(regex);
	}

	public static boolean esCorreoValido(String correo) {
	    if (correo == null) {
	        return false;
	    }
	    String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
	    return correo.matches(regex);
	}
    
    //metodo para dni 8 numeros y un letra
    public static boolean esDniValido(String dni) {
        if (dni == null || dni.length() != 9) {
            return false;
        }
        String numeros = dni.substring(0, 8);
        char letra = dni.charAt(8);

        try {
            int numero = Integer.parseInt(numeros);
            String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
            return letra == letras.charAt(numero % 23);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    //metodo validar cp España
    public static boolean esCpValido(String cp) {
        if (cp == null) {
            return false;
        }
        String regex = "\\d{5}";
        return cp.matches(regex);
    }
    
    
    
    
    
	
}