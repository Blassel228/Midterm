FROM openjdk:17-jdk-slim
WORKDIR /app
COPY gradlew gradlew.bat /app/
COPY gradle /app/gradle
COPY build.gradle settings.gradle /app/
COPY .env /app/.env
RUN ./gradlew dependencies
COPY src /app/src
RUN ./gradlew build
EXPOSE 8081
CMD ["./gradlew", "bootRun"]
