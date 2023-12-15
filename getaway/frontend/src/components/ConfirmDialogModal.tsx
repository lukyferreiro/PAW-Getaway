import {Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions,} from "@mui/material";
import {create} from 'zustand';
import React from "react";
import {useTranslation} from "react-i18next";


type ConfirmDialogStore = {
    title: string,
    message: string,
    onSubmit?: () => void,
    close: () => void,
}

const UseConfirmDialogStore = create<ConfirmDialogStore>((set) => {
    return ({
        title: "",
        message: "",
        onSubmit: undefined,
        close: () => {
            return set({
                onSubmit: undefined,
            });
        },
    })
})

export const confirmDialogModal = (title: string, message: string, onSubmit: () => void) => {
    UseConfirmDialogStore.setState({
        title,
        message,
        onSubmit,
    })
}

const ConfirmDialogModal: React.FC = () => {

    const {title, message, onSubmit, close} = UseConfirmDialogStore()
    const {t} = useTranslation()

    return (
        <Dialog open={Boolean(onSubmit)} onClose={close} maxWidth="sm" fullWidth>
            <DialogTitle>{title}</DialogTitle>
            <DialogContent>
                <DialogContentText>{message}</DialogContentText>
            </DialogContent>
            <DialogActions className="d-flex align-items-center justify-content-around">
                <button className="btn btn-cancel-form px-3 py-2" id="cancelFormButton"
                        aria-label={t("AriaLabel.cancel")} title={t("AriaLabel.cancel")}
                        onClick={close}>
                    {t('Button.cancel')}
                </button>
                <button type="button" id="confirmDelete" className='btn button-primary'
                        aria-label={t("AriaLabel.confirm")} title={t("AriaLabel.confirm")}
                        onClick={() => {
                            if (onSubmit) {
                                onSubmit()
                            }
                            close();
                        }
                        }>
                    {t('Button.confirm')}
                </button>
            </DialogActions>
        </Dialog>
    );
};

export default ConfirmDialogModal