<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/></title>
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

         <div class="container-experiences container-fluid overflow-auto p-0 mt-0 mb-3 h-100 d-flex flex-wrap justify-content-center">
            <c:forEach var="activity" varStatus="myIndex" items="${activities}">
               <div class="card card-experience mx-3 my-2 p-0">
                  <a class="card-link" href="<c:url value="/experiences/${activity.categoryName}/${activity.id}"/>">
                     <c:choose>
                        <c:when test="${activity.hasImage == false}">
                           <img class="card-img-top container-fluid p-0" alt="Imagen ${activity.categoryName}"
                                src="<c:url value="/resources/images/${activity.categoryName}.jpg" />">
                        </c:when>
                        <c:otherwise>
                           <img class="card-img-top container-fluid p-0" src="<c:url value='/${activity.id}/image'/>" alt="Imagen"/>
                        </c:otherwise>
                     </c:choose>
                        <%--                                       <img class="card-img-top container-fluid p-0 mw-100" src="<c:url value='/${activity.id}/image'/>"/>--%>

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
                           <jsp:include page="/WEB-INF/views/star_avg.jsp">
                              <jsp:param name="avgReview" value="${avgReviews[myIndex.index]}"/>
                           </jsp:include>
                        </div>
                     </div>
                  </a>
               </div>
            </c:forEach>
         </div>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
