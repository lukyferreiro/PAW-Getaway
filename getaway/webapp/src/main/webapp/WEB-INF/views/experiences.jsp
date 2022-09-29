<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="experienceForm.${dbCategoryName}"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <div class="container-fluid p-0 mt-3 d-flex">
            <div class="container-filters container-fluid px-2 py-0 mx-2 my-0 d-flex flex-column justify-content-start align-items-center border-end">
               <p class="filters-title m-0">
                  <spring:message code="filters.title"/>
               </p>
               <%--FILTER--%>
               <c:url value="/experiences/${categoryName}" var="postPath"/>
               <form:form modelAttribute="filterForm" action="${postPath}" cssClass="filter-form" id="submitForm" method="post" acceptCharset="UTF-8">
                  <div>
                     <form:label path="activityCity" class="form-label"><spring:message code="filters.city"/></form:label>
                     <form:select path="activityCity" class="form-select">
                        <option disabled selected value><spring:message code="filters.city.placeholder"/></option>
                        <c:forEach var="city" items="${cities}">
                           <option <c:if test="${city.id == cityId}"> selected </c:if> ><c:out value="${city.name}"/></option>
                        </c:forEach>
                     </form:select>
                     <form:errors path="activityCity" element="p" cssClass="form-error-label"/>
                  </div>

                  <div class="container-slider-price">
                     <form:label path="activityPriceMax" class="form-label"><spring:message code="filters.price.title"/></form:label>
                     <output id="priceRange" name="priceRange" for="customRange">
                        ${maxPrice}
                     </output>
                     <div class="slider-price">
                        <div class="value left">
                           <spring:message code="filters.price.min"/>
                        </div>
                        <div class="slider">
                           <form:input id="customRange" path="activityPriceMax"
                                       type="range" min="0" max="${max}" value="${maxPrice}"
                                       oninput="document.getElementById('priceRange').value = this.value"/>
                        </div>
                        <div class="value right">
                           ${max}
                        </div>
                     </div>
                  </div>

                  <div>
                     <form:label path="score" class="form-label"><spring:message code="review.scoreAssign"/></form:label>
                     <jsp:include page="/WEB-INF/components/star_form.jsp"/>
                     <form:input value="${score}" path="score" type="hidden" class="form-control" cssErrorClass="form-control is-invalid" id="scoreInput"/>
                     <form:errors path="score" element="p" cssClass="form-error-label"/>
                  </div>
               </form:form>

               <button class="btn btn-search px-3 py-2 my-2" type="submit" id="submitFormButton" form="submitForm">
                  <spring:message code="filters.btn.submit"/>
               </button>

               <jsp:include page="/WEB-INF/components/order_dropdown.jsp">
                  <jsp:param name="orderByModels" value="${orderByModels}"/>
                  <jsp:param name="path" value="${path}"/>
               </jsp:include>

               <a href="<c:url value = "/experiences/${categoryName}"/>">
                  <button class="btn btn-clean-filter px-3 py-2 my-2" type="button" id="cleanFilterFormButton" form="submitForm">
                     <spring:message code="filters.btn.clear"/>
                  </button>
               </a>
            </div>

            <div class="container-experiences container-fluid p-0 mx-2 mt-0 mb-3 d-flex flex-column justify-content-center align-content-center">
               <div class="d-flex flex-wrap justify-content-center">
                  <c:forEach var="experience" varStatus="myIndex" items="${experiences}">
                     <jsp:include page="/WEB-INF/components/card_experience.jsp">
                        <jsp:param name="hasImage" value="${experience.hasImage}"/>
                        <jsp:param name="categoryName" value="${experience.categoryName}"/>
                        <jsp:param name="id" value="${experience.experienceId}"/>
                        <jsp:param name="name" value="${experience.experienceName}"/>
                        <jsp:param name="description" value="${experience.description}"/>
                        <jsp:param name="address" value="${experience.address}"/>
                        <jsp:param name="price" value="${experience.price}"/>
                        <jsp:param name="favExperienceModels" value="${favExperienceModels}"/>
                        <jsp:param name="favUrlFalse" value="${path}experience=${experience.experienceId}&set=${false}"/>
                        <jsp:param name="favUrlTrue" value="${path}experience=${experience.experienceId}&set=${true}"/>
                        <jsp:param name="avgReviews" value="${avgReviews[myIndex.index]}"/>
                        <jsp:param name="isEditing" value="${isEditing}"/>
                     </jsp:include>
                  </c:forEach>
               </div>

               <div class="d-flex justify-content-center align-items-center">
                  <ul class="pagination m-0">
                     <%--                     <li class="page-item">--%>
                     <%--                        <a class="page-link" href="--%>
                     <%--                        <c:url value = "/experiences/${categoryName}/">--%>
                     <%--                           <c:param name = "pageNum" value = "1"/>--%>
                     <%--                        </c:url>">--%>
                     <%--                           &lt;%&ndash;                        <spring:message code="home.pagination.first"/>&ndash;%&gt;--%>
                     <%--                        </a>--%>
                     <%--                     </li>--%>


                     <c:forEach var = "i" begin = "1" end = "${totalPages}">
                        <li class="page-item">
                           <a class="page-link ${i == currentPage ? 'font-weight-bold' : ''}" href="
                        <c:url value = "${path}">
                           <c:param name = "pageNum" value = "${i}"/>
<%--                           <c:param name = "category" value = "${param.category}"/>--%>
                        </c:url>">
                              <c:out value="${i}"/>
                           </a>
                        </li>
                     </c:forEach>


                     <%--                     <li class="page-item">--%>
                     <%--                        <a class="page-link" href="--%>
                     <%--                        <c:url value = "/experiences/${categoryName}">--%>
                     <%--                           <c:param name = "pageNum" value = "${currentPage+1}"/>--%>
                     <%--&lt;%&ndash;                           <c:param name = "category" value = "${param.category}"/>&ndash;%&gt;--%>
                     <%--                        </c:url>">--%>
                     <%--                        <spring:message code="experience.pagination.next"/>--%>
                     <%--                        </a>--%>
                     <%--                     </li>--%>
                  </ul>
               </div>
            </div>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
      <script src='<c:url value="/resources/js/submitStarsButton.js"/>'></script>

   </body>
</html>
