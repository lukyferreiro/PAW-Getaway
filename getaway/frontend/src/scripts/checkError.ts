import {CurrentUserModel} from "../types";

function handleResponseStatus<RetType>(response: Response): Promise<RetType> | null {
    if (response.status === 204) {
        return null;
    }
    if (
        response.status >= 200 &&
        response.status <= 299
    ) {
        return response.json() as Promise<RetType>;
    } else {
        throw Error(response.status.toString());
    }
}

// Si obtenemos un 401 quiere decir que vencio el token, entonces nos volvemos a autenticar
// con Basic (el cual lo teniamos encodeado en una cookie) y obtenemos el nuevo token
// que volvemos a guardar en local storage
export function checkValidJWT<RetType>(response: Response): Promise<RetType> | null {
    if (response.status === 401) {
        localStorage.removeItem('getawayToken')
        localStorage.removeItem('getawayUser')
        return handleResponseStatus(response);
    }
    if (response) {
        const accessHeader = response.headers ? response.headers.get('Authorization') : null
        if (accessHeader) {
            const getawayToken = accessHeader.split(' ')[1]
            const user = JSON.parse(atob(getawayToken.split('.')[1])) as CurrentUserModel
            localStorage.setItem('getawayToken', getawayToken)
            localStorage.setItem('getawayUser', JSON.stringify(user))
        }

    }
    return handleResponseStatus(response);
}
