FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B -Dmaven.wagon.http.retryHandler.count=3 -Dmaven.wagon.http.retryHandler.requestSentEnabled=true -Dhttp.keepAlive=false

COPY src ./src

RUN mvn clean package

#FROM openjdk:21-jdk AS runner
FROM eclipse-temurin:21-jre-alpine AS runner

WORKDIR /app

COPY --from=builder ./app/target/api-gateway-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 4004

ENTRYPOINT ["java", "-jar", "app.jar"]

######
#FROM maven:3.9.9-eclipse-temurin-21 AS builder
#WORKDIR /app
#
## 1. Copy the parent pom and ALL submodule poms first (for layer caching)
#COPY pom.xml .
#COPY auth-service/pom.xml auth-service/
#COPY api-gateway/pom.xml api-gateway/
#
## 2. Copy the actual source code of the dependency
#COPY auth-service/src auth-service/src
#
## 3. CRITICAL: Install the auth-service to the local .m2 repository
## This satisfies the dependency requirement for the next step
#RUN mvn install -pl auth-service -am -DskipTests
#
## 4. Now handle the api-gateway
#WORKDIR /app/api-gateway
## Now this will succeed because auth-service is "installed" locally
#RUN mvn dependency:go-offline -B
#
## 5. Copy gateway source and build
#COPY src ./src
#RUN mvn clean package -DskipTests
#
## Runner Stage
#FROM eclipse-temurin:21-jre-alpine AS runner
#WORKDIR /app
#COPY --from=builder /app/api-gateway/target/*.jar app.jar
#EXPOSE 4004
#ENTRYPOINT ["java", "-jar", "app.jar"]