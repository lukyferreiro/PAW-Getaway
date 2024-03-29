import {useTranslation} from "react-i18next"
import "../common/i18n/index"
import {useForm} from "react-hook-form"
import React, {useEffect, useState} from "react"
import {loginService} from "../services"
import {useAuth} from "../hooks/useAuth"
import {Link, useLocation, useNavigate} from 'react-router-dom'
import {IconButton} from "@mui/material"
import ModalResetPassword from "../components/ModalResetPassword"
import {showToast} from "../scripts/toast"
// @ts-ignore
import VisibilityIcon from "@mui/icons-material/Visibility"
// @ts-ignore
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff"
import ic_user_black from "../images/ic_user_black.svg";
import ic_password from "../images/ic_password.svg";

type FormDataLogin = {
    email: string;
    password: string;
};

export default function Login() {
    const {t} = useTranslation()
    const navigate = useNavigate()
    const location = useLocation()

    const {signIn} = useAuth()

    // @ts-ignore
    const from = location.state?.from?.pathname || "/";
    const [seePassword, setSeePassword] = useState(false);
    const isOpenPassword = useState(false);
    const [invalidCredentials, setInvalidCredendtials] = useState(false);

    const {register, handleSubmit} = useForm<FormDataLogin>({
        criteriaMode: "all",
    });

    useEffect(() => {
        document.title = `${t('PageName')} - ${t('PageTitles.login')}`
    }, [])

    const onSubmitLogin = handleSubmit((data: FormDataLogin) => {
            setInvalidCredendtials(false);
            loginService.login(data.email, data.password)
                .then((result) => {
                    if (!result.hasFailed()) {
                        const user = signIn(() => {
                            navigate(from, {replace: true})
                            showToast(t('Login.toast.success', {
                                name: user.name,
                                surname: user.surname
                            }), 'success')
                            if (!user.isVerified) {
                                showToast(t('Login.toast.verifySent'), 'success')
                            }
                        })
                    } else {
                        setInvalidCredendtials(true)
                    }
                })
                .catch(() => {
                    showToast(t('Login.toast.error'), 'error')
                });
        }
    );

    function showPassword() {
        setSeePassword(!seePassword)
    }

    return (
        <div className="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">
            <div className="container-lg w-100 p-2 modalContainer">
                <div className="row w-100 m-0 p-5 align-items-center justify-content-center">
                    <div className="col-12">
                        <h1 className="text-center title">
                            {t('Navbar.loginTitle')}
                        </h1>
                        <p className="subtitle text-center">
                            {t('Navbar.loginDescription')}
                        </p>
                    </div>
                    <div className="col-12">
                        <div className="container-fluid px-0">
                            <form id="loginForm" onSubmit={onSubmitLogin}>
                                <div className="row">
                                    <div className="col-12">  {/*  Email */}
                                        <label className="form-label d-flex align-items-center"
                                               htmlFor="email">
                                            <img src={ic_user_black} alt="Imagen perfil"
                                                 style={{marginRight: "5px"}}/>
                                            {t('Navbar.email')}
                                        </label>
                                        <input type="text" id="email"
                                               placeholder={t('Navbar.emailPlaceholder')}
                                               className="form-control mb-2"
                                               aria-describedby="email input"
                                               {...register("email", {})}/>
                                    </div>
                                </div>

                                <div className="col-12 mt-2">
                                    <div className="container-fluid">
                                        <div className="row">
                                            <div className="col-6 px-0">
                                                <label className="form-label d-flex align-items-center"
                                                       htmlFor="password">
                                                    <img src={ic_password} alt="Imagen perfil"
                                                         style={{marginRight: "5px"}}/>
                                                    {t('Navbar.password')}
                                                </label>
                                            </div>
                                            <div className="col-6 px-0 d-flex justify-content-end form-label">
                                                <div className="link-primary text-end" style={{cursor: "pointer"}} tabIndex={-1}
                                                     onClick={() => {
                                                         isOpenPassword[1](true)
                                                     }}>
                                                   <span style={{fontSize: "medium"}}>
                                                       {t('Navbar.forgotPassword')}
                                                   </span>
                                                </div>
                                            </div>
                                            <div className="col-12 px-0 d-flex justify-content-start align-items-center">
                                                <div className="input-group d-flex justify-content-start align-items-center">
                                                    <input type={seePassword ? "text" : "password"} className="form-control"
                                                           id="password" aria-describedby="password input"
                                                           {...register("password", {})}/>
                                                    <div className="input-group-append">
                                                        <IconButton className="btn btn-eye input-group-text"
                                                                    id="passwordEye" type="button" tabIndex={-1} onClick={() => showPassword()}
                                                                    aria-label={t("AriaLabel.showPassword")} title={t("AriaLabel.showPassword")}>
                                                            {seePassword ? <VisibilityIcon/> : <VisibilityOffIcon/>}
                                                        </IconButton>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div className="col-12 d-flex align-items-center justify-content-center">
                        {invalidCredentials &&
                            <p className="form-control is-invalid form-error-label">
                                {t("Login.invalidCredentials")}
                            </p>
                        }
                    </div>


                    <div className="col-12 d-flex align-items-center justify-content-center">
                        <button form="loginForm" type="submit" className="w-100 btn-login my-2"
                                aria-label={t("AriaLabel.login")} title={t("AriaLabel.login")}>
                            {t('Navbar.login')}
                        </button>
                    </div>
                    <div className="col-12 mt-4">
                        <p className="mb-0 text-center">
                            {t('Navbar.newUser')}
                        </p>
                        <Link to="/createAccount">
                            <div className="form-label mb-0 text-center" tabIndex={-1}>
                                {t('Navbar.createAccount')}
                            </div>
                        </Link>
                    </div>
                </div>
            </div>

            <ModalResetPassword isOpen={isOpenPassword}/>
        </div>
    );

}