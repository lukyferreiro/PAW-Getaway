import React from 'react'
import Article from "../../components/Article";
import * as testingLibrary from "../test_utils/contextRender";

const customPropsMap = (options = {}) => {
    const map = {
       isOpen: true,
       experienceId: 1,
       userId: 1,
    }

    return { ...map, ...options };
};