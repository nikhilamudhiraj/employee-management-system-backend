# Build stage
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Restrict Maven heap to 300MB so Render free tier (512MB RAM) never kills the build
ENV MAVEN_OPTS="-Xmx300m -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -Dmaven.wagon.http.pool=false"

COPY pom.xml .
COPY src ./src

# Fast clean build in batch and quiet mode
RUN mvn clean package -DskipTests -B -q

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

# Tune JVM runtime memory for Render 512MB RAM
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
