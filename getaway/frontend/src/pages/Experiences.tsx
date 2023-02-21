import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import OrderDropdown from "../components/OrderDropdown";
import CardExperience from "../components/CardExperience";
import {IconButton, Slider, Typography} from '@mui/material';
import React, {Dispatch, SetStateAction, useEffect, useState} from "react";
import {ExperienceModel, OrderByModel} from "../types";
import {useLocation, useNavigate, useSearchParams} from "react-router-dom";
import "../styles/star_rating.css";
import {CityModel, CountryModel} from "../types";
import {experienceService, locationService} from "../services";
import {serviceHandler} from "../scripts/serviceHandler";
import Pagination from "../components/Pagination";
import {Close} from "@mui/icons-material";
import DataLoader from "../components/DataLoader";
import {getQueryOrDefault, useQuery} from "../hooks/useQuery";

export default function Experiences(props: {nameProp: [string | undefined, Dispatch<SetStateAction<string | undefined>>], categoryProp: [string | undefined, Dispatch<SetStateAction<string | undefined>>]}) {
    const {t} = useTranslation()
    const navigate = useNavigate()
    const location = useLocation()

    const {nameProp, categoryProp} = props

    const query = useQuery()

    const [searchParams, setSearchParams] = useSearchParams();

    const [experiences, setExperiences] = useState<ExperienceModel[]>(new Array(0))
    const [isLoading, setIsLoading] = useState(false)

    //------------FILTERS----------
    //Location
    const [countries, setCountries] = useState<CountryModel[]>(new Array(1))
    const [country, setCountry] = useState(parseInt(getQueryOrDefault(query, "country", "-1")))
    const [cities, setCities] = useState<CityModel[]>(new Array(0))
    const [city, setCity] = useState(parseInt(getQueryOrDefault(query, "city", "-1")))
    //Price
    const [maxPrice, setMaxPrice] = useState<number>(-1)
    const [price, setPrice] = useState<number>(parseInt(getQueryOrDefault(query, "price", "-1")))
    //Score
    const [rating, setRating] = useState(parseInt(getQueryOrDefault(query, "rating", "0")))
    const [hover, setHover] = useState(0)
    //Order
    const [orders, setOrders] = useState<OrderByModel[]>(new Array(0))
    const order = useState<string>(getQueryOrDefault(query, "order", "OrderByAZ"))
    //Page
    const [maxPage, setMaxPage] = useState(1)
    const currentPage = useState<number>(parseInt(getQueryOrDefault(query, "page", "1")))

    useEffect(()=>{
        serviceHandler(
            experienceService.getUserOrderByModels(),
            navigate, (orders) => {
                setOrders(orders)
            },
            () => {
            },
            () => {
                setOrders(new Array(0))
                order[1]("OrderByAZ")
            }
        );
        serviceHandler(
            locationService.getCountries(),
            navigate, (country) => {
                setCountries(country)
            },
            () => {
            },
            () => {
                setCountries(new Array(0))
            }
        );
    }, [])

    //TODO: maxprice changes makes double refresh. Maybe add maxprice to experiencelist dto
    useEffect(() => {
        if( nameProp[0] !== undefined && categoryProp[0] !== undefined) {
            searchParams.set("order", "OrderByAZ")
            searchParams.set("page", "1")
            setSearchParams(searchParams)
            serviceHandler(
                experienceService.getFilterMaxPrice(categoryProp[0], nameProp[0]),
                navigate, (priceModel) => {
                    setMaxPrice(priceModel.maxPrice)
                    if (maxPrice != priceModel.maxPrice || price > priceModel.maxPrice || price === -1) {
                        setPrice(priceModel.maxPrice)
                    }
                },
                () => {
                },
                () => {
                    setPrice(-1)
                }
            );
        }
    }, [categoryProp[0], nameProp[0]])

    useEffect(() => {
        if( nameProp[0] !== undefined && categoryProp[0] !== undefined) {
            console.log("Reloading experiences with neew filter")
            setIsLoading(true)
            serviceHandler(
                experienceService.getExperiencesByFilter(categoryProp[0], nameProp[0], order[0], price, -rating, city, currentPage[0]),
                navigate, (experiences) => {
                    setExperiences(experiences.getContent())
                    setMaxPage(experiences.getMaxPage())
                },
                () => {
                    setIsLoading(false)
                },
                () => {
                    setExperiences(new Array(0))
                    setIsLoading(false)
                }
            );
        }
    }, [categoryProp[0], nameProp[0], rating, city, order[0], currentPage[0], price])

    function handleCountryChange(countryId: number) {
        setCountry(countryId)
        searchParams.set("country", countryId.toString())
        setSearchParams(searchParams)
        serviceHandler(
            locationService.getCitiesByCountry(countryId),
            navigate, (cities) => {
                setCities(cities)
            },
            () => {
            },
            () => {
                setCities(new Array(0))
                setCity(-1);
            }
        );
    }

    function handleCityChange(cityId: number) {
        searchParams.set("city", cityId.toString())
        setSearchParams(searchParams)
        setCity(cityId)
    }

    const handlePriceChange = (event: Event, newValue: number | number[]) => {
        if (typeof newValue === 'number') {
            searchParams.set("price", newValue.toString())
            setSearchParams(searchParams)
            setPrice(newValue);
        }
    };

    function handleRatingChange(index: number) {
        searchParams.set("rating", (-index).toString())
        setSearchParams(searchParams)
        setRating(index)
    }

    function cleanForm() {
        searchParams.delete("price")
        searchParams.delete("country")
        searchParams.delete("city")
        searchParams.delete("rating")
        searchParams.set("order", "OrderByAZ")
        searchParams.set("page", "1")
        setSearchParams(searchParams)

        setCities(new Array(0));
        setCountry(-1)
        setCity(-1);
        setRating(0);
        setHover(0);
        setPrice(maxPrice);
        order[1]("OrderByAZ")
        currentPage[1](1)
        console.log("Reseting filters")
    }

    function cleanQuery() {
        //TODO: make subcases
        categoryProp[1]("")
        nameProp[1]("")
    }

    return (
        <div className="container-fluid p-0 mt-3 d-flex">
            <div className="container-filters container-fluid px-2 py-0 mx-2 my-0 d-flex flex-column justify-content-start align-items-center border-end">
                {/*FILTERS*/}
                <p className="filters-title m-0">
                    {t('Filters.title')}
                </p>

                <div className="filter-form">
                    <div>
                        <label className="form-label" htmlFor="country">
                            {t('Experience.country')}
                        </label>
                        <select id="experienceFormCountryInput" className="form-select"
                                onChange={e => handleCountryChange(parseInt(e.target.value))}
                        >
                            {/*TODO: check usage after filter reset*/}
                            {country === -1 &&
                            <option hidden value="">{t('Experience.placeholder')}</option>
                            }
                            {countries.map((country) => (
                                <option key={country.id} value={country.id}>
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
                                disabled={country === -1}
                                onChange={e => handleCityChange(parseInt(e.target.value))}
                        >
                            {city === -1 &&
                            <option hidden value="">{t('Experience.placeholder')}</option>
                            }
                            {cities.map((city) => (
                                <option key={city.id} value={city.id} >
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
                            <div className="slider"/>
                            <Slider
                                value={price}
                                min={5}
                                step={1}
                                max={maxPrice}
                                onChange={handlePriceChange}
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
                                index -= 5;
                                return (
                                    <button
                                        type="button"
                                        key={index}
                                        className={index >= ((rating && hover) || hover) ? "on" : "off"}
                                        onClick={() => handleRatingChange(index)}
                                        onMouseEnter={() => setHover(index)}
                                        onMouseLeave={() => setHover(rating)}
                                    >
                                        <span className="star">&#9733;</span>
                                    </button>
                                );
                            })}
                        </div>
                        <input value="TODO" type="hidden" className="form-control" id="scoreInput"/>
                    </div>
                </div>

                {/*<button className="btn btn-search px-3 py-2 my-2" type="submit" id="submitFormButton" form="submitForm">*/}
                {/*    {t('Filters.btn.submit')}*/}
                {/*</button>*/}

                <button className="btn btn-clean-filter px-3 py-2 my-2" type="reset" id="cleanFilterFormButton"
                        onClick={cleanForm}>
                    {t('Filters.btn.clear')}
                </button>
            </div>


            <div className="container-experiences container-fluid p-0 mx-2 mt-0 mb-3 d-flex
                            flex-column justify-content-center align-content-center"
                 style={{minHeight: "650px"}}>
                <DataLoader spinnerMultiplier={2} isLoading={isLoading}>


                    <div className="d-flex justify-content-center align-content-center">
                        <div style={{margin: "0 auto 0 20px", flex: "1"}}>
                            <OrderDropdown orders={orders} order={order} currentPage={currentPage}/>
                        </div>
                        <div className="d-flex justify-content-center" style={{fontSize: "x-large"}}>

                            { categoryProp[0] !== undefined && categoryProp[0].length > 0 ?
                                <div>
                                    {t('Experiences.search.search')}
                                    {t('Experiences.search.category')}
                                    {t('Categories.' + categoryProp[0])}
                                    <div className="d-flex justify-content-center">
                                        { nameProp[0] !== undefined &&
                                            nameProp[0].length > 0 &&
                                            <div>
                                                {t('Experiences.search.name', {name: nameProp[0]})}
                                                <IconButton className="justify-content-center" onClick={cleanQuery}>
                                                    <Close/>
                                                </IconButton>
                                            </div>
                                        }
                                    </div>

                                </div>
                                :
                                <div>
                                    { nameProp[0] !== undefined && nameProp[0].length > 0 &&
                                    <div>
                                        {t('Experiences.search.search')}
                                        {t('Experiences.search.name', {name: nameProp[0]})}
                                        <IconButton className="justify-content-center" onClick={cleanQuery}>
                                            <Close/>
                                        </IconButton>
                                    </div>
                                    }
                                </div>
                            }
                        </div>
                        <div style={{margin: "0 20px 0 auto", flex: "1"}}/>
                    </div>

                    {experiences.length === 0 ?
                        <div className="my-auto mx-5 px-3 d-flex justify-content-center align-content-center">
                            <div className="d-flex justify-content-center align-content-center">
                                <img src={'./images/ic_no_search.jpeg'} alt="Imagen lupa"
                                     style={{
                                         width: "150px",
                                         height: "150px",
                                         minWidth: "150px",
                                         minHeight: "150px",
                                         marginRight: "5px"
                                     }}/>
                                <h1 className="d-flex align-self-center">
                                    {t('EmptyResult')}
                                </h1>

                            </div>
                        </div>
                        :
                        <div>
                            <div className="d-flex flex-wrap justify-content-center">
                                {experiences.map((experience) => (
                                    <CardExperience experience={experience} key={experience.id}/>
                                ))}
                            </div>
                        </div>
                    }

                    <div className="mt-auto d-flex justify-content-center align-items-center">
                        {maxPage > 1 && (
                            <Pagination
                                maxPage={maxPage}
                                currentPage={currentPage}
                            />
                        )}
                    </div>
                </DataLoader>

            </div>
        </div>
    )
}