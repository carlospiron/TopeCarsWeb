

    <%@ page import="com.pinguela.topecars.web.util.*"%>

<%@include file="/common/header.jsp"%>
   <%@include file="/common/menu.jsp"%>
   
   <form action="<%=request.getContextPath()%>/private/CitaServlet" method="post">

	<h3><fmt:message key="buscar_citas" bundle="${messages}" /></h3>
			
	<input type="hidden" name="action" value="search"/>
	
	<label><fmt:message key="matricula" bundle="${messages}"/></label>
	<input type="number" name="matricula" /> 
	
	<label><fmt:message key="fecha_desde" bundle="${messages}"/></label> 
	<input type="text" name="fecha_desde" />
	
	<label><fmt:message key="fecha_hasta" bundle="${messages}"/></label> 
	<input type="text" name="fecha_hasta" />
	
	<input type="submit" value="<fmt:message key="buscar" bundle="${messages}"/>"/>
</form>

<div class="results-container">
	<!--  getPage() ? -->
	<c:choose>
		<c:when test="${not empty resultados.page}">
			<h3><fmt:message key="resultados" bundle="${messages}"/></h3>
			<ul>
				<c:forEach var="cliente" items="${resultados.page}">
					<li>
						<a href="<%=request.getContextPath()%>/private/CitaServlet?action=detail&fecha=${cita.fechaDesde}">
							<c:out value="${cita.fechaHasta}" />
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