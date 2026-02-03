# Validador de CNPJ Alfanumérico para Spring Boot

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Validação customizada de CNPJ alfanumérico brasileiro para projetos Spring Boot usando Bean Validation (JSR 380).

## 📋 Sobre o Projeto

Este projeto implementa uma anotação personalizada `@ValidCNPJ` que valida o novo formato alfanumérico de CNPJ brasileiro, composto por:
- **12 caracteres base**: letras maiúsculas (A-Z) e/ou dígitos (0-9)
- **2 dígitos verificadores**: apenas números (0-9)

### Características

✅ Suporte a CNPJ alfanumérico e numérico  
✅ Validação automática de dígitos verificadores  
✅ Remove automaticamente caracteres de formatação (`.`, `/`, `-`)  
✅ Integração nativa com Spring Boot  
✅ Testes unitários e de integração completos  
✅ Mensagens de erro customizáveis  
✅ Suporte a valores nulos configurável  

## 🚀 Início Rápido

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- Spring Boot 3.2.0+

### Instalação

1. Clone o repositório ou copie os arquivos para seu projeto

2. Adicione as dependências no `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

3. Copie as classes para o seu projeto:
   - `ValidCNPJ.java` - Anotação
   - `CNPJValidator.java` - Validador

## 📖 Como Usar

### 1. Em DTOs

```java
import com.example.validation.cnpj.ValidCNPJ;
import jakarta.validation.constraints.NotBlank;

public class EmpresaDTO {
    
    @NotBlank(message = "Razão social é obrigatória")
    private String razaoSocial;
    
    @NotBlank(message = "CNPJ é obrigatório")
    @ValidCNPJ(message = "CNPJ inválido")
    private String cnpj;
    
    // getters e setters
}
```

### 2. Em Controllers REST

```java
@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {
    
    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody EmpresaDTO dto) {
        // A validação ocorre automaticamente
        return ResponseEntity.ok("Empresa cadastrada!");
    }
}
```

### 3. Em Parâmetros de Método

```java
@GetMapping("/consulta")
public ResponseEntity<?> consultar(
    @ValidCNPJ(message = "CNPJ inválido") 
    @RequestParam String cnpj) {
    
    return ResponseEntity.ok("CNPJ válido: " + cnpj);
}
```

### 4. Validação Programática

```java
import com.example.validation.cnpj.CNPJValidator;

public class ExemploUso {
    
    public void exemplo() {
        // Calcular dígitos verificadores
        String dv = CNPJValidator.calculaDV("12ABC34501DE");
        System.out.println("DV: " + dv); // Output: 35
        
        // Validar CNPJ completo
        CNPJValidator validator = new CNPJValidator();
        boolean valido = validator.isValid("12ABC34501DE35", null);
        System.out.println("Válido: " + valido); // Output: true
    }
}
```

## 🎯 Formatos Aceitos

### CNPJ Alfanumérico

```
Sem formatação: 12ABC34501DE35
Com formatação: 12.ABC.345/01DE-35
```

### CNPJ Numérico (tradicional)

```
Sem formatação: 90021382000122
Com formatação: 90.021.382/0001-22
```

## ✅ Exemplos de CNPJs Válidos

```java
// Alfanuméricos
"12ABC34501DE35"
"1345C3A5000106"
"R55231B3000757"

// Numéricos
"90.021.382/0001-22"
"90.024.778/0001-23"
"90.025.255/0001-00"

