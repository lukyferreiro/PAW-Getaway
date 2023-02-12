import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import Carousel from "../components/Carousel";
import CardExperience from "../components/CardExperience";
import {useEffect, useState} from "react";
import {AnonymousLandingPageModel, UserLandingPageModel} from "../types";
import {useAuth} from "../hooks/useAuth";
import {useLocation, useNavigate} from "react-router-dom";
import {serviceHandler} from "../scripts/serviceHandler";
import {experienceService} from "../services";

export default function Home() {

    const {t} = useTranslation();
    const navigate = useNavigate()

    const {user} = useAuth()

    const [anonymousLandingPage, setAnonymousLandingPage] = useState<AnonymousLandingPageModel | undefined>(undefined)
    const [userLandingPage, setUserLandingPage] = useState<UserLandingPageModel | undefined>(undefined)

    useEffect(() => {
        if (user) {
            serviceHandler(
                experienceService.getUserLandingPage(),
                navigate, (userLandingPage) => {
                    setUserLandingPage(userLandingPage)
                },
                () => {
                }
            );
        } else {
            serviceHandler(
                experienceService.getAnonymousLandingPage(),
                navigate, (anonymousLandingPage) => {
                    setAnonymousLandingPage(anonymousLandingPage)
                },
                () => {
                }
            );
        }
    }, []);

    return (
        <div className="my-2">
            {user ?
                <>
                    <Carousel title={t('Landing.user.viewed')} experiences={userLandingPage?.viewed} show={3}/>
                    <Carousel title={t('Landing.user.recommendedByFavs')} experiences={userLandingPage?.recommendedByFavs} show={3}/>
                    <Carousel title={t('Landing.user.recommendedByReviews')} experiences={userLandingPage?.recommendedByReviews} show={3}/>
                </>
                :
                <>
                    <Carousel title={t('Landing.anonymous.aventura')} experiences={anonymousLandingPage?.Aventura} show={3}/>
                    <Carousel title={t('Landing.anonymous.gastronomia')} experiences={anonymousLandingPage?.Gastronomia} show={3}/>
                    <Carousel title={t('Landing.anonymous.hoteleria')} experiences={anonymousLandingPage?.Hoteleria} show={3}/>
                    <Carousel title={t('Landing.anonymous.relax')} experiences={anonymousLandingPage?.Relax} show={3}/>
                    <Carousel title={t('Landing.anonymous.vida_nocturna')} experiences={anonymousLandingPage?.Vida_nocturna} show={3}/>
                    <Carousel title={t('Landing.anonymous.historico')} experiences={anonymousLandingPage?.Historico} show={3}/>
                </>
            }
        </div>

    )

}