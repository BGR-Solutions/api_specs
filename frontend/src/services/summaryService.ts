import type { MonthlySummary } from '../types/summary';

const summaryData: MonthlySummary = {
  stats: {
    totalIncome: 7800,
    totalExpense: 5400,
    balance: 2400,
  },
  categories: [
    { category: 'Moradia', total: 1800 },
    { category: 'Alimentação', total: 1500 },
    { category: 'Transporte', total: 900 },
    { category: 'Lazer', total: 700 },
    { category: 'Saúde', total: 500 },
  ],
};

export async function getMonthlySummary(): Promise<MonthlySummary> {
  return Promise.resolve(summaryData);
}
