<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="navbar.favourites"/></title>
      <link href='<c:url value="/resources/css/user_experiences.css"/>' rel="stylesheet">
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <c:choose>
            <c:when test="${experiences.size() == 0}">
               <h2 class="title"><spring:message code="favourite.notExist"/></h2>
            </c:when>
            <c:otherwise>
               <h2 class="title"><spring:message code="navbar.favourites"/></h2>
               <div class="cards container-experiences container-fluid">
                  <c:forEach var="experience" varStatus="myIndex" items="${experiences}">
                     <c:set var = "fav" value = "${false}"/>
                     <c:forEach var="favExperience" items="${favExperienceModels}">
                        <c:if test="${favExperience == experience.experienceId}">
                           <c:set var = "fav"  value = "${true}"/>
                        </c:if>
                     </c:forEach>
                     <c:if test="${fav}">
                        <div class="card card-experience mx-3 my-2 p-0">
                           <div>
                              <a href="<c:url value = "/user/favourites?experience=${experience.experienceId}&set=${false}"/>">
                                 <button type="button" class="btn" id="setFalse">
                                    <i class="fas fa-heart heart-color"></i>
                                 </button>
                              </a>
                              <a class="card-link"
                                 href="<c:url value="/experiences/${experience.categoryName}/${experience.experienceId}"/>">
                                 <jsp:include page="/WEB-INF/views/card_experience.jsp">
                                    <jsp:param name="hasImage" value="${experience.hasImage}"/>
                                    <jsp:param name="categoryName" value="${experience.categoryName}"/>
                                    <jsp:param name="id" value="${experience.experienceId}"/>
                                    <jsp:param name="name" value="${experience.experienceName}"/>
                                    <jsp:param name="description" value="${experience.description}"/>
                                    <jsp:param name="address" value="${experience.address}"/>
                                    <jsp:param name="price" value="${experience.price}"/>
                                    <jsp:param name="myIndex" value="${myIndex.index}"/>
                                 </jsp:include>
                                 <div class="card-body container-fluid p-2">
                                    <jsp:include page="/WEB-INF/views/star_avg.jsp">
                                       <jsp:param name="avgReview" value="${avgReviews[myIndex.index]}"/>
                                    </jsp:include>
                                 </div>
                              </a>
                              <div class="btn-group" role="group">
                                 <a href="<c:url value="/user/experiences/edit/${experience.experienceId}"/>" class="btn-exp">
                                    <button type="button" class="btn btn-circle">
                                       <i class="bi bi-pencil"></i>
                                    </button>
                                 </a>
                                 <a href="<c:url value="/user/experiences/delete/${experience.experienceId}"/>" class="btn-exp">
                                    <button type="button" class="btn btn-circle">
                                       <i class="bi bi-trash"></i>
                                    </button>
                                 </a>
                              </div>
                           </div>
                        </div>
                     </c:if>
                  </c:forEach>
               </div>
            </c:otherwise>
         </c:choose>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
