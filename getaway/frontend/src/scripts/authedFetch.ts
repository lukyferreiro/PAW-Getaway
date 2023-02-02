function updateOptions(options: any) {
    let newOptions = {...options};
    newOptions.headers = {...options.headers};
    const token = localStorage.getItem("token");
    if (token) {
        newOptions.headers["Authorization"] = `Bearer ${token}`;
    }
    return newOptions;
}

export function authedFetch(url: string, options: any) {
    return fetch(url, updateOptions(options));
}