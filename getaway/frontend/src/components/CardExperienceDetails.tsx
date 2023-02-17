import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {Link, useNavigate} from "react-router-dom";
import {IconButton} from "@mui/material";
// @ts-ignore
import VisibilityIcon from "@mui/icons-material/Visibility";
// @ts-ignore
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import React from "react";
import {CategoryModel, ExperienceModel} from "../types";
import StarRating from "./StarRating";
import {experienceService} from "../services";
import confirmDialogModal from "./ConfirmDialogModal";

export default function CardExperienceDetails(props: { experience: ExperienceModel; categoryModel: CategoryModel; isEditing: boolean; }) {

    const {experience, categoryModel, isEditing} = props
    const {t} = useTranslation();
    const hasImage = false;
    const navigate = useNavigate()
    const handleSubmit = () => console.log("ok")
    function setVisibility(experienceId: number, visibility: boolean) {
        experienceService.setExperienceObservable(experienceId, visibility).then()
            .catch(() => {
            });
    }

    function deleteExperience(experienceId: number) {
        experienceService.deleteExperienceById(experienceId).then()
            .catch(() => {
            });
        // navigate(-1)
    }

    function editExperience(experienceId: number) {
        navigate({pathname: "/experienceForm", search: `?id=${experienceId}`}, {replace: true});
    }


    return (
        <>
            <div className="d-flex flex-wrap justify-content-center align-content-center">
                <div className="d-flex flex-column">
                    <div className="p-2" style={{width: "600px"}}>
                        {hasImage ?
                            <img className="container-fluid p-0" style={{height: "fit-content", maxHeight: "550px"}}
                                 src={`/experiences/${experience.id}/experienceImage`} alt={`Imagen ${experience.name}`}/>
                            :
                            <img className="container-fluid p-0" style={{height: "fit-content", maxHeight: "450px"}}
                                 src={`./images/${categoryModel.name}.svg`} alt={`${categoryModel.name}`}/>
                        }
                        {!hasImage &&
                            <h5 className="mt-3 text-center">
                                {t('ExperienceDetail.imageDefault')}
                            </h5>}
                    </div>
                </div>
                <div style={{flex: "5", minWidth: "350px"}}>
                    <div className="row">
                        <div className="col-3 p-0"/>
                        <div className="card-body col-7 h-100">
                            <div> {/*  Direccion y ciudad */}
                                <h5 className="information-title">
                                    {t('Experience.address')}
                                </h5>
                                <p className="information-text">
                                    {experience.address}, {experience.city.name}, {experience.country.name}
                                </p>
                            </div>

                            <div> {/*Precio*/}
                                <h5 className="information-title">
                                    {t('Experience.price.name')}
                                </h5>
                                <div className="information-text">
                                    {(experience.price === undefined ?
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
                                </div>
                            </div>

                            <div> {/* Descripcion */}
                                <h5 className="information-title">
                                    {t('ExperienceDetail.description')}
                                </h5>
                                <div className="information-text" id="experienceDescription">
                                    {experience.description == '' ?
                                        <p>
                                            {t('ExperienceDetail.noData')}
                                        </p>
                                        :
                                        <p>
                                            {experience.description}
                                        </p>
                                    }
                                </div>
                            </div>

                            <div> {/* URL */}
                                <h5 className="information-title">
                                    {t('ExperienceDetail.url')}
                                </h5>
                                {experience.siteUrl == '' ?
                                    <p className="information-text">
                                        {t('ExperienceDetail.noData')}
                                    </p>
                                    :
                                    //TODO: make this link to redirect
                                    <a href={experience.siteUrl}>
                                        <p className="information-text">
                                            {experience.siteUrl}
                                        </p>
                                    </a>
                                }
                            </div>

                            <div> {/*  Email de contacto */}
                                <h5 className="information-title">
                                    {t('ExperienceDetail.email')}
                                </h5>
                                <p className="information-text">
                                    {experience.email}
                                </p>
                            </div>

                            <div className="d-flex"> {/* Ranking */}
                                <h6 className="information-title">
                                    {t('ExperienceDetail.review')}: {experience.reviewCount}
                                </h6>
                                <StarRating score={experience.score}/>
                            </div>
                        </div>
                        <div className="col-2 p-0"></div>
                    </div>
                </div>
            </div>

            {!experience.observable &&
                <div className="my-1 d-flex justify-content-center align-content-center">
                    <h6 className="obs-info">
                        {t('ExperienceDetail.notVisible')}
                    </h6>
                </div>
            }

            {isEditing &&
                <div className="btn-group my-2 d-flex justify-content-center align-content-center" role="group">
                    {experience.observable ?
                        <div>
                            {/*<a href="<c:url value=" ${param.path}"> <c:param name=" setObs" value="*/}
                            {/*   ${false}"/> </c:url>">*/}
                            <IconButton onClick={() => setVisibility(experience.id, false)} aria-label="visibilityOn" component="span" style={{fontSize: "xxx-large"}} id="setFalse">
                                <VisibilityIcon/>
                            </IconButton>
                            {/*</a>*/}
                        </div>
                        :
                        <div>
                            {/*<a href="<c:url value=" ${param.path}"> <c:param name=" setObs" value="*/}
                            {/*   ${true}"/> </c:url>">*/}
                            <IconButton onClick={() => setVisibility(experience.id, true)} aria-label="visibilityOff" component="span" style={{fontSize: "xx-large"}} id="setTrue">
                                <VisibilityOffIcon/>
                            </IconButton>
                            {/*</a>  */}
                        </div>

                    }

                    {/*<a href="<c:url value="/user/experiences/edit/${param.id}"/>">*/}
                    <IconButton onClick={() => editExperience(experience.id)} aria-label="edit" component="span" style={{fontSize: "xx-large"}}>
                        <EditIcon/>
                    </IconButton>

                    {/*</a>*/}
                    {/*<a href="<c:url value="/user/experiences/delete/${param.id}"/>">*/}
                    <IconButton onClick={() => deleteExperience(experience.id)} aria-label="trash" component="span" style={{fontSize: "xx-large"}}>
                        <DeleteIcon/>
                    </IconButton>
                    {/*</a>*/}
                </div>
            }
        </>
    );

}