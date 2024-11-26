package com.pinguela.topecars.web.util;

import java.util.Arrays;
import java.util.List;

public class Validator {
	
	//password patterns
	private static final int MIN_PASSWORD_LENGTH = 6;
	private static final int MAX_PASSWORD_LENGTH = 12;
	private static final String UPPERCASE_PATTERN = ".*[A-Z].*"; //mayusculas
	private static final String LOWERCASE_PATTERN = ".*[a-z].*"; //minusculas
	private static final String DIGIT_PATTERN = ".*\\d.*"; //numeros
	private static final String SPECIAL_CHAR_PATTERN = ".*[!@#$%^&*()].*"; //caracteres especiales
	
	private static final String EMAIL_PATTERN = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"; //email pattern
	
	private static final List<String> VALID_ROLES = Arrays.asList(Rols.ADMINISTRADOR, Rols.JEFE_DE_TALLER, Rols.TECNICO);
	
	
	public static boolean isValidEmail(String email) {
		
		if(email == null || email.isEmpty()) {
			return false;
		}
		return email.matches(EMAIL_PATTERN);
		
	}
	
	public static boolean isValidRole(String rol) {
		
		if(rol == null || rol.isEmpty()) {
			return false;
		}
		
		return VALID_ROLES.contains(rol);
	}
	

	public static boolean containsUpperCase(String password) {
        return password != null && password.matches(UPPERCASE_PATTERN);
    }

    
    public static boolean containsLowerCase(String password) {
        return password != null && password.matches(LOWERCASE_PATTERN);
    }

    
    public static boolean containsDigit(String password) {
        return password != null && password.matches(DIGIT_PATTERN);
    }
    
    
    public static boolean containsSpecialCharacter(String password) {
        return password != null && password.matches(SPECIAL_CHAR_PATTERN);
    }

    
    public static boolean isValidPasswordLength(String password) {
        return password != null && password.length() >= MIN_PASSWORD_LENGTH && password.length() <= MAX_PASSWORD_LENGTH;
    }

	
	public static boolean isValidPassword(String password) {
		
		return isValidPasswordLength(password) &&
				containsUpperCase(password) &&
				containsLowerCase(password) &&
				containsDigit(password) &&
				containsSpecialCharacter(password);
	}
	

}
