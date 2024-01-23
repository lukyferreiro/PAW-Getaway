import '@testing-library/jest-dom/extend-expect';
import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import UserExperiencesTable from '../../../components/UserExperiencesTable';
import { experienceModelFav, experienceModelNoFav } from '../../Mocks';

const mockedUsedNavigate = jest.fn();

jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockedUsedNavigate,
}));

describe('UserExperiencesTable Component', () => {
    const experiences = [experienceModelFav, experienceModelNoFav];

    test('renders table headers', () => {
        const { getByText } = render(
            <BrowserRouter>
                <UserExperiencesTable
                    experiences={experiences}
                    onEdit={[false, jest.fn()]}
                    setExperienceId={jest.fn()}
                    isOpenImage={[false, jest.fn()]}
                />
            </BrowserRouter>
        );

        expect(getByText(experiences[0].name)).toBeInTheDocument();
        expect(getByText('Category')).toBeInTheDocument();
        expect(getByText('Score')).toBeInTheDocument();
        expect(getByText('Price')).toBeInTheDocument();
        expect(getByText('Views')).toBeInTheDocument();
    });

    test('renders table rows for each experience', () => {
        const { getByText } = render(
            <BrowserRouter>
                <UserExperiencesTable
                    experiences={experiences}
                    onEdit={[false, jest.fn()]}
                    setExperienceId={jest.fn()}
                    isOpenImage={[false, jest.fn()]}
                />
            </BrowserRouter>
        );

        experiences.forEach((experience) => {
            expect(getByText(experience.name)).toBeInTheDocument();
        });
    });

});
