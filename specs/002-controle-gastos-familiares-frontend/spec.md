# Feature Specification: Controle de Gastos Familiares - Frontend

**Feature Branch**: `002-controle-gastos-familiares-frontend`

**Created**: 2026-09-03

**Status**: Draft

**Input**: User description: "Precisamos de uma tela de 'Dashboard' que mostre o resumo dos gastos e ganhos do mês, e uma tela de 'Nova Despesa/Receita' com um formulário simples."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Visualizar o resumo financeiro do mês (Priority: P1)

A família precisa acompanhar rapidamente o saldo do mês, o total de receitas e despesas, além dos principais gastos por categoria, para entender se o orçamento está em equilíbrio.

**Why this priority**: O dashboard é o ponto central da experiência. Ele entrega valor imediato ao usuário porque permite uma leitura rápida do cenário financeiro sem precisar navegar em registros detalhados.

**Independent Test**: Pode ser validado ao abrir o Dashboard com dados carregados e confirmar que a soma de receitas, despesas e saldo apresentados correspondem ao mês selecionado.

**Acceptance Scenarios**:

1. **Given** que existem receitas e despesas cadastradas no mês atual, **When** o usuário acessa a página de Dashboard, **Then** o sistema deve exibir total de receitas, total de despesas e saldo consolidado em um resumo visual claro.
2. **Given** que há despesas em categorias diferentes, **When** o usuário observa o painel do Dashboard, **Then** o sistema deve mostrar a distribuição por categoria com destaque para os maiores valores.

---

### User Story 2 - Registrar uma nova despesa ou receita (Priority: P1)

O usuário precisa registrar rapidamente uma transação financeira, preenchendo campos mínimos e escolhendo se é despesa ou receita, para manter o acompanhamento em tempo real.

**Why this priority**: O cadastro de transações é a ação operacional que alimenta o dashboard. Sem esse fluxo, o aplicativo perde sua utilidade prática.

**Independent Test**: Pode ser validado preenchendo o formulário de nova transação e confirmando que a operação é enviada ao serviço e o resultado do status é exibido ao usuário.

**Acceptance Scenarios**:

1. **Given** que o usuário está na tela de cadastro de nova transação, **When** informa tipo, categoria, valor, data e responsável ou origem, **Then** o sistema deve habilitar o envio do formulário e validar os campos obrigatórios.
2. **Given** que o formulário foi preenchido corretamente, **When** o usuário envia a transação, **Then** o sistema deve chamar o serviço apropriado e mostrar confirmação visual de sucesso.

---

### User Story 3 - Validar erros de entrada no formulário (Priority: P2)

O usuário deve receber feedback claro quando faltar algum dado obrigatório ou quando o valor informado for inválido, evitando registros inconsistentes no sistema.

**Why this priority**: A validação reduz erros de dados e melhora a confiabilidade das informações financeiras exibidas no dashboard.

**Independent Test**: Pode ser validada ao submeter formulário incompleto ou com valor inválido e verificar mensagens de erro visíveis e consistentes.

**Acceptance Scenarios**:

1. **Given** que o usuário tenta salvar uma transação sem valor, **When** envia o formulário, **Then** o sistema deve exibir uma mensagem de erro e impedir o envio.
2. **Given** que o usuário insere uma data ou valor inválido, **When** clica em salvar, **Then** o sistema deve destacar o campo e mostrar uma orientação correta de correção.

---

### User Story 4 - Navegar entre Dashboard e formulário de transação (Priority: P3)

O usuário deve conseguir alternar facilmente entre a visão geral financeira e a criação de novas transações sem quebrar o fluxo de uso.

**Why this priority**: A navegação é essencial para a usabilidade, mas não representa a funcionalidade central isolada do MVP.

**Independent Test**: Pode ser testada ao clicar nos elementos de navegação da aplicação e verificar que a rota ou componente correspondente é renderizado.

**Acceptance Scenarios**:

