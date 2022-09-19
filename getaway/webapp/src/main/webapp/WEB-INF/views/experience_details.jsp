<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <c:out value="${activity.name}"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
      <link href="<c:url value = "/resources/css/start_rating.css" />" rel="stylesheet">
   </head>

   <body>
      <div class="container-main">
         <jsp:include page="/WEB-INF/components/navbar.jsp">
            <jsp:param name="loggedUser" value="${loggedUser}"/>
         </jsp:include>

         <div class="container-fluid px-5 d-flex justify-content-center align-content-center flex-column">
            <div class="card mx-5 my-3 p-4">
               <div class="card-title d-flex justify-content-center align-content-center">
                  <h1>
                     <c:out value="${activity.name}"/>
                  </h1>
               </div>

               <div class="d-flex flex-wrap justify-content-center align-content-center">
                  <div class="p-2" style="width: 600px;">
                     <c:choose>
                        <c:when test="${activity.hasImage == false}">
                           <img class="container-fluid p-0" style="height: fit-content" alt="Imagen ${activity.categoryName}"
                                src="<c:url value="/resources/images/${activity.categoryName}.jpg" />" >
                        </c:when>
                        <c:otherwise>
                           <img class="container-fluid p-0" style="height: fit-content" src="<c:url value='/${activity.id}/image'/>" alt="Imagen"/>
                        </c:otherwise>
                     </c:choose>
                  </div>
                  <div style="flex:5; min-width: 350px;">
                     <div class="row">
                        <div class="col-3 p-0"></div>
                        <div class="card-body col-7 h-100">
                           <div> <!-- Direccion y ciudad -->
                              <h5 class="information-title">
                                 <spring:message code="experienceDetail.address"/>
                              </h5>
                              <p class="information-text">
                                 <c:out value="${activity.address}"/>, <c:out value="${countryCity}"/>
                              </p>
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
                        <div class="col-2 p-0"></div>
                     </div>
                  </div>
               </div>
            </div>
            <div class="mx-5 my-3">
               <div class="d-flex justify-content-between align-content-center">
                  <h2 class="align-self-center">
                     <spring:message code="review.start"/>
                  </h2>
                  <a href="<c:url value = "/experiences/${activity.categoryName}/${activity.id}/create_review"/>">
                     <button type="button" class="btn btn-create-review">
                        <spring:message code="review.createReview"/>
                     </button>
                  </a>
               </div>
            </div>

            <!-- --------------RESEÑAS-------------- -->
            <div class="mx-5 my-2">
               <c:choose>
                  <c:when test="${reviews.size()!=0}">
                     <c:forEach var="review" items="${reviews}">
                        <div class="card mx-5 my-3 p-4">
                           <div class="card-title d-flex">
                              <div class="col m-2">
                                 <img class="user-img" src="<c:url value = "/resources/images/ic_user.png" />" alt="User"/>
                                 <h5><c:out value="${review.userName}"/> <c:out value="${review.userSurname}"/></h5>
                              </div>
                              <h2 class="col m-2"><c:out value="${review.title}"/></h2>
                           </div>
                           <div class="col card-body p-2 mw-25">
                              <div class="card-text">
                                 <h5 class="text-truncate"><c:out value="${review.description}"/></h5>
                                 <h6><spring:message code="review.date"/> <c:out value="${review.reviewDate}"/></h6>
                                 <h6><spring:message code="review.score"/></h6>
                                 <div class="star-rating">
                                    <c:choose>
                                       <c:when test="${review.score == 1}">
                                          <i class="fas fa-star"></i>
                                          <i class="fas fa-star"></i>
                                          <i class="fas fa-star"></i>
                                          <i class="fas fa-star"></i>
                                          <i class="fas fa-star star-color"></i>
                                       </c:when>
                                       <c:when test="${review.score == 2}">
                                          <i class="fas fa-star"></i>
                                          <i class="fas fa-star"></i>
                                          <i class="fas fa-star"></i>
                                          <i class="fas fa-star star-color"></i>
                                          <i class="fas fa-star star-color"></i>
                                       </c:when>
                                       <c:when test="${review.score == 3}">
                                          <i class="fas fa-star"></i>
                                          <i class="fas fa-star"></i>
                                          <i class="fas fa-star star-color"></i>
                                          <i class="fas fa-star star-color"></i>
                                          <i class="fas fa-star star-color"></i>
                                       </c:when>
                                       <c:when test="${review.score == 4}">
                                          <i class="fas fa-star"></i>
                                          <i class="fas fa-star star-color"></i>
                                          <i class="fas fa-star star-color"></i>
                                          <i class="fas fa-star star-color"></i>
                                          <i class="fas fa-star star-color"></i>
                                       </c:when>
                                       <c:when test="${review.score == 5}">
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
                        </div>
                     </c:forEach>
                  </c:when>
                  <c:otherwise>
                     <div class="d-flex justify-content-center mb-2" style="font-size: x-large;">
                        <spring:message code="review.noReviews"/>
                     </div>
                  </c:otherwise>
               </c:choose>
            </div>

         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
