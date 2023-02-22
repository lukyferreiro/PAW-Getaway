import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogContentText,
    DialogActions,
    Box,
    IconButton
} from "@mui/material";
import {create} from 'zustand';
import {Close} from "@mui/icons-material";
import React from "react";
import {t} from "i18next";


type ConfirmDialogStore = {
    title: string;
    message: string;
    onSubmit?: () => void;
    close: () => void;
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
    });
});

export const confirmDialogModal = (title: string, message: string, onSubmit: () => void) => {
    UseConfirmDialogStore.setState({
        title,
        message,
        onSubmit,
    })
}

const ConfirmDialogModal: React.FC = () => {
    const {title, message, onSubmit, close} = UseConfirmDialogStore();
    return (
        <Dialog open={Boolean(onSubmit)} onClose={close} maxWidth="sm" fullWidth>
            <DialogTitle>{title}</DialogTitle>
            <Box position="absolute" top={0} right={0}>
                <IconButton onClick={close}>
                    <Close/>
                </IconButton>
            </Box>
            <DialogContent>
                <DialogContentText>{message}</DialogContentText>
            </DialogContent>
            <DialogActions>
                <button type="button" className="btn btn-error" onClick={() => {
                    if (onSubmit) {
                        onSubmit()
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

export default ConfirmDialogModal