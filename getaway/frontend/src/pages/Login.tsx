import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {useLocation, useNavigate} from "react-router-dom";
import {useForm} from "react-hook-form";
import React, {useState} from "react";
import {loginService} from "../services";
import {useAuth} from "../hooks/useAuth";


type FormData = {
    email: string;
    password: string;
    rememberMe: boolean;
};

export default function Login() {

    const {t} = useTranslation();
    let navigate = useNavigate();
    let location = useLocation();
    let auth = useAuth();
    // @ts-ignore
    let from = location.state?.from?.pathname || "/";
    const [invalidCredentials, setInvalidCredendtials] = useState(false);

    const {register, handleSubmit} = useForm<FormData>({
        criteriaMode: "all",
    });

    const onSubmit = handleSubmit(({email, password, rememberMe}: FormData) => {
            setInvalidCredendtials(false);
            loginService.login(email, password)
                .then((user) =>
                    user.hasFailed() ? setInvalidCredendtials(true) :
                        auth.signIn(user.getData(), rememberMe, () => {
                            navigate(from, {replace: true});
                        })
                )
                .catch(() => navigate("/error?code=500"));
        }
    );

    // let eyeBtn = document.getElementById("passwordEye") ;
    // let passwordInput = document.getElementById("password");
    // let eye = document.getElementById("eye");
    // let visible = false;

    // @ts-ignore
    // eyeBtn.addEventListener("click", () => {
    //     if (visible) {
    //         visible = false;
    //         // @ts-ignore
    //         passwordInput.setAttribute("type", "password");
    //     } else {
    //         visible = true;
    //         // @ts-ignore
    //         passwordInput.setAttribute("type", "text");
    //     }
    //     // @ts-ignore
    //     eye.classList.toggle("fa-eye-slash");
    //     // @ts-ignore
    //     eye.classList.toggle("fa-eye");
    // });

    return (
        <div className="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">
            <div className="container-lg w-100 p-2">
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
                            <form id="loginForm" onSubmit={onSubmit}>
                                <div className="row">
                                    <div className="col-12">  {/*  Email */}
                                        <label className="form-label d-flex align-items-center"
                                               htmlFor="email">
                                            <img src={"./images/ic_user.svg"} alt="Imagen perfil"
                                                 style={{marginRight: "5px"}}/>
                                            {t('Navbar.email')}
                                        </label>
                                        <input type="text" id="email"
                                               className="form-control mb-2"
                                               aria-describedby="email input"
                                               {...register("email", {})}/>
                                    </div>
                                </div>

                                <div className="col-12 mt-2">
                                    <div className="container-fluid">
                                        <div className="row">
                                            <div className="col-6 px-0">
                                                <label
                                                    className="form-label d-flex align-items-center"
                                                    htmlFor="password">
                                                    <img src={"./images/ic_password.svg"} alt="Imagen perfil"
                                                         style={{marginRight: "5px"}}/>
                                                    {t('Navbar.password')}
                                                </label>
                                            </div>
                                            <div className="col-6 px-0 d-flex justify-content-end form-label">
                                                <div className="link-primary" tabIndex={-1}>
                                                   <span className="text-right" style={{fontSize: "medium"}}>
                                                       {t('Navbar.forgotPassword')}
                                                   </span>
                                                </div>
                                            </div>
                                            <div className="col-12 px-0 d-flex justify-content-start align-items-center">
                                                <div className="input-group d-flex justify-content-start align-items-center">
                                                    <input type="password" className="form-control"
                                                           id="password" aria-describedby="password input"
                                                           {...register("password", {})}/>
                                                    <div className="input-group-append">
                                                        <button className="btn btn-lg form-control btn-eye input-group-text"
                                                                id="passwordEye" type="button" tabIndex={-1} >
                                                            <i id="eye" className="far fa-eye-slash"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div className="col-12 mt-3 d-flex justify-content-start align-items-center">
                                    <input type="checkbox" id="rememberMe"
                                           {...register("rememberMe", {})}/>
                                    <label className="mb-0 mx-2" htmlFor="rememberMe">
                                        {t('Navbar.rememberMe')}
                                    </label>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div className="col-12 d-flex align-items-center justify-content-center">
                        {invalidCredentials && (
                            <p>Todo mal</p>
                        )}
                    </div>


                    <div className="col-12 d-flex align-items-center justify-content-center">
                        <button form="loginForm" type="submit" className="w-100 btn-login my-2">
                            {t('Navbar.login')}
                        </button>
                    </div>
                    <div className="col-12 mt-4">
                        <p className="mb-0 text-center">
                            {t('Navbar.newUser')}
                            <div className="link-primary form-label"
                                 tabIndex={-1}>
                                {t('Navbar.createAccount')}
                            </div>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );

}