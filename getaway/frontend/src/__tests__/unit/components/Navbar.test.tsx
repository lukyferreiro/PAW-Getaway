import '@testing-library/jest-dom/extend-expect';
import {fireEvent, render, screen, waitFor} from '@testing-library/react';
import Navbar from '../../../components/Navbar';
import { act } from 'react-dom/test-utils';
import {BrowserRouter} from "react-router-dom";

const mockedUsedNavigate = jest.fn();

jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockedUsedNavigate,
}));

jest.mock('../../../hooks/useAuth', () => ({
    ...jest.requireActual('../../../hooks/useAuth'),
    useAuth: () => ({
        signOut: jest.fn(),
        isLogged: jest.fn(() => true),
        isProvider: jest.fn(() => true),
        isVerified: jest.fn(() => true),
    }),
}));

describe('Navbar Component', () => {
    const renderComponent = () => {
        return render(
            <BrowserRouter>
                <Navbar nameProp={['Test', jest.fn()]} categoryProp={['Aventura', jest.fn()]} />
            </BrowserRouter>
        );
    };

    test('renders Navbar component common texts', () => {
        const { getByText, getByLabelText } = renderComponent();

        expect(getByText('Getaway')).toBeInTheDocument();
        expect(getByLabelText('Search')).toBeInTheDocument();
        expect(getByText('Create experience')).toBeInTheDocument();
    });

    test('renders user profile dropdown when logged in', async() => {
        const { getByText } = renderComponent();

        expect(getByText('My profile')).toBeInTheDocument();
        expect(getByText('My experiences')).toBeInTheDocument();
        expect(getByText('My favourites')).toBeInTheDocument();
        expect(getByText('My reviews')).toBeInTheDocument();
        expect(getByText('Log out')).toBeInTheDocument();
    });

    test('handles form submission and navigation', async () => {
        const { getByLabelText } = renderComponent();

        await act(async () => {
            fireEvent.change(getByLabelText('Search'), { target: { value: 'Test' } });
            fireEvent.submit(getByLabelText('Search'));
        });

        await waitFor(() => {
            expect(window.location.pathname).toBe('/');
        });
    });

    test('resets form on close button click', async () => {
        const { getByLabelText } = renderComponent();

        await act(async () => {
            fireEvent.click(getByLabelText('Delete search'));
        });

        expect(getByLabelText('Search')).toHaveValue('');
    });

    test('resets form on create experience button click', async () => {
        const { getByLabelText, getByText } = renderComponent();

        await act(async () => {
            fireEvent.click(getByText('Create experience'));
        });

        expect(getByLabelText('Search')).toHaveValue('');
    });
});
