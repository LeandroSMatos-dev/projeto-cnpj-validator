# Guia de Migração - Validador de CNPJ

Este guia auxilia na migração de projetos existentes para utilizar a validação customizada de CNPJ alfanumérico.

## 📋 Pré-requisitos

Antes de iniciar a migração, certifique-se de que seu projeto possui:

- ✅ Spring Boot 3.x (ou 2.7+ com ajustes)
- ✅ Java 17+ (ou Java 11+ para versões antigas)
- ✅ Maven ou Gradle como build tool
- ✅ Spring Boot Starter Validation

## 🔄 Etapas de Migração

### 1. Adicionar Dependências

#### Maven (pom.xml)

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

#### Gradle (build.gradle)

```gradle
implementation 'org.springframework.boot:spring-boot-starter-validation'
```

### 2. Copiar Classes de Validação

Copie os seguintes arquivos para seu projeto:

```
src/main/java/com/seupackage/validation/cnpj/
├── ValidCNPJ.java
└── CNPJValidator.java
```

**Importante**: Ajuste o package conforme a estrutura do seu projeto.

### 3. Atualizar DTOs Existentes

#### Antes (sem validação)

```java
public class EmpresaDTO {
    private String razaoSocial;
    private String cnpj;
    
    // getters e setters
}
```

#### Depois (com validação)

```java
import com.seupackage.validation.cnpj.ValidCNPJ;
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

### 4. Atualizar Controllers

#### Antes (validação manual)

```java
@PostMapping
public ResponseEntity<?> cadastrar(@RequestBody EmpresaDTO dto) {
    // Validação manual do CNPJ
    if (!validarCNPJManualmente(dto.getCnpj())) {
        return ResponseEntity.badRequest().body("CNPJ inválido");
    }
    
    // lógica de negócio
    return ResponseEntity.ok(dto);
}

private boolean validarCNPJManualmente(String cnpj) {
    // código de validação duplicado
    // ...
}
```

#### Depois (validação automática)

```java
@PostMapping
public ResponseEntity<?> cadastrar(@Valid @RequestBody EmpresaDTO dto) {
    // A validação ocorre automaticamente!
    // lógica de negócio
    return ResponseEntity.ok(dto);
}
```

### 5. Adicionar Exception Handler Global (Opcional mas Recomendado)

Crie ou atualize seu exception handler:

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
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

## 🔧 Ajustes para Spring Boot 2.x

Se você estiver usando Spring Boot 2.x, faça as seguintes alterações:

### 1. Substituir Imports

```java
// Trocar
import jakarta.validation.*;

// Por
import javax.validation.*;
```

### 2. Versão do Java

Spring Boot 2.x pode usar Java 11+:

```xml
<properties>
    <java.version>11</java.version>
</properties>
```

## 🧪 Testar a Migração

### 1. Executar Testes Unitários

```bash
mvn clean test
```

### 2. Testar Endpoints REST

Use o arquivo `api-requests.http` fornecido ou ferramentas como Postman:

```bash
# CNPJ válido
curl -X POST http://localhost:8080/api/empresas \
  -H "Content-Type: application/json" \
  -d '{"razaoSocial":"Teste","cnpj":"12ABC34501DE35","nomeFantasia":"Teste"}'

# CNPJ inválido
curl -X POST http://localhost:8080/api/empresas \
  -H "Content-Type: application/json" \
  -d '{"razaoSocial":"Teste","cnpj":"12ABC34501DE00","nomeFantasia":"Teste"}'
```

## 📝 Checklist de Migração

- [ ] Dependências adicionadas
- [ ] Classes `ValidCNPJ` e `CNPJValidator` copiadas
- [ ] Package names ajustados
- [ ] DTOs atualizados com `@ValidCNPJ`
- [ ] Controllers com `@Valid` adicionado
- [ ] Exception handler implementado
- [ ] Testes unitários criados
- [ ] Testes de integração executados
- [ ] Documentação da API atualizada
- [ ] Mensagens de erro customizadas (se necessário)

## 🔍 Casos Especiais

### Validação Condicional

Se você precisa validar CNPJ apenas em certas condições:

```java
public interface CadastroCompleto {}
public interface CadastroSimples {}

public class EmpresaDTO {
    
    // CNPJ obrigatório apenas no cadastro completo
    @ValidCNPJ(groups = CadastroCompleto.class)
    private String cnpj;
}

// No controller
@PostMapping("/completo")
public ResponseEntity<?> cadastrarCompleto(
    @Validated(CadastroCompleto.class) @RequestBody EmpresaDTO dto) {
    // ...
}
```

### Permitir CNPJ Nulo

Se CNPJ for opcional em alguns casos:

```java
@ValidCNPJ(acceptNull = true, message = "CNPJ inválido quando informado")
private String cnpj;
```

### Validar em Entidades JPA

```java
@Entity
@Table(name = "empresas")
public class Empresa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String razaoSocial;
    
    @Column(nullable = false, unique = true, length = 14)
    @ValidCNPJ
    private String cnpj;
    
    // getters e setters
}
```

## ⚠️ Problemas Comuns

### Erro: "jakarta.validation.* não encontrado"

**Solução**: Verifique se está usando a versão correta para seu Spring Boot:
- Spring Boot 3.x → `jakarta.validation.*`
- Spring Boot 2.x → `javax.validation.*`

### Validação não está funcionando

**Soluções**:
1. Verifique se `@Valid` ou `@Validated` está presente no controller
2. Confirme que `spring-boot-starter-validation` está no classpath
3. Verifique se o `@EnableAutoConfiguration` está configurado

### Mensagens de erro não aparecem

**Solução**: Implemente um `@ControllerAdvice` para capturar `MethodArgumentNotValidException`

## 🚀 Próximos Passos

Após a migração bem-sucedida:

1. ✅ Remover código de validação manual duplicado
2. ✅ Atualizar documentação da API (Swagger/OpenAPI)
3. ✅ Adicionar testes para novos cenários
4. ✅ Considerar adicionar validações customizadas similares (CPF, etc.)
5. ✅ Revisar e otimizar tratamento de exceções

## 📚 Recursos Adicionais

- [Bean Validation Specification](https://beanvalidation.org/)
- [Spring Validation Documentation](https://docs.spring.io/spring-framework/reference/core/validation.html)
- [Hibernate Validator](https://hibernate.org/validator/)

## 💡 Dicas

- Use mensagens de erro em arquivo `.properties` para internacionalização
- Configure profiles diferentes para desenvolvimento e produção
- Implemente logs detalhados para validações em ambiente de desenvolvimento
- Considere cache para melhorar performance em alta carga

---

**Última atualização**: 2024  
**Versão do guia**: 1.0
