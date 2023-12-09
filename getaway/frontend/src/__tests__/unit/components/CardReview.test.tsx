import '@testing-library/jest-dom/extend-expect';
import { fireEvent, render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import CardReview from '../../../components/CardReview';
import { reviewModel1 } from '../../Mocks';

const mockedUsedNavigate = jest.fn();

jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockedUsedNavigate,
}));

describe('CardReview Component', () => {
    test('renders content', () => {
        const component = render(
            <CardReview reviewModel={reviewModel1} isEditing={true} />,
            { wrapper: BrowserRouter }
        );
        expect(component.container).toHaveTextContent(reviewModel1.user.name);
        expect(component.container).toHaveTextContent(reviewModel1.date);
    });

    test('calls editReview function when edit button is clicked', () => {
        const component = render(
            <CardReview reviewModel={reviewModel1} isEditing={true} />,
            { wrapper: BrowserRouter }
        );

        fireEvent.click(component.getByLabelText('Edit review'));
        expect(mockedUsedNavigate).toHaveBeenCalledWith(
            {
                pathname: `/experiences/${reviewModel1.experience.id}/reviewForm`,
                search: `?id=${reviewModel1.id}`,
            },
            { replace: true }
        );
    });

    test('renders link to review form when isEditing is true', () => {
        const component = render(
            <CardReview reviewModel={reviewModel1} isEditing={true} />,
            { wrapper: BrowserRouter }
        );

        expect(component.getByText(reviewModel1.experience.name)).toBeInTheDocument();
    });

    test('does not render link to review form when isEditing is false', () => {
        const component = render(
            <CardReview reviewModel={reviewModel1} isEditing={false} />,
            { wrapper: BrowserRouter }
        );

        expect(component.queryByText(reviewModel1.experience.name)).toBeNull();
    });
});
