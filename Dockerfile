FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y default-mysql-client && rm -rf /var/lib/apt/lists/*

ENV JDBC_DATABASE_URL='jdbc:mysql://msclientsDB/msclientsDB?createDatabaseIfNotExist=true&autoReconnect=true&serverTimeZone=GMT-3'
ENV JDBC_DATABASE_USERNAME=root
ENV JDBC_DATABASE_PASSWORD=admin

ARG JAR_FILE=target/Client_Manager-0.0.1.jar
COPY ${JAR_FILE} ms-clients.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "ms-clients.jar"]