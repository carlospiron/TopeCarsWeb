package com.pinguela.topecars.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.pinguela.topecars.PinguelaException;
import com.pinguela.topecars.model.ClienteDTO;
import com.pinguela.topecars.service.ClienteService;
import com.pinguela.topecars.service.impl.ClienteServiceImpl;
import com.pinguela.topecars.web.util.Actions;
import com.pinguela.topecars.web.util.CookieManager;
import com.pinguela.topecars.web.util.Parameters;
import com.pinguela.topecars.web.util.RouterUtils;
import com.pinguela.topecars.web.util.SessionManager;
import com.pinguela.topecars.web.util.Views;

/**
 * Servlet implementation class ClienteServlet
 */
@WebServlet("/public/PublicClienteServlet")
public class PublicClienteServlet extends HttpServlet {
private Logger logger = LogManager.getLogger(PublicClienteServlet.class);
	
	private ClienteService clienteService = null;

    public PublicClienteServlet() {
        super();
       clienteService = new ClienteServiceImpl(); 
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action=request.getParameter(Parameters.ACTION);
		String targetView = null;
		boolean forwardOrRedirect = false;
		
		if (Actions.LOGIN.equalsIgnoreCase(action)) {
			
			String email = request.getParameter(Parameters.EMAIL);
			String password = request.getParameter(Parameters.PASSWORD);
			
			try {
				logger.debug("Autenticando usuario "+email);
				
				ClienteDTO cliente = clienteService.autenticar(email, password);
				
				if(cliente != null) {
					logger.info("Usuario "+email+" autenticado con éxito");
					SessionManager.setAttribute(request, "cliente", cliente);
					String rememberMeStr = request.getParameter(Parameters.REMEMBER_USER);
					Boolean rememberMe = rememberMeStr!=null;
					
					if(rememberMe) {
						CookieManager.setCookie(response, request.getContextPath(), "cliente", cliente.getCorreo(), 30*24*60*60);
						
					}else {
						CookieManager.removeCookie(response, request.getContextPath(), "cliente");
					}
					
					targetView = Views.CITA_SEARCH; //deberia llevar al a la parte privada despues de hacer el login
					forwardOrRedirect = false;
					
				} else {
					targetView = Views.REGISTER;
					forwardOrRedirect = true;
					logger.info("Usuario no encontradp redirigiendo a la pagina de registro");
				}
				
			} catch(PinguelaException pe) {
				logger.error(pe.getMessage(), pe);
			}
			
			}else if(Actions.REGISTER.equalsIgnoreCase(action)) {
				
				String nombre = request.getParameter(Parameters.NOMBRE);
				String apellido1 = request.getParameter(Parameters.APELLIDO1);
				String apellido2 = request.getParameter(Parameters.APELLIDO2);
				String dni = request.getParameter(Parameters.DNI);
				String email = request.getParameter(Parameters.EMAIL);
				String telefono = request.getParameter(Parameters.TELEFONO);
				String cp = request.getParameter(Parameters.CP);
				String password = request.getParameter(Parameters.PASSWORD);
				
				try {
					
					logger.info("Registrando nuevo cliente "+email);
					
					ClienteDTO nuevoCliente = new ClienteDTO();
					nuevoCliente.setNombre(nombre);
					nuevoCliente.setApellido1(apellido1);
					nuevoCliente.setApellido2(apellido2);
					nuevoCliente.setDni(dni);
					nuevoCliente.setCorreo(email);
					nuevoCliente.setTelefono(telefono);
					nuevoCliente.setCp(cp);
					nuevoCliente.setPassword(password);
					
					Long id = clienteService.registrar(nuevoCliente);
					
					logger.info("Usuario " + email + " registrado con éxito");
	                SessionManager.setAttribute(request, "cliente", nuevoCliente);
	                targetView = Views.HOME;
	                forwardOrRedirect = true;
					
				}catch(PinguelaException pe) {
					logger.error(pe.getMessage(), pe);	
				}
			}
		
		RouterUtils.route(request, response, forwardOrRedirect, targetView);
		}
		
		


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
