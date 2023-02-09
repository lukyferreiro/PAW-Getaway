import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {ReviewModel} from "../types";
import {IconButton} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import React from "react";
import DeleteIcon from "@mui/icons-material/Delete";
import {useAuth} from "../hooks/useAuth";

export default function CardReview(props: {reviewModel: ReviewModel; isEditing: boolean;}) {

    const {t} = useTranslation();

    const {reviewModel, isEditing} = props
    const hasImage = false;
    const {user} = useAuth();

    let isLogged = user !== null;


    return (
        <div className="card m-2">
            {isEditing &&
                <div className="card-title m-2 d-flex justify-content-center align-content-center">
                    {/*            <a href="<c:url value="/experiences/${param.experienceCategory}/${param.experienceId}">*/}
                    {/*<c:param name=" view" value=" ${true}"/> </c:url>">*/}
                    <h4 className="text-center"
                        style={{
                            fontWeight: "bold",
                            textDecoration: "underline",
                            wordBreak: "break-all",
                            color: "black"
                        }}>
                        {reviewModel.experience.name}
                    </h4>
                    {/*</a>*/}

                </div>
            }

            <div className="card-title m-2 d-flex justify-content-between">
                <div className="d-flex">
                    {hasImage &&
                        <img className="user-img" src="<c:url value='/user/profileImage/${param.profileImageId}'/>"
                             alt="Imagen"
                             style={{marginRight: "8px"}}/>
                    }
                    {!hasImage &&
                        <img className="user-img"
                             src="./images/user_default.png" alt="Imagen"/>
                    }

                    <div className="d-flex flex-column justify-content-center align-content-center">
                        <h5 className="my-1">
                            {reviewModel.user.name} , {reviewModel.user.surname}
                        </h5>
                        <h6 className="my-1" style={{fontSize: "small"}}>
                            {reviewModel.date}
                        </h6>
                    </div>
                </div>
                <div className="my-2 d-flex">
                    <div className="star-rating">
                        {[...Array(5)].map((star, index) => {
                            index -=5;
                            return (
                                <div
                                    key={index}
                                    className={index >= -reviewModel.score ? "on" : "off"}
                                >
                                    <span className="star">&#9733;</span>
                                </div>
                            );
                        })}
                    </div>
                </div>
            </div>

            <div className="card-body m-2 p-0">
                <div className="card-text">
                    <h2 className="m-0 align-self-center" style={{fontSize: "x-large"}}>
                        {reviewModel.title}
                    </h2>
                    <h6 className="m-0" style={{fontSize: "medium"}} id="reviewDescription">
                        {reviewModel.description}
                    </h6>
                    {/* No me acuerdo si era chquear que la experience o la review sea observable*/}
                    {!reviewModel.experience?.observable && isEditing &&
                        <p className="obs-info">
                            {t('ExperienceDetail.notVisible')}
                        </p>
                    }
                </div>
            </div>

            {isEditing &&
                <div className="btn-group card-body container-fluid p-1 d-flex justify-content-center align-items-end"
                     role="group">
                    {/*<a href="<c:url value="/user/reviews/edit/${param.reviewId}"/>">*/}
                    <IconButton aria-label="edit" component="span" style={{fontSize: "xx-large"}}>
                        <EditIcon/>
                    </IconButton>
                    {/*</a>*/}
                    {/*<a href="<c:url value="/user/reviews/delete/${param.reviewId}"/>">*/}
                    <IconButton aria-label="trash" component="span" style={{fontSize: "xx-large"}}>
                        <DeleteIcon/>
                    </IconButton>
                    {/*</a>*/}
                </div>
            }
        </div>
    );
}