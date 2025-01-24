# SAAS

## Descrição do Projeto

Este projeto é uma aplicação de microserviços que inclui diversos serviços, como o `ms-authentication`, que é responsável pelo login e cadastro de usuários.

## Tecnologias Utilizadas

- **Java 21**: Linguagem de programação utilizada para o desenvolvimento da aplicação.
- **Spring Boot 3.4.1**: Framework utilizado para facilitar a criação e configuração dos microserviços.
- **Flyway**: Ferramenta de versionamento de banco de dados.
- **H2**: Banco de dados em memória utilizado para testes.
- **PostgreSQL**: Banco de dados relacional utilizado em produção.
- **Spring Security**: Framework de segurança utilizado para autenticação e autorização.

## Serviços

### ms-authentication

Serviço responsável pelo login e cadastro de usuários.

## Estrutura do Projeto

A estrutura do projeto é organizada em microserviços, cada um com responsabilidades específicas e independentes.

## Como Executar

1. Clone o repositório.
2. Navegue até o diretório do projeto.
3. Execute o comando `mvn clean install` para compilar o projeto.
4. Execute o comando `mvn spring-boot:run` para iniciar a aplicação.

## Contribuição

Para contribuir com o projeto, siga os passos abaixo:

1. Faça um fork do repositório.
2. Crie uma branch para sua feature (`git checkout -b feature/nome-da-feature`).
3. Faça commit das suas alterações (`git commit -m 'Adiciona nova feature'`).
4. Faça push para a branch (`git push origin feature/nome-da-feature`).
5. Abra um Pull Request.

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo `LICENSE` para mais detalhes.