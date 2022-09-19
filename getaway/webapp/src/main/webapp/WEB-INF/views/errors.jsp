<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> -
         <c:choose>
            <c:when test="${code == 403}">
               <spring:message code="forbidden.title"/>
            </c:when>
            <c:when test="${code < 500}">
               <spring:message code="pageNotFound.title"/>
            </c:when>
            <c:otherwise>
               <spring:message code="serverError.title"/>
            </c:otherwise>
         </c:choose>
      </title>
      <%@ include file="../components/includes/headers.jsp" %>
      <link href="<c:url value = "/resources/css/error.css" />" rel="stylesheet">
   </head>

   <body>
      <div class="container-main">
         <jsp:include page="/WEB-INF/components/navbar.jsp">
            <jsp:param name="loggedUser" value="${loggedUser}"/>
         </jsp:include>

         <div class="container-fluid p-0 h-100 d-flex justify-content-center align-items-center">
            <div class="container-fluid p-0 d-flex flex-column justify-content-center align-items-center" style="width: 60%">
               <h1 class="font-weight-bold m-0" style="font-size: 15vh;">
                  <c:out value="${code}"/>
               </h1>
               <h1 class="font-weight-bold text-center" style="font-size: 5vh;">
                  <c:choose>
                     <c:when test="${code == 403}">
                        <spring:message code="forbidden.description"/>
                     </c:when>
                     <c:when test="${code < 500}">
                        <spring:message code="pageNotFound.description"/>
                     </c:when>
                     <c:otherwise>
                        <spring:message code="serverError.description"/>
                     </c:otherwise>
                  </c:choose>
               </h1>
               <h4 class="text-center">
                  <span style="color:red;"><spring:message code="error.whoops"/></span>
                  <c:out value="${errors}"/>
               </h4>
               <a href="<c:url value='/'/>">
                  <button type="button" class="btn btn-error">
                     <spring:message code="error.btn"/>
                  </button>
               </a>
            </div>
         </div>
         <%@ include file="../components/footer.jsp" %>
      </div>
      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>

