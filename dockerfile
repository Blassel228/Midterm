FROM openjdk:17-jdk-slim
WORKDIR /app
COPY gradlew gradlew.bat /app/
COPY gradle /app/gradle
COPY build.gradle settings.gradle /app/
RUN ./gradlew dependencies
COPY src /app/src
RUN ./gradlew build
EXPOSE 8080
CMD ["./gradlew", "bootRun"]
