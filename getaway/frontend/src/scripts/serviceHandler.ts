import {NavigateFunction} from "react-router-dom";
import {Result} from "../types";

export function serviceHandler<T>(
    promise: Promise<Result<T>>,
    navigate: NavigateFunction,
    setterFunction: (data: T) => void,
    cleanerFunction: () => void,
    noContentFunction: () => void
): void {
    promise.then((response: Result<T>) => {
        if (response.hasFailed()) {
            if (isNaN(response.getError().getStatus())) {
                return;
            } else {
                navigate('/error', {
                    state: {
                        code: response.getError().getStatus(),
                        message: response.getError().getMessage(),
                    },
                    replace: true,
                })
            }
        } else {
            if (response.getStatusCode() === 204) {
                noContentFunction();
            } else {
                setterFunction(response.getData());
            }
        }
    })
        .catch(() => {
            navigate('/error', {
                state: {
                    code: 500,
                    message: 'Server error',
                },
                replace: true,
            })
        })
        .finally(cleanerFunction);
}
