FROM openjdk:21-slim
ARG JAR_FILE=/core/core-api/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=live", "-jar", "app.jar"]