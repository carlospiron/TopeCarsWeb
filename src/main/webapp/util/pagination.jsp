<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="pagination-container">
    <c:choose>
        <c:when test="${not empty resultados.page}">
            <!-- Paginaci�n -->
            <div class="pagination">
                <!-- Primera p�gina -->
                <c:if test="${currentPage != 1}">
                    <c:url value="${baseURL}" var="firstPageURL">
                        <c:param name="page" value="1"/>
                    </c:url>
                    <a href="${firstPageURL}">Primera</a>
                </c:if>

                <!-- P�gina anterior -->
                <c:if test="${currentPage > 1 && lastPage > 2}">
                    <c:url value="${baseURL}" var="previousPageURL">
                        <c:param name="page" value="${currentPage - 1}"/>
                    </c:url>
                    <a href="${previousPageURL}">Anterior</a>
                </c:if>

                <!-- P�ginas intermedias -->
                <c:forEach var="i" begin="${fromPage}" end="${toPage}" step="1">
                    <c:if test="${i != currentPage}">
                        <c:url value="${baseURL}" var="pageURL">
                            <c:param name="page" value="${i}"/>
                        </c:url>
                        <a href="${pageURL}">${i}</a>
                    </c:if>
                    <c:if test="${i == currentPage}">
                        <span>${i}</span>
                    </c:if>
                </c:forEach>

                <!-- P�gina siguiente -->
                <c:if test="${currentPage < lastPage && (lastPage - currentPage) > 2}">
                    <c:url value="${baseURL}" var="nextPageURL">
                        <c:param name="page" value="${currentPage + 1}"/>
                    </c:url>
                    <a href="${nextPageURL}">Siguiente</a>
                </c:if>

                <!-- �ltima p�gina -->
                <c:if test="${currentPage != lastPage}">
                    <c:url value="${baseURL}" var="lastPageURL">
                        <c:param name="page" value="${lastPage}"/>
                    </c:url>
                    <a href="${lastPageURL}">�ltima</a>
                </c:if>
            </div>

        </c:when>
        <c:otherwise>
            <p><fmt:message key="sin_resultados" bundle="${messages}"/></p>
        </c:otherwise>
    </c:choose>
</div>