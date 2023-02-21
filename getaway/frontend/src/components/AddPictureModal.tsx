import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogContentText,
    DialogActions,
    Button,
    Box,
    IconButton
} from "@mui/material";
import {Close} from "@mui/icons-material";
import React, {Dispatch, SetStateAction, useState} from "react";
import {t} from "i18next";
import {useForm} from "react-hook-form";
import {experienceService, userService} from "../services";
import {useTranslation} from "react-i18next";
import Modal from "react-modal";

type FormDataImg = {
    image?: FileList
}

//TODO: make generic for user AND experiences
export default function AddPictureModal(props: { isOpen: [boolean, Dispatch<SetStateAction<boolean>>], experienceId: number }) {
    const {t} = useTranslation()
    const {isOpen, experienceId} = props;

    const {register, reset, handleSubmit, formState: {errors},} = useForm<FormDataImg>({
        criteriaMode: "all",
    });

    const onSubmit = handleSubmit((data: FormDataImg) => {
        experienceService.updateExperienceImage(experienceId, data.image![0])
            .then((result) => {
                    if (result.getError().getStatus() === 204) {
                        isOpen[1](false);
                        reset()
                    }
                }
            )
            .catch(() => {
            });
        }
    );

    return (
        <Modal style={{overlay: {zIndex: 100}}}
               className="modal-pop-up"
               isOpen={isOpen[0]}
               contentLabel="PopUpImage"
               onRequestClose={() => {
                   isOpen[1](false);
                   reset()
               }}
        >

            <div className="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">
                <div className="row w-100 h-100 py-5 px-3 m-0 align-items-center justify-content-center">
                    <div className="col-12">
                        <h1 className="text-center title">
                            {t('User.imgTitle')}
                        </h1>
                    </div>

                    <div className="col-12">
                        <div className="container-fluid">
                            <div className="row">
                                <form encType='multipart/form-data' acceptCharset='utf-8'
                                      id="imageForm" onSubmit={onSubmit} >

                                    <input type='file'
                                           accept='image/png, image/jpeg, image/jpg' className="form-control"
                                           {...register("image", {
                                               validate: {
                                                   required: (image) =>
                                                       image !== undefined && image[0] !== undefined,

                                                   size: (image) =>
                                                       image && image[0] && image[0].size / (1024 * 1024) < 5,
                                               },
                                           })}
                                    />
                                    {errors.image?.type === 'required' && (
                                        <p className="form-control is-invalid form-error-label">
                                            {t("User.error.image.isRequired")}
                                        </p>
                                    )}
                                    {errors.image?.type === 'size' && (
                                        <p className="form-control is-invalid form-error-label">
                                            {t("User.error.image.size")}
                                        </p>
                                    )}
                                </form>
                            </div>
                        </div>
                    </div>


                    <div className="col-12 mt-3 d-flex align-items-center justify-content-center">
                        <button form="imageForm" type="submit" id="ImageButton" className='btn button-primary'>
                            {t('Navbar.resetPasswordButton')}
                        </button>
                    </div>

                </div>
            </div>
        </Modal>
    );
};