import {Location, Navigate, Outlet, To, useLocation, useNavigate} from "react-router-dom";
import {useAuth} from "../hooks/useAuth";
import {useEffect} from "react";

function getCorrectPrivilegeRoute(location: Location): To {
    const startsWithUserOrError = location.pathname.startsWith("/user") || location.pathname.startsWith("/error");
    if (startsWithUserOrError) {
        return location;
    }
    return "/";
}

export default function UserPage() {

    const {user, signIn} = useAuth()
    const location = useLocation()
    const navigate = useNavigate()
    const readUser = localStorage.getItem("user")
    const rememberMe = localStorage.getItem("rememberMe") === "true"
    const correctRoute = getCorrectPrivilegeRoute(location)

    useEffect(() => {
        if (readUser && readUser !== "")
            signIn(JSON.parse(readUser), rememberMe, () => navigate(correctRoute));
    }, [])

    if (!user && !readUser) {
        // Redirect them to the /login page, but save the current location they were
        // trying to go to when they were redirected. This allows us to send them
        // along to that page after they login
        return <Navigate to="/login" state={{from: correctRoute}} replace/>
    }

    if (getCorrectPrivilegeRoute(location) !== location) {
        return <Navigate to={correctRoute}/>
    }

    return (
        <Outlet/>
    );

}

