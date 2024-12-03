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
import com.pinguela.topecars.model.CitaDTO;
import com.pinguela.topecars.model.ClienteDTO;
import com.pinguela.topecars.model.Results;
import com.pinguela.topecars.service.CitaService;
import com.pinguela.topecars.service.MailException;
import com.pinguela.topecars.service.ServiceException;
import com.pinguela.topecars.service.impl.CitaServiceImpl;
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

@WebServlet("/private/CitaServlet")
public class CitaServlet extends HttpServlet {

	private static SimpleDateFormat FECHA = new SimpleDateFormat("yyyy-MM-dd");
	private Logger logger = LogManager.getLogger(CitaServlet.class);

	private CitaService citaService = null;

	public CitaServlet() {
		super();
		citaService = new CitaServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		Errors errors = new Errors();
		request.setAttribute(Attributes.ERRORS, errors);
		
		String action = request.getParameter(Parameters.ACTION);
		String targetView = null;
		boolean forwardOrRedirect = false;
		
		Date fecha = null;
		Date fechaDesde = null;
	    Date fechaHasta = null;

		logger.info("action" + action);

		if (Actions.SEARCH.equalsIgnoreCase(action)) {

			CitaDTO cita = new CitaDTO();

			String fechaDesdeStr = request.getParameter(Parameters.FECHA_DESDE);
			String fechaHastaStr = request.getParameter(Parameters.FECHA_HASTA);

			logger.info("Fecha desde: " + fechaDesdeStr);
			logger.info("Fecha hasta: " + fechaHastaStr);
			

			try {

			    if (fechaDesdeStr == null || fechaDesdeStr.isEmpty()) {
			        fechaDesde = null;
			    } else {
			    	fechaDesde = FECHA.parse(fechaDesdeStr);
			    	cita.setFechaDesde(fechaDesde);
			    }

			    if (fechaHastaStr == null || fechaHastaStr.isEmpty()) {
			        fechaHasta = null;
			    } else {
			    	fechaHasta = FECHA.parse(fechaHastaStr);
			    	cita.setFechaHasta(fechaHasta);
			    }
				
			}catch(ParseException e) {
				logger.error(e.getMessage(), e);
			}
				
			try {
				int PAGE_SIZE = 3; 
				int BROWSABLE_PAGE_COUNT  = 10;
				
				String newPageStr = request.getParameter(Parameters.PAGE);
				int newPage = Strings.isEmpty(newPageStr)?1:Integer.valueOf(newPageStr);

				Results<CitaDTO> resultados = citaService.findByFecha(fechaDesde, fechaHasta, (newPage-1)*PAGE_SIZE+1, PAGE_SIZE);	
				logger.info("Encontrados "+resultados.getTotal()+" citas");
				
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
				
				targetView = Views.CITA_SEARCH;
				forwardOrRedirect = true; 

			} catch (DataException | ServiceException e) {
				logger.error(e.getMessage(), e);
			}
			
		} else if (Actions.DETAIL.equalsIgnoreCase(action)) {
			
		try {
			String idStr = request.getParameter(Parameters.ID);
			Long id = Long.valueOf(idStr);

			CitaDTO cita = citaService.findById(id);

			request.setAttribute(Attributes.CITA, cita);

			targetView = Views.CITA_DETAIL;
			forwardOrRedirect = true;
			
		} catch (PinguelaException pe) {
			logger.error(pe.getMessage(), pe);
		}
		
	} else if (Actions.DELETE.equalsIgnoreCase(action)) {

		try {
			String idStr = request.getParameter(Parameters.ID);
			Long id = Long.valueOf(idStr);
			citaService.anular(id);
			targetView = Views.CITA_SEARCH;
			forwardOrRedirect = true;
			
		} catch (PinguelaException pe) {
			logger.error(pe.getMessage(), pe);
		}

		logger.info("targetView: " + targetView);
	}
	else if (Actions.REGISTER.equalsIgnoreCase(action)) {
		
		try {
	
			CitaDTO cita = new CitaDTO();
			String fechaStr = request.getParameter(Parameters.FECHA);
			fecha = FECHA.parse(fechaStr);
			String motivo = request.getParameter(Parameters.MOTIVO);
			String correo = request.getParameter(Parameters.EMAIL);
			String telefono = request.getParameter(Parameters.TELEFONO);
			String matricula = request.getParameter(Parameters.MATRICULA);
			String numeroBastidor = request.getParameter(Parameters.NUMERO_BASTIDOR);
			
			
			if (!Validator.esCorreoValido(correo)) {
				errors.addFieldError(Parameters.EMAIL, ErrorCodes.INVALID_EMAIL);
				forwardOrRedirect = true;
				targetView = Views.CITA_INSERT;
			}
			
			if (!Validator.checkMatricula(matricula)) {
				errors.addFieldError(Parameters.MATRICULA, ErrorCodes.INVALID_TUITION);
				forwardOrRedirect = true;
				targetView = Views.CITA_INSERT;
			}

			if (errors.hasErrors()) {
				forwardOrRedirect = true;
				targetView = Views.CITA_INSERT;
				
			} else {
				
				cita.setFecha(fecha); 
				cita.setMotivo(motivo);
				cita.setCorreo(correo);
				cita.setTelefono(telefono);
				cita.setMatricula(matricula);
				cita.setNumeroBastidor(numeroBastidor);

				
				try {
				 Long id = citaService.registrar(cita);
					
				}catch (MailException m) {
					logger.error(m.getMessage(), m);
		        }
		        
				
				targetView = Views.CITA_SEARCH;
				forwardOrRedirect = true;
			}
		} catch (ParseException | ServiceException | DataException pe) {
			logger.error(pe.getMessage(), pe);
			errors.addGlobal(ErrorCodes.INCORRECT_FIELD);
	        forwardOrRedirect = true;
	        targetView = Views.CITA_INSERT;
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
