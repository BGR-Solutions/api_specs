import type { Transaction, TransactionFormValues } from '../types/transaction';

const transactions: Transaction[] = [];

export async function createTransaction(payload: TransactionFormValues): Promise<Transaction> {
  const numericValue = Number(payload.value.replace(',', '.'));

  if (!payload.type || !payload.category || !payload.date || !payload.member || Number.isNaN(numericValue) || numericValue <= 0) {
    throw new Error('Dados inválidos para criar a transação.');
  }

  const nextTransaction: Transaction = {
    id: transactions.length + 1,
    type: payload.type,
    category: payload.category,
    value: numericValue,
    date: payload.date,
    description: payload.description,
    member: payload.member,
  };

  transactions.push(nextTransaction);

  return Promise.resolve(nextTransaction);
}
