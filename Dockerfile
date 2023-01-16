# AS <NAME> to name this stage as maven
FROM docker.io/maven:3.6.3 AS maven

WORKDIR /usr/src/app
COPY ./src /usr/src/app

# Compile and package the application to an executable JAR
RUN mvn package 

FROM docker.io/adoptopenjdk/openjdk11:alpine-jre

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]