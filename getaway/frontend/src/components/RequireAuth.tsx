import React, {useEffect} from "react";
import {useAuth} from "../hooks/useAuth";

export default function RequireAuth({children}: { children: JSX.Element }) {
    const {signIn, signOut, getUser} = useAuth()
    const readUser = getUser()

    useEffect(() => {
        if (readUser) {
            signIn( () => {})
        } else {
            signOut(() => {})
        }

    }, [])

    return children;
}