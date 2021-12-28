FROM adoptopenjdk/openjdk11:jre-11.0.9.1_1-alpine
RUN addgroup -S spring && adduser -S spring -G spring
RUN mkdir -p /laboratory-files
RUN chown -R spring:spring /laboratory-files
RUN chmod -R 666 /laboratory-files
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
