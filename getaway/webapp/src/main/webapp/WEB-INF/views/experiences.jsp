<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <c:url value="${dbCategoryName}"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <div class="container-fluid overflow-auto h-100 p-0 my-3 d-flex">
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
                     <output id="priceRange" name="priceRange" for="customRange">
                        <spring:message code="filters.price.min"/></output>
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
                     <jsp:include page="/WEB-INF/components/star_form.jsp"/>
                     <form:input path="activityReview" type="hidden" class="form-control" cssErrorClass="form-control is-invalid" id="scoreInput"/>
                     <form:errors path="activityReview" element="p" cssClass="form-error-label"/>
                  </div>
               </form:form>

               <button class="btn btn-search px-3 py-2 my-2" type="submit" id="cityFilterFormButton" form="cityFilterForm">
                  <spring:message code="filters.btn.submit"/>
               </button>

               <jsp:include page="/WEB-INF/components/order_dropdown.jsp">
                  <jsp:param name="path1" value="${path}orderBy=avg(score)&direction=asc"/>
                  <jsp:param name="path2" value="${path}orderBy=avg(score)&direction=desc"/>
                  <jsp:param name="path3" value="${path}orderBy=experienceName&direction=asc"/>
                  <jsp:param name="path4" value="${path}orderBy=experienceName&direction=desc"/>
                  <jsp:param name="path5" value="${path}orderBy=price&direction=desc"/>
                  <jsp:param name="path6" value="${path}orderBy=price&direction=asc"/>
               </jsp:include>

               <a href="<c:url value = "/experiences/${categoryName}"/>">
                  <button class="btn btn-clean-filter px-3 py-2 my-2" type="button" id="cleanFilterFormButton" form="cityFilterForm">
                     <spring:message code="filters.btn.clear"/>
                  </button>
               </a>
            </div>

            <div class="container-experiences container-fluid overflow-auto p-0 mx-2 mt-0 mb-3 h-100 d-flex flex-wrap justify-content-center">
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
                  </jsp:include>
               </c:forEach>
            </div>

            <div>
               <nav class="d-flex justify-content-center align-items-center">
                  <ul class="pagination">
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
               </nav>
            </div>


         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
      <script src='<c:url value="/resources/js/filter.js"/>'></script>
<%--      TODO SE ESTABA USANDO ESTO ??--%>
      <%--      <script src='<c:url value="/resources/js/favExperience.js"/>'></script>--%>
      <%--      <script src="https://kit.fontawesome.com/5ea815c1d0.js"></script>--%>

   </body>
</html>
