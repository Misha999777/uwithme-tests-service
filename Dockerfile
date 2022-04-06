FROM ubuntu:20.04 AS build
RUN apt-get update
RUN apt-get install -y openjdk-11-jdk
RUN apt-get install -y nodejs
RUN apt-get install -y npm
RUN apt-get install -y maven
COPY testsystem-ui /home/app/testsystem-ui
COPY testsystem-service /home/app/testsystem-service
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM ubuntu:20.04
RUN apt-get update
RUN apt-get install -y openjdk-11-jdk
COPY --from=build /home/app/testsystem-service/target/TestSystem.jar /usr/app/TestSystem.jar
CMD java -jar /usr/app/TestSystem.jar --spring.profiles.active=prod