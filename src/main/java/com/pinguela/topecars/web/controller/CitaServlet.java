package com.pinguela.topecars.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.topecars.dao.DataException;
import com.pinguela.topecars.model.CitaDTO;
import com.pinguela.topecars.model.Results;
import com.pinguela.topecars.service.CitaService;
import com.pinguela.topecars.service.ServiceException;
import com.pinguela.topecars.service.impl.CitaServiceImpl;
import com.pinguela.topecars.web.util.Actions;
import com.pinguela.topecars.web.util.Attributes;
import com.pinguela.topecars.web.util.Parameters;
import com.pinguela.topecars.web.util.RouterUtils;
import com.pinguela.topecars.web.util.Views;

@WebServlet("/private/CitaServlet")
public class CitaServlet extends HttpServlet {

	private static SimpleDateFormat FECHA = new SimpleDateFormat("yyyy/MM/dd");
	private Logger logger = LogManager.getLogger(CitaServlet.class);

	private CitaService citaService = null;

	public CitaServlet() {
		super();
		citaService = new CitaServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter(Parameters.ACTION);
		String targetView = null;
		boolean fordwarOrRedirect = false;

		logger.info("action" + action);

		if (Actions.SEARCH.equalsIgnoreCase(action)) {

			CitaDTO cita = new CitaDTO();

			String matricula = request.getParameter(Parameters.MATRICULA);

			if (matricula == null || matricula.isEmpty()) {
				cita.setMatricula(null);
			} else {
				cita.setMatricula(matricula);
			}

			String fechaDesdeStr = request.getParameter(Parameters.FECHA_DESDE); //quitar fecha para el cliente desde hasta o arreglar con el id del cliente para que solo me salgan sus citas
			String fechaHastaStr = request.getParameter(Parameters.FECHA_HASTA);

			logger.info("Fecha desde: " + fechaDesdeStr);
			logger.info("Fecha hasta: " + fechaHastaStr);

			try {
				if (fechaDesdeStr != null && !fechaDesdeStr.isEmpty()) {
					cita.setFechaDesde(FECHA.parse(fechaDesdeStr));
				}

				if (fechaHastaStr != null && !fechaHastaStr.isEmpty()) {
					cita.setFechaHasta(FECHA.parse(fechaHastaStr));
				}

				Results<CitaDTO> resultados = citaService.findByFecha(FECHA.parse(fechaDesdeStr),
						FECHA.parse(fechaHastaStr), 1, 2);

				logger.info(resultados.getPage());
				request.setAttribute(Attributes.RESULTADOS, resultados);
				targetView = Views.CITA_SEARCH; 

			} catch (ParseException | DataException | ServiceException e) {

				logger.error(e.getMessage(), e);
			}
		}

		logger.info("targetView: " + targetView);

		RouterUtils.route(request, response, fordwarOrRedirect, targetView);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
