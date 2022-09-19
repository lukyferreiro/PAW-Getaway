<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/></title>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
      <%@ include file="../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <jsp:include page="/WEB-INF/components/navbar.jsp">
            <jsp:param name="loggedUser" value="${loggedUser}"/>
         </jsp:include>

         <c:choose>
            <c:when test="${!loggedUser}">
               <h2><spring:message code="experience.notExist"/></h2>
            </c:when>
            <c:otherwise>
               <h2><spring:message code="experience.myExperiences"/></h2>
               <div class="container-experiences container-fluid overflow-auto p-0 mx-2 mt-0 mb-3 h-100 d-flex flex-wrap justify-content-center">
                  <c:forEach var="activity" items="${activities}">
                     <div class="card card-experience mx-3 my-2 p-0">
                        <a class="card-link"
                           href="<c:url value="/experiences/${activity.categoryName}/${activity.id}"/>">
                           <c:choose>
                              <c:when test="${activity.hasImage == false}">
                                 <c:if test="${activity.categoryId == 1}">
                                    <img class="card-img-top container-fluid p-0 mw-100"
                                         src="<c:url value="/resources/images/adventure_image.jpg" />"
                                         alt="Imagen Aventura">
                                 </c:if>
                                 <c:if test="${activity.categoryId == 2}">
                                    <img class="card-img-top container-fluid p-0 mw-100"
                                         src="<c:url value="/resources/images/gastronomy_image.jpg" />"
                                         alt="Imagen Gastronomia">
                                 </c:if>
                                 <c:if test="${activity.categoryId == 3}">
                                    <img class="card-img-top container-fluid p-0 mw-100"
                                         src="<c:url value="/resources/images/hotels_image.jpeg" />"
                                         alt="Imagen Hoteles">
                                 </c:if>
                                 <c:if test="${activity.categoryId == 4}">
                                    <img class="card-img-top container-fluid p-0 mw-100"
                                         src="<c:url value="/resources/images/relax_image.jpg" />"
                                         alt="Imagen Relax">
                                 </c:if>
                                 <c:if test="${activity.categoryId == 5}">
                                    <img class="card-img-top container-fluid p-0 mw-100"
                                         src="<c:url value="/resources/images/night_image.jpg" />"
                                         alt="Imagen Vida Nocturna">
                                 </c:if>
                                 <c:if test="${activity.categoryId == 6}">
                                    <img class="card-img-top container-fluid p-0 mw-100"
                                         src="<c:url value="/resources/images/historic_image.jpg" />"
                                         alt="Imagen Historico">
                                 </c:if>
                              </c:when>
                              <c:otherwise>
                                 <img class="card-img-top container-fluid p-0 mw-100"
                                      src="<c:url value='/${activity.id}/image'/>" alt="Imagen"/>
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
                        <div class="btn-group" role="group">
                           <a href="<c:url value="/edit/${activity.id}"/>">
                              <button type="button" class="btn btn-circle">
                                 <i class="bi bi-pencil"></i>
                              </button>
                           </a>
                           <a href="<c:url value="/delete/${activity.id}"/>">
                              <button type="button" class="btn btn-circle">
                                    <%--                            <button type="button" class="btn btn-circle" data-toggle="modal" data-target="#deleteModal">--%>
                                 <i class="bi bi-trash"></i>
                              </button>

                           </a>
                        </div>
                           <%--                        <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">--%>
                           <%--                            <div class="modal-dialog" role="document">--%>
                           <%--                                <div class="modal-content">--%>
                           <%--                                    <div class="modal-header">--%>
                           <%--                                        <h5 class="modal-title" id="exampleModalLabel"><spring:message code="experience.delete" /></h5>--%>
                           <%--                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">--%>
                           <%--                                            <span aria-hidden="true">&times;</span>--%>
                           <%--                                        </button>--%>
                           <%--                                    </div>--%>
                           <%--                                    <div class="modal-body">--%>
                           <%--                                        <spring:message code="experience.deleteQuestion"/>--%>
                           <%--                                    </div>--%>
                           <%--                                    <div class="modal-footer">--%>
                           <%--                                        <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="experienceForm.cancel"/></button>--%>
                           <%--                                        <a href="<c:url value="/user/experiences/${activity.id}"/>">--%>
                           <%--                                            <button type="button" class="btn btn-primary"><spring:message code="experience.deleteFinal"/></button>--%>
                           <%--                                        </a>--%>
                           <%--                                    </div>--%>
                           <%--                                </div>--%>
                           <%--                            </div>--%>
                           <%--                        </div>--%>

                     </div>
                  </c:forEach>
               </div>
            </c:otherwise>
         </c:choose>

         <%@ include file="../components/footer.jsp" %>
      </div>

      <%@ include file="../components/includes/bottomScripts.jsp" %>
   </body>
</html>
