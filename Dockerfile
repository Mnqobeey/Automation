FROM maven:3.9.12-eclipse-temurin-25-alpine
WORKDIR /app
COPY . .
CMD ["mvn", "clean", "verify"]
