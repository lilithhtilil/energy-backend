FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-alpine
COPY --from=build /app/target/energy-backend-0.0.1-SNAPSHOT.jar energy-backend.jar

ENTRYPOINT ["java","-Dspring.profiles.active=render","-jar","energy-backend.jar"]