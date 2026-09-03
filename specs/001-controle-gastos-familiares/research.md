# Research: Controle de Gastos Familiares

## Decision

The feature will be implemented as a backend REST API focused on personal budgeting, transaction classification, and monthly financial summaries. The domain model will include `MembroFamilia`, `Categoria`, `Despesa`, `Receita`, `OrcamentoMensal`, and `ResumoFinanceiro`.

## Rationale

The feature specification clearly defines a household budgeting workflow with monthly planning, category-based tracking, payment dates, and comparisons between income and expenses. A REST API is the most direct and testable fit because it supports both web clients and future mobile interfaces without coupling the business logic to a UI.

## Alternatives considered

- Mobile-only solution: rejected because the feature requires persistent multi-user household tracking and API-level testability.
- Spreadsheet-style standalone tool: rejected because it would not provide domain validation, REST contract testing, or service-level business rules.
- Full financial integration with banks: rejected for MVP scope; the feature focuses on manual household tracking and planning rather than live bank synchronization.

## Key findings

- Category-driven expenses is a core invariant: all spending must be mapped to a category and a family member.
- Income timing varies across the month; therefore, summaries must support date-range aggregation and monthly balance calculation.
- Budget validation must compare planned versus actual spending per category without violating data integrity.
- Test strategy must include black-box HTTP tests and white-box business logic tests.
