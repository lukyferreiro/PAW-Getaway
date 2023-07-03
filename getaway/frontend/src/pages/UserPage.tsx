import {Location, Navigate, Outlet, To, useLocation} from "react-router-dom"
import {useAuth} from "../hooks/useAuth"
import {showToast} from "../scripts/toast";
import {useEffect} from "react";
import {useTranslation} from "react-i18next";

function getCorrectPrivilegeRoute(location: Location): To {
    const startsWithUserOrError = location.pathname.startsWith("/user") || location.pathname.startsWith("/error")
    if (startsWithUserOrError) {
        return location
    }
    return "/"
}

export default function UserPage() {
    const {t} = useTranslation()
    const {isLogged} = useAuth()
    const location = useLocation()
    const correctRoute = getCorrectPrivilegeRoute(location)

    useEffect(() => {
        if (!isLogged()) {
            showToast(t('User.toast.notSigned'), 'error');
        }
    }, []);

    if (!isLogged()) {
        return <Navigate to="/login" state={{from: correctRoute}} replace/>
    }

    if (getCorrectPrivilegeRoute(location) !== location) {
        return <Navigate to={correctRoute}/>
    }

    return (
        <Outlet/>
    )

}

