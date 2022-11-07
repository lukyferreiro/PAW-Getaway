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
                  <c:if test="${param.successReview}">
                     <div id="snackbar"><spring:message code="reviewDetail.success"/></div>
                  </c:if>
                  <c:if test="${param.deleteReview}">
                     <div id="snackbar"><spring:message code="reviewDetail.delete"/></div>
                  </c:if>

                  <div class="d-flex justify-content-around align-content-center">
                     <h3 class="title"><spring:message code="review.profile.description"/></h3>
                  </div>
                  <div class="mx-5 my-2 d-flex flex-wrap justify-content-center align-content-center">
                     <c:forEach var="review" varStatus="myIndex" items="${reviews}">
                        <div style="min-width: 700px; max-width: 700px; height: auto">
                           <jsp:include page="/WEB-INF/components/cardReview.jsp">
                              <jsp:param name="userName" value="${review.user.name}"/>
                              <jsp:param name="userSurname" value="${review.user.surname}"/>
                              <jsp:param name="title" value="${review.title}"/>
                              <jsp:param name="description" value="${review.description}"/>
                              <jsp:param name="reviewDate" value="${review.reviewDate}"/>
                              <jsp:param name="score" value="${review.score}"/>
                              <jsp:param name="hasImage" value="${hasImage}"/>
                              <jsp:param name="profileImageId" value="${profileImageId}"/>
                              <jsp:param name="reviewId" value="${review.reviewId}"/>
                              <jsp:param name="isEditing" value="${isEditing}"/>
                              <jsp:param name="experienceName" value="${review.experience.experienceName}"/>
                              <jsp:param name="observable" value="${review.experience.observable}"/>
                           </jsp:include>
                        </div>
                     </c:forEach>
                  </div>

                  <div class="mt-auto d-flex justify-content-center align-items-center">
                     <ul class="pagination m-0">
                        <li class="page-item">
                           <a class="page-link "
                              href="<c:url value = "/user/reviews">
                                       <c:param name = "pageNum" value = "1"/>
                                 </c:url>">
                              <spring:message code="pagination.start"/>
                           </a>
                        </li>
                        <c:forEach var="i" begin="${minPage}" end="${maxPage}">
                           <li class="page-item">
                              <a class="page-link ${i == currentPage ? 'current-page-link' : ''}"
                                 href="<c:url value = "/user/reviews">
                                       <c:param name = "pageNum" value = "${i}"/>
                                       </c:url>">
                                 <c:out value="${i}"/>
                              </a>
                           </li>
                        </c:forEach>
                        <li class="page-item">
                           <a class="page-link "
                              href="<c:url value = "/user/reviews">
                                       <c:param name = "pageNum" value = "${totalPages}"/>
                                 </c:url>">
                              <spring:message code="pagination.end"/>
                           </a>
                        </li>
                     </ul>
                  </div>

               </c:otherwise>
            </c:choose>
         </div>
         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
      <script src='<c:url value="/resources/js/snackbar.js"/>'></script>
   </body>
</html>
