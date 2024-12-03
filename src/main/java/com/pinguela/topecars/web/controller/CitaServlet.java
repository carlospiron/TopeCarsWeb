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

import com.pinguela.topecars.dao.DataException;
import com.pinguela.topecars.model.CitaDTO;
import com.pinguela.topecars.model.Results;
import com.pinguela.topecars.service.CitaService;
import com.pinguela.topecars.service.ServiceException;
import com.pinguela.topecars.service.impl.CitaServiceImpl;
import com.pinguela.topecars.web.util.Actions;
import com.pinguela.topecars.web.util.Attributes;
import com.pinguela.topecars.web.util.Errors;
import com.pinguela.topecars.web.util.PaginationUtils;
import com.pinguela.topecars.web.util.Parameters;
import com.pinguela.topecars.web.util.RouterUtils;
import com.pinguela.topecars.web.util.URLUtils;
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
			throws ServletException, IOException {
		
		Errors errors = new Errors();
		request.setAttribute(Attributes.ERRORS, errors);
		
		String action = request.getParameter(Parameters.ACTION);
		String targetView = null;
		boolean forwardOrRedirect = false;

		logger.info("action" + action);

		if (Actions.SEARCH.equalsIgnoreCase(action)) {

			CitaDTO cita = new CitaDTO();

			String matricula = request.getParameter(Parameters.MATRICULA);

			if (matricula == null || matricula.isEmpty()) {
				cita.setMatricula(null);
			} else {
				cita.setMatricula(matricula);
			}

			String fechaDesdeStr = request.getParameter(Parameters.FECHA_DESDE);
			String fechaHastaStr = request.getParameter(Parameters.FECHA_HASTA);

			logger.info("Fecha desde: " + fechaDesdeStr);
			logger.info("Fecha hasta: " + fechaHastaStr);
			
			Date fechaDesde = null;
		    Date fechaHasta = null;

			try {

			    if (fechaDesdeStr != null && !fechaDesdeStr.isEmpty()) {
			        fechaDesde = FECHA.parse(fechaDesdeStr);
			    }

			    if (fechaHastaStr != null && !fechaHastaStr.isEmpty()) {
			        fechaHasta = FECHA.parse(fechaHastaStr);
			    }
				
			}catch(ParseException e) {
				logger.error(e.getMessage(), e);
			}
				
			try {
				int PAGE_SIZE = 3; 
				int BROWSABLE_PAGE_COUNT  = 10;
				
				String newPageStr = request.getParameter(Parameters.PAGE);
				int newPage = Strings.isEmpty(newPageStr)?1:Integer.valueOf(newPageStr);

				Results<CitaDTO> resultados = citaService.findByFecha(fechaDesde, fechaHasta, 1, 2);
						
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
		}

		logger.info("targetView: " + targetView);

		RouterUtils.route(request, response, forwardOrRedirect, targetView);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
