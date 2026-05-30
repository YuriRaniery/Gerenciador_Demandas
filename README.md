# 📋 Gerenciador de Demandas e Tarefas (Gerenciador_Demandas)

API REST desenvolvida com Spring Boot para gerenciamento de demandas e tarefas, oferecendo operações de cadastro, autenticação de usuários, controle de fluxo de trabalho e persistência de dados em múltiplos ambientes.

---

## 👥 Integrantes do Grupo

* **Jose Ramos** - Desenvolvedor
* **Yuri Ferreira** - Desenvolvedor
* **Yuri Raniery** - Desenvolvedor

---

## 🚀 Pré-requisitos

Para compilar, executar e testar a aplicação, certifique-se de possuir:

* **Java Development Kit (JDK) 21** ou superior
* **Apache Maven 3.9+** (ou o Maven Wrapper `./mvnw`)
* **PostgreSQL 15+** (para execução em ambiente de produção)
* **H2 Database** (utilizado automaticamente no perfil `dev`)

---

## 🛠️ Variáveis de Ambiente (`.env`)

A aplicação utiliza variáveis de ambiente para configuração.

Exemplo:

```properties
# CONFIGURAÇÕES DA APLICAÇÃO
SPRING_APPLICATION_NAME=Gerenciador_Demandas
SERVER_PORT=8080

# BANCO DE DADOS
DB_URL=jdbc:postgresql://localhost:5432/gerenciador_demandas
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha

# DOCUMENTAÇÃO
SWAGGER_ENABLED=true
```

---

## ⚙️ Como Executar a Aplicação

A aplicação possui suporte a múltiplos perfis de configuração.

### 🟩 Ambiente de Desenvolvimento (`dev`)

Utiliza banco de dados H2 em memória, ideal para testes e demonstrações.

Linux/macOS:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Windows:

```cmd
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

---

### 🟦 Ambiente de Produção (`prod`)

Utiliza PostgreSQL configurado através das variáveis de ambiente.

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

---

## 📖 Documentação da API (Swagger/OpenAPI)

A documentação interativa da API permite visualizar endpoints, modelos de dados e realizar testes diretamente pelo navegador.

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON:

```text
http://localhost:8080/v3/api-docs
```

> Caso a URL do Swagger seja diferente em sua configuração, consulte as propriedades do SpringDoc utilizadas no projeto.

---

## 🔐 Segurança

* Senhas armazenadas utilizando criptografia/hash seguro.
* Validação de dados realizada através dos recursos do Spring Validation.
* Separação de ambientes por perfis (`dev` e `prod`).

---

## 🛣️ Endpoints da API

### 👤 Usuários (`/api/usuarios`)

Responsável pelo gerenciamento de usuários e autenticação.

| Método | Endpoint                             | Descrição                           |
| ------ | ------------------------------------ | ----------------------------------- |
| GET    | `/api/usuarios`                      | Lista todos os usuários cadastrados |
| POST   | `/api/usuarios/cadastrar`            | Cadastra um novo usuário            |
| POST   | `/api/usuarios/login`                | Realiza autenticação de usuário     |
| PUT    | `/api/usuarios/{email}`              | Atualiza os dados de um usuário     |
| PATCH  | `/api/usuarios/alterarSenha/{email}` | Atualiza a senha do usuário         |
| DELETE | `/api/usuarios/deletar/{email}`      | Remove um usuário do sistema        |

---

### 📋 Demandas e Tarefas (`/api/tasks`)

Responsável pelo gerenciamento das demandas cadastradas.

| Método | Endpoint                 | Descrição                    |
| ------ | ------------------------ | ---------------------------- |
| GET    | `/api/tasks`             | Lista todas as demandas      |
| POST   | `/api/tasks`             | Cria uma nova demanda        |
| GET    | `/api/tasks/{id}`        | Busca uma demanda pelo ID    |
| PUT    | `/api/tasks/{id}`        | Atualiza os dados da demanda |
| PATCH  | `/api/tasks/{id}/status` | Atualiza o status da demanda |
| DELETE | `/api/tasks/{id}`        | Remove uma demanda           |

---

## 📊 Testes e Cobertura

O projeto utiliza:

* **JUnit 5**
* **Mockito**
* **JaCoCo**

Para executar os testes e gerar o relatório de cobertura:

```bash
./mvnw clean verify
```

Relatório gerado em:

```text
target/site/jacoco/index.html
```

---

## 🏗️ Tecnologias Utilizadas

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Validation
* PostgreSQL
* H2 Database
* Maven
* Swagger / OpenAPI
* JUnit 5
* Mockito
* JaCoCo

---

## 📄 Licença

Projeto desenvolvido para fins acadêmicos.
