#!/bin/bash

# Check if all required environment variables are set
REQUIRED_VARS=("OAUTH2_CLIENT_ID" "OAUTH2_API_KEY" "OAUTH2_USERNAME" "OAUTH2_PASSWORD" "OAUTH2_URL")

for VAR in "${REQUIRED_VARS[@]}"; do
  if [ -z "${!VAR}" ]; then
    echo "Error: Environment variable $VAR is not set."
    exit 1
  fi
done

# If all variables are set, run the Docker container
docker run -e OAUTH2_CLIENT_ID="${OAUTH2_CLIENT_ID}" \
           -e OAUTH2_API_KEY="${OAUTH2_API_KEY}" \
           -e OAUTH2_USERNAME="${OAUTH2_USERNAME}" \
           -e OAUTH2_PASSWORD="${OAUTH2_PASSWORD}" \
           -e OAUTH2_URL="${OAUTH2_URL}" \
           -p 8080:8080 springboot-demo
