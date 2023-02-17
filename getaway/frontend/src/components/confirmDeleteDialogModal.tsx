
import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogContentText,
    DialogActions,
    Button,
    Box,
    IconButton
} from "@mui/material"
import create from "zustand"
import {Close} from "@mui/icons-material";

type ConfirmDialogStore = {
    message: string;
    onSubmit?: () => void;
    close: () => void;
}

const useConfirmDialogStore = create<ConfirmDialogStore>((set) => ({
    message: "",
    onSubmit: undefined,
    close: () =>
        set({
            onSubmit: undefined,
        }),
}));

export const confirmDialog = (message: string, onSubmit: () => void) => {
    useConfirmDialogStore.setState({
        message,
        onSubmit,
    })
}

const confirmDeleteDialogModal: React.FC = () => {
    const {message, onSubmit, close} = useConfirmDialogStore();
    return (
        <Dialog open={/*Boolean(onSubmit)*/true} onClose={close} maxWidth={"sm"} fullWidth>
            <DialogTitle>Eliminar</DialogTitle>
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
                        onSubmit();
                    }
                    close();
                }
                    } >
                    Confirmar
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default confirmDeleteDialogModal