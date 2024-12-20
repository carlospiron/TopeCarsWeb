 
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    
   <%@ page import="com.pinguela.topecars.web.util.*"%>
   
   
   <nav class="menu">
    <ul>
        <li><a href="#"><fmt:message key="empleados" bundle="${messages}"/></a>
            <ul>
                <li><a href="<%=request.getContextPath()%><%=Views.EMPLEADO_SEARCH%>">
                			<fmt:message key="buscar_empleados" bundle="${messages}" /></a></li>
                			
            </ul>
        </li>
        <li><a href="#"><fmt:message key="clientes" bundle="${messages}"/></a>
            <ul>
                <li><a href="<%=request.getContextPath()%><%=Views.CLIENTE_SEARCH%>">
                			<fmt:message key="buscar_clientes" bundle="${messages}"/></a></li>
                			
                <li><a href="<%=request.getContextPath()%><%=Views.CLIENTE_INSERT%>">
                			<fmt:message key="nuevo_cliente" bundle="${messages}"/></a></li>
                			
                			
            </ul>
        </li>
        <li><a href="#"><fmt:message key="citas" bundle="${messages}"/></a>
            <ul>
                <li><a href="<%=request.getContextPath()%><%=Views.CITA_INSERT%>">
                		<fmt:message key="solicitar_cita" bundle="${messages}"/></a></li>
                		
                <li><a href="<%=request.getContextPath()%><%=Views.CITA_SEARCH%>">
                		<fmt:message key="buscar_citas" bundle="${messages}"/></a></li>
                		
            </ul>
        </li>
        <li><a href="#"><fmt:message key="reparaciones" bundle="${messages}"/></a>
            <ul>
                <li><a href="<%=request.getContextPath()%><%=Views.REPARACION_SEARCH%>">
                			<fmt:message key="buscar_reparaciones" bundle="${messages}"/></a></li>
                			
                <li><a href="<%=request.getContextPath()%><%=Views.REPARACION_INSERT%>">
                			<fmt:message key="nueva_reparacion" bundle="${messages}"/></a></li>
                			
            </ul>
        </li>
       
    </ul>
</nav>
