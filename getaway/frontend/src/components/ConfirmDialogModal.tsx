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
import create from "zustand";
import {Close} from "@mui/icons-material";
import React from "react";

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

export const confirmDialogModal = (title:string, message: string, onSubmit: () => void) => {
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
                <Button color="primary" variant="contained" onClick={() => {
                    if (onSubmit) {
                        onSubmit()
                    }
                    close();
                }
                }>
                    Confirmar
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default ConfirmDialogModal