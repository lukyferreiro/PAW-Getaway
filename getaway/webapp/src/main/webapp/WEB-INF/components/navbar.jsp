<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="navbar container-fluid p-0 d-flex flex-column">
   <div class="container-header container-fluid p-2 d-flex">
      <a href="<c:url value = "/" />" class="logo d-flex">
         <img class="logo-img" src="<c:url value = "/resources/images/getaway-icon.png"/>" alt="Logo">
         <span class="logo-text align-self-center text-uppercase font-weight-bold">
                <spring:message code="pageName"/>
            </span>
      </a>
<%--      <a href="<c:url value = "/"/>" class="link-home" >--%>
<%--         <spring:message code="navbar.home"/>--%>
<%--      </a>--%>
      <div class="container-header-btn d-flex justify-content-between">
         <a href="<c:url value = "/create_experience"/>">
            <button type="button" class="btn btn-header">
               <spring:message code="navbar.createExperience"/>
            </button>
         </a>
         <c:choose>
            <c:when test="${!hasSign}">
               <a href="<c:url value = "/login"/>">
                  <button type="button" class="btn btn-header">
                     <spring:message code="navbar.login"/>
                  </button>
               </a>
            </c:when>
            <c:otherwise>
               <div  class="dropdown">
                  <button class="btn btn-header dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                     <spring:message code="navbar.user"/>
                  </button>
                  <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                     <a class="dropdown-item" href="<c:url value = "/"/>">
                        <button type="button" class="btn">
                           <spring:message code="navbar.experiencies"/>
                        </button>
                     </a>
                     <a class="dropdown-item" href="<c:url value = "/logout"/>">
                        <button type="button" class="btn">
                           <spring:message code="navbar.logout"/>
                        </button>
                     </a>
                  </ul>
               </div>


            </c:otherwise>
         </c:choose>
      </div>
   </div>


   <div class="container-types container-fluid p-0 d-flex justify-content-center m-0">
      <a href="<c:url value = "/experiences/Aventura"/>">
         <button type="button" class="btn btn-filter">
            <img src="<c:url value="/resources/images/ic_adventure.svg"/>" alt="Logo aventura"/>
            <spring:message code="navbar.filter.adventure"/>
         </button>
      </a>
      <a href="<c:url value = "/experiences/Gastronomia"/>">
         <button type="button" class="btn btn-filter">
            <img src="<c:url value="/resources/images/ic_food.svg"/>" alt="Logo aventura"/>
            <spring:message code="navbar.filter.gastronomy"/>
         </button>
      </a>
      <a href="<c:url value = "/experiences/Hoteleria"/>">
         <button type="button" class="btn btn-filter">
            <img src="<c:url value="/resources/images/ic_hotel.svg"/>" alt="Logo aventura"/>
            <spring:message code="navbar.filter.hotels"/>
         </button>
      </a>
      <a href="<c:url value = "/experiences/Relax"/>">
         <button type="button" class="btn btn-filter">
            <img src="<c:url value="/resources/images/ic_relax.svg"/>" alt="Logo aventura"/>
            <spring:message code="navbar.filter.relax"/>
         </button>
      </a>
      <a href="<c:url value = "/experiences/Vida_nocturna"/>">
         <button type="button" class="btn btn-filter">
            <img src="<c:url value="/resources/images/ic_nightlife.svg"/>" alt="Logo aventura"/>
            <spring:message code="navbar.filter.night"/>
         </button>
      </a>
      <a href="<c:url value = "/experiences/Historico"/>">
         <button type="button" class="btn btn-filter">
            <img src="<c:url value="/resources/images/ic_museum.svg"/>" alt="Logo aventura"/>
            <spring:message code="navbar.filter.historic"/>
         </button>
      </a>
   </div>
</div>

<hr class="separator"/>

