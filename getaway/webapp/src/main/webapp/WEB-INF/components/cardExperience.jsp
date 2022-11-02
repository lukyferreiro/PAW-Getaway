<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="card card-experience mx-3 my-2 p-0">

      <div class="btn-fav">
         <jsp:include page="/WEB-INF/components/fav.jsp">
            <jsp:param name="isFav" value="${param.isFav}"/>
            <jsp:param name="experienceId" value="${param.id}"/>
            <jsp:param name="path" value="${param.path}"/>
            <jsp:param name="query" value="${param.query}"/>
            <jsp:param name="score" value="${param.score}"/>
            <jsp:param name="cityId" value="${param.cityId}"/>
            <jsp:param name="maxPrice" value="${param.maxPrice}"/>
            <jsp:param name="orderBy" value="${param.orderBy}"/>
            <jsp:param name="filter" value="${param.filter}"/>
            <jsp:param name="search" value="${param.search}"/>
         </jsp:include>
      </div>

   <div class="card-link h-100 d-flex flex-column">
      <div>
         <c:choose>
            <c:when test="${param.hasImage}">
               <img class="card-img-top container-fluid p-0 mw-100" src="<c:url value='/experiences/${param.id}/image'/>" alt="Imagen"/>
            </c:when>
            <c:otherwise>
               <img class="card-img-top container-fluid p-4 mw-100" alt="Imagen ${param.categoryName}"
                    src="<c:url value="/resources/images/${param.categoryName}.svg" />">
            </c:otherwise>
         </c:choose>

         <div class="card-body container-fluid p-2">
            <div class="title-link">
               <a href="<c:url value="/experiences/${param.categoryName}/${param.id}">
                           <c:param name="view" value="${true}"/>
                  </c:url>" <c:if test="${!param.observable}"> class="disabled" </c:if>>
                  <h2 class="experience card-title container-fluid p-0 text-truncate"><c:out value="${param.name}"/></h2>
               </a>
            </div>
            <div class="card-text container-fluid p-0">
               <p class="text-truncate"><c:out value="${param.description}"/></p>
               <h5 class="text-truncate"><c:out value="${param.address}"/></h5>
               <h6>
                  <c:choose>
                     <c:when test="${param.price == ''}">
                        <spring:message code="experience.noPrice"/>
                     </c:when>
                     <c:when test="${param.price == '0.0'}">
                        <spring:message code="experience.price.free"/>
                     </c:when>
                     <c:otherwise>
                        <spring:message code="experience.price.value" arguments="${param.price}"/>
                     </c:otherwise>
                  </c:choose>
               </h6>
            </div>
         </div>
      </div>

      <div class="card-body container-fluid d-flex p-2 mb-1 align-items-end">
         <h5 class="mb-1">
            <spring:message code="experience.reviews" arguments="${param.reviewCount}"/>
         </h5>
         <jsp:include page="/WEB-INF/components/starAvg.jsp">
            <jsp:param name="avgReview" value="${param.avgReviews}"/>
         </jsp:include>
      </div>
      <div class="card-body p-0 d-flex justify-content-center">
         <c:if test="${!param.observable}">
            <h5 class="obs-info align-self-center" style="font-size: small"><spring:message code="experience.notVisible" /></h5>
         </c:if>
      </div>

   </div>
</div>