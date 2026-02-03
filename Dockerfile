# Save this as Dockerfile.maven
FROM maven:3.9.12-eclipse-temurin-25-alpine AS builder
WORKDIR /app
COPY . .
CMD ["mvn", "clean", "verify", "sonar:sonar"]
