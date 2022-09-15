<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <c:url value="${dbCategoryName}"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
      <link href="<c:url value = "/resources/css/experiences.css" />" rel="stylesheet">
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <div class="container-body container-fluid p-0 d-flex">
            <div class="container-filters container-fluid px-2 py-0 mx-2 my-0 d-flex flex-column justify-content-start align-items-center border-end">
               <p class="font-weight-bold">
                  <spring:message code="filters.title"/>
               </p>
               <%--FILTER--%>
               <c:url value="/${categoryName}" var="postPath"/>
               <form:form modelAttribute="filterForm" action="${postPath}" id="cityFilterForm" method="post" acceptCharset="UTF-8">
                  <div>
                     <form:label path="activityCity" class="form-label"><spring:message code="experienceForm.activityCity"/></form:label>
                     <form:select path="activityCity" class="form-select">
                        <option disabled selected value>¿A donde?</option>
                        <c:forEach var="city" items="${cities}">
                           <option><c:out value="${city.name}"/></option>
                        </c:forEach>
                     </form:select>
                     <form:errors path="activityCity" element="p" cssClass="form-error-label"/>
                  </div>
                  <div class="form-check form-switch">
                     <input id="enablePrice" class="form-check-input" type="checkbox" onchange="document.getElementById('customRange').disabled = !this.checked;"/>
                     <label for="enablePrice" class="form-check-label">Habilitar busqueda por precio</label>
                  </div>
                  <div>
                     <form:label path="activityPriceMax" class="form-label">Precio maximo:</form:label>
                     <output id="priceRange" name="priceRange" for="customRange">0</output>
                     <form:input disabled="true" id="customRange" path="activityPriceMax" type="range" class="range" min="0" max="10000" value="0" oninput="this.previousElementSibling.value = this.value"/>
                  </div>
                  <button class="btn btn-submit-form px-3 py-2" type="submit" id="cityFilterFormButton" form="cityFilterForm">
                     <spring:message code="filters.place.submit"/>
                  </button>
               </form:form>

            </div>

            <div class="container-experiences container-fluid p-0 mx-2 my-0 d-flex flex-wrap justify-content-center">
               <c:forEach var="activity" items="${activities}">
                  <div class="card card-experience mx-3 my-2 p-0">
                     <a class="card-link" href="<c:url value="${activity.categoryName}/${activity.id}"/>">
                        <c:if test="${dbCategoryName == 'Aventura'}">
                           <img class="card-img-top container-fluid p-0 mw-100" src="<c:url value="/resources/images/adventure_image.jpg" />" alt="Imagen Aventura">
                        </c:if>
                        <c:if test="${dbCategoryName == 'Gastronomia'}">
                           <img class="card-img-top container-fluid p-0 mw-100" src="<c:url value="/resources/images/gastronomy_image.jpg" />" alt="Imagen Gastronomia">
                        </c:if>
                        <c:if test="${dbCategoryName == 'Hoteleria'}">
                           <img class="card-img-top container-fluid p-0 mw-100" src="<c:url value="/resources/images/hotels_image.jpeg" />" alt="Imagen Hoteles">
                        </c:if>
                        <c:if test="${dbCategoryName == 'Relax'}">
                           <img class="card-img-top container-fluid p-0 mw-100" src="<c:url value="/resources/images/relax_image.jpg" />" alt="Imagen Relax">
                        </c:if>
                        <c:if test="${dbCategoryName == 'Vida nocturna'}">
                           <img class="card-img-top container-fluid p-0 mw-100" src="<c:url value="/resources/images/night_image.jpg" />" alt="Imagen Vida Nocturna">
                        </c:if>
                        <c:if test="${dbCategoryName == 'Historico'}">
                           <img class="card-img-top container-fluid p-0 mw-100" src="<c:url value="/resources/images/historic_image.jpg" />" alt="Imagen Historico">
                        </c:if>
<%--                        <div class="">--%>
<%--                            <button type="button" class="btn btn-bookmark">--%>
<%--                                <img src="<c:url value="/resources/images/ic_bookmark_white.svg"/>" alt="Guardar"/>--%>
<%--                            </button>--%>
<%--                        </div>--%>
                        <div class="card-body container-fluid p-2">
                           <h2 class="card-title container-fluid p-0"><c:out value="${activity.name}"/></h2>
                           <div class="card-text container-fluid p-0">
                              <p class="text-truncate"><c:out value="${activity.description}"/></p>
                              <h5><c:out value="${activity.address}"/></h5>
                              <h6>
                                 <c:choose>
                                    <c:when test="${activity.price == null}">
                                       <spring:message code="experience.noPrice"/>
                                    </c:when>
                                    <c:when test="${activity.price == 0}">
                                       <spring:message code="experience.price.free"/>
                                    </c:when>
                                    <c:otherwise>
                                       <spring:message code="experience.price.symbol"/>
                                       <c:out value="${activity.price}"/>
                                       <spring:message code="experience.price.perPerson"/>
                                    </c:otherwise>
                                 </c:choose>
                              </h6>
                           </div>
                        </div>
                     </a>
                  </div>
               </c:forEach>
            </div>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
      <script src='<c:url value="/resources/js/filter.js"/>'></script>

   </body>
</html>
