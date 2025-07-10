# Gerenciador de Compromissos 


## Descrição

Este projeto tem como objetivo ajudar o usuário a gerenciar seus compromissos de forma automatizada. O usuário poderá interagir com o sistema através do **Telegram**, enviando mensagens com informações sobre compromissos (data, hora, descrição) e o sistema irá armazenar, listar, modificar ou deletar esses compromissos.

A aplicação permite:
- Adicionar compromissos.
- Listar compromissos por dia ou mês.
- Modificar compromissos existentes.
- Deletar compromissos.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2**
- **Telegram Bot API**
- **Banco de dados MySQL**
- **JPA (Java Persistence API)**
- **Docker** (para containerização)

## Estrutura do Projeto

- **Controller**: Gerencia as interações com o usuário via Telegram.
- **Service**: Contém a lógica de negócios para adicionar, listar, modificar e deletar compromissos.
- **Repository**: Responsável pela persistência dos dados no banco de dados.
- **Model**: Contém a estrutura de dados dos compromissos.

## Endpoints de Telegram

O usuário interage com o bot enviando mensagens formatadas conforme demonstrado no tópico de funcionalidades.


## Funcionalidades

### 1. Adicionar Compromisso


**Exemplo de entrada:**

```javascript
Assunto: Adicionar compromisso
Data: 30/06/2025
Hora: 16:00
Descrição: Aniversário Raphaela
Local: Salão de festas Além da Alegria
```

**Resposta de sucesso:**

```javascript
Compromisso adicionado com sucesso! 
ID: 12345 
Data: 30/06/2025 
Hora: 16:00
Descrição: Aniversário Raphaela
Local: Salão de festas Além da Alegria
```

### 2. Listar Compromissos

**Exemplo de entrada:**

```javascript
Assunto: Listar compromissos 
Data: 30/06/2025
```

**Resposta de sucesso:**

```javascript
Compromissos no dia 30/06/2025: 
ID: 12345 
Hora: 16:00
Descrição: Aniversário Raphaela
Local: Salão de festas Além da Alegria

ID: 67892 
Hora: 10:00
Descrição: Manutenção unhas de gel
Local: Salão CasemiroNails
```


### 3. Modificar Compromisso

**Exemplo de entrada:**

```javascript
Assunto: Modificar compromisso 
ID: 67892  
Hora: 08:00
```

**Resposta de sucesso:**

```javascript
Compromisso modificado com sucesso! 
ID: 67892 
Hora: 08:00
Descrição: Manutenção unhas de gel
Local: Salão CasemiroNails
```


### 4. Deletar Compromisso

**Exemplo de entrada:**

```javascript
Assunto: Deletar compromisso 
ID: 67892  
```
**Resposta de sucesso:**

```javascript
Compromisso deletado com sucesso! 
ID: 67892
```

## Modelagem de Dados

### Entidade `Compromisso`
Esta entidade representa um compromisso que o usuário irá gerenciar (adicionar, listar, modificar, deletar).

```java
@Entity
public class CompromissoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Strig data;
    
    @Column(nullable = false)
    private LocalTime hora;
    
    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String local;
    
    //@Column(nullable = false, unique = true)
    //private String idTelegram;
    
    // Getters e Setters
}

```
### Atributos do Compromisso

- id: Identificador único do compromisso no banco de dados.

- data: A data em que o compromisso ocorrerá.

- hora: O horário do compromisso.

- descricao: Descrição do compromisso (ex.: "Aniversário Raphaela").

- local: Descrição do local do compromisso (ex.: Salão de festas Além da Alegria)

- idTelegram: Identificador único gerado para o compromisso para integração com o Telegram.

```sql
CREATE TABLE compromisso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    data DATE NOT NULL,
    hora TIME NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    local VARCHAR(255) NOT NULL,
    idTelegram VARCHAR(255) NOT NULL UNIQUE
);
```

## Configuração do Banco de Dados MySQL
Para conectar ao banco de dados MySQL, ajuste as configurações no arquivo application.properties:

```java
spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_banco
spring.datasource.username=usuario
spring.datasource.password=senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```
- Substitua nome_do_banco, usuario e senha pelas credenciais do seu banco MySQL.

## Configuração do Docker

A seguir, estão as instruções para rodar o projeto e o banco de dados MySQL utilizando Docker.

### 1. Criando o `Dockerfile` para o Spring Boot
Crie um arquivo chamado `Dockerfile` na raiz do seu projeto com o seguinte conteúdo:

```java
# Etapa 1: Construção (build) da aplicação
        FROM maven:3.9.6-eclipse-temurin-17 as builder

        WORKDIR /app

        # Copia todos os arquivos para o container e realiza o build
        COPY . .
        RUN mvn clean package -DskipTests

        # Etapa 2: Imagem final com apenas o JAR
        FROM eclipse-temurin:17-jre

        WORKDIR /app

        # Copia apenas o JAR gerado da etapa anterior
        COPY --from=builder /app/target/gerenciadorcompromissos-0.0.1-SNAPSHOT.jar app.jar

        # Expõe a porta da aplicação
        EXPOSE 8080

        # Comando de inicialização da aplicação
        ENTRYPOINT ["java", "-jar", "app.jar"]

```

Explicação do Dockerfile:

* FROM openjdk:17-jre-slim: Usa uma imagem base leve com Java 17 JRE para rodar a aplicação.

* WORKDIR /app: Define o diretório padrão para comandos dentro do container.

