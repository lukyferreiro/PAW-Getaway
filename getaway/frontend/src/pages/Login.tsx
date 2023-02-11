import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {useForm} from "react-hook-form";
import React, {useState} from "react";
import {loginService} from "../services";
import {useAuth} from "../hooks/useAuth";
import Modal from "react-modal";
import {Link, useLocation, useNavigate} from 'react-router-dom'
import {IconButton} from "@mui/material";
import VisibilityIcon from "@mui/icons-material/Visibility";
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";

type FormDataLogin = {
    email: string;
    password: string;
    rememberMe: boolean;
};

type FormDataPassReset = {
    email: string;
};

export default function Login() {

    const {t} = useTranslation();
    let navigate = useNavigate();
    let location = useLocation();
    let auth = useAuth();
    // @ts-ignore
    let from = location.state?.from?.pathname || "/";
    const [seePassword, setSeePassword] = useState(false);
    const [isOpenPassword, setIsOpenPassword] = useState(false);
    const [invalidCredentials, setInvalidCredendtials] = useState(false);

    const {register, handleSubmit} = useForm<FormDataLogin>({
        criteriaMode: "all",
    });

    const onSubmitLogin = handleSubmit((data: FormDataLogin) => {
            setInvalidCredendtials(false);
            loginService.login(data.email, data.password)
                .then((user) =>
                    user.hasFailed() ? setInvalidCredendtials(true) :
                        auth.signIn(user.getData(), data.rememberMe, () => {
                            navigate(from, {replace: true});
                        })
                )
                .catch(() => navigate("/error?code=500&message=Server error"));
        }
    );

    function showPassword() {
        setSeePassword(!seePassword);
    }

    // const {register, handleSubmit} = useForm<FormDataPassReset>({
    //     criteriaMode: "all",
    // });
    //
    // const onSubmitPassReset = handleSubmit((data: FormData) => {
    //
    //     }
    // );

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
                                                <label className="form-label d-flex align-items-center"
                                                       htmlFor="password">
                                                    <img src={"./images/ic_password.svg"} alt="Imagen perfil"
                                                         style={{marginRight: "5px"}}/>
                                                    {t('Navbar.password')}
                                                </label>
                                            </div>
                                            <div className="col-6 px-0 d-flex justify-content-end form-label">
                                                <div className="link-primary" tabIndex={-1}
                                                     onClick={() => {
                                                         setIsOpenPassword(true)
                                                     }}>
                                                   <span className="text-right" style={{fontSize: "medium"}}>
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
                                                        <button className="btn btn-eye input-group-text"
                                                                id="passwordEye" type="button" tabIndex={-1} onClick={() => showPassword()}>
                                                            <IconButton aria-label="eye">
                                                                {seePassword ? <VisibilityIcon/> : <VisibilityOffIcon/>}
                                                            </IconButton>
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
                                    <label className="mb-0 mx-2" htmlFor="rememberMe" style={{fontSize: "medium"}}>
                                        {t('Navbar.rememberMe')}
                                    </label>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div className="col-12 d-flex align-items-center justify-content-center">
                        {invalidCredentials &&
                            <p>Todo mal</p>
                        }
                    </div>


                    <div className="col-12 d-flex align-items-center justify-content-center">
                        <button form="loginForm" type="submit" className="w-100 btn-login my-2">
                            {t('Navbar.login')}
                        </button>
                    </div>
                    <div className="col-12 mt-4">
                        <p className="mb-0 text-center">
                            {t('Navbar.newUser')}
                            <Link to="/createAccount">
                                <div className="form-label" tabIndex={-1}>
                                    {t('Navbar.createAccount')}
                                </div>
                            </Link>

                        </p>
                    </div>
                </div>
            </div>

            <Modal
                style={{overlay: {zIndex: 100}}}
                className="modal-pop-up"
                isOpen={isOpenPassword}
                contentLabel="PopUpPassword"
                onRequestClose={() => setIsOpenPassword(false)}
            >
                <div
                    className="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">
                    <div
                        className="row w-100 h-100 py-5 px-3 m-0 align-items-center justify-content-center">
                        <div className="col-12">
                            <h1 className="text-center title">
                                {t('Navbar.resetPasswordTitle')}
                            </h1>
                        </div>
                        <div className="col-12">
                            <div className="container-fluid">
                                <div className="row">
                                    {/*<form id="passResetRequest"*/}
                                    {/*           modelAttribute="resetPasswordEmailForm"*/}
                                    {/*           action="${postUrl}"*/}
                                    {/*           method="POST" acceptCharset="UTF-8">*/}
                                    <label className="form-label d-flex align-items-center"
                                           htmlFor="email">
                                        <img
                                            src={"./images/ic_user.svg"}
                                            alt="Imagen perfil"
                                            style={{marginRight: "5px"}}/>
                                        {t('Navbar.email')}
                                        <span className="required-field">*</span>

                                    </label>
                                    <input type="text" id="email" name="email"
                                           className="form-control mb-2"
                                           placeholder="juan@ejemplo.com"
                                           aria-describedby="email input"/>

                                    {/*<form:errors path="email" cssClass="form-error-label"*/}
                                    {/*             element="p"/>*/}
                                    <div
                                        className="col-12 mt-3 d-flex align-items-center justify-content-center">
                                        <button type="button" className='btn button-primary'>
                                            {/*<button form="passResetRequest" type="submit"*/}
                                            {/*        className="btn btn-continue">*/}
                                            {t('Navbar.resetPasswordButton')}
                                        </button>
                                    </div>
                                    {/*</form>*/}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </Modal>

        </div>
    );

}