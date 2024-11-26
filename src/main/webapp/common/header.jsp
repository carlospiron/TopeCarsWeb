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
<link rel="stylesheet" type="text/css" href="/TopeCarsWeb/css/styles.css"/>
<title>TopeCars</title>
</head>
<body>
<%@include file="/common/user-menu.jsp"%>