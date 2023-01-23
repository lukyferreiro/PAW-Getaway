import {useTranslation} from "react-i18next";
import "../common/i18n/index";

export default function Footer() {

    const {t} = useTranslation();
    const date = new Date().getFullYear();

    return (
        <div className="footer container-fluid p-0 mt-auto d-flex justify-content-center align-items-center font-weight-bold">
            {t('Copyright', {year: date})}
        </div>
    );
}