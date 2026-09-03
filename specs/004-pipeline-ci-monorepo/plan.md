# Implementation Plan: Pipeline de Integração Contínua do Monorepo

**Branch**: `004-pipeline-ci-monorepo` | **Date**: 2026-09-03 | **Spec**: [spec.md](spec.md)

**Input**: Feature specification from [spec.md](spec.md)

## Summary

Este plano define a automação de CI para o monorepo composto por backend Java/Spring Boot e frontend React/TypeScript. A implementação será feita em GitHub Actions com um workflow único na pasta padrão do repositório, disparado em `push` e `pull_request` para a branch principal (`main`). A esteira será separada em jobs paralelos de backend e frontend, com cache de dependências para Maven e npm, além de validação de build e testes para cada projeto.

## Technical Context

**Language/Version**: Java 21; Spring Boot 3.x; React + TypeScript; Node.js 20 LTS; GitHub Actions

**Primary Dependencies**: Maven, JUnit 5, Mockito, Spring Boot Test, Vite, Jest, React Testing Library, npm, GitHub Actions

**Storage**: N/A for the CI workflow itself; validation runs against existing project dependencies and test suites

**Testing**: Maven test suite for backend; Jest + React Testing Library for frontend; build verification via `mvn package` and `npm run build`

**Target Platform**: GitHub-hosted runners for continuous validation of the monorepo on pushes and PRs

**Project Type**: monorepo / web application / CI workflow

**Performance Goals**: execute backend and frontend validation in parallel, minimize install time with dependency cache, and fail quickly on first failing job

**Constraints**: workflows must respect the repo’s CI/CD requirements: jobs in parallel, Maven cache configured, Node cache configured, fail-fast behavior, no sequential validation dependency; branch trigger limited to main

**Scale/Scope**: monorepo with two active projects (`backend` and `frontend`) and a single automation entry point in GitHub Actions

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- Pass: o workflow será implementado em GitHub Actions, conforme exigência das regras globais.
- Pass: os jobs do backend e do frontend serão executados em paralelo, sem dependência sequencial.
- Pass: o backend usará JDK 21 e cache do Maven para acelerar a pipeline.
- Pass: o frontend usará Node.js e cache do npm para acelerar a esteira.
- Pass: o pipeline falhará imediatamente quando qualquer suíte de testes ou build do backend/frontend quebrar.
- Pass: a solução será válida para monorepo com estrutura separada em `backend/` e `frontend/`.

## Project Structure

### Documentation (this feature)

```text
specs/004-pipeline-ci-monorepo/
├── spec.md
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   └── github-actions-contract.md
└── tasks.md
```

### Source Code (repository root)

```text
.
├── .github/
│   └── workflows/
│       └── ci.yml
├── backend/
│   ├── pom.xml
│   └── src/
├── frontend/
│   ├── package.json
│   └── src/
├── docker-compose.yml
├── README.md
├── .env.example
└── .gitignore
```

**Structure Decision**: a ação será centralizada em um workflow único em `.github/workflows/ci.yml`, mantendo `backend/` e `frontend/` como projetos independentes, com jobs separados e caminhos específicos para cache e execução de testes/build.

## Complexity Tracking

> No constitution violations identified. The solution remains within the mandated GitHub Actions, parallelism, and dependency-cache requirements and does not require exceptions.
