import '@testing-library/jest-dom/extend-expect';
import { render, screen } from '@testing-library/react';
import DataLoader from '../../../path-to-your-component/DataLoader';

describe('DataLoader Component', () => {
    test('renders DataLoader component with spinner when loading', () => {
        render(<DataLoader isLoading={true} />);
        const spinner = screen.getByTestId('spinner');
        expect(spinner).toBeInTheDocument();
    });

    test('renders DataLoader component without spinner when not loading', () => {
        render(<DataLoader isLoading={false} />);
        const spinner = screen.queryByTestId('spinner');
        expect(spinner).not.toBeInTheDocument();
    });

    test('renders DataLoader component with children when not loading', () => {
        render(<DataLoader isLoading={false}>Test Children</DataLoader>);
        const children = screen.getByText('Test Children');
        expect(children).toBeInTheDocument();
    });

    test('does not render children when loading', () => {
        render(<DataLoader isLoading={true}>Test Children</DataLoader>);
        const children = screen.queryByText('Test Children');
        expect(children).not.toBeInTheDocument();
    });
});
