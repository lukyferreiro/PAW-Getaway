import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {ExperienceModel, ReviewModel} from "../types";
import React, {Dispatch, SetStateAction, useEffect, useState} from "react";
import {useNavigate, useParams, useSearchParams} from "react-router-dom";
import CardExperienceDetails from "../components/CardExperienceDetails";
import CardReview from "../components/CardReview";
import {serviceHandler} from "../scripts/serviceHandler";
import {experienceService} from "../services";
import {useAuth} from "../hooks/useAuth";
import Pagination from "../components/Pagination";
import DataLoader from "../components/DataLoader";
import {getQueryOrDefault, useQuery} from "../hooks/useQuery";
import {showToast} from "../scripts/toast";

export default function ExperienceDetails(props: { nameProp: [string | undefined, Dispatch<SetStateAction<string | undefined>>], categoryProp: [string | undefined, Dispatch<SetStateAction<string | undefined>>] }) {

    const {t} = useTranslation()
    const navigate = useNavigate()
    const query = useQuery()

    const {nameProp, categoryProp} = props
    const [searchParams, setSearchParams] = useSearchParams();

    const [experience, setExperience] = useState<ExperienceModel | undefined>(undefined)
    const [reviews, setReviews] = useState<ReviewModel[]>(new Array(0))

    const {experienceId} = useParams()
    let parsedExperienceId = -1
    if (typeof experienceId === "string" && /^\d+$/.test(experienceId)) {
        parsedExperienceId = parseInt(experienceId, 10);
    }

    const [isLoading, setIsLoading] = useState(false)

    const {isLogged, getUser, isVerified} = useAuth()
    const isLoggedValue = isLogged()
    const isVerifiedValue = isVerified()
    const user = getUser()

    const [maxPage, setMaxPage] = useState(0)
    const currentPage = useState<number>(parseInt(getQueryOrDefault(query, "page", "1")))
    const pageToShow = useState<number>(1)

    useEffect(() => {
        setIsLoading(true)
        serviceHandler(
            experienceService.getExperienceById(parsedExperienceId, true),
            navigate, (experience) => {
                setExperience(experience)
                document.title = `${t('PageName')} - ${t('PageTitles.experienceDetails', {experienceName: experience.name})}`
            },
            () => {
                setIsLoading(false)
            },
            () => {
                setExperience(undefined)
            }
        )
    }, [])

    useEffect(() => {
        if (experience?.reviewCount !== 0) {
            if ((maxPage === 0 && (pageToShow[0] <= 1 || pageToShow[0] > maxPage))
                ||
                ((pageToShow[0] >= 1 && pageToShow[0] <= maxPage) && (currentPage[0] >= 0 && currentPage[0] <= maxPage))
            ) {
                serviceHandler(
                    experienceService.getExperienceReviews(parsedExperienceId, currentPage[0]===0 ? 1 : currentPage[0]),
                    navigate, (fetchedExperienceReviews) => {
                        setReviews(fetchedExperienceReviews.getContent())
                        setMaxPage(fetchedExperienceReviews ? fetchedExperienceReviews.getMaxPage() : 0)
                        if (currentPage[0] <= 0) {
                            pageToShow[1](currentPage[0])
                            searchParams.set("page", "1")
                            currentPage[1](1)
                        } else if (currentPage[0] > fetchedExperienceReviews.getMaxPage()) {
                            pageToShow[1](currentPage[0])
                            searchParams.set("page", fetchedExperienceReviews.getMaxPage().toString())
                            currentPage[1](fetchedExperienceReviews.getMaxPage())
                        } else {
                            pageToShow[1](currentPage[0])
                            searchParams.set("page", currentPage[0].toString())
                        }
                        setSearchParams(searchParams)
                    },
                    () => {
                    },
                    () => {
                        setReviews(new Array(0))
                        setMaxPage(0)
                    }
                )
            }
        }
    }, [currentPage[0]])

    function clearNavBar() {
        searchParams.delete("category")
        searchParams.delete("name")
        setSearchParams(searchParams)
        categoryProp[1]("")
        nameProp[1]("")
    }

    function attemptAccessCreateReview() {
        if (!isLoggedValue) {
            clearNavBar()
            navigate("/login",{replace: true})
            showToast(t('ReviewForm.toast.forbidden.noUser'), 'error')
        } else if (isLoggedValue && !isVerifiedValue) {
            clearNavBar()
            navigate("/user/profile",{replace: true})
            showToast(t('ReviewForm.toast.forbidden.notVerified'), 'error')
        } else {
            navigate(`/experiences/${experienceId}/reviewForm`,{replace: true})
        }
    }

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
                        <CardExperienceDetails experience={experience} isEditing={experience.user.id === user?.id} nameProp={nameProp} categoryProp={categoryProp}/>
                    }
                </div>

                {/*/!*REVIEWS*!/*/}
                <div className="mx-5 my-3">
                    <div className="d-flex justify-content-between align-content-center">
                        <h2 className="align-self-center">
                            {t('ExperienceDetail.review')}
                        </h2>

                        <button type="button" className='btn button-primary'
                                aria-label={t("AriaLabel.writeReview")} title={t("AriaLabel.writeReview")}
                                onClick={() => attemptAccessCreateReview()}>
                            {t('ExperienceDetail.writeReview')}
                        </button>
                    </div>
                </div>

                <div className="d-flex mb-3 flex-column">
                    <div className="mx-5 my-2 d-flex flex-wrap">
                        {reviews.length === 0 ?
                            <div className="d-flex justify-content-center mb-2" style={{fontSize: "x-large"}}>
                                {t('ExperienceDetail.noReviews')}
                            </div>
                            :
                            <>
                                {reviews.map((review) => (
                                    <div className="pl-5 pr-2 w-50"
                                         style={{
                                             minWidth: "400px",
                                             minHeight: "150px",
                                             height: "fit-content"
                                         }} key={review.id}>
                                        <CardReview reviewModel={review} isEditing={false}/>
                                    </div>
                                ))}
                            </>
                        }

                    </div>
                </div>

                {experience?.reviewCount !== 0 && reviews.length !== 0 && maxPage > 1 &&
                    <div className="mb-3 d-flex justify-content-center align-items-center">
                        <Pagination
                            maxPage={maxPage}
                            currentPage={currentPage}
                            pageToShow={pageToShow}
                        />
                    </div>
                }
            </div>
        </DataLoader>

    );
}