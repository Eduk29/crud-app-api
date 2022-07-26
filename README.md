# Java REST CRUD API

This project is a study application, so it may contain some errors. Will be a pleasure receive your code review, suggestions and/or tips. :raised_hands:

This application uses Java, Spring Framework, Maven and Postgres Database. And this section deals with installation details, application startup guide, dependencies and more. So you must have the following versions/dependencies installed on your computer to start the application:

- Java JDK => v11.x.x
- Maven => v3.6.x
- Postgres => v14.4.1

## Installation

This project was bootstrapped with [Spring Initializr](https://start.spring.io/)

Clone the project and install the dependencies:

```bash
$ git clone https://github.com/Eduk29/crud-app-api.git
$ cd crud-app-api
$ mvn clean install
```

## Start Application

To start the backend application, go to folder that you did the repository clone and start backend application with:

```bash
$ cd crud-app-api
$ mvn spring-boot:run
```

To validate if everything went well, open your browser at [http://localhost:8080/app-status](http://localhost:8080/app-status). You will see a with welcome and some other informations.

## Dependecies

- Spring Framework
- Java
- Maven
- Postgres

## Application Endpoints

To view the application endpoints documentation, run the application as mentioned in Start Application section and access the Swagger UI in [http://localhost:8080/api/swagger-ui.html](http://localhost:8080/api/swagger-ui.html)


## Application Roadmap

- Add Swagger API to Document API :heavy_check_mark:
- Implement a CRUD for Person :heavy_check_mark:
- Implement a CRUD for User :heavy_check_mark:
- Implement a CRUD for Role
    - List All Roles
    - List Roles by id
    - List Roles by filter
    - Create an Role
    - Remove an Role
    - Update an Role
- Add Spring Security to project
- Configure a basic database authentication
- Implement JWT based authentication

## Developer :computer:

José Eduardo Trindade E Marques  
[LinkedIn](https://www.linkedin.com/in/eduardomarques29/)  
edu.temarques@gmail.com