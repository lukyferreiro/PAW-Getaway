import {useTranslation} from "react-i18next";
import "../common/i18n/index";
import {useNavigate, useSearchParams} from "react-router-dom";
import {IconButton} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import AddPhotoAlternateIcon from '@mui/icons-material/AddPhotoAlternate';
import React, {Dispatch, SetStateAction, useState} from "react";
import {ExperienceModel} from "../types";
import StarRating from "./StarRating";
import ConfirmDialogModal, {confirmDialogModal} from "../components/ConfirmDialogModal";
import {Favorite, FavoriteBorder} from "@mui/icons-material";
import {useAuth} from "../hooks/useAuth";
import AddPictureModal from "../components/AddPictureModal";
import {deleteExperience, editExperience, setFavExperience, setVisibility} from "../scripts/experienceOperations";
import Price from "./Price";
// @ts-ignore
import VisibilityIcon from "@mui/icons-material/Visibility";
// @ts-ignore
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";

export default function CardExperienceDetails(props: { experience: ExperienceModel, isEditing: boolean, nameProp: [string | undefined, Dispatch<SetStateAction<string | undefined>>], categoryProp: [string | undefined, Dispatch<SetStateAction<string | undefined>>] }
) {

    const {experience, isEditing, nameProp, categoryProp} = props
    const {t} = useTranslation()
    const navigate = useNavigate()
    const {user} = useAuth()

    const [searchParams, setSearchParams] = useSearchParams();

    const isOpenImage = useState(false)
    const [fav, setFav] = useState(experience.fav)
    const [view, setView] = useState(experience.observable)

    function clearNavBar() {
        searchParams.delete("category")
        searchParams.delete("name")
        setSearchParams(searchParams)
        categoryProp[1]("")
        nameProp[1]("")
    }

    return (
        <>
            <div className="d-flex flex-wrap justify-content-center align-content-center">
                <div className="d-flex flex-column">
                    <div className="p-2" style={{width: "600px"}}>
                        <img className="container-fluid p-0" alt={`Imagen ${experience.category.name}`}
                             src={experience.hasImage ? experience.imageUrl : `./images/${experience.category.name}.svg`}
                             style={{height: "fit-content", maxHeight: experience.hasImage ? "550px" : "450px"}}/>

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
                                    <Price price={experience.price}/>
                                </div>
                            </div>

                            <div> {/* Descripcion */}
                                <h5 className="information-title">
                                    {t('ExperienceDetail.description')}
                                </h5>
                                <div className="information-text" id="experienceDescription">
                                    {experience.description === undefined ?
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
                                {experience.siteUrl === undefined ?
                                    <p className="information-text">
                                        {t('ExperienceDetail.noData')}
                                    </p>
                                    :
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
                                <h6 className="information-title mb-0">
                                    {t('ExperienceDetail.review')}: {experience.reviewCount}
                                </h6>
                                <StarRating score={experience.score}/>
                            </div>
                        </div>
                        <div className="col-2 p-0"></div>
                    </div>
                </div>
            </div>

            {!view &&
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
                            <IconButton onClick={() => setFavExperience(experience, false, setFav, t)}>
                                <Favorite className="fa-heart heart-color"/>
                            </IconButton>
                            :
                            <IconButton onClick={() => setFavExperience(experience, true, setFav, t)}>
                                <FavoriteBorder className="fa-heart"/>
                            </IconButton>
                        }
                    </div>
                    :
                    <div>
                        <IconButton onClick={() => {
                            clearNavBar();
                            navigate("/login")
                        }}>
                            <FavoriteBorder className="fa-heart"/>
                        </IconButton>
                    </div>
                }
            </div>

            {isEditing &&
                <div className="btn-group my-2 d-flex justify-content-center align-content-center" role="group">
                    {view ?
                        <div>
                            <IconButton onClick={() => setVisibility(experience, false, setView, t)} aria-label="Visibility"
                                        component="span" style={{fontSize: "xxx-large"}} id="setFalse">
                                <VisibilityIcon/>
                            </IconButton>
                        </div>
                        :
                        <div>
                            <IconButton onClick={() => setVisibility(experience, true, setView, t)} aria-label="Visibility"
                                        component="span" style={{fontSize: "xx-large"}} id="setTrue">
                                <VisibilityOffIcon/>
                            </IconButton>
                        </div>
                    }

                    <IconButton onClick={() => editExperience(experience.id, navigate)} aria-label="edit" component="span"
                                style={{fontSize: "xx-large"}}>
                        <EditIcon/>
                    </IconButton>

                    <IconButton
                        onClick={() => {
                            isOpenImage[1](true)
                        }}
                        aria-label="picture"
                        component="span"
                        style={{fontSize: "xx-large"}}>
                        <AddPhotoAlternateIcon/>
                    </IconButton>

                    <IconButton
                        onClick={() => {
                            confirmDialogModal(
                                t('User.experiences.deleteTitle'), t('User.experiences.confirmDelete',
                                    {experienceName: experience.name}),
                                () => deleteExperience(experience, undefined, false, navigate, t)
                            )
                        }}
                        aria-label="trash"
                        component="span"
                        style={{fontSize: "xx-large"}}>
                        <DeleteIcon/>
                    </IconButton>

                    <AddPictureModal isOpen={isOpenImage} experienceId={experience.id}/>
                    <ConfirmDialogModal/>
                </div>


            }
        </>
    );

}