// Com formatação
"12.ABC.345/01DE-35"
"90.024.420/0001-09"
```

## ❌ Exemplos de CNPJs Inválidos

```java
"12ABC34501DE00"      // DV incorreto
"1345c3A5000106"      // Letras minúsculas não permitidas
"90.024.420/0001A2"   // Letras no DV não permitidas
"90.025.255/0001"     // Tamanho inválido
"00000000000000"      // CNPJ zerado
```

## 🧪 Executando os Testes

### Testes Unitários

```bash
mvn test
```

### Testes de uma Classe Específica

```bash
mvn test -Dtest=CNPJValidatorTest
```

### Com Relatório de Cobertura

```bash
mvn clean test jacoco:report
```

## 📚 Documentação Interativa (Swagger)

O projeto inclui **documentação automática** da API usando Swagger/OpenAPI!

### Acessar o Swagger UI

1. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

2. Abra no navegador:
   ```
   http://localhost:8080/swagger-ui.html
   ```

### Recursos do Swagger

- ✅ **Interface Interativa** - Teste a API direto no navegador
- ✅ **Documentação Automática** - Sempre atualizada com o código
- ✅ **Exemplos Práticos** - CNPJs válidos e inválidos
- ✅ **Descrições Detalhadas** - Explicações de cada endpoint
- ✅ **Try it Out** - Execute requisições sem Postman

### Links Úteis

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **API Docs (JSON):** http://localhost:8080/api-docs
- **API Docs (YAML):** http://localhost:8080/api-docs.yaml

Para mais detalhes, consulte o [Guia do Swagger](docs/SWAGGER_GUIDE.md).

## 📊 Estrutura do Projeto

```
src/
├── main/
│   └── java/
│       └── com/example/
│           ├── validation/cnpj/
│           │   ├── ValidCNPJ.java              # Anotação
│           │   └── CNPJValidator.java          # Validador
│           ├── dto/
│           │   └── EmpresaDTO.java             # DTO de exemplo
│           └── controller/
│               └── EmpresaController.java      # Controller de exemplo
└── test/
    └── java/
        └── com/example/validation/cnpj/
            ├── CNPJValidatorTest.java                    # Testes unitários
            └── CNPJValidationIntegrationTest.java        # Testes integração
```

## ⚙️ Configurações Avançadas

### Personalizar Mensagem de Erro

```java
@ValidCNPJ(message = "O CNPJ informado não é válido")
private String cnpj;
```

### Permitir Valores Nulos

```java
@ValidCNPJ(acceptNull = true)
private String cnpj; // null será considerado válido
```

### Usar com Grupos de Validação

```java
public interface CadastroCompleto {}

@ValidCNPJ(groups = CadastroCompleto.class)
private String cnpj;
```

## 🔧 Tratamento de Erros

### Exemplo de Handler Global

```java
@ControllerAdvice
public class ValidationExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(
        MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });
        return errors;
    }
}
```

### Resposta de Erro (JSON)

```json
{
  "cnpj": "CNPJ inválido ou com dígitos verificadores incorretos",
  "razaoSocial": "Razão social é obrigatória"
}
```

## 📝 Algoritmo de Validação

O algoritmo implementado segue as especificações da Receita Federal:

1. Remove caracteres de formatação (`.`, `/`, `-`)
2. Valida o formato: 12 caracteres alfanuméricos + 2 dígitos
3. Verifica se não é um CNPJ zerado
4. Calcula os dígitos verificadores usando pesos específicos
5. Compara os DVs calculados com os informados

### Pesos para Cálculo do DV

```
Posição: 1  2  3  4  5  6  7  8  9  10 11 12 13
Peso:    6  5  4  3  2  9  8  7  6  5  4  3  2
```

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para:

1. Fazer fork do projeto
2. Criar uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abrir um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 👥 Autores

- Sistema de Validação - *Desenvolvimento inicial*

## 🙏 Agradecimentos

- Baseado no código original de validação de CNPJ alfanumérico
- Spring Framework e Bean Validation (JSR 380)
- Comunidade Java Brasil

## 📮 Suporte

Para reportar bugs ou solicitar features, abra uma issue no repositório.

---

**Nota**: Este validador foi desenvolvido para o novo formato alfanumérico de CNPJ brasileiro. Certifique-se de que está usando a versão correta para o seu caso de uso.
