#!/bin/bash

# Check if all required environment variables are set
REQUIRED_VARS=("OAUTH2_CLIENT_ID" "OAUTH2_API_KEY" "OAUTH2_USERNAME" "OAUTH2_PASSWORD" "OAUTH2_URL" "OAUTH_RETRY_INTERVAL" "SSL_CERT_FILE")

for VAR in "${REQUIRED_VARS[@]}"; do
  if [ -z "${!VAR}" ]; then
    echo "Error: Environment variable $VAR is not set."
    exit 1
  fi
done

# Configure Java TrustStore with the SSL certificate file
if [ -f "$SSL_CERT_FILE" ]; then
  keytool -import -trustcacerts -noprompt -alias mycert -file "$SSL_CERT_FILE" -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit
else
  echo "Error: SSL certificate file $SSL_CERT_FILE does not exist."
  exit 1
fi

# If all variables are set, run the Java application
java -jar app.jar
