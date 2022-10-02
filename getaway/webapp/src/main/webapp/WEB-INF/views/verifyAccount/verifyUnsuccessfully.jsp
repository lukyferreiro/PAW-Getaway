<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="verifyUnsuccessfully.title"/></title>
      <%@ include file="../../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../../components/simpleNavbar.jsp" %>

         <div class="container-fluid p-0 h-100 w-100 d-flex justify-content-center align-items-center">
            <div class="w-100 h-50 modalContainer">
               <div class="row w-100 h-100 m-0 align-items-center justify-content-center">
                  <div class="col-12">
                     <h1 class="text-center title">
                        <spring:message code="verifyUnsuccessfully.unsuccess"/>
                     </h1>
                     <p class="subtitle text-center mb-4">
                        <spring:message code="verifyUnsuccessfully.unsuccess.description"/>
                     </p>
                  </div>
                  <div class="col-12 px-0 mt-4 d-flex align-items-center justify-content-center">
                     <a href="<c:url value = "/user/verifyAccount/status/resend"/>">
                        <button type="button" class="btn btn-continue">
                           <spring:message code="verifyUnsuccessfully.sentAgain"/>
                        </button>
                     </a>
                  </div>
               </div>
            </div>
         </div>

         <%@ include file="../../components/footer.jsp" %>
      </div>

      <%@ include file="../../components/includes/bottomScripts.jsp" %>
   </body>
</html>

