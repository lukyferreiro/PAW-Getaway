import '@testing-library/jest-dom/extend-expect';
import { render } from '@testing-library/react';
import Footer from '../path-to-your-component/Footer';

describe('Footer Component', () => {
    test('renders copyright information', () => {
        const { getByText } = render(<Footer />);

        const currentDate = new Date().getFullYear();
        const expectedText = `Getaway Copyright © ${currentDate} - All rights reserved`;
        expect(getByText(expectedText)).toBeInTheDocument();
    });
});
