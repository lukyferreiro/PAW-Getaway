import {useTranslation} from "react-i18next";
import "../common/i18n/index"
import {Link, useNavigate} from 'react-router-dom'
import {ExperienceModel} from "../types";
import StarRating from "./StarRating";
import React, {useEffect, useState} from "react";
import {serviceHandler} from "../scripts/serviceHandler";
import {experienceService} from "../services";

export default function CardExperience(props: { experience: ExperienceModel; }) {
    const {t} = useTranslation()
    const {experience} = props
    const navigate = useNavigate()
    const [experienceImg, setexperienceImg] = useState<string | undefined>(undefined)
    const [isLoadingImg, setIsLoadingImg] = useState(false)

    useEffect(() => {
        setIsLoadingImg(true)
        serviceHandler(
            experienceService.getExperienceImage(experience?.id),
            navigate, (experienceImg) => {
                setexperienceImg(experienceImg.size > 0 ? URL.createObjectURL(experienceImg) : undefined)
            },
            () => setIsLoadingImg(false)
        )
    }, [])

    return (

        <div className="card card-experience mx-3 my-2 p-0">

            <div className="btn-fav">
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
            </div>

            <div className="card-link h-100 d-flex flex-column">
                <div>
                    <img className="card-img-top container-fluid p-4 mw-100" alt={`Imagen ${experience.category.name}`}
                         src={experienceImg ? experienceImg : `./images/${experience.category.name}.svg`}/>

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
                                {experience.address}
                            </h5>
                            {
                                (experience.price === undefined ?
                                    <div>
                                        <h6>
                                            {t('Experience.price.null')}
                                        </h6>
                                    </div>

                                    :
                                    (experience.price == 0 ?
                                            <div>
                                                <h6>
                                                    {t('Experience.price.free')}
                                                </h6>
                                            </div>
                                            :
                                            <div>
                                                <h6>
                                                    {t('Experience.price.exist', {price: experience.price})}
                                                </h6>
                                            </div>
                                    ))
                            }
                        </div>
                    </div>
                </div>

                <div className="card-body container-fluid d-flex p-2 mb-1 align-items-end">
                    <h5 className="mb-1">
                        {t('Experience.reviews', {reviewCount: experience.reviewCount})}
                    </h5>
                    <StarRating score={experience.score}/>
                </div>
                {!experience.observable &&  <div className="card-body p-0 d-flex justify-content-center">
                    <h5 className="obs-info align-self-center" style={{fontSize: "small"}}>
                        {t('Experience.notVisible')}
                    </h5>
                </div>}
                </div>
        </div>
    )
}