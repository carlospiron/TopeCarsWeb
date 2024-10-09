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
import com.pinguela.topecars.model.ClienteCriteria;
import com.pinguela.topecars.model.ClienteDTO;
import com.pinguela.topecars.model.Results;
import com.pinguela.topecars.service.ClienteService;
import com.pinguela.topecars.service.impl.ClienteServiceImpl;
import com.pinguela.topecars.web.util.Actions;
import com.pinguela.topecars.web.util.Parameters;
import com.pinguela.topecars.web.util.RouterUtils;
import com.pinguela.topecars.web.util.SessionManager;
import com.pinguela.topecars.web.util.Views;

/**
 * Servlet implementation class UsuarioServlet
 */
@WebServlet("/ClienteServlet")
public class ClienteServlet extends HttpServlet {
private Logger logger = LogManager.getLogger(ClienteServlet.class);
	
	private ClienteService clienteService = null;

    public ClienteServlet() {
        super();
       clienteService = new ClienteServiceImpl(); 
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter(Parameters.ACTION);
		
		String targetView = null;
		boolean forwardOrRedirect = true;
		
		if (Actions.LOGIN.equalsIgnoreCase(action)) {
			
			String email = request.getParameter(Parameters.EMAIL);
			String password = request.getParameter(Parameters.PASSWORD);
			
			try {
				logger.info("login correcto");
				
				ClienteDTO cliente = clienteService.autenticar(email, password);
				
				if(cliente != null) {
					SessionManager.setAttribute(request, "cliente", cliente);
					targetView = Views.HOME;
					forwardOrRedirect = false;
					
				}
			}catch(PinguelaException pe) {
				logger.error(pe.getMessage(), pe);
			}
		}else if (Actions.LOGOUT.equalsIgnoreCase(action)) {
			
			SessionManager.removeAttribute(request, "cliente");
			targetView = Views.HOME;
			forwardOrRedirect = false;
		}
		
		RouterUtils.route(request, response, forwardOrRedirect, targetView);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
