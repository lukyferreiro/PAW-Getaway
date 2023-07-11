import React, {createContext, Dispatch, ReactNode, SetStateAction, useState} from "react"
import {internalAuthProvider} from "../scripts/auth"
import {UserModel} from "../types"
import {removeCookie} from "../scripts/cookies"

interface AuthContextType {
    user: UserModel | null
    setUser: Dispatch<SetStateAction<UserModel | null>>
    signIn: (user: UserModel, callback: VoidFunction) => void
    signOut: (callback: VoidFunction) => void
    verifyUser: (callback: VoidFunction) => void
    makeProvider: (callback: VoidFunction) => void
    setHasImage: () => void
    editUserInfo: (name: string, surname: string, callback: VoidFunction) => void
    isLogged: () => boolean
    isVerified: () => boolean
    isProvider: () => boolean
    getUser: () => UserModel | null
}

export const AuthContext = createContext<AuthContextType>(null!)

export function AuthProvider({children}: { children: ReactNode }) {
    const [user, setUser] = useState<UserModel | null>(null)

    const signIn = (newUser: UserModel, callback: VoidFunction) => {
        return internalAuthProvider.signIn(() => {
            setUser(newUser)
            localStorage.setItem("user", JSON.stringify(newUser))
            localStorage.setItem("token", newUser.token!)
            localStorage.setItem("isVerified", newUser.verified ? "true" : "false")
            localStorage.setItem("isProvider", newUser.provider ? "true" : "false")
            callback()
        })
    }

    const signOut = (callback: VoidFunction) => {
        return internalAuthProvider.signOut(() => {
            setUser(null)
            localStorage.removeItem("user")
            localStorage.removeItem("token")
            localStorage.removeItem("isVerified")
            localStorage.removeItem("isProvider")
            // removeCookie("basic-token")
            callback()
        })
    }

    const verifyUser = (callback: VoidFunction) => {
        if (user !== null) {
            user.verified = true
            setUser(user)
            localStorage.setItem("user", JSON.stringify(user))
        }
        localStorage.setItem("isVerified", "true")
        callback()
    }

    const makeProvider = (callback: VoidFunction) => {
        if (user !== null) {
            user.provider = true
            setUser(user)
            localStorage.setItem("user", JSON.stringify(user))
        }
        localStorage.setItem("isProvider", "true")
        callback()
    }

    const setHasImage = () => {
        if (user !== null) {
            user.hasImage = true
            setUser(user)
            localStorage.setItem("user", JSON.stringify(user))
        }
    }

    const editUserInfo = (name: string, surname: string, callback: VoidFunction) => {
        if (user !== null) {
            user.name = name
            user.surname = surname
            setUser(user)
            localStorage.setItem("user", JSON.stringify(user))
        }
        callback()
    }

    const isLogged = () => {
        return localStorage.getItem("user") !== null
    }

    const isVerified = () => {
        return localStorage.getItem("isVerified") === 'true'
    }

    const isProvider = () => {
        return localStorage.getItem("isProvider") === 'true'
    }

    const getUser = () => {
        const readUser = localStorage.getItem("user")
        if (readUser) {
            return JSON.parse(readUser)
        }
        return null
    }

    const value = {user, setUser, signIn, signOut, verifyUser, makeProvider, setHasImage, editUserInfo, isLogged, isVerified, isProvider, getUser}

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}
