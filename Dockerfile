# 1 - сборка
FROM maven:3.9.11-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

## 2 - запуск
FROM eclipse-temurin:17-jre
WORKDIR /app
# копирование jar из сборки
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]