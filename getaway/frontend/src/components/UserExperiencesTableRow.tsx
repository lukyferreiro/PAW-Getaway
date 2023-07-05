import {ExperienceModel} from "../types";
import React, {Dispatch, SetStateAction, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import StarRating from "./StarRating";
import {IconButton} from "@mui/material";
import {deleteExperience, editExperience, setVisibility} from "../scripts/experienceOperations";
import VisibilityIcon from "@mui/icons-material/Visibility";
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";
import EditIcon from "@mui/icons-material/Edit";
import AddPhotoAlternateIcon from "@mui/icons-material/AddPhotoAlternate";
import {confirmDialogModal} from "./ConfirmDialogModal";
import DeleteIcon from "@mui/icons-material/Delete";
import {useTranslation} from "react-i18next";
import Price from "./Price";

export default function UserExperiencesTableRow(props: {
    experience: ExperienceModel,
    onEdit: [boolean, Dispatch<SetStateAction<boolean>>],
    setExperienceId: React.Dispatch<React.SetStateAction<number>>,
    isOpenImage: [boolean, Dispatch<SetStateAction<boolean>>],
}) {

    const {t} = useTranslation()
    const navigate = useNavigate()
    const {experience, onEdit, setExperienceId, isOpenImage} = props
    const [view, setView] = useState(experience.observable)

    return (
        <>
            <tr key={experience.id}>
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
                        <h5 className="mb-0">
                            {t("User.experiences.reviewsCount", {count: experience.reviewCount})}
                        </h5>
                        <StarRating score={experience.score}/>
                    </div>
                </td>
                <td>
                    <div className="container-fluid d-flex p-2 mb-1 align-items-end">
                        <h5 className="mb-1">
                            <Price price={experience.price}/>
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
                        {view ?
                            <div>
                                <IconButton onClick={() => setVisibility(experience, false, setView, t)}
                                            aria-label={t("AriaLabel.visibility")} title={t("AriaLabel.visibility")}
                                            component="span" style={{fontSize: "x-large"}} id="setFalse">
                                    <VisibilityIcon/>
                                </IconButton>
                            </div>
                            :
                            <div>
                                <IconButton onClick={() => setVisibility(experience, true, setView, t)}
                                            aria-label={t("AriaLabel.visibility")} title={t("AriaLabel.visibility")}
                                            component="span" style={{fontSize: "xx-large"}} id="setTrue">
                                    <VisibilityOffIcon/>
                                </IconButton>
                            </div>
                        }

                        <IconButton onClick={() => editExperience(experience.id, navigate)}
                                    aria-label={t("AriaLabel.editExperience")} title={t("AriaLabel.editExperience")}
                                    component="span" style={{fontSize: "x-large"}}>
                            <EditIcon/>
                        </IconButton>

                        <IconButton
                            onClick={() => {
                                setExperienceId(experience.id);
                                isOpenImage[1](true)
                            }}
                            aria-label={t("AriaLabel.editImage")} title={t("AriaLabel.editImage")}
                            component="span" style={{fontSize: "xx-large"}}>
                            <AddPhotoAlternateIcon/>
                        </IconButton>

                        <IconButton onClick={() =>
                            confirmDialogModal(
                                t('User.experiences.deleteTitle'),
                                t('User.experiences.confirmDelete', {experienceName: experience.name}),
                                () => deleteExperience(experience, onEdit, true, navigate, t))
                        }
                                    aria-label={t("AriaLabel.deleteExperience")} title={t("AriaLabel.deleteExperience")}
                                    component="span" style={{fontSize: "x-large"}}>
                            <DeleteIcon/>
                        </IconButton>
                    </div>
                </td>
            </tr>
        </>
    )

}