* COPY target/gerenciador-compromissos.jar /app/gerenciador-compromissos.jar: Copia o arquivo JAR gerado pela build Maven para o container.

* EXPOSE 8080: Informa ao Docker que o container irá escutar a porta 8080.

* ENTRYPOINT ["java", "-jar", "gerenciador-compromissos.jar"]: Define o comando padrão para iniciar a aplicação Java.

### 2. 📦 Documentação do Serviço MySQL no `docker-compose.yml`

Define dois serviços: app e mysql.

* app: constrói a imagem Docker usando o Dockerfile na raiz (context: .).

* Mapeia a porta 8080 da aplicação para o host.

* Passa variáveis de ambiente para conectar ao banco MySQL pelo hostname mysql (nome do serviço).

* mysql: utiliza a imagem oficial do MySQL 8.0, configurada via variáveis de ambiente.

* Monta volume para configuração personalizada do MySQL (pode ser vazio ou conter .cnf).

* Ambos os serviços estão na mesma rede app-network para comunicação interna.

```java
version: '3.8'

        services:
        app:
        build:
        context: ..
        dockerfile: Dockerfile
        ports:
        - "8080:8080"  # Só a app é exposta para o host
        depends_on:
        - mysql
        environment:
        - SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/db_example
        - SPRING_DATASOURCE_USERNAME=springuser
        - SPRING_DATASOURCE_PASSWORD=ThePassword

        mysql:
        image: mysql
        expose:
        - "3306"  # Visível apenas para os containers da mesma rede
        environment:
        - MYSQL_USER=springuser
        - MYSQL_PASSWORD=ThePassword
        - MYSQL_DATABASE=db_example
        - MYSQL_ROOT_PASSWORD=root
        volumes:
        - "./conf.d:/etc/mysql/conf.d:ro"

```

### 🔒 Segurança de Exposição
A porta do banco de dados não é exposta para o host, garantindo que o MySQL só possa ser acessado pela aplicação dentro da mesma rede Docker.


###Descrição Geral
Este serviço configura uma instância do MySQL utilizando a imagem oficial do Docker Hub. Ele define variáveis de ambiente, mapeamento de portas, e monta um volume com arquivos de configuração personalizados.

### ⚙️ Parâmetros do Serviço
`image: mysql`
Utiliza a imagem oficial do MySQL. A versão padrão será a mais recente, a menos que especificado (ex: `mysql:8.0`).


🌐 `ports`

| Porta no Host | Porta no Container | Descrição             |
| ------------- | ------------------ | --------------------- |
| 3306          | 3306               | Porta padrão do MySQL |


🌱 `environment`

| Variável              | Descrição                                             |
| --------------------- | ----------------------------------------------------- |
| `MYSQL_USER`          | Nome do usuário padrão que será criado (`springuser`) |
| `MYSQL_PASSWORD`      | Senha para o usuário padrão (`ThePassword`)           |
| `MYSQL_DATABASE`      | Nome do banco de dados a ser criado (`db_example`)    |
| `MYSQL_ROOT_PASSWORD` | Senha do usuário root do MySQL (`root`)               |

Monta um volume com arquivos de configuração personalizados do MySQL:

📁 `volumes`
``` yaml
- "./conf.d:/etc/mysql/conf.d:ro"
```
- `./conf.d` é o diretório local onde você pode adicionar arquivos `.cnf` para configurar o MySQL.

- Os arquivos são montados como somente leitura (`ro`).

- Exemplo de uso: ajustar parâmetros como tamanho do buffer, charset padrão, etc.

### ✅ Requisitos
- Docker e Docker Compose instalados.

- Docker Compose instalado

- Projeto compilado (mvn clean install) gerando o JAR em target/

### 3. ▶️ Rodando o Docker
Para rodar a aplicação e o MySQL usando Docker Compose, execute os seguintes comandos:

1- Construa as imagens:
```java
docker-compose up build
```

Este comando:

* Constrói a imagem da aplicação usando o Dockerfile.

* Inicia o banco de dados MySQL.

* Inicia sua aplicação Spring Boot.

* A aplicação estará disponível na porta http://localhost:8080.

## Como Rodar o Projeto com Docker

#### Pré-requisitos
- Docker e Docker Compose instalados

- Java 17 instalado

- Maven instalado

#### Passos para executar:

#### 1- Clone o repositório:

```java
git clone <url_do_repositorio>
cd <diretorio_do_projeto>
```

#### 2- Configure o bot do Telegram:

- Crie um bot no Telegram usando o BotFather.

- Obtenha o token do bot.

Configure as variáveis de ambiente no `application.properties`:

```java
telegram.bot.token=SEU_TOKEN_AQUI
telegram.bot.username=SEU_NOME_DE_USUARIO_AQUI
```
#### 3- Compile o projeto:

```java
mvn clean install
```

#### 4 - Construa as imagens e suba os containers:

Na pasta docker, onde está localizado o arquivo docker-compose.yml), execute:
```java
docker-compose up --build
```

Esse comando vai:
-Construir a imagem Docker da aplicação Spring Boot.

-Baixar e iniciar o container do MySQL.

-Subir ambos os containers na mesma rede Docker.

#### 5 - Acesse a aplicação:

Após os containers estarem rodando, sua aplicação estará disponível em:

```aidl
http://localhost:8080
``` 
#### 6- Testando a aplicação
Você pode acessar os endpoints da API, por exemplo via navegador ou ferramentas como Postman, usando a URL acima.

Acesse a documentação interativa em:
```aidl
http://localhost:8080/swagger-ui.html
``` 
