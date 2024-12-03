package com.pinguela.topecars.web.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	
	//matricula patrones
	private static final String CONSONANTES = "BCDFGHIJKLMNPQRSTUVWXYZ";
    private static final Pattern PATTERN_ES = Pattern.compile("([" + CONSONANTES + "]{1,2})(\\d{4})([" + CONSONANTES + "]{0,2})");
    private static final Pattern PATTERN_EU = Pattern.compile("(\\d{4})([" + CONSONANTES + "]{3})");
    
    
	public static boolean esCorreoValido(String correo) {
	    if (correo == null) {
	        return false;
	    }
	    String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
	    return correo.matches(regex);
	}
    
    //metodo para dni 8 numeros y un letra     //nie y cifs empresa
	public static boolean esDniValido(String dni) {
	    if (dni == null || !dni.matches("\\d{8}[A-Z]")) {
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
    
    // metodo que indica si nombre no es nulo y cumpla con el patrón
    public static boolean esEntradaValida(String nombre) {
        
        return nombre != null && nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñüÜ]{2,}$");
    }
    
    

    public static boolean checkMatricula(String matricula){
            matricula = matricula.toUpperCase(); 
            Matcher eu = PATTERN_EU.matcher(matricula);
            if(eu.groupCount()>1){
                 return true;
            }else {
                Matcher es = PATTERN_ES.matcher(matricula);
                if(es.groupCount()>1){
                    return true;
                }else {
                    return false;
                }
           }
    }
    
    
	
}