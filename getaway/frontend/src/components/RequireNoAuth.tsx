import React, {useEffect} from "react";
import {useAuth} from "../hooks/useAuth";
import {useNavigate} from "react-router-dom";
import {showToast} from "../scripts/toast";
import {useTranslation} from "react-i18next";

export default function RequireNoAuth({children}: { children: JSX.Element }) {
    const {t} = useTranslation()
    const {getUser} = useAuth()
    const readUser = getUser()
    const navigate = useNavigate()

    useEffect(() => {
        if (readUser) {
            navigate("/", {replace: true});
            showToast(t('User.toast.alreadySigned'), 'error')
        }

    }, [])

    return children;
}