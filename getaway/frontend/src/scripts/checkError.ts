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
        console.log(`Recibi un 401`)
        localStorage.removeItem('accessToken')
        return handleResponseStatus(response);
    }
    if (response) {
        const accessHeader = response.headers.get('Authorization')
        if (accessHeader) {
            const accessToken = accessHeader.split(' ')[1]
            localStorage.setItem('accessToken', accessToken)
        }

    }
    return handleResponseStatus(response);
}
