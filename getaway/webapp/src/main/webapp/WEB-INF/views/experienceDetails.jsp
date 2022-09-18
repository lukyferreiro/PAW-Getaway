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

         <div class="card mx-5 my-3 p-4">
            <button id="goBackButton" class="btn btn-leave-experience-details d-flex">
               <img class="go-back-arrow align-self-center" src="<c:url value = "/resources/images/go_back.png"/>" alt="Flecha">
               <span><spring:message code="experienceDetail.goBack"/></span>
            </button>
            <h1 class="card-title d-flex justify-content-center">
               <c:out value="${activity.name}"/>
            </h1>
<%--            <div>--%>
<%--               <div class="d-inline-block">--%>
<%--                  <p>--%>
<%--                     experience.ranking <img src="<c:url value = "/resources/images/ic_star.svg" />" alt="Icono estrella">--%>
<%--                  </p>--%>
<%--                  </div>--%>
<%--                  <div class="d-inline-block">--%>
<%--                     <a href="#"><c:out value="${activity.siteUrl}"/></a>--%>
<%--                  </div>--%>
<%--            </div>--%>
<%--            <div class="">--%>
<%--               <button type="button" class="btn btn-bookmark">--%>
<%--                  <img src="<c:url value="/resources/images/ic_bookmark_white.svg"/>" alt="Guardar"/>--%>
<%--               </button>--%>
<%--            </div>--%>
            <div class="row">
               <div class="col-auto">
                  <div class="card-body p-2 mw-25">
<%--                     replace with conditional to check if experience has image associated--%>
                    <c:choose>
                        <c:when test="${activity.hasImage == false}">
                            <c:if test="${activity.categoryId == 1}">
                                <img class="card-img-top container-fluid p-0 mw-100" src="<c:url value="/resources/images/adventure_image.jpg" />" alt="Imagen Aventura">
                            </c:if>
                            <c:if test="${activity.categoryId == 2}">
                                <img class="card-img-top container-fluid p-0 mw-100" src="<c:url value="/resources/images/gastronomy_image.jpg" />" alt="Imagen Gastronomia">
                            </c:if>
                            <c:if test="${activity.categoryId == 3}">
                                <img class="card-img-top container-fluid p-0 mw-100" src="<c:url value="/resources/images/hotels_image.jpeg" />" alt="Imagen Hoteles">
                            </c:if>
                            <c:if test="${activity.categoryId == 4}">
                                <img class="card-img-top container-fluid p-0 mw-100" src="<c:url value="/resources/images/relax_image.jpg" />" alt="Imagen Relax">
                            </c:if>
                            <c:if test="${activity.categoryId == 5}">
                                <img class="card-img-top container-fluid p-0 mw-100" src="<c:url value="/resources/images/night_image.jpg" />" alt="Imagen Vida Nocturna">
                            </c:if>
                            <c:if test="${activity.categoryId == 6}">
                                <img class="card-img-top container-fluid p-0 mw-100" src="<c:url value="/resources/images/historic_image.jpg" />" alt="Imagen Historico">
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <img class="card-img-top container-fluid p-0 mw-100" src="<c:url value='/${activity.id}/image'/>"/>
                        </c:otherwise>
                    </c:choose>
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
<%--                     <h5 class="card-title"> <spring:message code="experienceDetail.tags"/> </h5>--%>
<%--                     <p><a href="#">#tag1</a>   <a href="#">#tag2</a></p>--%>
                  </div>
               </div>
            </div>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
      <script src='<c:url value="/resources/js/experienceDetailsPage.js"/>'></script>
   </body>
</html>
