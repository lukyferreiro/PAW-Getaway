import '@testing-library/jest-dom/extend-expect';
import { fireEvent, render, waitFor } from '@testing-library/react';
import Navbar from '../../../path-to-your-component/Navbar';
import { act } from 'react-dom/test-utils';

jest.mock('react-i18next', () => ({
    useTranslation: () => ({ t: jest.fn() }),
}));

jest.mock('../../../hooks/useAuth', () => ({
    useAuth: () => ({
        signOut: jest.fn(),
        isLogged: jest.fn(),
        isProvider: jest.fn(),
        isVerified: jest.fn(),
    }),
}));

jest.mock('../../../services', () => ({
    serviceHandler: jest.fn(),
    experienceService: {
        getCategories: jest.fn(),
    },
}));

jest.mock('../../../hooks/useQuery', () => ({
    useQuery: jest.fn(),
    getQueryOrDefault: jest.fn(),
}));

describe('Navbar Component', () => {
    const renderComponent = (nameProp = ['', jest.fn()], categoryProp = ['', jest.fn()]) => {
        // @ts-ignore
        return render(<Navbar nameProp={nameProp} categoryProp={categoryProp} />);
    };

    test('renders Navbar component', () => {
        const { getByText } = renderComponent();

        expect(getByText('Getaway')).toBeInTheDocument();
    });

    test('renders search form', () => {
        const { getByLabelText } = renderComponent();

        expect(getByLabelText('Search')).toBeInTheDocument();
    });

    test('renders create experience button', () => {
        const { getByText } = renderComponent();

        expect(getByText('Create experience')).toBeInTheDocument();
    });

    test('renders login button when not logged in', () => {
        const { getByText } = renderComponent();

        expect(getByText('Sign in')).toBeInTheDocument();
    });

    test('renders user profile dropdown when logged in', () => {
        jest.mock('../../../hooks/useAuth', () => ({
            useAuth: () => ({
                signOut: jest.fn(),
                isLogged: jest.fn(() => true),
                isProvider: jest.fn(() => true),
                isVerified: jest.fn(() => true),
            }),
        }));

        const { getByLabelText, getByText } = renderComponent();

        expect(getByLabelText('My account')).toBeInTheDocument();
        expect(getByText('My profile')).toBeInTheDocument();
        expect(getByText('My experiences')).toBeInTheDocument();
        expect(getByText('y favourites')).toBeInTheDocument();
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
            expect(window.location.pathname).toBe('/experiences');
            expect(window.location.search).toBe('?category=&name=Test&order=OrderByAZ&page=1');
        });
    });

    test('resets form on close button click', async () => {
        const { getByLabelText, getByText } = renderComponent();

        await act(async () => {
            fireEvent.change(getByLabelText('Search'), { target: { value: 'Test' } });
            fireEvent.click(getByLabelText('Delete search'));
        });

        expect(getByText('Navbar.search')).toHaveValue('');
    });

    test('resets form on create experience button click', async () => {
        const { getByText } = renderComponent();

        await act(async () => {
            fireEvent.click(getByText('Create experience'));
        });

        // Ensure the form is reset
        expect(getByText('Search')).toHaveValue('');
    });
});
