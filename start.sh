#!/bin/bash

# Check if all required environment variables are set
REQUIRED_VARS=("OAUTH2_CLIENT_ID" "OAUTH2_API_KEY" "OAUTH2_USERNAME" "OAUTH2_PASSWORD" "OAUTH2_URL" "OAUTH_RETRY_INTERVAL" "SSL_CERT_FILE")

for VAR in "${REQUIRED_VARS[@]}"; do
  if [ -z "${!VAR}" ]; then
    echo "Error: Environment variable $VAR is not set."
    exit 1
  fi
done

# If all variables are set, run the Java application
java -jar app.jar
