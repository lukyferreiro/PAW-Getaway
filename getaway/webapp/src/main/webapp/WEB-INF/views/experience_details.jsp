<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <c:out value="${activity.name}"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

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
                           <div> <!-- Precio -->
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
                           <div> <!-- Descripcion -->
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
                           <div> <!-- URL -->
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
                           <!-- TODO -->
<%--                           <div> <!-- Email de contacto -->--%>
<%--                              --%>
<%--                           </div>--%>
                           <jsp:include page="/WEB-INF/views/star_avg.jsp">
                              <jsp:param name="avgReview" value="${reviewAvg}"/>
                           </jsp:include>
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
            <div class="mx-5 my-2 d-flex flex-wrap">
               <c:choose>
                  <c:when test="${reviews.size()!=0}">
                     <c:forEach var="review" items="${reviews}">
                        <jsp:include page="/WEB-INF/views/card_review.jsp">
                           <jsp:param name="userName" value="${review.userName}"/>
                           <jsp:param name="userSurname" value="${review.userSurname}"/>
                           <jsp:param name="title" value="${review.title}"/>
                           <jsp:param name="description" value="${review.description}"/>
                           <jsp:param name="reviewDate" value="${review.reviewDate}"/>
                           <jsp:param name="score" value="${review.score}"/>
                        </jsp:include>
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
