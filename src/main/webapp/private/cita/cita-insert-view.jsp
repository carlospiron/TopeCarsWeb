<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@include file="/common/header.jsp"%>
   <%@include file="/common/menu.jsp"%>
   
 	<c:forEach var="error" items="${errors.globalErrors}">
			<li><fmt:message key="${error}" bundle="${messages}" /></li>
	</c:forEach>  
	
<fmt:setLocale value="${pageContext.request.locale}"/>

<form action="<%=request.getContextPath()%>/private/CitaServlet" method="post" class="purchase-container">
    <h3>
        <fmt:message key="solicitar_cita" bundle="${messages}" />
    </h3>
    
    <input type="hidden" name="action" value="register" />
    
<BR/>	
    <label><fmt:message key="fecha" bundle="${messages}" /></label>
	<c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.FECHA)}">
			<li><fmt:message key="${errorCode}" bundle="${messages}" /></li>
	</c:forEach>       
    <input type="date" name="fecha" value="${param.fecha}" required>

<BR/>
    <label><fmt:message key="motivo" bundle="${messages}" /></label> 
    <c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.MOTIVO)}">
			<li><fmt:message key="${errorCode}" bundle="${messages}" /></li>
	</c:forEach> 
    <input type="text" name="motivo" value="${param.motivo}" required />
    
<BR/>
    <label><fmt:message key="correo" bundle="${messages}" /></label>
    <c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.EMAIL)}">
			<li><fmt:message key="${errorCode}" bundle="${messages}" /></li>
	</c:forEach> 
    <input type="email" name="email" value="${param.email}"  required/>

<BR/>
    <label><fmt:message key="telefono" bundle="${messages}" /></label>          
    <input type="text" name="telefono" value="${param.telefono}" required />
<BR/>

    <label><fmt:message key="matricula" bundle="${messages}" /></label> 
    <c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.MATRICULA)}">
			<li><fmt:message key="${errorCode}" bundle="${messages}" /></li>
	</c:forEach> 
    <input type="text" name="matricula" value="${param.matricula}" placeholder="5678ABC" required />
<BR/>

    <label><fmt:message key="numero_bastidor" bundle="${messages}" /></label>
    <input type="text" name="numeroBastidor" value="${param.numeroBastidor}" required />
 <BR/>
    
    <input type="submit" value="<fmt:message key="aceptar" bundle="${messages}" />"/>
</form>