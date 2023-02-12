import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {useEffect, useState} from "react";
import {OrderByModel} from "../types";
import {useNavigate} from "react-router-dom";
import {serviceHandler} from "../scripts/serviceHandler";
import {experienceService} from "../services";

export default function OrderDropdown(props: { orders: OrderByModel[] }) {

    const {t} = useTranslation();
    const navigate = useNavigate()
    const {orders} = props

    return (
        <div className="d-flex justify-content-center align-content-center">
            <div>
                <button className="btn btn-search my-2 dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                    {t('Order.title')}
                    <b>
                        {t(`Order.${orders[0].order.toString()}`)}
                    </b>
                </button>
                <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    {orders.map((order) => (
                        <p className="dropdown-item">
                            {t(`Order.${order.toString()}`)}
                        </p>
                    ))}
                </ul>
            </div>
        </div>
    );

}