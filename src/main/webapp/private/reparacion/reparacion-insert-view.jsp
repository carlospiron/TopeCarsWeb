<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@include file="/common/header.jsp"%>
   <%@include file="/common/menu.jsp"%>
   <%@include file="/common/load-maestra.jsp"%>
   
 	<c:forEach var="error" items="${errors.globalErrors}">
			<li><fmt:message key="${error}" bundle="${messages}" /></li>
	</c:forEach>  
	
	<fmt:setLocale value="${pageContext.request.locale}"/>
	
	<form action="<%=request.getContextPath()%>/private/ReparacionServlet" method="post" class="purchase-container">
    <h3>
        <fmt:message key="nueva_reparacion" bundle="${messages}" />
    </h3>
    
    <input type="hidden" name="action" value="register" />
    
    <BR/>	
    <label><fmt:message key="fecha_inicio" bundle="${messages}" /></label>       
    <input type="date" name="fechaInicio" value="${param.fechaInicio}" required>

<BR/>
    <label><fmt:message key="fecha_fin" bundle="${messages}" /></label>  
    <input type="date" name="fechaFin" value="${param.fechaFin}" required />
    
<BR/>
    <label><fmt:message key="descripcion" bundle="${messages}" /></label> 
    <input type="text" name="descripcion" value="${param.descripcion}"  required/>

<BR/>
    <label><fmt:message key="matricula" bundle="${messages}" /></label> 
    <c:forEach var="errorCode" items="${errors.getFieldErrors(Parameters.MATRICULA)}">
			<li><fmt:message key="${errorCode}" bundle="${messages}" /></li>
	</c:forEach> 
    <input type="text" name="matricula" value="${param.matricula}" placeholder="5678ABC" required />
    
<BR/>
    <label><fmt:message key="id_estado_reparacion" bundle="${messages}" /></label> 
    <select class="input-field" name="${Parameters.ID_ESTADO_REPARACION}">
				<option value="">Seleccionar</option>
				<c:forEach var="estados" items="${estadoReparacion}">
					<option value="${estados.idEstadoReparacion}" 
					<c:if test="${param.idEstadoReparacion == estados.idEstadoReparacion && param.idEstadoReparacion != null}">selected</c:if>>${estados.nombre}</option>
				</c:forEach>
			</select>

<BR/>
    <label><fmt:message key="numero_bastidor" bundle="${messages}" /></label>
    <input type="text" name="numeroBastidor" value="${param.numeroBastidor}" required />
 <BR/>
 	<label><fmt:message key="id_empleado" bundle="${messages}" /></label>
    <input type="text" name="idEmpleado" value="${param.idEmpleado}" required />
 
 
    
    <input type="submit" value="<fmt:message key="aceptar" bundle="${messages}" />"/>
</form>