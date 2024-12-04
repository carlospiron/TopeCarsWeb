package com.pinguela.topecars.web.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.pinguela.topecars.PinguelaException;
import com.pinguela.topecars.dao.DataException;
import com.pinguela.topecars.model.EstadoReparacion;
import com.pinguela.topecars.model.ReparacionDTO;
import com.pinguela.topecars.model.Results;
import com.pinguela.topecars.service.EstadoReparacionService;
import com.pinguela.topecars.service.ReparacionService;
import com.pinguela.topecars.service.ServiceException;
import com.pinguela.topecars.service.impl.ReparacionServiceImpl;
import com.pinguela.topecars.web.util.Actions;
import com.pinguela.topecars.web.util.Attributes;
import com.pinguela.topecars.web.util.ErrorCodes;
import com.pinguela.topecars.web.util.Errors;
import com.pinguela.topecars.web.util.PaginationUtils;
import com.pinguela.topecars.web.util.Parameters;
import com.pinguela.topecars.web.util.RouterUtils;
import com.pinguela.topecars.web.util.URLUtils;
import com.pinguela.topecars.web.util.Validator;
import com.pinguela.topecars.web.util.Views;


@WebServlet("/private/ReparacionServlet")
public class ReparacionServlet extends HttpServlet {
       
	private static SimpleDateFormat FECHA = new SimpleDateFormat("yyyy-MM-dd");
	private Logger logger = LogManager.getLogger(ReparacionServlet.class);
	
	private ReparacionService reparacionService = null;
   
    public ReparacionServlet() {
        super();
        reparacionService = new ReparacionServiceImpl();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		Errors errors = new Errors();
		request.setAttribute(Attributes.ERRORS, errors);
		
		String action = request.getParameter(Parameters.ACTION);
		String targetView = null;
		boolean forwardOrRedirect = false;
		
		Date fechaInicio = null;
		Date fechaFin = null;
		
		logger.info("action" + action);
		
		if (Actions.SEARCH.equalsIgnoreCase(action)) {
			
			ReparacionDTO reparacion = new ReparacionDTO();
			
			String fechaInicioStr = request.getParameter(Parameters.FECHA_INICIO);
			String fechaFinStr = request.getParameter(Parameters.FECHA_FIN);
			
			logger.info("Fecha desde: " + fechaInicioStr);
			logger.info("Fecha hasta: " + fechaFinStr);
			
			
			try {
				
				if (fechaInicioStr == null || fechaInicioStr.isEmpty()) {
					reparacion.setFechaInicio(null);
				}else {
					fechaInicio = FECHA.parse(fechaInicioStr);
					reparacion.setFechaInicio(fechaInicio);
				}
				
				if (fechaFinStr == null || fechaFinStr.isEmpty()) {
					reparacion.setFechaFin(null);
				}else {
					fechaFin = FECHA.parse(fechaFinStr);
					reparacion.setFechaFin(fechaFin);
				}
				
			} catch(ParseException e) {
				logger.error(e.getMessage(), e);
			}
			
			try {
				int PAGE_SIZE = 3; 
				int BROWSABLE_PAGE_COUNT  = 10;
				
				String newPageStr = request.getParameter(Parameters.PAGE);
				int newPage = Strings.isEmpty(newPageStr)?1:Integer.valueOf(newPageStr);

				Results<ReparacionDTO> resultados = reparacionService.findByFecha(fechaInicio, fechaFin, (newPage-1)*PAGE_SIZE+1, PAGE_SIZE);	
				logger.info("Encontrados "+resultados.getTotal()+" reparaciones");
				
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
				
				targetView = Views.REPARACION_SEARCH;
				forwardOrRedirect = true; 

			} catch (DataException | ServiceException e) {
				logger.error(e.getMessage(), e);
			}
			
		} else if (Actions.DETAIL.equalsIgnoreCase(action)) {
			
		try {
			String idStr = request.getParameter(Parameters.ID);
			Long id = Long.valueOf(idStr);

			ReparacionDTO reparacion = reparacionService.findById(id);

			request.setAttribute(Attributes.REPARACION, reparacion);

			targetView = Views.REPARACION_DETAIL;
			forwardOrRedirect = true;
			
		} catch (PinguelaException pe) {
			logger.error(pe.getMessage(), pe);
		}
			
		logger.info("targetView: " + targetView);	
			
	} else if (Actions.REGISTER.equalsIgnoreCase(action)) {
		
		try {
			
			ReparacionDTO reparacion = new ReparacionDTO();
			String ordenDeTrabajo = request.getParameter(Parameters.ORDEN_DE_TRABAJO);
			
			String fechaInicioStr = request.getParameter(Parameters.FECHA_INICIO);
			fechaInicio = FECHA.parse(fechaInicioStr);
			String fechaFinStr = request.getParameter(Parameters.FECHA_FIN);
			fechaFin = FECHA.parse(fechaFinStr);
			
			String descripcion = request.getParameter(Parameters.DESCRIPCION);
			String matricula = request.getParameter(Parameters.MATRICULA);
			
			String idEstadoReparacionStr = request.getParameter(Parameters.ID_ESTADO_REPARACION);
			Integer idEstadoReparacion = Integer.valueOf(idEstadoReparacionStr);
			
			String numeroBastidor = request.getParameter(Parameters.NUMERO_BASTIDOR);
			
			String idEmpleadoStr = request.getParameter(Parameters.ID_EMPLEADO);
			Long idEmpleado = Long.valueOf(idEmpleadoStr);
			
			
			if (!Validator.checkMatricula(matricula)) {
				errors.addFieldError(Parameters.MATRICULA, ErrorCodes.INVALID_TUITION);
				forwardOrRedirect = true;
				targetView = Views.REPARACION_INSERT;
			
			}
			
			if (errors.hasErrors()) {
				forwardOrRedirect = true;
				targetView = Views.CITA_INSERT;
				
			} else {
				
				reparacion.setOrdenDeTrabajo(ordenDeTrabajo);
				reparacion.setFechaInicio(fechaInicio);
				reparacion.setFechaFin(fechaFin);
				reparacion.setDescripcion(descripcion);
				reparacion.setMatricula(matricula);
				reparacion.setIdEstadoReparacion(idEstadoReparacion);
				reparacion.setNumeroBastidor(numeroBastidor);
				reparacion.setIdEmpleado(idEmpleado);
				
				
				Long id = reparacionService.create(reparacion);
				
				targetView = Views.REPARACION_SEARCH;
				forwardOrRedirect = true;
			
			}
		} catch (ParseException | PinguelaException pe) {
			logger.error(pe.getMessage(), pe);
			errors.addGlobal(ErrorCodes.INCORRECT_FIELD);
	        forwardOrRedirect = true;
	        targetView = Views.REPARACION_INSERT;
		}
		
	}
			
		RouterUtils.route(request, response, forwardOrRedirect, targetView);		
			
}
		

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
