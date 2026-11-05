FROM eclipse-temurin:25-jre-alpine

EXPOSE 8090

RUN apk add --no-cache gettext-envsubst

WORKDIR /usr/app

COPY target/TestSystem.jar image/application.properties.template ./
COPY image/substitute.sh /opt/uwm/

RUN chmod +x /opt/uwm/substitute.sh

CMD /opt/uwm/substitute.sh && exec java -jar /usr/app/TestSystem.jar
