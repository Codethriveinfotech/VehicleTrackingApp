# Build stage
FROM gradle:8.9-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew :backend:buildFatJar --no-daemon

# Run stage
FROM openjdk:17-slim
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/backend/build/libs/*.jar /app/ktor-app.jar
ENTRYPOINT ["java", "-jar", "/app/ktor-app.jar"]
