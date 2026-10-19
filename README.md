# University With Me Test System

As a component of the broader `University With Me` project, this application provides universities and other educational entities with a platform to set up tests and assess students.

## Requirements

To develop the application, you will need:

- [JDK](https://openjdk.java.net/projects/jdk/21/)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)

## Running the application locally

To run this application, follow these steps:

1. Download the [Docker files](https://github.com/HappyMary16/uwithme-docker-files).
2. Start them using:

```shell
docker compose up -d
```

3. Start the `testsystem-service` using:

```shell
mvn spring-boot:run
```

## Copyright

Released under the GNU General Public License v2.0.
See the [LICENSE](https://github.com/Misha999777/uwithme-testsystem-service/blob/master/LICENSE) file.