import React, {createContext, Dispatch, ReactNode, SetStateAction, useState} from "react"
import {internalAuthProvider} from "../scripts/auth"
import {UserModel} from "../types"
import {removeCookie} from "../scripts/cookies"

interface AuthContextType {
    user: UserModel | null
    setUser: Dispatch<SetStateAction<UserModel | null>>
    signin: (user: UserModel, rememberMe: boolean, callback: VoidFunction) => void;
    signout: (callback: VoidFunction) => void
}

export const AuthContext = createContext<AuthContextType>(null!)

export function AuthProvider({children}: { children: ReactNode }) {

    let [user, setUser] = useState<UserModel | null>(null)

    let signin = (newUser: UserModel, rememberMe: boolean, callback: VoidFunction) => {
        return internalAuthProvider.signin(() => {
            setUser(newUser)
            if (!localStorage.getItem("user")) {
                localStorage.setItem("user", JSON.stringify(newUser))
            }
            localStorage.setItem("token", newUser.token!)
            localStorage.setItem("isVerified", newUser.verified ? "true" : "false")
            localStorage.setItem("isProvider", newUser.provider ? "true" : "false")
            localStorage.setItem("rememberMe", rememberMe ? "true" : "false")
            callback()
        })
    }

    let signout = (callback: VoidFunction) => {
        return internalAuthProvider.signout(() => {
            setUser(null)
            localStorage.removeItem("user")
            localStorage.removeItem("token")
            localStorage.removeItem("isVerified")
            localStorage.removeItem("isProvider")
            localStorage.removeItem("rememberMe")
            removeCookie("basic-token")
            callback()
        })
    }

    let value = {user, setUser, signin, signout}

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}
