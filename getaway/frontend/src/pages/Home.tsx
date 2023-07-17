import {useTranslation} from "react-i18next"
import "../common/i18n/index"
import Carousel from "../components/Carousel"
import {useEffect, useState} from "react"
import {AnonymousRecommendationsModel, UserRecommendationsModel} from "../types"
import {useAuth} from "../hooks/useAuth"
import {useNavigate} from "react-router-dom"
import {serviceHandler} from "../scripts/serviceHandler"
import {userService, experienceService} from "../services"
import DataLoader from "../components/DataLoader"

export default function Home() {

    const {t} = useTranslation()
    const navigate = useNavigate()

    const {isLogged, getUser} = useAuth()
    const user = getUser()

    const [anonymousRecommendations, setAnonymousRecommendations] = useState<AnonymousRecommendationsModel | undefined>(undefined)
    const [userRecommendations, setUserRecommendations] = useState<UserRecommendationsModel | undefined>(undefined)
    const [isLoading, setIsLoading] = useState(false)

    useEffect(() => {
        setIsLoading(true)
        if (isLogged()) {
            serviceHandler(
                userService.getUserRecommendations(user?.id),
                navigate, (userRecommendations) => {
                    setUserRecommendations(userRecommendations)
                },
                () => {
                    setIsLoading(false)
                },
                () => setUserRecommendations(undefined)
            )
        } else {
            serviceHandler(
                experienceService.getExperiencesRecommendations(),
                navigate, (anonymousLandingPage) => {
                    setAnonymousRecommendations(anonymousLandingPage)
                },
                () => {
                    setIsLoading(false)
                },
                () => {
                    setAnonymousRecommendations(undefined)
                }
            )
        }
        document.title = `${t('PageName')}`
    }, [isLogged()])

    return (
        <DataLoader spinnerMultiplier={2} isLoading={isLoading}>
            <div className="my-2">
                {anonymousRecommendations !== undefined &&
                    <>
                        <Carousel title={t('Landing.anonymous.aventura')} experiences={anonymousRecommendations?.aventura} show={3}/>
                        <Carousel title={t('Landing.anonymous.gastronomia')} experiences={anonymousRecommendations?.gastronomia} show={3}/>
                        <Carousel title={t('Landing.anonymous.hoteleria')} experiences={anonymousRecommendations?.hoteleria} show={3}/>
                        <Carousel title={t('Landing.anonymous.relax')} experiences={anonymousRecommendations?.relax} show={3}/>
                        <Carousel title={t('Landing.anonymous.vida_nocturna')} experiences={anonymousRecommendations?.vida_nocturna} show={3}/>
                        <Carousel title={t('Landing.anonymous.historico')} experiences={anonymousRecommendations?.historico} show={3}/>
                    </>
                }
                {userRecommendations !== undefined &&
                    <>
                        <Carousel title={t('Landing.user.viewed')} experiences={userRecommendations?.viewed} show={3}/>
                        <Carousel title={t('Landing.user.recommendedByFavs')} experiences={userRecommendations?.recommendedByFavs} show={3}/>
                        <Carousel title={t('Landing.user.recommendedByReviews')} experiences={userRecommendations?.recommendedByReviews} show={3}/>
                    </>
                }
            </div>
        </DataLoader>

    )

}