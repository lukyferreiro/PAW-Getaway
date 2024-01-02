import '@testing-library/jest-dom/extend-expect';
import { fireEvent, render } from '@testing-library/react';
import CardExperienceDetails from '../../../components/CardExperienceDetails';
import { experienceModelNoFav } from '../../Mocks';
import {setFavExperience, setVisibility} from "../../../scripts/experienceOperations";
import {BrowserRouter} from "react-router-dom";

jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => jest.fn(),
}));

jest.mock('../../../hooks/useAuth', () => ({
    useAuth: () => ({
        getUser: jest.fn(() => ({ /* Mocked user object */ })),
    }),
}));

jest.mock('../../../scripts/experienceOperations', () => ({
    setFavExperience: jest.fn(),
    setVisibility: jest.fn(),
    editExperience: jest.fn(),
    deleteExperience: jest.fn(),
}));

jest.mock('../../../scripts/toast', () => ({
    showToast: jest.fn(),
}));

describe('CardExperienceDetails Component', () => {

    test('renders experience details correctly', () => {
        const { getByText } = render(
            <BrowserRouter>
                <CardExperienceDetails experience={experienceModelNoFav}
                                       nameProp={['', jest.fn()]} categoryProp={['', jest.fn()]} />
            </BrowserRouter>
        );

        // @ts-ignore
        expect(getByText(experienceModelNoFav.description)).toBeInTheDocument();

    });

    test('calls setFavExperience when clicking favorite button', () => {
        const { getByLabelText } = render(
            <BrowserRouter>
                <CardExperienceDetails experience={experienceModelNoFav}
                                       nameProp={['', jest.fn()]} categoryProp={['', jest.fn()]} />
            </BrowserRouter>
        );

        const favoriteButton = getByLabelText('Save in favourites');
        fireEvent.click(favoriteButton);

        expect(setFavExperience).toHaveBeenCalledWith(experienceModelNoFav, true, expect.any(Function), expect.any(Function));
    });

});
