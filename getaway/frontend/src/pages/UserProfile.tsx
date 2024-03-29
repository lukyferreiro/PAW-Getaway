import {useTranslation} from "react-i18next"
import "../common/i18n/index"
import React, {useEffect, useState} from "react"
import {userService} from "../services"
import {useNavigate, useSearchParams} from "react-router-dom"
import {useAuth} from "../hooks/useAuth"
import DataLoader from "../components/DataLoader"
import {getQueryOrDefault, useQuery} from "../hooks/useQuery"
import {showToast} from "../scripts/toast"
import AddPhotoAlternateIcon from "@mui/icons-material/AddPhotoAlternate"
import {IconButton} from "@mui/material"
import AddPictureModal from "../components/AddPictureModal"
import ic_user_no_image from "../images/ic_user_no_image.png"

export default function UserProfile() {

    const {t} = useTranslation()
    const navigate = useNavigate()

    const query = useQuery()
    const verificationToken = getQueryOrDefault(query, "verificationToken", "")

    const {getUser, verifyUser, isVerified} = useAuth()
    const user = getUser()

    const isVerifiedValue = isVerified()
    const isOpenImage = useState(false)
    const [isLoadingImg, setIsLoadingImg] = useState(false)
    const [searchParams, setSearchParams] = useSearchParams();

    useEffect(() => {
        if (verificationToken !== "" || verificationToken === undefined) {
            if (isVerifiedValue) {
                showToast(t('User.toast.verify.alreadyVerified'), 'error')
            } else {
                userService.verifyUser(user?.sub, verificationToken)
                    .then((result) => {
                        if (!result.hasFailed()) {
                            verifyUser(() => navigate("/user/profile",{replace: true}))
                            showToast(t('User.toast.verify.success'), 'success')
                        }
                    })
                    .catch(() => {
                        showToast(t('User.toast.verify.error'), 'error')
                    })
                    .finally(() => {
                        searchParams.delete("verificationToken")
                        setSearchParams(searchParams)
                    })
            }
        }
        document.title = `${t('PageName')} - ${t('PageTitles.userProfile')}`
    }, [])

    return (
        <>
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
                                 src={user?.hasImage ? user.profileImageUrl : ic_user_no_image}/>
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
                                {t('User.profile.email', {userEmail: user?.sub})}
                            </h3>
                        </div>

                        <div className="mb-2">
                            {isVerifiedValue ?
                                <>
                                    <div className="d-flex flex-column justify-items-center align-items-center">
                                        <div className="d-flex justify-items-center align-items-center">
                                            <h4 className="mb-0">
                                                {t('User.profile.photo')}
                                            </h4>
                                            <IconButton
                                                onClick={() => {
                                                    isOpenImage[1](true)
                                                }}
                                                aria-label={t("AriaLabel.editImage")} title={t("AriaLabel.editImage")}
                                                component="span" style={{fontSize: "xx-large"}}>
                                                <AddPhotoAlternateIcon/>
                                            </IconButton>
                                        </div>
                                        <button onClick={() => navigate({pathname: "/user/editProfile"},{replace: true})} type="button" className="btn btn-error"
                                                aria-label={t("AriaLabel.editProfile")} title={t("AriaLabel.editProfile")}>
                                            {t('User.profile.editBtn')}
                                        </button>
                                    </div>
                                </>
                                :
                                <div className="m-1 justify-self-center align-self-center">
                                    <h6>
                                        {t("AriaLabel.verifyAccount")}
                                    </h6>
                                </div>
                            }
                        </div>
                    </div>
                </div>
            </DataLoader>
            <AddPictureModal isOpen={isOpenImage} userId={user?.userId}/>
        </>
    );

}