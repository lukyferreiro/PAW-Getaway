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
import {create} from 'zustand';
import {Close} from "@mui/icons-material";
import React from "react";
import {t} from "i18next";
import {useForm} from "react-hook-form";
import {experienceService, userService} from "../services";


type AddPictureStore = {
    experienceId: number;
    onSubmitModal?: () => void;
    close: () => void;
}

type FormDataImg = {
    image?: FileList
}

const UseAddPictureStore = create<AddPictureStore>((set) => {
    return ({
        experienceId: -1,
        onSubmitModal: undefined,
        close: () => {
            return set({
                onSubmitModal: undefined,
            });
        },
    });
});

export const addPictureModal = (experienceId: number, onSubmitModal: () => void) => {
    UseAddPictureStore.setState({
        experienceId,
        onSubmitModal,
    })
}

const AddPictureModal: React.FC = () => {
    const {experienceId, onSubmitModal, close} = UseAddPictureStore();
    const {register, handleSubmit, reset, formState: { errors },} =
        useForm<FormDataImg>({ criteriaMode: 'all' })

    const onSubmit = handleSubmit((data: FormDataImg) => {
        experienceService.updateExperienceImage(experienceId, data.image![0])
    })

    return (
        <Dialog open={Boolean(onSubmit)} onClose={close} maxWidth="sm" fullWidth>
            {/*<DialogTitle>{title}</DialogTitle>*/}
            <Box position="absolute" top={0} right={0}>
                <IconButton onClick={close}>
                    <Close/>
                </IconButton>
            </Box>
            <DialogContent>
                <div className="m-1 justify-self-center align-self-center">
                    <form encType='multipart/form-data' acceptCharset='utf-8'
                          id="imageForm" onSubmit={onSubmit} >
                        <label className="form-label d-flex justify-content-between">
                            {t("User.imgTitle")}
                        </label>

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
            </DialogContent>
            <DialogActions>
                <button type="button" className="btn btn-error"   onClick={() => {
                    if (onSubmitModal) {
                    }
                    close();
                }
                }>
                    {t("Button.confirm")}
                </button>
            </DialogActions>
        </Dialog>
    );
};

export default AddPictureModal