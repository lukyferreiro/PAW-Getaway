import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import Carousel from "../components/Carousel";
import {useEffect, useState} from "react";
import {AnonymousLandingPageModel, UserLandingPageModel} from "../types";
import {useAuth} from "../hooks/useAuth";
import {useNavigate} from "react-router-dom";
import {serviceHandler} from "../scripts/serviceHandler";
import {experienceService} from "../services";
import DataLoader from "../components/DataLoader";

export default function Home() {

    const {t} = useTranslation()
    const navigate = useNavigate()

    const {user} = useAuth()

    const [anonymousLandingPage, setAnonymousLandingPage] = useState<AnonymousLandingPageModel | undefined>(undefined)
    const [userLandingPage, setUserLandingPage] = useState<UserLandingPageModel | undefined>(undefined)
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        setIsLoading(true);
        if (user) {
            serviceHandler(
                experienceService.getUserLandingPage(),
                navigate, (userLandingPage) => {
                    setUserLandingPage(userLandingPage)
                },
                () => {
                    setIsLoading(false)
                },
                () => setUserLandingPage(undefined)
            );
        } else {
            serviceHandler(
                experienceService.getAnonymousLandingPage(),
                navigate, (anonymousLandingPage) => {
                    setAnonymousLandingPage(anonymousLandingPage)
                },
                () => {
                    setIsLoading(false)
                },
                () => {
                    setAnonymousLandingPage(undefined)
                }
            );
        }
    }, []);

    return (
        <DataLoader spinnerMultiplier={2} isLoading={isLoading}>
            <div className="my-2">
                {user ?
                    <>
                        <Carousel title={t('Landing.user.viewed')} experiences={userLandingPage?.viewed} show={3}/>
                        <Carousel title={t('Landing.user.recommendedByFavs')} experiences={userLandingPage?.recommendedByFavs} show={3}/>
                        <Carousel title={t('Landing.user.recommendedByReviews')} experiences={userLandingPage?.recommendedByReviews} show={3}/>
                    </>
                    :
                    <>
                        <Carousel title={t('Landing.anonymous.aventura')} experiences={anonymousLandingPage?.aventura} show={3}/>
                        <Carousel title={t('Landing.anonymous.gastronomia')} experiences={anonymousLandingPage?.gastronomia} show={3}/>
                        <Carousel title={t('Landing.anonymous.hoteleria')} experiences={anonymousLandingPage?.hoteleria} show={3}/>
                        <Carousel title={t('Landing.anonymous.relax')} experiences={anonymousLandingPage?.relax} show={3}/>
                        <Carousel title={t('Landing.anonymous.vida_nocturna')} experiences={anonymousLandingPage?.vida_nocturna} show={3}/>
                        <Carousel title={t('Landing.anonymous.historico')} experiences={anonymousLandingPage?.historico} show={3}/>
                    </>
                }
            </div>
        </DataLoader>

    )

}