#!/bin/bash

# Check if all required environment variables are set
REQUIRED_VARS=("OAUTH2_CLIENT_ID" "OAUTH2_API_KEY" "OAUTH2_USERNAME" "OAUTH2_PASSWORD" "OAUTH2_URL" "OAUTH_RETRY_INTERVAL" "SSL_CERT_FILE")

for VAR in "${REQUIRED_VARS[@]}"; do
  if [ -z "${!VAR}" ]; then
    echo "Error: Environment variable $VAR is not set."
    exit 1
  fi
done

# Set the trust store path and password explicitly
TRUST_STORE_PATH="${JAVA_HOME}/lib/security/cacerts"
TRUST_STORE_PASSWORD="changeit"

# If all variables are set, run the Java application with SSL debug logging and explicit trust store settings
/usr/lib/jvm/java-17-amazon-corretto/bin/java -Djavax.net.debug=ssl,handshake \
     -Djavax.net.ssl.trustStore="${TRUST_STORE_PATH}" \
     -Djavax.net.ssl.trustStorePassword="${TRUST_STORE_PASSWORD}" \
     -jar app.jar
