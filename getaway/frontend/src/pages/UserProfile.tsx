import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import React, {useEffect, useState} from "react";
import {serviceHandler} from "../scripts/serviceHandler";
import {userService} from "../services";
import {useNavigate, useSearchParams} from "react-router-dom";
import {useAuth} from "../hooks/useAuth";
import DataLoader from "../components/DataLoader";
import {getQueryOrDefault, useQuery} from "../hooks/useQuery";
import {useForm} from "react-hook-form";

type FormDataImg = {
    image?: FileList
}

export default function UserProfile() {

    const {t} = useTranslation()
    const navigate = useNavigate()

    //TODO manejar estos token que recibe el usuario tras entrar al link por el mail
    const query = useQuery()
    const verificationToken = getQueryOrDefault(query, "verificationToken", "")

    const rememberMe = localStorage.getItem("rememberMe") === "true"

    const {user, setUser, signIn} = useAuth()

    const [userImg, setUserImg] = useState<string | undefined>(undefined)
    const [isLoadingImg, setIsLoadingImg] = useState(false)
    const [reload, setReload] = useState(true)
    const [searchParams, setSearchParams] = useSearchParams();

    useEffect(() => {
        if (verificationToken !== "" && !user?.verified ) {
            serviceHandler(
                userService.verifyUser(verificationToken),
                navigate, () => {

                },
                () => {
                    searchParams.delete("verificationToken")
                    setSearchParams(searchParams)
                    //TODO
                    //SHOW SNACKBAR
                },
                () => {
                }
            )
            userService.getCurrentUser()
                .then((user) => {
                        if (!user.hasFailed()) {
                            signIn(user.getData(), rememberMe, ()=> {navigate("/user/profile")})
                        }
                    }
                )
                .catch(() => navigate("/error?code=500&message=Server error"));
        }
    }, [])

    useEffect(() => {
        setIsLoadingImg(true)
        if (user?.hasImage) {
            serviceHandler(
                userService.getUserProfileImage(user?.id),
                navigate, (userImg) => {
                    setUserImg(userImg.size > 0 ? URL.createObjectURL(userImg) : undefined)
                },
                () => {
                    setIsLoadingImg(false)
                },
                () => {
                    setIsLoadingImg(false)
                }
            )
        }
    }, [user, reload])

    const {register, handleSubmit, reset, formState: { errors },} =
        useForm<FormDataImg>({ criteriaMode: 'all' })

    const onSubmit = handleSubmit((data: FormDataImg) => {
        console.log(data.image![0].name)
        console.log(data.image![0].size)
        userService
            .updateUserProfileImage(user ? user.id : -1, data.image![0])
            .then((result) => {
                if (!result.hasFailed()) {
                    const imgAsUrl = URL.createObjectURL(data.image![0])
                    setUser({ ...user!, url: imgAsUrl })
                    setReload(!reload)
                    reset()
                }
            })
            .catch(() => {})
    })

    return (
        <DataLoader spinnerMultiplier={2} isLoading={isLoadingImg}>
            <div className="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">
                <div className="container-lg w-100 modalContainer d-flex flex-column justify-content-center align-items-center">
                    <div className="m-2">
                        <h1>
                            {t('User.profile.description')}
                        </h1>
                    </div>
                    <div className="m-2" style={{maxWidth: "200px"}}>
                        <img className="container-fluid p-0" style={{height: "fit-content"}} alt="Imagen usuario"
                             src={userImg ? userImg : './images/user_default.png'}/>
                    </div>

                    <div className="m-1 justify-self-center align-self-center">
                        <h3>
                            {t('User.profile.name', {userName: user?.name})}
                        </h3>
                    </div>
                    <div className="m-1 justify-self-center align-self-center">
                        <h3>
                            {t('User.profile.surname', {userSurname: user?.surname})}
                        </h3>
                    </div>
                    <div className="m-1 justify-self-center align-self-center">
                        <h3>
                            {t('User.profile.email', {userEmail: user?.email})}
                        </h3>
                    </div>

                    <div className="m-1 justify-self-center align-self-center">
                        <form encType='multipart/form-data' acceptCharset='utf-8'
                              id="imageForm" onSubmit={onSubmit}>
                            <label className="form-label d-flex justify-content-between">
                                {t("User.imgTitle")}
                            </label>

                            <input type='file'
                                   accept='image/png, image/jpeg, image/jpg' className="form-control"
                                   {...register("image", {
                                       validate: {
                                           required: (image) =>
                                               image !== undefined && image[0] !== undefined,

                                           size: (image) =>
                                               image && image[0] && image[0].size / (1024 * 1024) < 5,
                                       },
                                   })}
                            />
                            {errors.image?.type === 'required' && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("User.error.image.isRequired")}
                                </p>
                            )}
                            {errors.image?.type === 'size' && (
                                <p className="form-control is-invalid form-error-label">
                                    {t("User.error.image.size")}
                                </p>
                            )}
                        </form>
                    </div>
                    <div className="m-1 justify-self-center align-self-center">
                        <button className="btn btn-submit-form px-3 py-2" id="imageFormButton"
                                form="imageForm" type="submit">
                            {t('Button.create')}
                        </button>
                    </div>


                    <div className="mb-2">
                        {user?.verified ?
                            <button onClick={() => navigate({pathname: "/user/editAccount"})} type="button" className="btn btn-error">
                                {t('User.profile.editBtn')}
                            </button>
                            :
                            <button onClick={() => userService.sendNewVerifyUserEmail()} type="button" className="btn btn-error">
                                {/*TODO: add a snackbar to confirm*/}
                                {t('User.profile.verifyAccountBtn')}
                            </button>
                        }
                    </div>
                </div>
            </div>
        </DataLoader>
    );

}