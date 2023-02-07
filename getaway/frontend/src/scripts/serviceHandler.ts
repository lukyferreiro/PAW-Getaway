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
            if (response.getError().getCode() === 204) {
                // @ts-ignore
                setterFunction(undefined);
            } else if (isNaN(response.getError().getCode())) {
                return;
            } else {
                navigate(`/error?code=${response.getError().getCode()}`);
            }
        } else {
            setterFunction(response.getData());
        }
    })
        .catch(() => navigate("/error?code=500"))
        .finally(cleanerFunction);
}
