<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
      <link href="<c:url value = "/resources/css/carousel.css"/>" rel="stylesheet" type="text/css"/>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <c:forEach varStatus="categoryIndex" var="categoryList" items="${listByCategory}">
            <c:if test="${categoryList.size() != 0}">
               <div class="container text-center my-3">
                  <h2 class="font-weight-light">${categoryList.get(0).categoryName}</h2>
                  <div class="row mx-auto my-auto justify-content-center">
                     <div id="recipeCarousel<c:out value="${categoryIndex.index}"/>" class="carousel slide" data-bs-ride="carousel">
                        <div class="carousel-inner" role="listbox">
                           <div class="track">
                              <c:forEach var="experience" varStatus="myIndex" items="${categoryList}">
                                 <div class="carousel-item <c:if test="${myIndex.index == 0}">active</c:if>">
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
                                       <jsp:param name="avgReviews" value="${avgReviews[myIndex.index]}"/>
                                       <jsp:param name="reviewCount" value="${listReviewsCount[myIndex.index]}"/>
                                       <jsp:param name="isEditing" value="${isEditing}"/>
                                    </jsp:include>
                                 </div>
                              </c:forEach>
                           </div>
                        </div>

                        <div class="nav">
                           <a class="carousel-control-prev bg-transparent w-aut" href="#recipeCarousel<c:out value="${categoryIndex.index}"/>" role="button" data-bs-slide="prev">
                              <button class="prev"><i class="fas fa-arrow-left fa-2x"></i></button>
                           </a>
                           <a class="carousel-control-next bg-transparent w-aut" href="#recipeCarousel<c:out value="${categoryIndex.index}"/>" role="button" data-bs-slide="next">
                              <button class="next"><i class="fas fa-arrow-right fa-2x"></i></button>
                           </a>
                        </div>

<%--                        <a class="carousel-control-prev bg-transparent w-aut" href="#recipeCarousel<c:out value="${categoryIndex.index}"/>" role="button" data-bs-slide="prev">--%>
<%--                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>--%>
<%--                        </a>--%>
<%--                        <a class="carousel-control-next bg-transparent w-aut" href="#recipeCarousel<c:out value="${categoryIndex.index}"/>" role="button" data-bs-slide="next">--%>
<%--                            <span class="carousel-control-next-icon" aria-hidden="true"></span>--%>
<%--                        </a>--%>
                     </div>
                  </div>
               </div>
            </c:if>

         </c:forEach>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>

   </body>
</html>
