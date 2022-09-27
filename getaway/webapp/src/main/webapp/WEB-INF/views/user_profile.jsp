<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="profile.title"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <div class="container-fluid p-0 h-100 w-100 d-flex justify-content-center align-items-center" style="background-color: rgba(255,195,14,0.26); background-image: url(../../resources/images/background-image.svg)")">
            <div class="container-lg w-100 smallContentContainer d-flex flex-column justify-content-center align-items-center">
               <div class="m-2">
                  <h1> <spring:message code="profile.description"/> </h1>
               </div>
               <div class="m-2" style="max-width: 200px;">
                  <c:choose>
                     <c:when test="${hasImage}">
                        <img class="container-fluid p-0" src="<c:url value='/user/profileImage/${user.profileImageId}'/>" alt="Imagen perfil"/>
                     </c:when>
                     <c:otherwise>
                        <img class="container-fluid p-0" style="height: fit-content" alt="Imagen ${user.email}"
                             src="<c:url value="/resources/images/ic_user.png" />">
                     </c:otherwise>
                  </c:choose>
               </div>
               <div class="m-1 justify-self-center align-self-center">
                  <h3><spring:message code="profile.name" arguments="${user.name}"/></h3>
               </div>
               <div class="m-1 justify-self-center align-self-center">
                  <h3><spring:message code="profile.surname" arguments="${user.surname}"/></h3>
               </div>
               <div class="m-1 justify-self-center align-self-center">
                  <h3><spring:message code="profile.email" arguments="${user.email}"/></h3>
               </div>

               <div class="mb-2">
                  <a href="<c:url value = "/user/profile/edit"/>">
                     <button type="button" class="btn btn-error">
                        <spring:message code="profile.editBtn"/>
                     </button>
                  </a>
               </div>
            </div>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
