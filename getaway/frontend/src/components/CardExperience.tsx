import {useTranslation} from "react-i18next";
import "../common/i18n/index"
import {Link, useLocation, useNavigate} from 'react-router-dom'
import {CategoryModel, ExperienceModel} from "../types";
import CityModel from "../types/CityModel";
import UserModel from "../types/UserModel";
import CountryModel from "../types/CountryModel";

export default function CardExperience() {


    const {t} = useTranslation()

    const countryModel: CountryModel =
        {
            id: 2,
            name: "Pais campeon del mundo",
        };

    const cityModel: CityModel =
        {
            id: 1,
            name: "avellaneda city",
            country: countryModel,
        };


    const categoryModel: CategoryModel =
        {
            id: 1,
            name: 'Aventura',
        };


    const userModel: UserModel = {
        id: 1,
        name: "LUCAS FERREIRO PA",
        surname: "LUCAS FERREIRO PA",
        email: "lferreiro@itba.edu.ar PA",
        favsCount: 2,
        verified: true,
        provider: true,
    };

    const experience: ExperienceModel =
        {
            id: 1,
            name: "hola soy una prueba",
            price: 10,
            address: "SAN PEDRO 47",
            email: "LFERREIRO@ITBA.EDU.AR",
            description: "HOLA SOY LUQUITAS",
            siteUrl: "HOLA.COM",
            city: cityModel,
            category: categoryModel,
            user: userModel,
            observable: true,
            views: 200,
            score: 5,
            reviewsCount: 0,
        };


    return (

        <div className="card card-experience mx-3 my-2 p-0">

            {/*<div className="btn-fav">*/}
            {/*    <jsp:include page="/WEB-INF/components/fav.jsp">*/}
            {/*        <jsp:param name="isFav" value="${param.isFav}"/>*/}
            {/*        <jsp:param name="experienceId" value="${param.id}"/>*/}
            {/*        <jsp:param name="path" value="${param.path}"/>*/}
            {/*        <jsp:param name="query" value="${param.query}"/>*/}
            {/*        <jsp:param name="score" value="${param.score}"/>*/}
            {/*        <jsp:param name="cityId" value="${param.cityId}"/>*/}
            {/*        <jsp:param name="maxPrice" value="${param.maxPrice}"/>*/}
            {/*        <jsp:param name="orderBy" value="${param.orderBy}"/>*/}
            {/*        <jsp:param name="filter" value="${param.filter}"/>*/}
            {/*        <jsp:param name="search" value="${param.search}"/>*/}
            {/*    </jsp:include>*/}
            {/*</div>*/}

            <div className="card-link h-100 d-flex flex-column">
                <div>
                    {/*<c:choose>*/}
                    {/*    <c:when test="${param.hasImage}">*/}
                    {/*<img className="card-img-top container-fluid p-0 mw-100"*/}
                    {/*     src={"/experiences/${param.id}/image'/>"} alt="Imagen"/>*/}
                    {/*</c:when>*/}
                    {/*<c:otherwise>*/}
                    <img className="card-img-top container-fluid p-4 mw-100" alt="Imagen ${param.categoryName}"
                         src={"./images/Aventura.svg"}/>
                    {/*    </c:otherwise>*/}
                    {/*</c:choose>*/}

                    <div className="card-body container-fluid p-2">
                        <div className="title-link">
                            <Link to="experiences/${param.categoryName}/${param.id}">
                                <param name=" view" value=" ${true}"/>
                            </Link>
                            <h2 className="experience card-title container-fluid p-0 text-truncate">
                                {experience.name}
                            </h2>
                        </div>
                        <div className="card-text container-fluid p-0">
                            <p className="text-truncate">
                                {experience.description}
                            </p>
                            <h5 className="text-truncate">
                                ${experience.address}
                            </h5>
                            {/*<h6>*/}
                            {/*    <c:choose>*/}
                            {/*        <c:when test="${param.price == ''}">*/}
                            {/*            <spring:message code="experience.noPrice"/>*/}
                            {/*        </c:when>*/}
                            {/*        <c:when test="${param.price == '0.0'}">*/}
                            {/*            <spring:message code="experience.price.free"/>*/}
                            {/*        </c:when>*/}
                            {/*        <c:otherwise>*/}
                            {/*            <spring:message code="experience.price.value" arguments="${param.price}"/>*/}
                            {/*        </c:otherwise>*/}
                            {/*    </c:choose>*/}
                            {/*</h6>*/}
                        </div>

                    </div>
                </div>

                <div className="card-body container-fluid d-flex p-2 mb-1 align-items-end">
                    <h5 className="mb-1">
                        {t('experience.reviews', {reviewCount: experience.reviewsCount})}
                    </h5>
                    {/*<jsp:include page="/WEB-INF/components/starAvg.jsp">*/}
                    {/*    <jsp:param name="avgReview" value="${param.avgReviews}"/>*/}
                    {/*</jsp:include>*/}
                </div>
                //TODO este if y los otros y completar con los componentes que faltan
                {/*<c:if test="${!param.observable}">*/}
                {/*    <div className="card-body p-0 d-flex justify-content-center">*/}
                {/*        <h5 className="obs-info align-self-center" style="font-size: small">*/}
                {/*            <spring:message code="experience.notVisible"/>*/}
                {/*            {t('experience.notVisible')}*/}
                {/*            */}
                {/*        </h5>*/}
                {/*    </div>*/}
                {/*</c:if>*/}
            </div>

            <script src='/resources/js/snackbar.js"/>'></script>
        </div>
    )
}