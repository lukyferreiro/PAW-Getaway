import {useLocation, useNavigate, Location, To,} from "react-router-dom";
import React, {useEffect} from "react";
import {useAuth} from "../hooks/useAuth";

function getCorrectPrivilegeRoute(location: Location): To {
    const startsWithUserOrError = location.pathname.startsWith("/user") || location.pathname.startsWith("/error")
    if (startsWithUserOrError) {
        return location
    }
    return "/";
}

export default function RequireAuth({children}: { children: JSX.Element }) {
    const {signIn, signOut, getUser} = useAuth()
    const location = useLocation()
    const navigate = useNavigate()
    const readUser = getUser()
    const correctRoute = getCorrectPrivilegeRoute(location)

    useEffect(() => {
        if (readUser) {
            signIn(readUser, () => navigate(correctRoute))
        }
        else {
            signOut(()=>{})
        }

    }, [])

    return children;
}