<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
   <head>
      <title><spring:message code="pageName"/> <c:if test="${query != null}">-
         <spring:message code="searchResult.title" arguments="${query}"/></c:if></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>
   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <c:choose>
            <c:when test="${experiences.size() == 0 || query == null}">
               <div class="my-auto mx-5 px-3 d-flex justify-content-center align-content-center">
                  <div class="d-flex justify-content-center align-content-center">
                     <img src="<c:url value="/resources/images/ic_no_search.jpeg"/>" alt="Imagen lupa" style="width: 150px; height:150px; min-width: 150px; min-height: 150px; margin-right: 5px;">
                     <h4 class="d-flex align-self-center">
                        <spring:message code="experience.emptyResult"/>
                     </h4>
                  </div>
               </div>
            </c:when>
            <c:otherwise>
               <div class="container-fluid p-0 my-3 d-flex flex-column justify-content-center align-content-center">
                  <div class="d-flex justify-content-center align-content-center">
                     <div style="margin: 0 auto 0 20px; flex:1;">
                        <jsp:include page="/WEB-INF/components/orderDropdown.jsp">
                           <jsp:param name="orderByModels" value="${orderByModels}"/>
                           <jsp:param name="path" value="${path}"/>
                           <jsp:param name="query" value="${query}"/>
                           <jsp:param name="orderPrev" value="${orderBy}"/>
                        </jsp:include>
                     </div>

<%--                     Cambiar a tamaño global no de página --%>

                     <h3 class="my-2">
                        <spring:message code="searchResult.description" arguments="${totalResults},${query}"/></h3>
                     <div style="margin: 0 20px 0 auto; flex:1;"></div>
                  </div>

                  <div class="d-flex flex-wrap justify-content-center">
                     <c:forEach var="experience" varStatus="myIndex" items="${experiences}">
                        <jsp:include page="/WEB-INF/components/cardExperience.jsp">
                           <jsp:param name="hasImage" value="${experience.experienceImage.image != null}"/>
                           <jsp:param name="categoryName" value="${experience.category.categoryName}"/>
                           <jsp:param name="id" value="${experience.experienceId}"/>
                           <jsp:param name="name" value="${experience.experienceName}"/>
                           <jsp:param name="isFav" value="${experience.isFav}"/>
                           <jsp:param name="description" value="${experience.description}"/>
                           <jsp:param name="address" value="${experience.address}"/>
                           <jsp:param name="price" value="${experience.price}"/>
                           <jsp:param name="path" value="/search_result"/>
                           <jsp:param name="query" value="${query}"/>
                           <jsp:param name="orderBy" value="${orderBy}"/>
                           <jsp:param name="search" value="true"/>
                           <jsp:param name="avgReviews" value="${experience.averageScore}"/>
                           <jsp:param name="reviewCount" value="${experience.reviewCount}"/>
                           <jsp:param name="pageNum" value="${currentPage}"/>
                        </jsp:include>
                     </c:forEach>
                  </div>

                  <div class="mt-auto d-flex justify-content-center align-items-center">
                     <ul class="pagination m-0">
                        <li class="page-item">
                           <a class="page-link "
                              href=" <c:url value="/search_result">
                                           <c:param name="pageNum" value="1"/>
                                           <c:param name="query" value="${query}"/>
                                           <c:param name="orderBy" value="${orderBy}"/> </c:url>">
                              <spring:message code="pagination.start"/>
                           </a>
                        </li>
                        <c:forEach var="i" begin="${minPage}" end="${maxPage}">
                           <li class="page-item">
                              <a class="page-link ${i == currentPage ? 'current-page-link' : ''}"
                                 href=" <c:url value="/search_result">
                                           <c:param name="pageNum" value="${i}"/>
                                           <c:param name="query" value="${query}"/>
                                           <c:param name="orderBy" value="${orderBy}"/> </c:url>">
                                 <c:out value="${i}"/>
                              </a>
                           </li>
                        </c:forEach>
                        <li class="page-item">
                           <a class="page-link "
                              href=" <c:url value="/search_result">
                                           <c:param name="pageNum" value="${totalPages}"/>
                                           <c:param name="query" value="${query}"/>
                                           <c:param name="orderBy" value="${orderBy}"/> </c:url>"
                           >
                              <spring:message code="pagination.end"/>
                           </a>
                        </li>
                     </ul>
                  </div>
               </div>

               </c:otherwise>
            </c:choose>

            <%@ include file="../components/footer.jsp" %>
         </div>
         <%@ include file="../components/includes/bottomScripts.jsp" %>
         <script src='<c:url value="/resources/js/submitStarsButton.js"/>'></script>
   </body>
</html>
