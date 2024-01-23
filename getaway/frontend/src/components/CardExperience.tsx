import {useTranslation} from "react-i18next";
import "../common/i18n/index"
import {Link, useNavigate, useSearchParams} from 'react-router-dom'
import {CategoryModel, CityModel, ExperienceModel} from "../types";
import StarRating from "./StarRating";
import React, {Dispatch, SetStateAction, useEffect, useState} from "react";
import {IconButton} from "@mui/material";
import {useAuth} from "../hooks/useAuth";
import {Favorite, FavoriteBorder} from "@mui/icons-material";
import {setFavExperience} from "../scripts/experienceOperations";
import Price from "./Price";
import DataLoader from "./DataLoader";
import {showToast} from "../scripts/toast";
import categoryImages, {CategoryName} from "../common";
import {serviceHandler} from "../scripts/serviceHandler";
import {userService} from "../services";
import {useCommon} from "../hooks/useCommon";

export default function CardExperience(props: { experience: ExperienceModel, nameProp: [string | undefined, Dispatch<SetStateAction<string | undefined>>], categoryProp: [string | undefined, Dispatch<SetStateAction<string | undefined>>], fav: boolean }) {
    const {t} = useTranslation()
    const {experience, nameProp, categoryProp, fav} = props
    const [searchParams, setSearchParams] = useSearchParams();
    const navigate = useNavigate()

    const {getUser} = useAuth()
    const user = getUser()

    const [isLoadingImg, setIsLoadingImg] = useState(false)
    const [isFav, setIsFav] = useState(false)

    const common = useCommon() || {};
    const getCountry = common.getCountry || (() => {});
    const getCategory = common.getCategory || (() => {});
    const getCity = common.getCity || (() => {});

    const [category, setCategory] = useState<CategoryModel | undefined>(undefined)
    const [city, setCity] = useState<CityModel | undefined>(undefined)
    const country = getCountry()

    const getCityFromCommonContext = async () => {
        try {
            const cityId = parseInt(experience.cityUrl?.match(/(\d+)$/)?.[0] ?? '0', 10);
            setCity(await getCity(cityId) || undefined);
        } catch (error) {
            navigate('/error', {state: {code: 500, message: 'Server error',}, replace: true,})
        }
    };

    useEffect(() => {

        const categoryId = parseInt(experience.categoryUrl?.match(/(\d+)$/)?.[0] ?? '0', 10);
        setCategory(getCategory(categoryId) || undefined)

        getCityFromCommonContext();

        if (user !== null) {
            if (fav) {
                setIsFav(true)
            } else {
                serviceHandler(
                    userService.isExperienceFav(user.userId, experience.id),
                    navigate, (isFavResponse) => {
                        setIsFav(isFavResponse.favourite)
                    },
                    () => {
                    },
                    () => {
                        setIsFav(false)
                    }
                )
            }
        }

    }, [])

    function clearNavBar() {
        searchParams.delete("category")
        searchParams.delete("name")
        setSearchParams(searchParams)
        categoryProp[1]("")
        nameProp[1]("")
    }

    return (

        <div className="card card-experience mx-3 my-2 p-0">

            <div className="card-link h-100 d-flex flex-column">
                <div>
                    <DataLoader spinnerMultiplier={2} isLoading={isLoadingImg}>
                        <img className={`card-img-top container-fluid ${experience.hasImage ? "p-0" : "p-4"} mw-100`}
                             alt={`Imagen ${category?.name}`}
                            src={experience.hasImage ? experience.imageUrl : categoryImages[category?.name as CategoryName] }/>
                    </DataLoader>

                    <div className="card-body container-fluid p-2">
                        <div className="title-link">
                            <Link to={"/experiences/" + experience.id}>
                                <h2 className="experience card-title container-fluid p-0 text-truncate">
                                    {experience.name}
                                </h2>
                            </Link>

                        </div>
                        <div className="card-text container-fluid p-0">
                            <p className="text-truncate">
                                {experience.description}
                            </p>
                            <h5 className="text-truncate">
                                {experience.address}, {city?.name}, {country?.name}
                            </h5>
                            <Price price={experience.price}/>
                        </div>
                    </div>
                </div>

                <div className="card-body container-fluid d-flex p-2 mb-1 align-items-end">
                    <h5 className="mb-0">
                        {t('Experience.reviews', {reviewCount: experience.reviewCount})}
                    </h5>
                    <StarRating score={experience.score}/>
                </div>

                <div className="btn-fav">
                    {user ?
                        <div>
                            {isFav ?
                                <IconButton onClick={() => setFavExperience(user.userId, experience, false, setIsFav, t)} aria-label={t("AriaLabel.fav")} title={t("AriaLabel.fav")}>
                                    <Favorite className="fa-heart heart-color"/>
                                </IconButton>
                                :
                                <IconButton onClick={() => setFavExperience(user.userId, experience, true, setIsFav, t)} aria-label={t("AriaLabel.fav")} title={t("AriaLabel.fav")}>
                                    <FavoriteBorder className="fa-heart"/>
                                </IconButton>
                            }
                        </div>
                        :
                        <div>
                            <IconButton aria-label={t("AriaLabel.fav")} title={t("AriaLabel.fav")}
                                        onClick={() => {
                                            clearNavBar();
                                            navigate("/login", {replace: true});
                                            showToast(t('Experience.toast.favNotSigned'), "error")
                                        }}>
                                <FavoriteBorder className="fa-heart"/>
                            </IconButton>
                        </div>
                    }
                </div>
                {!experience.observable &&
                    <div className="card-body p-0 d-flex justify-content-center">
                        <h5 className="obs-info align-self-center" style={{fontSize: "small"}}>
                            {t('Experience.notVisible')}
                        </h5>
                    </div>
                }
            </div>
        </div>
    )
}