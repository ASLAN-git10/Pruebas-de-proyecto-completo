# Etapa 1: Build
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copiar el wrapper de maven y el pom
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Dar permisos de ejecución al wrapper (por si se perdieron en Windows)
RUN chmod +x ./mvnw

# Descargar dependencias (optimiza caché de Docker)
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente
COPY src src

# Construir el .jar saltándose los tests (para despliegues más rápidos)
RUN ./mvnw clean package -DskipTests

# Etapa 2: Run
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el JAR compilado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Render inyectará el puerto automáticamente
# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
