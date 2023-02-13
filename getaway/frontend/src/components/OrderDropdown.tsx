import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {OrderByModel} from "../types";
import {useNavigate, useSearchParams} from "react-router-dom";
import {getQueryOrDefault, queryHasParam, useQuery} from "../hooks/useQuery";

export default function OrderDropdown(props: { orders: OrderByModel[] }) {

    const {t} = useTranslation();
    const navigate = useNavigate()
    const {orders} = props
    const [searchParams, setSearchParams] = useSearchParams();
    const query = useQuery()

    const orderQuery = getQueryOrDefault(query, "order", orders[0].order.toString())


    function changeOrder(queryOrder: string) {
        if (queryHasParam(query, "category")) {
            const category = getQueryOrDefault(query, "category", "")
            const name = getQueryOrDefault(query, "name", "")
            setSearchParams({category: category, name: name, order: "OrderByAZ"})
        } else {
            setSearchParams({order: queryOrder});
        }
    }

    return (
        <div className="d-flex justify-content-center align-content-center">
            <div>
                <button className="btn btn-search my-2 dropdown-toggle" type="button"
                        id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                    {t('Order.title')}
                    <b>
                        {t(`Order.${orderQuery}`)}
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