import {useTranslation} from "react-i18next"
import "../common/i18n/index"
import {Link, useLocation, useNavigate} from 'react-router-dom'
import {CategoryModel} from "../types";
import {getQueryOrDefaultMultiple, useQuery} from "../hooks/useQuery";

export default function Navbar() {

    const {t} = useTranslation()
    let navigate = useNavigate()
    let location = useLocation()

    const query = useQuery()
    const pathname = location?.pathname
    const categoryQuery = getQueryOrDefaultMultiple(query, "category");

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

                    {/*TODO que solo aparezca si el user es provider*/}
                    <Link to="/createExperience" style={{marginRight: '40px'}}>
                        <button type="button" className='btn button-primary'>
                            {t('Navbar.createExperience')}
                        </button>
                    </Link>

                    {/*TODO ver como hacer el case de si el usuario esta logeado o no*/}
                    {/*<c:choose>*/}
                    {/*    <c:when test="${loggedUser == null}">*/}
                    <Link to="/login">
                        <button type="button" className="btn button-primary">
                            {t('Navbar.login')}
                        </button>
                    </Link>
                    {/*    </c:when>*/}
                    {/*    <c:otherwise>*/}
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
                            {/*TODO que solo este si el usuario es provider*/}
                            {/*<c:if test="${loggedUser.hasRole('PROVIDER')}">*/}
                            <Link to="/user/experiences" className="dropdown-item">
                                <img src={'./images/ic_experiences.svg'} alt="Icono experiencias"/>
                                {t('Navbar.experiences')}
                            </Link>
                            {/*</c:if>*/}
                            <Link to="/user/favourites" className="dropdown-item">
                                <img src={'./images/ic_fav.svg'} alt="Icono favoritos"/>
                                {t('Navbar.favourites')}
                            </Link>
                            {/*TODO que solo este si el usuario es VERIFIED*/}
                            {/*<c:if test="${loggedUser.hasRole('VERIFIED')}">*/}
                            <Link to="/user/reviews" className="dropdown-item">
                                <img src={'./images/ic_review.svg'} alt="Icono reseñas"/>
                                {t('Navbar.reviews')}
                            </Link>
                            {/*</c:if>*/}
                            {/*TODO agregar onClick que haga el logout*/}
                            <button className="dropdown-item">
                                <img src={'./images/ic_logout.svg'} alt="Icono cerrar sesion"/>
                                {t('Navbar.logout')}
                            </button>
                        </ul>
                    </div>
                    {/*    </c:otherwise>*/}
                    {/*</c:choose>*/}
                </div>
            </div>

            <div className="container-types container-fluid pb-2 p-0 d-flex justify-content-center m-0">

                {categories.map((category) => (
                    <Link to={{pathname: "/experiences", search:`?category=${category.name}`}} >
                        <button type="button" className={`btn btn-category ${categoryQuery?.includes(`category=${category.name}`) ? 'isActive' : ''}`}>
                            <img src={`./images/${category.name}.svg`} alt={`${category.name}`}/>
                            {t('Navbar.categories.' + category.name)}
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