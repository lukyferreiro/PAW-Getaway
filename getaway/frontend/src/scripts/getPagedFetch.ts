import {ErrorResponse, PagedContent, Result} from "../types";
import {authedFetch} from "./authedFetch";
import {checkError} from "./checkError";

export async function getPagedFetch<RetType>(
    url: string,
    page?: number,
): Promise<Result<PagedContent<RetType>>> {
    let urlPaged = pageUrlMaker(url, page);
    try {
        const response = await authedFetch(urlPaged.toString(), {
            method: "GET",
        });

        const parsedResponse = await checkError<RetType>(response);
        const maxPage = response.headers.get("x-total-pages");
        return Result.ok(
            new PagedContent(parsedResponse, maxPage ? parseInt(maxPage) : 1)
        );
    } catch (err: any) {
        return Result.failed(new ErrorResponse(parseInt(err.message), err.message));
    }
}

function pageUrlMaker(
    path: string,
    page?: number,
): URL {
    let url = new URL(path);
    if (page) {
        url.searchParams.append("page", page.toString());
    }
    return url;
}
