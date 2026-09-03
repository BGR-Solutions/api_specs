# Diretrizes de Desenvolvimento (Spec-Driven Development)

- **Proibição de Lombok**: É estritamente proibido importar ou utilizar qualquer anotação do Lombok (ex: `@Data`, `@Getter`, `@Builder`, `@RequiredArgsConstructor`).
- **Encapsulamento e Construtores**: Todas as entidades e DTOs devem ter atributos privados, conter métodos get e set explícitos e construtores gerados linha a linha.
- **Injeção de Dependência**: Nas classes `@Service` e `@RestController`, utilize apenas injeção via construtor escrito manualmente. Não utilize `@Autowired` em atributos (Field Injection).
- **Isolamento com DTOs**: Entidades de banco de dados (`@Entity`) nunca devem ser expostas em endpoints. A criação de DTOs de entrada e saída é obrigatória.
- **Validação e Exceções**: Utilize Jakarta Validation (`@NotNull`, `@Valid`) no payload e centralize o tratamento de erros em uma classe com `@RestControllerAdvice`.
- **Testes Unitários (Padrão AAA)**: Siga rigorosamente o formato Arrange, Act e Assert.
- **Isolamento de Testes**: Use `@WebMvcTest` para testar controladores. Para regras de negócio, use apenas JUnit 5 e Mockito. A criação de testes que acessem o banco de dados real é proibida.
- **Estratégia Abrangente de Testes**: Todo endpoint criado deve obrigatoriamente incluir Testes de Caixa Branca (unitários isolados com Mockito na camada de Serviço focados em caminhos lógicos) e Testes de Caixa Preta (testes no Controlador validando exclusivamente o contrato HTTP, payloads de entrada e códigos de status de saída).