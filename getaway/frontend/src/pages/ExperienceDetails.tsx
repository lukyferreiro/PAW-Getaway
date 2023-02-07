import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {ExperienceModel, ReviewModel} from "../types";
import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import CardExperienceDetails from "../components/CardExperienceDetails";
import CardReview from "../components/CardReview";
import {serviceHandler} from "../scripts/serviceHandler";
import {experienceService} from "../services";
export default function ExperienceDetails() {

    const {t} = useTranslation();
    const navigate = useNavigate()

    const [experience, setExperience] = useState<ExperienceModel | undefined>(undefined);
    const [reviews, setReviews] = useState<ReviewModel[]>(new Array(0))
    const {experienceId} = useParams();


    useEffect(() => {
        serviceHandler(
            experienceService.getExperienceById(parseInt(experienceId ? experienceId : '-1')),
            navigate, (fetchedExperience) => {
                setExperience(fetchedExperience)
            },
            () => {}
        ) ;
        //Esto me da error 500 vaya uno a saber por que
        // serviceHandler(
        //     experienceService.getExperienceReviews(parseInt(experienceId ? experienceId : '-1')),
        //     navigate, (fetchedExperienceReviews) => {
        //         setReviews(fetchedExperienceReviews.getContent())
        //     },
        //     () => {}
        // ) ;
    }, []);


    return (
        <div className="container-fluid px-5 d-flex justify-content-center align-content-center flex-column">
            <div className="card mx-5 my-3 px-5 pt-4">
                <div className="d-flex justify-content-center align-content-center">
                    <h1 className="text-center" style={{wordBreak: "break-all"}}>
                        {experience?.name}
                    </h1>
                </div>

                {/*<CardExperienceDetails experience={experience!}*/}
                {/*                       categoryModel={experience!.category}/>*/}
            </div>

            {/*/!*REVIEWS*!/*/}
            <div className="d-flex mb-3 flex-column">
                <div className="mx-5 my-2 d-flex flex-wrap">
                    {reviews.length == 0 ?
                        <div className="d-flex justify-content-center mb-2" style={{fontSize: "x-large"}}>
                            {t('ExperienceDetail.noReviews')}
                        </div> :
                        <div className="pl-5 pr-2 w-50"
                             style={{minWidth: "400px", minHeight: "150px", height: "fit-content"}}>
                            {reviews.map((review) => (
                                <CardReview reviewModel={review} isEditing={false}/>
                            ))}
                        </div>
                    }

                </div>
            </div>
        </div>

    );
}