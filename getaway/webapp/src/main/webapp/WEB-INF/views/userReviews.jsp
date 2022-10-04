<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="review.profile.title"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <div class="container-fluid p-0 my-3 d-flex flex-column justify-content-center">
            <c:choose>
               <c:when test="${reviews.size() == 0}">
                  <div class="d-flex justify-content-around align-content-center">
                     <h2 class="title"><spring:message code="review.profile.notExist"/></h2>
                  </div>
               </c:when>
               <c:otherwise>
                  <div class="d-flex justify-content-around align-content-center">
                     <h3 class="title"><spring:message code="review.profile.description"/></h3>
                  </div>
                  <div class="mx-5 my-2 d-flex flex-wrap justify-content-center align-content-center ">
                     <c:forEach var="review" varStatus="myIndex" items="${reviews}">
                        <div style="min-width: 500px; min-height: 150px; height: fit-content;">
                           <jsp:include page="/WEB-INF/components/cardReview.jsp">
                              <jsp:param name="userName" value="${review.userName}"/>
                              <jsp:param name="userSurname" value="${review.userSurname}"/>
                              <jsp:param name="title" value="${review.title}"/>
                              <jsp:param name="description" value="${review.description}"/>
                              <jsp:param name="reviewDate" value="${review.reviewDate}"/>
                              <jsp:param name="score" value="${review.score}"/>
                              <jsp:param name="hasImage" value="${listReviewsHasImages[myIndex.index]}"/>
                              <jsp:param name="userId" value="${reviews[myIndex.index].imgId}"/>
                              <jsp:param name="reviewId" value="${review.reviewId}"/>
                              <jsp:param name="isEditing" value="${isEditing}"/>
                              <jsp:param name="experienceName" value="${listExperiencesOfReviews[myIndex.index].experienceName}"/>
                           </jsp:include>
                        </div>
                     </c:forEach>
                  </div>
               </c:otherwise>
            </c:choose>
         </div>
         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
