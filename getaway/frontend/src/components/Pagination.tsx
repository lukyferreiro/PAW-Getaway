import {useTranslation} from "react-i18next";
import "../common/i18n/index"
import React from "react";
import PropTypes, {InferType} from "prop-types";
import {Link} from "react-router-dom";
import styled from "styled-components";

Pagination.propTypes = {
    currentPage: PropTypes.number.isRequired,
    maxPage: PropTypes.number.isRequired,
    baseURL: PropTypes.string.isRequired,
};

export default function Pagination({currentPage, maxPage, baseURL,}:
                                       InferType<typeof Pagination.propTypes>) {

    const {t} = useTranslation()

    const PageArrow = styled.img<{ xRotated?: boolean }>`
          transform: ${(props) => (props.xRotated ? "rotate(180deg);" : "")};
          cursor: pointer;
          margin: 0 8px;
          height: 36px;
    `;

    return (
        <>
            {currentPage > 1 && (
                <Link to={`${baseURL}?page=${currentPage - 1}`}
                      style={{alignItems: "center", display: "flex"}}>
                    <PageArrow xRotated={true}
                               src="./images/page-arrow.png"
                               alt={`${t("Pagination.alt.beforePage")}`}/>
                </Link>
            )}

            {t("Pagination.message", {
                currentPage: currentPage,
                maxPage: maxPage,
            })}

            {currentPage < maxPage && (
                <Link to={`${baseURL}?page=${currentPage + 1}`}
                      style={{alignItems: "center", display: "flex"}}>
                    <PageArrow src="./images/page-arrow.png"
                               alt={`${t("Pagination.alt.nextPage")}`}
                    />
                </Link>
            )}
        </>
    );
}
