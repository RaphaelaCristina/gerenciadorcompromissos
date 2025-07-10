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
