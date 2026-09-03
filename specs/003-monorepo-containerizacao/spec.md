# Feature Specification: Conteinerização do Monorepo com Docker e Docker Compose

**Feature Branch**: `003-monorepo-containerizacao`

**Created**: 2026-09-03

**Status**: Draft

**Input**: User description: "Precisamos criar a infraestrutura de containerização do monorepo com backend Java/Spring Boot, frontend React/TypeScript e PostgreSQL. O Docker Compose deve ficar na raiz do repositório e orquestrar os serviços. Todos os Dockerfiles precisam seguir multi-stage builds. O backend deve compilar com JDK completo e rodar em JRE slim. O frontend deve compilar em Node e servir com Nginx. O PostgreSQL deve ser provisionado no compose com volume persistente."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Subir o monorepo completo com docker compose (Priority: P1)

A equipe precisa iniciar o ambiente completo do projeto em um único comando, incluindo frontend, backend e banco de dados, para facilitar desenvolvimento local, testes e validação end-to-end.

**Why this priority**: Esse é o objetivo central da infraestrutura. Sem o compose funcionando, o projeto não pode ser executado de forma consistente em qualquer ambiente de desenvolvimento.

**Independent Test**: Pode ser validado rodando `docker compose up --build` na raiz do monorepo e confirmando que backend, frontend e PostgreSQL iniciam sem falhas de conexão.

**Acceptance Scenarios**:

1. **Given** que o monorepo está na raiz do projeto, **When** o desenvolvedor executa `docker compose up --build`, **Then** o backend, o frontend e o PostgreSQL devem iniciar em ordem correta e ficar disponíveis nas portas definidas.
2. **Given** que o ambiente já foi inicializado, **When** o serviço de banco é reiniciado, **Then** o volume persistente deve manter os dados do PostgreSQL entre reinicializações.

---

### User Story 2 - Empacotar o backend Java em imagem conforme as regras do projeto (Priority: P1)

A aplicação Java precisa ser entregue em uma imagem leve e segura, compilando com JDK e executando a aplicação em uma imagem final com JRE, sem Lombok e sem depender do ambiente local do sistema.

**Why this priority**: A imagem final do backend precisa seguir as regras de operação e segurança estabelecidas pelo projeto, reduzindo custo e mantendo o container enxuto.

**Independent Test**: Pode ser validado ao construir a imagem do backend e verificar que a etapa de compile usa JDK e a execução final usa uma base JRE slim/alpine sem dependências de build.

**Acceptance Scenarios**:

1. **Given** que a imagem do backend for construída, **When** o processo de build for inspecionado, **Then** a etapa de compilação deve usar uma imagem com JDK completo e a etapa final deve usar uma imagem com JRE.
2. **Given** que o backend esteja em execução no container, **When** a aplicação iniciar, **Then** ela deve escutar na porta configurada e se conectar ao PostgreSQL usando as variáveis de ambiente esperadas.

---

### User Story 3 - Empacotar o frontend React em imagem servida por Nginx (Priority: P1)

O frontend precisa ser compilado em build estática e servido por Nginx para satisfazer o requisito de produção e evitar execução do servidor de desenvolvimento em container.

**Why this priority**: A aplicação web precisa ser entregue como artefato estático em um servidor HTTP eficiente, alinhado ao padrão de implementação da equipe.

**Independent Test**: Pode ser validado construindo a imagem do frontend e acessando a porta do Nginx para confirmar que a interface é entregue corretamente.

**Acceptance Scenarios**:

1. **Given** que a imagem do frontend for construída, **When** o processo de build for analisado, **Then** a etapa de compilação deve usar Node.js e a etapa final deve usar Nginx para servir os arquivos gerados.
2. **Given** que o frontend esteja rodando em container, **When** o usuário acessar a URL do frontend, **Then** a aplicação deve carregar a interface sem depender do ambiente local de Node.

---

### User Story 4 - Provisionar o banco PostgreSQL com persistência e variáveis de ambiente (Priority: P2)

O banco precisa ficar disponível no ambiente de desenvolvimento e com persistência de dados para que os dados do sistema não desapareçam ao reiniciar os containers.

