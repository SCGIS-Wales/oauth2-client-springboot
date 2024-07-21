# Spring Boot Demo Application

This is a Spring Boot demo application designed to interact with an OAuth2 authentication service. The application demonstrates the use of environment variables for configuration, custom metrics for monitoring, and containerization using Docker. It is integrated with GitHub Actions for CI/CD, which includes building, versioning, and deploying Docker images to Docker Hub.

# Overview

# Features

##  OAuth2 Authentication:

The application retrieves an OAuth2 token from a specified authentication service using credentials provided via environment variables.

##  Custom Metrics:

Custom metrics are integrated using Micrometer and Prometheus for monitoring application performance and status.

##  Environment Configuration:

Environment variables are used to configure client ID, API key, username, password, and the authentication URL, ensuring that sensitive information is not hardcoded.

## Containerization with Docker:

The application is containerized using Docker, making it easy to deploy and run in different environments.

## Usage
Environment Variables:

The application uses the following environment variables:

**OAUTH2_CLIENT_ID**: Client ID for OAuth2 authentication.
**OAUTH2_API_KEY**: API key for OAuth2 authentication.
**OAUTH2_USERNAME**: Username for OAuth2 authentication.
**OAUTH2_PASSWORD**: Password for OAuth2 authentication.
**OAUTH2_URL**: URL of the OAuth2 authentication service.


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
