import '@testing-library/jest-dom/extend-expect';
import { render, screen } from '@testing-library/react';
import Carousel from '../../../path-to-your-component/Carousel';
import {experienceModelCategory, experienceModelFav, experienceModelMaxPrice, experienceModelNoFav} from "../../Mocks";

const mockExperiences = [
    experienceModelMaxPrice, experienceModelFav,
    experienceModelCategory, experienceModelNoFav
];

describe('Carousel Component', () => {
    test('renders Carousel component with correct elements', async () => {
        render(<Carousel title="Test Carousel" experiences={mockExperiences} show={3} />);

        const carouselTitle = screen.getByText('Test Carousel');
        const leftArrow = screen.getByLabelText('Previous experience');
        const rightArrow = screen.getByLabelText('Next experience');

        expect(carouselTitle).toBeInTheDocument();
        expect(leftArrow).toBeInTheDocument();
        expect(rightArrow).toBeInTheDocument();
    });

    test('renders experiences in the carousel', async () => {
        render(<Carousel title="Test Carousel" experiences={mockExperiences} show={3} />);

        const experience1 = screen.getByText('Experiencia comun');
        const experience2 = screen.getByText('Experiencia fav');
        const experience3 = screen.getByText('Experiencia de categoria 2')
        const experience4 = screen.getByText('Experiencia de mayor precio');


        expect(experience1).toBeInTheDocument();
        expect(experience2).toBeInTheDocument();
        expect(experience3).toBeInTheDocument();
        expect(experience4).toBeInTheDocument();
    });
});
