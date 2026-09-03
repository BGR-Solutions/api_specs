export interface SummaryStats {
  totalIncome: number;
  totalExpense: number;
  balance: number;
}

export interface CategorySummary {
  category: string;
  total: number;
}

export interface MonthlySummary {
  stats: SummaryStats;
  categories: CategorySummary[];
}
