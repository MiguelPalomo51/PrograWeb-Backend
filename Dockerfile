FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

ENV PGHOST=dpg-d3vh458dl3ps73fq3aa0-a.oregon-postgres.render.com
ENV PGPORT=5432
ENV PGDATABASE=pistas
ENV PGUSER=pistas_user
ENV PGPASSWORD=4yr6oA6ltbbbuSv4NIWhguJgqBweU9Bv

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]