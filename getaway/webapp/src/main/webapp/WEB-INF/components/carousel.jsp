<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div>

   <jsp:useBean id="listByCategory" scope="request" type="java.util.List"/>
   <jsp:useBean id="avgReviews" scope="request" type="java.util.List"/>

   <c:forEach varStatus="categoryIndex" var="categoryList" items="${listByCategory}">
      <c:if test="${categoryList.size() != 0}">
         <div class="container text-center">
            <h2 class="font-weight-light">${categoryList.get(0).categoryName}</h2>
            <div id="recipeCarousel<c:out value="${categoryIndex.index}"/>" class="carousel slide" data-bs-ride="carousel">
               <div class="carousel-inner">
                  <c:forEach begin="0" step="3" end="${categoryList.size() - 1}" var="index">
                     <div class="carousel-item <c:if test="${index == 0}">active</c:if>">
                        <div class="row">
                           <c:forEach begin="${index}" step="1" end="${categoryList.size()-1 < 2 + index ? categoryList.size() - 1 : 2 + index}" var="experience">
                              <div class="col-md-3">
                                 <jsp:include page="/WEB-INF/components/card_experience.jsp">
                                    <jsp:param name="hasImage" value="${categoryList.get(experience).hasImage}"/>
                                    <jsp:param name="categoryName" value="${categoryList.get(experience).categoryName}"/>
                                    <jsp:param name="id" value="${categoryList.get(experience).experienceId}"/>
                                    <jsp:param name="name" value="${categoryList.get(experience).experienceName}"/>
                                    <jsp:param name="description" value="${categoryList.get(experience).description}"/>
                                    <jsp:param name="address" value="${categoryList.get(experience).address}"/>
                                    <jsp:param name="price" value="${categoryList.get(experience).price}"/>
                                    <jsp:param name="favExperienceModels" value="${favExperienceModels}"/>
                                    <jsp:param name="path" value="/"/>
                                    <jsp:param name="avgReviews" value="${avgReviews[experience]}"/>
                                 </jsp:include>
                              </div>
                           </c:forEach>
                        </div>
                     </div>
                  </c:forEach>
               </div>
               <div class="nav">
                  <a class="carousel-control-prev bg-transparent w-aut" href="#recipeCarousel<c:out value="${categoryIndex.index}"/>" role="button" data-bs-slide="prev">
                     <button class="prev"><i class="fas fa-arrow-left fa-2x"></i></button>
                  </a>
                  <a class="carousel-control-next bg-transparent w-aut" href="#recipeCarousel<c:out value="${categoryIndex.index}"/>" role="button" data-bs-slide="next">
                     <button class="next"><i class="fas fa-arrow-right fa-2x"></i></button>
                  </a>
               </div>
            </div>
         </div>
      </c:if>
   </c:forEach>

   <link href="<c:url value = "/resources/css/carousel.css"/>" rel="stylesheet" type="text/css"/>
   <script src='<c:url value="/resources/js/carousel.js"/>'></script>

</div>