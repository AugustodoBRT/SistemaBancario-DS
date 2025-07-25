# Etapa 1: Build com Maven e JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copia apenas os arquivos necessários para o build
COPY pom.xml .
COPY src ./src

# Compila o projeto, ignorando os testes
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final mais leve com apenas o JAR
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copia o JAR gerado do build anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta da aplicação
EXPOSE 8080

# Comando de execução da aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]