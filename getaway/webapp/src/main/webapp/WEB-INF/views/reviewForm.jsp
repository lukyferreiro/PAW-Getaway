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

         <div class="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">
            <div class="container-lg w-100 modalContainer d-flex flex-column justify-content-center align-items-center">
               <c:url value="${endpoint}" var="postPath"/>
               <form:form modelAttribute="reviewForm" action="${postPath}" id="submitForm" method="post" acceptCharset="UTF-8"
                          enctype="multipart/form-data" cssStyle="width: 100%;">
                  <div class="p-4 mx-4 mt-4 m-1">
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
                        <form:textarea path="description" class="form-control" cssErrorClass="form-control is-invalid" rows="4" cssStyle="max-height: 200px;"/>
                        <form:errors path="description" element="p" cssClass="form-error-label"/>
                     </div>
                     <div class="col m-2"> <!--Rating-->
                        <form:label path="score" class="form-label">
                           <spring:message code="review.scoreAssign"/>
                           <span class="required-field">*</span>
                        </form:label>
                        <spring:message code="reviewForm.score.placeholder" var="placeholder"/>
                        <div class="w-100 d-flex justify-content-center">
                           <div class="w-50">
                              <jsp:include page="/WEB-INF/components/starForm.jsp"/>
                           </div>
                        </div>
                        <form:input path="score" type="hidden" class="form-control" cssErrorClass="form-control is-invalid" id="scoreInput"/>
                        <form:errors path="score" element="p" cssClass="form-error-label mt-2"/>
                     </div>
                  </div>
                  <div class="p-0 d-flex justify-content-around">
                     <a href="<c:url value = "/experiences/${categoryName}/${experienceId}"/>">
                        <button class="btn btn-cancel-form px-3 py-2" id="cancelFormButton">
                           <spring:message code="experienceForm.cancel"/>
                        </button>
                     </a>
                     <button type="submit" class="btn btn-submit-form px-3 py-2" id="submitFormButton" form="submitForm">
                        <spring:message code="experienceForm.submit"/>
                     </button>
                  </div>
               </form:form>
            </div>
         </div>
         <%@ include file="../components/footer.jsp" %>
      </div>
      <%@ include file="../components/includes/bottomScripts.jsp" %>
      <script src='<c:url value="/resources/js/ratingScore.js"/>'></script>
      <script src='<c:url value="/resources/js/submitButton.js"/>'></script>
      <script src='<c:url value="/resources/js/cancelButton.js"/>'></script>
      <script src="https://kit.fontawesome.com/5ea815c1d0.js"></script>
   </body>
</html>