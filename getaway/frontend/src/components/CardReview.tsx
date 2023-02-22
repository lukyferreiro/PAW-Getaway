import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {ReviewModel} from "../types";
import {Link, useNavigate} from 'react-router-dom'
import {IconButton} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import React, {useEffect, useState} from "react";
import DeleteIcon from "@mui/icons-material/Delete";
import StarRating from "./StarRating";
import {serviceHandler} from "../scripts/serviceHandler";
import {reviewService, userService} from "../services";
import {showToast} from "../scripts/toast";
import ConfirmDialogModal, {confirmDialogModal} from "./ConfirmDialogModal";

export default function CardReview(props: { reviewModel: ReviewModel; isEditing: boolean; }) {

    const {t} = useTranslation()
    const navigate = useNavigate()
    const {reviewModel, isEditing} = props

    const [userImg, setUserImg] = useState<string | undefined>(undefined)

    useEffect(() => {
        if (reviewModel.user.hasImage) {
            serviceHandler(
                userService.getUserProfileImage(reviewModel.user.id),
                navigate, (userImg) => {
                    setUserImg(userImg.size > 0 ? URL.createObjectURL(userImg) : undefined)
                },
                () => {
                },
                () => {
                },
            )
        }
    }, [])

    function editReview(reviewId: number) {
        navigate({
            pathname: `/experiences/${reviewModel.experience.id}/reviewForm`,
            search: `?id=${reviewId}`
        }, {replace: true});
    }

    function deleteReview(reviewId: number) {
        reviewService.deleteReviewById(reviewId)
            .then(() => {
                showToast(t('Review.toast.deleteSuccess', {reviewTitle: reviewModel.title}), "success")
            })
            .catch(() => {
                showToast(t('Review.toast.deleteError', {reviewTitle: reviewModel.title}), "error")
            });
        // navigate(-1)
    }

    return (
        <div className="card m-2" style={{height: isEditing ? "310px" : ""}}>
            {isEditing &&
                <div className="card-title m-2 d-flex justify-content-center align-content-center">
                    <Link to={"/experiences/" + reviewModel.experience.id}>
                        <h4 className="text-center"
                            style={{
                                fontWeight: "bold",
                                textDecoration: "underline",
                                wordBreak: "break-all",
                                color: "black"
                            }}>
                            {reviewModel.experience.name}
                        </h4>
                    </Link>
                </div>
            }

            <div className="card-title m-2 d-flex justify-content-between">
                <div className="d-flex">
                    <img className="user-img" src={userImg ? userImg : './images/user_default.png'}
                         alt="Imagen"
                         style={{marginRight: userImg ? "8px" : ""}}/>

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
                    <StarRating score={reviewModel.score}/>
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
                </div>
            </div>

            {isEditing &&
                <div className="btn-group card-body container-fluid p-1 d-flex justify-content-center align-items-end"
                     role="group">
                    <IconButton onClick={() => editReview(reviewModel.id)}
                                aria-label="edit" component="span" style={{fontSize: "xx-large"}}>
                        <EditIcon/>
                    </IconButton>
                    <IconButton
                        onClick={() => {
                            confirmDialogModal(
                                t('Review.deleteModal.title'), t('Review.deleteModal.confirmDelete',
                                    {reviewTitle: reviewModel.title}),
                                () => deleteReview(reviewModel.id)
                            )
                        }}
                        aria-label="trash" component="span" style={{fontSize: "xx-large"}}>
                        <DeleteIcon/>
                    </IconButton>
                </div>
            }
            <ConfirmDialogModal/>
        </div>
    );
}