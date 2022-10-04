<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
   <head>
      <title><spring:message code="pageName"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <div class="container-experiences container-fluid p-0 mx-2 mt-0 mb-3 d-flex flex-column justify-content-center align-content-center">
            <c:choose>
               <c:when test="${experiences.size() == 0}">
                  <div class="my-auto mx-5 px-3 d-flex justify-content-center align-content-center">
                     <div class="d-flex justify-content-center align-content-center">
                        <img src="<c:url value="/resources/images/ic_no_search.jpeg"/>" alt="Imagen lupa" style="width: 150px; height:150px; min-width: 150px; min-height: 150px; margin-right: 5px;">
                        <h1 class="d-flex align-self-center">
                           <spring:message code="experience.emptyResult"/>
                        </h1>
                     </div>
                  </div>
               </c:when>
               <c:otherwise>
                  <div>
                     <button class="btn btn-search my-2 dropdown-toggle" type="button" id="dropdownMenuButton1"
                             data-bs-toggle="dropdown" aria-expanded="false">
                        <spring:message code="order.title"/>
                     </button>
                     <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                        <c:forEach var="orderBy" items="${orderByModels}">
                           <a class="dropdown-item" href="<c:url value="/search_result">
                                       <c:param name="query" value="${query}"/>
                                       <c:param name="orderBy" value="${orderBy}"/> </c:url>"
                           >
                              <spring:message code="order.${orderBy.toString()}"/>
                           </a>
                        </c:forEach>
                     </ul>
                  </div>

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
                           <jsp:param name="path" value="/search_result"/>
                           <jsp:param name="avgReviews" value="${avgReviews[myIndex.index]}"/>
                           <jsp:param name="isEditing" value="${isEditing}"/>
                        </jsp:include>
                     </c:forEach>
                  </div>

                  <div class="mt-auto d-flex justify-content-center align-items-center">
                     <ul class="pagination m-0">
                           <%--                     <li class="page-item">--%>
                           <%--                        <a class="page-link" href="--%>
                           <%--                        <c:url value = "/experiences/${categoryName}/">--%>
                           <%--                           <c:param name = "pageNum" value = "1"/>--%>
                           <%--                        </c:url>">--%>
                           <%--                           &lt;%&ndash;                        <spring:message code="home.pagination.first"/>&ndash;%&gt;--%>
                           <%--                        </a>--%>
                           <%--                     </li>--%>

                        <c:forEach var="i" begin="1" end="${totalPages}">
                           <li class="page-item">
                              <a class="page-link ${i == currentPage ? 'current-page-link' : ''}"
                                 href=" <c:url value = "/search_result">
                                           <c:param name = "pageNum" value = "${i}"/>
                                           <c:param name = "query" value="${query}"/>
                                           <c:param name = "orderBy" value = "${orderBy}"/> </c:url>"
                              >
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
               </c:otherwise>
            </c:choose>
         </div>
         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
      <script src='<c:url value="/resources/js/submitStarsButton.js"/>'></script>

   </body>
</html>
