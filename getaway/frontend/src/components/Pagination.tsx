import {useTranslation} from "react-i18next";
import "../common/i18n/index"
import React from "react";
import PropTypes, {InferType} from "prop-types";
import {Link, useSearchParams} from "react-router-dom";
import styled from "styled-components";
import {getQueryOrDefault, queryHasParam, useQuery} from "../hooks/useQuery";

Pagination.propTypes = {
    currentPage: PropTypes.number.isRequired,
    maxPage: PropTypes.number.isRequired,
};

export default function Pagination({currentPage, maxPage}:
                                       InferType<typeof Pagination.propTypes>) {

    const {t} = useTranslation()

    const [searchParams, setSearchParams] = useSearchParams()

    const PageArrow = styled.img<{ xRotated?: boolean }>`
          transform: ${(props) => (props.xRotated ? "rotate(180deg);" : "")};
          cursor: pointer;
          margin: 0 8px;
          height: 36px;
    `;

    function changePage(page: string) {
        searchParams.set("page", page)
        setSearchParams(searchParams)
    }

    return (
        <>
            {currentPage > 1 && (
                <div style={{alignItems: "center", display: "flex"}}>
                    <PageArrow xRotated={true}
                               src="./images/page-arrow.png"
                               alt={`${t("Pagination.alt.beforePage")}`}
                        onClick={() => changePage((currentPage-1).toString())}
                    />
                </div>
            )}

            {t("Pagination.message", {
                currentPage: currentPage,
                maxPage: maxPage,
            })}

            {currentPage < maxPage && (
                <div style={{alignItems: "center", display: "flex"}}>
                    <PageArrow src="./images/page-arrow.png"
                               alt={`${t("Pagination.alt.nextPage")}`}
                               onClick={() => changePage((currentPage+1).toString())}
                    />
                </div>
            )}
        </>
    );
}
