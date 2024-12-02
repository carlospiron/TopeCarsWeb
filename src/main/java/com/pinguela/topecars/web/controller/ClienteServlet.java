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
import com.pinguela.topecars.model.ClienteCriteria;
import com.pinguela.topecars.model.ClienteDTO;
import com.pinguela.topecars.model.Results;
import com.pinguela.topecars.service.ClienteService;
import com.pinguela.topecars.service.impl.ClienteServiceImpl;
import com.pinguela.topecars.web.util.Actions;
import com.pinguela.topecars.web.util.Attributes;
import com.pinguela.topecars.web.util.ErrorCodes;
import com.pinguela.topecars.web.util.Errors;
import com.pinguela.topecars.web.util.PaginationUtils;
import com.pinguela.topecars.web.util.Parameters;
import com.pinguela.topecars.web.util.RequestParameterUtils;
import com.pinguela.topecars.web.util.RouterUtils;
import com.pinguela.topecars.web.util.URLUtils;
import com.pinguela.topecars.web.util.Validator;
import com.pinguela.topecars.web.util.Views;


@WebServlet("/private/ClienteServlet")
public class ClienteServlet extends HttpServlet {
	private static Logger logger = LogManager.getLogger(ClienteServlet.class);
	
    private ClienteService clienteService = null; 
	
    public ClienteServlet() {
        super();
        clienteService = new ClienteServiceImpl();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	
		Errors errors = new Errors();
		request.setAttribute(Attributes.ERRORS, errors);
		
		String action = request.getParameter(Parameters.ACTION);
		String targetView = null;
		boolean forwardOrRedirect = false;
		
		if(Actions.SEARCH.equalsIgnoreCase(action)) {
			ClienteCriteria criteria = new ClienteCriteria();
			
			criteria.setIdCliente(RequestParameterUtils.getLongParameter(request, Parameters.ID));
		    criteria.setNombre(RequestParameterUtils.getStringParameter(request, Parameters.NOMBRE));
		    criteria.setApellido1(RequestParameterUtils.getStringParameter(request, Parameters.APELLIDO1));
		    criteria.setApellido2(RequestParameterUtils.getStringParameter(request, Parameters.APELLIDO2));
		    criteria.setDni(RequestParameterUtils.getStringParameter(request, Parameters.DNI));
		    criteria.setCorreo(RequestParameterUtils.getStringParameter(request, Parameters.EMAIL));
			
			try {
				int PAGE_SIZE = 3; 
				int BROWSABLE_PAGE_COUNT  = 10;
				
				String newPageStr = request.getParameter(Parameters.PAGE);
				int newPage = Strings.isEmpty(newPageStr)?1:Integer.valueOf(newPageStr);
				
				Results<ClienteDTO> resultados = clienteService.findByCriteria(criteria, (newPage-1)*PAGE_SIZE+1, PAGE_SIZE );			
				logger.info("Encontrados "+resultados.getTotal()+" clientes");
				
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
				
				targetView = Views.CLIENTE_SEARCH;
				forwardOrRedirect = true;

			} catch (PinguelaException pe) {
				logger.error(pe.getMessage(), pe);
			}
			
		} else if (Actions.DETAIL.equalsIgnoreCase(action)) {

			try {
				String idStr = request.getParameter(Parameters.ID);
				Long id = Long.valueOf(idStr);

				ClienteDTO cliente = clienteService.findById(id);

				request.setAttribute(Attributes.CLIENTE, cliente);

				targetView = Views.CLIENTE_DETAIL;
				forwardOrRedirect = true;
				
			} catch (PinguelaException pe) {
				logger.error(pe.getMessage(), pe);
			}
		
		} else if (Actions.DELETE.equalsIgnoreCase(action)) {

			try {
				String idStr = request.getParameter(Parameters.ID);
				Long id = Long.valueOf(idStr);
				clienteService.borrar(id);
				targetView = Views.CLIENTE_DETAIL;
				forwardOrRedirect = true;
				
			} catch (PinguelaException pe) {
				logger.error(pe.getMessage(), pe);
			}
			
		
		} else if (Actions.REGISTER.equalsIgnoreCase(action)) {
			
			try {
		
				ClienteDTO cliente = new ClienteDTO();
				String nombre = request.getParameter(Parameters.NOMBRE);
				String apellido1 = request.getParameter(Parameters.APELLIDO1);
				String apellido2 = request.getParameter(Parameters.APELLIDO2);
				String dni = request.getParameter(Parameters.DNI);
				String correo = request.getParameter(Parameters.EMAIL);
				String telefono = request.getParameter(Parameters.TELEFONO);
				String cp = request.getParameter(Parameters.CP);

				
				if (!Validator.esEntradaValida(nombre)) {
					errors.addFieldError(Parameters.NOMBRE, ErrorCodes.INVALID_NAME);
					forwardOrRedirect = true;
					targetView = Views.CLIENTE_INSERT;
				}
				
				if (!Validator.esEntradaValida(apellido1)) {
					errors.addFieldError(Parameters.APELLIDO1, ErrorCodes.LAST_NAME);
					forwardOrRedirect = true;
					targetView = Views.CLIENTE_INSERT;
				}
				
				if (!Validator.esEntradaValida(apellido2)) {
					errors.addFieldError(Parameters.APELLIDO2, ErrorCodes.LAST_NAME);
					forwardOrRedirect = true;
					targetView = Views.CLIENTE_INSERT;
				}

				if (!Validator.esDniValido(dni)) {
					errors.addFieldError(Parameters.DNI, ErrorCodes.INVALID_DNI);
					forwardOrRedirect = true;
					targetView = Views.CLIENTE_INSERT; 
				}
				if (!Validator.esCorreoValido(correo)) {
					errors.addFieldError(Parameters.EMAIL, ErrorCodes.INVALID_EMAIL);
					forwardOrRedirect = true;
					targetView = Views.CLIENTE_INSERT; 
				}
				if (!Validator.esCpValido(cp)) {
					errors.addFieldError(Parameters.CP, ErrorCodes.INVALID_CP);
					forwardOrRedirect = true;
					targetView = Views.CLIENTE_INSERT; 
				}
				


				if (errors.hasErrors()) {
					forwardOrRedirect = true;
					targetView = Views.CLIENTE_INSERT;
					
				} else {

					cliente.setNombre(nombre);
					cliente.setApellido1(apellido1);
					cliente.setApellido2(apellido2);
					cliente.setDni(dni);
					cliente.setCorreo(correo);
					cliente.setTelefono(telefono);
					cliente.setCp(cp);


					Long id = clienteService.registrar(cliente);
					
					targetView = Views.CLIENTE_INSERT;
					forwardOrRedirect = true;
				}
			} catch (PinguelaException pe) {
				logger.error(pe.getMessage(), pe);
				errors.addGlobal(ErrorCodes.INCORRECT_FIELD);
		        forwardOrRedirect = true;
		        targetView = Views.CLIENTE_INSERT;
			}
			
		}
		
		RouterUtils.route(request, response, forwardOrRedirect, targetView);
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
