import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {getQueryOrDefault, getQueryOrDefaultMultiple, useQuery} from "../hooks/useQuery";
import {Link, useNavigate} from "react-router-dom";
import {useAuth} from "../hooks/useAuth";
import {useEffect} from "react";

export default function Error() {

    const {t} = useTranslation()
    const query = useQuery()
    const navigate = useNavigate()
    const auth = useAuth()

    let error = getQueryOrDefault(query, "code", "404")
    if (error === "401") {
        auth.signOut(() => navigate("/"))
    }
    if (error === "NaN") {
        error = "404"
    }

    useEffect(() => {
        document.title = `${t('PageName')} - ${t('PageTitles.error')}`
    }, [])

    const description = getQueryOrDefaultMultiple(query, "description")

    return (
        <div className="container-fluid p-0 h-100 d-flex justify-content-center align-items-center">
            <div className="container-fluid p-0 d-flex flex-column justify-content-center align-items-center"
                 style={{width: "60%"}}>
                <h1 className="font-weight-bold m-0" style={{fontSize: "15vh"}}>
                    {t('Error.title', {errorCode: error})}
                </h1>
                <h1 className="font-weight-bold text-center" style={{fontSize: "5vh"}}>
                    <span style={{color: "red"}}>{t('Error.whoops')}</span>
                    {description}
                </h1>
                <Link to="/">
                    <button type="button" className="btn btn-error">
                        {t('Error.backBtn')}
                    </button>
                </Link>
            </div>
        </div>
    );

}