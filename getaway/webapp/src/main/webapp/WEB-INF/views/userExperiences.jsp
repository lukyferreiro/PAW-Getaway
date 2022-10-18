<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="experience.title"/></title>
      <%@ include file="../components/includes/headers.jsp" %>

   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <div class="container-fluid p-0 my-3 d-flex flex-column justify-content-center">
            <c:choose>
               <c:when test="${!hasExperiences}">
                  <div class="my-auto d-flex justify-content-center align-content-center">
                     <h2 class="title"><spring:message code="experience.notExist"/></h2>
                  </div>
               </c:when>
               <c:otherwise>
                  <c:if test="${param.delete}">
                     <div id="snackbar"><spring:message code="experienceDetail.delete"/></div>
                  </c:if>

                  <div class="d-flex justify-content-center align-content-center">
                     <div style="margin: 0 auto 0 20px; flex:1;"></div>
                     <h3 class="title m-0"><spring:message code="experience.description"/></h3>
                     <div style="margin: 0 20px 0 auto; flex:1;"></div>
                  </div>

                  <jsp:include page="/WEB-INF/components/carousel.jsp">
                     <jsp:param name="listByCategory" value="${listByCategory}"/>
                     <jsp:param name="favExperienceModels" value="${favExperienceModels}"/>
                     <jsp:param name="avgReviews" value="${avgReviews}"/>
                     <jsp:param name="listReviewsCount" value="${listReviewsCount}"/>
                     <jsp:param name="isEditing" value="${isEditing}"/>
                     <jsp:param name="path" value="/user/experiences"/>
                  </jsp:include>
               </c:otherwise>
            </c:choose>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <script src='<c:url value="/resources/js/snackbar.js"/>'></script>
      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
