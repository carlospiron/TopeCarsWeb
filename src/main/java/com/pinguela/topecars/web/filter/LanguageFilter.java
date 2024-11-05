package com.pinguela.topecars.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

import com.pinguela.topecars.config.ConfigurationParametersManager;

public class LanguageFilter extends HttpFilter implements Filter {

	private static String[] SUPPORTED_LOCALES = ConfigurationParametersManager.getParameterValue("locale.support")
			.split(",");

	public LanguageFilter() {
		super();
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		// El locale en el que se renderiza se decidirá de acuerdo al siguiente orden:
		// 1) Tiene cookie?
		// Idiomas de usuario

		// 2) HEADER
		String[] languages = httpRequest.getHeader("Accept-Language").split(",");

		// Encaje entre lo que pide el usuario y lo que soporta la web.
		// 3) ELSE: Si no hay cookie, ni header, O BIEN EL TIENE EN LA COOKIE O HEADER
		// NO ESTA ESTA SOPORTADO = > DEFECTO

		// SessionManager.setAttribute(httpRequest, "locale", new Locale(language));
	}

	public void destroy() {
	}
}
