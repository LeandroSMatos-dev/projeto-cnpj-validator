# 📚 Guia do Swagger/OpenAPI - CNPJ Validator

Este guia mostra como usar a documentação interativa Swagger da API.

---

## 🚀 Acessando o Swagger

### 1. Inicie a Aplicação

```bash
cd projeto-cnpj-validator
mvn spring-boot:run
```

### 2. Acesse o Swagger UI

Abra seu navegador e acesse:

```
http://localhost:8080/swagger-ui.html
```

**Ou alternativamente:**

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🎨 Interface do Swagger

Ao acessar o Swagger UI, você verá:

### 📋 Topo da Página

```
API de Validação de CNPJ Alfanumérico v1.0.0
```

**Descrição completa** com:
- Explicação do formato CNPJ alfanumérico
- Exemplos de CNPJs válidos
- Regras de validação
- Informações sobre as tecnologias

### 🔖 Tags (Grupos de Endpoints)

```
Empresas
  API para gerenciamento de empresas com validação de CNPJ alfanumérico
```

### 📡 Endpoints Disponíveis

```
POST   /api/empresas       Cadastrar nova empresa
PUT    /api/empresas/{id}  Atualizar empresa
```

---

## 🧪 Testando Endpoints no Swagger

### Exemplo: Cadastrar Empresa

#### 1️⃣ Expandir o Endpoint

- Clique em **POST /api/empresas**
- Clique em **"Try it out"**

#### 2️⃣ Preencher os Dados

O Swagger mostra um exemplo pré-preenchido:

```json
{
  "razaoSocial": "string",
  "cnpj": "string",
  "nomeFantasia": "string"
}
```

**Substitua pelos dados de teste:**

```json
{
  "razaoSocial": "Tech Inovação Ltda",
  "cnpj": "12ABC34501DE35",
  "nomeFantasia": "Tech Inova"
}
```

#### 3️⃣ Executar a Requisição

- Clique em **"Execute"**
- Role a página para ver a resposta

#### 4️⃣ Ver a Resposta

**Sucesso (201 Created):**

```json
{
  "mensagem": "Empresa cadastrada com sucesso!",
  "empresa": {
    "razaoSocial": "Tech Inovação Ltda",
    "cnpj": "12ABC34501DE35",
    "nomeFantasia": "Tech Inova"
  }
}
```

**Erro (400 Bad Request):**

```json
{
  "cnpj": "CNPJ inválido ou com dígitos verificadores incorretos"
}
```

---

## 📝 Exemplos de Testes

### ✅ Teste 1: CNPJ Alfanumérico Válido

```json
{
  "razaoSocial": "Tech Inovação Ltda",
  "cnpj": "12ABC34501DE35",
  "nomeFantasia": "Tech Inova"
}
```

**Resultado Esperado:** 201 Created

---

### ✅ Teste 2: CNPJ Numérico com Formatação

```json
{
  "razaoSocial": "Empresa Exemplo S.A.",
  "cnpj": "90.021.382/0001-22",
  "nomeFantasia": "Exemplo Corp"
}
```

**Resultado Esperado:** 201 Created

---

### ❌ Teste 3: CNPJ com DV Incorreto

```json
{
  "razaoSocial": "Empresa Teste",
  "cnpj": "12ABC34501DE00",
  "nomeFantasia": "Teste"
}
```

**Resultado Esperado:** 400 Bad Request

---

### ❌ Teste 4: CNPJ com Letras Minúsculas

```json
{
  "razaoSocial": "Empresa Beta",
  "cnpj": "1345c3a5000106",
  "nomeFantasia": "Beta"
}
```

**Resultado Esperado:** 400 Bad Request

---

### ❌ Teste 5: Campo Obrigatório Vazio

```json
{
  "razaoSocial": "",
  "cnpj": "12ABC34501DE35",
  "nomeFantasia": "Teste"
}
```

**Resultado Esperado:** 400 Bad Request

```json
{
  "razaoSocial": "Razão social é obrigatória"
}
```

---

## 📊 Recursos do Swagger UI

### 🔍 Filtro de Pesquisa

- No topo, há uma barra de pesquisa
- Digite para filtrar endpoints
- Exemplo: digite "cadastrar"

### 📖 Schemas (Modelos)

- Role até o final da página
- Seção **"Schemas"**
- Veja a estrutura completa do `EmpresaDTO`

```
EmpresaDTO {
  razaoSocial   string   (3 a 200 caracteres) *obrigatório
  cnpj          string   (formato CNPJ)       *obrigatório
  nomeFantasia  string   (máx 100 caracteres)
}
```

### 🌐 Servers (Ambientes)

No topo, você pode alternar entre servidores:

```
Servers
  http://localhost:8080            - Servidor de Desenvolvimento
  https://api.example.com          - Servidor de Produção (exemplo)
```

### 📥 Download da Especificação

Você pode baixar a especificação OpenAPI em:

