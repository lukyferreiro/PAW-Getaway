<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <c:url value="${dbCategoryName}"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
      <link href="<c:url value = "/resources/css/experiences.css" />" rel="stylesheet">
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
      <link href="<c:url value = "/resources/css/start_rating.css" />" rel="stylesheet">
   </head>

   <body>
      <div class="container-main">
         <jsp:include page="/WEB-INF/components/navbar.jsp">
            <jsp:param name="loggedUser" value="${loggedUser}"/>
         </jsp:include>

         <div class="container-fluid h-100 p-0 d-flex">
            <div class="container-filters container-fluid px-2 py-0 mx-2 my-0 d-flex flex-column justify-content-start align-items-center border-end">
               <p class="filters-title m-0">
                  <spring:message code="filters.title"/>
               </p>
               <%--FILTER--%>
               <c:url value="/experiences/${categoryName}" var="postPath"/>
               <form:form modelAttribute="filterForm" action="${postPath}" cssClass="filter-form" id="cityFilterForm" method="post" acceptCharset="UTF-8">
                  <div>
                     <form:label path="activityCity" class="form-label"><spring:message code="filters.city"/></form:label>
                     <form:select path="activityCity" class="form-select">
                        <option disabled selected value><spring:message code="filters.city.placeholder"/></option>
                        <c:forEach var="city" items="${cities}">
                           <option><c:out value="${city.name}"/></option>
                        </c:forEach>
                     </form:select>
                     <form:errors path="activityCity" element="p" cssClass="form-error-label"/>
                  </div>

                  <div class="form-check form-switch my-3 d-flex justify-content-start align-items-center">
                     <input id="enablePrice" class="form-check-input checkbox-price" type="checkbox"
                            onchange="let sliderPrice = document.getElementById('customRange');
                                     sliderPrice.disabled = !this.checked;
                                     if (this.checked) sliderPrice.style.cursor = 'pointer';
                                     else sliderPrice.style.cursor = 'default';"/>
                     <label for="enablePrice" class="form-check-label"><spring:message code="filters.price.checkbox"/></label>
                  </div>

                  <div class="container-slider-price">
                     <form:label path="activityPriceMax" class="form-label"><spring:message code="filters.price.title"/></form:label>
                     <output id="priceRange" name="priceRange" for="customRange"><spring:message code="filters.price.min"/></output>
                     <div class="slider-price">
                        <div class="value left">
                           <spring:message code="filters.price.min"/>
                        </div>
                        <div class="slider">
                           <form:input disabled="true" id="customRange" path="activityPriceMax"
                                       type="range" min="0" max="10000" value="0"
                                       oninput="document.getElementById('priceRange').value = this.value"/>
                        </div>
                        <div class="value right">
                           <spring:message code="filters.price.max"/>
                        </div>
                     </div>
                  </div>

                  <div>
                     <form:label path="activityReview" class="form-label"><spring:message code="review.scoreAssign"/></form:label>
                     <jsp:include page="/WEB-INF/views/star_form.jsp"/>
                     <form:input path="activityReview" type="hidden" class="form-control" cssErrorClass="form-control is-invalid" id="scoreInput"/>
                     <form:errors path="activityReview" element="p" cssClass="form-error-label"/>
                  </div>
               </form:form>

               <button class="btn btn-submit-form px-3 py-2" type="submit" id="cityFilterFormButton" form="cityFilterForm">
                  <spring:message code="filters.btn.submit"/>
               </button>
               <a href="<c:url value = "/experiences/${categoryName}"/>">
               <button class="btn btn-clean-filter px-3 py-2 m-2" type="button" id="cleanFilterFormButton" form="cityFilterForm">
                  <spring:message code="filters.btn.clear"/>
               </button>
               </a>
            </div>

            <div class="container-experiences container-fluid overflow-auto p-0 mx-2 mt-0 mb-3 h-100 d-flex flex-wrap justify-content-center">
               <c:forEach var="activity" varStatus="myIndex" items="${activities}">
                  <div class="card card-experience mx-3 my-2 p-0">
                     <a class="card-link" href="<c:url value="${activity.categoryName}/${activity.id}"/>">
                        <jsp:include page="/WEB-INF/views/card_experience.jsp">
                           <jsp:param name="hasImage" value="${activity.hasImage}"/>
                           <jsp:param name="categoryName" value="${activity.categoryName}"/>
                           <jsp:param name="id" value="${activity.id}"/>
                           <jsp:param name="name" value="${activity.name}"/>
                           <jsp:param name="description" value="${activity.description}"/>
                           <jsp:param name="address" value="${activity.address}"/>
                           <jsp:param name="price" value="${activity.price}"/>
                           <jsp:param name="myIndex" value="${myIndex.index}"/>
                        </jsp:include>
                        <div class="card-body container-fluid p-2">
                           <jsp:include page="/WEB-INF/views/star_avg.jsp">
                              <jsp:param name="avgReview" value="${avgReviews[myIndex.index]}"/>
                           </jsp:include>
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
      <script src='<c:url value="/resources/js/ratingScore.js"/>'></script>

   </body>
</html>
