<%@include file="/common/header.jsp" %>

<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.pinguela.topecars.web.util.*"%>
<%@ page import="com.pinguela.topecars.model.*"%>
<%@ page import="com.pinguela.topecars.web.util.*"%>


<%@include file="/common/menu.jsp"%>

<div class="container">
    <div class="form-container">
        <%
            DateFormat FECHA = new SimpleDateFormat("yyyy/MM/dd");
            Calendar fechaInicio = new GregorianCalendar();
            fechaInicio.add(Calendar.DATE, -7); // Fecha de inicio: 7 días atrás
            String fechaIni = FECHA.format(fechaInicio.getTime());

            Calendar fechaHasta = new GregorianCalendar(); // Fecha de fin: hoy
            String fechaFin = FECHA.format(fechaHasta.getTime());
        %>
        <form action="<%=request.getContextPath()%>/private/CitaServlet" method="post">
            <h3><fmt:message key="buscar_citas" bundle="${messages}" /></h3>

            <input type="hidden" name="action" value="search"/>

            <label><fmt:message key="matricula" bundle="${messages}"/></label>
            <input type="text" name="matricula" class="input-field"/><br>

            <label><fmt:message key="fecha_desde" bundle="${messages}"/></label>
            <input type="text" placeholder="yyyy/MM/dd" name="fechaDesde" class="input-field" value="<%= fechaIni %>"/><br>

            <label><fmt:message key="fecha_hasta" bundle="${messages}"/></label>
            <input type="text" placeholder="yyyy/MM/dd" name="fechaHasta" class="input-field" value="<%= fechaFin %>"/><br>

            <input type="submit" value="<fmt:message key='buscar' bundle='${messages}'/>" class="submit-button"/>
        </form>
    </div>

    <div class="results-container">
        <table class="results-table">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Matrícula</th>
                    <th>Fecha</th>
                    <th>Motivo</th>
                    <th>Teléfono</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty resultados}">
                        <c:forEach var="cita" items="${resultados}">
                            <tr>
                                <td><c:out value="${cita.idCita}"/></td>
                                <td><c:out value="${cita.matricula}"/></td>
                                <td><c:out value="${cita.fecha}"/></td>
                                <td><c:out value="${cita.motivo}"/></td>
                                <td><c:out value="${cita.telefono}"/></td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/private/CitaServlet?action=detail&id=${cita.idCita}">
                                        <fmt:message key="detalle" bundle="${messages}"/>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="6"><fmt:message key="resultados_no_encontrados" bundle="${messages}"/></td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/common/footer.jsp"%>