<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.pinguela.topecars.web.util.*"%>

<%@include file="/common/header.jsp"%>
 <%@include file="/common/menu.jsp"%>

<div class="container">

	<h2>
		ID:
		<c:out value="${cita.idCita}" />
	</h2>
	<p>
		<fmt:message key="motivo" bundle="${messages}" /> <c:out 
				value="${cita.motivo}" />
	</p>
	<p>
		<i><fmt:message key="correo" bundle="${messages}" /> <c:out 
				value="${cita.correo}" /></i>
	</p>
	<p>
		<i><fmt:message key="telefono" bundle="${messages}" /> <c:out 
				value="${cita.telefono}" /></i>
	</p>
	<p>
		<i><fmt:message key="matricula" bundle="${messages}" /> <c:out 
				value="${cita.matricula}" /></i>
	</p>
	<p>
		<i><fmt:message key="numero_bastidor" bundle="${messages}" /> <c:out 
				value="${cita.numeroBastidor}" /></i>
	</p>
	
	<form action="<%=request.getContextPath()%>/private/CitaServlet?action=delete&id=${cita.idCita}" method="post"
		onsubmit="return confirm('¿Estás seguro de que deseas eliminar este usuario?');">
		<input type="hidden" name="idCita" value="${cliente.idCliente}" />
		<button type="submit" class="delete-button"><fmt:message key="borrar_usuario_boton" bundle="${messages}" /> </button>
	</form>
</div>

<%@include file="/common/footer.jsp"%>