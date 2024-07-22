# Use the official Amazon Corretto 17 image from the Docker Hub
FROM amazoncorretto:17

# Set the working directory inside the container
WORKDIR /app

# Update the package lists, upgrade installed packages, and install required packages
RUN yum update -y && \
    yum install -y \
    bash \
    vim \
    jq \
    curl \
    wget \
    procps \
    openssl \
    pcre2 \
    libtasn1 \
    tar \
    zlib && \
    yum clean all

# Copy the packaged jar file into the container
ARG JAR_FILE=target/demo-1.0.0.jar
COPY ${JAR_FILE} app.jar

# Copy the start.sh script into the container
COPY start.sh start.sh

# Ensure the start.sh script has executable permissions
RUN chmod +x start.sh

# Set the entry point to the start.sh script
ENTRYPOINT ["./start.sh"]
