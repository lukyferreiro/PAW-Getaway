import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {ExperienceModel, ReviewModel} from "../types";
import React, {useEffect, useState} from "react";
import {Link, useLocation, useNavigate, useParams} from "react-router-dom";
import CardExperienceDetails from "../components/CardExperienceDetails";
import CardReview from "../components/CardReview";
import {serviceHandler} from "../scripts/serviceHandler";
import {experienceService} from "../services";
import {useAuth} from "../hooks/useAuth";
import Pagination from "../components/Pagination";
import DataLoader from "../components/DataLoader";

export default function ExperienceDetails() {

    const {t} = useTranslation()
    const navigate = useNavigate()
    const location = useLocation()

    const [experience, setExperience] = useState<ExperienceModel | undefined>(undefined)
    const [reviews, setReviews] = useState<ReviewModel[]>(new Array(0))
    const {experienceId} = useParams()
    const [isLoading, setIsLoading] = useState(false)

    const {user} = useAuth()
    let isVerified = localStorage.getItem("isVerified") === 'true'

    const [maxPage, setMaxPage] = useState(1)
    const currentPage = useState<number>(1)

    useEffect(() => {
        setIsLoading(true)
        serviceHandler(
            experienceService.getExperienceById(parseInt(experienceId ? experienceId : '-1')),
            navigate, (experience) => {
                setExperience(experience)
            },
            () => {
                setIsLoading(false)
            },
            () => {
                setExperience(undefined)
            }
        );
    }, []);

    //TODO: Add different dataloader
    useEffect(()=> {
        serviceHandler(
            experienceService.getExperienceReviews(parseInt(experienceId ? experienceId : '-1'), currentPage[0]),
            navigate, (fetchedExperienceReviews) => {
                setReviews(fetchedExperienceReviews.getContent())
                setMaxPage(fetchedExperienceReviews ? fetchedExperienceReviews.getMaxPage() : 1)
            },
            () => {
            },
            () => {
                setReviews(new Array(0))
                setMaxPage(0)
            }
        );
    }, [currentPage[0]])

    return (
        <DataLoader spinnerMultiplier={2} isLoading={isLoading}>
            <div className="container-fluid px-5 d-flex justify-content-center align-content-center flex-column">

                <div className="card mx-5 my-3 px-5 pt-4">
                    <div className="d-flex justify-content-center align-content-center">
                        <h1 className="text-center" style={{wordBreak: "break-all"}}>
                            {experience?.name}
                        </h1>
                    </div>
                    {experience !== undefined &&
                        <CardExperienceDetails experience={experience} categoryModel={experience.category} isEditing={experience.user.id === user?.id}/>
                    }
                </div>

                {/*/!*REVIEWS*!/*/}
                <div className="mx-5 my-3">
                    <div className="d-flex justify-content-between align-content-center">
                        <h2 className="align-self-center">
                            {t('ExperienceDetail.review')}
                        </h2>

                        <Link to={`/experiences/${experienceId}/reviewForm`}>
                            <button type="button" className='btn button-primary'
                                    onClick={() => {
                                        if (user === null) {
                                            navigate("/login")
                                        }
                                        if (!isVerified) {
                                            navigate("/user/profile")
                                        }
                                    }}
                            >
                                {t('ExperienceDetail.writeReview')}
                            </button>
                        </Link>
                    </div>
                </div>

                <div className="d-flex mb-3 flex-column">
                    <div className="mx-5 my-2 d-flex flex-wrap">
                        {reviews.length == 0 ?
                            <div className="d-flex justify-content-center mb-2" style={{fontSize: "x-large"}}>
                                {t('ExperienceDetail.noReviews')}
                            </div>
                            :
                            <>
                                {reviews.map((review) => (
                                    <div className="pl-5 pr-2 w-50"
                                         style={{minWidth: "400px", minHeight: "150px", height: "fit-content"}} key={review.id}>
                                        <CardReview reviewModel={review} isEditing={false}/>
                                    </div>
                                ))}
                            </>
                        }

                    </div>
                </div>

                {reviews.length != 0 && maxPage > 1 &&
                    <div className="d-flex justify-content-center align-content-center">
                        <Pagination
                            maxPage={maxPage}
                            currentPage={currentPage}
                        />
                    </div>
                }
            </div>
        </DataLoader>

    );
}