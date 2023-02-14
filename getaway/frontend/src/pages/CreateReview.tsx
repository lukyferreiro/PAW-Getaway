import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {Location, Navigate, To, useLocation, useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {serviceHandler} from "../scripts/serviceHandler";
import {experienceService} from "../services";
import {useForm} from "react-hook-form";
import {paths} from "../common";
import {useAuth} from "../hooks/useAuth";
import {ExperienceNameModel} from "../types";
import "../styles/star_rating.css";


type FormDataReview = {
    title: string;
    description: string;
    score: string;
};

function getCorrectPrivilegeRoute(location: Location): To {
    const startsWithUserOrError = location.pathname.startsWith("/experiences") || location.pathname.startsWith("/error");
    if (startsWithUserOrError) {
        return location;
    }
    return "/";
}

export default function CreateReview() {

    const {t} = useTranslation();
    const navigate = useNavigate()
    const [isLoading, setIsLoading] = useState(false)
    const [rating, setRating] = useState(1);
    const [hover, setHover] = useState(0);

    const {user, signIn} = useAuth();
    const location = useLocation();
    const readUser = localStorage.getItem("user");
    const isVerified = localStorage.getItem("isVerified") === "true";
    const isProvider = localStorage.getItem("isProvider") === "true";
    const rememberMe = localStorage.getItem("rememberMe") === "true";
    const correctRoute = getCorrectPrivilegeRoute(location);

    useEffect(() => {
        if (readUser && readUser !== "")
            signIn(JSON.parse(readUser), rememberMe, () => navigate(correctRoute));
    }, []);

    const [experience, setExperience] = useState<ExperienceNameModel | undefined>(undefined);
    const {experienceId} = useParams();


    useEffect(() => {
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
        if (readUser && readUser !== "") {
            signIn(JSON.parse(readUser), rememberMe, () => navigate(correctRoute));
        }
    }, []);

    const {register, handleSubmit, formState: {errors},}
        = useForm<FormDataReview>({criteriaMode: "all"});

    const onSubmit = handleSubmit((data: FormDataReview) => {
            data.score = String(-rating);
            experienceService.postNewReview(parseInt(experienceId ? experienceId : "-1"), data.title, data.description, data.score)
                .then((result) => {
                        if (!result.hasFailed()) {
                            navigate(paths.EXPERIENCES + "/" + experienceId, {replace: true})
                        }
                    }
                )
                .catch(() => {
                });
        }
    );

    if (!user && !readUser) {
        return <Navigate to="/login" state={{from: "/experiences/" + experienceId + "/createReview"}} replace/>;
    }
    if (!isVerified) {
        return <Navigate to="/user/profile" replace/>;
    }

    return (
        <div className="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">
            <div className="container-lg w-100 modalContainer d-flex flex-column justify-content-center align-items-center">
                <form id="createReviewForm" acceptCharset="utf-8" onSubmit={onSubmit} method="post" style={{width: "100%"}}>
                    <div className="p-4 mx-4 mt-4 m-1">
                        <div className="col m-2">
                            <h3 className="text-center" style={{wordBreak: "break-all"}}>
                                {t('CreateReview.title', {experienceName: experience?.name})}
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
                                       max: 50,
                                       min: 3,
                                       pattern: {
                                           value: /^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°"·#$%&=:¿?!¡/.-]*$/,
                                           message: t("CreateReview.error.title.pattern"),
                                       },
                                   })}
                            />
                            {errors.title?.type === "required" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("CreateReview.error.title.isRequired")}
                                </p>
                            )}
                            {errors.title?.type === "max" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("CreateReview.error.title.max")}
                                </p>
                            )}
                            {errors.title?.type === "min" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("CreateReview.error.title.min")}
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
                                          maxLength: 255,
                                          minLength: 3,
                                          pattern: {
                                              value: /^([A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°";$%#&=:¿?!¡\n\s\t/.-])*$/,
                                              message: t("CreateReview.error.description.pattern"),
                                          },
                                      })}
                            />

                            {errors.description?.type === "required" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("CreateReview.error.description.required")}
                                </p>
                            )}
                            {errors.description?.type === "maxLength" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("CreateReview.error.description.max")}
                                </p>
                            )}
                            {errors.description?.type === "minLength" && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("CreateReview.error.description.min")}
                                </p>
                            )}
                        </div>
                        <div className="col m-2">
                            <label htmlFor="score" className="form-label">
                                {t('Review.score')}
                                <span className="required-field">*</span>
                            </label>


                            {/*TODO hay que hacer el register de score*/}
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
                            {/*<form:errors path="score" element="p" cssClass="form-error-label mt-2"/>*/}
                            {/*<input type="text" className="form-control" id="scoreInput"*/}
                            {/*       {...register("score", {*/}
                            {/*           required: true,*/}
                            {/*           pattern: {*/}
                            {/*               value: /^([1-5])$/,*/}
                            {/*               message: t("CreateReview.error.score.pattern"),*/}
                            {/*           },*/}
                            {/*       })}*/}
                            {/*/>*/}

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