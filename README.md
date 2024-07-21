# Spring Boot Demo Application

This is a demo Spring Boot application that demonstrates the use of environment variables, custom metrics, and Docker.

## Prerequisites

- Java 17
- Maven
- Docker

## Building the Project

1. Clone the repository:
    ```bash
    git clone <repository-url>
    cd springboot-app
    ```

2. Build the application using Maven:
    ```bash
    mvn clean package
    ```

3. Build the Docker image:
    ```bash
    docker build -t springboot-demo .
    ```

## Running the Application

Run the Docker container with the necessary environment variables:
```bash
docker run -e OAUTH2_CLIENT_ID='your_client_id' -e OAUTH2_API_KEY='your_api_key' -e OAUTH2_USERNAME='your_username' -e OAUTH2_PASSWORD='your_password' -e OAUTH2_URL='https://api.organization.org/oauth2/token' -p 8080:8080 springboot-demo
