<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="verifySent.title"/></title>
      <%@ include file="../../components/includes/headers.jsp" %>

   </head>

   <body>
      <div class="container-main">
         <%@ include file="../../components/simpleNavbar.jsp" %>

         <div class="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">
            <div class="w-100 modalContainer">
               <div class="row w-100 h-100 py-5 px-3 m-0 align-items-center justify-content-center">
                  <div class="col-12">
                     <h1 class="text-center title">
                        <spring:message code="verifySent.sent"/>
                     </h1>
                     <p class="subtitle text-center mb-4">
                        <spring:message code="verifySent.revise"/>
                     </p>
                  </div>
                  <div class="col-12 px-0 mt-2 d-flex align-items-center justify-content-center">
                     <a href="<c:url value = "/"/>">
                        <button type="button" class="btn btn-continue">
                           <spring:message code="verifySent.homeBtn"/>
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

