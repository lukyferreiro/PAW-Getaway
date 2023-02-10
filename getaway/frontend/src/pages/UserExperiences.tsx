import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {Link, Navigate, useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {ExperienceModel} from "../types";
import {useAuth} from "../hooks/useAuth";
import {serviceHandler} from "../scripts/serviceHandler";
import {experienceService, userService} from "../services";
import StarRating from "../components/StarRating";
import {IconButton} from "@mui/material";
import VisibilityIcon from "@mui/icons-material/Visibility";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";

export default function UserExperiences() {

    const {t} = useTranslation();
    const navigate = useNavigate()

    const [userExperiences, setUserExperiences] = useState<ExperienceModel[]>(new Array(0))
    const {user} = useAuth();
    const [name, setName] = useState(undefined);
    const [page, setPage] = useState(1);
    const [order, setOrder] = useState("OrderByAZ");

    const isProvider = localStorage.getItem("isProvider") === "true";

    useEffect(() => {
        serviceHandler(
            userService.getUserExperiences(user ? user.id : -1, name, order, page),
            navigate, (experiences) => {
                setUserExperiences(experiences.getContent())
            },
            () => {}
        )
    }, [userExperiences])

    if (!isProvider) {
        return <Navigate to="/" replace/>;
    }

    function setVisibility(experienceId:number , visibility: boolean) {
        experienceService.setExperienceObservable(experienceId, visibility).then()
            .catch(() => {});
    }

    function deleteExperience(experienceId:number ) {
        experienceService.deleteExperienceById(experienceId).then()
            .catch(() => {});
    }

    function editExperience(experienceId:number ) {
        // experienceService.deleteExperienceById(experienceId).then()
        //     .catch(() => {});
    }

        return (
        <div className="container-fluid p-0 my-3 d-flex flex-column justify-content-center">
            {userExperiences.length == 0 ?
                <div className="my-auto d-flex justify-content-center align-content-center">
                    <h2 className="title">
                        {t('User.noExperiences')}
                    </h2>
                </div>
                :
                <div>
                    {/*SEARCH and ORDER*/}
                    <div className="d-flex justify-content-center align-content-center">
                        <div style={{margin: "0 auto 0 20px", flex: "1"}}>
                                {/*<jsp:include page="/WEB-INF/components/orderDropdown.jsp">*/}
                                {/*    <jsp:param name="orderByModels" value="${orderByModels}"/>*/}
                                {/*    <jsp:param name="path" value="${path}"/>*/}
                                {/*    <jsp:param name="userQuery" value="${userQuery}"/>*/}
                                {/*    <jsp:param name="orderPrev" value="${orderBy}"/>*/}
                                {/*</jsp:include>*/}
                        </div>

                        <h3 className="title m-0">
                            {t('User.experiencesTitle')}
                        </h3>

                        <div className="d-flex justify-content-center align-content-center"
                             style={{margin: "0 20px 0 auto", flex: "1"}}>
                            <button className="btn btn-search-navbar p-0" type="submit" form="searchExperiencePrivateForm">
                                <img src={'./images/ic_lupa.svg'} alt="Icono lupa"/>
                            </button>
                            {/*<spring:message code="navbar.search" var="placeholder"/>*/}
                            {/*<c:url value="/user/experiences" var="searchPrivateGetPath"/>*/}
                            <form className="my-auto" id="searchExperiencePrivateForm">
                                <input type="text" className="form-control" placeholder={t('Navbar.search')}/>
                                {/*<form:errors path="userQuery" element="p" cssClass="form-error-label"/>*/}
                            </form>
                        </div>
                    </div>

                    <div className="mt-4 mx-5">
                            {/*TODO cuando la busqueda por nombre tenga 0 mostrar esto*/}
                            {/*<div className="my-auto mx-5 px-3 d-flex justify-content-center align-content-center">*/}
                            {/*     <div className="d-flex justify-content-center align-content-center">*/}
                            {/*             <img src={'./images/ic_no_search.jpeg'} alt="Imagen lupa"*/}
                            {/*                  style={{width: "150px", height: "150px", minWidth: "150px", minHeight: "150px", marginRight: "5px"}}>*/}
                            {/*                 <h4 className="d-flex align-self-center">*/}
                            {/*                     {t('EmptyResult')}*/}
                            {/*                 </h4>*/}
                            {/*         </div>*/}
                            {/* </div>*/}
                        <table className="table table-bordered table-hover table-fit">
                            <thead className="table-light">
                                <tr>
                                    <th scope="col">
                                        <h4 className="table-title"> {t('User.experiences.title')}</h4>
                                    </th>
                                    <th scope="col">
                                        <h4 className="table-title"> {t('User.experiences.category')}</h4>
                                    </th>
                                    <th scope="col">
                                        <h4 className="table-title"> {t('User.experiences.score')}</h4>
                                    </th>
                                    <th scope="col">
                                        <h4 className="table-title"> {t('User.experiences.price')}</h4>
                                    </th>
                                    <th scope="col">
                                        <h4 className="table-title"> {t('User.experiences.views')}</h4>
                                    </th>
                                    <th scope="col">
                                        <h4 className="table-title"> {t('User.experiences.actions')}</h4>
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                {userExperiences.map((experience) => (
                                    <tr>
                                        <th scope="row">
                                            <div className="title-link" style={{width: "350px"}}>
                                                <Link to={"/experiences/" + experience.id}>
                                                    <h4 className="experience card-title container-fluid p-0"
                                                        style={{wordBreak: "break-all"}}>
                                                        {experience.name}
                                                    </h4>
                                                </Link>
                                            </div>
                                        </th>
                                        <td>
                                            <div className="container-fluid d-flex p-2 mb-1 align-items-end">
                                                <h4 className="container-fluid p-0">
                                                    {t('Categories.' + experience.category.name)}
                                                </h4>
                                            </div>
                                        </td>
                                        <td>
                                            <div className="container-fluid d-flex p-2 mb-1 align-items-end">
                                                <h5 className="mb-1">
                                                    {t("User.experiences.reviewsCount", {count: experience.reviewCount})}
                                                </h5>
                                                <StarRating score={experience.score}/>
                                            </div>
                                        </td>
                                        <td>
                                            <div className="container-fluid d-flex p-2 mb-1 align-items-end">
                                                <h5 className="mb-1">
                                                    {
                                                        (experience.price === undefined ?
                                                            <div>
                                                                <h6>
                                                                    {t('Experience.price.null')}
                                                                </h6>
                                                            </div>

                                                            :
                                                            (experience.price == 0 ?
                                                                <div>
                                                                    <h6>
                                                                        {t('Experience.price.free')}
                                                                    </h6>
                                                                </div>
                                                                :
                                                                <div>
                                                                    <h6>
                                                                        {t('Experience.price.exist', {price: experience.price})}
                                                                    </h6>
                                                                </div>
                                                                ))
                                                    }
                                                </h5>
                                            </div>
                                        </td>
                                        <td>
                                            <div className="container-fluid d-flex p-2 mb-1 align-items-end">
                                                <h5 className="mb-1">
                                                    {experience.views}
                                                </h5>
                                            </div>
                                        </td>
                                        <td>
                                            <div className="btn-group w-auto container-fluid p-2 d-flex align-items-end" role="group">
                                                {experience.observable ?
                                                    <IconButton onClick={() => setVisibility(experience.id, false)} aria-label="visibilityOn" component="span" style={{fontSize: "x-large"}} id="setFalse">
                                                        <VisibilityIcon/>
                                                    </IconButton>
                                                    :
                                                    <IconButton onClick={() => setVisibility(experience.id, true)} aria-label="visibilityOff" component="span" style={{fontSize: "xx-large"}} id="setTrue">
                                                        <VisibilityOffIcon/>
                                                    </IconButton>
                                                }

                                                <IconButton onClick={() => editExperience(experience.id)} aria-label="edit" component="span" style={{fontSize: "x-large"}}>
                                                    <EditIcon/>
                                                </IconButton>
                                                <IconButton onClick={() => deleteExperience(experience.id)} aria-label="trash" component="span" style={{fontSize: "x-large"}}>
                                                    <DeleteIcon/>
                                                </IconButton>
                                            </div>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>

                        <div className="mt-auto d-flex justify-content-center align-items-center">
                            {/*TODO add pagination*/}
                        </div>
                    </div>
                </div>
            }
        </div>
    );

}