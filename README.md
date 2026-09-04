# Family Finance

Aplicação de controle financeiro familiar com backend em Spring Boot, frontend em React + Vite e banco de dados PostgreSQL em container. O repositório também contém a documentação de especificação do projeto em `specs/`.

## Visão geral

O projeto foi estruturado em um monorepo com três partes principais:

- Backend: API REST em Java com Spring Boot
- Frontend: interface em React + TypeScript
- Infraestrutura: PostgreSQL e orquestração com Docker Compose

A aplicação permite:

- cadastrar membros da família
- cadastrar categorias de gasto
- registrar despesas
- consultar dados consolidados por mês

## Estrutura do projeto

```text
.
├── backend/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── frontend/
│   ├── Dockerfile
│   ├── package.json
│   ├── nginx.conf
│   └── src/
├── specs/
│   ├── 001-controle-gastos-familiares/
│   ├── 002-controle-gastos-familiares-frontend/
│   ├── 003-monorepo-containerizacao/
│   └── 004-pipeline-ci-monorepo/
├── docker-compose.yml
├── README.md
└── LICENSE
```

## Requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- Docker e Docker Compose
- Java 21+
- Maven
- Node.js 20+
- npm

## Execução com Docker Compose

A forma mais rápida de subir o ambiente completo é com o comando abaixo na raiz do projeto:

```bash
docker compose up --build
```

Isso inicia:

- PostgreSQL em `localhost:5432`
- Backend na porta `localhost:8080`
- Frontend em `http://localhost:5173`

Para encerrar os containers:

```bash
docker compose down
```

Para remover também o volume do banco de dados:

```bash
docker compose down -v
```

## Execução local do backend

Acesse a pasta do backend:

```bash
cd backend
```

Execute a aplicação:

```bash
mvn spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8080
```

Se quiser executar apenas os testes do backend:

```bash
mvn test
```

## Execução local do frontend

Acesse a pasta do frontend:

```bash
cd frontend
npm install
npm run dev
```

O frontend será servido em:

```text
http://localhost:5173
```

Para gerar a build de produção:

```bash
npm run build
```

Para executar os testes do frontend:

```bash
npm test
```

## Utilização da API

### Membros da família

- `POST /members` — cria um membro
- `GET /members` — lista os membros

### Categorias

- `POST /categories` — cria uma categoria
- `GET /categories` — lista as categorias

### Despesas

- `POST /expenses` — cria uma despesa
- `GET /expenses` — lista as despesas

Exemplo de criação de categoria:

```bash
curl -X POST http://localhost:8080/categories \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alimentação",
    "description": "Gastos com supermercado e restaurantes"
  }'
```

Exemplo de criação de despesa:

```bash
curl -X POST http://localhost:8080/expenses \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Supermercado",
    "value": 250.50,
    "date": "2026-09-04",
    "categoryId": 1,
    "memberId": 1
  }'
```

## Banco de dados e validação dos dados

O projeto usa PostgreSQL na execução via Docker. Em desenvolvimento local, o backend também pode utilizar H2 em memória por padrão, conforme configurado em `backend/src/main/resources/application.yml`.

### Conexão com o PostgreSQL via Docker

Após subir o ambiente com:

```bash
docker compose up --build
```

você pode validar os dados diretamente no banco com qualquer cliente SQL, por exemplo:

```bash
psql -h localhost -U familyfinance -d familyfinance
```

Senha padrão configurada no projeto:

```text
familyfinance
```

Queries úteis para validar os registros gerados:

```sql
SELECT * FROM members;
SELECT * FROM categories;
SELECT * FROM expenses;
```

Também é possível consultar as tabelas com o comando:

```sql
\dt
```

### Conexão via aplicação Spring Boot

Quando a aplicação estiver em execução, a URL padrão do banco para o ambiente local é:

```text
jdbc:postgresql://localhost:5432/familyfinance
```

Os valores esperados para conexão são:

- usuário: `familyfinance`
- senha: `familyfinance`
- banco: `familyfinance`

### Verificação via console H2

Se o backend estiver rodando localmente sem Docker, ele pode usar o banco H2 em memória. Para abrir o console do H2, acesse:

```text
http://localhost:8080/h2-console
```

Use as configurações:

- JDBC URL: `jdbc:h2:mem:familyfinance;DB_CLOSE_DELAY=-1;MODE=PostgreSQL`
- usuário: `sa`
- senha: vazia

Neste console, você pode validar os dados gerados pela aplicação executando consultas como:

```sql
SELECT * FROM members;
SELECT * FROM categories;
SELECT * FROM expenses;
```

## Testes

### Backend

```bash
cd backend
mvn test
```

### Frontend

```bash
cd frontend
npm test
```

### Testes manuais da interface

1. inicie o ambiente com Docker Compose ou o backend e o frontend localmente
2. abra o endereço do frontend no navegador
3. cadastre membros, categorias e despesas
4. valide a atualização do resumo mensal na dashboard

## Documentação complementar

A pasta `specs/` contém os artefatos do processo de especificação do projeto, incluindo:

- especificação funcional
- modelo de dados
- plano de implementação
- contratos de API
- tarefas e checklist

## Observações

- O backend usa Spring Boot 3 e Java 21
- O frontend usa React, TypeScript e Vite
- A execução em containers fica centralizada no arquivo `docker-compose.yml`
- O Dockerfile do frontend serve a aplicação com Nginx na imagem final

---

Se quiser, também posso adaptar este README para incluir badges, imagens de arquitetura e uma seção de troubleshooting.
