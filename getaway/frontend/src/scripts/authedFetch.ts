function updateOptions(options: any) {
    let newOptions = {...options};
    newOptions.headers = {...options.headers};
    const getawayToken = localStorage.getItem("getawayAccessToken");
    if (getawayToken) {
        newOptions.headers["Authorization"] = `Bearer ${getawayToken}`
    }
    return newOptions;
}

export function authedFetch(url: string, options: any) {
    return fetch(url, updateOptions(options));
}