<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="verify.account"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/simpleNavbar.jsp" %>

         <div class="container-fluid p-0 h-100 d-flex justify-content-center align-items-center">
            <div class="container-fluid p-0 d-flex flex-column justify-content-center align-items-center" style="width: 60%">
               <h1 class="font-weight-bold text-center" style="font-size: 5vh;">
                  <spring:message code="verify.account"/>
               </h1>
               <button type="button" class="btn btn-error" onclick="history.back()">
                  <spring:message code="goBack"/>
               </button>
            </div>
         </div>
      </div>
      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>

