# TestSystem

[![License](http://img.shields.io/:license-apache-blue.svg)](https://github.com/Misha999777/U-With-Me-Tests/blob/angular/LICENSE)

## Description

System for testing students. Uses [Spring Boot](http://projects.spring.io/spring-boot/)
and [Angular](https://angular.io).

## Requirements

For building the application you will need:

- [JDK](https://openjdk.java.net/projects/jdk/11/)
- [Maven](https://maven.apache.org/)
- [Node.js](https://nodejs.org/)
- [NPM](https://www.npmjs.com/)
- [Docker](https://www.docker.com/)

## Running the application locally

You can run this application by

1. Downloading [Docker files](https://github.com/HappyMary16/uwithme-docker-files)
2. Starting them with

```shell
docker compose up -d
```

3. Starting testsystem-service with

```shell
mvn spring-boot:run
```

4. Installing dependencies of testsystem-ui with

```shell
npm install
```

5. Starting testsystem-ui with

```shell
npx ng serve
```

## Copyright

Released under the Apache License 2.0.
See the [LICENSE](https://github.com/Misha999777/U-With-Me-Tests/blob/angular/LICENSE) file.