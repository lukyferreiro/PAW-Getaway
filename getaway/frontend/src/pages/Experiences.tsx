import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import OrderDropdown from "../components/OrderDropdown";
import CardExperience from "../components/CardExperience";
import {Slider, Typography} from '@mui/material';
import React, {useEffect, useState} from "react";
import {ExperienceModel} from "../types";
import {useAuth} from "../hooks/useAuth";
import {useLocation, useNavigate} from "react-router-dom";
import {getQueryOrDefault, getQueryOrDefaultMultiple, useQuery} from "../hooks/useQuery";
import "../styles/star_rating.css";
import {CityModel, CountryModel} from "../types";
import {categoryService, experienceService, locationService} from "../services";
import {useForm} from "react-hook-form";
import {serviceHandler} from "../scripts/serviceHandler";

type FormFilterData = {
    country: string,
    city: string,
    maxPrice: string,
    score: number,
};


export default function Experiences() {
    const {user} = useAuth();
    const {t} = useTranslation();
    const navigate = useNavigate()
    const location = useLocation();
    const query = useQuery()

    const category = getQueryOrDefault(query, "category", "");

    //FILTERS
    //Location
    const [countries, setCountries] = useState<CountryModel[]>(new Array(1))
    const [cities, setCities] = useState<CityModel[]>(new Array(0))
    const [city, setCity] = useState(-1);

    //Price
    const maxPrice = -1;
    const [price, setPrice] = useState<number>(maxPrice);
    //Por ahora lo hago fijo
    {/*    /!*TODO obtener el max price de todas las experiencias*!/*/}

    //Score
    const [rating, setRating] = useState(0);
    const [hover, setHover] = useState(0);
    const [score, setScore] = useState(0);

    //Order
    const [order, setOrder] = useState("OrderByAZ");

    const [page, setPage] = useState(1);

    const [experiences, setExperiences] = useState<ExperienceModel[]>(new Array(0))

    useEffect(() => {
        //TODO: getMaxPrice function in experience service
        serviceHandler(
            locationService.getCountries(),
            navigate, (country) => {
                setCountries(country)
            },
            () => {}
        ) ;
        serviceHandler(
            experienceService.getExperiencesByCategory(category!, order, price, score, city, page),
            navigate, (experiences) => {
                setExperiences(experiences.getContent())
            },
            () => {}
        );
    }, [category])

    function loadCities(countryName: string){
        serviceHandler(
            locationService.getCitiesByCountry(parseInt(countryName)),
            navigate, (city) => {
                setCities(city)
            },
            () => {}
        ) ;
    }
    const handleChange = (event: Event, newValue: number | number[]) => {
        if (typeof newValue === 'number') {
            setPrice(newValue);
        }
    };

    const {register, handleSubmit, formState: { errors },}
        = useForm<FormFilterData>({ criteriaMode: "all" });

    //TODO: on submit filter
    const onSubmit = handleSubmit((data: FormFilterData) => {
            data.score = -rating;
            data.maxPrice = String(price);
            //    RELOAD PAGE WITH FILTERS
        }
    );

    return (
        <div className="container-fluid p-0 mt-3 d-flex">
            <div className="container-filters container-fluid px-2 py-0 mx-2 my-0 d-flex flex-column justify-content-start align-items-center border-end">
                {/*FILTERS*/}
                <p className="filters-title m-0">
                    {t('Filters.title')}
                </p>

                <form id="submitForm" className="filter-form">
                    <div>
                        <label className="form-label" htmlFor="country">
                            {t('Experience.country')}
                            <span className="required-field">*</span>
                        </label>
                        <select id="experienceFormCountryInput" className="form-select" required
                                {...register("country", {required: true})}
                                onChange={e => loadCities(e.target.value)}
                        >
                            <option hidden value="">{t('Experience.placeholder')}</option>

                            {countries.map((country) => (
                                <option key={country.id} value={country.id} >
                                    {country.name}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="mt-2">
                        <label className="form-label" htmlFor="city">
                            {t('Filters.city.field')}
                            <span className="required-field">*</span>
                        </label>
                        <select id="experienceFormCityInput" className="form-select" required
                                {...register("city", {required: true})}
                                disabled={cities.length <= 0}
                        >
                            {cities.map((city) => (
                                <option key={city.id} value={city.name}>
                                    {city.name}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="container-slider-price">

                        <Typography id="non-linear-slider" gutterBottom className="form-label">
                            {t('Filters.price.title')}: {price}
                        </Typography>
                        <div className="slider-price">
                            <div className="value left">
                                {t('Filters.price.min')}
                            </div>
                            <div className="slider">
                            </div>
                            <Slider
                                value={price}
                                min={5}
                                step={1}
                                max={maxPrice}
                                onChange={handleChange}
                                valueLabelDisplay="auto"
                            />
                            <div className="value right">
                                {maxPrice}
                            </div>
                        </div>
                    </div>

                    <div>
                        <label className="form-label">
                            {t('Filters.scoreAssign')}
                        </label>
                        {/*TODO poner las estrellas*/}
                        <div className="star-rating">
                            {[...Array(5)].map((star, index) => {
                                index -=5;
                                return (
                                    <button
                                        type="button"
                                        key={index}
                                        className={index >= ((rating && hover) || hover) ? "on" : "off"}
                                        onClick={() => setRating(index)}
                                        onMouseEnter={() => setHover(index)}
                                        onMouseLeave={() => setHover(rating)}
                                    >
                                        <span className="star">&#9733;</span>
                                    </button>
                                );
                            })}
                        </div>
                        {/*TODO ver como poner esto cuando el input falla*/}
                        {/*cssErrorClass="form-control is-invalid"*/}
                        <input value="TODO" type="hidden" className="form-control" id="scoreInput"/>
                        {/*TODO ver como poner esto*/}
                        {/*<form:errors path="score" element="p" cssClass="form-error-label"/>*/}
                    </div>
                </form>

                {/*TODO ver como se aplican todos los filtros*/}
                <button className="btn btn-search px-3 py-2 my-2" type="submit" id="submitFormButton" form="submitForm">
                    {t('Filters.btn.submit')}
                </button>


                {/*TODO ver como se borran todos los filtros*/}
                <button className="btn btn-clean-filter px-3 py-2 my-2" type="button" id="cleanFilterFormButton"
                        form="submitForm">
                    {t('Filters.btn.clear')}
                </button>
            </div>

            <div className="container-experiences container-fluid p-0 mx-2 mt-0 mb-3 d-flex
                            flex-column justify-content-center align-content-center"
                 style={{minHeight: "650px"}}>
                <div className="d-flex justify-content-start">
                    <OrderDropdown/>
                    {/*<jsp:include page="/WEB-INF/components/orderDropdown.jsp">*/}
                    {/*    <jsp:param name="orderByModels" value="${orderByModels}"/>*/}
                    {/*    <jsp:param name="path" value="${path}"/>*/}
                    {/*    <jsp:param name="score" value="${score}"/>*/}
                    {/*    <jsp:param name="cityId" value="${cityId}"/>*/}
                    {/*    <jsp:param name="maxPrice" value="${maxPrice}"/>*/}
                    {/*    <jsp:param name="orderPrev" value="${orderBy}"/>*/}
                    {/*</jsp:include>*/}
                </div>

                {/*TODO*/}
                {/*<c:choose>*/}
                {/*    <c:when test="${experiences.size() == 0}">*/}
                { experiences === undefined ?
                    <div className="my-auto mx-5 px-3 d-flex justify-content-center align-content-center">
                        <div className="d-flex justify-content-center align-content-center">
                            <img src={'./images/ic_no_search.jpeg'} alt="Imagen lupa"
                                 style={{width: "150px", height: "150px", minWidth: "150px", minHeight: "150px", marginRight: "5px"}}/>
                            <h1 className="d-flex align-self-center">
                                {t('EmptyResult')}
                            </h1>

                        </div>
                    </div>
                    :
                    <div>
                        <div className="container-fluid my-3 d-flex flex-wrap justify-content-center">

                            <div className="pl-5 pr-2 w-50"
                                 style={{minWidth: "400px", minHeight: "150px", height: "fit-content"}}>
                                {experiences.map((experience) => (
                                    <CardExperience experience={experience} key={experience.id}/>
                                ))}
                            </div>
                        </div>
                    </div>
                }


                {/*TODO*/}
                {/*                    </c:when>*/}
                {/*                    <c:otherwise>*/}
                {/*                        <div class="d-flex flex-wrap justify-content-center">*/}
                {/*                            <c:forEach var="experience" varStatus="myIndex" items="${experiences}">*/}
                {/*                                <jsp:include page="/WEB-INF/components/cardExperience.jsp">*/}
                {/*                                    <jsp:param name="hasImage" value="${experience.image != null}"/>*/}
                {/*                                    <jsp:param name="categoryName" value="${experience.category.categoryName}"/>*/}
                {/*                                    <jsp:param name="id" value="${experience.experienceId}"/>*/}
                {/*                                    <jsp:param name="name" value="${experience.experienceName}"/>*/}
                {/*                                    <jsp:param name="isFav" value="${experience.isFav}"/>*/}
                {/*                                    <jsp:param name="description" value="${experience.description}"/>*/}
                {/*                                    <jsp:param name="address" value="${experience.getLocationName()}"/>*/}
                {/*                                    <jsp:param name="price" value="${experience.price}"/>*/}
                {/*                                    <jsp:param name="path" value="/experiences/${categoryName}"/>*/}
                {/*                                    <jsp:param name="score" value="${score}"/>*/}
                {/*                                    <jsp:param name="cityId" value="${cityId}"/>*/}
                {/*                                    <jsp:param name="maxPrice" value="${maxPrice}"/>*/}
                {/*                                    <jsp:param name="filter" value="true"/>*/}
                {/*                                    <jsp:param name="pageNum" value="${currentPage}"/>*/}
                {/*                                    <jsp:param name="orderBy" value="${orderBy}"/>*/}
                {/*                                    <jsp:param name="avgReviews" value="${experience.averageScore}"/>*/}
                {/*                                    <jsp:param name="reviewCount" value="${experience.reviewCount}"/>*/}
                {/*                                    <jsp:param name="observable" value="${experience.observable}"/>*/}
                {/*                                </jsp:include>*/}
                {/*                            </c:forEach>*/}
                {/*                        </div>*/}

                {/*                        <div class="mt-auto d-flex justify-content-center align-items-center">*/}
                {/*                            <ul class="pagination m-0">*/}
                {/*                                <li class="page-item">*/}
                {/*                                    <a class="page-link "*/}
                {/*                                       href="<c:url value = "/experiences/${categoryName}">*/}
                {/*                                          <c:param name = " pageNum" value = "1"/>*/}
                {/*                                    <c:param name="score" value="${score}"/>*/}
                {/*                                    <c:param name="cityId" value="${cityId}"/>*/}
                {/*                                    <c:param name="maxPrice" value="${maxPrice}"/>*/}
                {/*                                    <c:param name="orderBy" value="${orderBy}"/>*/}
                {/*                                </c:url>*/}
                {/*                                ">*/}
                {/*                                <spring:message code="pagination.start"/>*/}
                {/*                            </a>*/}
                {/*                        </li>*/}
                {/*                        <c:forEach var="i" begin="${minPage}" end="${maxPage}">*/}
                {/*                            <li class="page-item">*/}
                {/*                                <a class="page-link ${i == currentPage ? 'current-page-link' : ''}"*/}
                {/*                                   href="<c:url value = "/experiences/${categoryName}">*/}
                {/*                                          <c:param name = " pageNum" value = " ${i}"/>*/}
                {/*                                          <c:param name = " score" value = " ${score}"/>*/}
                {/*                                          <c:param name = " cityId" value = " ${cityId}"/>*/}
                {/*                                          <c:param name = " maxPrice" value = " ${maxPrice}"/>*/}
                {/*                                          <c:param name = " orderBy" value = " ${orderBy}" />*/}
                {/*                                          </c:url>">*/}
                {/*                                    <c:out value="${i}"/>*/}
                {/*                                </a>*/}
                {/*                            </li>*/}
                {/*                        </c:forEach>*/}
                {/*                        <li class="page-item">*/}
                {/*                            <a class="page-link "*/}
                {/*                               href="<c:url value = "/experiences/${categoryName}">*/}
                {/*                                          <c:param name = " pageNum" value = " ${totalPages}"/>*/}
                {/*                                          <c:param name = " score" value = " ${score}"/>*/}
                {/*                                          <c:param name = " cityId" value = " ${cityId}"/>*/}
                {/*                                          <c:param name = " maxPrice" value = " ${maxPrice}"/>*/}
                {/*                                          <c:param name = " orderBy" value = " ${orderBy}" />*/}
                {/*                                    </c:url>">*/}
                {/*                                <spring:message code="pagination.end"/>*/}
                {/*                            </a>*/}
                {/*                        </li>*/}
                {/*                    </ul>*/}
                {/*            </div>*/}
                {/*        </c:otherwise>*/}
                {/*</c:choose>*/}
            </div>
        </div>
    )
}