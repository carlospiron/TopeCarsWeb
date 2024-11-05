<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp" %>

<div class="login-container">

	<img src="/TopeCarsWeb/imgs/topecars.webp" alt="Login Image"
		class="login-image" />

<form action="/TopeCarsWeb/public/PublicClienteServlet" method="post">
	
	<h3>Autenticación usuario</h3>
	
	<input type="hidden" name="<%=Parameters.ACTION%>" value="<%=Actions.LOGIN%>"/>
 
	<label>E-mail</label>
	<input type="email" name="<%=Parameters.EMAIL%>" placeholder="user@gmail.com" required/>
	
	<label>Contraseña</label>
	<input type="password" name="<%=Parameters.PASSWORD%>" placeholder="Introduzca contraseña" required/>
	<input type="checkbox" name="<%=Parameters.REMEMBER_USER%>" checked>Recordar usuario</input>
	
	<input type="submit" value="Login"/>
	<input type="reset" value="Cancelar"/>
	
</form>

</div>

<%@include file="/common/footer.jsp" %>
