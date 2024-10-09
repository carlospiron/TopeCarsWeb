<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import="com.pinguela.topecars.web.util.*"%>
<%@include file="/common/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TopeCars</title>
</head>
<body>
<form action="/TopeCarsWeb/ClienteServlet" method="post">

<input type="hidden" name="<%=Parameters.ACTION%>" value="<%=Actions.LOGIN%>"/>
 
	<label>E-mail</label>
	<input type="email" name="<%=Parameters.EMAIL%>" placeholder="user@gmail.com"/>
	
	<label>Contraseña</label>
	<input type="password" name="<%=Parameters.PASSWORD%>">
	
	<input type="submit" value="Login"/>
	<input type="reset" value="Cancelar"/>
	
</form>

<%@include file="/common/footer.jsp" %>
