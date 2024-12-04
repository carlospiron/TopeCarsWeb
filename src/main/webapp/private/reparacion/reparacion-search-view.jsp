<%@ page import="com.pinguela.topecars.web.util.*"%>

<%@include file="/common/header.jsp"%>
   <%@include file="/common/menu.jsp"%>
   
   <form action="<%=request.getContextPath()%>/private/ReparacionServlet" method="post">

	<h3><fmt:message key="buscar_reparaciones" bundle="${messages}" /></h3>
			
	<input type="hidden" name="action" value="search"/>
	
	<label><fmt:message key="fecha_inicio" bundle="${messages}"/></label> 
	<input type="date" name="<%=Parameters.FECHA_INICIO %>" />
	
	<label><fmt:message key="fecha_fin" bundle="${messages}"/></label> 
	<input type="date" name="<%=Parameters.FECHA_FIN %>" />
	
	<input type="submit" value="<fmt:message key="buscar" bundle="${messages}"/>"/>
</form>

<div class="results-container">
	<!--  getPage() ? -->
	<c:choose>
		<c:when test="${not empty resultados.page}">
			<h3><fmt:message key="resultados" bundle="${messages}"/></h3>
			<ul>
				<c:forEach var="reparacion" items="${resultados.page}">
					<li>
						<a href="<%=request.getContextPath()%>/private/ReparacionServlet?action=detail&id=${reparacion.idReparacion}">
							<c:out value="${reparacion.ordenDeTrabajo}" />
						</a>
					</li>
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
				
				<p>${resultados.total} resultados encontrados</p>	
		</c:when>
		<c:otherwise>
			<p><fmt:message key="sin_resultados" bundle="${messages}"/></p>
		</c:otherwise>
	</c:choose>
</div>
<%@include file="/common/footer.jsp"%>