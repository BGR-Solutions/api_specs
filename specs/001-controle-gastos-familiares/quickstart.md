# Quickstart: Controle de Gastos Familiares

## Prerequisites

- Java 21+
- Maven or Gradle
- PostgreSQL instance
- Application configuration with database credentials

## Run the API

1. Configure the database in the application properties.
2. Start the backend module.
3. Use the available endpoints to create members, categories, expenses, incomes, and budgets.

## Validation scenarios

### Scenario 1: register and summarize expenses

- Create a family member named Maria.
- Create category `Mercado` with type `DESPESA`.
- Add an expense of `R$ 420,00` for Maria on the current date.
- Query the monthly summary.
- Expected result: total expenses include this record under `Mercado` and the family member breakdown reflects Maria's spend.

### Scenario 2: compare budget versus actual

- Set monthly budget for `Mercado` at `R$ 600,00`.
- Register expenses totalling `R$ 520,00` in that category.
- Query the budget dashboard.
- Expected result: system shows 86.7% usage and no over-budget alert.

### Scenario 3: check monthly balance

- Register multiple income entries and several expenses in the same period.
- Request the financial summary.
- Expected result: total income minus total expenses equals the period balance.

## Test commands

- `mvn test`
- `mvn -Dtest=controller.*Test test`
- `mvn -Dtest=service.*Test test`
