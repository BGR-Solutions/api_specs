import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { TransactionForm } from './TransactionForm';

describe('TransactionForm', () => {
  const categories = [
    { id: 'moradia', label: 'Moradia', type: 'despesa' as const },
    { id: 'salario', label: 'Salário', type: 'receita' as const },
  ];

  it('validates required fields before submit', async () => {
    const user = userEvent.setup();
    const onSubmit = jest.fn();

    render(<TransactionForm categories={categories} onSubmit={onSubmit} />);

    await user.click(screen.getByRole('button', { name: /salvar/i }));

    expect(screen.getByText('Selecione uma categoria.')).toBeInTheDocument();
    expect(screen.getByText('Informe um valor maior que zero.')).toBeInTheDocument();
    expect(screen.getByText('Informe a data.')).toBeInTheDocument();
    expect(screen.getByText('Informe o responsável ou a origem.')).toBeInTheDocument();
    expect(onSubmit).not.toHaveBeenCalled();
  });

  it('submits a valid transaction and shows success message', async () => {
    const user = userEvent.setup();
    const onSubmit = jest.fn().mockResolvedValue(undefined);

    render(<TransactionForm categories={categories} onSubmit={onSubmit} />);

    await user.selectOptions(screen.getByLabelText('Tipo'), 'despesa');
    await user.selectOptions(screen.getByLabelText('Categoria'), 'Moradia');
    await user.type(screen.getByLabelText('Valor'), '1500');
    await user.type(screen.getByLabelText('Data'), '2026-09-03');
    await user.type(screen.getByLabelText('Responsável / origem'), 'César');

    await user.click(screen.getByRole('button', { name: /salvar/i }));

    expect(onSubmit).toHaveBeenCalledWith(
      expect.objectContaining({
        type: 'despesa',
        category: 'Moradia',
        value: '1500',
        date: '2026-09-03',
        member: 'César',
      }),
    );
    expect(await screen.findByRole('status')).toHaveTextContent('Transação criada com sucesso.');
  });
});
