import { useEffect, useState } from 'react';
import type { CategoryOption } from '../../types/category';
import type { TransactionFormErrors, TransactionFormValues, TransactionType } from '../../types/transaction';

interface TransactionFormProps {
  categories: CategoryOption[];
  onSubmit: (values: TransactionFormValues) => Promise<void> | void;
  isSubmitting?: boolean;
}

const initialValues: TransactionFormValues = {
  type: 'despesa',
  category: '',
  value: '',
  date: '',
  description: '',
  member: '',
};

export function TransactionForm({ categories, onSubmit, isSubmitting = false }: TransactionFormProps) {
  const [values, setValues] = useState<TransactionFormValues>(initialValues);
  const [errors, setErrors] = useState<TransactionFormErrors>({});
  const [submitStatus, setSubmitStatus] = useState<'idle' | 'success' | 'error'>('idle');

  useEffect(() => {
    setValues((current) => ({
      ...current,
      category: '',
    }));
  }, [values.type]);

  const filteredCategories = categories.filter((category) => category.type === values.type);

  const validate = (): TransactionFormErrors => {
    const nextErrors: TransactionFormErrors = {};

    if (!values.type) {
      nextErrors.type = 'Selecione o tipo da transação.';
    }

    if (!values.category) {
      nextErrors.category = 'Selecione uma categoria.';
    }

    if (!values.value || Number(values.value.replace(',', '.')) <= 0) {
      nextErrors.value = 'Informe um valor maior que zero.';
    }

    if (!values.date) {
      nextErrors.date = 'Informe a data.';
    }

    if (!values.member.trim()) {
      nextErrors.member = 'Informe o responsável ou a origem.';
    }

    return nextErrors;
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const nextErrors = validate();
    setErrors(nextErrors);

    if (Object.keys(nextErrors).length > 0) {
      setSubmitStatus('error');
      return;
    }

    try {
      await onSubmit(values);
      setSubmitStatus('success');
      setErrors({});
      setValues(initialValues);
    } catch {
      setSubmitStatus('error');
    }
  };

  const handleChange = (field: keyof TransactionFormValues, value: string) => {
    setValues((current) => ({ ...current, [field]: value }));
    setErrors((current) => ({ ...current, [field]: undefined }));
    if (field === 'type') {
      setSubmitStatus('idle');
    }
  };

  return (
    <form aria-label="Formulário de nova transação" onSubmit={handleSubmit} noValidate>
      <h2>Nova Despesa/Receita</h2>

      <label htmlFor="type">Tipo</label>
      <select
        id="type"
        value={values.type}
        onChange={(event) => handleChange('type', event.target.value as TransactionType)}
      >
        <option value="despesa">Despesa</option>
        <option value="receita">Receita</option>
      </select>
      {errors.type && <span role="alert">{errors.type}</span>}

      <label htmlFor="category">Categoria</label>
      <select
        id="category"
        value={values.category}
        onChange={(event) => handleChange('category', event.target.value)}
      >
        <option value="">Selecione</option>
        {filteredCategories.map((category) => (
          <option key={category.id} value={category.label}>
            {category.label}
          </option>
        ))}
      </select>
      {errors.category && <span role="alert">{errors.category}</span>}

      <label htmlFor="value">Valor</label>
      <input
        id="value"
        type="text"
        inputMode="decimal"
        value={values.value}
        onChange={(event) => handleChange('value', event.target.value)}
        placeholder="0,00"
      />
      {errors.value && <span role="alert">{errors.value}</span>}

      <label htmlFor="date">Data</label>
      <input
        id="date"
        type="date"
        value={values.date}
        onChange={(event) => handleChange('date', event.target.value)}
      />
      {errors.date && <span role="alert">{errors.date}</span>}

      <label htmlFor="member">Responsável / origem</label>
      <input
        id="member"
        type="text"
        value={values.member}
        onChange={(event) => handleChange('member', event.target.value)}
      />
      {errors.member && <span role="alert">{errors.member}</span>}

      <label htmlFor="description">Descrição</label>
      <input
        id="description"
        type="text"
        value={values.description}
        onChange={(event) => handleChange('description', event.target.value)}
      />

      {submitStatus === 'success' && (
        <p role="status">Transação criada com sucesso.</p>
      )}

      {submitStatus === 'error' && (
        <p role="alert">Não foi possível criar a transação.</p>
      )}

      <button type="submit" disabled={isSubmitting}>
        {isSubmitting ? 'Salvando...' : 'Salvar'}
      </button>
    </form>
  );
}
