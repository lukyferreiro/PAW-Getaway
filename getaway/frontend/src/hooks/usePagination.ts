import {getQueryOrDefault, useQuery} from "./useQuery";

export function usePagination(defaultSize: number) {
    const query = useQuery();
    return parseInt(getQueryOrDefault(query, "page", "1"))
}
