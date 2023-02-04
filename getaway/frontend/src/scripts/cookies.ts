export function setCookie(name: string, value: string, expirationDays: number) {
    let date = new Date()
    date.setTime(date.getTime() + expirationDays * 24 * 60 * 60 * 1000)
    document.cookie = `${name}=${value};Expires=${date.toUTCString()}`
}

export function getCookie(name: string): string | undefined {
    const value = `; ${document.cookie}`
    const parts = value.split(`; ${name}=`)
    if (parts.length === 2){
        return parts?.pop()?.split(";").shift()
    }
}

export function removeCookie(name: string) {
    document.cookie = name + "=; Expires=Thu, 01 Jan 1970 00:00:01 GMT;"
}
