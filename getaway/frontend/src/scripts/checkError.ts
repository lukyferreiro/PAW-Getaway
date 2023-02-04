import {getCookie} from "./cookies";
import {authedFetch} from "./authedFetch";


export function checkError<RetType>(response: Response): Promise<RetType> {
    if (
        response.status === 401 &&
        localStorage.getItem("rememberMe") === "true"
    ) {
        localStorage.removeItem("token");
        const basic = getCookie("basic-token");
        if (basic) {
            authedFetch(response.url, {headers: {Authorization: `Basic ${basic}`},})
                .then((newResponse) => {
                    response = newResponse;
                    const token = newResponse.headers
                        .get("Authorization")?.toString().split(" ")[1];
                    if (token){
                        localStorage.setItem("token", token);
                    }
                    return handleResponseStatus(response);
            });
        }
    }
    return handleResponseStatus(response);
}

function handleResponseStatus<RetType>(response: Response): Promise<RetType> {
    if (
        response.status >= 200 &&
        response.status <= 299 &&
        response.status !== 204
    ) {
        return response.json() as Promise<RetType>;
    } else {
        throw Error(response.status.toString());
    }
}

export function checkImageError(response: Response): Promise<Blob> {
    if (response.status >= 200 && response.status <= 299) {
        return response.blob() as Promise<Blob>;
    } else {
        throw Error(response.status.toString());
    }
}
