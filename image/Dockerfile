FROM eclipse-temurin:21-jre

RUN apt-get update
RUN apt-get install gettext-base

COPY ./target/TestSystem.jar /usr/app/TestSystem.jar

COPY ./image/application.properties.tmpl /usr/app/application.properties.tmpl
COPY ./image/substitute.sh /opt/uwm/substitute.sh

WORKDIR /usr/app

CMD /opt/uwm/substitute.sh && java -jar /usr/app/TestSystem.jar