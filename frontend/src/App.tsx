import { useEffect, useState } from 'react';
import { Dashboard } from './components/dashboard/Dashboard';
import { TransactionForm } from './components/forms/TransactionForm';
import { getCategories } from './services/categoryService';
import { getMonthlySummary } from './services/summaryService';
import { createTransaction } from './services/transactionService';
import type { CategoryOption } from './types/category';
import type { MonthlySummary } from './types/summary';
import type { TransactionFormValues } from './types/transaction';
import './App.css';

const initialSummary: MonthlySummary = {
  stats: {
    totalIncome: 0,
    totalExpense: 0,
    balance: 0,
  },
  categories: [],
};

function App() {
  const [summary, setSummary] = useState<MonthlySummary>(initialSummary);
  const [categories, setCategories] = useState<CategoryOption[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    const loadData = async () => {
      const [summaryData, categoryData] = await Promise.all([
        getMonthlySummary(),
        getCategories(),
      ]);

      setSummary(summaryData);
      setCategories(categoryData);
      setIsLoading(false);
    };

    void loadData();
  }, []);

  const handleSubmit = async (values: TransactionFormValues) => {
    setIsSubmitting(true);
    try {
      await createTransaction(values);
      const nextSummary = await getMonthlySummary();
      setSummary(nextSummary);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <main className="app-shell">
      <Dashboard summary={summary} isLoading={isLoading} />
      <TransactionForm categories={categories} onSubmit={handleSubmit} isSubmitting={isSubmitting} />
    </main>
  );
}

export default App;
