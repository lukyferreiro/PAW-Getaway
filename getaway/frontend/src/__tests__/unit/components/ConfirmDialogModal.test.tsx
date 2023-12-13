import '@testing-library/jest-dom/extend-expect';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import ConfirmDialogModal, { confirmDialogModal } from '../../../path-to-your-component/ConfirmDialogModal';

describe('ConfirmDialogModal Component', () => {
    test('renders ConfirmDialogModal component with correct elements', async () => {
        render(<ConfirmDialogModal />);

        const dialogTitle = screen.getByText(''); // Update with your default title
        const dialogContent = screen.getByText('');
        const cancelButton = screen.getByText('Cancel');
        const confirmButton = screen.getByText('Confirm');

        expect(dialogTitle).toBeInTheDocument();
        expect(dialogContent).toBeInTheDocument();
        expect(cancelButton).toBeInTheDocument();
        expect(confirmButton).toBeInTheDocument();
    });

    test('opens ConfirmDialogModal using confirmDialogModal function', async () => {
        confirmDialogModal('Test Title', 'Test Message', jest.fn());
        render(<ConfirmDialogModal />);

        const dialogTitle = screen.getByText('Test Title');
        const dialogContent = screen.getByText('Test Message');
        const cancelButton = screen.getByText('Cancel');
        const confirmButton = screen.getByText('Confirm');

        expect(dialogTitle).toBeInTheDocument();
        expect(dialogContent).toBeInTheDocument();
        expect(cancelButton).toBeInTheDocument();
        expect(confirmButton).toBeInTheDocument();
    });

    test('calls onSubmit when Confirm button is clicked', async () => {
        const onSubmitMock = jest.fn();
        confirmDialogModal('Test Title', 'Test Message', onSubmitMock);
        render(<ConfirmDialogModal />);

        const confirmButton = screen.getByText('Confirm');
        fireEvent.click(confirmButton);

        await waitFor(() => {
            expect(onSubmitMock).toHaveBeenCalled();
        });
    });

    test('closes ConfirmDialogModal when Cancel button is clicked', async () => {
        const onSubmitMock = jest.fn();
        confirmDialogModal('Test Title', 'Test Message', onSubmitMock);
        render(<ConfirmDialogModal />);

        const cancelButton = screen.getByText('Cancel');
        fireEvent.click(cancelButton);

        await waitFor(() => {
            expect(screen.queryByText('Test Title')).not.toBeInTheDocument();
            expect(screen.queryByText('Test Message')).not.toBeInTheDocument();
        });
    });
});
