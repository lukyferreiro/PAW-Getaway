import {useAuth} from "../hooks/useAuth"
import {useLocation, Navigate, useNavigate, Location, To} from "react-router-dom"
import React, {useEffect} from "react"

function getCorrectPrivilegeRoute(location: Location): To {
    const startsWithUserOrError = location.pathname.startsWith("/user") || location.pathname.startsWith("/error");
    if (startsWithUserOrError) {
        return location;
    }
    return "/";
}

function RequireAuth({children}: { children: JSX.Element }) {
    const {user, signin} = useAuth();
    const location = useLocation();
    const navigate = useNavigate();
    const readUser = localStorage.getItem("user");
    const isVerified = localStorage.getItem("isVerified") === "true";
    const isProvider = localStorage.getItem("isProvider") === "true";
    const rememberMe = localStorage.getItem("rememberMe") === "true";
    const correctRoute = getCorrectPrivilegeRoute(location);

    useEffect(() => {
        if (readUser && readUser !== "")
            signin(JSON.parse(readUser), rememberMe, () => navigate(correctRoute));
    }, []);

    if (!user && !readUser) {
        // Redirect them to the /login page, but save the current location they were
        // trying to go to when they were redirected. This allows us to send them
        // along to that page after they login
        return <Navigate to="/login" state={{from: correctRoute}} replace/>;
    }

    if (getCorrectPrivilegeRoute(location) !== location) {
        return <Navigate to={correctRoute}/>;
    }
    return children;
}

export default RequireAuth;
