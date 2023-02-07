import {NavigateFunction} from "react-router-dom";
import {Result} from "../types";

export function serviceHandler<T>(
    promise: Promise<Result<T>>,
    navigate: NavigateFunction,
    setterFunction: (data: T) => void,
    cleanerFunction: () => void
): void {
    promise.then((response: Result<T>) => {
        if (response.hasFailed()) {
            if (response.getError().getStatus() === 204) {
                // @ts-ignore
                setterFunction(undefined);
            } else if (isNaN(response.getError().getStatus())) {
                return;
            } else {
                navigate(`/error?code=${response.getError().getStatus()}&message=${response.getError().getMessage()}`);
            }
        } else {
            setterFunction(response.getData());
        }
    })
        .catch(() => navigate(`/error?code=500&message=Server error`))
        .finally(cleanerFunction);
}
