## :taxi: Taxi Service

### REST API designed to provide taxi booking services.

## Functionality

- Create new users
- Update and Delete created accounts
- Create new trips
- Manage trip statuses

## Requirements

- Java version 11
- Maven
- MySQL Server

## Summary

- Migrated functionality related to core business logic of [Servlet-based application](https://github.com/ostapchug/taxi-service) to newly created Spring MVC project
- Added logging to all layers of the application
- Used MapStruct to map from business classes to DTO and vice versa
- Added basic validation to DTO classes
- Implemented custom, common and unified error handling functionality using Spring MVC components
- Added the Spring Boot Actuator and configured the ‘/info’ endpoint
- Extended RESTful endpoints by Swagger Documentation
- Created custom validation annotations using ConstraintValidator
- Added multilingual support for validation exception messages
- Integrated application with MySQL relational database using Spring Data JPA
- Added JPA entity mapping
- Added transactions
- Covered all business logic with the unit tests using JUnit 5, Mockito, Hamcrest Matchers
- Covered all APIs using MockMvc
- Implemented integration tests that run against the fully configured application with a temporary in-memory database using TestRestTemplate
- Extended application with the mutation tests and made sure that all of the created mutations were killed
