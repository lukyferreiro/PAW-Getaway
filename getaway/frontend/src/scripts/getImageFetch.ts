import {ErrorResponse, Result} from "../types";
import {authedFetch} from "./authedFetch";
import {checkImageError} from "./checkError";

export async function getImageFetch(url: string): Promise<Result<Blob>> {
    try {
        const response = await authedFetch(url, {
            method: "GET",
        });
        const parsedResponse = await checkImageError(response);

        return Result.ok(parsedResponse as Blob);
    } catch (err: any) {
        return Result.failed(new ErrorResponse(parseInt(err.message), err.message));
    }
}
