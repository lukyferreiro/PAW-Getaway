function updateOptions(options: any) {
    let newOptions = {...options};
    newOptions.headers = {...options.headers};
    const accessToken = localStorage.getItem("accessToken");
    console.log(`AUTH FETCH ${accessToken}`)
    if (accessToken) {
        newOptions.headers["Authorization"] = `Bearer ${accessToken}`
    }
    return newOptions;
}

export function authedFetch(url: string, options: any) {
    return fetch(url, updateOptions(options));
}