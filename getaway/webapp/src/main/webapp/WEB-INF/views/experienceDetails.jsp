<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <c:out value="${experience.experienceName}"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <div class="container-fluid px-5 d-flex justify-content-center align-content-center flex-column">

            <jsp:include page="/WEB-INF/components/cardExperienceDetail.jsp">
               <jsp:param name="name" value="${experience.experienceName}"/>
               <jsp:param name="id" value="${experience.experienceId}"/>
               <jsp:param name="experienceCategoryName" value="${experience.categoryName}"/>
               <jsp:param name="categoryName" value="${categoryName}"/>
               <jsp:param name="address" value="${experience.address}"/>
               <jsp:param name="city" value="${city}"/>
               <jsp:param name="country" value="${country}"/>
               <jsp:param name="price" value="${experience.price.toString()}"/>
               <jsp:param name="description" value="${experience.description}"/>
               <jsp:param name="siteUrl" value="${experience.siteUrl}"/>
               <jsp:param name="email" value="${experience.email}"/>
               <jsp:param name="hasImage" value="${experience.hasImage}"/>
               <jsp:param name="reviewAvg" value="${reviewAvg}"/>
               <jsp:param name="reviewCount" value="${reviewCount}"/>
                <jsp:param name="isEditing" value="${isEditing}"/>
               <jsp:param name="path" value="/experiences/${categoryName}/${experience.experienceId}"/>
            </jsp:include>

            <c:if test="${success}">
               <div id="snackbar"><spring:message code="experienceDetail.success"/></div>
            </c:if>
            <c:if test="${successReview}">
               <div id="snackbar"><spring:message code="reviewDetail.success"/></div>
            </c:if>

            <!-- --------------RESEÑAS-------------- -->
            <div class="mx-5 my-3">
               <div class="d-flex justify-content-between align-content-center">
                  <h2 class="align-self-center">
                     <spring:message code="review.start"/>
                  </h2>
                  <c:if test="${loggedUser.hasRole('VERIFIED')}">
                     <a href="<c:url value = "/experiences/${experience.categoryName}/${experience.experienceId}/create_review"/>">
                        <button type="button" class="btn btn-create-review">
                           <spring:message code="review.createReview"/>
                        </button>
                     </a>
                  </c:if>
               </div>
            </div>

            <div class="d-flex mb-3 flex-column">
               <div class="mx-5 my-2 d-flex flex-wrap">
                  <c:choose>
                     <c:when test="${reviews.size()!=0}">
                        <c:forEach var="review" varStatus="myIndex" items="${reviews}">
                           <div class="pl-5 pr-2 w-50" style="min-width: 400px; min-height: 150px; height: fit-content;">
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
                              </jsp:include>
                           </div>
                        </c:forEach>
                     </c:when>
                     <c:otherwise>
                        <div class="d-flex justify-content-center mb-2" style="font-size: x-large;">
                           <spring:message code="review.noReviews"/>
                        </div>
                     </c:otherwise>
                  </c:choose>
               </div>
               <c:if test="${reviews.size()!=0}">
                  <div class="d-flex justify-content-center align-content-center">
                     <ul class="pagination m-0">
                        <li class="page-item">
                           <a class="page-link "
                              href="<c:url value = "/experiences/${categoryName}/${experience.experienceId}">
                                          <c:param name = "pageNum" value = "1"/>
                                    </c:url>">
                              <spring:message code="pagination.start"/>
                           </a>
                        </li>
                        <c:forEach var="i" begin="${minPage}" end="${maxPage}">
                           <li class="page-item">
                              <a class="page-link ${i == currentPage ? 'current-page-link' : ''}"
                                 href="<c:url value = "/experiences/${categoryName}/${experience.experienceId}">
                                          <c:param name = "pageNum" value = "${i}"/>
                                          </c:url>">
                                 <c:out value="${i}"/>
                              </a>
                           </li>
                        </c:forEach>
                        <li class="page-item">
                           <a class="page-link "
                              href="<c:url value = "/experiences/${categoryName}/${experience.experienceId}">
                                          <c:param name = "pageNum" value = "${totalPages}"/>
                                    </c:url>">
                              <spring:message code="pagination.end"/>
                           </a>
                        </li>
                     </ul>
                  </div>
               </c:if>
            </div>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
