<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="com.pinguela.topecars.web.util.*"%>

<%@include file="/common/header.jsp"%>
    
    
    <form action="<%=request.getContextPath()%>/private/PrivateEmpleadoServlet" method="post">

	<h3><fmt:message key="buscarusuarios" bundle="${messages}" /></h3>
			
	<input type="hidden" name="action" value="search"/>
	
	<label><fmt:message key="id2p" bundle="${messages}"/></label>
	<input type="number" name="id" /> 
	<label><fmt:message key="nombre" bundle="${messages}"/></label> 
	<input type="text" name="nombre" />
	<label><fmt:message key="rol" bundle="${messages}"/></label>
	<input type="text" name="rolId" />
	<label><fmt:message key="email" bundle="${messages}"/></label> 
	<input type="text" name="email" />
	<input type="submit" value="<fmt:message key="search" bundle="${messages}"/>"/>
</form>

<div class="results-container">
	<c:choose>
		<c:when test="${not empty resultados}">
			<%-- <c:when test="{resultados is not null}"> --%>
			<h3><fmt:message
			key="resultadosbusqueda" bundle="${messages}" /></h3>
			<ul> 
	 	
				<c:forEach var="empleado" items="${resultados}">
					<li> 
					<a
						href="<%=request.getContextPath()%>/private/PrivateEmpleadoServlet?action=detail&id=${empleado.id}">
							<c:out value="${empleado.nombre}"/>
					</a></li>
				</c:forEach>
			</ul>
		</c:when>
		<c:otherwise>
			<p><fmt:message
			key="resultadosnoencontrados" bundle="${messages}" /></p>
		</c:otherwise>		
	</c:choose>
</div>
<%@include file="/common/footer.jsp"%>