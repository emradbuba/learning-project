FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/learningproject-1.0.0-SNAPSHOT.jar /app/restapp-1.0.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/restapp-1.0.0-SNAPSHOT.jar"]
