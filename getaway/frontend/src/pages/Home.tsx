import {useTranslation} from "react-i18next"
import "../common/i18n/index"
import Carousel from "../components/Carousel"
import {useEffect, useState} from "react"
import {useAuth} from "../hooks/useAuth"
import {useNavigate} from "react-router-dom"
import {serviceHandler} from "../scripts/serviceHandler"
import {userService, experienceService} from "../services"
import DataLoader from "../components/DataLoader"
import {ExperienceModel} from "../types";

export default function Home() {

    const {t} = useTranslation()
    const navigate = useNavigate()

    const {isLogged, getUser} = useAuth()
    const user = getUser()

    const [experiencesViewed, setExperiencesViewed] = useState<ExperienceModel[]>(new Array(0))
    const [experiencesByFavs, setExperiencesByFavs] = useState<ExperienceModel[]>(new Array(0))
    const [experiencesByReviews, setExperiencesByReviews] = useState<ExperienceModel[]>(new Array(0))
    const [experiencesAdventure, setExperiencesAdventure] = useState<ExperienceModel[]>(new Array(0))
    const [experiencesGastronomy, setExperiencesGastronomy] = useState<ExperienceModel[]>(new Array(0))
    const [experiencesHotels, setExperiencesHotels] = useState<ExperienceModel[]>(new Array(0))
    const [experiencesRelax, setExperiencesRelax] = useState<ExperienceModel[]>(new Array(0))
    const [experiencesNight, setExperiencesNight] = useState<ExperienceModel[]>(new Array(0))
    const [experiencesHistoric, setExperiencesHistoric] = useState<ExperienceModel[]>(new Array(0))
    const [isLoading, setIsLoading] = useState(false)

    useEffect(() => {
        setIsLoading(true)
        if (isLogged()) {
            serviceHandler(
                userService.getUserViewedExperiences(user?.userId),
                navigate, (experiencesViewed) => {
                    setExperiencesViewed(experiencesViewed)
                },
                () => {
                },
                () => setExperiencesViewed(new Array(0))
            )
            serviceHandler(
                userService.getUserRecommendationsByFavs(user?.userId),
                navigate, (experiencesByFavs) => {
                    setExperiencesByFavs(experiencesByFavs)
                },
                () => {
                },
                () => setExperiencesByFavs(new Array(0))
            )
            serviceHandler(
                userService.getUserRecommendationsByReviews(user?.userId),
                navigate, (experiencesByReviews) => {
                    setExperiencesByReviews(experiencesByReviews)
                },
                () => {
                },
                () => setExperiencesByReviews(new Array(0))
            )
            setIsLoading(false)
        } else {
            serviceHandler(
                experienceService.getExperiencesBestCategory("Aventura"),
                navigate, (experiences) => {
                    setExperiencesAdventure(experiences)
                },
                () => {
                    setIsLoading(false)
                },
                () => {
                    setExperiencesAdventure(new Array(0))
                }
            )
            serviceHandler(
                experienceService.getExperiencesBestCategory("Aventura"),
                navigate, (experiences) => {
                    setExperiencesAdventure(experiences)
                },
                () => {
                    setIsLoading(false)
                },
                () => {
                    setExperiencesAdventure(new Array(0))
                }
            )
            serviceHandler(
                experienceService.getExperiencesBestCategory("Gastronomia"),
                navigate, (experiences) => {
                    setExperiencesGastronomy(experiences)
                },
                () => {
                    setIsLoading(false)
                },
                () => {
                    setExperiencesGastronomy(new Array(0))
                }
            )
            serviceHandler(
                experienceService.getExperiencesBestCategory("Hoteleria"),
                navigate, (experiences) => {
                    setExperiencesHotels(experiences)
                },
                () => {
                    setIsLoading(false)
                },
                () => {
                    setExperiencesHotels(new Array(0))
                }
            )
            serviceHandler(
                experienceService.getExperiencesBestCategory("Relax"),
                navigate, (experiences) => {
                    setExperiencesRelax(experiences)
                },
                () => {
                    setIsLoading(false)
                },
                () => {
                    setExperiencesRelax(new Array(0))
                }
            )
            serviceHandler(
                experienceService.getExperiencesBestCategory("Vida_nocturna"),
                navigate, (experiences) => {
                    setExperiencesNight(experiences)
                },
                () => {
                    setIsLoading(false)
                },
                () => {
                    setExperiencesNight(new Array(0))
                }
            )
            serviceHandler(
                experienceService.getExperiencesBestCategory("Historico"),
                navigate, (experiences) => {
                    setExperiencesHistoric(experiences)
                },
                () => {
                    setIsLoading(false)
                },
                () => {
                    setExperiencesHistoric(new Array(0))
                }
            )
        }
        document.title = `${t('PageName')}`
    }, [isLogged()])

    return (
        <DataLoader spinnerMultiplier={2} isLoading={isLoading}>
            <div className="my-2">
                {isLogged() ?
                    <>
                        <Carousel title={t('Landing.user.viewed')} experiences={experiencesViewed} show={3}/>
                        <Carousel title={t('Landing.user.recommendedByFavs')} experiences={experiencesByFavs} show={3}/>
                        <Carousel title={t('Landing.user.recommendedByReviews')} experiences={experiencesByReviews} show={3}/>
                    </>
                    :
                    <>
                        <Carousel title={t('Landing.anonymous.aventura')} experiences={experiencesAdventure} show={3}/>
                        <Carousel title={t('Landing.anonymous.gastronomia')} experiences={experiencesGastronomy} show={3}/>
                        <Carousel title={t('Landing.anonymous.hoteleria')} experiences={experiencesHotels} show={3}/>
                        <Carousel title={t('Landing.anonymous.relax')} experiences={experiencesRelax} show={3}/>
                        <Carousel title={t('Landing.anonymous.vida_nocturna')} experiences={experiencesNight} show={3}/>
                        <Carousel title={t('Landing.anonymous.historico')} experiences={experiencesHistoric} show={3}/>
                    </>
                }
            </div>
        </DataLoader>

    )

}