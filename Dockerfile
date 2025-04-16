FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /workspace
COPY pom.xml .
COPY src src

RUN apk add --no-cache maven && \
    mvn clean package -DskipTests && \
    mkdir -p /layers && \
    java -Djarmode=layertools -jar target/*.jar extract --destination /layers


FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -S spring && \
    adduser -S spring -G spring && \
    apk add --no-cache tzdata curl && \
    ln -sf /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime && \
    rm -rf /var/cache/apk/*

COPY --from=builder --chown=spring:spring /layers/dependencies/ ./
COPY --from=builder --chown=spring:spring /layers/spring-boot-loader/ ./
COPY --from=builder --chown=spring:spring /layers/snapshot-dependencies/ ./
COPY --from=builder --chown=spring:spring /layers/application/ ./

HEALTHCHECK --interval=10s --timeout=3s --start-period=5s --retries=3 \
    CMD sh -c "echo 'Verificando saúde da aplicação em http://localhost:8080/health/api' && \
               if curl -fsS http://localhost:8080/health/api | grep -q 'OK'; then \
                 echo 'Healthcheck passou!'; \
                 exit 0; \
               else \
                 echo 'Healthcheck falhou!'; \
                 exit 1; \
               fi"

USER spring

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]