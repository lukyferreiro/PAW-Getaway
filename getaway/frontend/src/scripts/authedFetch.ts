function isTokenExpired (token: string): boolean  {
    const payload = JSON.parse(atob(token))
    return payload.exp * 1000 >= Date.now()
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