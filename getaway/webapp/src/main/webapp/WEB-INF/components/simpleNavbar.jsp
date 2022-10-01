<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="navbar container-fluid p-0 d-flex flex-column">
   <div class="container-fluid px-2 pt-2 d-flex">
      <a href="<c:url value = "/" />" class="logo d-flex">
         <img class="logo-img" src="<c:url value = "/resources/images/getaway-icon.png"/>" alt="Logo">
         <span class="logo-text align-self-center text-uppercase font-weight-bold">
                <spring:message code="pageName"/>
         </span>
      </a>
      <div class="container-navbar-buttons d-flex justify-content-between align-items-center">
         <c:if test="${loggedUser.hasRole('VERIFIED')}">
            <a href="<c:url value = "/create_experience"/>" style="margin-right: 40px;">
               <button type="button" class="btn button-primary">
                  <spring:message code="navbar.createExperience"/>
               </button>
            </a>
         </c:if>
         <c:choose>
            <c:when test="${loggedUser == null}">
               <a href="<c:url value = "/login"/>">
                  <button type="button" class="btn button-primary">
                     <spring:message code="navbar.login"/>
                  </button>
               </a>
            </c:when>
            <c:otherwise>
               <div class="dropdown">
                  <button class="btn button-primary dropdown-toggle d-flex align-items-center" type="button"
                          id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                     <img src="<c:url value="/resources/images/ic_user_white.svg"/>" alt="Imagen perfil" style="width:35px; height:35px;">
                  </button>

                  <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1" style="left: -50px;">
                     <a class="dropdown-item" href="<c:url value="/user/profile"/>">
                        <img src="<c:url value="/resources/images/ic_user.svg"/>" alt="Imagen perfil">
                        <spring:message code="navbar.profile"/>
                     </a>
                     <a class="dropdown-item" href="<c:url value="/user/experiences"/>">
                        <img src="<c:url value="/resources/images/ic_experiences.svg"/>" alt="Imagen perfil">
                        <spring:message code="navbar.experiences"/>
                     </a>
                     <a class="dropdown-item" href="<c:url value="/user/favourites"/>">
                        <img src="<c:url value="/resources/images/ic_fav.svg"/>" alt="Imagen perfil">
                        <spring:message code="navbar.favourites"/>
                     </a>
                     <a class="dropdown-item" href="<c:url value="/user/reviews"/>">
                        <img src="<c:url value="/resources/images/ic_review.svg"/>" alt="Imagen perfil">
                        <spring:message code="navbar.reviews"/>
                     </a>
                     <a class="dropdown-item" href="<c:url value="/logout"/>">
                        <img src="<c:url value="/resources/images/ic_logout.svg"/>" alt="Imagen perfil">
                        <spring:message code="navbar.logout"/>
                     </a>
                  </ul>
               </div>
            </c:otherwise>
         </c:choose>
      </div>
   </div>
</div>

