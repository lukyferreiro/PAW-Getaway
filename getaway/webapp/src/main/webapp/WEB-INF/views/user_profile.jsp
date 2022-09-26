<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <head>
        <title><spring:message code="pageName"/> - <spring:message code="navbar.profile"/></title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
        <link href='<c:url value="/resources/css/user_experiences.css"/>' rel="stylesheet">
        <%@ include file="../components/includes/headers.jsp" %>
    </head>

    <body>
        <div class="container-main">
            <jsp:include page="/WEB-INF/components/navbar.jsp">
                <jsp:param name="loggedUser" value="${loggedUser}"/>
            </jsp:include>

            <h1>${user.email}</h1>
            <h1>${user.name}</h1>
            <h1>${user.surname}</h1>

            <a href="<c:url value = "/user/profile/edit"/>">
                <button type="button" class="btn btn-header">
                    <spring:message code="editProfile.title"/>
                </button>
            </a>


            <div class="p-2" style="width: 600px;">
                <c:choose>
                    <c:when test="${user.profileImageId == 0}">
                        <img class="container-fluid p-0" style="height: fit-content" alt="Imagen ${user.email}"
                             src="<c:url value="/resources/images/ic_user.png" />" >
                    </c:when>
                    <c:otherwise>
                        <img class="container-fluid p-0" style="height: fit-content" src="<c:url value='/user/profileImage'/>" alt="Imagen"/>
                    </c:otherwise>
                </c:choose>
            </div>

            <%@ include file="../components/footer.jsp" %>
        </div>

        <%@ include file="../components/includes/bottomScripts.jsp" %>
    </body>
</html>
