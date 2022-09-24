<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="resetEmailConfirmation.title"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
      <link href='<c:url value="/resources/css/resetRequest.css"/>' rel="stylesheet">
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/simpleNavbar.jsp" %>

         <div class="container-fluid py-4 px-0 d-flex align-items-center">
            <div class="container-lg p-5 mt-5 smallContentContainer">
               <div class="row w-100 h-100 m-0 align-items-center justify-content-center">
                  <div class="col-12">
                     <h1 class="text-center title">
                        <spring:message code="resetEmailConfirmation.sent"/>
                     </h1>
                     <p class="subtitle text-center mb-4">
                        <spring:message code="resetEmailConfirmation.revise"/>
                  </div>
                  <div class="col-12 d-flex align-items-center justify-content-center">
                     <i class="far fa-check-circle fa-7x" id="success"></i>
                  </div>
                  <div class="col-12 px-0 d-flex align-items-center justify-content-center">
                     <a href="<c:url value = "/"/>">
                        <button type="button" class="btn btn-continue">
                           <spring:message code="resetEmailConfirmation.homeBtn"/>
                        </button>
                     </a>
                  </div>
               </div>
            </div>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
