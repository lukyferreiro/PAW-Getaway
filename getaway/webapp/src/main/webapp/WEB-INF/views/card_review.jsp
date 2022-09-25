<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="pl-5 pr-2 w-50" style="min-width: 400px; min-height: 150px; height: fit-content;">
   <div class="card m-2">
      <div class="card-title m-2 d-flex justify-content-between">
         <div class="d-flex">
            <img class="user-img" src="<c:url value = "/resources/images/ic_user.png" />" alt="User"/>
            <div class="d-flex flex-column justify-content-center align-content-center">
               <h5 class="my-1">
                  <c:out value="${param.userName}"/> <c:out value="${param.userSurname}"/>
               </h5>
               <h6 class="my-1" style="font-size: small;">
                  <spring:message code="review.date"/> <c:out value="${param.reviewDate}"/>
               </h6>
            </div>
         </div>
         <div class="my-2 d-flex">
            <div class="star-rating">
               <c:choose>
                  <c:when test="${param.score == '1'}">
                     <i class="fas fa-star"></i>
                     <i class="fas fa-star"></i>
                     <i class="fas fa-star"></i>
                     <i class="fas fa-star"></i>
                     <i class="fas fa-star star-color"></i>
                  </c:when>
                  <c:when test="${param.score == '2'}">
                     <i class="fas fa-star"></i>
                     <i class="fas fa-star"></i>
                     <i class="fas fa-star"></i>
                     <i class="fas fa-star star-color"></i>
                     <i class="fas fa-star star-color"></i>
                  </c:when>
                  <c:when test="${param.score == '3'}">
                     <i class="fas fa-star"></i>
                     <i class="fas fa-star"></i>
                     <i class="fas fa-star star-color"></i>
                     <i class="fas fa-star star-color"></i>
                     <i class="fas fa-star star-color"></i>
                  </c:when>
                  <c:when test="${param.score == '4'}">
                     <i class="fas fa-star"></i>
                     <i class="fas fa-star star-color"></i>
                     <i class="fas fa-star star-color"></i>
                     <i class="fas fa-star star-color"></i>
                     <i class="fas fa-star star-color"></i>
                  </c:when>
                  <c:when test="${param.score == '5'}">
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
      <div class="card-body m-2 p-0">
         <div class="card-text">
            <h2 class="m-0 align-self-center" style="font-size: x-large">
               <c:out value="${param.title}"/>
            </h2>
            <h6 style="font-size: medium">
               <c:out value="${param.description}"/>
            </h6>
         </div>
      </div>
   </div>
</div>
