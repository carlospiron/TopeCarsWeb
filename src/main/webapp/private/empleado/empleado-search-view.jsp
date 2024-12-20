
<%@ page import="com.pinguela.topecars.web.util.*"%>

<%@include file="/common/header.jsp"%>
   <%@include file="/common/menu.jsp"%>
    
    <form action="<%=request.getContextPath()%>/private/PrivateEmpleadoServlet" method="post">

	<h3><fmt:message key="buscar_empleados" bundle="${messages}" /></h3>
			
	<input type="hidden" name="action" value="search"/>
	
	<label><fmt:message key="identificacion" bundle="${messages}"/></label>
	<input type="number" name="id" /> 
	
	<label><fmt:message key="nombre" bundle="${messages}"/></label> 
	<input type="text" name="nombre" />
	
	<label><fmt:message key="primer_apellido" bundle="${messages}"/></label> 
	<input type="text" name="apellido1" />
	
	<label><fmt:message key="segundo_apellido" bundle="${messages}"/></label> 
	<input type="text" name="apellido2" />
	
	<label><fmt:message key="id_rol" bundle="${messages}"/></label>
	<select name="idRol">
  		<option value="1">1</option>
  		<option value="2">2</option>
  		<option value="3">3</option>
	</select>
	
	<label><fmt:message key="correo" bundle="${messages}"/></label> 
	<input type="text" name="email" />
	
	<input type="submit" value="<fmt:message key="buscar" bundle="${messages}"/>"/>
</form>

<div class="results-container">
	<c:choose>
		<c:when test="${not empty resultados.page}">
			
			<h3><fmt:message key="resultados" bundle="${messages}"/></h3>
			<ul> 
	 			<c:forEach var="empleado" items="${resultados.page}">
					<li> 
					<a
						href="<%=request.getContextPath()%>/private/PrivateEmpleadoServlet?action=detail&id=${empleado.idEmpleado}">
							<c:out value="${empleado.nombre}"/>
					</a></li>
				</c:forEach>
			</ul>
			
			<jsp:include page="/util/pagination.jsp">
                <jsp:param name="resultados" value="${resultados}" />
                <jsp:param name="baseURL" value="${baseURL}" />
                <jsp:param name="currentPage" value="${currentPage}" />
                <jsp:param name="fromPage" value="${fromPage}" />
                <jsp:param name="toPage" value="${toPage}" />
                <jsp:param name="lastPage" value="${lastPage}" />
            </jsp:include>
			
		</c:when>
		<c:otherwise>
			<p><fmt:message
			key="resultados_no_encontrados" bundle="${messages}" /></p>
		</c:otherwise>		
	</c:choose>
</div>
<%@include file="/common/footer.jsp"%>