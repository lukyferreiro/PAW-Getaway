<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="experience.title"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../components/navbar.jsp" %>

         <div class="container-fluid p-0 my-3 d-flex flex-column justify-content-center">
            <c:choose>
               <c:when test="${!hasExperiences}">
                  <div class="my-auto d-flex justify-content-center align-content-center">
                     <h2 class="title"><spring:message code="experience.notExist"/></h2>
                  </div>
               </c:when>
               <c:otherwise>
                  <c:if test="${param.delete}">
                     <div id="snackbar"><spring:message code="experienceDetail.delete"/></div>
                  </c:if>

                  <%--SEARCH--%>
                  <div class="d-flex align-items-center p-3">
                     <button class="btn btn-search-navbar p-0" type="submit" form="searchExperiencePrivateForm">
                        <img src="<c:url value="/resources/images/ic_lupa.svg"/>" alt="Icono lupa">
                     </button>
                     <spring:message code="navbar.search" var="placeholder"/>
                     <c:url value="/user/experiences" var="searchPrivatePostPath"/>
                     <form:form modelAttribute="searchFormPrivate" action="${searchPrivatePostPath}" id="searchExperiencePrivateForm" method="post"
                                acceptCharset="UTF-8" cssClass="my-auto">
                        <form:input path="query" type="text" class="form-control" cssErrorClass="form-control is-invalid" placeholder="${placeholder}"/>
                        <form:errors path="query" element="p" cssClass="form-error-label"/>
                     </form:form>
                  </div>

                  <div class="d-flex justify-content-center align-content-center">
                     <h3 class="title m-0"><spring:message code="experience.description"/></h3>
                  </div>
               <div class="mt-5 mx-5">

                     <c:choose>
                        <c:when test="${experienceList.size() == 0}">
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
                        <table class="table table-bordered table-hover table-fit">
                           <thead class="table-light">
                              <tr>
                                 <th scope="col"><h4 class="table-title"><spring:message code="experience.title"/></h4></th>
                                 <th scope="col"><h4 class="table-title"><spring:message code="experienceForm.experienceCategory"/></h4></th>
                                 <th scope="col"><h4 class="table-title"><spring:message code="review.scoreAssign"/></h4></th>
                                 <th scope="col"><h4 class="table-title"><spring:message code="experience.viewsAmount"/></h4></th>
                                 <th scope="col"><h4 class="table-title"><spring:message code="experience.actions"/></h4></th>
                              </tr>
                           </thead>
                           <tbody>
                           <c:forEach var="experience" items="${experienceList}" varStatus="myIndex">
                              <tr>
                                 <th scope="row">
                                    <div class="title-link">
                                       <a href="<c:url value="/experiences/${experience.category.categoryName}/${experience.experienceId}"/>">
                                          <h4 class="card-title container-fluid p-0"><c:out value="${experience.experienceName}"/></h4>
                                       </a>
                                    </div>
                                 </th>
                                 <td>

<%--                                    Change to spring messagfe value--%>

                                    <div class="container-fluid d-flex p-2 mb-1 align-items-end">
                                       <h4 class="container-fluid p-0"><c:out value="${experience.category.categoryName}"/></h4>
                                    </div>
                                 </td>
                                 <td>
                                    <div class="container-fluid d-flex p-2 mb-1 align-items-end">
                                       <h5 class="mb-1">
                                          <spring:message code="experience.reviews" arguments="${experience.reviewCount}"/>
                                       </h5>
                                       <jsp:include page="/WEB-INF/components/starAvg.jsp">
                                          <jsp:param name="avgReview" value="${experience.averageScore}"/>
                                       </jsp:include>
                                    </div>
                                 </td>
                                 <td>
                                    <div class="container-fluid d-flex p-2 mb-1 align-items-end">
                                       <h5 class="mb-1">
                                          <c:out value="${experience.views}"/>
                                       </h5>
                                    </div>
                                 </td>
                                 <td>
                                    <div class="btn-group w-auto container-fluid p-2 d-flex align-items-end" role="group">
                                       <c:choose>
                                          <c:when test="${experience.observable}">
                                             <a href="<c:url value="/user/experiences">
                                          <c:param name="pageNum" value="${currentPage}"/>
                                          <c:param name="set" value="${false}"/>
                                          <c:param name="experience" value="${experience.experienceId}"/>
                                       </c:url>">
                                                <button type="button" class="btn btn-eye" style="font-size: x-large" id="setFalse">
                                                   <i class="bi bi-eye"></i>
                                                </button>
                                             </a>
                                          </c:when>
                                          <c:otherwise>
                                             <a href="<c:url value="/user/experiences">
                                          <c:param name="pageNum" value="${currentPage}"/>
                                          <c:param name="set" value="${true}"/>
                                          <c:param name="experience" value="${experience.experienceId}"/>
                                       </c:url>">
                                                <button type="button" class="btn btn-eye" style="font-size: x-large" id="setTrue">
                                                   <i class="bi bi-eye-slash"></i>
                                                </button>
                                             </a>
                                          </c:otherwise>
                                       </c:choose>

                                       <a href="<c:url value="/user/experiences/edit/${experience.experienceId}"/>">
                                          <button type="button" class="btn btn-pencil" style="font-size: x-large">
                                             <i class="bi bi-pencil"></i>
                                          </button>
                                       </a>
                                       <a href="<c:url value="/user/experiences/delete/${experience.experienceId}"/>">
                                          <button type="button" class="btn btn-trash" style="font-size: x-large">
                                             <i class="bi bi-trash"></i>
                                          </button>
                                       </a>
                                    </div>
                                 </td>
                              </tr>
                           </c:forEach>
                           </tbody>

                        </table>
                        </c:otherwise>
                     </c:choose>

                  <div class="mt-auto d-flex justify-content-center align-items-center">
                     <ul class="pagination m-0">
                        <li class="page-item">
                           <a class="page-link "
                              href="<c:url value = "/experiences/${categoryName}">
                                       <c:param name = "pageNum" value = "1"/>
                                       <c:param name = "score" value = "${score}"/>
                                       <c:param name = "cityId" value = "${cityId}"/>
                                       <c:param name = "maxPrice" value = "${maxPrice}"/>
                                       <c:param name = "orderBy" value = "${orderBy}" />
                                 </c:url>">
                              <spring:message code="pagination.start"/>
                           </a>
                        </li>
                        <c:forEach var="i" begin="${minPage}" end="${maxPage}">
                           <li class="page-item">
                              <a class="page-link ${i == currentPage ? 'current-page-link' : ''}"
                                 href="<c:url value = "/user/experiences">
                                       <c:param name = "pageNum" value = "${i}"/>
                                       <c:param name = "orderBy" value = "${orderBy}" />
                                       </c:url>">
                                 <c:out value="${i}"/>
                              </a>
                           </li>
                        </c:forEach>
                        <li class="page-item">
                           <a class="page-link "
                              href="<c:url value = "/user/experiences">
                                       <c:param name = "pageNum" value = "${totalPages}"/>
                                       <c:param name = "orderBy" value = "${orderBy}" />
                                 </c:url>">
                              <spring:message code="pagination.end"/>
                           </a>
                        </li>
                     </ul>
                  </div>

               </div>
               </c:otherwise>
            </c:choose>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <script src='<c:url value="/resources/js/snackbar.js"/>'></script>
      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
