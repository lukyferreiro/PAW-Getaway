import React from "react";
import Spinner from "./Spinner";

interface LoadableDataProps {
    spinnerMultiplier?: number;
    isLoading: boolean;
    children: React.ReactNode;
}

export default function LoadableData({spinnerMultiplier = 1, isLoading, children,}: LoadableDataProps) {
    return (
        <>
            {isLoading && <Spinner multiplier={spinnerMultiplier}/>}
            {!isLoading && <>{children}</>}
        </>
    );
}