# API Administrativa

Backend desenvolvido em **Java + Spring Boot** para gerenciamento administrativo de clientes, processos e documentos.

## Tecnologias

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Security
* MySQL
* Maven
* Hibernate

## Funcionalidades

* Autenticação de usuários
* Cadastro e gerenciamento de clientes
* Gerenciamento de processos
* Upload e gerenciamento de documentos
* Gerenciamento de guias do INSS
* Controle de pagamentos
* Painel administrativo
* Controle de permissões
* API REST

## Estrutura do Projeto

```
src/
 ├── controller/
 ├── dto/
 ├── entity/
 ├── exception/
 ├── repository/
 ├── security/
 ├── service/
 └── DemoApplication.java
```

## Configuração

Crie um arquivo de configuração contendo as propriedades do banco de dados e demais variáveis necessárias para o ambiente.

Exemplo:

```
spring.datasource.url=...
spring.datasource.username=...
spring.datasource.password=...
```

## Executando

Com Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Ou utilizando Maven instalado:

```bash
mvn spring-boot:run
```

## Build

```bash
./mvnw clean package
```

## Banco de Dados

A aplicação utiliza MySQL através do Spring Data JPA.

## Observações

Este projeto foi desenvolvido para uso interno e faz parte de um sistema administrativo. Não contém documentação pública da API, credenciais, regras de negócio internas ou informações sensíveis.

## Licença

Projeto privado. Todos os direitos reservados.
