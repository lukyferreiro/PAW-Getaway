<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="card card-experience mx-3 my-2 p-0">

   <c:if test="${loggedUser != null}">
      <div class="btn-fav">
         <jsp:useBean id="favExperienceModels" scope="request" type="java.util.List"/>
         <jsp:include page="/WEB-INF/components/fav.jsp">
            <jsp:param name="favExperienceModels" value="${favExperienceModels}"/>
            <jsp:param name="experienceId" value="${param.id}"/>
            <jsp:param name="urlFalse" value="${param.favUrlFalse}"/>
            <jsp:param name="urlTrue" value="${param.favUrlTrue}"/>
         </jsp:include>
      </div>
   </c:if>

   <a class="card-link" href="<c:url value="/experiences/${param.categoryName}/${param.id}"/>">
      <div>
         <c:choose>
            <c:when test="${param.hasImage}">
               <img class="card-img-top container-fluid p-0 mw-100" src="<c:url value='/experiences/${param.id}/image'/>" alt="Imagen"/>
            </c:when>
            <c:otherwise>
               <img class="card-img-top container-fluid p-0 mw-100" alt="Imagen ${param.categoryName}"
                    src="<c:url value="/resources/images/${param.categoryName}.jpg" />">
            </c:otherwise>
         </c:choose>

         <div class="card-body container-fluid p-2">
            <h2 class="card-title container-fluid p-0"><c:out value="${param.name}"/></h2>
            <div class="card-text container-fluid p-0">
               <p class="text-truncate"><c:out value="${param.description}"/></p>
               <h5><c:out value="${param.address}"/></h5>
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

      <div class="card-body container-fluid p-2">
         <jsp:include page="/WEB-INF/components/star_avg.jsp">
            <jsp:param name="avgReview" value="${param.avgReviews}"/>
         </jsp:include>
      </div>
   </a>
</div>