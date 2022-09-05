<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="navbar p-0 d-flex flex-column">
    <div class="container-header w-100 d-flex">
        <a href="<c:url value = "/" />" class="logo">
            <img class="logo-img" src="<c:url value = "/resources/images/getaway-icon.png"/>" alt="Logo">
<%--            <img class="logo-img" src="./resources/GETAWAY.svg" alt="Logo">--%>
            GETAWAY
        </a>
        <div class="container-header-btn d-flex justify-content-between">
            <a href="<c:url value = "/create_experience"/>" class="link-btn-filter">
                <button type="button" class="btn btn-header">Crear experiencia</button>
            </a>
<%--            <button type="button" class="btn btn-header">Iniciar sesion</button>--%>
        </div>
    </div>


    <div class="container-types w-100 d-flex justify-content-center m-0">
        <a href="<c:url value = "/adventures"/>">
            <button type="button" class="btn btn-filter">
                <img src="<c:url value = "/resources/images/ic_adventure.svg" />" alt="Logo aventura"/>Aventura
            </button>
        </a>
        <a href="<c:url value = "/gastronomy"/>">
            <button type="button" class="btn btn-filter">
                <img src="<c:url value = "/resources/images/ic_food.svg" />" alt="Logo aventura"/>Gastronomia
            </button>
        </a>
        <a href="<c:url value = "/hotels"/>">
            <button type="button" class="btn btn-filter">
                <img src="<c:url value = "/resources/images/ic_hotel.svg" />" alt="Logo aventura"/>Hoteleria
            </button>
        </a>
        <a href="<c:url value = "/relax"/>">
            <button type="button" class="btn btn-filter">
                <img src="<c:url value = "/resources/images/ic_relax.svg" />" alt="Logo aventura"/>Relax
            </button>
        </a>
        <a href="<c:url value = "/night"/>">
            <button type="button" class="btn btn-filter">
                <img src="<c:url value = "/resources/images/ic_nightlife.svg" />" alt="Logo aventura"/>Vida nocturna
            </button>
        </a>
        <a href="<c:url value = "/historic"/>">
            <button type="button" class="btn btn-filter">
                <img src="<c:url value = "/resources/images/ic_museum.svg" />" alt="Logo aventura"/>Historico
            </button>
        </a>
    </div>
</div>

<hr class="separator"/>

