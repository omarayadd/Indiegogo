
FROM openjdk:17-jdk-alpine

ARG JAR_FILE=target/*.jar

# Copy the built application JAR file to the container
COPY ./target/indiegogo-0.0.1-SNAPSHOT.jar app.jar



# Define the entry point for the container
ENTRYPOINT ["java", "-jar", "/app.jar"]
