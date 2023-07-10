import {getCookie} from "./cookies";
import {authedFetch} from "./authedFetch";

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

// Si obtenemos un 401 quiere decir que vencio el token, entonces nos volvemos a autenticar
// con Basic (el cual lo teniamos encodeado en una cookie) y obtenemos el nuevo token
// que volvemos a guardar en local storage
export function checkValidJWT<RetType>(response: Response): Promise<RetType> {
    if (response.status === 401) {
        localStorage.removeItem("token");
        const basic = getCookie("basic-token");
        if (basic) {
            authedFetch(response.url, {headers: {Authorization: `Basic ${basic}`},})
                .then((newResponse) => {
                    response = newResponse;
                    const token = newResponse.headers.get("Authorization")?.toString().split(" ")[1];
                    if (token) {
                        localStorage.setItem("token", token);
                    }
                    return handleResponseStatus(response);
                });
        }
    }
    return handleResponseStatus(response);
}