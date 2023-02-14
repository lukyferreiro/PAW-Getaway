import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {useLocation, useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {ReviewModel} from "../types";
import {useAuth} from "../hooks/useAuth";
import {serviceHandler} from "../scripts/serviceHandler";
import {userService} from "../services";
import CardReview from "../components/CardReview";
import {usePagination} from "../hooks/usePagination";
import Pagination from "../components/Pagination";
import DataLoader from "../components/DataLoader";

export default function UserReviews() {

    const {t} = useTranslation()
    const navigate = useNavigate()
    const location = useLocation()

    const {user} = useAuth()

    const [reviews, setReviews] = useState<ReviewModel[]>(new Array(0))
    const [isLoading, setIsLoading] = useState(false)

    const [maxPage, setMaxPage] = useState(1)
    const [currentPage] = usePagination()

    useEffect(() => {
        setIsLoading(true)
        serviceHandler(
            userService.getUserReviews(user ? user.id : -1, currentPage),
            navigate, (reviews) => {
                setReviews(reviews.getContent())
                setMaxPage(reviews ? reviews.getMaxPage() : 1)
            },
            () => {
                setIsLoading(false)
            },
            () => {
                setReviews(new Array(0))
                setMaxPage(1)
            }
        )
    }, [currentPage])

    return (
        <DataLoader spinnerMultiplier={2} isLoading={isLoading}>
            <div className="container-fluid p-0 my-3 d-flex flex-column justify-content-center">

                {reviews.length === 0 ?
                    <div className="d-flex justify-content-around align-content-center">
                        <h2 className="title">{t('User.noReviews')}</h2>
                    </div>
                    :
                    <>
                        <div className="d-flex justify-content-around align-content-center">
                            <h3 className="title">
                                {t('User.reviewsTitle')}
                            </h3>
                        </div>

                        <div className="mx-5 my-2 d-flex flex-wrap justify-content-center align-content-center">
                            {reviews.map((review) => (
                                <div style={{minWidth: "700px", maxWidth: "700px", height: "auto"}}>
                                    <CardReview reviewModel={review} isEditing={true} key={review.id}/>
                                </div>
                            ))}
                        </div>

                        <div className="mt-auto d-flex justify-content-center align-items-center">
                            {maxPage > 1 && (
                                <Pagination
                                    currentPage={currentPage}
                                    maxPage={maxPage}
                                    baseURL={location.pathname}
                                    // TODO check baseUrl
                                />
                            )}
                        </div>
                    </>
                }
            </div>
        </DataLoader>
    );

}