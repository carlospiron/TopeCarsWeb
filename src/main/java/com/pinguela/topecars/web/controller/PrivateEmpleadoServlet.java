package com.pinguela.topecars.web.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.pinguela.topecars.PinguelaException;
import com.pinguela.topecars.model.ClienteDTO;
import com.pinguela.topecars.model.EmpleadoCriteria;
import com.pinguela.topecars.model.EmpleadoDTO;
import com.pinguela.topecars.model.Results;
import com.pinguela.topecars.service.EmpleadoService;
import com.pinguela.topecars.service.ServiceException;
import com.pinguela.topecars.service.impl.EmpleadoServiceImpl;
import com.pinguela.topecars.web.util.Actions;
import com.pinguela.topecars.web.util.Attributes;
import com.pinguela.topecars.web.util.ErrorCodes;
import com.pinguela.topecars.web.util.Errors;
import com.pinguela.topecars.web.util.PaginationUtils;
import com.pinguela.topecars.web.util.Parameters;
import com.pinguela.topecars.web.util.RequestParameterUtils;
import com.pinguela.topecars.web.util.RouterUtils;
import com.pinguela.topecars.web.util.SessionManager;
import com.pinguela.topecars.web.util.URLUtils;
import com.pinguela.topecars.web.util.Validator;
import com.pinguela.topecars.web.util.Views;

@WebServlet("/private/PrivateEmpleadoServlet")
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

			criteria.setNombre(RequestParameterUtils.getStringParameter(request, Parameters.NOMBRE));
			criteria.setIdEmpleado(RequestParameterUtils.getLongParameter(request, Parameters.ID));
			criteria.setApellido1(RequestParameterUtils.getStringParameter(request, Parameters.APELLIDO1));
		    criteria.setApellido2(RequestParameterUtils.getStringParameter(request, Parameters.APELLIDO2));
		    criteria.setIdRol(RequestParameterUtils.getIntegerParameter(request, Parameters.ID_ROL));

			try {
				int PAGE_SIZE = 3; 
				int BROWSABLE_PAGE_COUNT  = 10;
				
				String newPageStr = request.getParameter(Parameters.PAGE);
				int newPage = Strings.isEmpty(newPageStr)?1:Integer.valueOf(newPageStr);
				
				Results<EmpleadoDTO> resultados = empleadoService.findByCriteria(criteria, (newPage-1)*PAGE_SIZE+1, PAGE_SIZE );			
				logger.info("Encontrados "+resultados.getTotal()+" empleados");
				
				request.setAttribute(Attributes.RESULTADOS, resultados);
				
				String baseURL = URLUtils.buildBaseURL(request);
				request.setAttribute(Attributes.BASE_URL, baseURL);

				request.setAttribute(Attributes.CURRENT_PAGE, Integer.valueOf(newPage));
				
				int fromPage = PaginationUtils.calculateFromPage(newPage, BROWSABLE_PAGE_COUNT);
			    request.setAttribute(Attributes.FROM_PAGE, fromPage);
				
			    int lastPage = PaginationUtils.calculateLastPage(resultados.getTotal(), PAGE_SIZE);
			    request.setAttribute(Attributes.LAST_PAGE, lastPage);
				
			    int toPage = PaginationUtils.calculateToPage(newPage, BROWSABLE_PAGE_COUNT, lastPage);
			    request.setAttribute(Attributes.TO_PAGE, toPage);
				
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

		}

		RouterUtils.route(request, response, forwardOrRedirect, targetView);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
