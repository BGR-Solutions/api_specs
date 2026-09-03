import type { CategoryOption } from '../types/category';

export async function getCategories(): Promise<CategoryOption[]> {
  return Promise.resolve([
    { id: 'moradia', label: 'Moradia', type: 'despesa' },
    { id: 'alimentacao', label: 'Alimentação', type: 'despesa' },
    { id: 'transporte', label: 'Transporte', type: 'despesa' },
    { id: 'saude', label: 'Saúde', type: 'despesa' },
    { id: 'lazer', label: 'Lazer', type: 'despesa' },
    { id: 'salario', label: 'Salário', type: 'receita' },
    { id: 'freelance', label: 'Freelance', type: 'receita' },
    { id: 'investimento', label: 'Investimento', type: 'receita' },
  ]);
}
