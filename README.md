<h1 style="font-size: 42px;">Graal JS Rest Api</h1>

# Summary
This application provides a REST API wrapper around the GraalJs JavaScript interpreter, enabling users to evaluate arbitrary JavaScript code through non-blocking or optionally blocking execution. It supports executing scripts while collecting their stdout and stderr streams, retrieving script lists with statuses, execution times, and other details, and accessing detailed information about individual scripts, including their output. Additionally, it allows forcibly stopping running or scheduled scripts to prevent resource overconsumption and deleting inactive scripts. The application leverages an in-memory H2 database for storing script details and is built using Spring Boot with Swagger for API documentation.
# Endpoints
<h1 style="font-size: 14px;">Acceptable endpoints in the application:</h1>

- POST: /scripts - Execute a script with optional blocking.
- GET: /scripts - Retrieve a list of scripts with optional filtering by status.
- GET: /scripts/{id} - Retrieve detailed information about a script by its ID.
- PATCH: /scripts/{id} - Forcibly stop a running script by its ID.
- DELETE: /scripts/{id}/delete - Delete an inactive script by its ID.

# Project structure
- src/main/java: contains all the source code for the application.
- Dockerfile - is a text file that contains instructions for building a Docker image.
- checkstyle/checkstyle.xml - is a configuration file for the checkstyle tool, which is used to check the code style. It contains settings for various checkstyle modules that perform various code checks for compliance with style standards.
- pom.xml - used to configure and create a Maven project, add the necessary dependencies.

# Technologies used
- JDK 17
- SpringBoot 3.3.1
- H2 database
- Apache Maven 3.8.1
- Docker
- Graalvm
- SWAGGER
- Mapstruct 1.5.5

# How to run the application
In order to launch this project, you need to take the following steps:
1. Clone this project from GitHub to your local machine.
2. Install the following software:
- IntelliJ IDEA (IDE) to run the application.
- Install Postman for sending requests, or you can use your browser.
- Install DOCKER DESKTOP from https://www.docker.com/products/docker-desktop/ if you want to run the program through a docker and follow the setup steps).
3. Open the project in IntelliJ IDEA.
4. Build the project using Maven: mvn clean package.
5. In the terminal, enter the command : docker build -t generation-app .
- and then enter the command to run the application on port 8081: docker run -p 8081:8080 graal-js-rest-app
6. If all the steps have been followed correctly, the server will start successfully.
7. Use Postman or a web browser to interact with the endpoints and test the application by this URL http://localhost:8081/ or http://localhost:8081/swagger-ui.html if you want to use SWAGGER UI for testing.
   Please follow these instructions carefully to launch the project.
