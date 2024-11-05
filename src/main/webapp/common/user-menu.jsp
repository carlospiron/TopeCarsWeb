<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pinguela.topecars.model.*"%>
<%@ page import="com.pinguela.topecars.web.util.*"%>

<% 	
	ClienteDTO cliente = (ClienteDTO) SessionManager.getAttribute(request, Attributes.CLIENTE_AUTENTICADO);
	if (cliente==null) {
		%><a href="/TopeCarsWeb/public/cliente/cliente-login-view.jsp">Autenticarse</a><%
	} else {
		%>
			<p><%=cliente.getNombre()%></p>
			<a href="/TopeCarsWeb/ClienteServlet?action=logout">Salir</a>
			<%@include file="/common/menu.jsp"%>
		<%		
	}
%>