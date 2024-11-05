<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.pinguela.topecars.web.util.Parameters"%>
<%@ page import="com.pinguela.topecars.web.util.Actions"%>
<%@ page import="com.pinguela.topecars.model.Results"%>
<%@ page import="com.pinguela.topecars.model.CitaDTO"%>
<%@ page import="com.pinguela.topecars.web.util.Attributes"%>

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
		<form action="/TopeCarsWeb/private/CitaServlet" method="get">
			<input type="hidden" name="<%= Parameters.ACTION %>"
				value="<%= Actions.SEARCH %>"> <label>Matricula:</label> <input
				type="text" name="<%= Parameters.MATRICULA %>" class="input-field"><br>

			<label>Fecha Desde:</label> <input type="text"
				placeholder="yyyy/MM/dd" name="<%= Parameters.FECHA_DESDE %>"
				class="input-field" value="<%= fechaIni %>"><br> <label>Fecha
				Hasta:</label> <input type="text" placeholder="yyyy/MM/dd"
				name="<%= Parameters.FECHA_HASTA %>" class="input-field"
				value="<%= fechaFin %>"><br> <input type="submit"
				value="Buscar" class="submit-button">
		</form>
	</div>

	<div class="results-container">
		<table class="results-table">
			<thead>
				<tr>
					<th>Id</th>
					<th>Matricula</th>
					<th>Fecha</th>
					<th>Motivo</th>
					<th>Telefono</th>
				</tr>
			</thead>
			<tbody>
				<%
                    Results<CitaDTO> resultados = (Results<CitaDTO>) request.getAttribute("resultados");
                    if (resultados != null) {
                        for (CitaDTO cita : resultados.getPage()) {
                %>
				<tr>
					<td><%= cita.getIdCita() %></td>
					<td><%= cita.getMatricula() %></td>
					<td><%= FECHA.format(cita.getFecha()) %></td>
					<td><%= cita.getMotivo() %></td>
					<td><%= cita.getTelefono() %></td>
					<td><a href="/TopeCarsWeb/private/CitaServlet?action=detail&id=<%= cita.getIdCita() %>">Detalle</a></td>
				</tr>
				<%
                        }
                    } else {
                %>
				<tr>
					<td colspan="6">No se encontraron resultados para la búsqueda.</td>
				</tr>
				<%
                    }
                %>
			</tbody>
		</table>
	</div>
</div>

<%@ include file="/common/footer.jsp"%>