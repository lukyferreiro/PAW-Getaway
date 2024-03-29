import {useTranslation} from "react-i18next"
import React, {useEffect} from "react"
import {userService} from "../services"
import {useAuth} from "../hooks/useAuth"
import {useNavigate} from "react-router-dom"
import {useForm} from "react-hook-form"
import {showToast} from "../scripts/toast"

type FormDataEditProfile = {
    name: string
    surname: string
}

export default function UserEditProfile() {

    const {t} = useTranslation()
    const {user, isVerified, editUserInfo} = useAuth()
    const isVerifiedValue = isVerified()
    const navigate = useNavigate()

    useEffect(() => {
        if (!isVerifiedValue) {
            navigate("/user/profile",{replace: true})
            showToast(t('User.toast.editProfile.forbidden'), 'error')
        } else {
            setValue('name', user!.name);
            setValue('surname', user!.surname)
            document.title = `${t('PageName')} - ${t('PageTitles.userEditProfile')}`
        }
    }, [])

    const {register, handleSubmit, setValue, formState: {errors},}
        = useForm<FormDataEditProfile>({criteriaMode: "all"})

    const onSubmitEdit = handleSubmit((data: FormDataEditProfile) => {
        userService.updateUserInfoById(user?.userId, data.name, data.surname)
            .then((result) => {
                if(!result.hasFailed()) {
                    editUserInfo(data.name, data.surname, () => navigate("/user/profile",{replace: true}))
                    showToast(t('User.toast.editProfile.success'), 'success')
                }
            })
            .catch(() => {
                showToast(t('User.toast.editProfile.error'), 'error')
            })

    })

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
                                        <input type="text" disabled={true} style={{color: "grey"}}
                                               className="form-control mb-2"
                                               placeholder={t('Navbar.emailPlaceholder')}
                                               aria-describedby="email input"
                                               defaultValue={user?.sub}
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
                                                       value: /^[A-Za-zàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð' ]*$/,
                                                       message: t("CreateAccount.error.name.pattern"),
                                                   },
                                               })}/>
                                        {errors.name?.type === "required" && (
                                            <p className="form-control is-invalid form-error-label">
                                                {t("CreateAccount.error.name.isRequired")}
                                            </p>
                                        )}
                                        {errors.name?.type === "length" && (
                                            <p className="form-control is-invalid form-error-label">
                                                {t("CreateAccount.error.name.length")}
                                            </p>
                                        )}
                                        {errors.name?.type === "pattern" && (
                                            <p className="form-control is-invalid form-error-label">
                                                {t("CreateAccount.error.name.pattern")}
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
                                                       value: /^[A-Za-zàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆŠŽ∂ð' ]*$/,
                                                       message: t("CreateAccount.error.surname.pattern"),
                                                   },
                                               })}
                                        />
                                        {errors.surname?.type === "required" && (
                                            <p className="form-control is-invalid form-error-label">
                                                {t("CreateAccount.error.surname.isRequired")}
                                            </p>
                                        )}
                                        {errors.surname?.type === "length" && (
                                            <p className="form-control is-invalid form-error-label">
                                                {t("CreateAccount.error.surname.length")}
                                            </p>
                                        )}
                                        {errors.name?.type === "pattern" && (
                                            <p className="form-control is-invalid form-error-label">
                                                {t("CreateAccount.error.surname.pattern")}
                                            </p>
                                        )}
                                    </div>
                                </form>

                                <div className="col-12 px-0 d-flex align-items-center justify-content-around">
                                    <button className="btn btn-cancel-form px-3 py-2" id="cancelFormButton"
                                            aria-label={t("AriaLabel.cancel")} title={t("AriaLabel.cancel")}
                                            onClick={() => navigate(-1)}>
                                        {t('Button.cancel')}
                                    </button>
                                    <button form="editProfileForm" id="editProfileFormButton"
                                            className="btn btn-submit-form px-3 py-2" type="submit"
                                            aria-label={t("AriaLabel.save")} title={t("AriaLabel.save")}>
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