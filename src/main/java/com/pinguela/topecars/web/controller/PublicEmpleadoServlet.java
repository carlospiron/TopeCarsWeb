package com.pinguela.topecars.web.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.topecars.dao.DataException;
import com.pinguela.topecars.model.EmpleadoDTO;
import com.pinguela.topecars.service.EmpleadoService;
import com.pinguela.topecars.service.ServiceException;
import com.pinguela.topecars.service.impl.EmpleadoServiceImpl;
import com.pinguela.topecars.web.util.Actions;
import com.pinguela.topecars.web.util.Attributes;
import com.pinguela.topecars.web.util.CookieManager;
import com.pinguela.topecars.web.util.ErrorCodes;
import com.pinguela.topecars.web.util.Errors;
import com.pinguela.topecars.web.util.LocaleUtils;
import com.pinguela.topecars.web.util.Parameters;
import com.pinguela.topecars.web.util.RouterUtils;
import com.pinguela.topecars.web.util.SessionManager;
import com.pinguela.topecars.web.util.ValueName;
import com.pinguela.topecars.web.util.Views;

@WebServlet("/public/PublicEmpleadoServlet")
public class PublicEmpleadoServlet extends HttpServlet {
	private static Logger logger = LogManager.getLogger(PublicEmpleadoServlet.class);

	private EmpleadoService empleadoService = null;

	public PublicEmpleadoServlet() {
		super();
		empleadoService = new EmpleadoServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Errors errors = new Errors();
		request.setAttribute(Attributes.ERRORS, errors);

		String action = request.getParameter(Parameters.ACTION);
		String targetView = null;
		boolean forwardOrRedirect = false;

		if (Actions.LOGIN.equalsIgnoreCase(action)) {

			try {

				String email = request.getParameter(Parameters.EMAIL);
				String password = request.getParameter(Parameters.PASSWORD);

				if (!errors.hasErrors()) {

					EmpleadoDTO empleado = empleadoService.autenticar(email, password);
					
					if (empleado != null) {
						SessionManager.setAttribute(request, Attributes.EMPLEADO, empleado);
						String rememberMeStr = request.getParameter(Parameters.REMEMBER_USER);
						Boolean rememberMe = rememberMeStr != null;
						if (rememberMe) {
							CookieManager.setCookie(response, request.getContextPath(), Attributes.EMPLEADO,
									empleado.getCorreo(), 30 * 24 * 60 * 60);
							logger.info("Empleado: {}", empleado.getCorreo());
						} else {
							CookieManager.removeCookie(response, request.getContextPath(), Attributes.EMPLEADO);
						}

						targetView = Views.HOME;
						forwardOrRedirect = false;
					} else {
						logger.warn("Authentication failed: user = {} ", email);
						errors.addGlobal(ErrorCodes.AUTHENTICATION_FAILED);
						forwardOrRedirect = true;
						targetView = Views.LOGIN;
					}
				} else {
					forwardOrRedirect = false;
					targetView = Views.LOGIN;
				}
			} catch (DataException | ServiceException e) {
				logger.error(e.getMessage(), e);

			}
			
			
		}else if (Actions.CHANGE_LOCALE.equalsIgnoreCase(action)) {
			Locale localeSeleted = LocaleUtils.validLocale(LocaleUtils.findSupported(request.getParameter(Parameters.LOCALE))) ;
			logger.info("localeSeleted: " + localeSeleted);
			
			SessionManager.setAttribute(request, Attributes.LOCALE, localeSeleted);
			CookieManager.setCookie(response, request.getContextPath(), ValueName.VALUE_COOKIE_LOCALE, localeSeleted.toString(), 30*24*60*60);
			//targetView = String.valueOf(Base64.getDecoder().decode(request.getParameter(ParameterName.CALLBACK_URL).getBytes()));
			//TODO decode
			targetView = request.getParameter(Parameters.CALLBACK_URL);			
			forwardOrRedirect = false;
		}	
			RouterUtils.route(request, response, forwardOrRedirect, targetView);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
