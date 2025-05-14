FROM openjdk:24-jdk-slim

WORKDIR /app

COPY target/complaint-service-0.0.1-SNAPSHOT.jar complaint-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "complaint-service.jar"]