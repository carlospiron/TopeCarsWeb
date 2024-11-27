<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.pinguela.topecars.model.*"%>
<%@ page import="com.pinguela.topecars.web.util.*"%>
<%@ page import="com.pinguela.topecars.web.controller.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<fmt:setLocale value="${sessionScope['locale']}" />
<fmt:setBundle basename="classes.i18n.Messages" var="messages" />
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css'/>"/>
<title>TopeCars</title>
</head>
<header>
    <div class="logo">TopeCars</div>
    <div class="header-right">
        <div class="language-options">
            <a href="#">Inglés</a>
            <a href="#">Español</a>
            <a href="#">Gallego</a>
        </div>       
    </div>
</header>

<nav class="menu">
    <ul>
        <li><a href="#"><fmt:message key="empleados" bundle="${messages}"/></a>
            <ul>
                <li><a href="<%=request.getContextPath()%><%=Views.EMPLEADO_SEARCH%>">
                			<fmt:message key="buscar_empleados" bundle="${messages}" /></a></li>
                			
                <li><a href="<%=request.getContextPath()%><%=Views.EMPLEADO_INSERT%>">
                			<fmt:message key="insertar_empleado" bundle="${messages}" /></a></li>
            </ul>
        </li>
        <li><a href="#"><fmt:message key="clientes" bundle="${messages}"/></a>
            <ul>
                <li><a href="<%=request.getContextPath()%><%=Views.CLIENTE_SEARCH%>">
                			<fmt:message key="buscar_clientes" bundle="${messages}"/></a></li>
                			
                <li><a href="<%=request.getContextPath()%><%=Views.CLIENTE_INSERT%>">
                			<fmt:message key="ingresar_clente" bundle="${messages}"/></a></li>
                			
            </ul>
        </li>
        <li><a href="#"><fmt:message key="citas" bundle="${messages}"/></a>
            <ul>
                <li><a href="<%=request.getContextPath()%><%=Views.CITA_INSERT%>">
                		<fmt:message key="solicitar_cita" bundle="${messages}"/></a></li>
                		
                <li><a href="<%=request.getContextPath()%><%=Views.CITA_SEARCH%>">
                		<fmt:message key="buscar_citas" bundle="${messages}"/></a></li>
                		
                <li><a href="<%=request.getContextPath()%><%=Views.CITA_DELETE%>">
                		<fmt:message key="anular_citas" bundle="${messages}"/></a></li>
            </ul>
        </li>
        
        <li><a href="#"><fmt:message key="reparaciones" bundle="${messages}"/></a>
            <ul>
                <li><a href="<%=request.getContextPath()%><%=Views.CITA_INSERT%>">
                		<fmt:message key="solicitar_cita" bundle="${messages}"/></a></li>
                		
                <li><a href="<%=request.getContextPath()%><%=Views.CITA_SEARCH%>">
                		<fmt:message key="buscar_citas" bundle="${messages}"/></a></li>
                		
                <li><a href="<%=request.getContextPath()%><%=Views.CITA_DELETE%>">
                		<fmt:message key="anular_citas" bundle="${messages}"/></a></li>
            </ul>
        </li>
    </ul>
</nav>
<body>




