<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <head>
        <title><spring:message code="pageName"/> - <spring:message code="pageNotFound.title"/></title>
        <%@ include file="../components/includes/headers.jsp" %>
    </head>

    <body>
        <div class="container-fluid h-100" id="mainContainer">
            <div class="row h-100">
                <div class="col-12 h-25 d-flex justify-content-center align-items-center">
                    <h1><c:out value="${code}"/>: <c:out value="${errors}"/></h1>
                </div>
                <div class="col-12 h-50 d-flex justify-content-center align-items-center">
<%--                    <img src="<c:url value="/resources/images/pageNotFound.png"/>" alt="not found image"--%>
<%--                         style="object-fit: contain; width: 700px; height: 700px;">--%>
                </div>
                <div class="col-12 h-25 d-flex justify-content-center align-items-center">
                    <a href="<c:url value='/'/>" style="font-size: 30px;">
                        Volver
                    </a>
                </div>
            </div>
        </div>
    </body>
</html>
