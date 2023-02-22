import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {serviceHandler} from "../scripts/serviceHandler";
import {experienceService, reviewService} from "../services";
import {useForm} from "react-hook-form";
import {useAuth} from "../hooks/useAuth";
import {ExperienceNameModel, ReviewModel} from "../types";
import {getQueryOrDefault, useQuery} from "../hooks/useQuery";
import {showToast} from "../scripts/toast";


type FormDataReview = {
    title: string;
    description: string;
    score: string;
};

export default function ReviewForm() {
    const {experienceId} = useParams()
    const {user} = useAuth()
    const readUser = localStorage.getItem("user")
    const isVerified = localStorage.getItem("isVerified") === "true"
    const navigate = useNavigate()

    const {t} = useTranslation()

    const [experience, setExperience] = useState<ExperienceNameModel | undefined>(undefined)
    const [review, setReview] = useState<ReviewModel | undefined>(undefined)

    const [rating, setRating] = useState(1)
    const [hover, setHover] = useState(0)

    const query = useQuery()
    const currentId = getQueryOrDefault(query, "id", "-1")

    useEffect(() => {
        if (!user && !readUser) {
            navigate("/login")
            showToast(t('ReviewForm.toast.forbidden.noUser'), 'error')
        } else if (!isVerified) {
            navigate("/user/profile")
            showToast(t('ReviewForm.toast.forbidden.notVerified'), 'error')
        } else if (parseInt(currentId) !== -1) {
            serviceHandler(
                reviewService.getReviewById(parseInt(currentId)),
                navigate, (review) => {
                    if (review.user.id !== user?.id) {
                        navigate("/", {replace: true});
                    }
                    setReview(review)
                    setRating(-review.score)
                    setHover(-review.score)
                    setValue('title', review.title);
                    setValue('description', review.description);
                },
                () => {
                },
                () => {
                    setReview(undefined)
                    setRating(0)
                    setHover(0)
                }
            )
        }
        serviceHandler(
            experienceService.getExperienceNameById(parseInt(experienceId ? experienceId : '-1')),
            navigate, (fetchedExperience) => {
                setExperience(fetchedExperience)
            },
            () => {
            },
            () => {
                setExperience(undefined)
            }
        );
    }, []);

    const {register, handleSubmit, setValue, formState: {errors},}
        = useForm<FormDataReview>({criteriaMode: "all"});

    const onSubmit = handleSubmit((data: FormDataReview) => {
            data.score = String(-rating);
            if (review) {
                reviewService.updateReviewById(parseInt(currentId), data.title, data.description, data.score)
                    .then((result) => {
                        if (!result.hasFailed()) {
                            navigate(`/experiences/${experienceId}`, {replace: true})
                            showToast(t('ReviewForm.toast.updateSuccess', {reviewTitle: data.title}), 'success')
                        }
                    })
                    .catch(() => {
                        showToast(t('ReviewForm.toast.updateError', {reviewTitle: data.title}), 'error')
                    });
            } else {
                experienceService.postNewReview(parseInt(experienceId ? experienceId : "-1"), data.title, data.description, data.score)
                    .then((result) => {
                        if (!result.hasFailed()) {
                            navigate(`/experiences/${experienceId}`, {replace: true})
                            showToast(t('ReviewForm.toast.createSuccess', {reviewTitle: data.title}), 'success')
                        }
                    })
                    .catch(() => {
                        showToast(t('ReviewForm.toast.createError', {reviewTitle: data.title}), 'error')
                    });
            }
        }
    );

    return (
        <div className="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">
            <div className="container-lg w-100 modalContainer d-flex flex-column justify-content-center align-items-center">
                <form id="createReviewForm" acceptCharset="utf-8" onSubmit={onSubmit} method="post" style={{width: "100%"}}>
                    <div className="p-4 mx-4 mt-4 m-1">
                        <div className="col m-2">
                            <h3 className="text-center" style={{wordBreak: "break-all"}}>
                                {!review ?
                                    <div>
                                        {t('ReviewForm.title', {experienceName: experience?.name})}
                                    </div>
                                    :
                                    <div>
                                        {t('ReviewForm.editTitle', {experienceName: experience?.name})}
                                    </div>
                                }
                            </h3>
                        </div>
                        <div className="col m-2">
                            <label htmlFor="title" className="form-label d-flex justify-content-between">
                                <div>
                                    {t('Review.title')}
                                    <span className="required-field">*</span>
                                </div>
                                <div className="align-self-center">
                                    <h6 className="max-input-text">
                                        {t('Input.maxValue', {value: 50})}
                                    </h6>
                                </div>
                            </label>

                            <input min="3" max="50" type="text" className="form-control"
                                   {...register("title", {
                                       required: true,
                                       validate: {
                                           length: (title) =>
                                               title.length >= 3 && title.length <= 50,
                                       },
                                       pattern: {
                                           value: /^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°"·#$%&=:¿?!¡/.-]*$/,
                                           message: t("ReviewForm.error.title.pattern"),
                                       },
                                   })}
                                   defaultValue={review ? review.title : ""}
                            />
                            {errors.title?.type === "required" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ReviewForm.error.title.isRequired")}
                                </p>
                            )}
                            {errors.title?.type === "length" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ReviewForm.error.title.length")}
                                </p>
                            )}
                        </div>

                        <div className="col m-2">
                            <label htmlFor="description" className="form-label d-flex justify-content-between">
                                <div>
                                    {t('Review.description')}
                                    <span className="required-field">*</span>
                                </div>
                                <div className="align-self-center">
                                    <h6 className="max-input-text">
                                        {t('Input.maxValue', {value: 255})}
                                    </h6>
                                </div>
                            </label>
                            <textarea minLength={3} maxLength={255} className="form-control" style={{maxHeight: "200px"}}
                                      {...register("description", {
                                          required: true,
                                          validate: {
                                              length: (description) =>
                                                  description.length >= 3 && description.length <= 255,
                                          },
                                          pattern: {
                                              value: /^([A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°";$%#&=:¿?!¡\n\s\t/.-])*$/,
                                              message: t("ReviewForm.error.description.pattern"),
                                          },
                                      })}
                                      defaultValue={review ? review.description : ""}
                            />

                            {errors.description?.type === "required" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ReviewForm.error.description.isRequired")}
                                </p>
                            )}
                            {errors.description?.type === "length" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("ReviewForm.error.description.length")}
                                </p>
                            )}
                        </div>
                        <div className="col m-2">
                            <label htmlFor="score" className="form-label">
                                {t('Review.score')}
                                <span className="required-field">*</span>
                            </label>

                            <div className="w-100 d-flex justify-content-center">
                                <div className="w-50">
                                    <div className="star-rating">
                                        {[...Array(5)].map((star, index) => {
                                            index -= 5;
                                            return (
                                                <button
                                                    type="button"
                                                    key={index}
                                                    className={index >= ((rating && hover) || hover) ? "on" : "off"}
                                                    onClick={() => setRating(index)}
                                                    onMouseEnter={() => setHover(index)}
                                                    onMouseLeave={() => setHover(rating)}
                                                >
                                                    <span className="star">&#9733;</span>
                                                </button>
                                            );
                                        })}
                                    </div>
                                </div>
                            </div>
                            <input name="score" type="hidden" className="form-control" id="score"/>
                        </div>
                    </div>

                    <div className="p-0 d-flex justify-content-around">
                        <button className="btn btn-cancel-form px-3 py-2" id="cancelFormButton"
                                onClick={() => navigate(-1)}>
                            {t('Button.cancel')}
                        </button>
                        <button className="btn btn-submit-form px-3 py-2" id="createReviewFormButton"
                                form="createReviewForm" type="submit">
                            {t('Button.create')}
                        </button>
                    </div>

                </form>
            </div>
        </div>
    )

}