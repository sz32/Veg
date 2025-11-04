# Multi-stage build for optimized image size

# Build stage
FROM gradle:8.4-jdk17 AS build
WORKDIR /app

# Copy gradle files
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY product-apis/build.gradle.kts ./product-apis/

# Download dependencies (cached layer)
RUN gradle :product-apis:dependencies --no-daemon || true

# Copy source code
COPY product-apis/src ./product-apis/src

# Build the application
RUN gradle :product-apis:fatJar --no-daemon

# Runtime stage
FROM openjdk:17-slim
WORKDIR /app

# Create logs directory
RUN mkdir -p /app/logs

# Copy the JAR from build stage
COPY --from=build /app/product-apis/build/libs/product-apis-1.0.0.jar app.jar

# Expose port
EXPOSE 8080

# Set environment variables
ENV PORT=8080
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/health || exit 1

# Run the application
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

