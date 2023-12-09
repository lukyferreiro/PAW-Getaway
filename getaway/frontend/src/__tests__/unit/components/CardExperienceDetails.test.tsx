import '@testing-library/jest-dom/extend-expect';
import { fireEvent, render } from '@testing-library/react';
import CardExperienceDetails from '../../../components/CardExperienceDetails';
import { experienceModelNoFav } from '../../Mocks';
import {setFavExperience, setVisibility} from "../../../scripts/experienceOperations";

// Mocking react-router-dom useNavigate and useSearchParams hooks
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => jest.fn(),
    useSearchParams: () => [new URLSearchParams(), jest.fn()],
}));

// Mocking useAuth hook
jest.mock('../../../hooks/useAuth', () => ({
    useAuth: () => ({
        getUser: jest.fn(() => ({ /* Mocked user object */ })),
    }),
}));

// Mocking scripts/experienceOperations functions
jest.mock('../../../scripts/experienceOperations', () => ({
    setFavExperience: jest.fn(),
    setVisibility: jest.fn(),
    editExperience: jest.fn(),
    deleteExperience: jest.fn(),
}));

// Mocking scripts/toast function
jest.mock('../../../scripts/toast', () => ({
    showToast: jest.fn(),
}));

describe('CardExperienceDetails Component', () => {

    test('renders experience details correctly', () => {
        const { getByText } = render(
            <CardExperienceDetails experience={experienceModelNoFav} isEditing={false} nameProp={['', jest.fn()]} categoryProp={['', jest.fn()]} />
        );

        // @ts-ignore
        expect(getByText(experienceModelNoFav.description)).toBeInTheDocument();

    });

    test('calls setFavExperience when clicking favorite button', () => {
        const { getByLabelText } = render(
            <CardExperienceDetails experience={experienceModelNoFav} isEditing={false} nameProp={['', jest.fn()]} categoryProp={['', jest.fn()]} />
        );

        const favoriteButton = getByLabelText('Save in favourites');
        fireEvent.click(favoriteButton);

        expect(setFavExperience).toHaveBeenCalledWith(experienceModelNoFav, true, expect.any(Function), expect.any(Function));
    });

    test('calls setVisibility when clicking visibility button', () => {
        const { getByLabelText } = render(
            <CardExperienceDetails experience={experienceModelNoFav} isEditing={true} nameProp={['', jest.fn()]} categoryProp={['', jest.fn()]} />
        );

        const visibilityButton = getByLabelText('Visibility');
        fireEvent.click(visibilityButton);

        expect(setVisibility).toHaveBeenCalledWith(experienceModelNoFav, false, expect.any(Function), expect.any(Function));
    });

});
