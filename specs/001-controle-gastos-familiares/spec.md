# Feature Specification: Controle de Gastos Familiares

**Feature Branch**: `001-controle-gastos-familiares`

**Created**: 2026-09-03

**Status**: Draft

**Input**: User description: "O sistema deve categorizar gastos (como contas fixas, mercado, lojas e restaurantes) e permitir o planejamento financeiro cruzando essas saídas com receitas (ganhos) que entram em dias variados do mês."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Registrar e categorizar despesas mensais (Priority: P1)

Uma família deseja registrar cada gasto do mês em uma categoria clara, como contas fixas, mercado, lojas e restaurantes, para manter o controle financeiro com precisão.

**Why this priority**: A organização das despesas é a base da visão financeira. Sem esse registro, o planejamento financeiro e a comparação com as receitas ficam inconsistentes e pouco confiáveis.

**Independent Test**: Pode ser testado pela criação de despesas com valores, datas e categorias, e pela confirmação de que o sistema soma corretamente os gastos por período e categoria.

**Acceptance Scenarios**:

1. **Given** que a família possui uma categoria de mercado cadastrada, **When** registra uma despesa de R$ 420,00 no dia 14 do mês, **Then** o sistema deve persistir a despesa com valor, data, categoria e descrição e disponibilizá-la no resumo financeiro.
2. **Given** que existem despesas cadastradas em categorias diferentes, **When** o usuário consulta o total do mês, **Then** o sistema deve apresentar a soma agregada por categoria e o total geral.

---

### User Story 2 - Registrar receitas em datas variadas e comparar com saídas (Priority: P1)

Uma pessoa quer registrar ganhos que entram em dias diferentes do mês, como salário, freelance, rendimento ou bônus, para comparar com as despesas totais e entender o saldo disponível.

**Why this priority**: O controle financeiro útil exige comparação contínua entre entradas e saídas, especialmente quando os recebimentos não acontecem no mesmo dia da maioria das despesas.

**Independent Test**: Pode ser testado pela criação de receitas em datas distintas, seguida pela consulta do saldo e do fluxo financeiro por faixa de datas.

**Acceptance Scenarios**:

1. **Given** que uma receita de salário foi recebida em 05/09 e outra receita de freelance em 20/09, **When** o usuário consulta o resumo do mês, **Then** o sistema deve somar as receitas corretas e comparar com as despesas no mesmo intervalo.
2. **Given** que a família já registrou despesas e receitas, **When** solicita o saldo do período, **Then** o sistema deve calcular a diferença entre entradas e saídas e exibir o resultado em valor positivo ou negativo.

---

### User Story 3 - Planejar o orçamento e identificar excesso por categoria (Priority: P2)

O usuário deseja definir limites mensais ou estimativas para cada categoria e receber alertas quando as despesas se aproximam ou ultrapassam esses limites, permitindo o ajuste do planejamento financeiro.

**Why this priority**: O planejamento torna o sistema útil para tomada de decisão, não apenas para registro histórico. Esta funcionalidade agrega previsibilidade ao controle financeiro familiar.

**Independent Test**: Pode ser validada pelo cadastro de orçamento por categoria e pela verificação de que o sistema aponta o percentual de uso e a diferenciação entre planejado e realizado.

**Acceptance Scenarios**:

1. **Given** que um orçamento mensal para mercado foi definido em R$ 600,00, **When** o usuário registra despesas de mercado totalizando R$ 520,00, **Then** o sistema deve mostrar que 86,7% do orçamento foi usado e sinalizar a categoria como em andamento.
2. **Given** que a despesa total de restaurantes ultrapassou o limite definido para a categoria, **When** o usuário consulta o painel financeiro, **Then** o sistema deve indicar alerta de excesso e destacar o valor excedente.

---

### User Story 4 - Acessar relatórios e histórico por período (Priority: P3)

O usuário deseja consultar o comportamento financeiro em diferentes meses ou períodos, identificando tendências e ajustando o orçamento familiar com base em dados históricos.

**Why this priority**: Relatórios históricos melhoram o planejamento e apoiam decisões futuras, mas não são essenciais para a funcionalidade mínima do sistema.

**Independent Test**: Pode ser testado pela geração de um resumo mensal e pela confirmação de que o sistema filtra corretamente por intervalo de datas e categorias.

**Acceptance Scenarios**:

1. **Given** que existem registros em janeiro e fevereiro, **When** o usuário filtra por mês, **Then** o sistema deve exibir somente os dados do período selecionado.
2. **Given** que o histórico contém despesas e receitas, **When** o usuário solicita o relatório do trimestre, **Then** o sistema deve compor uma visão do fluxo financeiro, das categorias mais impactantes e do saldo consolidado.

### Edge Cases

