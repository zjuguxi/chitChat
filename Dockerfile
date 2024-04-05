FROM eclipse-temurin:21-jdk as build
WORKDIR /src
COPY . .
RUN ./gradlew clean build -x test

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /src/app/build/libs/app.jar app.jar
CMD ["java", "-jar", "app.jar"]
