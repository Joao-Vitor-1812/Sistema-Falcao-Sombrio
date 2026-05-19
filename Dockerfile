# Estágio 1: Build da aplicação usando Maven e Java 25
FROM maven:3-eclipse-temurin-25 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Estágio 2: Execução da aplicação com o JRE do Java 25
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/falcao-sombrio-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]