**Why this priority**: O PostgreSQL é um componente essencial do ecossistema. A persistência e a configuração correta de ambiente são requisitos para garantir estabilidade do desenvolvimento.

**Independent Test**: Pode ser validado pelo `docker compose` criando o volume e verificando a persistência após reinicialização do serviço.

**Acceptance Scenarios**:

1. **Given** que o serviço de banco foi criado, **When** docker compose sobe os containers, **Then** o PostgreSQL deve iniciar com usuário, senha e banco configurados por variáveis de ambiente.
2. **Given** que o banco possua dados persistidos, **When** o container do PostgreSQL for reiniciado, **Then** o volume montado deve manter os dados salvos.

### Edge Cases

- O que acontece se o backend tentar iniciar antes do PostgreSQL ficar pronto?
- Como o sistema deve reagir se uma das imagens falhar ao construir?
- O que ocorre se os volumes do PostgreSQL estiverem vazios na primeira inicialização?
- Como o frontend deve se comportar quando o backend ainda não está pronto nos containers?
- O que acontece se a porta do backend, frontend ou PostgreSQL estiver ocupada no host?

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: The repository MUST include a root-level `docker-compose.yml` that orchestrates frontend, backend, and PostgreSQL services.
- **FR-002**: The compose configuration MUST define explicit ports for the backend, frontend, and PostgreSQL so they are accessible from the host machine.
- **FR-003**: The compose configuration MUST define a persistent volume for PostgreSQL data storage and ensure data survives container restarts.
- **FR-004**: The backend container MUST be built from a multi-stage Dockerfile that compiles the Java application using a JDK image and runs it with a JRE-only final stage.
- **FR-005**: The frontend container MUST be built from a multi-stage Dockerfile that compiles the React app with Node and serves static files with Nginx in the final stage.
- **FR-006**: Each Dockerfile MUST be kept in the dedicated service folder for the component it builds, with a clear separation between build and runtime stages.
- **FR-007**: The compose stack MUST configure environment variables for the backend database connection, including database name, user, password, and host.
- **FR-008**: The compose configuration MUST allow the backend to communicate with PostgreSQL over the internal Docker network while exposing only necessary ports externally.
- **FR-009**: The frontend service MUST be configured to serve the application through Nginx without using the Node development server in the production container.
- **FR-010**: Containers SHOULD run with a non-root user whenever the base image and runtime permit it, in line with the security requirement.
- **FR-011**: The stack MUST provide a consistent startup flow using container names, dependency ordering, and health checks or restart policies where appropriate.
- **FR-012**: The repository MUST include clear documentation describing how to run the compose stack from the root of the monorepo.
- **FR-013**: The Docker configuration MUST support local development and validation without requiring a host-level installation of Java, Node, or PostgreSQL in the developer environment.

### Key Entities *(include if feature involves data)*

- **ServiceContainer**: representa cada componente do ambiente em containers: backend, frontend e PostgreSQL.
- **DockerVolume**: representa o armazenamento persistente do banco e qualquer volume necessário para o ambiente local.
- **Network**: representa a rede interna do Docker Compose que permite comunicação entre o backend e o PostgreSQL.
- **BuildStage**: representa cada etapa do Dockerfile, separando compilação da execução final.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: The developer can run the complete monorepo environment from the repository root with a single Docker Compose command.
- **SC-002**: The backend, frontend, and PostgreSQL services are all reachable through the configured ports in a clean local environment.
- **SC-003**: The final images for both backend and frontend comply with the multi-stage build and runtime-base requirements defined by the project.
- **SC-004**: PostgreSQL data remains available after a restart of the database container, confirming persistent storage is configured correctly.
- **SC-005**: The Docker configuration supports predictable local start-up and avoids runtime dependency on host installations of Java, Node, or PostgreSQL.

## Assumptions

- The monorepo root will contain the `docker-compose.yml` file and the service-specific Dockerfiles.
- The backend and frontend services will communicate over the Docker network using internal service names rather than localhost host bindings.
- PostgreSQL will be the default database for the backend in the Dockerized environment.
- Local development uses Docker Desktop or a compatible Docker runtime without additional orchestration tooling.
- The initial scope is containerization for development/local validation, not production-grade orchestration or Kubernetes deployment.
