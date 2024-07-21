# Use the official OpenJDK 17 image from the Docker Hub
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Install required packages and update vulnerable packages
RUN apt-get update && \
    apt-get install -y \
    bash \
    vim \
    jq \
    wget \
    procps \
    && apt-get install -y --only-upgrade \
    openssl \
    pcre2 \
    libtasn1-6 \
    zlib1g \
    && rm -rf /var/lib/apt/lists/*

# Copy the packaged jar file into the container
ARG JAR_FILE=target/demo-1.0.0.jar
COPY ${JAR_FILE} app.jar

# Copy the start.sh script into the container
COPY start.sh start.sh

# Ensure the start.sh script has executable permissions
RUN chmod +x start.sh

# Set the entry point to the start.sh script
ENTRYPOINT ["./start.sh"]

