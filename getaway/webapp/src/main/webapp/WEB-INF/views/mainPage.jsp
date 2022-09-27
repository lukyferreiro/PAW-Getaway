<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
      <link href="<c:url value = "/resources/css/experiences.css" />" rel="stylesheet">
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <div class="container-experiences container-fluid overflow-auto p-0 mt-0 mb-3 h-100 d-flex flex-wrap justify-content-center">
            <c:forEach var="experience" varStatus="myIndex" items="${experiences}">
               <div class="card card-experience mx-3 my-2 p-0">
                  <c:if test="${loggedUser != null}">
                     <c:set var = "fav" value = "${false}"/>
                     <c:forEach var="favExperience" items="${favExperienceModels}">
                        <c:if test="${favExperience == experience.experienceId}">
                           <c:set var = "fav"  value = "${true}"/>
                        </c:if>
                     </c:forEach>

                     <c:choose>
                        <c:when test="${fav}">
                           <a href="<c:url value = "/?experience=${experience.experienceId}&set=${false}"/>">
                              <button type="button" class="btn" id="setFalse">
                                 <i class="fas fa-heart heart-color"></i>
                              </button>
                           </a>
                        </c:when>
                        <c:otherwise>
                           <a href="<c:url value = "/?experience=${experience.experienceId}&set=${true}"/>">
                              <button type="button" class="btn" id="setTrue">
                                 <i class="fas fa-heart"></i>
                              </button>
                           </a>
                        </c:otherwise>
                     </c:choose>

                  </c:if>
                  <a class="card-link" href="<c:url value="/experiences/${experience.categoryName}/${experience.experienceId}"/>">
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
               </div>
            </c:forEach>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
