<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div>
   <c:choose>
      <c:when test="${param.hasImage == false}">
         <img class="card-img-top container-fluid p-0 mw-100" alt="Imagen ${param.categoryName}"
              src="<c:url value="/resources/images/${param.categoryName}.jpg" />">
      </c:when>
      <c:otherwise>
         <img class="card-img-top container-fluid p-0 mw-100" src="<c:url value='/experiences/${param.id}/image'/>" alt="Imagen"/>
      </c:otherwise>
   </c:choose>

   <div class="card-body container-fluid p-2">
      <h2 class="card-title container-fluid p-0"><c:out value="${param.name}"/></h2>
      <div class="card-text container-fluid p-0">
         <p class="text-truncate"><c:out value="${param.description}"/></p>
         <h5><c:out value="${param.address}"/></h5>
         <h6>
            <c:choose>
               <c:when test="${param.price == null}">
                  <spring:message code="experience.noPrice"/>
               </c:when>
               <c:when test="${param.price == '0'}">
                  <spring:message code="experience.price.free"/>
               </c:when>
               <c:otherwise>
                  <spring:message code="experience.price.symbol"/>
                  <c:out value="${param.price}"/>
                  <spring:message code="experience.price.perPerson"/>
               </c:otherwise>
            </c:choose>
         </h6>
      </div>
   </div>
</div>
