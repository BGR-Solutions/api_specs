import type { MonthlySummary } from '../../types/summary';
import { formatCurrency } from '../../utils/formatters';

interface DashboardProps {
  summary: MonthlySummary;
  isLoading?: boolean;
}

export function Dashboard({ summary, isLoading = false }: DashboardProps) {
  if (isLoading) {
    return <div aria-live="polite">Carregando resumo financeiro...</div>;
  }

  return (
    <section aria-label="Dashboard financeiro">
      <h2>Dashboard</h2>

      <div>
        <div aria-label="Receitas totais">
          <span>Receitas</span>
          <strong>{formatCurrency(summary.stats.totalIncome)}</strong>
        </div>
        <div aria-label="Despesas totais">
          <span>Despesas</span>
          <strong>{formatCurrency(summary.stats.totalExpense)}</strong>
        </div>
        <div aria-label="Saldo do mês">
          <span>Saldo</span>
          <strong>{formatCurrency(summary.stats.balance)}</strong>
        </div>
      </div>

      <div>
        <h3>Resumo por categoria</h3>
        <ul>
          {summary.categories.map((item) => (
            <li key={item.category}>
              <span>{item.category}</span>
              <strong>{formatCurrency(item.total)}</strong>
            </li>
          ))}
        </ul>
      </div>
    </section>
  );
}
