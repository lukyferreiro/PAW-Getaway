import {useTranslation} from "react-i18next";
import React, {useState} from "react";
import {IconButton} from "@mui/material";
// @ts-ignore
import VisibilityIcon from "@mui/icons-material/Visibility";
// @ts-ignore
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";
import {loginService, userService} from "../services";
import {useAuth} from "../hooks/useAuth";
import {useLocation, useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";

type FormDataEditPassword = {
    password: string;
    confirmPassword: string;
};

export default function EditPassword() {

    const {t} = useTranslation()
    const navigate = useNavigate()

    const {user} = useAuth()

    const {register, watch, handleSubmit, formState: {errors},}
        = useForm<FormDataEditPassword>({criteriaMode: "all"})

    const [seePassword, setSeePassword] = useState(false)
    const [seeRepeatPassword, setSeeRepeatPassword] = useState(false)

    function showPassword() {
        setSeePassword(!seePassword)
    }

    function showRepeatPassword() {
        setSeeRepeatPassword(!seeRepeatPassword)
    }


    const onSubmitEditPassword = handleSubmit((data: FormDataEditPassword) => {
            userService.updateUserInfoById(user?.id, data.password, data.confirmPassword)
                .then(() => {
                    navigate("/user/profile")
                })
                .catch(() => {
                })
        }
    );
    return (
        <div className="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">
            <div className="container-lg w-100 p-2 modalContainer">
                <div className="row w-100 m-0 p-4 align-items-center justify-content-center">
                    <div className="col-12">
                        <h1 className="text-center title">
                            {t('Navbar.editPasswordPopUp')}
                        </h1>
                    </div>
                    <div className="col-12">
                        <div className="container-lg">
                            <div className="row">
                                <form id="changePasswordForm" onSubmit={onSubmitEditPassword}>
                                    <div className="form-group">
                                        <label htmlFor="password"
                                               className="form-label d-flex justify-content-between">
                                            <div>
                                                {t('Navbar.editPassword')}
                                                <span className="required-field">*</span>
                                            </div>
                                            <div className="align-self-center">
                                                <h6 className="max-input-text">
                                                    {t('Navbar.max', {num: 25})}
                                                </h6>
                                            </div>
                                        </label>
                                        <div className="input-group d-flex justify-content-start align-items-center">
                                            <input type={seePassword ? "text" : "password"}
                                                   className="form-control"
                                                   id="password"
                                                   aria-describedby="password input"
                                                   placeholder={t('Navbar.passwordPlaceholder')}
                                                   min="8" max="25"
                                                   {...register("password", {
                                                       required: true,
                                                       validate: {
                                                           length: (password) =>
                                                               password.length >= 8 && password.length <= 25,
                                                       },
                                                       pattern: {
                                                           value: /^[A-Za-z0-9@$!%*#?&_]*$/,
                                                           message: t("CreateAccount.error.password.pattern"),
                                                       },
                                                   })}
                                            />
                                            <div className="input-group-append">
                                                <button className="btn btn-eye input-group-text"
                                                        id="passwordEye" type="button" tabIndex={-1}
                                                        onClick={() => showPassword()}>
                                                    <IconButton aria-label="eye">
                                                        {seePassword ? <VisibilityIcon/> : <VisibilityOffIcon/>}
                                                    </IconButton>
                                                </button>
                                            </div>
                                        </div>
                                        {errors.password?.type === "required" && (
                                            <p className="form-control is-invalid form-error-label">
                                                {t("CreateAccount.error.password.isRequired")}
                                            </p>
                                        )}
                                        {errors.password?.type === "length" && (
                                            <p className="form-control is-invalid form-error-label">
                                                {t("CreateAccount.error.password.length")}
                                            </p>
                                        )}
                                    </div>

                                    <div className="form-group">
                                        <label htmlFor="confirmPassword" className="form-label">
                                            {t('Navbar.confirmEditPassword')}
                                            <span className="required-field">*</span>
                                        </label>
                                        <div className="input-group d-flex justify-content-start align-items-center">
                                            <input type={seeRepeatPassword ? "text" : "password"}
                                                   className="form-control"
                                                   id="confirmPassword"
                                                   aria-describedby="password input"
                                                   {...register("confirmPassword", {
                                                       required: true,
                                                       validate: {
                                                           length: (confirmPassword) =>
                                                               confirmPassword.length >= 8 && confirmPassword.length <= 25,
                                                       },
                                                       pattern: {
                                                           value: /^[A-Za-z0-9@$!%*#?&_]*$/,
                                                           message: t("CreateAccount.error.password.pattern"),
                                                       },
                                                   })}/>
                                            <div className="input-group-append">
                                                <button className="btn btn-eye input-group-text"
                                                        id="passwordEye2" type="button" tabIndex={-1}
                                                        onClick={() => showRepeatPassword()}>
                                                    <IconButton aria-label="eye2">
                                                        {seeRepeatPassword ? <VisibilityIcon/> : <VisibilityOffIcon/>}
                                                    </IconButton>
                                                </button>
                                            </div>
                                        </div>
                                        {errors.confirmPassword?.type === "required" && (
                                            <p className="form-control is-invalid form-error-label">
                                                {t("CreateAccount.error.isRequired")}
                                            </p>
                                        )}
                                        {watch('password') !== watch('confirmPassword') && (
                                            <p className="form-control is-invalid form-error-label">
                                                {t("CreateAccount.error.passwordsMustMatch")}
                                            </p>
                                        )}
                                    </div>
                                </form>

                                <div className="col-12 px-0 d-flex align-items-center justify-content-center">
                                    <button form="createAccountForm" type="submit" id="registerFormButton"
                                            className="w-100 btn-create-account my-2 ">
                                        {t('Navbar.changePassword')}
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}