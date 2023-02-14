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

type FormDataCreate = {
    name: string;
    surname: string;
    email: string;
    password: string;
    confirmPassword: string;
};

export default function CreateAccount() {
    const {t} = useTranslation()
    let auth = useAuth()
    let navigate = useNavigate()
    let location = useLocation()

    // @ts-ignore
    let from = location.state?.from?.pathname || "/"

    const [seePassword, setSeePassword] = useState(false)
    const [seeRepeatPassword, setSeeRepeatPassword] = useState(false)
    const [invalidCredentials, setInvalidCredendtials] = useState(false)

    function showPassword() {
        setSeePassword(!seePassword)
    }

    function showRepeatPassword() {
        setSeeRepeatPassword(!seeRepeatPassword)
    }

    const {register, watch, handleSubmit, formState: {errors},}
        = useForm<FormDataCreate>({criteriaMode: "all"})

    const onSubmitCreate = handleSubmit((data: FormDataCreate) => {
            setInvalidCredendtials(false);
            userService.createUser(data.name, data.surname, data.email, data.password, data.confirmPassword)
                .then((user) =>
                    user.hasFailed() ? setInvalidCredendtials(true) :
                        loginService.login(data.email, data.password)
                            .then((user) =>
                                user.hasFailed() ? setInvalidCredendtials(true) :
                                    auth.signIn(user.getData(), false, () => {
                                        navigate(from, {replace: true});
                                    })
                            )
                            .catch(() => navigate("/error?code=500&message=Server error"))
                )
                .catch(() => navigate("/error?code=500&message=Server error"));
        }
    );
    return (
        <div className="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">
            <div className="container-lg w-100 p-2 modalContainer">
                <div className="row w-100 m-0 p-4 align-items-center justify-content-center">
                    <div className="col-12">
                        <h1 className="text-center title">
                            {t('Navbar.createAccountPopUp')}
                        </h1>
                        <p className="subtitle text-center">
                            {t('Navbar.createAccountDescription')}
                        </p>
                    </div>
                    <div className="col-12">
                        <div className="container-lg">
                            <div className="row">
                                <form id="createAccountForm" onSubmit={onSubmitCreate}>
                                    <div className="form-group">
                                        <label className="form-label d-flex justify-content-between"
                                               htmlFor="email">
                                            <div>
                                                {t('Navbar.email')}
                                                <span className="required-field">*</span>
                                            </div>
                                            <div className="align-self-center">
                                                <h6 className="max-input-text">
                                                    {t('Navbar.max', {num: 255})}
                                                </h6>
                                            </div>
                                        </label>
                                        <input type="text" id="email"
                                               className="form-control mb-2"
                                               placeholder={t('Navbar.emailPlaceholder')}
                                               aria-describedby="email input"
                                               maxLength={255}
                                               {...register("email", {
                                                   required: true,
                                                   max: 255,
                                                   pattern: {
                                                       value: /^([a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+)*$/,
                                                       message: t("CreateAccount.error.pattern"),
                                                   },
                                               })}/>
                                        {errors.email?.type === "required" && (
                                            <p className="form-control is-invalid form-error-label">
                                                {t("CreateAccount.error.isRequired")}
                                            </p>
                                        )}
                                    </div>

                                    <div className="form-group">
                                        <label htmlFor="name"
                                               className="form-label d-flex justify-content-between">
                                            <div>
                                                {t('Navbar.name')}
                                                <span className="required-field">*</span>
                                            </div>
                                            <div className="align-self-center">
                                                <h6 className="max-input-text">
                                                    {t('Navbar.max', {num: 50})}
                                                </h6>
                                            </div>
                                        </label>

                                        <input maxLength={50} type="text" id="name"
                                               className="form-control"
                                               placeholder={t('Navbar.namePlaceholder')}
                                               {...register("name", {
                                                   required: true,
                                                   max: 50,
                                                   pattern: {
                                                       value: /^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°"·#$%&=:¿?!¡/.-]*$/,
                                                       message: t("CreateAccount.error.pattern"),
                                                   },
                                               })}/>
                                        {errors.name?.type === "required" && (
                                            <p className="form-control is-invalid form-error-label">
                                                {t("CreateAccount.error.isRequired")}
                                            </p>
                                        )}
                                    </div>

                                    <div className="form-group">
                                        <label htmlFor="surname"
                                               className="form-label d-flex justify-content-between">
                                            <div>
                                                {t('Navbar.surname')}
                                                <span className="required-field">*</span>
                                            </div>
                                            <div className="align-self-center">
                                                <h6 className="max-input-text">
                                                    {t('Navbar.max', {num: 50})}
                                                </h6>
                                            </div>
                                        </label>
                                        <input maxLength={50} type="text" id="surname"
                                               className="form-control"
                                               placeholder={t('Navbar.surnamePlaceholder')}
                                               {...register("surname", {
                                                   required: true,
                                                   max: 50,
                                                   pattern: {
                                                       value: /^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°"·#$%&=:¿?!¡/.-]*$/,
                                                       message: t("CreateAccount.error.pattern"),
                                                   },
                                               })}
                                        />
                                        {errors.surname?.type === "required" && (
                                            <p className="form-control is-invalid form-error-label">
                                                {t("CreateAccount.error.isRequired")}
                                            </p>
                                        )}
                                    </div>

                                    <div className="form-group">
                                        <label htmlFor="password"
                                               className="form-label d-flex justify-content-between">
                                            <div>
                                                {t('Navbar.password')}
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
                                                   {...register("password", {
                                                       required: true,
                                                   })}
                                            />
                                            <div className="input-group-append">
                                                <button className="btn btn-eye input-group-text"
                                                        id="passwordEye" type="button" tabIndex={-1} onClick={() => showPassword()}>
                                                    <IconButton aria-label="eye">
                                                        {seePassword ? <VisibilityIcon/> : <VisibilityOffIcon/>}
                                                    </IconButton>
                                                </button>
                                            </div>
                                        </div>
                                        {errors.password?.type === "required" && (
                                            <p className="form-control is-invalid form-error-label">
                                                {t("CreateAccount.error.isRequired")}
                                            </p>
                                        )}
                                    </div>

                                    <div className="form-group">
                                        <label htmlFor="confirmPassword" className="form-label">
                                            {t('Navbar.confirmPassword')}
                                            <span className="required-field">*</span>
                                        </label>
                                        <div className="input-group d-flex justify-content-start align-items-center">
                                            <input type={seeRepeatPassword ? "text" : "password"}
                                                   className="form-control"
                                                   id="confirmPassword"
                                                   aria-describedby="password input"
                                                   {...register("confirmPassword", {
                                                       required: true
                                                   })}/>
                                            <div className="input-group-append">
                                                <button className="btn btn-eye input-group-text"
                                                        id="passwordEye2" type="button" tabIndex={-1} onClick={() => showRepeatPassword()}>
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
                                                {t("CreateAccount.error.password")}
                                            </p>
                                        )}
                                    </div>
                                </form>

                                <div className="col-12 px-0 d-flex align-items-center justify-content-center">
                                    <button form="createAccountForm" type="submit" id="registerFormButton"
                                            className="w-100 btn-create-account my-2 ">
                                        {t('Navbar.createAccount')}
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