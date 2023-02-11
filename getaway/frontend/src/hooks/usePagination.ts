import {getQueryOrDefault, useQuery} from "./useQuery";

export function usePagination() {
    const query = useQuery();
    return [parseInt(getQueryOrDefault(query, "page", "1"))]
}
