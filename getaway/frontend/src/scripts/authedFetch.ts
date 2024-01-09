// import * as jwt from 'jsonwebtoken';

function isTokenExpired(token: string): boolean {
    // const payload: any = jwt.verify(token.split(".")[1], 'paw2022b-1-super-secret-key');
    const payload: any = JSON.parse(atob(token.split(".")[1]))
    return payload.exp * 1000 < new Date().getTime()
}

function updateOptions(options: any) {
    let newOptions = {...options};
    newOptions.headers = {...options.headers};
    const getawayAccessToken = localStorage.getItem("getawayAccessToken");
    const getawayRefreshToken = localStorage.getItem("getawayRefreshToken");
    if (getawayAccessToken && !isTokenExpired(getawayAccessToken)) {
        newOptions.headers["Authorization"] = `Bearer ${getawayAccessToken}`
    } else if (getawayRefreshToken) {
        newOptions.headers["Authorization"] = `Bearer ${getawayRefreshToken}`
    }
    return newOptions;
}

export function authedFetch(url: string, options: any) {
    return fetch(url, updateOptions(options));
}