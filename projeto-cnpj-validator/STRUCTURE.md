# Estrutura do Projeto - CNPJ Validator

```
projeto-cnpj-validator/
│
├── README.md                              # Documentação principal
├── QUICK_START.md                         # Guia rápido de início
├── LICENSE                                # Licença MIT
├── .gitignore                            # Arquivos ignorados pelo Git
├── pom.xml                               # Configuração Maven
│
├── docs/                                 # Documentação adicional
│   ├── MIGRATION_GUIDE.md               # Guia de migração
│   └── api-requests.http                # Exemplos de requisições HTTP
│
└── src/
    ├── main/
    │   ├── java/com/example/
    │   │   ├── CnpjValidatorApplication.java         # Classe principal
    │   │   │
    │   │   ├── validation/cnpj/                      # Validação customizada
    │   │   │   ├── ValidCNPJ.java                   # Anotação
    │   │   │   └── CNPJValidator.java               # Validador
    │   │   │
    │   │   ├── dto/                                  # Data Transfer Objects
    │   │   │   └── EmpresaDTO.java                  # DTO de exemplo
    │   │   │
    │   │   ├── controller/                           # REST Controllers
    │   │   │   └── EmpresaController.java           # Controller de exemplo
    │   │   │
    │   │   └── examples/                             # Exemplos de uso
    │   │       └── ExemplosDeUso.java               # Classe com exemplos
    │   │
    │   └── resources/
    │       └── application.properties                # Configurações
    │
    └── test/
        └── java/com/example/validation/cnpj/
            ├── CNPJValidatorTest.java                # Testes unitários
            └── CNPJValidationIntegrationTest.java    # Testes de integração
```

## Descrição dos Componentes

### 📂 Raiz do Projeto

- **README.md**: Documentação completa do projeto com exemplos, instalação e uso
- **QUICK_START.md**: Guia rápido para começar em 5 minutos
- **LICENSE**: Licença MIT do projeto
- **.gitignore**: Arquivos e diretórios ignorados pelo Git
- **pom.xml**: Configuração do Maven com dependências Spring Boot

### 📂 docs/

- **MIGRATION_GUIDE.md**: Guia detalhado para migrar projetos existentes
- **api-requests.http**: Arquivo com 15 exemplos de requisições REST prontas para uso

### 📂 src/main/java/

#### validation/cnpj/
- **ValidCNPJ.java**: Anotação customizada para validação de CNPJ
  - Suporta configuração de mensagens
  - Permite aceitar valores nulos
  - Compatível com grupos de validação
  
- **CNPJValidator.java**: Implementação do validador
  - Valida CNPJ alfanumérico e numérico
  - Remove formatação automaticamente
  - Calcula dígitos verificadores
  - Método estático público para cálculo de DV

#### dto/
- **EmpresaDTO.java**: Exemplo de DTO usando @ValidCNPJ
  - Demonstra integração com Bean Validation
  - Inclui outras validações (@NotBlank, @Size)

#### controller/
- **EmpresaController.java**: Controller REST de exemplo
  - Endpoints POST e PUT
  - Validação automática com @Valid
  - Exception handler para erros de validação

#### examples/
- **ExemplosDeUso.java**: Classe executável com exemplos práticos
  - 5 exemplos diferentes de uso
  - Pode ser executada via `main()`
  - Demonstra validação programática e com Bean Validation

### 📂 src/main/resources/

- **application.properties**: Configurações da aplicação
  - Porta do servidor
  - Configurações de encoding
  - Configurações de logging
  - Formato de JSON

### 📂 src/test/java/

- **CNPJValidatorTest.java**: Testes unitários completos
  - 50+ casos de teste
  - Testa CNPJs válidos e inválidos
  - Testa cálculo de DV
  - Testa tratamento de erros
  - Organizado em classes nested

- **CNPJValidationIntegrationTest.java**: Testes de integração
  - Testa validação em DTOs
  - Valida comportamento com Bean Validation
  - Múltiplos cenários de validação
  - Testa casos especiais

## Como Usar

### Para Iniciar a Aplicação

```bash
cd projeto-cnpj-validator
mvn spring-boot:run
```

### Para Executar Testes

```bash
mvn test
```

### Para Compilar

```bash
mvn clean install
```

### Para Ver Exemplos

```bash
mvn exec:java -Dexec.mainClass="com.example.examples.ExemplosDeUso"
```

## Integração em Projetos Existentes

Para usar apenas a validação sem toda a estrutura:

1. Copie `ValidCNPJ.java` e `CNPJValidator.java` para seu projeto
2. Ajuste o package conforme necessário
3. Use `@ValidCNPJ` nos seus DTOs
4. Adicione `spring-boot-starter-validation` no pom.xml

## Próximos Passos Sugeridos

- [ ] Adicionar testes de performance
- [ ] Implementar cache para melhor performance
- [ ] Adicionar suporte a internacionalização (i18n)
- [ ] Criar validador para CPF alfanumérico
- [ ] Adicionar métricas e monitoramento
- [ ] Configurar CI/CD
- [ ] Publicar no Maven Central

## Tecnologias Utilizadas

- Java 17+
- Spring Boot 3.2.0
- Bean Validation (JSR 380)
- JUnit 5
- Maven 3.6+

## Compatibilidade

- ✅ Spring Boot 3.x (Jakarta EE 9+)
- ✅ Spring Boot 2.7+ (com ajustes para javax.validation)
- ✅ Java 17+
- ✅ Java 11+ (com ajustes menores)
