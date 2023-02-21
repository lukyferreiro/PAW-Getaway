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
import AddPhotoAlternateIcon from '@mui/icons-material/AddPhotoAlternate';
import React, {useEffect, useState} from "react";
import {CategoryModel, ExperienceModel} from "../types";
import StarRating from "./StarRating";
import {experienceService} from "../services";
import ConfirmDialogModal, { confirmDialogModal } from "../components/ConfirmDialogModal";
import { Favorite, FavoriteBorder} from "@mui/icons-material";
import {useAuth} from "../hooks/useAuth";
import {serviceHandler} from "../scripts/serviceHandler";
import AddPictureModal, {addPictureModal} from "../components/AddPictureModal";

export default function CardExperienceDetails(props: { experience: ExperienceModel; isEditing: boolean; }) {

    const {experience, isEditing} = props
    const {t} = useTranslation();
    const navigate = useNavigate()
    const {user} = useAuth()

    const [experienceImg, setExperienceImg] = useState<string | undefined>(undefined)
    const [fav, setFav] = useState(experience.fav)

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

    function setFavExperience(fav: boolean) {
        experienceService.setExperienceFav(experience.id, fav).then()
            .catch(() => {
            });
        setFav(fav)
    }

    useEffect(() => {
        if(experience.hasImage) {
            serviceHandler(
                experienceService.getExperienceImage(experience?.id),
                navigate, (experienceImg) => {
                    setExperienceImg(experienceImg.size > 0 ? URL.createObjectURL(experienceImg) : undefined)
                },
                () => {
                },
                () => {
                }
            );
        }
    }, [])

    return (
        <>
            <div className="d-flex flex-wrap justify-content-center align-content-center">
                <div className="d-flex flex-column">
                    <div className="p-2" style={{width: "600px"}}>
                        <img className="container-fluid p-0" alt={`Imagen ${experience.category.name}`}
                             src={experienceImg ? experienceImg : `./images/${experience.category.name}.svg`}
                             style={{height: "fit-content", maxHeight: experienceImg ? "550px" : "450px"}}/>

                        {!experience.hasImage &&
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

            <div className="btn-fav">
                {user ?
                    <div>
                        {fav ?
                            <IconButton onClick={() => setFavExperience(false)}>
                                <Favorite className="fa-heart heart-color"/>
                            </IconButton>
                            :
                            <IconButton onClick={() => setFavExperience(true)}>
                                <FavoriteBorder className="fa-heart"/>
                            </IconButton>
                        }
                    </div>
                    :
                    <div>
                        <IconButton onClick={() => navigate("/login")}>
                            <FavoriteBorder className="fa-heart"/>
                        </IconButton>
                    </div>
                }
            </div>

            {isEditing &&
                <div className="btn-group my-2 d-flex justify-content-center align-content-center" role="group">
                    {experience.observable ?
                        <div>
                            <IconButton onClick={() => setVisibility(experience.id, false)} aria-label="visibilityOn"
                                        component="span" style={{fontSize: "xxx-large"}} id="setFalse">
                                <VisibilityIcon/>
                            </IconButton>
                        </div>
                        :
                        <div>
                            <IconButton onClick={() => setVisibility(experience.id, true)} aria-label="visibilityOff"
                                        component="span" style={{fontSize: "xx-large"}} id="setTrue">
                                <VisibilityOffIcon/>
                            </IconButton>
                        </div>
                    }

                    <IconButton onClick={() => editExperience(experience.id)} aria-label="edit" component="span"
                                style={{fontSize: "xx-large"}}>
                        <EditIcon/>
                    </IconButton>

                    {/*<IconButton*/}
                    {/*    onClick={() => (addPictureModal(experience.id, () => {}))}*/}
                    {/*    aria-label="picture"*/}
                    {/*    component="span"*/}
                    {/*    style={{fontSize: "xx-large"}}>*/}
                    {/*    <AddPhotoAlternateIcon/>*/}
                    {/*</IconButton>*/}

                    <IconButton
                        onClick={() => confirmDialogModal(t('User.experiences.deleteTitle'),t('User.experiences.confirmDelete',{experienceName: experience.name}), () => deleteExperience(experience.id))}
                        aria-label="trash"
                        component="span"
                        style={{fontSize: "xx-large"}}>
                        <DeleteIcon/>
                    </IconButton>

                    <ConfirmDialogModal/>
                </div>


            }
        </>
    );

}