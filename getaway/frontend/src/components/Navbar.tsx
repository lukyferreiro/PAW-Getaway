import {useTranslation} from "react-i18next"
import "../common/i18n/index"
import {Link, useLocation, useNavigate} from 'react-router-dom'
import {CategoryModel} from "../types";
import {getQueryOrDefaultMultiple, useQuery} from "../hooks/useQuery";
import React, {useState} from "react";
import Modal from 'react-modal';
import '../styles/navbar.css'
import {useAuth} from "../hooks/useAuth";


export default function Navbar() {

    const {t} = useTranslation()
    let navigate = useNavigate()
    let location = useLocation()

    const query = useQuery()
    const pathname = location?.pathname
    const categoryQuery = getQueryOrDefaultMultiple(query, "category");

    //Esto lo vamos a tener q obtener de alguna manera, por ahora lo fuerzo para ver que funcione
    const {user, signOut} = useAuth();


    let isLogged = user !== null;
    let isProvider = user?.provider;
    let isVerified = user?.verified;

    const [isOpenSignIn, setIsOpenSignIn] = useState(false);
    const [isOpenPassword, setIsOpenPassword] = useState(false);
    const [isOpenCreate, setIsOpenCreate] = useState(false);

    //TODO obtenerlas de un llamado a la API ??
    const categories: CategoryModel[] = [
        {id: 1, name: 'Aventura'},
        {id: 2, name: 'Gastronomia'},
        {id: 3, name: 'Hoteleria'},
        {id: 4, name: 'Relax'},
        {id: 5, name: 'Vida_nocturna'},
        {id: 6, name: 'Historico'},
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


                    <Link to="/createExperience" style={{marginRight: '40px'}}>
                        <button type="button" onClick={ () => {
                            if (user === null) {
                                navigate("/login")
                            }
                            if (!isVerified) {
                                navigate("/user/profile")
                            }
                        }} className='btn button-primary'
                         >
                            {t('Navbar.createExperience')}
                        </button>
                    </Link>


                    {!isLogged &&
                        <Link to="/login">
                            <button type="button" className="btn button-primary">
                                {t('Navbar.login')}
                            </button>
                        </Link>
                    }
                    {/*<Modal*/}
                    {/*    style={{overlay: {zIndex: 100}}}*/}
                    {/*    className="modal-pop-up"*/}
                    {/*    isOpen={isOpenSignIn}*/}
                    {/*    contentLabel="PopUpSignIn"*/}
                    {/*    onRequestClose={() => setIsOpenSignIn(false)}*/}
                    {/*>*/}
                        {/*<div*/}
                        {/*    className="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">*/}
                        {/*    <div className="container-lg w-100 p-2">*/}
                        {/*        <div className="row w-100 m-0 p-5 align-items-center justify-content-center">*/}
                        {/*            <div className="col-12">*/}
                        {/*                <h1 className="text-center title">*/}
                        {/*                    {t('Navbar.loginTitle')}*/}
                        {/*                </h1>*/}
                        {/*                <p className="subtitle text-center">*/}
                        {/*                    {t('Navbar.loginDescription')}*/}
                        {/*                </p>*/}
                        {/*            </div>*/}
                        {/*            <div className="col-12">*/}
                        {/*                <div className="container-fluid px-0">*/}
                        {/*                    /!*     <c:url value="/login" var="postPath"/>*!/*/}
                        {/*                    /!*     <form id="loginForm" action="${postPath}" accept-charset="UTF-8"*!/*/}
                        {/*                    /!*           method="POST"*!/*/}
                        {/*                    /!*           encType="application/x-www-form-urlencoded">*!/*/}
                        {/*                    <div className="row">*/}
                        {/*                        <div className="col-12">  /!*  Email *!/*/}
                        {/*                            <label className="form-label d-flex align-items-center"*/}
                        {/*                                   htmlFor="email">*/}
                        {/*                                <img*/}
                        {/*                                    src={"./images/ic_user.svg"}*/}
                        {/*                                    alt="Imagen perfil"*/}
                        {/*                                    style={{marginRight: "5px"}}/>*/}
                        {/*                                {t('Navbar.email')}*/}
                        {/*                            </label>*/}
                        {/*                            <input type="text" id="email" name="email"*/}
                        {/*                                   className="form-control mb-2"*/}
                        {/*                                   aria-describedby="email input"/>*/}
                        {/*                        </div>*/}
                        {/*                    </div>*/}

                        {/*                    <div className="col-12 mt-2">*/}
                        {/*                        <div className="container-fluid">*/}
                        {/*                            <div className="row">*/}
                        {/*                                <div className="col-6 px-0">*/}
                        {/*                                    <label*/}
                        {/*                                        className="form-label d-flex align-items-center"*/}
                        {/*                                        htmlFor="password">*/}
                        {/*                                        <img*/}
                        {/*                                            src={"./images/ic_password.svg"}*/}
                        {/*                                            alt="Imagen perfil"*/}
                        {/*                                            style={{marginRight: "5px"}}/>*/}
                        {/*                                        {t('Navbar.password')}*/}
                        {/*                                    </label>*/}
                        {/*                                </div>*/}
                        {/*                                <div*/}
                        {/*                                    className="col-6 px-0 d-flex justify-content-end form-label">*/}
                        {/*                                    <div className="link-primary" onClick={() => {*/}
                        {/*                                        setIsOpenSignIn(false)*/}
                        {/*                                        setIsOpenPassword(true)*/}
                        {/*                                    }}*/}
                        {/*                                         tabIndex={-1}>*/}
                        {/*                   <span className="text-right" style={{fontSize: "medium"}} onClick={() => setIsOpenSignIn(false)}>*/}
                        {/*                       {t('Navbar.forgotPassword')}*/}
                        {/*                   </span>*/}
                        {/*                                    </div>*/}
                        {/*                                </div>*/}
                        {/*                                <div*/}
                        {/*                                    className="col-12 px-0 d-flex justify-content-start align-items-center">*/}
                        {/*                                    <div*/}
                        {/*                                        className="input-group d-flex justify-content-start align-items-center">*/}
                        {/*                                        <input type="password" className="form-control"*/}
                        {/*                                               id="password" name="password"*/}
                        {/*                                               aria-describedby="password input"/>*/}
                        {/*                                        /!*<div className="input-group-append">*!/*/}
                        {/*                                        /!*    <button id="passwordEye" type="button"*!/*/}
                        {/*                                        /!*            tabIndex={-1}*!/*/}
                        {/*                                        /!*            className="btn btn-lg form-control btn-eye input-group-text">*!/*/}
                        {/*                                        /!*        <i id="eye"*!/*/}
                        {/*                                        /!*           className="far fa-eye-slash"></i>*!/*/}
                        {/*                                        /!*    </button>*!/*/}
                        {/*                                        /!*</div>*!/*/}
                        {/*                                    </div>*/}
                        {/*                                </div>*/}
                        {/*                                /!*                     <c:if test="${error==true}">*!/*/}
                        {/*                                /!*                         <div*!/*/}
                        {/*                                /!*                             className="col-12 mt-2 px-0 d-flex justify-content-start align-items-center">*!/*/}
                        {/*                                /!*                             <p className="mb-0 form-error-label">*!/*/}
                        {/*                                /!*                                 <spring:message code="loginForm.error"/>*!/*/}
                        {/*                                /!*                             </p>*!/*/}
                        {/*                                /!*                         </div>*!/*/}
                        {/*                                /!*                     </c:if>*!/*/}
                        {/*                            </div>*/}
                        {/*                        </div>*/}
                        {/*                    </div>*/}

                        {/*                    <div*/}
                        {/*                        className="col-12 mt-3 d-flex justify-content-start align-items-center">*/}
                        {/*                        <input type="checkbox" id="rememberMe" name="rememberMe"/>*/}
                        {/*                        <label className="mb-0 mx-2" htmlFor="rememberMe">*/}
                        {/*                            {t('Navbar.rememberMe')}*/}
                        {/*                        </label>*/}
                        {/*                    </div>*/}
                        {/*                    /!*</form>*!/*/}
                        {/*                </div>*/}
                        {/*            </div>*/}
                        {/*            <div className="col-12 d-flex align-items-center justify-content-center">*/}
                        {/*                <button form="loginForm" type="submit" className="w-100 btn-login my-2">*/}
                        {/*                    {t('Navbar.login')}*/}
                        {/*                </button>*/}
                        {/*            </div>*/}
                        {/*            <div className="col-12 mt-4">*/}
                        {/*                <p className="mb-0 text-center">*/}
                        {/*                    {t('Navbar.newUser')}*/}
                        {/*                    <div className="link-primary form-label" onClick={() => {*/}
                        {/*                        setIsOpenSignIn(false)*/}
                        {/*                        setIsOpenCreate(true)*/}
                        {/*                    }}*/}
                        {/*                         tabIndex={-1}>*/}
                        {/*                        {t('Navbar.createAccount')}*/}
                        {/*                    </div>*/}
                        {/*                </p>*/}
                        {/*            </div>*/}
                        {/*        </div>*/}
                        {/*    </div>*/}
                        {/*</div>*/}
                    {/*</Modal>*/}

                    {/*<Modal*/}
                    {/*    style={{overlay: {zIndex: 100}}}*/}
                    {/*    className="modal-pop-up"*/}
                    {/*    isOpen={isOpenPassword}*/}
                    {/*    contentLabel="PopUpPassword"*/}
                    {/*    onRequestClose={() => setIsOpenPassword(false)}*/}
                    {/*>*/}
                    {/*    <div*/}
                    {/*        className="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">*/}
                    {/*        <div*/}
                    {/*            className="row w-100 h-100 py-5 px-3 m-0 align-items-center justify-content-center">*/}
                    {/*            <div className="col-12">*/}
                    {/*                <h1 className="text-center title">*/}
                    {/*                    {t('Navbar.resetPasswordTitle')}*/}
                    {/*                </h1>*/}
                    {/*            </div>*/}
                    {/*            <div className="col-12">*/}
                    {/*                <div className="container-fluid">*/}
                    {/*                    <div className="row">*/}
                    {/*                        /!*<form id="passResetRequest"*!/*/}
                    {/*                        /!*           modelAttribute="resetPasswordEmailForm"*!/*/}
                    {/*                        /!*           action="${postUrl}"*!/*/}
                    {/*                        /!*           method="POST" acceptCharset="UTF-8">*!/*/}
                    {/*                        <label className="form-label d-flex align-items-center"*/}
                    {/*                               htmlFor="email">*/}
                    {/*                            <img*/}
                    {/*                                src={"./images/ic_user.svg"}*/}
                    {/*                                alt="Imagen perfil"*/}
                    {/*                                style={{marginRight: "5px"}}/>*/}
                    {/*                            {t('Navbar.email')}*/}
                    {/*                            <span className="required-field">*</span>*/}

                    {/*                        </label>*/}
                    {/*                        <input type="text" id="email" name="email"*/}
                    {/*                               className="form-control mb-2"*/}
                    {/*                               placeholder="juan@ejemplo.com"*/}
                    {/*                               aria-describedby="email input"/>*/}

                    {/*                        /!*<form:errors path="email" cssClass="form-error-label"*!/*/}
                    {/*                        /!*             element="p"/>*!/*/}
                    {/*                        <div*/}
                    {/*                            className="col-12 mt-3 d-flex align-items-center justify-content-center">*/}
                    {/*                            <button type="button" className='btn button-primary'>*/}
                    {/*                                /!*<button form="passResetRequest" type="submit"*!/*/}
                    {/*                                /!*        className="btn btn-continue">*!/*/}
                    {/*                                {t('Navbar.resetPasswordButton')}*/}
                    {/*                            </button>*/}
                    {/*                        </div>*/}
                    {/*                        /!*</form>*!/*/}
                    {/*                    </div>*/}
                    {/*                </div>*/}
                    {/*            </div>*/}
                    {/*        </div>*/}
                    {/*    </div>*/}
                    {/*</Modal>*/}

                    {/*<Modal*/}
                    {/*    style={{overlay: {zIndex: 100}}}*/}
                    {/*    className="modal-pop-up"*/}
                    {/*    isOpen={isOpenCreate}*/}
                    {/*    contentLabel="PopUpCreate"*/}
                    {/*    onRequestClose={() => setIsOpenCreate(false)}*/}
                    {/*>*/}
                    {/*    <div*/}
                    {/*        className="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">*/}
                    {/*        <div className="row w-100 m-0 p-4 align-items-center justify-content-center">*/}
                    {/*            <div className="col-12">*/}
                    {/*                <h1 className="text-center title">*/}
                    {/*                    {t('Navbar.createAccountPopUp')}*/}
                    {/*                </h1>*/}
                    {/*                <p className="subtitle text-center">*/}
                    {/*                    {t('Navbar.createAccountDescription')}*/}
                    {/*                </p>*/}
                    {/*            </div>*/}
                    {/*            <div className="col-12">*/}
                    {/*                <div className="container-lg">*/}
                    {/*                    <div className="row">*/}
                    {/*                        /!*<c:url value="/register" var="postPath"/>*!/*/}
                    {/*                        /!*<form modelAttribute="registerForm" action="${postPath}"*!/*/}
                    {/*                        /!*           id="registerForm"*!/*/}
                    {/*                        /!*           method="post" acceptCharset="UTF-8">*!/*/}
                    {/*                        <div className="form-group">*/}
                    {/*                            <label className="form-label d-flex justify-content-between"*/}
                    {/*                                   htmlFor="email">*/}
                    {/*                                <div>*/}
                    {/*                                    {t('Navbar.email')}*/}
                    {/*                                    <span className="required-field">*</span>*/}
                    {/*                                </div>*/}
                    {/*                                <div className="align-self-center">*/}
                    {/*                                    <h6 className="max-input-text">*/}
                    {/*                                        {t('Navbar.max', {num: 255})}*/}
                    {/*                                    </h6>*/}
                    {/*                                </div>*/}
                    {/*                            </label>*/}
                    {/*                            <input type="text" id="email" name="email"*/}
                    {/*                                   className="form-control mb-2"*/}
                    {/*                                   placeholder={t('Navbar.emailPlaceholder')}*/}
                    {/*                                   aria-describedby="email input"*/}
                    {/*                                   maxLength={255}/>*/}
                    {/*                            /!*<form:errors path="email" cssClass="form-error-label"*!/*/}
                    {/*                            /!*             element="p"/>*!/*/}
                    {/*                        </div>*/}

                    {/*                        <div className="form-group">*/}
                    {/*                            <label htmlFor="name"*/}
                    {/*                                   className="form-label d-flex justify-content-between">*/}
                    {/*                                <div>*/}
                    {/*                                    {t('Navbar.name')}*/}
                    {/*                                    <span className="required-field">*</span>*/}
                    {/*                                </div>*/}
                    {/*                                <div className="align-self-center">*/}
                    {/*                                    <h6 className="max-input-text">*/}
                    {/*                                        {t('Navbar.max', {num: 50})}*/}
                    {/*                                    </h6>*/}
                    {/*                                </div>*/}
                    {/*                            </label>*/}

                    {/*                            <input maxLength={50} type="text" name="name" id="name"*/}
                    {/*                                   className="form-control"*/}
                    {/*                                   placeholder={t('Navbar.namePlaceholder')}/>*/}
                    {/*                            /!*<form:errors path="name" cssClass="form-error-label"*!/*/}
                    {/*                            /!*             element="p"/>*!/*/}
                    {/*                        </div>*/}

                    {/*                        <div className="form-group">*/}
                    {/*                            <label htmlFor="surname"*/}
                    {/*                                   className="form-label d-flex justify-content-between">*/}
                    {/*                                <div>*/}
                    {/*                                    {t('Navbar.surname')}*/}
                    {/*                                    <span className="required-field">*</span>*/}
                    {/*                                </div>*/}
                    {/*                                <div className="align-self-center">*/}
                    {/*                                    <h6 className="max-input-text">*/}
                    {/*                                        {t('Navbar.max', {num: 50})}*/}
                    {/*                                    </h6>*/}
                    {/*                                </div>*/}
                    {/*                            </label>*/}
                    {/*                            <input maxLength={50} type="text" name="surname" id="surname"*/}
                    {/*                                   className="form-control"*/}
                    {/*                                   placeholder={t('Navbar.surnamePlaceholder')}*/}
                    {/*                            />*/}
                    {/*                            /!*<form:errors path="surname" cssClass="form-error-label"*!/*/}
                    {/*                            /!*             element="p"/>*!/*/}
                    {/*                        </div>*/}

                    {/*                        <div className="form-group">*/}
                    {/*                            <label htmlFor="password"*/}
                    {/*                                   className="form-label d-flex justify-content-between">*/}
                    {/*                                <div>*/}
                    {/*                                    {t('Navbar.password')}*/}
                    {/*                                    <span className="required-field">*</span>*/}
                    {/*                                </div>*/}
                    {/*                                <div className="align-self-center">*/}
                    {/*                                    <h6 className="max-input-text">*/}
                    {/*                                        {t('Navbar.max', {num: 25})}*/}
                    {/*                                    </h6>*/}
                    {/*                                </div>*/}
                    {/*                            </label>*/}
                    {/*                            <div*/}
                    {/*                                className="input-group d-flex justify-content-start align-items-center">*/}
                    {/*                                <input type="password"*/}
                    {/*                                       className="form-control"*/}
                    {/*                                       id="password1" name="password"*/}
                    {/*                                       aria-describedby="password input"*/}
                    {/*                                       placeholder={t('Navbar.passwordPlaceholder')}*/}
                    {/*                                />*/}
                    {/*                                <div className="input-group-append">*/}
                    {/*                                    /!*<button id="passwordEye1" type="button" tabIndex={-1}*!/*/}
                    {/*                                    /!*        className="btn btn-lg form-control btn-eye input-group-text">*!/*/}
                    {/*                                    /!*    <i id="eye1" className="far fa-eye-slash"></i>*!/*/}
                    {/*                                    /!*</button>*!/*/}
                    {/*                                </div>*/}
                    {/*                            </div>*/}
                    {/*                            /!*<form:errors path="password" cssClass="form-error-label"*!/*/}
                    {/*                            /!*             element="p"/>*!/*/}
                    {/*                        </div>*/}

                    {/*                        <div className="form-group">*/}
                    {/*                            <label htmlFor="confirmPassword" className="form-label">*/}
                    {/*                                {t('Navbar.confirmPassword')}*/}
                    {/*                                <span className="required-field">*</span>*/}
                    {/*                            </label>*/}
                    {/*                            <div*/}
                    {/*                                className="input-group d-flex justify-content-start align-items-center">*/}
                    {/*                                <input type="password"*/}
                    {/*                                       className="form-control"*/}
                    {/*                                       id="password2" name="confirmPassword"*/}
                    {/*                                       aria-describedby="password input"/>*/}
                    {/*                                <div className="input-group-append">*/}
                    {/*                                    /!*<button id="passwordEye2" type="button" tabIndex="-1"*!/*/}
                    {/*                                    /!*        className="btn btn-lg form-control btn-eye input-group-text">*!/*/}
                    {/*                                    /!*    <i id="eye2" className="far fa-eye-slash"></i>*!/*/}
                    {/*                                    /!*</button>*!/*/}
                    {/*                                </div>*/}
                    {/*                            </div>*/}
                    {/*                            /!*<form:errors path="confirmPassword" cssClass="form-error-label"*!/*/}
                    {/*                            /!*             element="p"/>*!/*/}
                    {/*                        </div>*/}

                    {/*                        <div className="form-group">*/}
                    {/*                            /!*<spring:hasBindErrors name="registerForm">*!/*/}
                    {/*                            /!*    <c:if test="${errors.globalErrorCount > 0}">*!/*/}
                    {/*                            /!*        <div className="alert alert-danger">*!/*/}
                    {/*                            /!*            <form:errors/>*!/*/}
                    {/*                            /!*        </div>*!/*/}
                    {/*                            /!*    </c:if>*!/*/}
                    {/*                            /!*</spring:hasBindErrors>*!/*/}
                    {/*                        </div>*/}

                    {/*                        <div*/}
                    {/*                            className="col-12 px-0 d-flex align-items-center justify-content-center">*/}
                    {/*                            <button type="button" id="registerFormButton"*/}
                    {/*                                    form="registerForm"*/}
                    {/*                                    className="w-100 btn-create-account my-2 ">*/}
                    {/*                                {t('Navbar.createAccount')}*/}
                    {/*                            </button>*/}
                    {/*                        </div>*/}
                    {/*                        /!*</form>*!/*/}
                    {/*                    </div>*/}
                    {/*                </div>*/}
                    {/*            </div>*/}
                    {/*        </div>*/}
                    {/*    </div>*/}
                    {/*</Modal>*/}
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
                                <button className="dropdown-item" onClick={ () => {
                                    signOut( () => navigate("/") )
                                } }>
                                    <img src={'./images/ic_logout.svg'} alt="Icono cerrar sesion"/>
                                    {t('Navbar.logout')}
                                </button>
                            </ul>
                        </div>}
                </div>
            </div>

            <div className="container-types container-fluid pb-2 p-0 d-flex justify-content-center m-0">

                {categories.map((category) => (
                    <Link to={{pathname: "/experiences", search: `?category=${category.name}`}}>
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