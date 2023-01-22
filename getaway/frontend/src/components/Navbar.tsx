import {useTranslation} from "react-i18next"
import "../common/i18n/index"
import { Link, useLocation, useNavigate } from 'react-router-dom'
import Category from "../types/Category"
import "../styles/navbar.css"

export default function Navbar() {

    const {t} = useTranslation()
    let navigate = useNavigate()
    let location = useLocation()
    const pathname = location?.pathname

    //TODO obtenerlas de un llamado a la API ??
    const categories: Category[] = [
        { path: '/aventura', name: 'Aventura' },
        { path: '/gastronomia', name: 'Gastronomia' },
        { path: '/relax', name: 'Relax' },
        { path: '/historico', name: 'Historico' },
        { path: '/vidaNocturna', name: 'Vida nocturna' },
        //TODO,
    ]

    return (

        <div className="navbar container-fluid p-0 d-flex flex-column">
            <div className="container-fluid px-2 pt-2 d-flex">
                <Link to="/" className="logo d-flex">
                    <img className="logo-img" src="public/images/getaway-icon.png" alt="Logo"/>
                    <span className="logo-text align-self-center text-uppercase font-weight-bold">
                        {t('PageName')}
                    </span>
                </Link>
                <div className="container-navbar-buttons d-flex justify-content-between align-items-center">
                    {/*TODO este div tiene style="margin-right: 40px;*/}
                    <div className={`d-flex justify-items-center align-items-center`} >
                        <button className="btn btn-search-navbar p-0" type="submit" form="searchExperienceForm">
                            <img src="public/images/ic_lupa.svg" alt="Icono lupa"/>
                        </button>
                        Buscar
                        {/*<spring:message code="navbar.search" var="placeholder"/>*/}
                        {/*<c:url value="/search_result" var="searchGetPath"/>*/}
                        {/*<form:form modelAttribute="searchForm" action="${searchGetPath}" id="searchExperienceForm" method="get"*/}
                        {/*           acceptCharset="UTF-8" cssClass="my-auto">*/}
                        {/*    <form:input path="query" type="text" className="form-control" cssErrorClass="form-control is-invalid"*/}
                        {/*                placeholder="${placeholder}"/>*/}
                        {/*    <form:errors path="query" element="p" cssClass="form-error-label"/>*/}
                        {/*</form:form>*/}
                    </div>

                    {/*TODO este a tiene style="margin-right: 40px;*/}
                    <Link to="/createExperience">
                        <button type="button" className="btn button-primary">
                            {t('Navbar.CreateExperience')}
                        </button>
                    </Link>

                    {/*<c:choose>*/}
                    {/*    <c:when test="${loggedUser == null}">*/}
                    {/*        <a href="<c:url value="/login"/>">*/}
                    {/*            <button type="button" className="btn button-primary">*/}
                    {/*                <spring:message code="navbar.login"/>*/}
                    {/*            </button>*/}
                    {/*        </a>*/}
                    {/*    </c:when>*/}
                    {/*    <c:otherwise>*/}
                    {/*        <div className="dropdown">*/}
                    {/*            <button className="btn button-primary dropdown-toggle d-flex align-items-center" type="button"*/}
                    {/*                    id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">*/}
                    {/*                <img src="<c:url value="/public/images/ic_user_white.svg/images/ic_user_white.svg"/>" alt="Icono usuario"*/}
                    {/*                     style="width:35px; height:35px;">*/}
                    {/*            </button>*/}

                    {/*            <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1" style="left: -50px;">*/}
                    {/*                <a className="dropdown-item" href="<c:url value="/user/profile"/>">*/}
                    {/*                    <img src="<c:url value="/public/images/ic_user.svg/images/ic_user.svg"/>" alt="Icono perfil">*/}
                    {/*                        <spring:message code="navbar.profile"/>*/}
                    {/*                </a>*/}
                    {/*                <c:if test="${loggedUser.hasRole('PROVIDER')}">*/}
                    {/*                    <a className="dropdown-item" href="<c:url value="/user/experiences"/>">*/}
                    {/*                        <img src="<c:url value="/public/images/ic_experiences.svg/images/ic_experiences.svg"/>"*/}
                    {/*                             alt="Icono experiencias">*/}
                    {/*                            <spring:message code="navbar.experiences"/>*/}
                    {/*                    </a>*/}
                    {/*                </c:if>*/}
                    {/*                <a className="dropdown-item" href="<c:url value="/user/favourites"/>">*/}
                    {/*                    <img src="<c:url value="/public/images/ic_fav.svg/images/ic_fav.svg"/>" alt="Icono favoritos">*/}
                    {/*                        <spring:message code="navbar.favourites"/>*/}
                    {/*                </a>*/}
                    {/*                <c:if test="${loggedUser.hasRole('VERIFIED')}">*/}
                    {/*                    <a className="dropdown-item" href="<c:url value="/user/reviews"/>">*/}
                    {/*                        <img src="<c:url value="/public/images/ic_review.svg/images/ic_review.svg"/>" alt="Icono reseñas">*/}
                    {/*                            <spring:message code="navbar.reviews"/>*/}
                    {/*                    </a>*/}
                    {/*                </c:if>*/}
                    {/*                <a className="dropdown-item" href="<c:url value="/logout"/>">*/}
                    {/*                    <img src="<c:url value="/public/images/ic_logout.svg/images/ic_logout.svg"/>" alt="Icono cerrar sesion">*/}
                    {/*                        <spring:message code="navbar.logout"/>*/}
                    {/*                </a>*/}
                    {/*            </ul>*/}
                    {/*        </div>*/}
                    {/*    </c:otherwise>*/}
                    {/*</c:choose>*/}
                </div>
            </div>

            <div className="container-types container-fluid pb-2 p-0 d-flex justify-content-center m-0">

                {categories.map((category) => (
                    <Link to={category.path}>
                        <button type="button" className={`btn btn-category ${pathname === category.path ? "isActive" : ""}`}>
                            <img src={`public/images/${category.name}`} alt="Logo aventura"/>
                            {t('Navbar.categories.' + category.path)}
                        </button>
                    </Link>
                ))}

                {/*<a href="<c:url value="/experiences/Aventura"/>">*/}
                {/*    <button type="button"*/}
                {/*            className="btn btn-category <c:if test=" ${param.categoryName == 'Aventura'}"> isActive </c:if>">*/}
                {/*        <img src="<c:url value="/public/images/Aventura.svg/images/Aventura.svg"/>" alt="Logo aventura"/>*/}
                {/*        <spring:message code="navbar.filter.adventure"/>*/}
                {/*    </button>*/}
                {/*</a>*/}
                {/*<a href="<c:url value="/experiences/Gastronomia"/>">*/}
                {/*    <button type="button"*/}
                {/*            className="btn btn-category <c:if test=" ${param.categoryName == 'Gastronomia'}"> isActive </c:if>">*/}
                {/*        <img src="<c:url value="/public/images/Gastronomia.svg/images/Gastronomia.svg"/>" alt="Logo aventura"/>*/}
                {/*        <spring:message code="navbar.filter.gastronomy"/>*/}
                {/*    </button>*/}
                {/*</a>*/}
                {/*<a href="<c:url value="/experiences/Hoteleria"/>">*/}
                {/*    <button type="button"*/}
                {/*            className="btn btn-category <c:if test=" ${param.categoryName == 'Hoteleria'}"> isActive </c:if>">*/}
                {/*        <img src="<c:url value="/public/images/Hoteleria.svg/images/Hoteleria.svg"/>" alt="Logo aventura"/>*/}
                {/*        <spring:message code="navbar.filter.hotels"/>*/}
                {/*    </button>*/}
                {/*</a>*/}
                {/*<a href="<c:url value="/experiences/Relax"/>">*/}
                {/*    <button type="button"*/}
                {/*            className="btn btn-category <c:if test=" ${param.categoryName == 'Relax'}"> isActive </c:if>">*/}
                {/*        <img src="<c:url value="/public/images/Relax.svg/images/Relax.svg"/>" alt="Logo aventura"/>*/}
                {/*        <spring:message code="navbar.filter.relax"/>*/}
                {/*    </button>*/}
                {/*</a>*/}
                {/*<a href="<c:url value="/experiences/Vida_nocturna"/>">*/}
                {/*    <button type="button"*/}
                {/*            className="btn btn-category <c:if test=" ${param.categoryName == 'Vida_nocturna'}"> isActive </c:if>">*/}
                {/*        <img src="<c:url value="/public/images/Vida_nocturna.svg/images/Vida_nocturna.svg"/>" alt="Logo aventura"/>*/}
                {/*        <spring:message code="navbar.filter.night"/>*/}
                {/*    </button>*/}
                {/*</a>*/}
                {/*<a href="<c:url value="/experiences/Historico"/>">*/}
                {/*    <button type="button"*/}
                {/*            className="btn btn-category <c:if test=" ${param.categoryName == 'Historico'}"> isActive </c:if>">*/}
                {/*        <img src="<c:url value="/public/images/Historico.svg/images/Historico.svg"/>" alt="Logo aventura"/>*/}
                {/*        <spring:message code="navbar.filter.historic"/>*/}
                {/*    </button>*/}
                {/*</a>*/}
            </div>
        </div>

    // <hr className="separator"/>

    );

}