1. **Given** que o usuário está no Dashboard, **When** clica em "Nova Despesa/Receita", **Then** o sistema deve abrir a tela de formulário de transação.
2. **Given** que o usuário finalizou uma transação com sucesso, **When** retorna ao Dashboard, **Then** o sistema deve refletir o novo estado após a atualização de dados.

### Edge Cases

- O que acontece quando a API falha ao carregar os dados do Dashboard?
- Como a interface lida com um formulário de transação preenchido parcialmente e depois cancelado?
- O que ocorre quando a categoria ou membro da família não existe mais no backend?
- Como a UI se comporta quando o valor financeiro está em formato decimal com vírgula ou ponto?
- O que acontece quando a lista de transações for vazia?

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: The frontend application MUST provide a Dashboard screen that summarizes total income, total expense, and monthly balance for the selected period.
- **FR-002**: The frontend application MUST display the aggregate expense distribution by category on the Dashboard, prioritizing the most relevant values.
- **FR-003**: The frontend application MUST provide a form to create a transaction with type, category, value, date, and source or responsible member based on the selected transaction kind.
- **FR-004**: The frontend application MUST separate visual rendering from HTTP logic by keeping API calls in dedicated service modules, not in components.
- **FR-005**: The frontend application MUST validate required fields before submission and show clear error messages for invalid or missing values.
- **FR-006**: The frontend application MUST support the creation of both expense and income transactions using a single form with conditional fields driven by transaction type.
- **FR-007**: The frontend application MUST show a loading state while the Dashboard or form is fetching or submitting data.
- **FR-008**: The frontend application MUST provide feedback on success or failure after submitting a new transaction, including a clear user-visible confirmation or error message.
- **FR-009**: The frontend application MUST use React functional components and TypeScript interfaces or types for all props, state, and models.
- **FR-010**: The frontend application MUST avoid direct DOM manipulation and use state management patterns appropriate to the project, such as local component state or context for shared data.
- **FR-011**: The frontend application MUST expose accessible form labels, keyboard-friendly interactions, and aria attributes where appropriate to improve usability.
- **FR-012**: The frontend application MUST include unit tests for key UI behaviors using Jest and React Testing Library following the AAA pattern: Arrange, Act, Assert.
- **FR-013**: The frontend application MUST include tests for black-box behavior of the user experience, such as rendering summaries, validation feedback, and form submission flow.
- **FR-014**: The frontend application MUST include tests for white-box logic of service integration boundary and form state transitions, validating data mapping and API call behavior.

### Key Entities *(include if feature involves data)*

- **Transacao**: representa uma movimentação financeira do mês, podendo ser despesa ou receita. Possui tipo, categoria, valor, data, origem/responsável e status de envio.
- **CategoriaFinanceira**: representa a classificação de uma transação e pode ser usada tanto para gastos quanto para entradas.
- **ResumoMensal**: representa os valores consolidados do período atual, incluindo receitas, despesas, saldo e distribuição por categoria.
- **FormularioTransacao**: representa o estado do formulário de criação de despesa ou receita, com campos, erros e estado de carregamento.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: The user can open the Dashboard and understand the monthly financial status in under 5 seconds after data loads.
- **SC-002**: The user can complete the creation of a valid expense or income entry in under 2 minutes without support.
- **SC-003**: At least 90% of the form validation error scenarios are correctly displayed and prevent invalid submissions in test coverage.
- **SC-004**: Dashboard and form interactions are covered by unit tests for rendering and service integration logic, with all critical user flows passing.
- **SC-005**: The interface provides consistent feedback for success, loading, and validation states across the main financial flows.

## Assumptions

- The frontend will interact with an existing REST API that provides members, categories, expenses, incomes, and summary data.
- The initial version focuses on the monthly dashboard and simple transaction creation without advanced filtering or multi-page editing.
- The interface will be desktop-first, with responsive behavior as a secondary concern for the MVP.
- The user will be working in a browser environment with standard HTML form controls and keyboard navigation support.
- The project will use TypeScript and React with a modular service layer for HTTP requests.
