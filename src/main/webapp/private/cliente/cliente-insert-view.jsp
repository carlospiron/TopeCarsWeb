<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@include file="/common/header.jsp"%>
   <%@include file="/common/menu.jsp"%>
   
   <c:forEach var="error" items="${errors.globalErrors}">
			<li><fmt:message key="${error}" bundle="${messages}" /></li>
	</c:forEach>

<fmt:setLocale value="${pageContext.request.locale}" />

<form action="<%=request.getContextPath()%>/private/ClienteServlet" method="post" class="purchase-container">
    <h3>
        <fmt:message key="ingresar_cliente" bundle="${messages}" />
    </h3>
    
    <input type="hidden" name="action" value="register" />
    
<BR/>	
    <label><fmt:message key="nombre" bundle="${messages}" /></label>
	<c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.NOMBRE)}">
			<li><fmt:message key="${errorCode}" bundle="${messages}" /></li>
	</c:forEach>       
    <input type="text" name="nombre" value="${param.nombre}" required>

<BR/>
    <label><fmt:message key="primer_apellido" bundle="${messages}" /></label> 
    <c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.APELLIDO1)}">
			<li><fmt:message key="${errorCode}" bundle="${messages}" /></li>
	</c:forEach> 
    <input type="text" name="apellido1" value="${param.apellido1}" placeholder="Ejemplo: Pérez" required />
    
<BR/>
    <label><fmt:message key="segundo_apellido" bundle="${messages}" /></label>
    <c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.APELLIDO2)}">
			<li><fmt:message key="${errorCode}" bundle="${messages}" /></li>
	</c:forEach> 
    <input type="text" name="apellido2" value="${param.apellido2}"  placeholder="Ejemplo: Pérez" required/>

<BR/>
    <label><fmt:message key="dni" bundle="${messages}" /></label>
	<c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.DNI)}">
			<li><fmt:message key="${errorCode}" bundle="${messages}" /></li>
	</c:forEach>           
    <input type="text" name="dni" value="${param.dni}" placeholder="Ejemplo: 12345678-A" required />
<BR/>

    <label><fmt:message key="correo" bundle="${messages}" /></label> 
    <c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.EMAIL)}">
			<li><fmt:message key="${errorCode}" bundle="${messages}" /></li>
	</c:forEach> 
    <input type="email" name="email" value="${param.email}" placeholder="Ejemplo: juan@example.com" required />
<BR/>

    <label><fmt:message key="telefono" bundle="${messages}" /></label>
    <input type="text" name="telefono" value="${param.telefono}" required />
 <BR/>
    
    <label><fmt:message key="codigo_postal" bundle="${messages}" /></label>
    <c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.CP)}">
			<li><fmt:message key="${errorCode}" bundle="${messages}" /></li>
	</c:forEach>
    <input type="text" name="cp" value="${param.cp}" required />
<BR/>

    <input type="submit" value="<fmt:message key="aceptar" bundle="${messages}" />"/>
</form>
