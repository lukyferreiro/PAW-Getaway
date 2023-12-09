import React, {createContext, Dispatch, ReactNode, SetStateAction, useEffect, useState} from "react"
import {internalAuthProvider} from "../scripts/auth"
import {CurrentUserModel} from "../types"

interface AuthContextType {
    user: CurrentUserModel | null
    setUser: Dispatch<SetStateAction<CurrentUserModel | null>>
    signIn: (callback: VoidFunction) => void
    signOut: (callback: VoidFunction) => void
    verifyUser: (callback: VoidFunction) => void
    makeProvider: (callback: VoidFunction) => void
    setHasImage: () => void
    editUserInfo: (name: string, surname: string, callback: VoidFunction) => void
    isLogged: () => boolean
    isVerified: () => boolean
    isProvider: () => boolean
    getUser: () => CurrentUserModel | null
}

export const AuthContext = createContext<AuthContextType>(null!)

export function AuthProvider({children}: { children: ReactNode }) {

    const [user, setUser] = useState<CurrentUserModel | null>(null)


    useEffect(() => {
        const token = localStorage.getItem("getawayToken");
        if (token) {
            const loggedUser = JSON.parse(atob(token.split(".")[1])) as CurrentUserModel;
            setUser(loggedUser);
            localStorage.setItem('getawayUser', JSON.stringify(loggedUser))
        }
    }, []);

    const signIn = (callback: VoidFunction) => {
        internalAuthProvider.signIn(() => {
            const token = localStorage.getItem('getawayToken')
            console.log(`Sign in token: ${token}`)
            if(token) {
                const loggedUser = JSON.parse(atob(token.split('.')[1])) as CurrentUserModel
                setUser(loggedUser)
                localStorage.setItem('getawayUser', JSON.stringify(loggedUser))
            }
            callback()
        })
        // return user
    }

    const signOut = (callback: VoidFunction) => {
        return internalAuthProvider.signOut(() => {
            console.log(`Hago un sign out`)
            localStorage.removeItem('getawayToken')
            localStorage.removeItem('getawayUser')
            setUser(null)
            callback()
        })
    }

    const getUser = () => {
        console.log('GET USER')
        const userStorage = localStorage.getItem('getawayUser')
        console.log(userStorage)
        if(userStorage !== null)
            return JSON.parse(userStorage)
        return null
    }

    const verifyUser = (callback: VoidFunction) => {
        console.log(`verifyUser user`)
        const userStorage = localStorage.getItem('getawayUser')
        if (userStorage !== null) {
            const parsedUser = JSON.parse(userStorage)
            parsedUser.isVerified = true
            setUser(parsedUser)
            console.log(parsedUser)
        }
        callback()
    }

    const makeProvider = (callback: VoidFunction) => {
        console.log(`makeProvider user`)
        const userStorage = localStorage.getItem('getawayUser')
        if (userStorage !== null) {
            const parsedUser = JSON.parse(userStorage)
            parsedUser.isProvider = true
            setUser(parsedUser)
            console.log(parsedUser)
        }
        callback()
    }

    const setHasImage = () => {
        console.log(`setHasImage user`)
        const userStorage = localStorage.getItem('getawayUser')
        if (userStorage !== null) {
            const parsedUser = JSON.parse(userStorage)
            parsedUser.hasImage = true
            setUser(parsedUser)
            console.log(parsedUser)
        }
    }

    const editUserInfo = (name: string, surname: string, callback: VoidFunction) => {
        console.log(`editUserInfo user`)
        const userStorage = localStorage.getItem('getawayUser')
        if (userStorage !== null) {
            const parsedUser = JSON.parse(userStorage)
            parsedUser.name = name
            parsedUser.surname = surname
            setUser(parsedUser)
            console.log(parsedUser)
        }
        callback()
    }

    const isLogged = () => {
        console.log(`isLogged user`)
        return localStorage.getItem("getawayUser") !== null
    }

    const isVerified = () => {
        console.log(`isVerified user`)
        const userStorage = localStorage.getItem('getawayUser')
        if(userStorage !== null) {
            const parsedUser = JSON.parse(userStorage)
            console.log(parsedUser)
            return parsedUser.isVerified
        }
        return false
    }

    const isProvider = () => {
        console.log(`isProvider user`)
        const userStorage = localStorage.getItem('getawayUser')
        if(userStorage !== null) {
            const parsedUser = JSON.parse(userStorage)
            console.log(parsedUser)
            return parsedUser.isProvider
        }
        return false
    }

    const value = {user, setUser, signIn, signOut, verifyUser, makeProvider, setHasImage, editUserInfo, isLogged, isVerified, isProvider, getUser}

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}
