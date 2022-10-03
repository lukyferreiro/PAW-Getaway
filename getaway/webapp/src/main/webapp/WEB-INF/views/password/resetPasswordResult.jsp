<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="resetPasswordResult.title"/></title>
      <%@ include file="../../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../../components/simpleNavbar.jsp" %>

         <div class="container-fluid p-0 h-100 w-100 d-flex justify-content-center align-items-center">
            <div class="container-lg w-100 modalContainer">
               <div class="row w-100 m-0 p-4 align-items-center justify-content-center">
                  <c:choose>
                     <c:when test="${success}">
                        <div class="col-12">
                           <h1 class="text-center title">
                              <spring:message code="resetPasswordResult.successfull"/>
                           </h1>
                           <p class="subtitle text-center mb-4">
                              <spring:message code="resetPasswordResult.successfull.description"/>
                           </p>
                        </div>
                     </c:when>
                     <c:otherwise>
                        <div class="col-12">
                           <h1 class="text-center title">
                              <spring:message code="resetPasswordResult.error"/>
                           </h1>
                           <p class="subtitle text-center mb-4">
                              <spring:message code="resetPasswordResult.error.description"/>
                           </p>
                        </div>
                        <div class="col-12 d-flex align-items-center justify-content-center">
                           <i class="fas fa-exclamation-circle fa-7x" id="error" style="color:red;"></i>
                        </div>
                     </c:otherwise>
                  </c:choose>
                  <div class="col-12 px-0 mt-4 d-flex align-items-center justify-content-center">
                     <a href="<c:url value = "/"/>">
                        <button type="button" class="btn btn-continue">
                           <spring:message code="resetPasswordResult.homeBtn"/>
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

