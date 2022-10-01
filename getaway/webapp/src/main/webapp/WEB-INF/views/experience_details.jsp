<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <c:out value="${experience.experienceName}"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <div class="container-fluid px-5 d-flex justify-content-center align-content-center flex-column">

            <jsp:include page="/WEB-INF/components/card_experienceDetail.jsp">
               <jsp:param name="name" value="${experience.experienceName}"/>
               <jsp:param name="id" value="${experience.experienceId}"/>
               <jsp:param name="experienceCategoryName" value="${experience.categoryName}"/>
               <jsp:param name="categoryName" value="${categoryName}"/>
               <jsp:param name="address" value="${experience.address}"/>
               <jsp:param name="city" value="${city}"/>
               <jsp:param name="country" value="${country}"/>
               <jsp:param name="price" value="${experience.price}"/>
               <jsp:param name="description" value="${experience.description}"/>
               <jsp:param name="siteUrl" value="${experience.siteUrl}"/>
               <jsp:param name="email" value="${experience.email}"/>
               <jsp:param name="hasImage" value="${experience.hasImage}"/>
               <jsp:param name="reviewAvg" value="${reviewAvg}"/>
               <jsp:param name="reviewCount" value="${reviewCount}"/>
            </jsp:include>

            <!-- --------------RESEÑAS-------------- -->
            <div class="mx-5 my-3">
               <div class="d-flex justify-content-between align-content-center">
                  <h2 class="align-self-center">
                     <spring:message code="review.start"/>
                  </h2>
                  <a href="<c:url value = "/experiences/${experience.categoryName}/${experience.experienceId}/create_review"/>">
                     <button type="button" class="btn btn-create-review">
                        <spring:message code="review.createReview"/>
                     </button>
                  </a>
               </div>
            </div>

            <div class="mx-5 my-2 d-flex flex-wrap">
               <c:choose>
                  <c:when test="${reviews.size()!=0}">
                     <c:forEach var="review" varStatus="myIndex" items="${reviews}">
                        <jsp:include page="/WEB-INF/components/card_review.jsp">
                           <jsp:param name="userName" value="${review.userName}"/>
                           <jsp:param name="userSurname" value="${review.userSurname}"/>
                           <jsp:param name="title" value="${review.title}"/>
                           <jsp:param name="description" value="${review.description}"/>
                           <jsp:param name="reviewDate" value="${review.reviewDate}"/>
                           <jsp:param name="score" value="${review.score}"/>
                           <jsp:param name="hasImage" value="${listReviewsHasImages[myIndex.index]}"/>
                           <jsp:param name="userId" value="${reviews[myIndex.index].imgId}"/>
                        </jsp:include>
                     </c:forEach>
                  </c:when>
                  <c:otherwise>
                     <div class="d-flex justify-content-center mb-2" style="font-size: x-large;">
                        <spring:message code="review.noReviews"/>
                     </div>
                  </c:otherwise>
               </c:choose>
            </div>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
