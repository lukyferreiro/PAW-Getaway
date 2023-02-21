import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {OrderByModel} from "../types";
import {Dispatch, SetStateAction} from "react";
import {useSearchParams} from "react-router-dom";

export default function OrderDropdown(props: { orders: OrderByModel[], order: [string, Dispatch<SetStateAction<string>>], currentPage: [number , Dispatch<SetStateAction<number>>] }) {

    const {t} = useTranslation()
    const {orders, order, currentPage} = props

    const [searchParams, setSearchParams] = useSearchParams();

    function changeOrder(queryOrder: string) {
        searchParams.set("page", "1")
        searchParams.set("order", queryOrder)
        setSearchParams(searchParams)
        currentPage[1](1)
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