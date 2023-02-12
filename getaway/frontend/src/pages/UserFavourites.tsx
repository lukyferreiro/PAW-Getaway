import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import React, {useEffect, useState} from "react";
import {ExperienceModel, OrderByModel} from "../types";
import {useAuth} from "../hooks/useAuth";
import {serviceHandler} from "../scripts/serviceHandler";
import {experienceService, userService} from "../services";
import {useLocation, useNavigate} from "react-router-dom";
import CardExperience from "../components/CardExperience";
import {usePagination} from "../hooks/usePagination";
import Pagination from "../components/Pagination";
import OrderDropdown from "../components/OrderDropdown";

export default function UserFavourites() {

    const {t} = useTranslation();
    const navigate = useNavigate()
    const location = useLocation()

    const {user} = useAuth();

    const [favExperiences, setFavExperiences] = useState<ExperienceModel[]>(new Array(0))
    const [orders, setOrders] = useState<OrderByModel[]>(new Array(0))
    const [order, setOrder] = useState("OrderByAZ");

    const [maxPage, setMaxPage] = useState(1)
    const [currentPage] = usePagination()

    useEffect(() => {
        serviceHandler(
            experienceService.getUserOrderByModels(),
            navigate, (orders) => {
                setOrders(orders)
            },
            () => {
            }
        )
        serviceHandler(
            userService.getUserFavExperiences(user ? user.id : -1, order, currentPage),
            navigate, (experiences) => {
                setFavExperiences(experiences.getContent())
                setMaxPage(experiences ? experiences.getMaxPage() : 1)
            },
            () => {
            }
        )
    }, [currentPage, favExperiences])

    return (
        <div className="container-fluid p-0 my-3 d-flex flex-column justify-content-center">
            {favExperiences.length == 0 ?
                <div className="my-auto d-flex justify-content-center align-content-center">
                    <h2 className="title">
                        {t('User.noFavs')}
                    </h2>
                </div>
                :
                <>
                    <div className="d-flex justify-content-center align-content-center">
                        <div style={{margin: "0 auto 0 20px", flex: "1"}}>
                            <OrderDropdown orders={orders} key={1}/>
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
    );

}