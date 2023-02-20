import {useTranslation} from "react-i18next";
import React from "react";
// @ts-ignore
import VisibilityIcon from "@mui/icons-material/Visibility";
// @ts-ignore
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";
import {userService} from "../services";
import {useAuth} from "../hooks/useAuth";
import {useLocation, useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";


type FormDataEditAccount = {
    name: string;
    surname: string;
    email: string;
    password: string;
    confirmPassword: string;
};

export default function EditAccount() {
    const {t} = useTranslation()
    const navigate = useNavigate()
    const location = useLocation()

    const {user} = useAuth()

    const {register, handleSubmit, formState: {errors},}
        = useForm<FormDataEditAccount>({criteriaMode: "all"})

    const onSubmitEdit = handleSubmit((data: FormDataEditAccount) => {
            userService.updateUserInfoById(user?.id, data.name, data.surname)
                .then(() => {navigate("/user/profile")})
                .catch(() => {})
        }
    );
    return (
        <div className="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">
            <div className="container-lg w-100 p-2 modalContainer">
                <div className="row w-100 m-0 p-4 align-items-center justify-content-center">
                    <div className="col-12">
                        <h1 className="text-center title">
                            {t('Navbar.editAccountPopUp')}
                        </h1>
                    </div>
                    <div className="col-12">
                        <div className="container-lg">
                            <div className="row">
                                <form id="editAccountForm" onSubmit={onSubmitEdit}>
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
                                        <input type="text" id="email" disabled={true}
                                               className="form-control mb-2"
                                               placeholder={t('Navbar.emailPlaceholder')}
                                               aria-describedby="email input"
                                               />
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

                                        <input max="50" type="text" id="name"
                                               className="form-control"
                                               placeholder={t('Navbar.namePlaceholder')}
                                               {...register("name", {
                                                   required: true,
                                                   validate: {
                                                       length: (name) =>
                                                           name.length >= 0 && name.length <= 50,
                                                   },
                                                   pattern: {
                                                       value: /^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°"·#$%&=:¿?!¡/.-]*$/,
                                                       message: t("CreateAccount.error.namepattern"),
                                                   },
                                               })}/>
                                        {errors.name?.type === "required" && (
                                            <p className="form-control is-invalid form-error-label">
                                                {t("CreateAccount.error.name.isRequired")}
                                            </p>
                                        )}
                                        {errors.email?.type === "length" && (
                                            <p className="form-control is-invalid form-error-label">
                                                {t("CreateAccount.error.name.length")}
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
                                        <input max="50" type="text" id="surname"
                                               className="form-control"
                                               placeholder={t('Navbar.surnamePlaceholder')}
                                               {...register("surname", {
                                                   required: true,
                                                   validate: {
                                                       length: (surname) =>
                                                           surname.length >= 0 && surname.length <= 50,
                                                   },
                                                   pattern: {
                                                       value: /^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°"·#$%&=:¿?!¡/.-]*$/,
                                                       message: t("CreateAccount.error.surname.pattern"),
                                                   },
                                               })}
                                        />
                                        {errors.surname?.type === "required" && (
                                            <p className="form-control is-invalid form-error-label">
                                                {t("CreateAccount.error.surname.isRequired")}
                                            </p>
                                        )}
                                        {errors.email?.type === "length" && (
                                            <p className="form-control is-invalid form-error-label">
                                                {t("CreateAccount.error.surname.length")}
                                            </p>
                                        )}
                                    </div>
                                </form>

                                <div className="col-12 px-0 d-flex align-items-center justify-content-around">
                                    <button className="btn btn-cancel-form px-3 py-2" id="cancelFormButton"
                                            onClick={() => navigate(-1)}>
                                        {t('Button.cancel')}
                                    </button>
                                    <button form="editAccountForm" type="submit" id="editAccountFormButton"
                                            className="btn btn-submit-form px-3 py-2">
                                        {t('Button.create')}
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