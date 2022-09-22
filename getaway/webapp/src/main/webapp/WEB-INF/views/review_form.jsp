<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="review.titlePage"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
      <link href="<c:url value = "/resources/css/start_rating.css" />" rel="stylesheet">

   </head>

   <body>
      <div class="container-main">
         <jsp:include page="/WEB-INF/components/navbar.jsp">
            <jsp:param name="loggedUser" value="${loggedUser}"/>
         </jsp:include>

         <div class="justify-content-center">
            <form:form modelAttribute="reviewForm" action="${postPath}" id="createReviewForm" method="post" acceptCharset="UTF-8" enctype="multipart/form-data">
               <div class="container-inputs">
                  <div class="p-4 m-4">
                     <div class="col m-2"> <!--Titulo de la review-->
                        <form:label path="title" class="form-label">
                           <spring:message code="review.title"/>
                           <span class="required-field">*</span>
                        </form:label>
                        <form:input path="title" type="text" class="form-control" cssErrorClass="form-control is-invalid"/>
                        <form:errors path="title" element="p" cssClass="form-error-label"/>
                     </div>
                     <div class="col m-2"> <!--Descripcion de la review-->
                        <form:label path="description" class="form-label">
                           <spring:message code="review.description"/>
                           <span class="required-field">*</span>
                        </form:label>
                        <form:textarea path="description" class="form-control" cssErrorClass="form-control is-invalid" rows="4"/>
                        <form:errors path="description" element="p" cssClass="form-error-label"/>
                     </div>
                     <div class="col m-2"> <!--Rating-->
                        <form:label path="score" class="form-label">
                           <spring:message code="review.scoreAssign"/>
                           <span class="required-field">*</span>
                        </form:label>
                        <spring:message code="reviewForm.score.placeholder" var="placeholder"/>
                        <jsp:include page="/WEB-INF/views/star_form.jsp"/>
                        <form:input path="score" type="hidden" class="form-control" cssErrorClass="form-control is-invalid" id="scoreInput"/>
                        <form:errors path="score" element="p" cssClass="form-error-label"/>
                     </div>
                  </div>
               </div>
               <div class="p-0 mt-3 mb-0 d-flex justify-content-around">
                  <a href="<c:url value = "/experiences/${categoryName}/${experienceId}"/>">
                     <button class="btn btn-cancel-form px-3 py-2" id="cancelFormButton">
                        <spring:message code="experienceForm.cancel"/>
                     </button>
                  </a>
                  <button type="submit" class="btn btn-submit-form px-3 py-2" id="createReviewFormButton" form="createReviewForm">
                     <spring:message code="experienceForm.submit"/>
                  </button>
               </div>
            </form:form>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>
      <%@ include file="../components/includes/bottomScripts.jsp" %>
      <script src='<c:url value="/resources/js/createReview.js"/>'></script>
      <script src='<c:url value="/resources/js/cancelButton.js"/>'></script>
      <script src='<c:url value="/resources/js/ratingScore.js"/>'></script>
      <script src="https://kit.fontawesome.com/5ea815c1d0.js"></script>
   </body>