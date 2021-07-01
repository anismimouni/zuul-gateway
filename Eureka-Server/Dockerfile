FROM openjdk:8-jdk-alpine
EXPOSE 8761
ADD /target/*.jar eureka-server.jar
ENTRYPOINT ["java","-jar","eureka-server.jar"]
