import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {ExperienceModel, ReviewModel} from "../types";
import React, {useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import CardExperienceDetails from "../components/CardExperienceDetails";
import CardReview from "../components/CardReview";
import {serviceHandler} from "../scripts/serviceHandler";
import {experienceService} from "../services";
import {useAuth} from "../hooks/useAuth";
export default function ExperienceDetails() {

    const {t} = useTranslation();
    const navigate = useNavigate()

    const [experience, setExperience] = useState<ExperienceModel | undefined>(undefined);
    const [reviews, setReviews] = useState<ReviewModel[]>(new Array(0))
    const {experienceId} = useParams();

    const {user} = useAuth();

    let isLogged = user !== null;
    let isVerified = user?.verified;



    useEffect(() => {
        serviceHandler(
            experienceService.getExperienceById(parseInt(experienceId ? experienceId : '-1')),
            navigate, (experience) => {
                setExperience(experience)
            },
            () => {}
        ) ;
        serviceHandler(
            experienceService.getExperienceReviews(parseInt(experienceId ? experienceId : '-1')),
            navigate, (fetchedExperienceReviews) => {
                setReviews(fetchedExperienceReviews.getContent())
            },
            () => {}
        ) ;
    }, [] );

    return (
        <div>
            <div className="card mx-5 my-3 px-5 pt-4">
                <div className="d-flex justify-content-center align-content-center">
                    <h1 className="text-center" style={{wordBreak: "break-all"}}>
                        {experience?.name}
                    </h1>
                </div>
                {
                    experience !== undefined &&
                    <CardExperienceDetails experience={experience} categoryModel={experience.category} isEditing={experience.user.id === user?.id}/>
                }
            </div>

            <div className="mx-5 my-3">
                <div className="d-flex justify-content-between align-content-center">
                    <h2 className="align-self-center">
                        {t('ExperienceDetail.review')}
                    </h2>

                    <Link to={"/experiences/" + experienceId + "/createReview"} style={{marginRight: '40px'}}>
                        <button type="button" onClick={ () => {
                            if (user === null) {
                                navigate("/login")
                            }
                            if (!isVerified) {
                                navigate("/user/profile")
                            }
                        }} className='btn button-primary'
                        >
                            {t('ExperienceDetail.writeReview')}
                        </button>
                    </Link>
                </div>
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
                                <CardReview reviewModel={review} isEditing={false} key={review.id}/>
                            ))}
                        </div>
                    }

                </div>
            </div>

        {/*    TODO: add pagination*/}

        </div>

    );
}