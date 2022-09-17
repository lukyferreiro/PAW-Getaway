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
   </div>
</div>

