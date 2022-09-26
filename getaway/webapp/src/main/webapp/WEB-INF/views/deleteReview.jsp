<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="experience.deleteQuestion"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
      <link href='<c:url value="/resources/css/delete.css"/>' rel="stylesheet">
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/simpleNavbar.jsp" %>

         <div class="container-fluid py-4 px-0 d-flex align-items-center">
            <div class="container-lg p-5 mt-5 smallContentContainer">
               <div class="row w-100 h-100 m-0 align-items-center justify-content-center">
                  <div class="col-12">
                     <h1 class="text-center title">
                        <spring:message code="review.deleteQuestion"/>
                     </h1>
                  </div>
                  <div class="col-12 px-0 d-flex align-items-center justify-content-center">
                     <c:url value="/user/reviews/delete/${review.reviewId}" var="postPath"/>
                     <form:form modelAttribute="deleteForm" action="${postPath}" id="deleteForm" method="post" acceptCharset="UTF-8" enctype="multipart/form-data">
                        <div class="buttons">
                           <a href="<c:url value = "/user/reviews"/>">
                              <button class="btn btn-cancel-form m-3 px-3 py-2" id="cancelFormButton">
                                 <spring:message code="experienceForm.cancel"/>
                              </button>
                           </a>
                           <button type="submit" class="btn btn-submit-form m-3 px-3 py-2" id="deleteFormButton" form="deleteForm">
                              <spring:message code="experience.delete"/>
                           </button>
                        </div>
                     </form:form>
                  </div>
               </div>
            </div>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
      <script src='<c:url value="/resources/js/delete.js"/>'></script>
      <script src='<c:url value="/resources/js/cancelButton.js"/>'></script>

   </body>
</html>
