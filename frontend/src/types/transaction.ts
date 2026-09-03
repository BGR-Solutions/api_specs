export type TransactionType = 'despesa' | 'receita';

export interface Transaction {
  id: number;
  type: TransactionType;
  category: string;
  value: number;
  date: string;
  description: string;
  member: string;
}

export interface TransactionFormValues {
  type: TransactionType;
  category: string;
  value: string;
  date: string;
  description: string;
  member: string;
}

export interface TransactionFormErrors {
  type?: string;
  category?: string;
  value?: string;
  date?: string;
  description?: string;
  member?: string;
}
