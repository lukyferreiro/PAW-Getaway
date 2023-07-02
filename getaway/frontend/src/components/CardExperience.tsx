import {useTranslation} from "react-i18next";
import "../common/i18n/index"
import {Link, useNavigate, useSearchParams} from 'react-router-dom'
import {ExperienceModel} from "../types";
import StarRating from "./StarRating";
import React, {Dispatch, SetStateAction, useState} from "react";
import {IconButton} from "@mui/material";
import {useAuth} from "../hooks/useAuth";
import {Favorite, FavoriteBorder} from "@mui/icons-material";
import {setFavExperience} from "../scripts/experienceOperations";
import Price from "./Price";
import DataLoader from "./DataLoader";

export default function CardExperience(props: { experience: ExperienceModel, nameProp: [string | undefined, Dispatch<SetStateAction<string | undefined>>], categoryProp: [string | undefined, Dispatch<SetStateAction<string | undefined>>]}) {
    const {t} = useTranslation()
    const {experience, nameProp, categoryProp} = props
    const [searchParams, setSearchParams] = useSearchParams();
    const navigate = useNavigate()

    const {user} = useAuth()

    const [isLoadingImg, setIsLoadingImg] = useState(false)
    const [fav, setFav] = useState(experience.fav)

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
                        <img className={`card-img-top container-fluid ${experience.hasImage ? "p-0" : "p-4"} mw-100`} alt={`Imagen ${experience.category.name}`}
                             src={experience.hasImage ? experience.imageUrl : `./images/${experience.category.name}.svg`}/>
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
                                {experience.address}, {experience.city.name}, {experience.country.name}
                            </h5>
                            <Price price={experience.price}/>
                        </div>
                    </div>
                </div>

                <div className="card-body container-fluid d-flex p-2 mb-1 align-items-end">
                    <h5 className="mb-1">
                        {t('Experience.reviews', {reviewCount: experience.reviewCount})}
                    </h5>
                    <StarRating score={experience.score}/>
                </div>

                <div className="btn-fav">
                    {user ?
                        <div>
                            {fav ?
                                <IconButton onClick={() => setFavExperience(experience, false, setFav, t)}>
                                    <Favorite className="fa-heart heart-color"/>
                                </IconButton>
                                :
                                <IconButton onClick={() => setFavExperience(experience, true, setFav, t)}>
                                    <FavoriteBorder className="fa-heart"/>
                                </IconButton>
                            }
                        </div>
                        :
                        <div>
                            <IconButton onClick={() => {clearNavBar(); navigate("/login")}}>
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