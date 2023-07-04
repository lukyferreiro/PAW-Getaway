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
    const [isLoading, setIsLoading] = useState(false)

    const {isLogged, getUser, isVerified} = useAuth()
    const isLoggedValue = isLogged()
    const isVerifiedValue = isVerified()
    const user = getUser()

    const [maxPage, setMaxPage] = useState(1)
    const currentPage = useState<number>(parseInt(getQueryOrDefault(query, "page", "1")))

    useEffect(() => {
        setIsLoading(true)
        serviceHandler(
            experienceService.getExperienceById(parseInt(experienceId ? experienceId : '-1'), true),
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

    //TODO: Add different dataloader
    useEffect(() => {
        //TODO chequear, cuando refresheas en una pagina que no tiene reseñas, te redifige a login y hace este pedido igual
        //No deberia hacerse pq reviewCount es igual a 0, pero sospecho que entra igual pq experience == undefined
        if (experience?.reviewCount !== 0) {
            serviceHandler(
                experienceService.getExperienceReviews(parseInt(experienceId ? experienceId : '-1'), currentPage[0]),
                navigate, (fetchedExperienceReviews) => {
                    setReviews(fetchedExperienceReviews.getContent())
                    setMaxPage(fetchedExperienceReviews ? fetchedExperienceReviews.getMaxPage() : 1)
                    searchParams.set("page", currentPage[0].toString())
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
            navigate("/login")
            showToast(t('ReviewForm.toast.forbidden.noUser'), 'error')
        } else if (isLoggedValue && !isVerifiedValue) {
            clearNavBar()
            navigate("/user/profile")
            showToast(t('ReviewForm.toast.forbidden.notVerified'), 'error')
        } else {
            navigate(`/experiences/${experienceId}/reviewForm`)
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