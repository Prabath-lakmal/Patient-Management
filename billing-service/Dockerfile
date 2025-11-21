FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B #-Dmaven.wagon.http.retryHandler.count=3 -Dhttp.socketTimeout=10000

COPY src ./src

RUN mvn clean package

#FROM openjdk:jdk-21 AS runner
FROM eclipse-temurin:21-jdk-jammy AS runner
#FROM eclipse-temurin:21-jre-alpine AS runner

WORKDIR /app

COPY --from=builder ./app/target/billing-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 4001
EXPOSE 9001

ENTRYPOINT ["java", "-jar", "app.jar"]