package com.pinguela.topecars.web.util;

import javax.servlet.http.HttpServletRequest;

public class RequestParameterUtils {
	
	public static String getStringParameter(HttpServletRequest request, String parameterName) {
        String value = request.getParameter(parameterName);
        return (value == null || value.isEmpty()) ? null : value;
    }

    public static Long getLongParameter(HttpServletRequest request, String parameterName) {
        String value = request.getParameter(parameterName);
            return (value == null || value.isEmpty()) ? null : Long.valueOf(value);
    }
    
    public static Integer getIntegerParameter(HttpServletRequest request, String parameterName) {
        String value = request.getParameter(parameterName);
            return (value == null || value.isEmpty()) ? null : Integer.valueOf(value);
    }

}
