<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <head>
        <title><spring:message code="pageName"/> - <spring:message code="pageNotFound.title"/></title>
        <%@ include file="../components/includes/headers.jsp" %>
        <link href="<c:url value = "/resources/css/error.css" />" rel="stylesheet">
    </head>

    <body>
        <div class="w-100 h-100 d-flex justify-content-center align-items-center">
            <div class="d-flex flex-column justify-content-center align-items-center">
                <h1 class="font-weight-bold m-0" style="font-size: 15vh;">
                    <c:out value="${code}"/>
                </h1>
                <h1 class="font-weight-bold" style="font-size: 5vh; margin-top: -3%">
                    <spring:message code="pageNotFound.description"/>
                </h1>
                <h4>
                    <span style="color:red;"><spring:message code="error.whoops"/></span>
                    <c:out value="${errors}"/>
                </h4>
                <a href="<c:url value='/'/>">
                    <button type="button" class="btn btn-error">Volver</button>
                </a>
            </div>
        </div>
    </body>
</html>
