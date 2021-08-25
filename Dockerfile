FROM ubuntu:20.04
RUN apt-get update
RUN apt-get install -y openjdk-11-jdk
COPY TestSystem/UniBot.jar /usr/app/TestSystem.jar
CMD java -jar -Dspring.profiles.active=prod /usr/app/TestSystem.jar