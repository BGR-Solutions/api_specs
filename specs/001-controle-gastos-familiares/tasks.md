# Tasks: Controle de Gastos Familiares

**Input**: Design documents from `/specs/001-controle-gastos-familiares/`

**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Initialize the API project structure and shared configuration.

- [ ] T001 Create backend project structure per implementation plan in backend/src/main/java/com/example/familyfinance/ and backend/src/test/java/com/example/familyfinance/
- [ ] T002 Initialize Spring Boot project with Java 21, Spring Web, Validation, Data JPA, PostgreSQL, and test dependencies in backend/pom.xml
- [ ] T003 [P] Configure application.yml, database settings, and base test configuration in backend/src/main/resources/application.yml and backend/src/test/resources/

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Establish the shared domain, persistence, and API fundamentals required before story work starts.

**⚠️ CRITICAL**: No user story implementation can begin until this phase is complete.

- [ ] T004 Create base domain package structure for entity, repository, service, dto, mapper, controller, exception, and config in backend/src/main/java/com/example/familyfinance/
- [ ] T005 [P] Implement shared error handling and validation conventions in backend/src/main/java/com/example/familyfinance/exception/
- [ ] T006 [P] Create shared monetary validation utilities and date-range helpers in backend/src/main/java/com/example/familyfinance/application/
- [ ] T007 Create JPA entity base model and repository contracts for MembroFamilia, Categoria, Despesa, Receita, and OrcamentoMensal in backend/src/main/java/com/example/familyfinance/domain/
- [ ] T008 Configure database schema expectations and initial migration baseline in backend/src/main/resources/db/

**Checkpoint**: Foundation ready - user story implementation can now begin in parallel.

---

## Phase 3: User Story 1 - Registrar e categorizar despesas mensais (Priority: P1) 🎯 MVP

**Goal**: Allow users to register expenses with category, date, amount, and responsible family member and view the monthly summary.

**Independent Test**: A user can create at least one family member, one category, one expense, and retrieve the monthly total without any other story being complete.

### Tests for User Story 1

- [ ] T009 [P] [US1] Create black-box controller test for POST /expenses in backend/src/test/java/com/example/familyfinance/controller/DespesaControllerTest.java
- [ ] T010 [P] [US1] Create white-box service test for expense validation and monthly aggregation in backend/src/test/java/com/example/familyfinance/service/DespesaServiceTest.java

### Implementation for User Story 1

- [ ] T011 [P] [US1] Create MembroFamilia entity and repository in backend/src/main/java/com/example/familyfinance/domain/entity/MembroFamilia.java and backend/src/main/java/com/example/familyfinance/domain/repository/MembroFamiliaRepository.java
- [ ] T012 [P] [US1] Create Categoria entity and repository in backend/src/main/java/com/example/familyfinance/domain/entity/Categoria.java and backend/src/main/java/com/example/familyfinance/domain/repository/CategoriaRepository.java
- [ ] T013 [P] [US1] Create Despesa entity and repository in backend/src/main/java/com/example/familyfinance/domain/entity/Despesa.java and backend/src/main/java/com/example/familyfinance/domain/repository/DespesaRepository.java
- [ ] T014 [US1] Create DTOs and mappers for member, category, and expense create/list payloads in backend/src/main/java/com/example/familyfinance/application/dto/ and backend/src/main/java/com/example/familyfinance/application/mapper/
- [ ] T015 [US1] Implement DespesaService with validation, category association, member association, and monthly aggregation in backend/src/main/java/com/example/familyfinance/application/service/DespesaService.java
- [ ] T016 [US1] Implement DespesaController REST endpoints for creation and listing in backend/src/main/java/com/example/familyfinance/api/controller/DespesaController.java
- [ ] T017 [US1] Add validation for positive value, required category, required member, and invalid dates in backend/src/main/java/com/example/familyfinance/application/service/DespesaService.java
- [ ] T018 [US1] Add expense filtering by member, category, and period in backend/src/main/java/com/example/familyfinance/application/service/DespesaService.java

**Checkpoint**: At this point, User Story 1 should be fully functional and testable independently.

---

## Phase 4: User Story 2 - Registrar receitas em datas variadas e comparar com saídas (Priority: P1)

**Goal**: Allow creation of income entries on different days of the month and compare them with total spending.

**Independent Test**: A user can create income records across multiple dates and calculate a monthly net balance.

### Tests for User Story 2

- [ ] T019 [P] [US2] Create black-box controller test for POST /incomes and summary flows in backend/src/test/java/com/example/familyfinance/controller/ReceitaControllerTest.java
- [ ] T020 [P] [US2] Create white-box service test for income aggregation and net balance calculation in backend/src/test/java/com/example/familyfinance/service/ReceitaServiceTest.java

### Implementation for User Story 2

- [ ] T021 [P] [US2] Create Receita entity and repository in backend/src/main/java/com/example/familyfinance/domain/entity/Receita.java and backend/src/main/java/com/example/familyfinance/domain/repository/ReceitaRepository.java
- [ ] T022 [US2] Create DTOs and mapper for income inputs and response models in backend/src/main/java/com/example/familyfinance/application/dto/ and backend/src/main/java/com/example/familyfinance/application/mapper/
- [ ] T023 [US2] Implement ReceitaService for creation, date-based aggregation, and net balance in backend/src/main/java/com/example/familyfinance/application/service/ReceitaService.java
- [ ] T024 [US2] Implement ReceitaController REST endpoints for creation and listing in backend/src/main/java/com/example/familyfinance/api/controller/ReceitaController.java
- [ ] T025 [US2] Add summary logic that calculates income, expense, and balance by selected period in backend/src/main/java/com/example/familyfinance/application/service/ResumoFinanceiroService.java

