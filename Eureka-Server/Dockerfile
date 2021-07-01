FROM openjdk:8-jdk-alpine
EXPOSE 8761
ADD /target/eureka-*.jar ap.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
