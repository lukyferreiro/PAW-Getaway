import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {ExperienceModel, ReviewModel} from "../types";
import {useAuth} from "../hooks/useAuth";
import {serviceHandler} from "../scripts/serviceHandler";
import {userService} from "../services";
import CardExperience from "../components/CardExperience";
import CardReview from "../components/CardReview";

export default function UserReviews() {
    const {t} = useTranslation();
    const navigate = useNavigate()

    const [reviews, setReviews] = useState<ReviewModel[]>(new Array(0))
    const {user} = useAuth();
    const [page, setPage] = useState(1);

    useEffect(() => {
        serviceHandler(
            userService.getUserReviews(user? user.id : -1 , page ),
            navigate, (reviews) => {
                setReviews(reviews.getContent())
            },
            () => {}
        )
    }, [])

    return (
        <div>
            <div className="container-fluid p-0 my-3 d-flex flex-column justify-content-center">

                { reviews.length == 0 ?
                    <div className="d-flex justify-content-around align-content-center">
                        <h2>{t('User.noReviews')}</h2>
                    </div> :
                    <div>
                        <div className="d-flex justify-content-around align-content-center">
                            <h3 className="title">
                                {t('User.reviewsTitle')}
                            </h3>
                        </div>

                        <div className="mx-5 my-2 d-flex flex-wrap justify-content-center align-content-center">
                            <div className="pl-5 pr-2 w-50"
                                 style={{minWidth: "400px", minHeight: "150px", height: "fit-content"}}>
                                {reviews.map((review) => (
                                    <CardReview reviewModel={review} isEditing={true} key={review.id}/>
                                ))}
                            </div>
                        </div>
                    </div>
                }
            </div>
        </div>
    );

}