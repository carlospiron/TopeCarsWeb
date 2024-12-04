<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.pinguela.topecars.web.util.*"%>

<%@include file="/common/header.jsp"%>
 <%@include file="/common/menu.jsp"%>
 
 <div class="container">

	<h2>
		ID:
		<c:out value="${reparacion.idReparacion}" />
	</h2>
	<p>
		<fmt:message key="orden_de_trabajo" bundle="${messages}" /> <c:out 
				value="${reparacion.ordenDeTrabajo}" />
	</p>
	<p>
		<fmt:message key="fecha_inicio" bundle="${messages}" /> <c:out 
				value="${reparacion.fechaInicio}" />
	</p>
	<p>
		<i><fmt:message key="fecha_fin" bundle="${messages}" /> <c:out 
				value="${reparacion.fechaFin}" /></i>
	</p>
	<p>
		<i><fmt:message key="descripcion" bundle="${messages}" /> <c:out 
				value="${reparacion.descripcion}" /></i>
	</p>
	<p>
		<i><fmt:message key="matricula" bundle="${messages}" /> <c:out 
				value="${reparacion.matricula}" /></i>
	</p>
	<p>
		<i><fmt:message key="id_estado_reparacion" bundle="${messages}" /> <c:out 
				value="${reparacion.idEstadoReparacion}" /></i>
	</p>
	<p>
		<i><fmt:message key="numero_bastidor" bundle="${messages}" /> <c:out 
				value="${reparacion.numeroBastidor}" /></i>
	</p>
	
</div>
 