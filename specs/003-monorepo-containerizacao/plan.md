# Implementation Plan: Conteinerização do Monorepo

**Branch**: `003-monorepo-containerizacao` | **Date**: 2026-09-03 | **Spec**: [spec.md](spec.md)

**Input**: Feature specification from [spec.md](spec.md)

## Summary

Este plano define a infraestrutura de containerização do monorepo composto por backend Java/Spring Boot, frontend React/TypeScript e PostgreSQL. A solução será estruturada com um `docker-compose.yml` na raiz do repositório, usando três serviços principais, volumes persistentes, rede interna e Dockerfiles multi-stage em cada módulo. O objetivo é permitir inicialização completa do ambiente com um único comando, sem depender de instalações locais do Java, Node.js ou PostgreSQL.

## Technical Context

**Language/Version**: Java 21 / Spring Boot 3.x; React + TypeScript; PostgreSQL 16

**Primary Dependencies**: Docker Engine, Docker Compose, Maven, Node.js, Nginx, PostgreSQL

**Storage**: PostgreSQL com volume persistente em `postgres_data` para manter dados entre reinicializações

**Testing**: Validação por build dos containers, smoke test de inicialização e verificação de portas e conectividade dos serviços

**Target Platform**: Local development environment via Docker Compose, monorepo root orchestration

**Project Type**: multi-service monorepo / web application

**Performance Goals**: startup do ambiente em poucos minutos, resposta estável de frontend e backend em ambiente local, persistência correta do banco

**Constraints**: multi-stage Dockerfiles obrigatórios; backend final em JRE; frontend final em Nginx; DB deve persistir; containers devem funcionar em rede interna sem expor serviços desnecessários

**Scale/Scope**: monorepo com 3 componentes principais (backend, frontend, banco), com foco em desenvolvimento local e validação de integração

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- Pass: a arquitetura do Compose respeita a exigência de orquestrar backend, frontend e banco em um único arquivo raiz.
- Pass: os Dockerfiles seguirão builds multi-stage, conforme exigência do projeto.
- Pass: o backend usará JDK na etapa de build e JRE na imagem final, respeitando a regra de segurança e tamanho da imagem.
- Pass: o frontend usará Node na fase de build e Nginx na etapa final, sem depender do servidor de desenvolvimento do Node.
- Pass: o desenho usa volume persistente e rede interna, além de evitar execução com usuário root sempre que possível.

## Project Structure

### Documentation (this feature)

```text
specs/003-monorepo-containerizacao/
├── spec.md
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   └── docker-compose-contract.md
├── checklist/
│   └── requirements.md
└── tasks.md
```

### Source Code (repository root)

```text
.
├── docker-compose.yml
├── backend/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── frontend/
│   ├── Dockerfile
│   ├── package.json
│   └── src/
├── postgres/
│   └── init/
│       └── (opcional, scripts de inicialização)
├── .env.example
├── README.md
└── .gitignore
```

**Structure Decision**: a solução será organizada em um único compose raiz com três serviços definidos explicitamente: `backend`, `frontend` e `postgres`. Cada serviço terá um Dockerfile próprio no diretório correspondente. O volume persistente do PostgreSQL ficará no root do monorepo e a comunicação entre backend e banco ocorrerá em rede interna do Compose.

## Complexity Tracking

> No constitution violations identified. The design stays within the required Docker, Compose, and runtime-base constraints and does not require exceptions.
