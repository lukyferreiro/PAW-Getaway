import '@testing-library/jest-dom/extend-expect';
import { render } from '@testing-library/react';
import AddPictureModal from '../../../components/AddPictureModal';


jest.mock('../../../services', () => ({
    experienceService: {
        updateExperienceImage: jest.fn(),
    },
    userService: {
        updateUserProfileImage: jest.fn(),
    },
}));

jest.mock('../../../hooks/useAuth', () => ({
    useAuth: () => ({
        user: {
            hasImage: false
        },
        setHasImage: jest.fn(),
    }),
}));

describe('AddPictureModal Component', () => {
    const renderComponent = (isOpen = [true, jest.fn()], experienceId: number | undefined, userId: number | undefined) => {
        // @ts-ignore
        return render(<AddPictureModal isOpen={isOpen} experienceId={experienceId} userId={userId} />);
    };

    test('renders AddPictureModal for Experience', async () => {
        const { getByText, getByLabelText } = renderComponent([true, jest.fn()], 1, undefined);

        expect(getByText('Experience.imgTitle')).toBeInTheDocument();
        expect(getByLabelText('AriaLabel.cancel')).toBeInTheDocument();
        expect(getByLabelText('AriaLabel.confirm')).toBeInTheDocument();
    });

    test('renders AddPictureModal for User', async () => {
        const { getByText, getByLabelText } = renderComponent([true, jest.fn()], undefined, 1);

        expect(getByText('User.imgTitle')).toBeInTheDocument();
        expect(getByLabelText('AriaLabel.cancel')).toBeInTheDocument();
        expect(getByLabelText('AriaLabel.confirm')).toBeInTheDocument();
    });

});
