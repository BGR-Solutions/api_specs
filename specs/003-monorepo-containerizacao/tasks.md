# Tasks: Conteinerização do Monorepo

**Input**: Design documents from [spec.md](spec.md) and [plan.md](plan.md)

**Prerequisites**: plan.md (required), spec.md (required for user stories)

**Organization**: Tasks are grouped by user story to enable independent implementation and validation of each Dockerized component.

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Establish the root-level container orchestration structure and shared environment configuration.

- [ ] T001 Create repository-level Docker structure for the compose stack in `docker-compose.yml`, `.env.example`, and root-level docs
- [ ] T002 Define shared environment configuration for backend, frontend, and PostgreSQL in `.env.example` and service configuration files
- [ ] T003 [P] Create the backend Docker build context and runtime folder structure in `backend/` and add the initial `backend/Dockerfile`
- [ ] T004 [P] Create the frontend Docker build context and runtime folder structure in `frontend/` and add the initial `frontend/Dockerfile`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Set up the runtime foundations that all user stories depend on: database volume, networking, and startup order.

**⚠️ CRITICAL**: No user story implementation can begin until this phase is complete.

- [ ] T005 Define the root Docker network and service dependencies in `docker-compose.yml`
- [ ] T006 Configure PostgreSQL service with environment variables, ports, and persistent volume in `docker-compose.yml`
- [ ] T007 Configure backend service with database connection variables, port mapping, and startup dependency on PostgreSQL in `docker-compose.yml`
- [ ] T008 Configure frontend service with Nginx runtime, port mapping, and backend connection assumptions in `docker-compose.yml`
- [ ] T009 Add restart policy and health checks or readiness checks for backend and PostgreSQL in `docker-compose.yml`
- [ ] T010 Create root documentation for running the stack from the repository root in `README.md` or a feature quickstart file

**Checkpoint**: Foundation ready - user story implementation can now begin in parallel.

---

## Phase 3: User Story 1 - Subir o monorepo completo com docker compose (Priority: P1) 🎯 MVP

**Goal**: Provide one-command startup for backend, frontend, and PostgreSQL in a local Docker environment.

**Independent Test**: Run `docker compose up --build` from the repository root and confirm the three services are healthy and reachable.

### Implementation for User Story 1

- [ ] T011 [P] [US1] Create the root `docker-compose.yml` orchestration file with all required services and declared ports
- [ ] T012 [P] [US1] Define the PostgreSQL data volume and ensure it survives restarts in `docker-compose.yml`
- [ ] T013 [US1] Ensure backend startup waits for PostgreSQL readiness and exposes the API port for host access
- [ ] T014 [US1] Ensure frontend startup serves build artifacts via Nginx and exposes the web port for host access
- [ ] T015 [US1] Validate host connectivity and startup order using `docker compose config` and `docker compose up --build`

**Checkpoint**: At this point, User Story 1 should be fully runnable and independently testable.

---

## Phase 4: User Story 2 - Empacotar o backend Java em imagem conforme as regras do projeto (Priority: P1)

**Goal**: Build a secure, lightweight backend container that compiles with JDK and runs with JRE.

**Independent Test**: Build the backend image and inspect the stages to confirm JDK build + JRE runtime compliance.

### Implementation for User Story 2

- [ ] T016 [P] [US2] Implement a multi-stage `backend/Dockerfile` with a JDK build stage and a slim JRE runtime stage
- [ ] T017 [US2] Copy the Maven project files and dependencies into the build stage to optimize Docker caching
- [ ] T017B [US2] Compile the Spring Boot application inside the builder image and produce a runnable JAR
- [ ] T018 [US2] Configure the final runtime stage to execute the compiled JAR with a JRE-only base image
- [ ] T019 [US2] Set the backend container to use expected environment variables and port mapping for database connection and app startup
- [ ] T020 [US2] Validate the image build with `docker build -f backend/Dockerfile ./backend`

**Checkpoint**: User Story 2 is independently buildable and aligns with the repository’s Docker rules.

---

