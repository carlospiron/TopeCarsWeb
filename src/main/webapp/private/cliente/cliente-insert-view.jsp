<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@include file="/common/header.jsp"%>
   <%@include file="/common/menu.jsp"%>

<fmt:setLocale value="${pageContext.request.locale}" />

<form action="<%=request.getContextPath()%>/private/ClienteServlet" method="post" class="purchase-container">
    <h3>
        <fmt:message key="ingresar_cliente" bundle="${messages}" />
    </h3>
    
    <input type="hidden" name="action" value="register" />

    <label><fmt:message key="nombre" bundle="${messages}" /></label> 
    <input type="text" name="nombre" required />

    <label><fmt:message key="primer_apellido" bundle="${messages}" /></label> 
    <input type="text" name="apellido1" placeholder="Ejemplo: Pérez" required />

    <label><fmt:message key="segundo_apellido" bundle="${messages}" /></label> 
    <input type="text" name="apellido2" />

    <label><fmt:message key="dni" bundle="${messages}" /></label>
    <input type="text" name="dni" placeholder="Ejemplo: 12345678-A" required />

    <label><fmt:message key="correo" bundle="${messages}" /></label> 
    <input type="email" name="email" placeholder="Ejemplo: juan@example.com" required />

    <label><fmt:message key="telefono" bundle="${messages}" /></label>
    <input type="number" name="telefono" required />
    
    <label><fmt:message key="codigo_postal" bundle="${messages}" /></label>
    <input type="number" name="cp" required />

    <input type="submit" value="<fmt:message key="aceptar" bundle="${messages}" />"/>
</form>
