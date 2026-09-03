# Implementation Plan: Controle de Gastos Familiares

**Branch**: `001-controle-gastos-familiares` | **Date**: 2026-09-03 | **Spec**: [spec.md](spec.md)

**Input**: Feature specification from [spec.md](spec.md)

## Summary

A API deve permitir o cadastro, agrupamento e análise de despesas e receitas familiares, incluindo a classificação por categoria, o acompanhamento do orçamento mensal, a associação do gasto a um membro da família e o cálculo do saldo por período. A solução será estruturada como um serviço REST backend com persistência relacional, regras de negócio centralizadas e testes de contrato e unitários para garantir consistência financeira.

## Technical Context

**Language/Version**: Java 21 / Spring Boot 3.x

**Primary Dependencies**: Spring Web, Spring Validation, Spring Data JPA, PostgreSQL driver, Hibernate Validator, JUnit 5, Mockito, Spring Boot Test

**Storage**: PostgreSQL para ambiente de produção e testes; uso de banco em memória para testes locais opcionais

**Testing**: JUnit 5, Mockito, Spring Boot Test, @WebMvcTest para contrato HTTP, testes de serviço para regras de negócio

**Target Platform**: REST API backend para web/mobile clients

**Project Type**: web-service

**Performance Goals**: consultas financeiras mensais em menos de 200ms para volumes de até 10 mil transações por mês; geração de resumos por período com resposta funcional e previsível

**Constraints**: integridade de dados monetários, restrição de exclusão de categorias com transações vinculadas, agrupamento por categoria e responsável, consistência dentro do mês e do período selecionado

**Scale/Scope**: gerenciamento de uma família ou unidade doméstica com operações mensais, histórico de transações e orçamentos por categoria

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- Pass: a solução mantém foco em API REST com DTOs, entidades e regras de negócio separadas e sem expor entidades de persistência diretamente.
- Pass: os serviços e controladores serão implementados com injeção por construtor, conforme a regra do projeto de evitar field injection.
- Pass: a regra de não uso de Lombok foi respeitada, com geração explícita de getters, setters e construtores.
- Pass: a arquitetura funciona com testes unitários isolados e testes de controlador para o contrato HTTP, conforme as diretrizes do projeto.
- Pass: testes de caixa branca e caixa preta são obrigatórios e serão previstos no plano e na implementação.

## Project Structure

### Documentation (this feature)

```text
specs/001-controle-gastos-familiares/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   └── finance-api.yaml
├── checklist/
│   └── requirements.md
├── spec.md
└── tasks.md
```

### Source Code (repository root)

```text
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/familyfinance/
│   │   │       ├── api/
│   │   │       │   └── controller/
│   │   │       ├── application/
│   │   │       │   ├── dto/
│   │   │       │   ├── mapper/
│   │   │       │   └── service/
│   │   │       ├── domain/
│   │   │       │   ├── entity/
│   │   │       │   ├── enums/
│   │   │       │   └── repository/
│   │   │       ├── exception/
│   │   │       └── config/
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/
│           └── com/example/familyfinance/
│               ├── controller/
│               ├── service/
│               └── integration/
└── pom.xml
```

**Structure Decision**: a solução será entregue como serviço web REST em módulo backend, com separação clara entre controller, DTOs, serviço, entidades e repositórios. Este desenho atende à necessidade de testes por contrato e por regras de negócio sem misturar responsabilidades.

## Complexity Tracking

> No constitution violations identified. The design remains within project constraints and the no-Lombok and constructor-based dependency rules.
