FROM openjdk:17-jdk-slim

ADD src/main/docker/entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh

ARG JAR_FILE=target/*.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["./entrypoint.sh"]

ENV POSTGRES_USER="postgres"
ENV POSTGRES_PASSWORD="postgres"
ENV POSTGRES_DB="empresa_produto"

COPY src/main/docker/init-database.sh /docker-entrypoint-initdb.d/