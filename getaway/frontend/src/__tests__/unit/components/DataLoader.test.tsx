import '@testing-library/jest-dom/extend-expect';
import { render, screen } from '@testing-library/react';
import DataLoader from '../../../components/DataLoader';

describe('DataLoader Component', () => {
    test('renders DataLoader component with spinner when loading', async() => {
        render(<DataLoader isLoading={true} children={<p></p>}/>);
        const spinner = screen.getByTestId('spinner');
        expect(spinner).toBeInTheDocument();
    });

    test('renders DataLoader component without spinner when not loading', () => {
        render(<DataLoader isLoading={false} children={<p></p>}/>);
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
