<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><spring:message code="pageName"/> - <c:url value="${dbCategoryName}"/></title>
    <%@ include file="../components/includes/headers.jsp" %>
</head>

<body>
    <div class="container-main">
        <%@ include file="../components/navbar.jsp" %>
        <form:form modelAttribute="reviewForm" action="${postPath}" id="createReviewForm" method="post" acceptCharset="UTF-8" enctype="multipart/form-data">
            <div class="container-inputs">
                <div class="p-0 m-0 d-flex">
                    <div class="col m-2"> <!--Titulo de la review-->
                        <form:label path="title" class="form-label">
                            <spring:message code="review.title"/>
                            <span class="required-optional-text"><spring:message code="experienceForm.required"/></span>
                        </form:label>
                        <form:input path="title" type="text" class="form-control"/>
                        <form:errors path="title" element="p" cssClass="form-error-label"/>
                    </div>
                    <div class="col m-2"> <!--Descripcion de la review-->
                        <form:label path="description" class="form-label">
                            <spring:message code="review.description"/>
                            <span class="required-optional-text"><spring:message code="experienceForm.required"/></span>
                        </form:label>
                        <form:input path="description" type="text" class="form-control"/>
                        <form:errors path="description" element="p" cssClass="form-error-label"/>
                    </div>
                    <div class="col m-2"> <!--Rating-->
                        <form:label path="score" class="form-label">
                            <spring:message code="review.scoreAssign"/>
                            <span class="required-optional-text"><spring:message code="experienceForm.optional"/></span>
                        </form:label>
                        <form:input path="score" type="text" class="form-control" id="reviewFormScoreInput" placeholder="0"/>
                        <form:errors path="score" element="p" cssClass="form-error-label"/>
                    </div>
                </div>
            </div>
        </form:form>
        <%@ include file="../components/footer.jsp" %>
    </div>
    <%@ include file="../components/includes/bottomScripts.jsp" %>
</body>