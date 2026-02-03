# Guia Rápido de Início

## 🚀 Em 5 Minutos

### 1. Clone ou Baixe o Projeto

```bash
git clone <seu-repositorio>
cd projeto-cnpj-validator
```

### 2. Compile o Projeto

```bash
mvn clean install
```

### 3. Execute a Aplicação

```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### 4. Acesse a Documentação Swagger

Abra no navegador:

```
http://localhost:8080/swagger-ui.html
```

Você verá uma **interface interativa** onde pode:
- ✅ Ver todos os endpoints da API
- ✅ Testar requisições direto no navegador
- ✅ Ver exemplos de CNPJs válidos e inválidos
- ✅ Entender as regras de validação

### 5. Teste a API no Swagger

1. No Swagger UI, clique em **POST /api/empresas**
2. Clique em **"Try it out"**
3. Use o exemplo fornecido ou cole:
   ```json
   {
     "razaoSocial": "Tech Inovação Ltda",
     "cnpj": "12ABC34501DE35",
     "nomeFantasia": "Tech Inova"
   }
   ```
4. Clique em **"Execute"**
5. Veja a resposta abaixo!

### 6. Teste Via cURL (Opcional)

#### Usando cURL (CNPJ Válido)

```bash
curl -X POST http://localhost:8080/api/empresas \
  -H "Content-Type: application/json" \
  -d '{
    "razaoSocial": "Empresa Teste Ltda",
    "cnpj": "12ABC34501DE35",
    "nomeFantasia": "Teste"
  }'
```

**Resposta esperada (200 OK):**
```json
{
  "mensagem": "Empresa cadastrada com sucesso!",
  "empresa": {
    "razaoSocial": "Empresa Teste Ltda",
    "cnpj": "12ABC34501DE35",
    "nomeFantasia": "Teste"
  }
}
```

#### Usando cURL (CNPJ Inválido)

```bash
curl -X POST http://localhost:8080/api/empresas \
  -H "Content-Type: application/json" \
  -d '{
    "razaoSocial": "Empresa Teste Ltda",
    "cnpj": "12ABC34501DE00",
    "nomeFantasia": "Teste"
  }'
```

**Resposta esperada (400 Bad Request):**
```json
{
  "cnpj": "CNPJ inválido ou com dígitos verificadores incorretos"
}
```

## 💻 Usando em Seu Projeto

### Passo 1: Copie as Classes

Copie para seu projeto:
- `src/main/java/com/example/validation/cnpj/ValidCNPJ.java`
- `src/main/java/com/example/validation/cnpj/CNPJValidator.java`

### Passo 2: Adicione a Dependência

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

### Passo 3: Use nos Seus DTOs

```java
public class MeuDTO {
    @ValidCNPJ(message = "CNPJ inválido")
    private String cnpj;
}
```

### Passo 4: Valide nos Controllers

```java
@PostMapping
public ResponseEntity<?> criar(@Valid @RequestBody MeuDTO dto) {
    // Validação automática!
    return ResponseEntity.ok(dto);
}
```

## 📝 Exemplos Rápidos de CNPJs

### CNPJs Válidos

```
12ABC34501DE35        (alfanumérico)
90.021.382/0001-22    (numérico com formatação)
1345C3A5000106        (alfanumérico sem formatação)
R55231B3000757        (alfanumérico)
```

### Como Calcular DV

```java
String dv = CNPJValidator.calculaDV("12ABC34501DE");
// Resultado: "35"
```

## 🧪 Executar Testes

```bash
mvn test
```

## 📚 Próximos Passos

- Leia o [README.md](README.md) completo
- Consulte o [Guia de Migração](docs/MIGRATION_GUIDE.md)
- Veja os [exemplos de requisições HTTP](docs/api-requests.http)
- Execute a classe `ExemplosDeUso.java` para ver exemplos programáticos

## ❓ Problemas Comuns

### Erro de compilação com jakarta.validation

**Solução**: Use Spring Boot 3.x ou ajuste para `javax.validation` no Spring Boot 2.x

### Validação não funciona

**Solução**: Certifique-se de usar `@Valid` no controller

### Porta 8080 já em uso

**Solução**: Altere a porta em `application.properties`:
```properties
server.port=8081
```

## 🆘 Ajuda

Para mais informações, consulte a documentação completa no [README.md](README.md)