**Checkpoint**: At this point, User Stories 1 and 2 should both work independently.

---

## Phase 5: User Story 3 - Planejar o orçamento e identificar excesso por categoria (Priority: P2)

**Goal**: Let users set monthly budgets by category and detect overages against planned values.

**Independent Test**: A user can create a budget for `Mercado`, add expenses, and observe the usage percentage and alert status.

### Tests for User Story 3

- [ ] T026 [P] [US3] Create controller test for budgeting endpoints and alert responses in backend/src/test/java/com/example/familyfinance/controller/OrcamentoControllerTest.java
- [ ] T027 [P] [US3] Create service test for budget usage percentage and limit breach logic in backend/src/test/java/com/example/familyfinance/service/OrcamentoServiceTest.java

### Implementation for User Story 3

- [ ] T028 [P] [US3] Create OrcamentoMensal entity and repository in backend/src/main/java/com/example/familyfinance/domain/entity/OrcamentoMensal.java and backend/src/main/java/com/example/familyfinance/domain/repository/OrcamentoMensalRepository.java
- [ ] T029 [US3] Create DTOs and mapper for budget create and response in backend/src/main/java/com/example/familyfinance/application/dto/ and backend/src/main/java/com/example/familyfinance/application/mapper/
- [ ] T030 [US3] Implement OrcamentoMensalService with monthly category budget creation and comparison logic in backend/src/main/java/com/example/familyfinance/application/service/OrcamentoMensalService.java
- [ ] T031 [US3] Implement controller endpoints for budget CRUD and category analysis in backend/src/main/java/com/example/familyfinance/api/controller/OrcamentoController.java
- [ ] T032 [US3] Add logic to flag over-budget categories and compute spending percentage within the selected month in backend/src/main/java/com/example/familyfinance/application/service/ResumoFinanceiroService.java

**Checkpoint**: User Story 3 is independently testable and can coexist with the earlier stories.

---

## Phase 6: User Story 4 - Acessar relatórios e histórico por período (Priority: P3)

**Goal**: Provide historical summaries and per-period reporting, including member-level breakdowns and category insights.

**Independent Test**: A user can filter the financial record by month or date range and see only the matching results.

### Tests for User Story 4

- [ ] T033 [P] [US4] Create black-box report test for /summary and date-range filtering in backend/src/test/java/com/example/familyfinance/controller/ResumoFinanceiroControllerTest.java
- [ ] T034 [P] [US4] Create service test for per-period, per-category, and per-member summaries in backend/src/test/java/com/example/familyfinance/service/ResumoFinanceiroServiceTest.java

### Implementation for User Story 4

- [ ] T035 [P] [US4] Create summary response model and controller contract in backend/src/main/java/com/example/familyfinance/application/dto/ResumoFinanceiroDto.java and backend/src/main/java/com/example/familyfinance/api/controller/ResumoFinanceiroController.java
- [ ] T036 [US4] Implement report generation service with totals, category breakdown, member breakdown, and budget usage in backend/src/main/java/com/example/familyfinance/application/service/ResumoFinanceiroService.java
- [ ] T037 [US4] Add query parameters for period, category, member, and transaction type filtering in backend/src/main/java/com/example/familyfinance/api/controller/ResumoFinanceiroController.java
- [ ] T038 [US4] Ensure summary output preserves historical records without duplication or missing data in backend/src/main/java/com/example/familyfinance/application/service/ResumoFinanceiroService.java

**Checkpoint**: All user stories should now be independently functional.

---

## Phase 7: Polish & Cross-Cutting Concerns

**Purpose**: Final validation across all stories, API consistency, and documentation cleanup.

- [ ] T039 [P] Review and align DTOs, validation rules, and API responses across all controllers in backend/src/main/java/com/example/familyfinance/
- [ ] T040 [P] Run all unit and controller tests for expenses, incomes, budgets, and summaries in backend/src/test/java/com/example/familyfinance/
- [ ] T041 Validate quickstart.md usage scenarios against the implemented endpoints in backend/ and specs/001-controle-gastos-familiares/quickstart.md
- [ ] T042 Update project documentation and examples to reflect member-based expense ownership and monthly reporting in README.md and specs/001-controle-gastos-familiares/

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies - can start immediately.
- **Foundational (Phase 2)**: Depends on Setup completion and blocks all user stories.
- **User Stories (Phase 3+)**: All depend on Foundational completion.
- **Polish (Final Phase)**: Depends on all desired user stories being complete.

### User Story Dependencies

- **User Story 1 (P1)**: Can start after Foundational completion and is the MVP.
- **User Story 2 (P1)**: Can start after Foundational completion and should build on the same core transaction model.
- **User Story 3 (P2)**: Can start after Foundational completion and uses the same category and month logic.
- **User Story 4 (P3)**: Can start after the core transaction and budget models are ready.

### Parallel Opportunities

- Setup tasks T001-T003 can run in parallel.
- Foundational tasks T005-T007 can run in parallel.
- User Story 1 tests T009-T010 can run in parallel.
- User Story 1 model tasks T011-T013 can run in parallel.
- Different user stories can be developed in parallel once foundational work is complete.

### MVP Scope

The recommended MVP is User Story 1 plus the shared foundational layer, because it delivers the essential family spending tracking with category and responsible member attribution.
