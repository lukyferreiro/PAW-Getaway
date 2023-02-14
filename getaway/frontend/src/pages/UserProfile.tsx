import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {useEffect, useState} from "react";
import {serviceHandler} from "../scripts/serviceHandler";
import {userService} from "../services";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../hooks/useAuth";
import DataLoader from "../components/DataLoader";
import {getQueryOrDefault, useQuery} from "../hooks/useQuery";

export default function UserProfile() {

    const {t} = useTranslation()
    const navigate = useNavigate()

    //TODO manejar estos token que recibe el usuario tras entrar al link por el mail
    const query = useQuery()
    const verificationToken = getQueryOrDefault(query, "verificationToken", "")
    const passwordToken = getQueryOrDefault(query, "passwordToken", "")

    const {user} = useAuth()

    const [userImg, setUserImg] = useState<string | undefined>(undefined)
    const [isLoadingImg, setIsLoadingImg] = useState(false)

    useEffect(() => {
        setIsLoadingImg(true)
        if (user) {
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
    }, [user])


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

                    <div className="mb-2">
                        {user?.verified ?
                            <button type="button" className="btn btn-error">
                                {t('User.profile.editBtn')}
                            </button>
                            :
                            <button type="button" className="btn btn-error">
                                {t('User.profile.verifyAccountBtn')}
                            </button>
                        }
                    </div>
                </div>
            </div>
        </DataLoader>
    );

}