## Phase 5: User Story 3 - Empacotar o frontend React em imagem servida por Nginx (Priority: P1)

**Goal**: Package the React frontend as static assets served by Nginx without using the Node dev server in the final image.

**Independent Test**: Build the frontend image and confirm the static app is served through Nginx on the configured port.

### Implementation for User Story 3

- [ ] T021 [P] [US3] Implement a multi-stage `frontend/Dockerfile` using Node for the build stage and Nginx for the final runtime stage
- [ ] T022 [US3] Copy the package manifest and install dependencies in the build stage to preserve Docker layer efficiency
- [ ] T023 [US3] Build the React/Vite application into static assets and copy them to the Nginx public directory
- [ ] T024 [US3] Configure the frontend container to expose the web port and serve static files without the Node dev server
- [ ] T025 [US3] Validate the image build with `docker build -f frontend/Dockerfile ./frontend`

**Checkpoint**: User Story 3 is independently buildable and deployable as a static web artifact.

---

## Phase 6: User Story 4 - Provisionar o banco PostgreSQL com persistência e variáveis de ambiente (Priority: P2)

**Goal**: Deliver a stable PostgreSQL service with configuration values and persistent data storage.

**Independent Test**: Start the database service, create data, restart it, and confirm the data remains after container restart.

### Implementation for User Story 4

- [ ] T026 [P] [US4] Configure PostgreSQL credentials, database name, and host variables in the compose stack
- [ ] T027 [US4] Bind the database container to a persistent named volume for data retention across restarts
- [ ] T028 [US4] Expose the database port only as needed for local development and internal service access
- [ ] T029 [US4] Test persistence by inserting sample data, restarting the database container, and rechecking the data
- [ ] T030 [US4] Document the database variables and volume behavior in the project quickstart or README

**Checkpoint**: User Story 4 is independently operational and preserves state across restarts.

---

## Phase 7: Polish & Cross-Cutting Concerns

**Purpose**: Final review of stack health, docs, and runtime reliability.

- [ ] T031 [P] Run a final `docker compose config` validation to ensure the stack is syntactically correct
- [ ] T032 [P] Run `docker compose up --build` and verify all services start and remain healthy without crashes
- [ ] T033 [P] Validate all service ports and connectivity: frontend, backend, and PostgreSQL
- [ ] T034 Confirm the final images satisfy the project constraints: backend uses JRE runtime, frontend uses Nginx runtime, and PostgreSQL uses persistent storage
- [ ] T035 Update the root documentation to explain how to launch, stop, and troubleshoot the stack from the monorepo root
- [ ] T036 Final regression pass for Docker Compose startup ordering, volume persistence, and environment configuration

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies - can start immediately.
- **Foundational (Phase 2)**: Depends on Setup completion and blocks all user stories.
- **User Stories (Phase 3+)**: All depend on Foundational readiness.
- **Polish (Final Phase)**: Depends on all user stories being complete.

### User Story Dependencies

- **User Story 1 (P1)**: Can start after Foundational completion and delivers the MVP stack.
- **User Story 2 (P1)**: Can start once the compose root is defined and the backend build context exists.
- **User Story 3 (P1)**: Can start once the frontend build context exists and the root stack is defined.
- **User Story 4 (P2)**: Can start after the database service structure is ready and can be validated alongside the stack.

### Parallel Opportunities

- T003 and T004 can run in parallel.
- T011 and T012 can run in parallel for the compose root setup.
- T016 and T021 can run in parallel because each service has independent Dockerfile work.
- T026 and T027 can run in parallel with the database configuration tasks.
- Final validation tasks can run after all stories are implemented.

---

## Notes

- The stack is expected to run from the repository root with a single `docker compose up --build` command.
- The Dockerfiles must respect the repo’s rules: backend JDK build + JRE runtime, frontend Node build + Nginx runtime.
- The database persistence requirement is mandatory and should be validated after a restart of the PostgreSQL container.
- Root-level orchestration is mandatory; the stack must not rely on ad hoc local runtime installation of Java, Node.js, or PostgreSQL.
