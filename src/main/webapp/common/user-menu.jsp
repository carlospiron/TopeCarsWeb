<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pinguela.topecars.model.*"%>
<%@ page import="com.pinguela.topecars.web.util.*"%>

<% 	
	EmpleadoDTO empleado = (EmpleadoDTO) SessionManager.getAttribute(request, Attributes.EMPLEADO_AUTENTICADO);
	if (empleado==null) {
		%><a href="/TopeCarsWeb/public/empleado/empleado-login-view.jsp">Autenticarse</a><%
	} else {
		%>
			<p><%=empleado.getNombre()%></p>
			<a href="/TopeCarsWeb/ClienteServlet?action=logout">Salir</a>
			<%@include file="/common/menu.jsp"%>
		<%		
	}
%>