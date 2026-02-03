# 🧪 Guia de Testes da API - CNPJ Validator

Este guia mostra como testar a API usando diferentes ferramentas.

---

## 📋 Índice

1. [IntelliJ IDEA](#1-intellij-idea-recomendado)
2. [Postman](#2-postman)
3. [Bruno](#3-bruno)
4. [VS Code (REST Client)](#4-vs-code-rest-client)
5. [cURL (Terminal)](#5-curl-terminal)

---

## 1️⃣ IntelliJ IDEA (Recomendado)

### ✨ A Forma Mais Fácil!

O IntelliJ tem suporte **nativo** para arquivos `.http` - não precisa instalar nada!

### Passo a Passo:

1. **Abra o projeto** no IntelliJ IDEA
2. **Navegue até:**
   ```
   docs/api-requests.http
   ```
3. **Execute as requisições:**
   - Você verá um **botão verde ▶** ao lado de cada requisição
   - Clique no botão OU pressione `Ctrl+Enter` / `Cmd+Enter`
   - O resultado aparece em uma janela lateral à direita

### 📸 Como Fica:

```http
###
# 1. CADASTRO DE EMPRESA COM CNPJ VÁLIDO (Alfanumérico)
###

POST http://localhost:8080/api/empresas     ◀── ▶ Clique aqui
Content-Type: application/json

{
  "razaoSocial": "Tech Inovação Ltda",
  "cnpj": "12ABC34501DE35",
  "nomeFantasia": "Tech Inova"
}
```

### 💡 Recursos do IntelliJ:

- ✅ **Histórico** - Veja todas as requisições anteriores
- ✅ **Auto-complete** - Sugestões de URLs e headers
- ✅ **Variáveis** - Defina variáveis reutilizáveis
- ✅ **Ambiente** - Alterne entre dev/prod facilmente

### 🎯 Atalhos Úteis:

| Ação | Windows/Linux | Mac |
|------|--------------|-----|
| Executar requisição | `Ctrl + Enter` | `Cmd + Enter` |
| Ver histórico | `Ctrl + H` | `Cmd + H` |
| Comentar linha | `Ctrl + /` | `Cmd + /` |

---

## 2️⃣ Postman

### Importar Collection:

1. **Baixe o arquivo:**
   ```
   docs/CNPJ_Validator_API.postman_collection.json
   ```

2. **Abra o Postman**

3. **Importe a collection:**
   - Clique em **"Import"** (canto superior esquerdo)
   - Arraste o arquivo `CNPJ_Validator_API.postman_collection.json`
   - OU clique em **"Upload Files"** e selecione o arquivo

4. **Pronto!** Você verá a collection **"CNPJ Validator API"** com 3 pastas:
   - ✅ Cadastros Válidos (3 requisições)
   - ❌ Erros de Validação (5 requisições)
   - 🔄 Atualização (1 requisição)
   - ⚡ Casos Especiais (2 requisições)

### Executar Requisições:

1. Expanda a collection
2. Clique em uma requisição
3. Clique em **"Send"**
4. Veja a resposta na parte inferior

### 📸 Estrutura no Postman:

```
CNPJ Validator API/
├── 📁 Cadastros Válidos/
│   ├── 1. Cadastro - CNPJ Alfanumérico Válido
│   ├── 2. Cadastro - CNPJ Numérico com Formatação
│   └── 3. Cadastro - CNPJ Alfanumérico Sem Formatação
├── 📁 Erros de Validação/
│   ├── 4. ERRO - CNPJ com DV Incorreto
│   ├── 5. ERRO - CNPJ com Letras Minúsculas
│   ├── 6. ERRO - CNPJ com Letras no DV
│   ├── 7. ERRO - CNPJ Zerado
│   └── 8. ERRO - Múltiplos Campos Inválidos
├── 📁 Atualização/
│   └── 9. Atualizar Empresa
└── 📁 Casos Especiais/
    ├── 10. CNPJ com Espaços (Válido)
    └── 11. CNPJ com DV Zerados (Válido)
```

### 💡 Dicas Postman:

- ✅ Use **"Runner"** para executar todas as requisições de uma vez
- ✅ Salve **ambientes** (dev, prod) para alternar URLs facilmente
- ✅ Use **variáveis** `{{baseUrl}}` para não repetir URLs

---

## 3️⃣ Bruno

### Importar Collection:

1. **Baixe o arquivo:**
   ```
   docs/CNPJ_Validator_API.bruno.json
   ```

2. **Abra o Bruno**

3. **Importe a collection:**
   - Clique em **"Import Collection"**
   - Selecione o arquivo `CNPJ_Validator_API.bruno.json`
   - Clique em **"Import"**

4. **Pronto!** As requisições estarão organizadas em pastas

### Executar Requisições:

1. Navegue pelas pastas
2. Selecione uma requisição
3. Clique em **"Send"** ou pressione `Ctrl+Enter`
4. Veja a resposta

### 💡 Vantagens do Bruno:

- ✅ Open-source e gratuito
- ✅ Armazena collections em arquivos locais (Git-friendly)
- ✅ Mais leve que o Postman
- ✅ Interface moderna e limpa

---

## 4️⃣ VS Code (REST Client)

### Instalar Extensão:

1. Abra o VS Code
2. Vá em Extensions (`Ctrl+Shift+X`)
3. Pesquise: **"REST Client"**
4. Instale a extensão de **Huachao Mao**

### Usar o Arquivo:

1. **Abra o arquivo:**
   ```
   docs/api-requests.http
   ```

2. **Execute as requisições:**
   - Clique em **"Send Request"** que aparece acima de cada requisição
   - OU pressione `Ctrl+Alt+R` / `Cmd+Alt+R`

3. **Veja a resposta** em uma nova aba

### 📸 Como Fica no VS Code:

```http
###
# 1. CADASTRO DE EMPRESA COM CNPJ VÁLIDO
###
                                            ◀── Send Request
POST http://localhost:8080/api/empresas
Content-Type: application/json

{
  "razaoSocial": "Tech Inovação Ltda",
  "cnpj": "12ABC34501DE35",
  "nomeFantasia": "Tech Inova"
}
```

### 💡 Recursos da Extensão:

- ✅ Syntax highlighting
- ✅ Auto-complete
- ✅ Variáveis de ambiente
- ✅ Salvamento de histórico

---

## 5️⃣ cURL (Terminal)

### Uso Direto no Terminal:

#### ✅ CNPJ Válido:

```bash
curl -X POST http://localhost:8080/api/empresas \
  -H "Content-Type: application/json" \
  -d '{
    "razaoSocial": "Tech Inovação Ltda",
    "cnpj": "12ABC34501DE35",
    "nomeFantasia": "Tech Inova"
  }'
```

#### ❌ CNPJ Inválido:

```bash
curl -X POST http://localhost:8080/api/empresas \
  -H "Content-Type: application/json" \
  -d '{
    "razaoSocial": "Empresa Teste",
    "cnpj": "12ABC34501DE00",
    "nomeFantasia": "Teste"
  }'
```

#### 🔄 Atualizar Empresa:

```bash
curl -X PUT http://localhost:8080/api/empresas/1 \
  -H "Content-Type: application/json" \
  -d '{
    "razaoSocial": "Tech Inovação Ltda - Atualizada",
    "cnpj": "R55231B3000757",
    "nomeFantasia": "Tech Inova Plus"
  }'
```

### 💡 Dicas cURL:

- Adicione `-v` para ver detalhes: `curl -v ...`
- Adicione `-i` para ver headers: `curl -i ...`
- Salve em arquivo: `curl ... > response.json`
- Formatar JSON de resposta (Linux/Mac):
  ```bash
  curl ... | jq
  ```

---

## 📊 Comparação das Ferramentas

| Ferramenta | Facilidade | Recursos | Open Source | Recomendado Para |
|------------|-----------|----------|-------------|------------------|
| **IntelliJ IDEA** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ❌ | Desenvolvedores Java |
| **Postman** | ⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ❌ | Equipes grandes |
| **Bruno** | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ✅ | Git-friendly, Open Source |
| **VS Code REST Client** | ⭐⭐⭐⭐ | ⭐⭐⭐ | ✅ | Desenvolvedores VS Code |
| **cURL** | ⭐⭐⭐ | ⭐⭐ | ✅ | Scripts, CI/CD |

---

## 🎯 Recomendação por Perfil

### 👨‍💻 Desenvolvedor Java/IntelliJ
**Use:** IntelliJ IDEA (arquivo `.http`)
- Não precisa instalar nada
- Integrado ao IDE
- Histórico e auto-complete

### 👥 Equipe Colaborativa
**Use:** Postman
- Compartilhamento de collections
- Documentação automática
- Testes automatizados

### 🆓 Open Source / Git
**Use:** Bruno
- Arquivos versionáveis
- Git-friendly
- Gratuito e leve

### 💻 Usuário VS Code
**Use:** REST Client Extension
- Integrado ao editor
- Leve e rápido
- Suporte a variáveis

### 🤖 Automação / Scripts
**Use:** cURL
- Scriptável
- CI/CD friendly
- Universal

---

## 🧪 CNPJs para Testar

### ✅ Válidos (Devem retornar 200 OK):

```
12ABC34501DE35         (alfanumérico sem formatação)
1345C3A5000106         (alfanumérico sem formatação)
R55231B3000757         (alfanumérico sem formatação)
90.021.382/0001-22     (numérico com formatação)
90.024.778/0001-23     (numérico com formatação)
90.025.108/0001-21     (numérico com formatação)
90.025.255/0001-00     (DV zerados - válido!)
```

### ❌ Inválidos (Devem retornar 400 Bad Request):

```
12ABC34501DE00         (DV incorreto)
1345c3A5000106         (letras minúsculas)
90.024.420/0001A2      (letra no DV)
00000000000000         (CNPJ zerado)
12ABC345               (tamanho incorreto)
R55231B3000700         (DV incorreto)
```

---

## 🆘 Troubleshooting

### Problema: "Connection refused"
**Solução:** Certifique-se de que a aplicação está rodando:
```bash
mvn spring-boot:run
```

### Problema: "404 Not Found"
**Solução:** Verifique se está usando a URL correta:
```
http://localhost:8080/api/empresas
```

### Problema: "Whitelabel Error Page"
**Solução:** Normal! Use os endpoints da API, não acesse `http://localhost:8080` diretamente.

---

## 📞 Suporte

Se tiver dúvidas, consulte:
- 📘 [README.md](../README.md) - Documentação completa
- 🚀 [QUICK_START.md](../QUICK_START.md) - Início rápido
- 🔧 [MIGRATION_GUIDE.md](MIGRATION_GUIDE.md) - Guia de migração

---

**Última atualização:** 02/02/2026
