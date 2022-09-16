<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <c:out value="${activity.name}"/></title>
      <link href="<c:url value = "/resources/css/experiences.css" />" rel="stylesheet">
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <div class="card mx-5 my-3 p-4">
            <button id="goBackButton" class="btn btn-leave-experience-details d-flex">
               <img class="go-back-arrow align-self-center" src="<c:url value = "/resources/images/go_back.png"/>" alt="Flecha">
               <span><spring:message code="experienceDetail.goBack"/></span>
            </button>
            <h1 class="card-title d-flex justify-content-center">
               <c:out value="${activity.name}"/>
            </h1>
            <div class="container-header-btn">
               <a href="<c:url value = "/${activity.categoryName}/${activity.id}/create_review"/>">
                  <button type="button" class="btn btn-header">
                     <spring:message code="review.createReview"/>
                  </button>
               </a>
            </div>
            <div class="row">
               <div class="col-auto">
                  <div class="card-body p-2 mw-25">
                     <c:if test="/experiences/images/${activity.id} != null">
                        <img class="rounded img-detail" src="<c:url value="/experiences/images/${activity.id}" />"/>
                     </c:if>
                     <c:if test="${dbCategoryName == 'Aventura'}">
                        <img class="rounded img-detail" src="<c:url value="/resources/images/adventure_image.jpg" />" alt="Imagen Aventura">
                     </c:if>
                     <c:if test="${dbCategoryName == 'Gastronomia'}">
                        <img class="rounded img-detail" src="<c:url value="/resources/images/gastronomy_image.jpg" />" alt="Imagen Gastronomia">
                     </c:if>
                     <c:if test="${dbCategoryName == 'Hoteleria'}">
                        <img class="rounded img-detail" src="<c:url value="/resources/images/hotels_image.jpeg" />" alt="Imagen Hoteles">
                     </c:if>
                     <c:if test="${dbCategoryName == 'Relax'}">
                        <img class="rounded img-detail" src="<c:url value="/resources/images/relax_image.jpg" />" alt="Imagen Relax">
                     </c:if>
                     <c:if test="${dbCategoryName == 'Vida nocturna'}">
                        <img class="rounded img-detail" src="<c:url value="/resources/images/night_image.jpg" />" alt="Imagen Vida Nocturna">
                     </c:if>
                     <c:if test="${dbCategoryName == 'Historico'}">
                        <img class="rounded img-detail" src="<c:url value="/resources/images/historic_image.jpg" />" alt="Imagen Historico">
                     </c:if>
                  </div>
               </div>
               <div class="col">
                  <div class="card-body">
                     <div>
                        <h5 class="information-title">
                           <spring:message code="experienceDetail.address"/>
                        </h5>
                        <p class="information-text"><c:out value="${activity.address}"/></p>
                     </div>
                     <div>
                        <h5 class="information-title">
                           <spring:message code="experienceDetail.price"/>
                        </h5>
                        <p class="information-text">
                           <c:choose>
                              <c:when test="${activity.price == null}">
                                 <spring:message code="experienceDetail.noData"/>
                              </c:when>
                              <c:when test="${activity.price == 0}">
                                 <spring:message code="experienceDetail.price.free"/>
                              </c:when>
                              <c:otherwise>
                                 <spring:message code="experienceDetail.price.symbol"/>
                                 <c:out value="${activity.price}"/>
                                 <spring:message code="experienceDetail.price.perPerson"/>
                              </c:otherwise>
                           </c:choose>
                        </p>
                     </div>
                     <div>
                        <h5 class="information-title">
                           <spring:message code="experienceDetail.description"/>
                        </h5>
                        <p class="information-text">
                           <c:choose>
                              <c:when test="${activity.description == null}">
                                 <spring:message code="experienceDetail.noData"/>
                              </c:when>
                              <c:otherwise>
                                 <c:out value="${activity.description}"/>
                              </c:otherwise>
                           </c:choose>
                        </p>
                     </div>
                     <div>
                        <h5 class="information-title">
                           <spring:message code="experienceDetail.url"/>
                        </h5>
                        <c:choose>
                           <c:when test="${activity.siteUrl == null}">
                              <p class="information-text">
                                 <spring:message code="experienceDetail.noData"/>
                              </p>
                           </c:when>
                           <c:otherwise>
                              <a href="<c:url value="${activity.siteUrl}"/>">
                                 <p class="information-text">
                                    <c:out value="${activity.siteUrl}"/>
                                 </p>
                              </a>
                           </c:otherwise>
                        </c:choose>
                     </div>
                  </div>
               </div>
            </div>
         </div>

         <div>
            <c:forEach var="review" items="${reviews}">
               <div class="card card-review mx-3 my-2 p-0">
                  <img class="user-img" src="<c:url value = "/resources/images/ic_user.png" />" alt="User"> User name</div>
                  <div class="card-body container-fluid p-2">
                        <h2 class="card-title container-fluid p-0"><c:out value="${review.title}"/></h2>
                        <div class="card-text container-fluid p-0">
                           <h5 class="text-truncate"><c:out value="${review.description}"/></h5>
                           <h7> <spring:message code="review.score"/> <c:out value="${review.score}"/></h7>
                           <h7><c:out value="${review.reviewDate}"/></h7>
                        </div>
                  </div>
               </div>
            </c:forEach>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
      <script src='<c:url value="/resources/js/experienceDetailsPage.js"/>'></script>
   </body>
</html>
