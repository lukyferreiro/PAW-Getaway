import {useTranslation} from "react-i18next"
import "../common/i18n/index"
import {Link, useLocation, useNavigate} from 'react-router-dom'
import {CategoryModel} from "../types";
import {getQueryOrDefaultMultiple, useQuery} from "../hooks/useQuery";
import React, {useState} from "react";
import Modal from 'react-modal';
import '../styles/navbar.css'


export default function Navbar() {

    const {t} = useTranslation()
    let navigate = useNavigate()
    let location = useLocation()

    const query = useQuery()
    const pathname = location?.pathname
    const categoryQuery = getQueryOrDefaultMultiple(query, "category");

    //Esto lo vamos a tener q obtener de alguna manera, por ahora lo fuerzo para ver que funcione
    const isLogged = false;
    const isProvider = true;
    const isVerified = true;

    const [isOpen, setIsOpen] = useState(false);



    //TODO obtenerlas de un llamado a la API ??
    const categories: CategoryModel[] = [
        {categoryId: 1, name: 'Aventura'},
        {categoryId: 2, name: 'Gastronomia'},
        {categoryId: 3, name: 'Hoteleria'},
        {categoryId: 4, name: 'Relax'},
        {categoryId: 5, name: 'Historico'},
        {categoryId: 6, name: 'Vida_nocturna'},
    ]

    return (
        <div className="navbar container-fluid p-0 d-flex flex-column">
            <div className="container-fluid px-2 pt-2 d-flex">
                <Link to="/" className="logo d-flex">
                    <img className="logo-img" src={'./images/getaway-icon.png'} alt="Logo"/>
                    <span className="logo-text align-self-center text-uppercase font-weight-bold">
                        {t('PageName')}
                    </span>
                </Link>
                <div className="container-navbar-buttons d-flex justify-content-between align-items-center">
                    <div className="d-flex justify-items-center align-items-center"
                         style={{marginRight: '40px'}}>
                        <button className="btn btn-search-navbar p-0" type="submit" form="searchExperienceForm">
                            <img src={'./images/ic_lupa.svg'} alt="Lupa"/>
                        </button>
                        {/*<c:url value="/search_result" var="searchGetPath"/>*/}
                        {/*TODO agregar onSubmit*/}
                        <form className="my-auto">
                            {/*TODO cuando falle agregar cssErrorClass="form-control is-invalid*/}
                            <input type="text" className="form-control" placeholder={t('Navbar.search')}/>
                            {/*<form:input path="query" type="text" className="form-control" cssErrorClass="form-control is-invalid"*/}
                            {/*            placeholder="${placeholder}"/>*/}
                            {/*<form:errors path="query" element="p" cssClass="form-error-label"/>*/}
                        </form>
                    </div>


                    {isLogged && isProvider &&
                    <Link to="/createExperience" style={{marginRight: '40px'}}>
                        <button type="button" className='btn button-primary'>
                            {t('Navbar.createExperience')}
                        </button>
                    </Link>}


                    {!isLogged &&
                    //<Link to="/login">
                        <button type="button" className="btn button-primary" onClick={() => setIsOpen(true)}>
                            {t('Navbar.login')}
                        </button>
                    //</Link>
                    }
                    {isOpen && (
                        <Modal
                            className="modal-pop-up"
                            isOpen={isOpen}
                            contentLabel="PopUpSignIn"
                            onRequestClose={() => setIsOpen(false)}
                        >
                            <div
                                className="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">
                                <div className="container-lg w-100 p-2">
                                    <div className="row w-100 m-0 p-5 align-items-center justify-content-center">
                                        <div className="col-12">
                                            <h1 className="text-center title">
                                                {t('Navbar.loginTitle')}
                                            </h1>
                                            <p className="subtitle text-center">
                                                {t('Navbar.loginDescription')}
                                            </p>
                                        </div>
                                        <div className="col-12">
                                            <div className="container-fluid px-0">
                                           {/*     <c:url value="/login" var="postPath"/>*/}
                                           {/*     <form id="loginForm" action="${postPath}" accept-charset="UTF-8"*/}
                                           {/*           method="POST"*/}
                                           {/*           encType="application/x-www-form-urlencoded">*/}
                                                    <div className="row">
                                                        <div className="col-12">  {/*  Email */}
                                                            <label className="form-label d-flex align-items-center"
                                                                   htmlFor="email">
                                                                <img
                                                                    src={"./images/ic_user.svg"}
                                                                    alt="Imagen perfil"
                                                                    style={{marginRight: "5px"}}/>
                                                                    {t('Navbar.email')}
                                                            </label>
                                                            <input type="text" id="email" name="email"
                                                                   className="form-control mb-2"
                                                                   aria-describedby="email input"/>
                                                        </div>
                                                    </div>

                                                    <div className="col-12 mt-2">
                                                        <div className="container-fluid">
                                                            <div className="row">
                                                                <div className="col-6 px-0">
                                                                    <label
                                                                        className="form-label d-flex align-items-center"
                                                                        htmlFor="password">
                                                                        <img
                                                                            src={"./images/ic_password.svg"}
                                                                            alt="Imagen perfil"
                                                                            style={{marginRight: "5px"}}/>
                                                                        {t('Navbar.password')}
                                                                    </label>
                                                                </div>
                                                                <div
                                                                    className="col-6 px-0 d-flex justify-content-end form-label">
                                                                    <Link to="/user/resetPasswordRequest"
                                                                       tabIndex={-1}>
                                           <span className="text-right" style={{fontSize: "medium" }}>
                                               {t('Navbar.forgotPassword')}
                                           </span>
                                                                    </Link>
                                                                </div>
                                                                <div
                                                                    className="col-12 px-0 d-flex justify-content-start align-items-center">
                                                                    <div
                                                                        className="input-group d-flex justify-content-start align-items-center">
                                                                        <input type="password" className="form-control"
                                                                               id="password" name="password"
                                                                               aria-describedby="password input"/>
                                                                        {/*<div className="input-group-append">*/}
                                                                        {/*    <button id="passwordEye" type="button"*/}
                                                                        {/*            tabIndex={-1}*/}
                                                                        {/*            className="btn btn-lg form-control btn-eye input-group-text">*/}
                                                                        {/*        <i id="eye"*/}
                                                                        {/*           className="far fa-eye-slash"></i>*/}
                                                                        {/*    </button>*/}
                                                                        {/*</div>*/}
                                                                    </div>
                                                                </div>
                                           {/*                     <c:if test="${error==true}">*/}
                                           {/*                         <div*/}
                                           {/*                             className="col-12 mt-2 px-0 d-flex justify-content-start align-items-center">*/}
                                           {/*                             <p className="mb-0 form-error-label">*/}
                                           {/*                                 <spring:message code="loginForm.error"/>*/}
                                           {/*                             </p>*/}
                                           {/*                         </div>*/}
                                           {/*                     </c:if>*/}
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div
                                                        className="col-12 mt-3 d-flex justify-content-start align-items-center">
                                                        <input type="checkbox" id="rememberMe" name="rememberMe"/>
                                                        <label className="mb-0 mx-2" htmlFor="rememberMe">
                                                            {t('Navbar.rememberMe')}
                                                        </label>
                                                    </div>
                                                {/*</form>*/}
                                            </div>
                                        </div>
                                        <div className="col-12 d-flex align-items-center justify-content-center">
                                            <button form="loginForm" type="submit" className="w-100 btn-login my-2">
                                                {t('Navbar.login')}
                                            </button>
                                        </div>
                                        <div className="col-12 mt-4">
                                            <p className="mb-0 text-center">
                                                {t('Navbar.newUser')}
                                                <Link to="/register">
                                                    {t('Navbar.createAccount')}
                                                </Link>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </Modal>
                    )}
                    {isLogged &&
                    <div className="dropdown">
                        <button className="btn button-primary dropdown-toggle d-flex align-items-center" type="button"
                                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                            <img src={'./images/ic_user_white.svg'} alt="Icono usuario" style={{
                                width: "35px",
                                height: "35px"
                            }}/>
                        </button>

                        <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1" style={{left: "-50px"}}>
                            <Link to="/user/profile" className="dropdown-item">
                                <img src={'./images/ic_user.svg'} alt="Icono perfil"/>
                                {t('Navbar.profile')}
                            </Link>

                            {isProvider && <Link to="/user/experiences" className="dropdown-item">
                                <img src={'./images/ic_experiences.svg'} alt="Icono experiencias"/>
                                {t('Navbar.experiences')}
                            </Link>}
                            <Link to="/user/favourites" className="dropdown-item">
                                <img src={'./images/ic_fav.svg'} alt="Icono favoritos"/>
                                {t('Navbar.favourites')}
                            </Link>
                            {isVerified && <Link to="/user/reviews" className="dropdown-item">
                                <img src={'./images/ic_review.svg'} alt="Icono reseñas"/>
                                {t('Navbar.reviews')}
                            </Link>}
                            {/*TODO agregar onClick que haga el logout*/}
                            <button className="dropdown-item">
                                <img src={'./images/ic_logout.svg'} alt="Icono cerrar sesion"/>
                                {t('Navbar.logout')}
                            </button>
                        </ul>
                    </div>}
                </div>
            </div>

            <div className="container-types container-fluid pb-2 p-0 d-flex justify-content-center m-0">

                {categories.map((category) => (
                    <Link to={{pathname: "/experiences", search:`?category=${category.name}`}} >
                        <button type="button" className={`btn btn-category ${categoryQuery?.includes(`category=${category.name}`) ? 'isActive' : ''}`}>
                            <img src={`./images/${category.name}.svg`} alt={`${category.name}`}/>
                            {t('Categories.' + category.name)}
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

    )

}