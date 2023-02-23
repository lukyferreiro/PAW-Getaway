import {useTranslation} from "react-i18next";
import React, {useEffect} from "react";
import {userService} from "../services";
import {useAuth} from "../hooks/useAuth";
import {useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";
import {showToast} from "../scripts/toast";


type FormDataEditProfile = {
    name: string;
    surname: string;
    email: string;
};

export default function UserEditProfile() {

    const {t} = useTranslation()

    const {user} = useAuth()
    const isVerified = localStorage.getItem("isVerified") === "true"
    const navigate = useNavigate()

    if (!isVerified) {
        navigate("/user/profile")
        showToast(t('User.toast.editProfile.forbidden'), 'error')
    }

    useEffect(() => {
        setValue('name', user!.name);
        setValue('surname', user!.surname);
        setValue('email', user!.email);
    }, []);

    const {register, handleSubmit, setValue, formState: {errors},}
        = useForm<FormDataEditProfile>({criteriaMode: "all"})

    const onSubmitEdit = handleSubmit((data: FormDataEditProfile) => {
        console.log('entro')
        if(user) {
            userService.updateUserInfoById(user.id, data.name, data.surname)
                .then(() => {
                    navigate("/user/profile")
                    showToast(t('User.toast.editProfile.success'), 'success')
                })
                .catch(() => {
                    showToast(t('User.toast.editProfile.error'), 'error')
                })
        }else{
        //    TODO:ERROR
        }
    });
    return (
        <div className="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">
            <div className="container-lg w-100 p-2 modalContainer">
                <div className="row w-100 m-0 p-4 align-items-center justify-content-center">
                    <div className="col-12">
                        <h1 className="text-center title">
                            {t('Navbar.editProfilePopUp')}
                        </h1>
                    </div>
                    <div className="col-12">
                        <div className="container-lg">
                            <div className="row">
                                <form id="editProfileForm" onSubmit={onSubmitEdit} method="post">
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
                                        <input type="text"  disabled={true} style={{color: "grey"}}
                                               className="form-control mb-2"
                                               placeholder={t('Navbar.emailPlaceholder')}
                                               aria-describedby="email input"
                                            {...register("email", {
                                                required: true,
                                                validate: {
                                                    length: (email) =>
                                                        email.length >= 0 && email.length <= 255,
                                                },
                                                pattern: {
                                                    value: /^[A-Za-z0-9àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð ()<>_,'°"·#$%&=:¿?!¡/.-]*$/,
                                                    message: t("CreateAccount.error.email.pattern"),
                                                },
                                            })}
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
                                                       message: t("CreateAccount.error.name.pattern"),
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

                                    <div className="col-12 px-0 d-flex align-items-center justify-content-around">
                                        <button className="btn btn-cancel-form px-3 py-2" id="cancelFormButton"
                                                onClick={() => navigate(-1)}>
                                            {t('Button.cancel')}
                                        </button>
                                        <button form="editProfileForm" type="submit" id="editProfileFormButton"
                                                className="btn btn-submit-form px-3 py-2">
                                            {t('Button.create')}
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}