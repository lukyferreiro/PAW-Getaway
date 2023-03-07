import {useTranslation} from "react-i18next";
import "../common/i18n/index"
import {Link, useNavigate} from 'react-router-dom'
import {ExperienceModel} from "../types";
import StarRating from "./StarRating";
import React, {useEffect, useState} from "react";
import {serviceHandler} from "../scripts/serviceHandler";
import {experienceService} from "../services";
import {IconButton} from "@mui/material";
import {useAuth} from "../hooks/useAuth";
import {Favorite, FavoriteBorder} from "@mui/icons-material";
import {setFavExperience} from "../scripts/experienceOperations";
import Price from "./Price";
import DataLoader from "./DataLoader";

export default function CardExperience(props: { experience: ExperienceModel; }) {

    const {t} = useTranslation()
    const {experience} = props
    const navigate = useNavigate()

    const {user} = useAuth()

    const [experienceImg, setExperienceImg] = useState<string | undefined>(undefined)
    const [isLoadingImg, setIsLoadingImg] = useState(false)
    const [fav, setFav] = useState(experience.fav)

    useEffect(() => {
        if (experience.hasImage) {
            setIsLoadingImg(true)
            serviceHandler(
                experienceService.getExperienceImage(experience?.id),
                navigate, (experienceImg) => {
                    setExperienceImg(experienceImg.size > 0 ? URL.createObjectURL(experienceImg) : undefined)
                },
                () => {
                    setIsLoadingImg(false)
                },
                () => {
                    setIsLoadingImg(false)
                }
            )
        }
    }, [])

    return (

        <div className="card card-experience mx-3 my-2 p-0">

            <div className="card-link h-100 d-flex flex-column">
                <div>
                    <DataLoader spinnerMultiplier={2} isLoading={isLoadingImg}>
                        <img className={`card-img-top container-fluid ${experienceImg ? "p-0" : "p-4"} mw-100`} alt={`Imagen ${experience.category.name}`}
                             src={experienceImg ? experienceImg : `./images/${experience.category.name}.svg`}/>
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
                            <IconButton onClick={() => navigate("/login")}>
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