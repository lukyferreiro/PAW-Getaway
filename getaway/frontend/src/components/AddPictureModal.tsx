import React, {Dispatch, SetStateAction} from "react";
import {useForm} from "react-hook-form";
import {experienceService, userService} from "../services";
import {useTranslation} from "react-i18next";
import Modal from "react-modal";
import {showToast} from "../scripts/toast";
import {useAuth} from "../hooks/useAuth";

type FormDataImg = {
    image?: FileList
}

export default function AddPictureModal(
    props: {
        isOpen: [boolean, Dispatch<SetStateAction<boolean>>],
        experienceId?: number,
        userId?: number,
    }
) {
    const {t} = useTranslation()
    const {isOpen, experienceId, userId} = props;
    const {user, setHasImage} = useAuth()

    const {register, reset, handleSubmit, formState: {errors},} = useForm<FormDataImg>({
        criteriaMode: "all",
    })

    const onSubmit = handleSubmit((data: FormDataImg) => {
            if (experienceId) {
                experienceService.updateExperienceImage(experienceId, data.image![0])
                    .then((result) => {
                            if (!result.hasFailed()) {
                                isOpen[1](false)
                                reset()
                                showToast(t('Experience.toast.imageSuccess'), "success")
                            } else {
                                showToast(t('Experience.toast.imageInvalidFormat'), "error")
                            }
                        }
                    )
                    .catch(() => {
                        showToast(t('Experience.toast.imageError'), "error")
                    })
            } else if (userId) {
                userService.updateUserProfileImage(userId ? userId : -1, data.image![0])
                    .then((result) => {
                        if (!result.hasFailed()) {
                            if (!user?.hasImage) {
                                setHasImage()
                            }
                            isOpen[1](false)
                            reset()
                            showToast(t('User.toast.imageSuccess'), "success")
                        } else {
                            showToast(t('User.toast.imageInvalidFormat'), "error")
                        }
                    })
                    .catch(() => {
                        showToast(t('User.toast.imageError'), "error")
                    })
            }
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
                        <h2 className="text-center" style={{fontWeight: "600", marginBottom: "10px"}}>
                            {
                                (experienceId && t('Experience.imgTitle'))
                                ||
                                (userId && t('User.imgTitle'))
                            }
                        </h2>
                    </div>

                    <div className="col-12">
                        <div className="container-fluid">
                            <div className="row">
                                <form encType='multipart/form-data' acceptCharset='utf-8'
                                      id="imageForm" onSubmit={onSubmit}>

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
                                            {t("Image.error.isRequired")}
                                        </p>
                                    )}
                                    {errors.image?.type === 'size' && (
                                        <p className="form-control is-invalid form-error-label">
                                            {t("Image.error.size")}
                                        </p>
                                    )}
                                </form>
                            </div>
                        </div>
                    </div>

                    <div className="col-12 mt-3 d-flex align-items-center justify-content-around">
                        <button className="btn btn-cancel-form px-3 py-2" id="cancelFormButton"
                                onClick={() => {
                                    isOpen[1](false);
                                    reset()
                                }
                                }>
                            {t('Button.cancel')}
                        </button>
                        <button form="imageForm" type="submit" id="ImageButton" className='btn button-primary'>
                            {t('Button.confirm')}
                        </button>
                    </div>

                </div>
            </div>
        </Modal>
    );
};