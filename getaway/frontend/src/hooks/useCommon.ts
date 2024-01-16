import {useContext} from "react";
import {CommonContext} from "../context/CommonContext"

export function useCommon() {
    return useContext(CommonContext)
}