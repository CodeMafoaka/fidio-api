FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /app

# Copy gradle wrapper + config first (better caching)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x gradlew

# Copy source
COPY src src
COPY doc doc

# Build jar
RUN ./gradlew clean bootJar --no-daemon


FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy built jar
COPY --from=build /app/build/libs/*.jar app.jar

# No need for ARG/ENV mapping here (Render injects at runtime)
# DATABASE_* should come from Render environment variables

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "exec java -jar app.jar --server.port=${PORT:-8080}"]