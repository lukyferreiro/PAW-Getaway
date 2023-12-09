import '@testing-library/jest-dom/extend-expect';
import { render } from '@testing-library/react';
import Price from '../../../components/Price';

describe('Price Component', () => {
    test('renders message for null price', () => {
        const { getByText } = render(<Price price={undefined} />);
        const nullPriceMessage = getByText('Price not listed');

        expect(nullPriceMessage).toBeInTheDocument();
    });

    test('renders "Free" message for price of 0', () => {
        const { getByText } = render(<Price price={0} />);
        const freeMessage = getByText('Free');

        expect(freeMessage).toBeInTheDocument();
    });

    test('renders price message for non-zero price', () => {
        const { getByText } = render(<Price price={50} />);
        const priceMessage = getByText('$ 50');

        expect(priceMessage).toBeInTheDocument();
    });

});
