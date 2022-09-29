<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>




<html>
   <head>
      <title><spring:message code="pageName"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

            <div class="container-fluid overflow-auto h-100 p-0 my-3 d-flex">
               <c:forEach var="category" varStatus="myIndex" items="${listByCategory}">
                  <div id="carouselLanding" class="carousel slide" data-ride="carousel">
                     <ol class="carousel-indicators">
                        <c:forEach var = "i" begin = "1" end = "${category.size()}" varStatus="myIndex2">
                           <li data-target="#carouselLanding" data-slide-to="${myIndex2.index}" <c:if test='${myIndex2.index == 0}'>class="active"</c:if>></li>
                        </c:forEach>
                     </ol>
                     <div class="carousel-inner" role="listbox">
                        <c:forEach var="experience" varStatus="myIndex3" items="${category}">
                           <div class="carousel-item <c:if test='${myIndex3.index == 0}'>active</c:if>">
                                 <jsp:include page="/WEB-INF/components/card_experience.jsp">
                                    <jsp:param name="hasImage" value="${experience.hasImage}"/>
                                    <jsp:param name="categoryName" value="${experience.categoryName}"/>
                                    <jsp:param name="id" value="${experience.experienceId}"/>
                                    <jsp:param name="name" value="${experience.experienceName}"/>
                                    <jsp:param name="description" value="${experience.description}"/>
                                    <jsp:param name="address" value="${experience.address}"/>
                                    <jsp:param name="price" value="${experience.price}"/>
                                    <jsp:param name="favExperienceModels" value="${favExperienceModels}"/>
                                    <jsp:param name="favUrlFalse" value="/?experience=${experience.experienceId}&set=${false}"/>
                                    <jsp:param name="favUrlTrue" value="/?experience=${experience.experienceId}&set=${true}"/>
                                    <jsp:param name="avgReviews" value="${avgReviews[myIndex3.index]}"/>
                                 </jsp:include>
                           </div>
                        </c:forEach>
                     </div>
                     <button class="carousel-control-prev" type="button" data-bs-target="#carouselLanding" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                     </button>
                     <button class="carousel-control-next" type="button" data-bs-target="#carouselLanding" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                     </button>
                  </div>
               </c:forEach>
            </div>




         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
