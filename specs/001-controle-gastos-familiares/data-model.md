# Data Model: Controle de Gastos Familiares

## Entities

### MembroFamilia

- id: UUID
- nome: string
- apelido: optional string
- relacao: string
- ativo: boolean
- criadoEm: date-time

Relationships:
- One member can be associated with many expenses.
- One member may be referenced in summaries and filters for household spend analysis.

### Categoria

- id: UUID
- nome: string
- tipo: enum (`DESPESA`, `RECEITA`)
- descricao: optional string
- ativo: boolean

Relationships:
- One category can be linked to many expense records and many income records.

### Despesa

- id: UUID
- categoriaId: UUID
- membroFamiliaId: UUID
- descricao: string
- valor: decimal(19,2)
- dataOcorrencia: date
- observacoes: optional string
- recorrente: boolean
- formaPagamento: optional string
- status: enum (`ATIVA`, `EXCLUIDA`, `AJUSTADA`)
- criadoEm: date-time

Validation rules:
- value must be greater than zero
- member id is required
- category id is required
- date is required and must be valid

### Receita

- id: UUID
- categoriaId: UUID
- origem: string
- valor: decimal(19,2)
- dataRecebimento: date
- recorrente: boolean
- observacoes: optional string
- criadoEm: date-time

Validation rules:
- value must be greater than zero
- origin is required
- date is required

### OrcamentoMensal

- id: UUID
- categoriaId: UUID
- periodo: string (YYYY-MM)
- valorPlanejado: decimal(19,2)
- criadaEm: date-time

Validation rules:
- planned amount must be greater than or equal to zero
- category id is required
- one category budget per month

### ResumoFinanceiro

- periodo: string (YYYY-MM)
- totalReceitas: decimal(19,2)
- totalDespesas: decimal(19,2)
- saldo: decimal(19,2)
- despesasPorCategoria: map<string, decimal>
- receitasPorOrigem: map<string, decimal>
- gastosPorMembro: map<string, decimal>
- percentualUsoOrcamento: map<string, decimal>

## Notes

The model is designed to preserve historical records while allowing summaries to be generated on demand from underlying transactions.
