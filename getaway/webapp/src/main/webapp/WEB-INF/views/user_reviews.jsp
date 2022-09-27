<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="navbar.reviews"/></title>
      <link href='<c:url value="/resources/css/user_experiences.css"/>' rel="stylesheet">
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <c:choose>
            <c:when test="${reviews.size() == 0}">
               <h2 class="title"><spring:message code="review.notExist"/></h2>
            </c:when>
            <c:otherwise>
               <h2 class="title"><spring:message code="navbar.reviews"/></h2>
               <div class="cards container-experiences container-fluid">
                  <c:forEach var="review" items="${reviews}">
                     <div class="card mx-5 my-3 p-4">
                        <jsp:include page="/WEB-INF/views/card_review.jsp">
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

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
