<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="pageName"/></title>
    <%@ include file="../components/includes/headers.jsp" %>
</head>

<body>
<div class="container-main">
    <%@ include file="../components/navbar.jsp" %>

    <c:if test="${successFav}">
        <div id="snackbar" style="left:37%;"><spring:message code="favExperience.success"/></div>
    </c:if>

    <jsp:include page="/WEB-INF/components/carousel.jsp">
        <jsp:param name="listByCategory" value="${listByCategory}"/>
        <jsp:param name="isLogged" value="${isLogged}"/>
        <jsp:param name="path" value="/"/>
    </jsp:include>

    <%@ include file="../components/footer.jsp" %>
</div>

<%@ include file="../components/includes/bottomScripts.jsp" %>

</body>
</html>