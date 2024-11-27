<%@ page import="com.pinguela.topecars.web.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope['locale']}"/>
<fmt:setBundle basename="i18n.Messages" var="messages"/> 



<nav class="horizontal-menu">
    <ul>
        <li><a href="#"><fmt:message key="empleados" bundle="${messages}"/></a>
            <ul class="submenu">
                <li><a href="<%=request.getContextPath()%><%=Views.EMPLEADO_SEARCH%>"><fmt:message key="buscar_empleados" bundle="${messages}" /></a></li>
                <li><a href="<%=request.getContextPath()%><%=Views.EMPLEADO_INSERT%>"><fmt:message key="insertar-empleado" bundle="${messages}" /></a></li>
			
            </ul>
        </li>
        <li><a href="#"><fmt:message key="clientes" bundle="${messages}"/></a>
            <ul class="submenu">
                <li><a href="<%=request.getContextPath()%><%=Views.CLIENTE_SEARCH%>"><fmt:message key="buscar_clientes" bundle="${messages}"/></a></li>
                <li><a href="<%=request.getContextPath()%><%=Views.CLIENTE_INSERT%>"><fmt:message key="ingresar_clente" bundle="${messages}"/></a></li>
            </ul>
        </li>
        <li><a href="#"><fmt:message key="citas" bundle="${messages}"/></a>
            <ul class="submenu">
                <li><a href="<%=request.getContextPath()%><%=Views.CITA_INSERT%>"><fmt:message key="solicitar_cita" bundle="${messages}"/></a></li>
                <li><a href="<%=request.getContextPath()%><%=Views.CITA_SEARCH%>"><fmt:message key="buscar_citas" bundle="${messages}"/></a></li>
            </ul>
        </li>
    </ul>
</nav>