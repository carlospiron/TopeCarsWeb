<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.pinguela.topecars.web.util.*"%>

<%@include file="/common/header.jsp"%>
 <%@include file="/common/menu.jsp"%>

<div class="container">

	<h2>
		Nombre:
		<c:out value="${cliente.nombre}" />
	</h2>
	<p>
		<i>Id: <c:out value="${cliente.idCliente}" /></i>
	</p>
	<p>
		<i><fmt:message key="primer_apellido" bundle="${messages}" /> <c:out 
				value="${cliente.apellido1}" /></i>
	</p>
	<p>
		<i><fmt:message key="segundo_apellido" bundle="${messages}" /> <c:out 
				value="${cliente.apellido2}" /></i>
	</p>
	<p>
		<i><fmt:message key="dni" bundle="${messages}" /> <c:out 
				value="${cliente.dni}" /></i>
	</p>
	<p>
		<i><fmt:message key="correo" bundle="${messages}" /> <c:out 
				value="${cliente.correo}" /></i>
	</p>
	<form action="<%=request.getContextPath()%>/private/ClienteServlet?action=delete&id=${cliente.idCliente}" method="post"
		onsubmit="return confirm('¿Estás seguro de que deseas eliminar este usuario?');">
		<input type="hidden" name="idCliente" value="${cliente.idCliente}" />
		<button type="submit" class="delete-button"><fmt:message key="borrar_usuario_boton" bundle="${messages}" /> </button>
	</form>
</div>

<%@include file="/common/footer.jsp"%>