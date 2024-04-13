FROM maven:3.9.6-amazoncorretto-17 as builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:17-slim
WORKDIR /app
COPY --from=builder /app/target/backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
