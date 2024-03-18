FROM openjdk:21-jdk-slim
LABEL authors="TheSpaceMan915"
WORKDIR /docker

ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]