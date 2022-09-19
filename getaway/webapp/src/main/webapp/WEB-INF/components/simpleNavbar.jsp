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
         <c:if test="${!loggedUser}">
            <a href="<c:url value = "/login"/>">
               <button type="button" class="btn btn-header">
                  <spring:message code="navbar.login"/>
               </button>
            </a>
         </c:if>
      </div>
   </div>
</div>

