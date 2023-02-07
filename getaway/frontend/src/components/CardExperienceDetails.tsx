import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {Link} from "react-router-dom";
import {IconButton} from "@mui/material";
import VisibilityIcon from "@mui/icons-material/Visibility";
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import React from "react";
import {CategoryModel, ExperienceModel} from "../types";

export default function CardExperienceDetails(props: { experience: ExperienceModel; categoryModel: CategoryModel; }) {

    const {experience, categoryModel} = props
    const {t} = useTranslation();
    const isEditing = true;
    const hasImage = false;

    return (
        <div>
            <div className="d-flex flex-wrap justify-content-center align-content-center">
                <div className="d-flex flex-column">
                    <div className="p-2" style={{width: "600px"}}>
                        {hasImage ?
                            <img className="container-fluid p-0" style={{height: "fit-content", maxHeight: "550px"}}
                                 src={`/experiences/${experience.id}/image.svg`} alt="Imagen"/>
                            :
                            <div>
                                <img className="container-fluid p-0" style={{height: "fit-content", maxHeight: "450px"}}
                                     src={`./images/${categoryModel.name}.svg`} alt={`${categoryModel.name}`}/>
                                <h5 className="mt-3 text-center">
                                    {t('ExperienceDetail.imageDefault')}
                                </h5>
                            </div>
                        }

                    </div>
                </div>
                <div style={{flex: "5", minWidth: "350px"}}>
                    <div className="row">
                        <div className="col-3 p-0"></div>
                        <div className="card-body col-7 h-100">
                            <div>{/*  Direccion y ciudad */}
                                <h5 className="information-title">
                                    {t('Experience.address')}
                                </h5>
                                <p className="information-text">
                                    {experience.address}
                                </p>
                            </div>

                            <div> {/*Precio*/}
                                <h5 className="information-title">
                                    {t('Experience.price')}
                                </h5>
                                <p className="information-text">
                                    {/*<c:when test="${param.price == ''}">*/}
                                    {/*    <spring:message code="experienceDetail.price.noPrice"/>*/}
                                    {/*</c:when>*/}
                                    {experience.price == 0 &&
                                        <h5>
                                            {t('ExperienceDetail.priceFree')}
                                        </h5>
                                    }
                                    {experience.price > 0 &&
                                        <h5>
                                            $ {experience.price}
                                        </h5>
                                    }
                                </p>
                            </div>

                            <div> {/* Descripcion */}
                                <h5 className="information-title">
                                    {t('ExperienceDetail.description')}
                                </h5>
                                <p className="information-text" id="experienceDescription">
                                    {experience.description == '' ?
                                        <div>
                                            {t('ExperienceDetail.noData')}
                                        </div>
                                    :
                                        <div>
                                            {experience.description}
                                        </div>
                                    }
                                </p>
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
                                    <Link to={experience.siteUrl}>
                                        <p className="information-text">
                                            {experience.siteUrl}
                                        </p>
                                    </Link>
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
                                    {t('ExperienceDetails.review')}: {experience.reviewsCount}
                                </h6>
                                {/*<jsp:include page="/WEB-INF/components/starAvg.jsp">*/}
                                {/*    <jsp:param name="avgReview" value="${param.reviewAvg}"/>*/}
                                {/*</jsp:include>*/}
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
                            <IconButton aria-label="visibilityOn" component="span" style={{fontSize: "xxx-large"}} id="setFalse">
                                <VisibilityIcon/>
                            </IconButton>
                            {/*</a>*/}
                        </div>
                        :
                        <div>
                            {/*<a href="<c:url value=" ${param.path}"> <c:param name=" setObs" value="*/}
                            {/*   ${true}"/> </c:url>">*/}
                            <IconButton aria-label="visibilityOff" component="span" style={{fontSize: "xx-large"}} id="setTrue">
                                <VisibilityOffIcon/>
                            </IconButton>
                            {/*</a>  */}
                        </div>

                    }

                    {/*<a href="<c:url value="/user/experiences/edit/${param.id}"/>">*/}
                    <IconButton aria-label="edit" component="span" style={{fontSize: "xx-large"}}>
                        <EditIcon/>
                    </IconButton>
                    {/*</a>*/}
                    {/*<a href="<c:url value="/user/experiences/delete/${param.id}"/>">*/}
                    <IconButton aria-label="trash" component="span" style={{fontSize: "xx-large"}}>
                        <DeleteIcon/>
                    </IconButton>
                    {/*</a>*/}
                </div>
            }
        </div>
    );

}