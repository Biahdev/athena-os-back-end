# ============ ESTÁGIO 1: Build e extração ============
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /application
COPY pom.xml .
COPY src src

RUN apk add --no-cache maven && \
    mvn clean package -DskipTests && \
    java -Djarmode=layertools -jar target/*.jar extract

RUN jlink \
    --add-modules java.base,java.logging,java.sql,jdk.unsupported,java.desktop,java.management,jdk.crypto.ec,java.naming \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --output /jlink-runtime

FROM alpine:3.19
WORKDIR /application

RUN addgroup -S appgroup && \
    adduser -S appuser -G appgroup && \
    apk add --no-cache tzdata musl-locales && \
    ln -sf /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime

COPY --from=builder /jlink-runtime /opt/jlink-runtime
COPY --from=builder /application/dependencies/ ./
COPY --from=builder /application/spring-boot-loader/ ./
COPY --from=builder /application/snapshot-dependencies/ ./
COPY --from=builder /application/application/ ./

# Configura PATH do Java
ENV PATH="/opt/jlink-runtime/bin:${PATH}"
USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]