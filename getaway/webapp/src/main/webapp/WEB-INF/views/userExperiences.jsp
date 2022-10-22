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

                  <div class="d-flex justify-content-center align-content-center">
                     <h3 class="title m-0"><spring:message code="experience.description"/></h3>
                  </div>
               <div>
                  <table class="table table-bordered table-hover">
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
                              <div class="container-fluid d-flex p-2 mb-1 align-items-end">
                                 <h4 class="container-fluid p-0"><c:out value="${experience.category.categoryName}"/></h4>
                              </div>
                           </td>
                           <td>
                              <div class="container-fluid d-flex p-2 mb-1 align-items-end">
                                 <h5 class="mb-1">
                                    <spring:message code="experience.reviews" arguments="${listReviewsCount[myIndex.index]}"/>
                                 </h5>
                                 <jsp:include page="/WEB-INF/components/starAvg.jsp">
                                    <jsp:param name="avgReview" value="${avgReviews[myIndex.index]}"/>
                                 </jsp:include>
                              </div>
                           </td>
                           <td>
                              <div class="container-fluid d-flex p-2 mb-1 align-items-end">
                                 <h5 class="mb-1">
                                    Vistas
                                       <%--                <spring:message arguments="${param.viewCount}"/>--%>
                                 </h5>
                              </div>
                           </td>
                           <td>
                              <div class="btn-group w-auto container-fluid p-2 d-flex align-items-end" role="group">
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
