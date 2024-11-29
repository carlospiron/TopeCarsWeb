<%@ page language="java" contentType="text/html"  pageEncoding="UTF-8"%>
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
        
        <c:url var="spanish" value="/public/PublicEmpleadoServlet">
        	<c:param name="${Parameters.ACTION}" value="${Actions.CHANGE_LOCALE}"/>
        	<c:param name="${Parameters.LOCALE}" value="${LocalName.SPANISH}"/>
        	<c:param name="${Parameters.CALLBACK_URL}" value="${requestScope.callbackURL}"/>
        </c:url>
		<a href="${spanish}"><fmt:message key="idioma_espanol" bundle="${message}"/></a>
		
		<c:url var="galician" value="/public/PublicEmpleadoServlet">
        	<c:param name="${ParameterName.ACTION}" value="${Actions.CHANGE_LOCALE}"/>
        	<c:param name="${ParameterName.LOCALE}" value="${LocaleName.GALICIAN}"/>
        	<c:param name="${ParameterName.CALLBACK_URL}" value="${requestScope.callbackURL}"/>        	
        </c:url>
        <a href="${galician}"><fmt:message key="idioma_gallego" bundle="${message}"/></a>
		
		<c:url var="english" value="/public/PublicEmpleadoServlet">
        	<c:param name="${ParameterName.ACTION}" value="${Actions.CHANGE_LOCALE}"/>
        	<c:param name="${ParameterName.LOCALE}" value="${LocalName.ENGLISH}"/>
        	<c:param name="${ParameterName.CALLBACK_URL}" value="${requestScope.callbackURL}"/>        	
        </c:url>
		<a href="${english}"><fmt:message key="idioma_ingles" bundle="${message}"/></a>
		
		
            <a href="#">Inglés</a>
            <a href="#">Español</a>
            <a href="#">Gallego</a>
        </div>       
    </div>
    
    <button class="settings"><%@include file="/common/user-menu.jsp"%></button>
</header>


<body>




