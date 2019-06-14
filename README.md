# Payment API

In order to run this project, you will need gradle(version 4.5):

    gradle build
    java -jar payment.jar

# Documentation
All documentation regarding the usage of the API, including requests, responses and status codes can be found in 'swagger' file. With the open api documentation found, you can access it by using editor.swagger

    Curl examples can be found in 'postman_curls' file

## Architecture, design, principles and dependencies used:

    CLEAN architecture(http, usecase and repository layers)
    SOLID principles(Single responsibility, interfaces)
    REST principles(GET, POST HttpStatus for different responses)
    Java 1.8
    Embedded MongoDB
    Spring Boot 1.5
    Lombok
    Spring actuator(/info endpoint shows version number and last git commit)
    Standard error responses (Overriding some of the spring exceptions and using ControllerAdvice for unknown errors)
    Unit testing
    Sonarqube report

