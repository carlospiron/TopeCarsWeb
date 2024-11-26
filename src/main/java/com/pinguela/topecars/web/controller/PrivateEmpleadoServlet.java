package com.pinguela.topecars.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.pinguela.topecars.PinguelaException;
import com.pinguela.topecars.model.EmpleadoCriteria;
import com.pinguela.topecars.model.EmpleadoDTO;
import com.pinguela.topecars.model.Results;
import com.pinguela.topecars.service.EmpleadoService;
import com.pinguela.topecars.service.impl.EmpleadoServiceImpl;
import com.pinguela.topecars.web.util.Actions;
import com.pinguela.topecars.web.util.Attributes;
import com.pinguela.topecars.web.util.ErrorCodes;
import com.pinguela.topecars.web.util.Errors;
import com.pinguela.topecars.web.util.Parameters;
import com.pinguela.topecars.web.util.RouterUtils;
import com.pinguela.topecars.web.util.SessionManager;
import com.pinguela.topecars.web.util.Validator;
import com.pinguela.topecars.web.util.Views;

@WebServlet("/PrivateEmpleadoServlet")
public class PrivateEmpleadoServlet extends HttpServlet {
	private static Logger logger = LogManager.getLogger(PrivateEmpleadoServlet.class);

	private EmpleadoService empleadoService = null;

