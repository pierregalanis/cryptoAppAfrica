# Use official OpenJDK image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy JAR file from build
COPY target/africacrypto-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Default command
ENTRYPOINT ["java", "-jar", "app.jar"]



