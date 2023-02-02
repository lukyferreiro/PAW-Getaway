import {ErrorResponse, PostResponse, PutResponse, Result} from "../types";
import {authedFetch} from "./authedFetch";
import {checkError} from "./checkError";

export async function resultFetch<RetType>(
    url: string,
    options: any
): Promise<Result<RetType>> {
    try {
        let parsedResponse;

        const response = await authedFetch(url, options);

        if (options.method === "POST") {
            parsedResponse = postCheckError(response);
        } else if (options.method === "PUT") {
            parsedResponse = putCheckError(response);
        } else {
            parsedResponse = await checkError<RetType>(response);
        }
        return Result.ok(parsedResponse as RetType);
    } catch (err: any) {
        return Result.failed(new ErrorResponse(parseInt(err.message), err.message));
    }
}

function postCheckError(response: Response): PostResponse {
    if (
        response.status >= 200 &&
        response.status <= 299 &&
        response.status !== 204
    ) {
        return {url: response.headers.get("Location")} as PostResponse;
    } else {
        throw Error(response.status.toString());
    }
}

function putCheckError(response: Response): PutResponse {
    if (
        response.status >= 200 &&
        response.status <= 299 &&
        response.status !== 204
    ) {
        return {statusCode: response.status} as PutResponse;
    } else {
        throw Error(response.status.toString());
    }
}
