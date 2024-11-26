package com.pinguela.topecars.web.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

import com.pinguela.topecars.config.ConfigurationParametersManager;
import com.pinguela.topecars.web.util.CookieManager;
import com.pinguela.topecars.web.util.SessionManager;

public class LanguageFilter extends HttpFilter implements Filter {

	private static String[] SUPPORTED_LOCALES = ConfigurationParametersManager.getParameterValue("locale.support").split(",");
	private static String DEFAULT_LOCALE = ConfigurationParametersManager.getParameterValue("locale.default");
			

	public LanguageFilter() {
		super();
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		 String localeFromCookie = CookieManager.getValue(httpRequest, "locale"); //idioma de la cookie
	        Locale locale = null;
	        if (localeFromCookie != null) {
	            locale = Locale.forLanguageTag(localeFromCookie);
	        }
	        
	        if (locale == null) { //idioma de la sesion
	            locale = (Locale) SessionManager.getAttribute(httpRequest, "locale");
	        }

	        if (locale == null) {
	            // Tentar obter o idioma do cabeçalho
	            String[] languages = httpRequest.getHeader("Accept-Language").split(",");
	            if (languages.length > 0) {
	                locale = Locale.forLanguageTag(languages[0]);
	            }
	            
	            boolean supported = false;
	            for (String supportedLocale : SUPPORTED_LOCALES) {
	                if (locale.toString().equals(supportedLocale)) {
	                    supported = true;
	                    break;
	                }
	            }
	            
	            if (!supported) {
	                locale = Locale.forLanguageTag(DEFAULT_LOCALE);
	            }
	            
	            SessionManager.setAttribute(httpRequest, "locale", locale);
	        }
	        
	        chain.doFilter(request, response);
	}

	public void destroy() {
	}
}
