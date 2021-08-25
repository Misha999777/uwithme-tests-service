# TestSystem Service

[![Build Status](https://github.com/Misha999777/TestSystem-Service/workflows/Main/badge.svg)](https://github.com/Misha999777/TestSystem-Service/actions?query=workflow%3A%22Main%22)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

Service that runs TestSystem. Uses [Spring Boot](http://projects.spring.io/spring-boot/). TestSystem is a system for
testing any students in any educational institutions,

## Usage

Service is running [here](https://catalina.u-with-me.education) and you are free to use it's public REST API in your
projects, as well as use [web client](https://testsytem.u-with-me.education) (PWA is supported)

You are also welcome to clone, modify, use and redistribute this repository.

## Requirements

For building and running the application you need:

- [JDK 11](https://openjdk.java.net/projects/jdk/11/)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method
in the `tk.tcomad.unibot.Main` class from your IDE.

Alternatively you can use
the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html)
like so:

```shell
mvn spring-boot:run
```

## Copyright

Released under the Apache License 2.0. See the [LICENSE](https://github.com/tCoMaD/UniBot-Service/blob/master/LICENSE)
file.