<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="review.delete"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/simpleNavbar.jsp" %>

         <div class="container-fluid p-0 h-100 w-100 d-flex justify-content-center align-items-center">
            <div class="w-100 h-50 modalContainer">
               <div class="row w-100 h-100 m-0 align-items-center justify-content-center">
                  <div class="col-12">
                     <h1 class="text-center title">
                        <spring:message code="review.deleteQuestion"/>
                     </h1>
                  </div>
                  <div class="col-12 px-0 d-flex align-items-center justify-content-center">
                     <c:url value="/user/reviews/delete/${review.reviewId}" var="postPath"/>
                     <form:form modelAttribute="deleteForm" action="${postPath}" id="submitForm" method="post"
                                acceptCharset="UTF-8" enctype="multipart/form-data" cssStyle="margin: 0;">
                        <div class="p-0 m-0 d-flex justify-content-around">
                           <a href="<c:url value = "/user/reviews"/>">
                              <button class="btn btn-cancel-form m-3 px-3 py-2" id="cancelFormButton">
                                 <spring:message code="review.cancelBtn"/>
                              </button>
                           </a>
                           <button type="submit" class="btn btn-submit-form m-3 px-3 py-2" id="submitFormButton" form="submitForm">
                              <spring:message code="review.deleteBtn"/>
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
      <script src='<c:url value="/resources/js/submitButton.js"/>'></script>
      <script src='<c:url value="/resources/js/cancelButton.js"/>'></script>

   </body>
</html>
