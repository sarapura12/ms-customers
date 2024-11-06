FROM openjdk:17-jdk-slim

ARG JAR_FILE=target/Client_Manager-0.0.1.jar
COPY ${JAR_FILE} ms-clients.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "ms-clients.jar"]