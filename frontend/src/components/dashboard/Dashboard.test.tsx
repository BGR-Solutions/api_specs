import { render, screen } from '@testing-library/react';
import { Dashboard } from './Dashboard';

describe('Dashboard', () => {
  it('renders total income, expense and balance', () => {
    render(
      <Dashboard
        summary={{
          stats: { totalIncome: 7800, totalExpense: 5400, balance: 2400 },
          categories: [{ category: 'Moradia', total: 1800 }],
        }}
      />,
    );

    expect(screen.getByText('Receitas')).toBeInTheDocument();
    expect(screen.getByText('R$ 7.800,00')).toBeInTheDocument();
    expect(screen.getByText('Despesas')).toBeInTheDocument();
    expect(screen.getByText('R$ 5.400,00')).toBeInTheDocument();
    expect(screen.getByText('Saldo')).toBeInTheDocument();
    expect(screen.getByText('R$ 2.400,00')).toBeInTheDocument();
  });

  it('renders category distribution', () => {
    render(
      <Dashboard
        summary={{
          stats: { totalIncome: 1000, totalExpense: 800, balance: 200 },
          categories: [
            { category: 'Moradia', total: 400 },
            { category: 'Alimentação', total: 200 },
          ],
        }}
      />,
    );

    expect(screen.getByText('Moradia')).toBeInTheDocument();
    expect(screen.getByText('R$ 400,00')).toBeInTheDocument();
    expect(screen.getByText('Alimentação')).toBeInTheDocument();
  });

  it('shows loading state', () => {
    render(
      <Dashboard
        summary={{
          stats: { totalIncome: 0, totalExpense: 0, balance: 0 },
          categories: [],
        }}
        isLoading
      />,
    );

    expect(screen.getByText('Carregando resumo financeiro...')).toBeInTheDocument();
  });
});
