import React, {createContext, Dispatch, ReactNode, SetStateAction, useState} from "react"
import {internalAuthProvider} from "../scripts/auth"
import {UserModel} from "../types"

interface AuthContextType {
    user: UserModel | null
    setUser: Dispatch<SetStateAction<UserModel | null>>
    signIn: (callback: VoidFunction) => void
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

    const signIn = (callback: VoidFunction) => {
        internalAuthProvider.signIn(() => {
            const token = localStorage.getItem('accessToken')
            console.log(`Sign in token: ${token}`)
            if(token) {
                const loggedUser = JSON.parse(atob(token.split('.')[1])) as UserModel
                console.log(`Sign in LOGGED USER`)
                console.log(loggedUser)
                setUser({
                    ...user,
                    userId: loggedUser.userId,
                    sub: loggedUser.sub,
                    name: loggedUser.name,
                    surname: loggedUser.surname,
                    isVerified: loggedUser.isVerified,
                    isProvider: loggedUser.isProvider,
                    hasImage: loggedUser.hasImage,
                    profileImageUrl: loggedUser.profileImageUrl,
                })
                console.log(`Use effect AUTH CONTEXT user`)
                console.log(user)
            }
            callback()
        })
        // return user
    }

    const signOut = (callback: VoidFunction) => {
        return internalAuthProvider.signOut(() => {
            console.log(`Hago un sign out`)
            localStorage.removeItem('accessToken')
            setUser(null)
            callback()
        })
    }

    const getUser = () => {
        console.log(`GET USER ${user}`)
        if(user !== null)
            return user
        return null
    }

    const verifyUser = (callback: VoidFunction) => {
        console.log(`verifyUser user`)
        console.log(user)
        if (user !== null) {
            user.isVerified = true
            setUser(user)
        }
        callback()
    }

    const makeProvider = (callback: VoidFunction) => {
        console.log(`makeProvider user`)
        console.log(user)
        if (user !== null) {
            user.isProvider = true
            setUser(user)
        }
        callback()
    }

    const setHasImage = () => {
        console.log(`setHasImage user`)
        console.log(user)
        if (user !== null) {
            user.hasImage = true
            setUser(user)
        }
    }

    const editUserInfo = (name: string, surname: string, callback: VoidFunction) => {
        console.log(`editUserInfo user`)
        console.log(user)
        if (user !== null) {
            user.name = name
            user.surname = surname
            setUser(user)
        }
        callback()
    }

    const isLogged = () => {
        console.log(`isLogged user`)
        console.log(user)
        return !!user;

    }

    const isVerified = () => {
        console.log(`isVerified user`)
        console.log(user)
        if(user !== null)
            return user.isVerified
        return false
    }

    const isProvider = () => {
        console.log(`isProvider user`)
        console.log(user)
        if(user !== null)
            return user.isProvider
        return false
    }

    const value = {user, setUser, signIn, signOut, verifyUser, makeProvider, setHasImage, editUserInfo, isLogged, isVerified, isProvider, getUser}

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}
