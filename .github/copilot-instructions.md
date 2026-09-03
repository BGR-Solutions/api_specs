# Diretrizes de Desenvolvimento (Spec-Driven Development) para Monorepo

## Regras Globais
- Siga rigorosamente as especificações descritas nos arquivos spec.md.
- **Entenda o contexto**: se estiver trabalhando na pasta /backend, aplique as regras de Java; na pasta /frontend, aplique TypeScript.

## Regras do Backend (Java/Spring Boot)
- **Proibição de Lombok**: É estritamente proibido importar ou utilizar qualquer anotação do Lombok (ex: `@Data`, `@Getter`, `@Builder`, `@RequiredArgsConstructor`).
- **Encapsulamento e Construtores**: Todas as entidades e DTOs devem ter atributos privados, conter métodos get e set explícitos e construtores gerados linha a linha.
- **Injeção de Dependência**: Nas classes `@Service` e `@RestController`, utilize apenas injeção via construtor escrito manualmente. Não utilize `@Autowired` em atributos (Field Injection).
- **Isolamento com DTOs**: Entidades de banco de dados (`@Entity`) nunca devem ser expostas em endpoints. A criação de DTOs de entrada e saída é obrigatória.
- **Validação e Exceções**: Utilize Jakarta Validation (`@NotNull`, `@Valid`) no payload e centralize o tratamento de erros em uma classe com `@RestControllerAdvice`.
- **Testes Unitários (Padrão AAA)**: Siga rigorosamente o formato Arrange, Act e Assert.
- **Isolamento de Testes**: Use `@WebMvcTest` para testar controladores. Para regras de negócio, use apenas JUnit 5 e Mockito. A criação de testes que acessem o banco de dados real é proibida.
- **Estratégia Abrangente de Testes**: Todo endpoint criado deve obrigatoriamente incluir Testes de Caixa Branca (unitários isolados com Mockito na camada de Serviço focados em caminhos lógicos) e Testes de Caixa Preta (testes no Controlador validando exclusivamente o contrato HTTP, payloads de entrada e códigos de status de saída).

## Regras do Frontend (TypeScript/React)
- **Uso de Tipagem Estrita**: O uso do tipo any é rigorosamente proibido. Sempre utilize tipos explícitos para variáveis, funções e componentes.
- **Separação de Camadas**: A lógica de requisições à API (ex: uso do `fetch` ou `axios`) deve ficar isolada em serviços, e não misturada nos componentes visuais.
- **Componentes Funcionais**: Prefira componentes funcionais com hooks ao invés de classes.
- **Gerenciamento de Estado**: Utilize Context API ou bibliotecas de gerenciamento de estado como Redux ou Zustand, evitando manipulação direta do DOM.
- **Validação de Props**: Utilize `PropTypes` ou interfaces TypeScript para validar as props dos componentes.
- **Testes Unitários**: Utilize Jest e React Testing Library para criar testes unitários e de integração. Siga o padrão AAA (Arrange, Act, Assert).
- **Estilo de Código**: Siga as regras definidas no arquivo de configuração do ESLint e Prettier do projeto.
- **Organização de Pastas**: Mantenha uma estrutura de pastas clara e consistente, separando componentes, páginas, hooks, contextos e estilos de forma lógica.
- **Boas Práticas de Importação**: Sempre utilize imports relativos consistentes e evite caminhos relativos complexos. Prefira aliases configurados no TypeScript para facilitar a manutenção.
- **Acessibilidade**: Sempre considere a acessibilidade ao criar componentes, utilizando atributos como `aria-label` e garantindo que a navegação por teclado seja possível.
- **Internacionalização (i18n)**: Sempre que possível, utilize bibliotecas de internacionalização como `react-i18next` para suportar múltiplos idiomas no frontend.

## Regras para DevOps e Docker (Monorepo):
- **Arquitetura do Compose**: O arquivo `docker-compose.yml` deve ficar obrigatoriamente na raiz do monorepo, orquestrando os serviços de backend, frontend e banco de dados.
- **Multi-stage Builds (Obrigatório)**: Todos os Dockerfiles devem utilizar builds de múltiplos estágios para reduzir o tamanho da imagem final.
- **Backend (Java)**: O estágio de build deve usar uma imagem com o JDK completo para compilar, mas a imagem final deve usar apenas o JRE (preferencialmente Alpine ou Slim) para rodar o `.jar`.
- **Frontend (React)**: O estágio de build deve usar o Node.js, mas a imagem final deve utilizar obrigatoriamente o **Nginx** para servir os arquivos estáticos compilados. Nunca utilize o servidor de desenvolvimento do Node (`npm start`) na imagem final.
- **Segurança**: Sempre que possível, configure os contêineres para rodar com um usuário não-root.

## Regras para CI/CD:
- **Plataforma**: Utilize exclusivamente o GitHub Actions.
- **Execução Paralela**: Os jobs de teste do Backend (Java) e do Frontend (React) devem rodar em paralelo, e não de forma sequencial.
- **Performance (Cache)**: Configure obrigatoriamente o cache para as dependências do Node.js e do Java para acelerar o tempo de execução do pipeline.
- **Validação Estrita**: O pipeline deve executar o build e os testes. A esteira deve falhar (fail-fast) imediatamente se qualquer teste de caixa branca, caixa preta ou do React quebrar.