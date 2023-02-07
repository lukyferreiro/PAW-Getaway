import {useTranslation} from "react-i18next";
import React, {useState} from "react";
import {IconButton} from "@mui/material";
import VisibilityIcon from "@mui/icons-material/Visibility";
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";

export default function CreateAccount() {
    const {t} = useTranslation();
    const [seePassword, setSeePassword] = useState(false);
    const [seeRepeatPassword, setSeeRepeatPassword] = useState(false);

    function showPassword(){
        setSeePassword(!seePassword);
    }

    function showRepeatPassword(){
        setSeeRepeatPassword(!seeRepeatPassword);
    }
    return (
            <div
                className="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">
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
                                {/*<c:url value="/register" var="postPath"/>*/}
                                {/*<form modelAttribute="registerForm" action="${postPath}"*/}
                                {/*           id="registerForm"*/}
                                {/*           method="post" acceptCharset="UTF-8">*/}
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
                                    <input type="text" id="email" name="email"
                                           className="form-control mb-2"
                                           placeholder={t('Navbar.emailPlaceholder')}
                                           aria-describedby="email input"
                                           maxLength={255}/>
                                    {/*<form:errors path="email" cssClass="form-error-label"*/}
                                    {/*             element="p"/>*/}
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

                                    <input maxLength={50} type="text" name="name" id="name"
                                           className="form-control"
                                           placeholder={t('Navbar.namePlaceholder')}/>
                                    {/*<form:errors path="name" cssClass="form-error-label"*/}
                                    {/*             element="p"/>*/}
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
                                    <input maxLength={50} type="text" name="surname" id="surname"
                                           className="form-control"
                                           placeholder={t('Navbar.surnamePlaceholder')}
                                    />
                                    {/*<form:errors path="surname" cssClass="form-error-label"*/}
                                    {/*             element="p"/>*/}
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
                                    <div
                                        className="input-group d-flex justify-content-start align-items-center">
                                        <input type={seePassword ? "text":"password"}
                                               className="form-control"
                                               id="password1" name="password"
                                               aria-describedby="password input"
                                               placeholder={t('Navbar.passwordPlaceholder')}
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
                                    {/*<form:errors path="password" cssClass="form-error-label"*/}
                                    {/*             element="p"/>*/}
                                </div>

                                <div className="form-group">
                                    <label htmlFor="confirmPassword" className="form-label">
                                        {t('Navbar.confirmPassword')}
                                        <span className="required-field">*</span>
                                    </label>
                                    <div
                                        className="input-group d-flex justify-content-start align-items-center">
                                        <input type={seeRepeatPassword ? "text":"password"}
                                               className="form-control"
                                               id="password2" name="confirmPassword"
                                               aria-describedby="password input"/>
                                        <div className="input-group-append">
                                            <button className="btn btn-eye input-group-text"
                                                    id="passwordEye2" type="button" tabIndex={-1} onClick={() => showRepeatPassword()}>
                                                <IconButton aria-label="eye2">
                                                    {seeRepeatPassword ? <VisibilityIcon/> : <VisibilityOffIcon/>}
                                                </IconButton>
                                            </button>
                                        </div>
                                    </div>
                                    {/*<form:errors path="confirmPassword" cssClass="form-error-label"*/}
                                    {/*             element="p"/>*/}
                                </div>

                                <div className="form-group">
                                    {/*<spring:hasBindErrors name="registerForm">*/}
                                    {/*    <c:if test="${errors.globalErrorCount > 0}">*/}
                                    {/*        <div className="alert alert-danger">*/}
                                    {/*            <form:errors/>*/}
                                    {/*        </div>*/}
                                    {/*    </c:if>*/}
                                    {/*</spring:hasBindErrors>*/}
                                </div>

                                <div
                                    className="col-12 px-0 d-flex align-items-center justify-content-center">
                                    <button type="button" id="registerFormButton"
                                            form="registerForm"
                                            className="w-100 btn-create-account my-2 ">
                                        {t('Navbar.createAccount')}
                                    </button>
                                </div>
                                {/*</form>*/}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    );
}