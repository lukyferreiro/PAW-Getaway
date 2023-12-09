import '@testing-library/jest-dom/extend-expect';
import { fireEvent, render } from '@testing-library/react';
import OrderDropdown from '../../../components/OrderDropdown';
import { orderByModel } from '../../Mocks';

const orders_translation = [
    "Ascendant score",
    "Descendant score",
    "A-Z",
    "Z-A",
    "Lower price",
    "Higher price",
]

describe('OrderDropdown Component', () => {

    test('renders button with current order title', () => {
        const { getByText } = render(
            <OrderDropdown orders={orderByModel} order={['date', jest.fn()]} currentPage={[1, jest.fn()]} />
        );

        const orderButton = getByText('Order by:');
        expect(orderButton).toBeInTheDocument();
    });

    test('renders dropdown with order options', () => {
        const { getByText } = render(
            <OrderDropdown orders={orderByModel} order={['date', jest.fn()]} currentPage={[1, jest.fn()]} />
        );

        const orderOptions = orderByModel.orders.map((order, idx) => getByText(`${orders_translation[idx]}`));
        expect(orderOptions).toHaveLength(6);
    });

});