	public PrivateEmpleadoServlet() {
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

		if (Actions.SEARCH.equalsIgnoreCase(action)) {
			EmpleadoCriteria criteria = new EmpleadoCriteria();

			String nombre = request.getParameter(Parameters.NOMBRE);
			if (nombre == null || nombre.isEmpty()) {
				criteria.setNombre(null);
			} else {
				criteria.setNombre(nombre);
			}

			String idStr = request.getParameter(Parameters.ID);
			if (idStr == null || idStr.isEmpty()) {
				criteria.setIdEmpleado(null);
			} else {
				Long id = Long.valueOf(idStr);
				criteria.setIdEmpleado(id);
			}

			String apellido1 = request.getParameter(Parameters.APELLIDO1);
			if (apellido1 == null || apellido1.isEmpty()) {
				criteria.setApellido1(apellido1);
			} else {
				criteria.setApellido1(apellido1);
			}

			String apellido2 = request.getParameter(Parameters.APELLIDO2);
			if (apellido2 == null || apellido2.isEmpty()) {
				criteria.setApellido2(apellido2);
			} else {
				criteria.setApellido2(apellido2);
			}

			String idStrRol = request.getParameter(Parameters.ID_ROL);
			if (idStrRol == null || idStrRol.isEmpty()) {
				criteria.setIdRol(null);
			} else {
				Integer idRol = Integer.valueOf(idStrRol);
				criteria.setIdRol(idRol);
			}

			try {

				Results<EmpleadoDTO> resultados = empleadoService.findByCriteria(criteria, 1, 15);
				if (resultados.getPage().size() > 0) { // fuerzo el null para el c:when test en la JSP
					request.setAttribute(Attributes.RESULTADOS, resultados.getPage());
				} // else resultados será null
				targetView = Views.EMPLEADO_SEARCH;
				forwardOrRedirect = true;

			} catch (PinguelaException pe) {
				logger.error(pe.getMessage(), pe);
			}
		} else if (Actions.DETAIL.equalsIgnoreCase(action)) {

			try {
				String idStr = request.getParameter(Parameters.ID);
				Long id = Long.valueOf(idStr);

				EmpleadoDTO empleado = empleadoService.findById(id);

				request.setAttribute(Attributes.EMPLEADO, empleado);

				targetView = Views.EMPLEADO_DETAIL;
				forwardOrRedirect = true;

			} catch (PinguelaException pe) {
				logger.error(pe.getMessage(), pe);
			}
		} else if (Actions.LOGOUT.equalsIgnoreCase(action)) {
			SessionManager.removeAttribute(request, Attributes.EMPLEADO);
			targetView = Views.HOME;
			forwardOrRedirect = false;

		} else if (Actions.REGISTER.equalsIgnoreCase(action)) {
			EmpleadoDTO empleado = new EmpleadoDTO();

			String nombre = request.getParameter(Parameters.NOMBRE);
			String apellido1 = request.getParameter(Parameters.APELLIDO1);
			String apellido2 = request.getParameter(Parameters.APELLIDO2);
			
			String email = request.getParameter(Parameters.EMAIL);
			
			if(!Strings.isBlank(email)) {
				email = email.trim();
			}
			
				if(!Validator.isValidEmail(email)) {
					errors.addFieldError(Parameters.EMAIL, ErrorCodes.INVALID_EMAIL);
				}
				else {
				errors.addFieldError(Parameters.EMAIL, ErrorCodes.MANDATORY_FIELD);
			}	
			

			// Validacion de la password
			String password = request.getParameter(Parameters.PASSWORD);
			if (!Strings.isBlank(password)) {
				password = password.trim();
				
				if (!Validator.isValidPasswordLength(password)) {
					errors.addFieldError(Parameters.PASSWORD, ErrorCodes.INVALID_PASSWORD_LENGTH);
				}
				else if (!Validator.containsUpperCase(password)) {
					errors.addFieldError(Parameters.PASSWORD, ErrorCodes.PASSWORD_NO_UPPERCASE);
				}
				else if (!Validator.containsLowerCase(password)) {
					errors.addFieldError(Parameters.PASSWORD, ErrorCodes.PASSWORD_NO_LOWERCASE);
				}
				else if (!Validator.containsDigit(password)) {
					errors.addFieldError(Parameters.PASSWORD, ErrorCodes.PASSWORD_NO_NUMBER);
				}
				else if (!Validator.containsSpecialCharacter(password)) {
					errors.addFieldError(Parameters.PASSWORD, ErrorCodes.PASSWORD_NO_SPECIAL_CHARACTER);
				}
				
			} else {
				errors.addFieldError(Parameters.PASSWORD, ErrorCodes.MANDATORY_FIELD);
			}

			String rolStr = request.getParameter(Parameters.ID_ROL);
			Integer idRol = Integer.valueOf(rolStr);
			// Validacion de rol
			
			if (!Validator.isValidRole(rolStr)) {
				errors.addFieldError(Parameters.ID_ROL, ErrorCodes.UNKNOWN_ROLE);
			}

			if (!errors.hasErrors()) {

				empleado.setNombre(nombre);
				empleado.setApellido1(apellido1);
				empleado.setApellido2(apellido2);
				empleado.setCorreo(email);
				empleado.setPassword(password);
				empleado.setIdRol(idRol);

				try {
					Long id = empleadoService.registrar(empleado);
				} catch (PinguelaException pe) {
					logger.error(pe.getMessage() + ": nombre = " + nombre + ", email=" + email, pe);
				}
				targetView = Views.EMPLEADO_INSERT; 
				forwardOrRedirect = false;

			} else {
				forwardOrRedirect = true;
				targetView = Views.EMPLEADO_INSERT;
			}

		} else if (Actions.DETAIL.equalsIgnoreCase(action)) {
			try {
				String idStr = request.getParameter(Parameters.ID);
				Long id = Long.valueOf(idStr);

				EmpleadoDTO empleado = empleadoService.findById(id);

				request.setAttribute(Attributes.EMPLEADO, empleado);

				targetView = Views.EMPLEADO_DETAIL;
				forwardOrRedirect = true;
			} catch (PinguelaException pe) {
				logger.error(pe.getMessage(), pe);
			}
		} else if (Actions.DELETE.equalsIgnoreCase(action)) {

			try {
				String idStr = request.getParameter(Parameters.ID);
				Long id = Long.valueOf(idStr);
				empleadoService.anular(id);
				targetView = Views.EMPLEADO_SEARCH;
				forwardOrRedirect = true;

			} catch (PinguelaException pe) {
				logger.error(pe.getMessage(), pe);
			}
		}

		RouterUtils.route(request, response, forwardOrRedirect, targetView);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
