# --- Étape 1 : build avec Maven ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests -B

# --- Étape 2 : image d'exécution légère ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# curl est nécessaire pour le healthcheck défini dans docker-compose.yml.
# apk (et non apt-get) est le gestionnaire de paquets d'Alpine.
RUN apk add --no-cache curl
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]