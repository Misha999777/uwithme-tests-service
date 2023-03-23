# University With Me Test System

[![License](https://img.shields.io/:license-GPL-blue.svg)](https://github.com/Misha999777/uwithme-testsystem-service/blob/master/LICENSE)

## Description

System for testing students. Uses [Spring Boot](http://projects.spring.io/spring-boot/)


## Requirements

For building the application you will need:

- [JDK](https://openjdk.java.net/projects/jdk/11/)
- [Maven](https://maven.apache.org/)
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

## Copyright

Released under the GNU General Public License v2.0.
See the [LICENSE](https://github.com/Misha999777/uwithme-testsystem-service/blob/master/LICENSE) file.