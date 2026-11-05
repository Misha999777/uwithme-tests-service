#!/bin/sh
set -euo pipefail

SCHEMA=http://

if [ "${SSL_ENABLED:-}" = "true" ]; then
    SCHEMA=https://
fi

export SCHEMA="${SCHEMA}"

envsubst < application.properties.template > application.properties
