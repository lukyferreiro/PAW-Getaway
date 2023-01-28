import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import CountryModel from "../types/CountryModel";
import CityModel from "../types/CityModel";
import {CategoryModel, ExperienceModel} from "../types";
import UserModel from "../types/UserModel";
import React from "react";
import {Link} from "react-router-dom";
import {IconButton} from "@mui/material";
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
export default function ExperienceDetails() {

    const {t} = useTranslation();

    const countryModel: CountryModel =
        {
            countryId: 2,
            name: "Pais campeon del mundo"
        }

    const cityModel: CityModel =
        {
            cityId: 1,
            name: "avellaneda city",
            country: countryModel
        }


    const categoryModel: CategoryModel =
        {categoryId: 1, name: 'Aventura'}


    const userModel: UserModel = {
        userId: 1,
        name: "LUCAS FERREIRO PA",
        surname: "LUCAS FERREIRO PA",
        email: "lferreiro@itba.edu.ar PA"
    }

    //TODO: Habria q recibirla por el id que se clickea e ir a buscar esta info a la api
    const experience: ExperienceModel =
        {
            experienceId: 1,
            name: "hola soy una prueba",
            price: 10,
            address: "SAN PEDRO 47",
            email: "LFERREIRO@ITBA.EDU.AR",
            description: "HOLA SOY LUQUITAS",
            siteUrl: "HOLA.COM",
            city: cityModel,
            category: categoryModel,
            user: userModel,
            observable: true,
            views: 200,
            score: 5,
            reviewsCount: 0}

    const isEditing = true;
    const hasImage = false;
    return (
        <div className="card mx-5 my-3 px-5 pt-4">
            <div className="d-flex justify-content-center align-content-center">
                <h1 className="text-center" style={{wordBreak: "break-all"}}>
                    {experience.name}
                </h1>
            </div>

            <div className="d-flex flex-wrap justify-content-center align-content-center">
                <div className="d-flex flex-column">
                    <div className="p-2" style={{width: "600px"}}>
                        {hasImage &&
                        <img className="container-fluid p-0" style={{height: "fit-content", maxHeight: "550px"}}
                             src={`/experiences/${experience.experienceId}/image.svg`} alt="Imagen"/>}
                        {!hasImage &&
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
                <div style={{flex:"5", minWidth: "350px"}}>
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
                                    {experience.description == '' &&
                                        <div>
                                            {t('ExperienceDetail.noData')}
                                        </div>
                                    }
                                    {experience.description != '' &&
                                    <div>
                                        {experience.description}
                                    </div>}
                                </p>
                            </div>

                            <div> {/* URL */}
                                <h5 className="information-title">
                                    {t('ExperienceDetail.url')}
                                </h5>
                                {experience.siteUrl == '' &&
                                <p className="information-text">
                                    {t('ExperienceDetail.noData')}
                                </p>
                                }
                                {experience.siteUrl != '' &&
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
                {experience.observable &&
                    <div>
                        {/*<a href="<c:url value=" ${param.path}"> <c:param name=" setObs" value="*/}
                        {/*   ${false}"/> </c:url>">*/}
                        <IconButton aria-label="visibilityOn" component="span" style={{fontSize: "xxx-large"}} id="setFalse">
                            <VisibilityIcon/>
                        </IconButton>
                        {/*</a>*/}
                    </div>

                 }
                {!experience.observable &&
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
                            <EditIcon />
                        </IconButton>
                {/*</a>*/}
                {/*<a href="<c:url value="/user/experiences/delete/${param.id}"/>">*/}
                <IconButton aria-label="trash" component="span" style={{fontSize: "xx-large"}}>
                    <DeleteIcon />
                </IconButton>
                {/*</a>*/}
            </div>
            }


        </div>
    );
}