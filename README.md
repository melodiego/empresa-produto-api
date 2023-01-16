# Controle de Estoque para Empresas

Um parágrafo da descrição do projeto vai aqui

### 📋 Pré-requisitos

Pré requisitos para rodar o projeto em sua máquina

1.Instalar o [Docker](https://docs.docker.com/engine/install/)

```
Docker
```

### 🔧 Instalação para rodar o projeto
1. Entre na raíz do projeto
2. Execute o seguinte comando

```
docker-compose -f src/main/docker/docker-compose.yml up -d
```
3. Clique [aqui](http://localhost:8080/empresa-produto-api) para acessar o serviço


### 🔧 Instalação para desenvolvimento

Para ter um ambiente de desenvolvimento local você deve seguir os seguintes passos:

Para instalar o Docker em sua máquina seguir os passos do link informado, 
escolhendo o sistema operacional da sua máquina

    
1. Execute o seguinte comando

```
docker-compose -f src/main/docker/postgres/docker-compose.yml up -d
```

2. O docker subirá as imagens do postgres e pgadmin. 
3. Clique [aqui](http://localhost:5050/) para acessar a url do pgadmin para criar o banco de dados
4. Utilize as seguintes credenciais para acessar
```
Login: admin@gmail.com
Senha: admin
```

5. Em cima do menu **SERVER**, clique com o botão direito e em **Server**.
6. Na aba **General** coloque um nome exemplo: "**localhost**"
7. Na aba **Connection** em hostname/address coloque: postgres
8. Na mesma aba em username coloque: **postgres** e password: **postgres** e clique em salvar
9. Em cima do servidor criado "localhost", clique com o botão direito e em **New/Database**
10. Coloque o nome do banco: **empresa_produto** e **clique em salvar**
11. Agora você pode rodar o projeto em sua IDE de preferência

## 🛠️ Frameworks

* [Spring](https://docs.spring.io/spring-framework/docs/current/reference/html/) - O framework web usado
* [Maven](https://maven.apache.org/) - Gerenciamento de Dependência
* [Lombok](https://projectlombok.org/) - Automatize suas variáveis de registro e muito mais
* [Swagger](https://swagger.io/) - Documentação do projeto

## 🛠️ Swagger do projeto

* [Swagger](http://localhost:8080/empresa-produto-api/swagger-ui/index.html) - Documentação do projeto


---
⌨️ por [Diego Melo](https://github.com/melodiego)