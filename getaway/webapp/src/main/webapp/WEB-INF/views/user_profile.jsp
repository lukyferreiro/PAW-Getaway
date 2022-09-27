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
            <%@ include file="../components/navbar.jsp" %>

            <div class="p-2" style="width: 600px;">
                <c:choose>
                    <c:when test="${hasImage}">
                        <img class="container-fluid p-0" src="<c:url value='/user/profileImage/${user.profileImageId}'/>" alt="Imagen"/>
                    </c:when>
                    <c:otherwise>
                        <img class="container-fluid p-0" style="height: fit-content" alt="Imagen ${user.email}"
                             src="<c:url value="/resources/images/ic_user.png" />" >
                    </c:otherwise>
                </c:choose>
            </div>

                    <div class="container-main">

                        <div class="container-fluid p-0 mt-3 h-100 w-100 ">
                            <div class="container-lg w-100 p-2 smallContentContainer">
                                <h5><spring:message code="registerForm.email.title"/></h5>
                                <h6>${user.email}</h6>

                                <h5><spring:message code="registerForm.name.title"/></h5>
                                <h6>${user.name}</h6>

                                <h5><spring:message code="registerForm.surname.title"/></h5>
                                <h6>${user.surname}</h6>

                            </div>
                        </div>
                    </div>

                    <a href="<c:url value = "/user/profile/edit"/>">
                <button type="button" class="btn btn-header">
                    <spring:message code="editProfile.title"/>
                </button>
            </a>


            <%@ include file="../components/footer.jsp" %>
        </div>

        <%@ include file="../components/includes/bottomScripts.jsp" %>
    </body>
</html>
