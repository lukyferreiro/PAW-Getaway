<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/></title>
      <%@ include file="../components/includes/headers.jsp" %>
      <link href="<c:url value = "/resources/css/experiences.css" />" rel="stylesheet">
   </head>

   <body>
      <div class="container-main">
         <jsp:include page="/WEB-INF/components/navbar.jsp">
            <jsp:param name="hasSign" value="${hasSign}"/>
         </jsp:include>

<%--         <div class="container-mainpage container-fluid p-0 d-flex justify-content-center align-items-center">--%>
<%--            <div class="main-text container-fluid p-0 text-center font-weight-bold">--%>
<%--               <spring:message code="mainPage.description"/>--%>
<%--            </div>--%>
<%--         </div>--%>

         <div class="container-experiences container-fluid overflow-auto p-0 mx-2 mt-0 mb-3 h-100 d-flex flex-wrap justify-content-center">
            <c:forEach var="activity" items="${activities}">
               <div class="card card-experience mx-3 my-2 p-0">
                  <a class="card-link" href="<c:url value="/experiences/${activity.categoryName}/${activity.id}"/>">
                     <c:choose>
                        <c:when test="${activity.hasImage == false}">
                           <c:if test="${activity.categoryId == 1}">
                              <img class="card-img-top container-fluid p-0" src="<c:url value="/resources/images/adventure_image.jpg" />" alt="Imagen Aventura">
                           </c:if>
                           <c:if test="${activity.categoryId == 2}">
                              <img class="card-img-top container-fluid p-0" src="<c:url value="/resources/images/gastronomy_image.jpg" />" alt="Imagen Gastronomia">
                           </c:if>
                           <c:if test="${activity.categoryId == 3}">
                              <img class="card-img-top container-fluid p-0" src="<c:url value="/resources/images/hotels_image.jpeg" />" alt="Imagen Hoteles">
                           </c:if>
                           <c:if test="${activity.categoryId == 4}">
                              <img class="card-img-top container-fluid p-0" src="<c:url value="/resources/images/relax_image.jpg" />" alt="Imagen Relax">
                           </c:if>
                           <c:if test="${activity.categoryId == 5}">
                              <img class="card-img-top container-fluid p-0" src="<c:url value="/resources/images/night_image.jpg" />" alt="Imagen Vida Nocturna">
                           </c:if>
                           <c:if test="${activity.categoryId == 6}">
                              <img class="card-img-top container-fluid p-0" src="<c:url value="/resources/images/historic_image.jpg" />" alt="Imagen Historico">
                           </c:if>
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
