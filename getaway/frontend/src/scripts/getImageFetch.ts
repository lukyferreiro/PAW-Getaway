import {ErrorResponse, Result} from "../types";
import {authedFetch} from "./authedFetch";

function checkImageError(response: Response): Promise<Blob> {
    if (response.status >= 200 && response.status <= 299) {
        return response.blob() as Promise<Blob>;
    } else {
        throw Error(response.status.toString());
    }
}

export async function getImageFetch(url: string): Promise<Result<Blob>> {
    try {
        const response = await authedFetch(url, {
            method: "GET",
        });
        const parsedResponse = await checkImageError(response);
        return Result.ok(parsedResponse as Blob);
    } catch (err: any) {
        return Result.failed(new ErrorResponse(parseInt(err.message), err.title, err.message));
    }
}
