import {useTranslation} from "react-i18next"
import "../common/i18n/index"
import {Link, useNavigate, useSearchParams} from 'react-router-dom'
import {CategoryModel, CityModel} from "../types";
import React, {Dispatch, SetStateAction, useEffect, useState} from "react";
import {useAuth} from "../hooks/useAuth";
import {serviceHandler} from "../scripts/serviceHandler";
import {experienceService} from "../services";
import {useForm} from "react-hook-form";
import {Close} from "@mui/icons-material";
import {IconButton} from "@mui/material";
import {getQueryOrDefault, useQuery} from "../hooks/useQuery";
import {showToast} from "../scripts/toast";
import ic_getaway from "../images/ic_getaway.png";
import ic_lupa from "../images/ic_lupa.svg";
import ic_user_white from "../images/ic_user_white.svg";
import ic_user_black from "../images/ic_user_black.svg";
import ic_experiences from "../images/ic_experiences.svg";
import ic_fav from "../images/ic_fav.svg";
import ic_review from "../images/ic_review.svg";
import ic_logout from "../images/ic_logout.svg";
import categoryImages, {CategoryName} from "../common";

type FormDataSearch = {
    name: string
};

export default function Navbar(props: { nameProp: [string | undefined, Dispatch<SetStateAction<string | undefined>>], categoryProp: [string | undefined, Dispatch<SetStateAction<string | undefined>>] }) {

    const {t} = useTranslation()
    const navigate = useNavigate()
    const query = useQuery()

    const {nameProp, categoryProp} = props
    const [searchParams, setSearchParams] = useSearchParams();

    const {signOut, isLogged, isProvider, isVerified} = useAuth()
    const isLoggedValue = isLogged()
    const isProviderValue = isProvider()
    const isVerifiedValue = isVerified()

    const [categories, setCategories] = useState<CategoryModel[]>(new Array(0))

    const country = !isNaN(parseInt(getQueryOrDefault(query, "country", "-1"))) ?
        parseInt(getQueryOrDefault(query, "country", "-1")) : -1
    const city = !isNaN(parseInt(getQueryOrDefault(query, "city", "-1"))) ?
        parseInt(getQueryOrDefault(query, "city", "-1")) : -1
    const price = !isNaN(parseInt(getQueryOrDefault(query, "price", "-1"))) ?
        parseInt(getQueryOrDefault(query, "price", "-1")) : -1
    const rating = !isNaN(parseInt(getQueryOrDefault(query, "rating", "0"))) ?
        parseInt(getQueryOrDefault(query, "rating", "0")) : 0

    function checkUrlFilters(category: string | undefined, name: string | undefined): string {
        const filtersArray: string[] = []

        if (category) filtersArray.push(`category=${category}`)
        if (name) filtersArray.push(`name=${name}`)
        if (country !== -1) filtersArray.push(`country=${country}`)
        if (city !== -1) filtersArray.push(`city=${city}`)
        if (price !== -1) filtersArray.push(`price=${price}`)
        if (rating !== 0) filtersArray.push(`rating=${rating}`)

        return filtersArray.length > 0 ? '&' + filtersArray.join('&') : ''
    }

    const {register, handleSubmit, formState: {errors}, reset, setValue}
        = useForm<FormDataSearch>({criteriaMode: "all"})

    useEffect(() => {
        setValue("name", nameProp[0] ? nameProp[0] : "")
    }, [nameProp[0]])

    useEffect(() => {
        nameProp[1](getQueryOrDefault(query, "name", ""))
        categoryProp[1](getQueryOrDefault(query, "category", ""))
        serviceHandler(
            experienceService.getCategories(),
            navigate, (category) => {
                setCategories(category)
            },
            () => {
            },
            () => {
                setCategories(new Array(0))
            }
        )
    }, [])

    const onSubmit = handleSubmit((data: FormDataSearch) => {
        nameProp[1](data.name)
        navigate({
            pathname: "/experiences",
            search: `?order=OrderByAZ&page=1${checkUrlFilters(categoryProp[0], data.name)}`
        }, {replace: true})
    })

    function clearNavBar() {
        console.log("clear NAVBAR")
        searchParams.delete("category")
        searchParams.delete("name")
        setSearchParams(searchParams)
        categoryProp[1]("")
        nameProp[1]("")
        reset()
    }

    function resetForm() {
        console.log("reset form NAVBAR")
        nameProp[1]("")
        searchParams.delete("name")
        setSearchParams(searchParams)
        reset()
    }

    function attemptAccessCreateExperience() {
        clearNavBar();
        if (!isLoggedValue) {
            navigate("/login",{replace: true})
            showToast(t('ExperienceForm.toast.forbidden.noUser'), 'error')
        } else if (isLoggedValue && !isVerifiedValue) {
            navigate("/user/profile",{replace: true})
            showToast(t('ExperienceForm.toast.forbidden.notVerified'), 'error')
        } else {
            navigate(`/experienceForm`,{replace: true})
        }
    }

    return (
        <div className="navbar container-fluid p-0 d-flex flex-column">
            <div className="container-fluid px-2 pt-2 d-flex">
                <Link to="/" className="logo d-flex" onClick={() => {
                    clearNavBar()
                }}>
                    <img className="logo-img w-auto h-auto" src={ic_getaway} alt="Logo"/>
                    <span className="logo-text align-self-center text-uppercase font-weight-bold">
                        {t('PageName')}
                    </span>
                </Link>
                <div className="container-navbar-buttons d-flex justify-content-between align-items-center">
                    <div className="d-flex justify-items-center align-items-center"
                         style={{marginRight: '40px'}}>
                        <button className="btn btn-search-navbar p-0" type="submit" form="searchExperienceForm"
                                aria-label={t("AriaLabel.search")} title={t("AriaLabel.search")}>
                            <img className="w-auto h-auto" src={ic_lupa} alt="Lupa"/>
                        </button>
                        <form id="searchExperienceForm" acceptCharset="utf-8" className="my-auto" onSubmit={onSubmit}>
                            <input max="50" type="text" className="form-control" placeholder={t('Navbar.search')}
                                   {...register("name", {
                                       validate: {
                                           length: (name) =>
                                               name.length <= 50,
                                       },
                                       pattern: {
                                           value: /^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°"·#$%&=:¿?!¡/.-]*$/,
                                           message: t("ExperienceForm.error.name.pattern"),
                                       }
                                   })}
                            />
                            {errors.name?.type === "length" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("Navbar.error")}
                                </p>
                            )}
                            {errors.name?.type === "pattern" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ExperienceForm.error.name.pattern")}
                                </p>
                            )}
                        </form>
                        <IconButton onClick={resetForm} aria-label={t("AriaLabel.closeForm")} title={t("AriaLabel.closeForm")}>
                            <Close/>
                        </IconButton>
                    </div>

                    <button type="button" style={{marginRight: '40px'}} className='btn button-primary'
                            aria-label={t("AriaLabel.createExperience")} title={t("AriaLabel.createExperience")}
                            onClick={() => attemptAccessCreateExperience()}>
                        {t('Navbar.createExperience')}
                    </button>

                    {!isLoggedValue &&
                        <Link to="/login">
                            <button type="button" className="btn button-primary"
                                    aria-label={t("AriaLabel.login")} title={t("AriaLabel.login")}
                                    onClick={() => clearNavBar()}>
                                {t('Navbar.login')}
                            </button>
                        </Link>
                    }


                    {isLoggedValue &&
                        <div className="dropdown">
                            <button className="btn button-primary dropdown-toggle d-flex align-items-center" type="button"
                                    id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false"
                                    aria-label={t("AriaLabel.profileInfo")} title={t("AriaLabel.profileInfo")}>
                                <img src={ic_user_white} alt="Icono usuario"
                                     style={{width: "35px", height: "35px"}}/>
                            </button>

                            <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1" style={{left: "-50px"}}>
                                <Link to="/user/profile" className="dropdown-item"
                                      onClick={() => clearNavBar()}>
                                    <img src={ic_user_black} alt="Icono perfil"/>
                                    {t('Navbar.profile')}
                                </Link>

                                {isProviderValue &&
                                    <Link to="/user/experiences" className="dropdown-item"
                                          onClick={() => clearNavBar()}>
                                        <img src={ic_experiences} alt="Icono experiencias"/>
                                        {t('Navbar.experiences')}
                                    </Link>}
                                <Link to="/user/favourites" className="dropdown-item"
                                      onClick={() => clearNavBar()}>
                                    <img src={ic_fav} alt="Icono favoritos"/>
                                    {t('Navbar.favourites')}
                                </Link>
                                {isVerifiedValue &&
                                    <Link to="/user/reviews" className="dropdown-item"
                                          onClick={() => clearNavBar()}>
                                        <img src={ic_review} alt="Icono reseñas"/>
                                        {t('Navbar.reviews')}
                                    </Link>}
                                <button className="dropdown-item" aria-label={t("AriaLabel.signOut")} title={t("AriaLabel.signOut")}
                                        onClick={() => {
                                    clearNavBar()
                                    signOut(() => navigate("/",{replace: true}))
                                }}>
                                    <img src={ic_logout} alt="Icono cerrar sesion"/>
                                    {t('Navbar.logout')}
                                </button>
                            </ul>
                        </div>}
                </div>
            </div>

            <div className="container-types container-fluid pb-2 p-0 d-flex justify-content-center m-0">
                {categories.map((category) => (
                    <button type="button" className={`btn btn-category ${(categoryProp[0] === category.name) ? 'isActive' : ''}`} key={category.id}
                            aria-label={`${category.name}`} title={`${category.name}`}
                            onClick={() => {
                                categoryProp[1](category.name);
                                navigate({
                                    pathname: "/experiences",
                                    search:`?order=OrderByAZ&page=1${checkUrlFilters(category.name, nameProp[0])}`
                                }, {replace: true});
                            }}
                    >
                        <img src={categoryImages[category.name as CategoryName]} alt={`${category.name}`}/>
                        {t('Categories.' + category.name)}
                    </button>
                ))}
            </div>
        </div>

    )

}