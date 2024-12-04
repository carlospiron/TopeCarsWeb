<%@page import="com.pinguela.topecars.model.EstadoReparacion"%>

<%@page import="com.pinguela.topecars.service.EstadoReparacionService"%>

<%@page import="com.pinguela.topecars.service.impl.EstadoReparacionServiceImpl"%>

<%@page import="java.util.List"%>
<%@page import="com.pinguela.topecars.service.ServiceException"%>
<%@page import="com.pinguela.topecars.dao.DataException"%>

<%@page import="com.pinguela.topecars.web.util.LocaleUtils"%>
<%@page import="com.pinguela.topecars.web.util.SessionManager"%>
<%@page import="com.pinguela.topecars.web.util.Attributes"%>

<%
	EstadoReparacionService estadoReparacionService = new EstadoReparacionServiceImpl();
	
	List<EstadoReparacion> estadosReparacion = null;
	
	String defaultLocale = LocaleUtils.validLocale(SessionManager.getAttribute(request, Attributes.LOCALE).toString()).toString();
	
	try {
		estadosReparacion = estadoReparacionService.findAll(defaultLocale);
		
		request.setAttribute(Attributes.ESTADO_REPARACION, estadosReparacion);
		
	} catch (ServiceException | DataException e) {
		e.printStackTrace();
	}
    %>