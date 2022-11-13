<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="pageName"/> - <spring:message code="error.title" arguments="${code}"/></title>
    <%@ include file="../components/includes/headers.jsp" %>
</head>

<body>
<div class="container-main">
    <div class="container-fluid p-0 h-100 d-flex justify-content-center align-items-center">
        <div class="container-fluid p-0 d-flex flex-column justify-content-center align-items-center"
             style="width: 60%">
            <h1 class="font-weight-bold m-0" style="font-size: 15vh;">
                <c:out value="${code}"/>
            </h1>
            <h1 class="font-weight-bold text-center" style="font-size: 5vh;">
                <spring:message code="error.description" arguments="${code}"/>
            </h1>
            <h4 class="text-center">
                <span style="color:red;"><spring:message code="error.whoops"/></span>
                <c:out value="${description}"/>
            </h4>
            <a href="<c:url value='/'/>">
                <button type="button" class="btn btn-error">
                    <spring:message code="error.btn"/>
                </button>
            </a>
        </div>
    </div>
</div>
<%@ include file="../components/includes/bottomScripts.jsp" %>
</body>
</html>

