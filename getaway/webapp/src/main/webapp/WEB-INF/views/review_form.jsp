<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="review.titlePage"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>
         <div class="justify-content-center">
            <form:form modelAttribute="reviewForm" action="${postPath}" id="createReviewForm" method="post" acceptCharset="UTF-8" enctype="multipart/form-data">
               <div class="container-inputs">
                  <div class="p-4 m-4">
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
                        <form:textarea path="description" class="form-control" rows="4"/>
                        <form:errors path="description" element="p" cssClass="form-error-label"/>
                     </div>
                     <div class="col m-2"> <!--Rating-->
                        <form:label path="score" class="form-label">
                           <spring:message code="review.scoreAssign"/>
                           <span class="required-optional-text"><spring:message code="experienceForm.required"/></span>
                        </form:label>
                        <form:input path="score" type="text" class="form-control" id="reviewFormScoreInput" placeholder="0"/>
                        <form:errors path="score" element="p" cssClass="form-error-label"/>
                     </div>
                  </div>
               </div>
               <div class="p-0 mt-3 mb-0 d-flex justify-content-around">
                  <a href="<c:url value = "/"/>">
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

   </body>