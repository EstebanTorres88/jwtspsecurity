# Use Eclipse Temurin Java 25 for build (needs JDK 25 to match pom.xml)
FROM eclipse-temurin:25-jdk AS build
WORKDIR /workspace

# Copy Maven wrapper and project files first to leverage Docker cache
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src ./src

RUN chmod +x mvnw \
    && ./mvnw -B -DskipTests package

# Runtime image
FROM eclipse-temurin:25-jre
WORKDIR /app

# Copy the built jar from the build stage. Uses wildcard to match final artifact name.
ARG JAR_FILE=target/*.jar
COPY --from=build /workspace/${JAR_FILE} /app/app.jar

# Use non-root user for better security
RUN addgroup --system app && adduser --system --ingroup app app || true
USER app

# Expose default Spring Boot port
EXPOSE 8080

# Render.com provides a PORT env var; let JVM use it when present.
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar /app/app.jar"]