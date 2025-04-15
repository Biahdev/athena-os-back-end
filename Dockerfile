# Estágio de build
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Estágio de execução
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

RUN mkdir -p /app/config
COPY .env /app/config/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=classpath:/,file:/app/config/"]