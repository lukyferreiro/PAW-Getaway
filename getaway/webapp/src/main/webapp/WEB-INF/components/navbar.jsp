<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="navbar container-fluid p-0 d-flex flex-column">
    <div class="container-header container-fluid p-2 d-flex">
        <a href="<c:url value = "/" />" class="logo text-uppercase font-weight-bold">
            <img class="logo-img" src="<c:url value = "/resources/images/getaway-icon.png"/>" alt="Logo">
            <spring:message code="pageName"/>
        </a>
        <div class="container-header-btn d-flex justify-content-between">
            <a href="<c:url value = "/create_experience"/>" class="link-btn-filter">
                <button type="button" class="btn btn-header">
                    <spring:message code="navbar.createExperience"/>
                </button>
            </a>
<%--            <button type="button" class="btn btn-header">--%>
<%--                <spring:message code="navbar.login"/>--%>
<%--            </button>--%>
        </div>
    </div>


    <div class="container-types container-fluid p-0 d-flex justify-content-center m-0">
        <a href="<c:url value = "/adventures"/>">
            <button type="button" class="btn btn-filter">
                <img src="<c:url value="/resources/images/ic_adventure.svg"/>" alt="Logo aventura"/>
                <spring:message code="navbar.filter.adventure"/>
            </button>
        </a>
        <a href="<c:url value = "/gastronomy"/>">
            <button type="button" class="btn btn-filter">
                <img src="<c:url value="/resources/images/ic_food.svg"/>" alt="Logo aventura"/>
                <spring:message code="navbar.filter.gastronomy"/>
            </button>
        </a>
        <a href="<c:url value = "/hotels"/>">
            <button type="button" class="btn btn-filter">
                <img src="<c:url value="/resources/images/ic_hotel.svg"/>" alt="Logo aventura"/>
                <spring:message code="navbar.filter.hotels"/>
            </button>
        </a>
        <a href="<c:url value = "/relax"/>">
            <button type="button" class="btn btn-filter">
                <img src="<c:url value="/resources/images/ic_relax.svg"/>" alt="Logo aventura"/>
                <spring:message code="navbar.filter.relax"/>
            </button>
        </a>
        <a href="<c:url value = "/night"/>">
            <button type="button" class="btn btn-filter">
                <img src="<c:url value="/resources/images/ic_nightlife.svg"/>" alt="Logo aventura"/>
                <spring:message code="navbar.filter.night"/>
            </button>
        </a>
        <a href="<c:url value = "/historic"/>">
            <button type="button" class="btn btn-filter">
                <img src="<c:url value="/resources/images/ic_museum.svg"/>" alt="Logo aventura"/>
                <spring:message code="navbar.filter.historic"/>
            </button>
        </a>
    </div>
</div>

<hr class="separator"/>

