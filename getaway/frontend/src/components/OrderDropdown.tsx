import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {OrderByModel} from "../types";
import {useNavigate, useSearchParams} from "react-router-dom";
import {getQueryOrDefault, queryHasParam, useQuery} from "../hooks/useQuery";
import {Dispatch, SetStateAction} from "react";

export default function OrderDropdown(props: { orders: OrderByModel[], order: [string | undefined, Dispatch<SetStateAction<string | undefined>>] }) {

    const {t} = useTranslation()
    const {orders, order} = props

    function changeOrder(queryOrder: string) {
        order[1](queryOrder)
    }

    return (
        <div className="d-flex justify-content-center align-content-center">
            <div>
                <button className="btn btn-search my-2 dropdown-toggle" type="button"
                        id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                    {t('Order.title')}
                    <b>
                        {t(`Order.${order[0]}`)}
                    </b>
                </button>
                <ul className="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    {orders.map((order) => (
                        <p className="dropdown-item" key={order.order}
                           onClick={() => changeOrder(order.order.toString())}>
                            {t(`Order.${order.order.toString()}`)}
                        </p>
                    ))}
                </ul>
            </div>
        </div>
    );

}