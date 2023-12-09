import '@testing-library/jest-dom/extend-expect';
import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import UserExperiencesTableRow from '../../../components/UserExperiencesTableRow';
import {experienceModelFav} from '../../Mocks';

const mockedUsedNavigate = jest.fn();

jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockedUsedNavigate,
}));

describe('UserExperiencesTableRow Component', () => {

    test('renders category name', () => {
        const component = render(
            <UserExperiencesTableRow
                experience={experienceModelFav}
                onEdit={[false, jest.fn()]}
                setExperienceId={jest.fn()}
                isOpenImage={[false, jest.fn()]}
            />,
            { wrapper: BrowserRouter }
        );

        expect(component.container).toHaveTextContent(experienceModelFav.name);
    });

});
