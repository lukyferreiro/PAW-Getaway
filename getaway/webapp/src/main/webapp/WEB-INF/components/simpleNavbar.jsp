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
      <%--      <a href="<c:url value = "/"/>" class="link-home" >--%>
      <%--         <spring:message code="navbar.home"/>--%>
      <%--      </a>--%>
      <div class="container-navbar-buttons d-flex justify-content-between">
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
                  <button class="btn button-primary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
<%--                     <c:choose>--%>
<%--                        <c:when test="${loggedUser.hasImage}">--%>
<%--                           <img class="container-fluid p-0" style="height: 60px; width: 60px" src="<c:url value='/user/profileImage/${loggedUser.profileImageId}'/>" alt="Imagen"/>--%>
<%--                        </c:when>--%>
<%--                        <c:otherwise>--%>
<%--                           <img class="container-fluid p-0" style="height: 60px; width: 60px" alt="Imagen ${loggedUser.email}"--%>
<%--                                src="<c:url value="/resources/images/ic_user.png" />"--%>
<%--                        </c:otherwise>--%>
<%--                     </c:choose>--%>
                     <spring:message code="navbar.user"/>
                  </button>

                  <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                     <a class="dropdown-item" href="<c:url value = "/user/profile"/>">
                        <spring:message code="navbar.profile"/>
                     </a>
                     <a class="dropdown-item" href="<c:url value = "/user/experiences"/>">
                        <spring:message code="navbar.experiencies"/>
                     </a>
                     <a class="dropdown-item" href="<c:url value = "/user/favourites"/>">
                        <spring:message code="navbar.favourites"/>
                     </a>
                     <a class="dropdown-item" href="<c:url value = "/user/reviews"/>">
                        <spring:message code="navbar.reviews"/>
                     </a>
                     <a class="dropdown-item" href="<c:url value = "/logout"/>">
                        <spring:message code="navbar.logout"/>
                     </a>
                  </ul>
               </div>
            </c:otherwise>
         </c:choose>
      </div>
   </div>
</div>

