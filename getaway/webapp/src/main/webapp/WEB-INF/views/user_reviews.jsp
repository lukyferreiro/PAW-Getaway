<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="navbar.reviews"/></title>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
      <link href="<c:url value = "/resources/css/start_rating.css" />" rel="stylesheet">
      <link href='<c:url value="/resources/css/user_experiences.css"/>' rel="stylesheet">
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <jsp:include page="/WEB-INF/components/navbar.jsp">
            <jsp:param name="loggedUser" value="${loggedUser}"/>
         </jsp:include>

         <c:choose>
            <c:when test="${reviews.size() == 0}">
               <h2 class="title"><spring:message code="review.notExist"/></h2>
            </c:when>
            <c:otherwise>
               <h2 class="title"><spring:message code="navbar.reviews"/></h2>
               <div class="cards container-experiences container-fluid">
                  <c:forEach var="review" items="${reviews}">
                     <div class="card mx-5 my-3 p-4">
                        <div class="card-title d-flex">
                           <div class="col m-2">
                              <img class="user-img" src="<c:url value = "/resources/images/ic_user.png" />" alt="User"/>
                              <h5><c:out value="${review.userName}"/> <c:out value="${review.userSurname}"/></h5>
                           </div>
                           <h2 class="col m-2"><c:out value="${review.title}"/></h2>
                        </div>
                        <div class="col card-body p-2 mw-25">
                           <div class="card-text">
                              <h5 class="text-truncate"><c:out value="${review.description}"/></h5>
                              <h6><spring:message code="review.date"/> <c:out value="${review.reviewDate}"/></h6>
                              <h6><spring:message code="review.score"/></h6>
                              <div class="star-rating">
                                 <c:choose>
                                    <c:when test="${review.score == 1}">
                                       <i class="fas fa-star"></i>
                                       <i class="fas fa-star"></i>
                                       <i class="fas fa-star"></i>
                                       <i class="fas fa-star"></i>
                                       <i class="fas fa-star star-color"></i>
                                    </c:when>
                                    <c:when test="${review.score == 2}">
                                       <i class="fas fa-star"></i>
                                       <i class="fas fa-star"></i>
                                       <i class="fas fa-star"></i>
                                       <i class="fas fa-star star-color"></i>
                                       <i class="fas fa-star star-color"></i>
                                    </c:when>
                                    <c:when test="${review.score == 3}">
                                       <i class="fas fa-star"></i>
                                       <i class="fas fa-star"></i>
                                       <i class="fas fa-star star-color"></i>
                                       <i class="fas fa-star star-color"></i>
                                       <i class="fas fa-star star-color"></i>
                                    </c:when>
                                    <c:when test="${review.score == 4}">
                                       <i class="fas fa-star"></i>
                                       <i class="fas fa-star star-color"></i>
                                       <i class="fas fa-star star-color"></i>
                                       <i class="fas fa-star star-color"></i>
                                       <i class="fas fa-star star-color"></i>
                                    </c:when>
                                    <c:when test="${review.score == 5}">
                                       <i class="fas fa-star star-color"></i>
                                       <i class="fas fa-star star-color"></i>
                                       <i class="fas fa-star star-color"></i>
                                       <i class="fas fa-star star-color"></i>
                                       <i class="fas fa-star star-color"></i>
                                    </c:when>
                                 </c:choose>
                              </div>
                           </div>
                        </div>

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