- O que acontece quando uma despesa ou receita tem valor zero ou negativo?
- Como o sistema trata a criação de categorias duplicadas com nomes iguais, mas diferentes tipos?
- O que ocorre quando uma receita ou despesa é registrada fora do mês atual ou em uma data futura?
- Como o sistema se comporta quando o usuário tenta excluir uma categoria que já está vinculada a transações?
- O que acontece quando um usuário informa um valor com casas decimais ou formatação monetária inválida?
- Como o sistema trata meses com ausência de receita ou despesas de uma categoria específica?

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: O sistema MUST registrar cada despesa com identificador único, descrição, valor, data, categoria, membro da família responsável pelo gasto e observações opcionais.
- **FR-002**: O sistema MUST registrar cada receita com identificador único, origem, valor, data de recebimento, recorrência opcional e observações opcionais.
- **FR-003**: O sistema MUST permitir a criação, atualização, listagem e remoção de categorias de gasto e de receita, incluindo valores como contas fixas, mercado, lojas, restaurantes, salário, freelance e outros rendimentos.
- **FR-004**: O sistema MUST validar que o valor monetário de despesas e receitas seja positivo e formatado corretamente, rejeitando valores inválidos ou inconsistentes.
- **FR-005**: O sistema MUST permitir a associação de cada despesa ou receita a um período de referência, com data de ocorrência para comparação entre meses e planejamento financeiro.
- **FR-006**: O sistema MUST calcular o total de despesas por categoria, por período e por mês, permitindo a análise de concentração de gastos.
- **FR-007**: O sistema MUST calcular o total de receitas por origem e por período, incluindo receitas recebidas em dias diferentes ao longo do mês.
- **FR-008**: O sistema MUST calcular o saldo financeiro do período como a diferença entre receitas e despesas, exibindo resultado positivo, negativo ou neutro.
- **FR-009**: O sistema MUST permitir o cadastro de limites ou orçamentos mensais por categoria para comparação entre planejamento e execução financeira.
- **FR-010**: O sistema MUST disponibilizar um resumo consolidado por mês com receitas, despesas, saldo, categorias mais relevantes, gastos por membro da família e percentual de utilização do orçamento.
- **FR-011**: O sistema MUST permitir filtros por categoria, intervalo de datas, tipo de transação, membro da família responsável e status de recorrência para a consulta do histórico financeiro.
- **FR-012**: O sistema MUST impedir a exclusão de categorias que já possuem transações vinculadas, preservando a integridade dos dados e a consistência do histórico.
- **FR-013**: O sistema MUST permitir o cadastro, atualização, listagem e remoção de membros da família, mantendo a associação correta entre cada despesa e o responsável pelo gasto.
- **FR-014**: O sistema MUST fornecer testes de caixa preta cobrindo contratos HTTP, payloads de entrada, validações de campos obrigatórios, códigos de resposta esperados e cenários de uso real do usuário final.
- **FR-015**: O sistema MUST fornecer testes de caixa branca cobrindo regras de negócio internas, como cálculo do saldo, agrupamento por categoria, filtros por período, validação de limites, lógica de recorrência e análise por membro da família.
- **FR-016**: O sistema MUST manter registro de transações em ordem cronológica e preservar a origem, a categoria e o responsável de cada valor para análise histórica e planejamento.
- **FR-017**: O sistema MUST permitir que receitas e despesas sejam visualizadas separadamente e em conjunto, facilitando a comparação entre o fluxo de entrada e saída do orçamento familiar.

### Key Entities *(include if feature involves data)*

- **MembroFamilia**: representa cada pessoa envolvida no planejamento financeiro doméstico. Possui identificador único, nome, apelido opcional, relação com a família, status ativo e informações de perfil para filtros e análise por responsável.
- **Categoria**: representa a classificação da transação, como mercado, contas fixas, lojas, restaurantes, salário, investimento, freelance ou outras fontes de renda. Possui identificador, nome, tipo, descrição opcional, status ativo e referência de agrupamento.
- **Despesa**: representa uma saída financeira. Possui identificador único, categoria, descrição, valor, data de ocorrência, observações, indicador de recorrência, forma de pagamento, membro da família responsável pelo gasto e status de registro.
- **Receita**: representa uma entrada financeira. Possui identificador único, fonte, valor, data de recebimento, recorrência opcional, categoria de tipo de ganho e observações opcionais.
- **OrçamentoMensal**: representa o limite planejado para cada categoria dentro de um mês. Possui identificador, categoria, valor planejado, mês ou período, e indicadores de utilização e alerta.
- **ResumoFinanceiro**: representa a consolidação do período selecionado. Possui receitas totais, despesas totais, saldo, despesas por categoria, receitas por origem, gastos por membro da família e indicadores de sobrecarga de orçamento.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: Usuários conseguem registrar e consultar despesas e receitas em menos de 2 minutos para um fluxo típico de cadastro.
- **SC-002**: O sistema deve calcular corretamente totais mensais de receitas, despesas e saldo para 100% dos cenários de teste de regra de negócio contemplados.
- **SC-003**: O sistema deve permitir que pelo menos 90% dos usuários concluam o acompanhamento financeiro do mês sem suporte adicional, seguindo o fluxo principal de criação, categorização e consulta.
- **SC-004**: O sistema deve identificar alertas de orçamento excedido em pelo menos 95% dos casos em que o previsto for ultrapassado por categoria.
- **SC-005**: O relatório consolidado deve refletir o período selecionado com precisão, sem duplicação ou perda de registros em transações cadastradas.

## Assumptions

- O sistema será usado por uma família ou por um responsável pelo planejamento financeiro doméstico, com foco em consumo e orçamento pessoal.
- O MVP considera a gestão mensal e a comparação entre período atual e histórico recente, sem exigir múltiplas contas bancárias ou integrações financeiras automáticas.
- As categorias principais serão definidas por tipo de despesa e renda, mas o sistema deve permitir extensões futuras por outros agrupamentos.
- O usuário trabalha com valores em moeda local e exige consistência na precisão monetária, sem arredondamentos inconsistentes.
- O armazenamento de dados deve preservar integridade do histórico mesmo quando a categoria for alterada ou a transação for revisada.
