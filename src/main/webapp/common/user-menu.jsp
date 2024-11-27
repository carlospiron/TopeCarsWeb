<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.pinguela.topecars.model.*"%>
<%@ page import="com.pinguela.topecars.web.util.*"%>

<c:choose>
	<c:when test="${empleado == null}">
		<a href="/TopeCarsWeb/public/empleado/empleado-login-view.jsp"><fmt:message
				key="autenticarse" bundle="${messages}" /></a>
	</c:when>
	<c:otherwise>
		<a
			href="/TopeCarsWeb/private/PrivateEmpleadoServlet?action=viewProfile&id=${empleado.id}">
			${usuario.nombre} </a>
		<br>
		<br>
		<a href="/TopeCarsWeb/private/PrivateEmpleadoServlet?action=logout">Salir</a>
		<jsp:include page="/common/menu.jsp" />
	</c:otherwise>
</c:choose>

