<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="card mx-5 my-3 p-4">
   <div class="experience card-title d-flex justify-content-center align-content-center">

      <h1>
         <c:out value="${param.name}"/>
      </h1>
         <jsp:useBean id="favExperienceModels" scope="request" type="java.util.List"/>
         <jsp:include page="/WEB-INF/components/fav.jsp">
            <jsp:param name="favExperienceModels" value="${favExperienceModels}"/>
            <jsp:param name="experienceId" value="${param.id}"/>
            <jsp:param name="path" value="${param.path}"/>
         </jsp:include>
   </div>

   <div class="d-flex flex-wrap justify-content-center align-content-center">
      <div class="p-2" style="width: 600px;">
         <c:choose>
            <c:when test="${param.hasImage}">
               <img class="container-fluid p-0" style="height: fit-content; max-height: 550px;" src="<c:url value='/experiences/${param.id}/image'/>" alt="Imagen"/>
            </c:when>
            <c:otherwise>
               <img class="container-fluid p-0" style="height: fit-content; max-height: 550px;" alt="Imagen ${param.experienceCategoryName}"
                    src="<c:url value="/resources/images/${param.experienceCategoryName}.jpg" />">
            </c:otherwise>
         </c:choose>
      </div>
      <div style="flex:5; min-width: 350px;">
         <div class="row">
            <div class="col-3 p-0"></div>
            <div class="card-body col-7 h-100">
               <div> <!-- Direccion y ciudad -->
                  <h5 class="information-title">
                     <spring:message code="experienceDetail.address"/>
                  </h5>
                  <p class="information-text">
                     <spring:message code="experienceDetail.address.value" arguments="${param.address},${param.city},${param.country}"/>
                  </p>
               </div>

               <div> <!-- Precio -->
                  <h5 class="information-title">
                     <spring:message code="experienceDetail.price"/>
                  </h5>
                  <p class="information-text">
                     <c:choose>
                        <c:when test="${param.price == ''}">
                           <spring:message code="experienceDetail.price.noPrice"/>
                        </c:when>
                        <c:when test="${param.price == '0.0'}">
                           <spring:message code="experienceDetail.price.free"/>
                        </c:when>
                        <c:otherwise>
                           <spring:message code="experienceDetail.price.value" arguments="${param.price}"/>
                        </c:otherwise>
                     </c:choose>
                  </p>
               </div>

               <div> <!-- Descripcion -->
                  <h5 class="information-title">
                     <spring:message code="experienceDetail.description"/>
                  </h5>
                  <p class="information-text" id="experienceDescription">
                     <c:choose>
                        <c:when test="${param.description == ''}">
                           <spring:message code="experienceDetail.noData"/>
                        </c:when>
                        <c:otherwise>
                           <c:out value="${param.description}"/>
                        </c:otherwise>
                     </c:choose>
                  </p>
               </div>

               <div> <!-- URL -->
                  <h5 class="information-title">
                     <spring:message code="experienceDetail.url"/>
                  </h5>
                  <c:choose>
                     <c:when test="${param.siteUrl == ''}">
                        <p class="information-text">
                           <spring:message code="experienceDetail.noData"/>
                        </p>
                     </c:when>
                     <c:otherwise>
                        <a href="<c:url value="${param.siteUrl}"/>">
                           <p class="information-text">
                              <c:out value="${param.siteUrl}"/>
                           </p>
                        </a>
                     </c:otherwise>
                  </c:choose>
               </div>

               <div> <!-- Email de contacto -->
                  <h5 class="information-title">
                     <spring:message code="experienceDetail.email"/>
                  </h5>
                  <p class="information-text">
                     <c:out value="${param.email}"/>
                  </p>
               </div>

               <div class="d-flex"> <!-- Ranking -->
                  <h6 class="information-title">
                     <spring:message code="experienceDetail.review" arguments="${param.reviewCount}"/>
                  </h6>
                  <jsp:include page="/WEB-INF/components/starAvg.jsp">
                     <jsp:param name="avgReview" value="${param.reviewAvg}"/>
                  </jsp:include>
               </div>

               <c:if test="${param.isEditing}">
                  <div class="btn-group w-auto container-fluid p-2 d-flex align-items-end" role="group">
                     <c:choose>
                        <c:when test="${param.observable}">
                           <a href="<c:url value="${param.path}">
                                          <c:param name="setObs" value="${false}"/>
                                       </c:url>">
                              <button type="button" class="btn btn-eye" style="font-size: x-large" id="setFalse">
                                 <i class="bi bi-eye"></i>
                              </button>
                           </a>
                        </c:when>
                        <c:otherwise>
                           <a href="<c:url value="${param.path}">
                                          <c:param name="setObs" value="${true}"/>
                                       </c:url>">
                              <button type="button" class="btn btn-eye" style="font-size: x-large" id="setTrue">
                                 <i class="bi bi-eye-slash"></i>
                              </button>
                           </a>
                        </c:otherwise>
                     </c:choose>
                     <a href="<c:url value="/user/experiences/edit/${param.id}"/>">
                        <button type="button" class="btn btn-pencil" style="font-size: x-large">
                           <i class="bi bi-pencil"></i>
                        </button>
                     </a>
                     <a href="<c:url value="/user/experiences/delete/${param.id}"/>">
                        <button type="button" class="btn btn-trash" style="font-size: x-large">
                           <i class="bi bi-trash"></i>
                        </button>
                     </a>
                  </div>
               </c:if>
            </div>
            <div class="col-2 p-0"></div>
         </div>
      </div>
   </div>
</div>


<script src='<c:url value="/resources/js/expParse.js"/>'></script>
<script src='<c:url value="/resources/js/snackbar.js"/>'></script>
