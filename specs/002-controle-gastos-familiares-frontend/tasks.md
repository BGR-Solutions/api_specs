# Tasks: Controle de Gastos Familiares - Frontend

**Input**: Design documents from [spec.md](spec.md), [plan.md](plan.md)

**Prerequisites**: plan.md, spec.md

**Organization**: Tasks are grouped by user story to enable independent implementation and testing.

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Prepare the frontend project shell and base tooling.

- [ ] T001 Create frontend project structure under `frontend/src/`, `frontend/tests/`, and shared configuration files
- [ ] T002 Initialize React + TypeScript + Vite project and install frontend dependencies (`react`, `react-dom`, `typescript`, `vite`, `jest`, `@testing-library/react`, `@testing-library/jest-dom`)
- [ ] T003 [P] Configure ESLint, Prettier, and test scripts in `frontend/package.json`
- [ ] T004 [P] Configure test setup for Jest and React Testing Library in `frontend/jest.config.*` and `frontend/src/test/setupTests.ts`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Establish the shared contracts and service layer before user story work.

**Critical**: No story work may start before this phase is complete.

- [ ] T005 Create shared types for finance data in `frontend/src/types/summary.ts`, `transaction.ts`, and `category.ts`
- [ ] T006 Create API client/service modules in `frontend/src/services/summaryService.ts`, `transactionService.ts`, and `categoryService.ts`
- [ ] T007 [P] Implement HTTP error handling and request state helpers for loading and failure flows
- [ ] T008 [P] Define reusable formatter utilities in `frontend/src/utils/formatters.ts`
- [ ] T009 Create shell layout and navigation in `frontend/src/App.tsx` and `frontend/src/pages/`

**Checkpoint**: Foundation ready - user story implementation can now begin.

---

## Phase 3: User Story 1 - Visualizar o resumo financeiro do mês (Priority: P1) 🎯 MVP

**Goal**: Deliver the Dashboard view with summary totals and category breakdown.

**Independent Test**: Open the Dashboard with sample data and confirm totals, balance, and category distribution render correctly.

### Tests for User Story 1

- [ ] T010 [P] [US1] Render dashboard summary with income, expense, and balance in `frontend/tests/unit/Dashboard.test.tsx`
- [ ] T011 [P] [US1] Validate category distribution rendering in `frontend/tests/unit/Dashboard.test.tsx`
- [ ] T012 [P] [US1] Validate empty and loading states in `frontend/tests/unit/Dashboard.test.tsx`

### Implementation for User Story 1

- [ ] T013 [P] [US1] Create dashboard component in `frontend/src/components/dashboard/Dashboard.tsx`
- [ ] T014 [P] [US1] Create summary cards and distribution widgets in `frontend/src/components/dashboard/`
- [ ] T015 [US1] Build `DashboardPage.tsx` to fetch and render summary data from service layer
- [ ] T016 [US1] Wire the dashboard page into application navigation and route entry in `frontend/src/App.tsx`
- [ ] T017 [US1] Add accessibility labels and empty/loading UI states for the dashboard

**Checkpoint**: User Story 1 is fully functional and independently testable.

---

## Phase 4: User Story 2 - Registrar uma nova despesa ou receita (Priority: P1)

**Goal**: Allow the user to create a new financial transaction from a simple form.

**Independent Test**: Fill out a valid transaction form and confirm that the submit flow calls the service and shows success feedback.

### Tests for User Story 2

- [ ] T018 [P] [US2] Render transaction form and required fields in `frontend/tests/unit/TransactionForm.test.tsx`
- [ ] T019 [P] [US2] Validate field-level errors and submit blocking when required data is missing in `frontend/tests/unit/TransactionForm.test.tsx`
- [ ] T020 [P] [US2] Validate successful submission and success message in `frontend/tests/unit/TransactionForm.test.tsx`
- [ ] T021 [P] [US2] Validate API call mapping for expense vs income flows in `frontend/tests/unit/TransactionForm.test.tsx`

### Implementation for User Story 2

- [ ] T022 [P] [US2] Create transaction form component in `frontend/src/components/forms/TransactionForm.tsx`
- [ ] T023 [P] [US2] Create transaction form page in `frontend/src/pages/TransactionFormPage.tsx`
- [ ] T024 [US2] Implement form state model and validation logic for type, category, value, date, and responsible/origin fields
- [ ] T025 [US2] Integrate submit handler with `transactionService.ts` and manage loading/success/error states
- [ ] T026 [US2] Add user feedback for validation failures and submission result in the form UI
- [ ] T027 [US2] Include navigation from dashboard to form and return to dashboard after successful submission

**Checkpoint**: User Stories 1 and 2 can both work independently.

---

## Phase 5: User Story 3 - Validar erros e estados de feedback do formulário (Priority: P2)

**Goal**: Improve reliability by capturing invalid user input and response feedback across the transaction flow.

**Independent Test**: Submit incomplete, invalid, or rejected payloads and confirm that the UI shows clear guidance and blocks invalid requests.

### Tests for User Story 3

- [ ] T028 [P] [US3] Validate invalid value and date handling in `frontend/tests/unit/TransactionForm.test.tsx`
- [ ] T029 [P] [US3] Validate failure states from API response in `frontend/tests/integration/dashboard-flow.test.tsx`
- [ ] T030 [P] [US3] Validate cancellation/reset behavior for the form in `frontend/tests/unit/TransactionForm.test.tsx`

### Implementation for User Story 3

- [ ] T031 [US3] Add message mapping for validation and server-side errors in the form state layer
- [ ] T032 [US3] Add reset/clear behavior for partial form entries after cancellation or success
- [ ] T033 [US3] Add visual feedback for loading, error, and success states in the form and dashboard flows

**Checkpoint**: Core user flows are robust and the application handles validation gracefully.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Final quality pass across all stories.

- [ ] T034 [P] Review accessibility and keyboard support across dashboard and form screens
- [ ] T035 [P] Review TypeScript strictness and remove any `any` usage from frontend code
- [ ] T036 [P] Verify all unit tests pass and adjust edge-case behaviors if needed
- [ ] T037 Documentation update for quickstart and usage notes in `frontend/README.md` or project docs
- [ ] T038 Final regression check for navigation, dashboard summary, and creation flow

---

## Dependencies & Execution Order

### Phase Dependencies

- Setup (Phase 1): no dependencies
- Foundational (Phase 2): depends on setup completion and blocks all story work
- User Story 1 (Phase 3): depends on foundation
- User Story 2 (Phase 4): depends on foundation; can be developed in parallel with US1 if capacity allows
- User Story 3 (Phase 5): depends on foundation and should validate the refinement of user flows
- Polish (Phase 6): depends on all user stories being complete

### User Story Dependencies

- User Story 1 (P1): can start after foundational tasks
- User Story 2 (P1): can start after foundational tasks and may integrate with US1
- User Story 3 (P2): can start after foundational tasks; should validate behavior after US1/US2

### Parallel Opportunities

- T003 and T004 can run in parallel
- T007 and T008 can run in parallel
- US1 tests can run in parallel with other US1 implementation tasks
- US2 tests and implementation can proceed in parallel once the form foundation exists
- Final polish tasks can run after all stories are implemented

---

## Notes

- Each user story is designed to be independently testable from the user viewpoint.
- Tests are written before implementation to ensure behavior matches requirements.
- The service layer remains the only place that calls the backend; components should not contain fetch logic.
- The application should prioritize clear feedback, validation messaging, and speed in the main financial workflow.
