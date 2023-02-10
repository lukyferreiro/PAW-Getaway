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
import {experienceService, locationService} from "../services";
import {useForm} from "react-hook-form";
import {serviceHandler} from "../scripts/serviceHandler";

type FormFilterData = {
    country: string,
    city: number,
    maxPrice: number,
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
    const [maxPrice, setMaxPrice] = useState<number>(-1);
    const [price, setPrice] = useState<number>(maxPrice);

    //Score
    const [rating, setRating] = useState(0);
    const [hover, setHover] = useState(0);

    //Order
    const [order, setOrder] = useState("OrderByAZ");
    const [page, setPage] = useState(1);

    const [experiences, setExperiences] = useState<ExperienceModel[]>(new Array(0))

    useEffect(() => {
        //TODO: getMaxPrice function in experience service

        // serviceHandler(
        //     experienceService.getCategoryMaxPrice(category),
        //     navigate, (price) => {
        //         setMaxPrice(price)
        //     },
        //     () => {}
        // ) ;
        serviceHandler(
            locationService.getCountries(),
            navigate, (country) => {
                setCountries(country)
            },
            () => {}
        ) ;
        serviceHandler(
            experienceService.getExperiencesByCategory(category!, order, price, rating, city, page),
            navigate, (experiences) => {
                setExperiences(experiences.getContent())
            },
            () => {}
        );
    }, [category])

    function loadCities(countryName: string){
        serviceHandler(
            locationService.getCitiesByCountry(parseInt(countryName)),
            navigate, (cities) => {
                setCities(cities)
                setCity(cities[0].id)
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

    function cleanForm() {
        window.location.reload();
    }
        //TODO: on submit filter
    const onSubmit = handleSubmit((data: FormFilterData) => {
        experienceService.getExperiencesByCategory(category!, order, price, -rating, city, page)
            .then((result) => {
                    if (!result.hasFailed()) {
                        setExperiences(result.getData().getContent())
                    }else{
                        setExperiences(Array(0));
                    }
                }
            )
            .catch(() => {});
        }
    );

    return (
        <div className="container-fluid p-0 mt-3 d-flex">
            <div className="container-filters container-fluid px-2 py-0 mx-2 my-0 d-flex flex-column justify-content-start align-items-center border-end">
                {/*FILTERS*/}
                <p className="filters-title m-0">
                    {t('Filters.title')}
                </p>

                <form id="submitForm" className="filter-form" onSubmit={onSubmit}>
                    <div>
                        <label className="form-label" htmlFor="country">
                            {t('Experience.country')}
                        </label>
                        <select id="experienceFormCountryInput" className="form-select"
                                {...register("country", {required: false})}
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
                        </label>
                        <select id="experienceFormCityInput" className="form-select"
                                {...register("city", {required: false})}
                                disabled={cities.length <= 0}
                        >
                            {cities.map((city) => (
                                <option key={city.id} value={city.id} onChange={e => setCity(city.id)}>
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


                <button className="btn btn-clean-filter px-3 py-2 my-2" type="button" id="cleanFilterFormButton"
                        onClick={cleanForm}>
                    {t('Filters.btn.clear')}
                </button>
            </div>

            <div className="container-experiences container-fluid p-0 mx-2 mt-0 mb-3 d-flex
                            flex-column justify-content-center align-content-center"
                 style={{minHeight: "650px"}}>
                <div className="d-flex justify-content-start">
                    <OrderDropdown/>
                </div>

                { experiences.length === 0 ?
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


                {/*TODO: pagination*/}
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