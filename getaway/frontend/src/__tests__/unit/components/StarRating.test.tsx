import '@testing-library/jest-dom/extend-expect';
import { render } from '@testing-library/react';
import StarRating from '../../../components/StarRating';

describe('StarRating Component', () => {
    test('renders five stars for a score of 5', () => {
        const { container } = render(<StarRating score={5} />);
        const stars = container.querySelectorAll('.star');

        expect(stars).toHaveLength(5);
    });

    test('renders all empty stars for a score of 0', () => {
        const { container } = render(<StarRating score={0} />);
        const emptyStars = container.querySelectorAll('.star:not(.filled)');

        expect(emptyStars).toHaveLength(5);
    });
});
