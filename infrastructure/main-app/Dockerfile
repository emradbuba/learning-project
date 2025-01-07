FROM openjdk:21
WORKDIR /app-workdir
COPY ./../../target/learningproject-*.jar learning-project.jar
# Expose learning-project for other containers on 8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","learning-project.jar"]
