# Sistema de Gerenciamento de Usuários

Aplicação desenvolvida em **Java** para gerenciamento de usuários com operações de **CRUD (Create, Read, Update, Delete)**.

Este projeto passou por uma **evolução de arquitetura** durante seu desenvolvimento:

1. Inicialmente foi criado como uma aplicação Java simples utilizando **banco de dados H2 em memória**, com interação local para fins de estudo de persistência de dados.
2. Posteriormente o projeto foi **refatorado e expandido para uma aplicação Web utilizando Spring Boot**, permitindo acesso através do navegador.
3. A versão atual foi **publicada na nuvem utilizando Azure App Service**.

Essa evolução permitiu aplicar conceitos importantes como:

- refatoração de código  
- separação em camadas  
- desenvolvimento de aplicações web com Spring  
- deploy em cloud  

🔗 **Aplicação online:**  
https://cadastropromocoesbootcamp-beh9ercvgmh3fpdq.brazilsouth-01.azurewebsites.net/usuarios

## Interface da Aplicação

### Lista de usuários
<img width="1321" height="362" alt="image" src="https://github.com/user-attachments/assets/83091539-e1de-4faf-8e19-9506120ff78e" />

### Cadastro de usuário
<img width="1335" height="284" alt="image" src="https://github.com/user-attachments/assets/ad21e76c-506b-4290-89fa-c5e30ae1d50e" />

---

# Tecnologias utilizadas

- Java  
- Spring Boot  
- Spring MVC  
- Spring Data JPA  
- Jakarta Persistence (JPA)  
- Thymeleaf  
- Maven  
- H2 Database (versão inicial do projeto)  
- Azure App Service  

---

# Evolução do projeto

O projeto começou como um **sistema simples para estudo de persistência de dados** utilizando **H2 em memória**, permitindo cadastrar e manipular usuários localmente.

Com o avanço do desenvolvimento, o projeto foi **refatorado e estruturado em camadas**, adotando o ecossistema **Spring Boot** para transformar a aplicação em um **sistema web acessível via navegador**.

Principais melhorias implementadas na evolução do projeto:

- migração para **Spring Boot**
- implementação do padrão **MVC**
- criação de **controllers web**
- separação em **Service e Repository**
- validação de dados
- tratamento de exceções personalizadas
- deploy da aplicação na **Azure**

---

# Funcionalidades

O sistema permite:

- Cadastrar usuários  
- Listar usuários cadastrados  
- Buscar usuário por ID  
- Atualizar dados de um usuário  
- Remover usuários  

Cada usuário possui:

- ID  
- Nome  
- Email  

---

# Arquitetura do projeto

O projeto segue o padrão de arquitetura em camadas.

```
Controller  → recebe requisições HTTP
Service     → regras de negócio
Repository  → acesso ao banco de dados
Model       → entidades
Exception   → tratamento de erros
Util        → mensagens e constantes
```

Estrutura simplificada:

```
src
 └─ main
     └─ java
         └─ org.example
             ├─ controller
             ├─ model
             ├─ repository
             ├─ service
             ├─ exception
             └─ util
```

---

# Modelo de dados

Entidade principal do sistema:

```
Usuario
```

Campos:

| Campo | Tipo | Descrição |
|------|------|-----------|
| id | Long | Identificador do usuário |
| nome | String | Nome do usuário |
| email | String | Email do usuário |

---

# Validações

O sistema valida os dados antes de salvar ou atualizar usuários:

- Nome não pode ser vazio  
- Email deve possuir formato válido  

Caso alguma regra não seja respeitada, são lançadas exceções personalizadas:

- `DadosInvalidosException`
- `UsuarioNaoEncontradoException`

---
