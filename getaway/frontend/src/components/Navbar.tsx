import {useTranslation} from "react-i18next"
import "../common/i18n/index"
import {Link, useNavigate, useSearchParams} from 'react-router-dom'
import {CategoryModel} from "../types";
import React, {Dispatch, SetStateAction, useEffect, useState} from "react";
import {useAuth} from "../hooks/useAuth";
import {serviceHandler} from "../scripts/serviceHandler";
import {categoryService} from "../services";
import {useForm} from "react-hook-form";
import {Close} from "@mui/icons-material";
import {IconButton} from "@mui/material";
import {getQueryOrDefault, useQuery} from "../hooks/useQuery";
import {showToast} from "../scripts/toast";

type FormDataSearch = {
    name: string
};

export default function Navbar(props: { nameProp: [string | undefined, Dispatch<SetStateAction<string | undefined>>], categoryProp: [string | undefined, Dispatch<SetStateAction<string | undefined>>] }) {
    const {t} = useTranslation()
    const navigate = useNavigate()
    const query = useQuery()

    const {nameProp, categoryProp} = props

    const [searchParams, setSearchParams] = useSearchParams();

    nameProp[1](getQueryOrDefault(query, "name", ""))
    categoryProp[1](getQueryOrDefault(query, "category", ""))

    const {signOut} = useAuth()
    const user = localStorage.getItem("user")
    let isLogged = user !== null
    let isProvider = localStorage.getItem("isProvider") === 'true'
    let isVerified = localStorage.getItem("isVerified") === 'true'

    const [categories, setCategories] = useState<CategoryModel[]>(new Array(0))

    const {register, handleSubmit, formState: {errors}, reset, setValue}
        = useForm<FormDataSearch>({criteriaMode: "all"})

    useEffect(() => {
        serviceHandler(
            categoryService.getCategories(),
            navigate, (category) => {
                setCategories(category)
            },
            () => {
            },
            () => {
                setCategories(new Array(0))
            }
        );
    }, [])

    useEffect(() => {
        setValue("name", getQueryOrDefault(query, "name", ""))
    }, [nameProp[0]])

    const onSubmit = handleSubmit((data: FormDataSearch) => {
        navigate({pathname: "/experiences", search: `?category=${categoryProp[0]}&name=${data.name}`}, {replace: true})
    });

    function clearNavBar() {
        searchParams.delete("category")
        categoryProp[1]("")
        resetForm()
    }

    function resetForm() {
        searchParams.delete("name")
        setSearchParams(searchParams)
        nameProp[1]("")
        reset()
    }

    function attemptAccessCreateExperience() {
        clearNavBar();
        if (user === null) {
            navigate("/login")
            // showToast(t('ExperienceForm.toast.forbidden.noUser'), 'error')
        } else if (!isVerified) {
            navigate("/user/profile")
            // showToast(t('ExperienceForm.toast.forbidden.notVerified'), 'error')
        }
    }

    return (
        <div className="navbar container-fluid p-0 d-flex flex-column">
            <div className="container-fluid px-2 pt-2 d-flex">
                <Link to="/" className="logo d-flex" onClick={() => {
                    clearNavBar()
                }}>
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
                        <form id="searchExperienceForm" acceptCharset="utf-8" className="my-auto" onSubmit={onSubmit}>
                            <input type="text" className="form-control" placeholder={t('Navbar.search')}
                                   {...register("name", {
                                       max: 255,
                                       pattern: {
                                           value: /^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°"·#$%&=:¿?!¡/.-]*$/,
                                           message: t("ExperienceForm.error.name.pattern"),
                                       }
                                   })}
                            />
                            {errors.name?.type === "max" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ExperienceForm.error.name.max")}
                                </p>
                            )}
                        </form>
                        <IconButton onClick={resetForm}>
                            <Close/>
                        </IconButton>
                    </div>


                    <Link to="/experienceForm" style={{marginRight: '40px'}}>
                        <button type="button" className='btn button-primary'
                                onClick={() => attemptAccessCreateExperience()}>
                            {t('Navbar.createExperience')}
                        </button>
                    </Link>


                    {!isLogged &&
                        <Link to="/login">
                            <button type="button" className="btn button-primary"
                                    onClick={() => clearNavBar()}>
                                {t('Navbar.login')}
                            </button>
                        </Link>
                    }


                    {isLogged &&
                        <div className="dropdown">
                            <button className="btn button-primary dropdown-toggle d-flex align-items-center" type="button"
                                    id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                                <img src={'./images/ic_user_white.svg'} alt="Icono usuario"
                                     style={{width: "35px", height: "35px"}}/>
                            </button>

                            <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1" style={{left: "-50px"}}>
                                <Link to="/user/profile" className="dropdown-item"
                                      onClick={() => clearNavBar()}>
                                    <img src={'./images/ic_user.svg'} alt="Icono perfil"/>
                                    {t('Navbar.profile')}
                                </Link>

                                {isProvider &&
                                    <Link to="/user/experiences" className="dropdown-item"
                                          onClick={() => clearNavBar()}>
                                        <img src={'./images/ic_experiences.svg'} alt="Icono experiencias"/>
                                        {t('Navbar.experiences')}
                                    </Link>}
                                <Link to="/user/favourites" className="dropdown-item"
                                      onClick={() => clearNavBar()}>
                                    <img src={'./images/ic_fav.svg'} alt="Icono favoritos"/>
                                    {t('Navbar.favourites')}
                                </Link>
                                {isVerified &&
                                    <Link to="/user/reviews" className="dropdown-item"
                                          onClick={() => clearNavBar()}>
                                        <img src={'./images/ic_review.svg'} alt="Icono reseñas"/>
                                        {t('Navbar.reviews')}
                                    </Link>}
                                <button className="dropdown-item" onClick={() => {
                                    clearNavBar()
                                    signOut(() => navigate("/"))
                                }}>
                                    <img src={'./images/ic_logout.svg'} alt="Icono cerrar sesion"/>
                                    {t('Navbar.logout')}
                                </button>
                            </ul>
                        </div>}
                </div>
            </div>

            <div className="container-types container-fluid pb-2 p-0 d-flex justify-content-center m-0">
                {categories.map((category) => (
                    <button type="button" className={`btn btn-category ${(categoryProp[0] === category.name) ? 'isActive' : ''}`} key={category.id}
                            onClick={() => {
                                categoryProp[1](category.name);
                                navigate({
                                    pathname: "/experiences",
                                    search: `?category=${category.name}&name=${nameProp[0]}`
                                })
                            }}
                    >
                        <img src={`./images/${category.name}.svg`} alt={`${category.name}`}/>
                        {t('Categories.' + category.name)}
                    </button>
                ))}
            </div>
        </div>

    )

}