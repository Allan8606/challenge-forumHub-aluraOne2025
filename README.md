# FórumHub: Sua API REST de Tópicos para um Fórum Online

Este projeto implementa uma API REST para um sistema de fórum, focando especificamente na gestão de tópicos. Desenvolvido como parte de um desafio de backend, o FórumHub permite criar, consultar, atualizar e deletar tópicos, além de gerenciar usuários, autores e cursos, tudo isso com autenticação via JWT e documentação Swagger.

## 🚀 Tecnologias Utilizadas

O projeto foi construído utilizando as seguintes tecnologias e ferramentas:

* **Java 21**
* **Spring Boot 3.x**
* **Maven 4.x**
* **MySQL 8.x** como banco de dados relacional
* **Spring Data JPA** para persistência de dados
* **Flyway Migration** para controle de versão do banco de dados
* **Spring Security** para autenticação e autorização
* **JWT (JSON Web Tokens)** para segurança baseada em tokens
* **Lombok** para reduzir código boilerplate
* **SpringDoc OpenAPI** para geração de documentação Swagger UI
* **Insomnia** (ou Postman) para testar os endpoints da API

## ✨ Funcionalidades (CRUD de Tópicos e mais!)

A API se concentra na gestão de tópicos e oferece as seguintes operações RESTful:

### Tópicos (`/topicos`)
* **`POST /topicos`**: Cria um novo tópico no fórum.
* **`GET /topicos`**: Lista todos os tópicos existentes. Suporta paginação.
* **`GET /topicos/{id}`**: Exibe os detalhes de um tópico específico pelo seu ID.
* **`PUT /topicos/{id}`**: Atualiza as informações de um tópico existente.
* **`DELETE /topicos/{id}`**: Remove um tópico do sistema.

### Autores (`/autores`)
* **`POST /autores`**: Cadastra um novo autor.
* **`GET /autores`**: Lista todos os autores.
* **`GET /autores/{id}`**: Detalha um autor específico.
* **`PUT /autores/{id}`**: Atualiza um autor.
* **`DELETE /autores/{id}`**: Remove um autor.

### Cursos (`/cursos`)
* **`POST /cursos`**: Cadastra um novo curso.
* **`GET /cursos`**: Lista todos os cursos.
* **`GET /cursos/{id}`**: Detalha um curso específico.
* **`PUT /cursos/{id}`**: Atualiza um curso.
* **`DELETE /cursos/{id}`**: Remove um curso.

### Autenticação (`/login`)
* **`POST /login`**: Autentica um usuário e retorna um token JWT para acesso aos endpoints protegidos.

## 🔒 Regras de Negócio e Segurança

* Todos os campos obrigatórios (definidos com `@NotBlank`, `@NotNull` nas DTOs) são validados.
* A API não permite o cadastro de tópicos duplicados (com o mesmo título e mensagem).
* **Apenas usuários autenticados e com um token JWT válido podem acessar os endpoints de CRUD de tópicos, autores e cursos.** O endpoint `/login` é público.

## ⚙️ Como Rodar o Projeto

Siga os passos abaixo para configurar e executar o projeto em sua máquina local.

### Pré-requisitos
* Java JDK 17 ou superior
* Maven 4 ou superior
* MySQL 8 ou superior
* Uma IDE (IntelliJ IDEA é recomendado)

### Configuração do Banco de Dados
1.  Crie um banco de dados MySQL com o nome `forumhub`. Você pode fazer isso no MySQL Workbench ou no terminal:
    ```sql
    CREATE DATABASE forumhub;
    ```
