<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="navbar">
    <div class="container-header">
        <a href="<c:url value = "/" />" class="logo">
            <img class="logo-img" src="<c:url value = "/resources/images/getaway-icon.png"/>" alt="Logo">
<%--            <img class="logo-img" src="./resources/GETAWAY.svg" alt="Logo">--%>
            GETAWAY
        </a>
        <div class="container-header-btn">
            <a href="<c:url value = "/create_experience"/>" class="link-btn-filter">
                <button type="button" class="btn btn-header">Crear experiencia</button>
            </a>
<%--            <button type="button" class="btn btn-header">Iniciar sesion</button>--%>
        </div>
    </div>


    <div class="container-types">
        <a href="<c:url value = "/adventures"/>" class="link-btn-filter">
            <button type="button" class="btn btn-filter">
                <img src="<c:url value = "/resources/images/ic_adventure.svg" />" alt="Logo aventura"/>Aventura
            </button>
        </a>
        <a href="<c:url value = "/gastronomy"/>" class="link-btn-filter">
            <button type="button" class="btn btn-filter">
                <img src="<c:url value = "/resources/images/ic_food.svg" />" alt="Logo aventura"/>Gastronomia
            </button>
        </a>
        <a href="<c:url value = "/hotels"/>" class="link-btn-filter">
            <button type="button" class="btn btn-filter">
                <img src="<c:url value = "/resources/images/ic_hotel.svg" />" alt="Logo aventura"/>Hoteleria
            </button>
        </a>
        <a href="<c:url value = "/relax"/>" class="link-btn-filter">
            <button type="button" class="btn btn-filter">
                <img src="<c:url value = "/resources/images/ic_relax.svg" />" alt="Logo aventura"/>Relax
            </button>
        </a>
        <a href="<c:url value = "/night"/>" class="link-btn-filter">
            <button type="button" class="btn btn-filter">
                <img src="<c:url value = "/resources/images/ic_nightlife.svg" />" alt="Logo aventura"/>Vida nocturna
            </button>
        </a>
        <a href="<c:url value = "/historic"/>" class="link-btn-filter">
            <button type="button" class="btn btn-filter">
                <img src="<c:url value = "/resources/images/ic_museum.svg" />" alt="Logo aventura"/>Historico
            </button>
        </a>
    </div>
</div>

<hr class="separator"/>

