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
                  <div class="mx-5 my-2 d-flex flex-wrap justify-content-around align-content-center">
                     <c:forEach var="review" items="${reviews}">
                        <div class="d-flex flex-column justify-content-center align-content-center">
                           <jsp:include page="/WEB-INF/components/card_review.jsp">
                              <jsp:param name="userName" value="${review.userName}"/>
                              <jsp:param name="userSurname" value="${review.userSurname}"/>
                              <jsp:param name="title" value="${review.title}"/>
                              <jsp:param name="description" value="${review.description}"/>
                              <jsp:param name="reviewDate" value="${review.reviewDate}"/>
                              <jsp:param name="score" value="${review.score}"/>
                           </jsp:include>

                           <div class="btn-group" role="group">
                              <a href="<c:url value="/user/reviews/edit/${review.reviewId}"/>" class="btn-exp">
                                 <button type="button" class="btn btn-circle">
                                    <i class="bi bi-pencil"></i>
                                 </button>
                              </a>
                              <a href="<c:url value="/user/reviews/delete/${review.reviewId}"/>" class="btn-exp">
                                 <button type="button" class="btn btn-circle">
                                    <i class="bi bi-trash"></i>
                                 </button>
                              </a>
                           </div>
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
