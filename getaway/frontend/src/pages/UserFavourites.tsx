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
            userService.getUserFavExperiences(user ? user.id : -1, order, page),
            navigate, (experiences) => {
                setFavExperiences(experiences.getContent())
            },
            () => {
            }
        )
    }, [])
    //TODO: add page and order to deps

    return (
        <div className="container-fluid p-0 my-3 d-flex flex-column justify-content-center">
            {favExperiences.length == 0 ?
                <div className="my-auto d-flex justify-content-center align-content-center">
                    <h2 className="title">
                        {t('User.noFavs')}
                    </h2>
                </div>
                :
                <div>
                    <div className="d-flex justify-content-center align-content-center">
                        <div style={{margin: "0 auto 0 20px;", flex: "1"}}>
                            {/*TODO add orderDropdown*/}
                            {/*<jsp:include page="/WEB-INF/components/orderDropdown.jsp">*/}
                            {/*    <jsp:param name="orderByModels" value="${orderByModels}"/>*/}
                            {/*    <jsp:param name="path" value="/user/favourites"/>*/}
                            {/*    <jsp:param name="orderPrev" value="${orderBy}"/>*/}
                            {/*</jsp:include>*/}
                        </div>
                        <h3 className="title m-0">
                            {t('User.favsTitle')}
                        </h3>
                        <div style={{margin: "0 20px 0 auto", flex: "1"}}/>
                    </div>

                    <div className="container-fluid my-3 d-flex flex-wrap justify-content-center">
                        {favExperiences.map((experience) => (
                            <CardExperience experience={experience} key={experience.id}/>
                        ))}
                    </div>

                    <div className="mt-auto d-flex justify-content-center align-items-center">
                        {/*TODO add pagination*/}
                    </div>

                </div>
            }
        </div>
    );

}