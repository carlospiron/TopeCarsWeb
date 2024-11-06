<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pinguela.topecars.model.*"%>
<%@ page import="com.pinguela.topecars.web.util.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="/TopeCarsWeb/css/login.css"/>
<title>TopeCars</title>
</head>
<body>
<%@include file="/common/user-menu.jsp"%>


<div class="register-container">
    <h3>Registro de usuario</h3>
    <form action="/TopeCarsWeb/public/PublicClienteServlet" method="post">
        <input type="hidden" name="<%=Parameters.ACTION%>" value="<%=Actions.REGISTER%>"/>
        
        <label>Nombre</label>
        <input type="text" name="<%=Parameters.NOMBRE%>" required/>
        
        <label>Apellido1</label>
        <input type="text" name="<%=Parameters.APELLIDO1%>" required/>
        
        <label>Apellido2</label>
        <input type="text" name="<%=Parameters.APELLIDO2%>" required/>
        
        <label>DNI</label>
        <input type="text" name="<%=Parameters.DNI%>" required/>

        <label>E-mail</label>
        <input type="email" name="<%=Parameters.EMAIL%>" placeholder="user@gmail.com" required/>
        
        <label>Teléfono</label>
        <input type="tel" name="<%=Parameters.TELEFONO%>" required/>
        
        <label>CP</label>
        <input type="text" name="<%=Parameters.CP%>" required/>
        
        <label>Contraseña</label>
        <input type="password" name="<%=Parameters.PASSWORD%>" placeholder="Introduzca contraseña" required/>
        
        <input type="submit" value="Registrarse"/>
    </form>
</div>

<%@include file="/common/footer.jsp" %>