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
        expect(component.container).toHaveTextContent(reviewModel1.title);
        expect(component.container).toHaveTextContent(reviewModel1.description);
        expect(component.container).toHaveTextContent(reviewModel1.date);
    });

    test('does not render link to review form when isEditing is false', () => {
        const component = render(
            <CardReview reviewModel={reviewModel1} isEditing={false} />,
            { wrapper: BrowserRouter }
        );

        expect(component.queryByText("Experiencia comun")).toBeNull();
    });
});
