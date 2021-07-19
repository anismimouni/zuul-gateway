FROM openjdk:8-jdk-alpine
RUN mkdir /root/.ssh
RUN touch /root/.ssh/id_rsa
RUN touch /root/.ssh/known_hosts
ARG SSH_KEY
RUN echo "$SSH_KEY" > /root/.ssh/id_rsa
ARG HOSTS
RUN echo "$HOSTS" > /root/.ssh/known_hosts
ADD /target/config_server-0.0.1-SNAPSHOT.jar config-server.jar
ARG PROFILES
ENV PROFILE=$PROFILES
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${PROFILE}","config-server.jar"]
