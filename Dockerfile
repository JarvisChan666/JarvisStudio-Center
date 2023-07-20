FROM maven:3.8.1-jdk-8-slim as builder
# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

#Build a release artifact.
RUN mvn package -DskipTests

CMD ["java","-jar","target/employee-0.0.1-SNAPSHOT.jar","/app/target/user-center-backend-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]