FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine-slim
ARG JAR_FILE=rest-api/target/*.jar
COPY ${JAR_FILE} app.jar
RUN apk add ttf-dejavu
RUN apk add tzdata
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar