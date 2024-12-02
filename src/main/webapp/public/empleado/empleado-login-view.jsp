
<%@include file="/common/header.jsp"%>
<%@ page import="com.pinguela.topecars.web.util.*" %>	

	<div class="login-container">

	
	<c:forEach var="error" items="${errors.globalErrors}">
			<li><fmt:message key="${error}" bundle="${messages}" /></li>
	</c:forEach>
	
	<form action="<%=request.getContextPath()%>/public/PublicEmpleadoServlet" method="post">
		<h3><fmt:message key="autenticacion_usuario" bundle="${messages}"/></h3>
		
		<input type="hidden" name="${Parameters.ACTION}" value="${Actions.LOGIN}" /> 
		
		<label><fmt:message key="correo" bundle="${messages}"/></label>		
		<input type="email" name="${Parameters.EMAIL}" placeholder="Inserta correo" required />
		 
		<label><fmt:message key="contrasena" bundle="${messages}"/></label> 
		<input type="password" name="password" placeholder="Introduce contraseña" required />
		 
		<input type="checkbox" name="${Parameters.REMEMBER_USER}" checked>Recordar usuario</input>

		<input type="submit" value="Acceso"> <input type="reset" value="Limpiar">
	</form>

</div>

<%@include file="/common/footer.jsp" %>