**JSON:**
```
http://localhost:8080/api-docs
```

**YAML:**
```
http://localhost:8080/api-docs.yaml
```

---

## 🎯 Casos de Uso do Swagger

### 1. Documentação para Desenvolvedores

- Compartilhe a URL do Swagger com a equipe
- Todos veem a mesma documentação atualizada
- Exemplos práticos e interativos

### 2. Testes Manuais

- Teste rapidamente sem Postman/cURL
- Interface visual intuitiva
- Resultados imediatos

### 3. Geração de Código Cliente

- Use ferramentas como Swagger Codegen
- Gere SDKs para diferentes linguagens
- Baseado na especificação OpenAPI

### 4. Validação de Contratos

- Verifique se a API está conforme esperado
- Compare com versões anteriores
- Identifique breaking changes

---

## 🔧 Personalização

### Alterar Título e Descrição

Edite o arquivo:
```
src/main/java/com/example/config/OpenAPIConfig.java
```

### Alterar Cor do Tema

No `application.properties`:
```properties
springdoc.swagger-ui.theme=dark
```

Temas disponíveis:
- `default` (padrão)
- `dark`
- `classic`
- `newspaper`
- `outline`

### Desabilitar em Produção

```properties
# Em application-prod.properties
springdoc.swagger-ui.enabled=false
springdoc.api-docs.enabled=false
```

---

## 📱 Swagger no Mobile

O Swagger UI é **responsivo**! Acesse pelo celular:

```
http://SEU_IP_LOCAL:8080/swagger-ui.html
```

**Exemplo:**
```
http://192.168.1.100:8080/swagger-ui.html
```

---

## 🆚 Swagger vs Outras Ferramentas

| Recurso | Swagger UI | Postman | cURL |
|---------|-----------|---------|------|
| **Documentação Visual** | ✅ Excelente | ⚠️ Manual | ❌ Não |
| **Testes Interativos** | ✅ Sim | ✅ Sim | ⚠️ Terminal |
| **Auto-Atualização** | ✅ Automática | ❌ Manual | ❌ Manual |
| **Compartilhamento** | ✅ URL única | ⚠️ Export | ❌ Complexo |
| **Curva de Aprendizado** | ✅ Baixa | ⚠️ Média | ⚠️ Média |
| **Instalação** | ✅ Nenhuma | ❌ App | ✅ Nativa |

---

## 🎓 Dicas Profissionais

### 1. Use Exemplos Reais

Os exemplos no Swagger foram cuidadosamente criados com CNPJs válidos.
Use-os como referência!

### 2. Leia a Descrição Completa

A descrição no topo explica:
- Como funciona o CNPJ alfanumérico
- Regras de validação
- Exemplos de formatos

### 3. Teste Casos de Erro

Não teste apenas sucessos! Teste também:
- CNPJs inválidos
- Campos vazios
- Formatos incorretos

### 4. Compartilhe com a Equipe

Envie o link do Swagger para:
- Frontend developers
- QA/Testers
- Product managers
- Clientes (se aplicável)

---

## ⚙️ Configurações Avançadas

### Autenticação (JWT, OAuth2, etc.)

Para APIs com autenticação, edite `OpenAPIConfig.java`:

```java
.components(new Components()
    .addSecuritySchemes("bearer-key",
        new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")))
```

### Versionamento de API

```java
.info(new Info()
    .version("2.0.0")
    .title("API de Validação de CNPJ v2"))
```

### Tags Customizadas

No controller:
```java
@Tag(name = "Validação", description = "Endpoints de validação")
```

---

## 🐛 Troubleshooting

### Problema: 404 ao acessar /swagger-ui.html

**Solução 1:** Tente a URL alternativa:
```
http://localhost:8080/swagger-ui/index.html
```

**Solução 2:** Verifique se a dependência está no pom.xml:
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
</dependency>
```

### Problema: Swagger não mostra os endpoints

**Solução:** Verifique se o controller está no pacote correto:
```
com.example.controller
```

### Problema: Exemplos não aparecem

**Solução:** Recompile o projeto:
```bash
mvn clean install
mvn spring-boot:run
```

---

## 📚 Links Úteis

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/api-docs
- **OpenAPI YAML:** http://localhost:8080/api-docs.yaml
- **SpringDoc Docs:** https://springdoc.org/
- **OpenAPI Spec:** https://spec.openapis.org/oas/latest.html

---

## 🎉 Conclusão

O Swagger torna sua API:
- ✅ **Autodocumentada** - Sempre atualizada
- ✅ **Testável** - Interface interativa
- ✅ **Compartilhável** - Link único
- ✅ **Profissional** - Padrão da indústria

**Comece agora:**
1. Execute: `mvn spring-boot:run`
2. Acesse: http://localhost:8080/swagger-ui.html
3. Clique em "Try it out"
4. Teste a API! 🚀

---

**Última atualização:** 02/02/2026  
**Versão:** 1.0.0
