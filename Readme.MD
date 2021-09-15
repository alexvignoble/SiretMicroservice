# Business Aggregator Microservice

[![CI](https://github.com/AlexVinogradov/rss-aggregator/actions/workflows/main.yml/badge.svg?branch=master)](https://github.com/AlexVinogradov/rss-aggregator/actions/workflows/main.yml)

Business Microservice helps aggregate different informations regarding Various businesses corresponding to a SIRET Number.


## Features
It relies on SIRENE public API to retrieve the needed information. It provides certain functionalities to manage different resources:
- Create/Read/Update/Delete functionalities to manage the SIRET numbers
- "Update" endpoint to retrieve and  update the configured DB with the latest Business information according to the configured SIRET numbers
- On start update of business information based on the default configured SIRET numbers.
- Read funtionality to retrieve the stored Business information.
- SWAGGER documentation for the provided endpoints.


## Tech

Business Microservice uses a number of open source projects/technologies to work properly:

- Maven
- Spring Boot
- Spring Actuator
- Jackson for JSON Mapping support (for unmarshalling)
- Lombok - Boilerplate code reduction
- SLJ4J - Logging
- JUnit - Integration and unit tests.

## Structure

The project is structured as a Maven Reactor project with 3 sub-modules:

- Api - The api itself containing the interfaces, implementations and logic, data model, unit-tests and Spring configuration needed for the beans.
- Integration-tests - Sub-module containing tests that spawn a real Spring context. The purpose is to test the functionalities end to end including security enforcing.
- Example - We provide a integration example in a non-Spring managed project. Integration with Spring managed project will come soon.

## Building

Triggering the install command at maven project will build in order, the api, run the unit tests and pack the jar spring boot application.

Doing a simple
```sh
mvn clean install
```

will run all the above.


## Docker

The project also can be packaged as a docker image and ran as a container.
In the root folder running: 
```sh
docker build . -t <tag>
```
will build a docker image containing a version of the microservice ready to run.

After building the image you can run it using:
```sh
docker run -p 8080:8080 <tag>
```
Note: Portforwarding is needed in order to access the default 8080 port on which the microservice is exposing its endpoints


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.4/maven-plugin/reference/html/#build-image)
