# Tasks: Pipeline de Integração Contínua do Monorepo

**Input**: Design documents from [spec.md](spec.md) and [plan.md](plan.md)

**Prerequisites**: plan.md (required), spec.md (required for user stories)

**Organization**: Tasks are grouped by user story to enable independent implementation and validation of the CI workflow.

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Create the workflow scaffolding and repository-level CI entry point.

- [ ] T001 Create the GitHub Actions workflow file in `.github/workflows/ci.yml`
- [ ] T002 Define the `push` and `pull_request` triggers for the default branch (`main`) in `.github/workflows/ci.yml`
- [ ] T003 [P] Add workflow name, permissions, and basic job structure in `.github/workflows/ci.yml`
- [ ] T004 [P] Define job-level concurrency and fail-fast behavior for CI runs in `.github/workflows/ci.yml`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Lay down the shared CI contracts that all jobs depend on before implementing story-specific validation.

**⚠️ CRITICAL**: No user story implementation can begin until this phase is complete.

- [ ] T005 Configure Maven dependency cache for the backend in `.github/workflows/ci.yml`
- [ ] T006 Configure npm dependency cache for the frontend in `.github/workflows/ci.yml`
- [ ] T007 Define a reusable checkout/setup step for backend and frontend jobs in `.github/workflows/ci.yml`
- [ ] T008 Add environment assumptions for Java 21 and Node.js 20 in the workflow jobs
- [ ] T009 Create the CI strategy for parallel job execution of backend and frontend in `.github/workflows/ci.yml`

**Checkpoint**: Foundation ready - user story implementation can now begin in parallel.

---

## Phase 3: User Story 1 - Executar validação do monorepo em cada push ou pull request (Priority: P1) 🎯 MVP

**Goal**: Trigger the pipeline automatically on repository changes to the main branch and run both backend and frontend checks.

**Independent Test**: Push a change to `main` or open a PR against `main` and confirm the workflow starts and executes both jobs.

### Implementation for User Story 1

- [ ] T010 [P] [US1] Add workflow trigger conditions for `push` and `pull_request` targeting `main` in `.github/workflows/ci.yml`
- [ ] T011 [P] [US1] Create a backend job in `.github/workflows/ci.yml` with a distinct name and execution path
- [ ] T012 [P] [US1] Create a frontend job in `.github/workflows/ci.yml` with a distinct name and execution path
- [ ] T013 [US1] Ensure backend and frontend jobs run in parallel within the same workflow
- [ ] T014 [US1] Validate that the workflow file is recognized by GitHub Actions and abbreviates the run status clearly per job

**Checkpoint**: User Story 1 is runnable and independently testable.

---

## Phase 4: User Story 2 - Validar a aplicação Java em uma esteira de CI separada (Priority: P1)

**Goal**: Validate the Java backend with Java 21, Maven cache, tests, and build.

**Independent Test**: Run the backend job and confirm Maven downloads dependencies via cache and executes the Java test/build checks successfully.

### Implementation for User Story 2

- [ ] T015 [P] [US2] Add the Java setup step in `.github/workflows/ci.yml` using JDK 21
- [ ] T016 [US2] Add Maven cache configuration based on `backend/pom.xml` and project dependency folders in `.github/workflows/ci.yml`
- [ ] T017 [US2] Run the backend dependency installation and test step, e.g. `mvn test`, in the backend job
- [ ] T018 [US2] Run the backend build validation step, e.g. `mvn package` or equivalent build verification, in the backend job
- [ ] T019 [US2] Ensure the backend job fails immediately if any test or build step fails

**Checkpoint**: User Story 2 is independently buildable and validates the backend.

---

## Phase 5: User Story 3 - Validar a aplicação React em uma esteira de CI separada (Priority: P1)

**Goal**: Validate the React frontend with Node.js, npm cache, tests, and production build.

**Independent Test**: Run the frontend job and confirm npm dependencies are cached and the test/build pipeline completes successfully.

### Implementation for User Story 3

- [ ] T020 [P] [US3] Add the Node.js setup step in `.github/workflows/ci.yml` using a supported LTS version
- [ ] T021 [US3] Add npm cache configuration based on `frontend/package-lock.json` and `frontend/package.json` in `.github/workflows/ci.yml`
- [ ] T022 [US3] Install frontend dependencies with `npm ci` in the frontend job
- [ ] T023 [US3] Run the frontend test suite in the frontend job
- [ ] T024 [US3] Run the frontend production build using `npm run build` in the frontend job
- [ ] T025 [US3] Ensure the frontend job fails immediately if tests or build fail

**Checkpoint**: User Story 3 is independently buildable and validates the frontend.

---

## Phase 6: User Story 4 - Garantir falha rápida e sinalização clara em caso de regressão (Priority: P1)

**Goal**: Fail the workflow clearly when any part of the monorepo breaks.

**Independent Test**: Deliberately break a backend or frontend test and verify the corresponding job fails and marks the workflow as unsuccessful.

### Implementation for User Story 4

- [ ] T026 [P] [US4] Add explicit job naming and status outputs so failures are easy to locate in GitHub Actions
- [ ] T027 [US4] Configure the workflow to stop on failing backend or frontend job execution without hiding the failure source
- [ ] T028 [US4] Add concise documentation comments in `.github/workflows/ci.yml` describing the purpose of each job and cache configuration
- [ ] T029 [US4] Validate the job output and ensure failed steps are readable to developers reviewing the PR

**Checkpoint**: Workflow behavior is clear, fast, and actionable for the team.

---

## Phase 7: Polish & Cross-Cutting Concerns

**Purpose**: Final review of CI correctness and repository readiness.

- [ ] T030 [P] Review the complete workflow for branching, triggers, and permissions in `.github/workflows/ci.yml`
- [ ] T031 [P] Validate both backend and frontend jobs use the correct working directories and file paths
- [ ] T032 [P] Confirm the pipeline is aligned with the repo’s CI/CD rules: parallel jobs, dependency cache, fail-fast semantics, and build/test validation
- [ ] T033 Documentation update for CI usage in the repository README or a workflow-specific quickstart note

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: no dependencies.
- **Foundational (Phase 2)**: depends on Setup and blocks all story work.
- **User stories (Phase 3+)**: depend on Foundational completion.
- **Polish (Final Phase)**: depends on all user stories being complete.

### User Story Dependencies

- **User Story 1 (P1)**: can start after Quick Setup and enables the workflow trigger contract.
- **User Story 2 (P1)**: depends on the workflow skeleton and Java setup.
- **User Story 3 (P1)**: depends on the workflow skeleton and Node setup.
- **User Story 4 (P1)**: depends on the prior validations and ensures the workflow fails correctly.

### Parallel Opportunities

- T003 and T004 can run in parallel.
- T005 and T006 can run in parallel.
- T010, T011, and T012 can run in parallel once the workflow skeleton exists.
- T015 and T020 can be implemented in parallel because each job is independent.
- Final review tasks can run after all stories are complete.

---

## Notes

- The workflow should be stored in `.github/workflows/ci.yml` using standard GitHub Actions conventions.
- The primary branch for validation is `main`.
- The backend and frontend projects should be validated independently, with both jobs running in parallel for speed.
- The workflow should fail immediately if either project fails its tests or build validation.
