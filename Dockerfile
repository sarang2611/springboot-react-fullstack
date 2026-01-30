

# Use Java 21 (ARM compatible for Apple Silicon)
FROM eclipse-temurin:21-jdk

# Temp volume (recommended for Spring Boot)
VOLUME /tmp

# Copy the built jar from target folder
COPY target/*.jar app.jar

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","/app.jar"]

