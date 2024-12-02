<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.pinguela.topecars.web.util.*"%>

<%@include file="/common/header.jsp"%>
 <%@include file="/common/menu.jsp"%>

<div class="container">

	<h2>
		Nombre:
		<c:out value="${empleado.nombre}" />
	</h2>
	<p>
		<i>Id: <c:out value="${empleado.idEmpleado}" /></i>
	</p>
	<p>
		<i><fmt:message key="primer_apellido" bundle="${messages}" /> <c:out 
				value="${empleado.apellido1}" /></i>
	</p>
	<p>
		<i><fmt:message key="segundo_apellido" bundle="${messages}" /> <c:out 
				value="${empleado.apellido2}" /></i>
	</p>
	<p>
		<i><fmt:message key="correo" bundle="${messages}" /> <c:out value="${empleado.correo}" /></i>
	</p>
	<p>
		<i><fmt:message key="id_rol" bundle="${messages}" />  <c:out value="${empleado.idRol}" /></i>
	</p>
</div>

<%@include file="/common/footer.jsp"%>