2.  Configure as credenciais do seu banco de dados no arquivo `src/main/resources/application.properties`:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/forumhub?createDatabaseIfNotExist=true
    spring.datasource.username=[SEU_USUARIO_MYSQL] # Ex: root
    spring.datasource.password=[SUA_SENHA_MYSQL]
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

    spring.flyway.enabled=true
    spring.flyway.baseline-on-migrate=true

    # Chave secreta para o JWT. Mude este valor para uma string complexa e segura!
    api.security.token.secret=um_segredo_muito_secreto_e_forte
    ```
    *Lembre-se de substituir `[SEU_USUARIO_MYSQL]` e `[SUA_SENHA_MYSQL]`.*

3.  **Para poder testar a autenticação, insira um usuário manualmente no banco de dados:**
    * Crie uma classe temporária para gerar a senha criptografada (ex: `GeradorSenhas.java` na raiz do seu pacote `br.com.projetoAlura.ForumHub`):
        ```java
        package br.com.projetoAlura.ForumHub;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
        public class GeradorSenhas {
            public static void main(String[] args) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String senhaCriptografada = encoder.encode("123456"); // Senha original
                System.out.println("Senha criptografada: " + senhaCriptografada);
            }
        }
        ```
    * Execute o `main` desta classe e copie a senha criptografada gerada (começa com `$2a$`).
    * No MySQL Workbench, insira o usuário na tabela `usuarios`:
        ```sql
        USE forumhub;
        INSERT INTO usuarios (login, senha) VALUES ('teste', '[SUA_SENHA_CRIPTOGRAFADA_GERADA_AQUI]');
        ```

### Executando a Aplicação
1.  Abra o projeto em sua IDE (ex: IntelliJ IDEA).
2.  Certifique-se de que todas as dependências Maven foram sincronizadas (se necessário, clique em "Load Maven Changes" ou "Import Changes").
3.  Execute a classe principal da aplicação: `ForumHubApplication.java`.
4.  O Flyway executará as migrações e criará as tabelas necessárias no banco de dados automaticamente. O servidor Tomcat será iniciado na porta `8080`.

## 🧪 Testando a API

Você pode testar a API utilizando o Insomnia (ou Postman, ou a interface do Swagger UI).

### Acessando a Documentação (Swagger UI)
* Com a aplicação rodando, acesse: `http://localhost:8080/swagger-ui.html`
* Você poderá visualizar todos os endpoints documentados e testá-los diretamente pela interface. Para testar endpoints protegidos, clique no botão "Authorize" e insira o token JWT no formato `Bearer SEU_TOKEN`.

### Testando via Insomnia/Postman

#### 1. Autenticar e Obter Token JWT
* **Requisição:** `POST /login`
* **URL:** `http://localhost:8080/login`
* **Body (JSON):**
    ```json
    {
        "login": "teste",
        "senha": "123456"
    }
    ```
* **Resposta esperada:** `200 OK` com um JSON contendo o `token`. **Copie este token!**

#### 2. Usar o Token para Requisições Protegidas
Para todas as requisições aos endpoints de `/topicos`, `/autores` e `/cursos`, você deve adicionar um cabeçalho de `Authorization`. No Insomnia, vá na aba "Auth", selecione "Bearer Token" e cole o token JWT copiado.

#### Exemplos de Requisições (com o token no cabeçalho `Authorization`):

**a. Cadastrar um Tópico**
* **Requisição:** `POST /topicos`
* **URL:** `http://localhost:8080/topicos`
* **Body (JSON):**
    ```json
    {
        "titulo": "Duvida sobre APIs REST",
        "mensagem": "Qual a melhor forma de modelar recursos?",
        "status": "ABERTO",
        "autor": { "id": 1, "nome": "João Silva", "email": "joao.silva@example.com" },
        "curso": { "id": 1, "nome": "Spring Boot", "categoriaCurso": "PROGRAMACAO" }
    }
    ```
    * _Ajuste os IDs de autor e curso conforme os que você inseriu manualmente ou cadastrou._
* **Resposta esperada:** `201 Created` com os detalhes do tópico.

**b. Listar Tópicos**
* **Requisição:** `GET /topicos`
* **URL:** `http://localhost:8080/topicos`
* **Resposta esperada:** `200 OK` com uma lista de tópicos.

**c. Testar Tópico Duplicado (Regra de Negócio)**
* Tente enviar a mesma requisição `POST /topicos` do item `a` novamente.
* **Resposta esperada:** `400 Bad Request` com a mensagem `"Já existe um tópico com o mesmo título e mensagem."`.

