<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<div class="card m-2" <c:if test="${param.isEditing}"> style="height: 375px;" </c:if> >
   <c:if test="${param.isEditing}">
      <div class="card-title m-2 d-flex justify-content-center align-content-center">
         <h4 style="font-weight: bold; text-decoration: underline; word-break: break-all;">
            <c:out value="${param.experienceName}"/>
         </h4>
      </div>
   </c:if>
   <div class="card-title m-2 d-flex justify-content-between">
      <div class="d-flex">
         <c:choose>
            <c:when test="${param.hasImage}">
               <img class="user-img" src="<c:url value='/user/profileImage/${param.profileImageId}'/>" alt="Imagen" style="margin-right: 8px"/>
            </c:when>
            <c:otherwise>
               <img class="user-img" src="<c:url value="/resources/images/user_default.png" />" alt="Imagen"/>
            </c:otherwise>
         </c:choose>
         <div class="d-flex flex-column justify-content-center align-content-center">
            <h5 class="my-1">
               <spring:message code="review.user" arguments="${param.userName},${param.userSurname}"/>
            </h5>
            <h6 class="my-1" style="font-size: small;">
               <spring:message code="review.date" arguments="${param.reviewDate}"/>
            </h6>
         </div>
      </div>
      <div class="my-2 d-flex">
         <jsp:include page="/WEB-INF/components/starAvg.jsp">
            <jsp:param name="avgReview" value="${param.score}"/>
         </jsp:include>
      </div>
   </div>

   <div class="card-body m-2 p-0">
      <div class="card-text">
         <h2 class="m-0 align-self-center" style="font-size: x-large">
            <c:out value="${param.title}"/>
         </h2>
         <h6 style="font-size: medium" id="reviewDescription">
            <c:out value="${param.description}"/>
         </h6>
         <c:if test="${!param.observable && param.isEditing}">
            <p class="obs-info"><spring:message code="experience.notVisible" /></p>
         </c:if>
      </div>
   </div>

   <c:if test="${param.isEditing}">
      <div class="btn-group card-body container-fluid p-1 d-flex justify-content-center align-items-end" role="group">
         <a href="<c:url value="/user/reviews/edit/${param.reviewId}"/>">
            <button type="button" class="btn btn-pencil" style="font-size: large">
               <i class="bi bi-pencil"></i>
            </button>
         </a>
         <a href="<c:url value="/user/reviews/delete/${param.reviewId}"/>">
            <button type="button" class="btn btn-trash" style="font-size: large">
               <i class="bi bi-trash"></i>
            </button>
         </a>
      </div>
   </c:if>

   <script src='<c:url value="/resources/js/revParse.js"/>'></script>

</div>