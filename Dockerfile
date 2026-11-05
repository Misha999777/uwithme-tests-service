FROM eclipse-temurin:25-jre-alpine
EXPOSE 8090

COPY target/TestSystem.jar /usr/app/
CMD java -jar /usr/app/TestSystem.jar
