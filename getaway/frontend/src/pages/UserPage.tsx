import {Location, Navigate, Outlet, To, useLocation} from "react-router-dom";
import {useAuth} from "../hooks/useAuth";

function getCorrectPrivilegeRoute(location: Location): To {
    const startsWithUserOrError = location.pathname.startsWith("/user") || location.pathname.startsWith("/error");
    if (startsWithUserOrError) {
        return location;
    }
    return "/";
}

export default function UserPage() {

    const {user} = useAuth()
    const location = useLocation()
    const readUser = localStorage.getItem("user")
    const correctRoute = getCorrectPrivilegeRoute(location)

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

