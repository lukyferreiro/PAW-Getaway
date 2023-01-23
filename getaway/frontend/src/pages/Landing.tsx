import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {Outlet} from "react-router-dom";

export default function Landing() {

    const {t} = useTranslation();

    return (
        <Outlet/>
    )

}