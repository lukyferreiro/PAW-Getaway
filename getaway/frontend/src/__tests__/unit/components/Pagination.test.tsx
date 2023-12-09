import '@testing-library/jest-dom/extend-expect';
import { fireEvent, render } from '@testing-library/react';
import Pagination from '../../../components/Pagination';


describe('Pagination Component', () => {
    test('renders previous and next buttons when currentPage is greater than 1 and less than maxPage', () => {
        const { getByAltText } = render(<Pagination maxPage={5} currentPage={[3, jest.fn()]} pageToShow={[3, jest.fn()]} />);

        const previousButton = getByAltText('Before page');
        const nextButton = getByAltText('Next page');

        expect(previousButton).toBeInTheDocument();
        expect(nextButton).toBeInTheDocument();
    });

    test('does not render previous button when currentPage is 1', () => {
        const { queryByAltText } = render(<Pagination maxPage={5} currentPage={[1, jest.fn()]} pageToShow={[1, jest.fn()]} />);

        const previousButton = queryByAltText('Before page');

        expect(previousButton).toBeNull();
    });

    test('does not render next button when currentPage is equal to maxPage', () => {
        const { queryByAltText } = render(<Pagination maxPage={5} currentPage={[5, jest.fn()]} pageToShow={[5, jest.fn()]} />);

        const nextButton = queryByAltText('Next page');

        expect(nextButton).toBeNull();
    });

    test('calls changePage function when clicking previous button', () => {
        const changePageMock = jest.fn();
        const { getByAltText } = render(<Pagination maxPage={5} currentPage={[3, changePageMock]} pageToShow={[3, jest.fn()]} />);

        const previousButton = getByAltText('Before page');
        fireEvent.click(previousButton);

        expect(changePageMock).toHaveBeenCalledWith(2);
    });

    test('calls changePage function when clicking next button', () => {
        const changePageMock = jest.fn();
        const { getByAltText } = render(<Pagination maxPage={5} currentPage={[3, changePageMock]} pageToShow={[3, jest.fn()]} />);

        const nextButton = getByAltText('Next page');
        fireEvent.click(nextButton);

        expect(changePageMock).toHaveBeenCalledWith(4);
    });

});
