<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
	
	<div class="login-container">

	
	<c:forEach var="error" items="${errors.globalErrors}">
			<li><fmt:message key="${error}" bundle="${messages}" /></li>
	</c:forEach>
	
	<form action="<%=request.getContextPath()%>/public/PublicEmpleadoServlet" method="post">
		<h3><fmt:message key="autenticacion_usuario" bundle="${messages}"/></h3>
		
		<input type="hidden" name="action" value="login" /> 
		
		<label><fmt:message key="correo" bundle="${messages}"/></label>		
		<input type="email" name="correo" placeholder="Inserta correo" required />
		 
		<label><fmt:message key="contraseña" bundle="${messages}"/></label> 
		<input type="password" name="conraseña" placeholder="Introduce contraseña" required />
		 
		<input type="checkbox" name="remember-user" checked>Recordar usuario</input>

		<input type="submit" value="Acceso"> <input type="reset" value="Limpiar">
	</form>


</div>

<%@include file="/common/footer.jsp" %>