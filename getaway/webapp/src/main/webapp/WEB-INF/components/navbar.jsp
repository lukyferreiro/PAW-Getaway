<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="navbar container-fluid p-0 d-flex flex-column">
    <div class="container-fluid px-2 pt-2 d-flex">
        <a href="<c:url value = "/"/>" class="logo d-flex">
            <img class="logo-img" src="<c:url value = "/public/images/getaway-icon.png/images/getaway-icon.png"/>" alt="Logo">
            <span class="logo-text align-self-center text-uppercase font-weight-bold">
                <spring:message code="pageName"/>
         </span>
        </a>
        <div class="container-navbar-buttons d-flex justify-content-between align-items-center">
            <div class="d-flex justify-items-center align-items-center" style="margin-right: 40px;">

                <button class="btn btn-search-navbar p-0" type="submit" form="searchExperienceForm">
                    <img src="<c:url value="/public/images/ic_lupa.svg/images/ic_lupa.svg"/>" alt="Icono lupa">
                </button>
                <spring:message code="navbar.search" var="placeholder"/>
                <c:url value="/search_result" var="searchGetPath"/>
                <form:form modelAttribute="searchForm" action="${searchGetPath}" id="searchExperienceForm" method="get"
                           acceptCharset="UTF-8" cssClass="my-auto">
                    <form:input path="query" type="text" class="form-control" cssErrorClass="form-control is-invalid"
                                placeholder="${placeholder}"/>
                    <form:errors path="query" element="p" cssClass="form-error-label"/>
                </form:form>
            </div>

            <a href="<c:url value="/create_experience"/>" style="margin-right: 40px;">
                <button type="button" class="btn button-primary">
                    <spring:message code="navbar.createExperience"/>
                </button>
            </a>

            <c:choose>
                <c:when test="${loggedUser == null}">
                    <a href="<c:url value="/login"/>">
                        <button type="button" class="btn button-primary">
                            <spring:message code="navbar.login"/>
                        </button>
                    </a>
                </c:when>
                <c:otherwise>
                    <div class="dropdown">
                        <button class="btn button-primary dropdown-toggle d-flex align-items-center" type="button"
                                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                            <img src="<c:url value="/public/images/ic_user_white.svg/images/ic_user_white.svg"/>" alt="Icono usuario"
                                 style="width:35px; height:35px;">
                        </button>

                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1" style="left: -50px;">
                            <a class="dropdown-item" href="<c:url value="/user/profile"/>">
                                <img src="<c:url value="/public/images/ic_user.svg/images/ic_user.svg"/>" alt="Icono perfil">
                                <spring:message code="navbar.profile"/>
                            </a>
                            <c:if test="${loggedUser.hasRole('PROVIDER')}">
                                <a class="dropdown-item" href="<c:url value="/user/experiences"/>">
                                    <img src="<c:url value="/public/images/ic_experiences.svg/images/ic_experiences.svg"/>"
                                         alt="Icono experiencias">
                                    <spring:message code="navbar.experiences"/>
                                </a>
                            </c:if>
                            <a class="dropdown-item" href="<c:url value="/user/favourites"/>">
                                <img src="<c:url value="/public/images/ic_fav.svg/images/ic_fav.svg"/>" alt="Icono favoritos">
                                <spring:message code="navbar.favourites"/>
                            </a>
                            <c:if test="${loggedUser.hasRole('VERIFIED')}">
                                <a class="dropdown-item" href="<c:url value="/user/reviews"/>">
                                    <img src="<c:url value="/public/images/ic_review.svg/images/ic_review.svg"/>" alt="Icono reseñas">
                                    <spring:message code="navbar.reviews"/>
                                </a>
                            </c:if>
                            <a class="dropdown-item" href="<c:url value="/logout"/>">
                                <img src="<c:url value="/public/images/ic_logout.svg/images/ic_logout.svg"/>" alt="Icono cerrar sesion">
                                <spring:message code="navbar.logout"/>
                            </a>
                        </ul>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="container-types container-fluid pb-2 p-0 d-flex justify-content-center m-0">
        <a href="<c:url value="/experiences/Aventura"/>">
            <button type="button"
                    class="btn btn-category <c:if test="${param.categoryName == 'Aventura'}"> isActive </c:if>">
                <img src="<c:url value="/public/images/Aventura.svg/images/Aventura.svg"/>" alt="Logo aventura"/>
                <spring:message code="navbar.filter.adventure"/>
            </button>
        </a>
        <a href="<c:url value="/experiences/Gastronomia"/>">
            <button type="button"
                    class="btn btn-category <c:if test="${param.categoryName == 'Gastronomia'}"> isActive </c:if>">
                <img src="<c:url value="/public/images/Gastronomia.svg/images/Gastronomia.svg"/>" alt="Logo aventura"/>
                <spring:message code="navbar.filter.gastronomy"/>
            </button>
        </a>
        <a href="<c:url value="/experiences/Hoteleria"/>">
            <button type="button"
                    class="btn btn-category <c:if test="${param.categoryName == 'Hoteleria'}"> isActive </c:if>">
                <img src="<c:url value="/public/images/Hoteleria.svg/images/Hoteleria.svg"/>" alt="Logo aventura"/>
                <spring:message code="navbar.filter.hotels"/>
            </button>
        </a>
        <a href="<c:url value="/experiences/Relax"/>">
            <button type="button"
                    class="btn btn-category <c:if test="${param.categoryName == 'Relax'}"> isActive </c:if>">
                <img src="<c:url value="/public/images/Relax.svg/images/Relax.svg"/>" alt="Logo aventura"/>
                <spring:message code="navbar.filter.relax"/>
            </button>
        </a>
        <a href="<c:url value="/experiences/Vida_nocturna"/>">
            <button type="button"
                    class="btn btn-category <c:if test="${param.categoryName == 'Vida_nocturna'}"> isActive </c:if>">
                <img src="<c:url value="/public/images/Vida_nocturna.svg/images/Vida_nocturna.svg"/>" alt="Logo aventura"/>
                <spring:message code="navbar.filter.night"/>
            </button>
        </a>
        <a href="<c:url value="/experiences/Historico"/>">
            <button type="button"
                    class="btn btn-category <c:if test="${param.categoryName == 'Historico'}"> isActive </c:if>">
                <img src="<c:url value="/public/images/Historico.svg/images/Historico.svg"/>" alt="Logo aventura"/>
                <spring:message code="navbar.filter.historic"/>
            </button>
        </a>
    </div>
</div>

<hr class="separator"/>

