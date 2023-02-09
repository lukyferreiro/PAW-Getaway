import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import React, {useEffect, useState} from "react";
import {ExperienceModel} from "../types";
import {useAuth} from "../hooks/useAuth";
import {serviceHandler} from "../scripts/serviceHandler";
import {userService} from "../services";
import {useNavigate} from "react-router-dom";
import CardExperience from "../components/CardExperience";

export default function UserFavourites() {

    const {t} = useTranslation();
    const navigate = useNavigate()

    const [favExperiences, setFavExperiences] = useState<ExperienceModel[]>(new Array(0))
    const {user} = useAuth();
    const [page, setPage] = useState(1);
    const [order, setOrder] = useState("OrderByAZ");

    useEffect(() => {
        serviceHandler(
            userService.getUserFavExperiences(user? user.id : -1 , order, page ),
            navigate, (experiences) => {
                setFavExperiences(experiences.getContent())
            },
            () => {}
        )
    }, [])
    //TODO: add page and order to deps

    return (
        <div>
            { favExperiences.length == 0 ?
                <div className="my-auto d-flex justify-content-center align-content-center">
                    <h2>{t('User.noFavs')}</h2>
                </div> :

                <div>
                    {/*TODO add order dropdown y pagination*/}
                    <div className="d-flex justify-content-center align-content-center">

                        <h3 className="title m-0">
                            {t('User.favsTitle')}
                        </h3>
                        <div style={{margin: "0 20px 0 auto", flex: "1"}}/>
                    </div>

                    <div className="container-fluid my-3 d-flex flex-wrap justify-content-center">

                        <div className="pl-5 pr-2 w-50"
                             style={{minWidth: "400px", minHeight: "150px", height: "fit-content"}}>
                            {favExperiences.map((experience) => (
                                <CardExperience experience={experience} key={experience.id}/>
                            ))}
                        </div>
                    </div>
                </div>
            }
        </div>
    );

}